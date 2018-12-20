package com.mobile.hulklee01.musicallife;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class CrawlingService extends IntentService {
    private ArrayList<String> mUrls = new ArrayList<>();
    private final String PLAYDB_URL = "http://www.playdb.co.kr/playdb/playdblist.asp?Page=";
    private final String PLAYDB_DETAIL_URL = "http://www.playdb.co.kr/playdb/playdbDetail.asp?sReqPlayno=";
    private final String TAG = "ChoiSeonMun";
    private DBHelper mDBHelper;
    private static int Page = 0;

    public CrawlingService() {
        super("CrawlingService");

        //mDBHelper = new DBHelper(getApplicationContext());
        ++Page;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // 페이지에서 Url을 따온다.
        Document doc;
        Elements elements;
        try {
             doc = Jsoup.connect(PLAYDB_URL + Page).get();
             elements = doc.getElementsByAttribute("onClick");
             for (Element e : elements) {
                 String[] linkAttr = e.attr("onClick").split("'");
                 String url = PLAYDB_DETAIL_URL + linkAttr[1];
                 mUrls.add(url);
             }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String s : mUrls) {
            crawl(s);
        }
    }

    private void crawl(String url) {
        CrawledContent.Builder c = new CrawledContent.Builder();

        c.url(url);
        try {
            Document doc = Jsoup.connect(url).get();

            // Parse
            String title = doc.getElementsByClass("title").first().text();

            Element pdDetail = doc.getElementsByClass("pddetail").first();
            String image = pdDetail.select("h2 > img").attr("src");


            Element detailList = pdDetail.getElementsByClass("detaillist").first();
            Elements table = detailList.select("table");
            Elements trs = table.select("tr");

            String duration = trs.get(1).text();
            String location = trs.get(2).text();
            String actors = trs.get(3).text();
            String playtimeText = trs.get(5).text().split("분")[0];
            int playtime = Integer.parseInt(playtimeText);
            String bookingSite = detailList.select("p > a").attr("href");

            // Build



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
