package com.mobile.hulklee01.musicallife;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.URLUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class CrawlingService extends IntentService {
    private ArrayList<MusicalInfo> mMusicalInfos = new ArrayList<>();
    private final String PLAYDB_URL = "http://www.playdb.co.kr/playdb/playdblist.asp?sReqMainCategory=000001&Page=";
    private final String PLAYDB_DETAIL_URL = "http://www.playdb.co.kr/playdb/playdbDetail.asp?sReqPlayno=";
    private final String TAG = "ChoiSeonMun";
    private MusicalDBHelper mDB;
    private static int Page = 0;

    public CrawlingService() {
        super("CrawlingService");
        ++Page;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mDB = new MusicalDBHelper(getApplicationContext());

        // 페이지에서 Url을 따온다.
        Document doc;
        Elements elements;
        try {
            doc = Jsoup.connect(PLAYDB_URL + Page).get();
            elements = doc.getElementsByAttribute("onClick");
            for (Element e : elements) {
                if (e.is("a") == false) {
                    continue;
                }
                String[] linkAttr = e.attr("onClick").split("'");
                MusicalInfo info = new MusicalInfo();
                info.Url = PLAYDB_DETAIL_URL + linkAttr[1];
                info.Title =  e.text();
                mMusicalInfos.add(info);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (MusicalInfo info : mMusicalInfos) {
            crawl(info);
        }
    }

    private void crawl(MusicalInfo info) {
        int trsLast = 0;
        try {
            // 분석
            String url = info.Url;
            String title = info.Title;

            Document doc = Jsoup.connect(url).get();
            Element pdDetail = doc.getElementsByClass("pddetail").first();
            String image = pdDetail.select("h2 > img").attr("src");


            Element detailList = pdDetail.getElementsByClass("detaillist").first();
            Elements table = detailList.select("table");
            Elements trs = table.select("tr");
            trsLast = trs.size() - 1;

            String duration = trs.get(1).text();
            String location = trs.get(2).text();

            // 예외 처리
            String lastElemText = trs.get(trsLast).text();
            if (URLUtil.isValidUrl(lastElemText)) {
                trsLast -= 1;
                lastElemText = trs.get(trsLast).text();
            }

            String playtimeText = lastElemText.split("분")[0];
            int playtime = Integer.parseInt(playtimeText);
            String rating = trs.get(trsLast - 1).text();
            String bookingSite = detailList.select("p > a").attr("href");

            Element detailContentsBox = doc.getElementsByClass("detail_contentsbox").first();
            String information = detailContentsBox.select("p").text();

            Log.d("Url", url);
            Log.d("Title", title);
            Log.d("Image", image);
            Log.d("Duration", duration);
            Log.d("Location", location);
            Log.d("Playtime", playtime + "");
            Log.d("Rating", rating);
            Log.d("BookingSite", bookingSite);
            Log.d("Information", information);

            // DB에 삽입한다.
            mDB.insert()
                    .url(url)
                    .title(title)
                    .image(image)
                    .duration(duration)
                    .location(location)
                    .playtime(playtime)
                    .bookingsite(bookingSite)
                    .rating(rating)
                    .information(information)
                    .subscribe(false)
                    .done();
//
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class MusicalInfo {
        public String Url;
        public String Title;
    }
}
