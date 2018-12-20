package com.mobile.hulklee01.musicallife;

import android.os.AsyncTask;
import android.util.Log;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class Crawler {
    private final String PLAYDB_URL = "http://www.playdb.co.kr/playdb/playdblist.asp?sReqMainCategory=000001&Page=";
    private final String PLAYDB_DETAIL_URL = "http://www.playdb.co.kr/playdb/playdbDetail.asp?sReqPlayno=";
    private int mListIdx = 0;
    private int mPage = 1;
    private ArrayList<MusicalInfo> mInfos = ListManager.getList();
    private final FeederListViewAdapter mAdapter;

    public Crawler(FeederListViewAdapter adapter) {
        mAdapter = adapter;
    }

    public void crawl() {
        Thread thread1 = new Thread(() -> {
            crawlPage();
            extractDetailAsync();
        });
        thread1.start();

        try {
            thread1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void crawlPage() {
        // 페이지를 크롤링 해 각 뮤지컬들의 이름과 Url을 추출한다.
        Document doc;
        Elements elements;
        try {
            doc = Jsoup.connect(PLAYDB_URL + mPage).get();
            elements = doc.getElementsByAttribute("onClick");
            elements.remove(1); // 중복되는 정보를 삭제한다.
            for (Element e : elements) {
                if (e.is("a") == false) {
                    continue;
                }
                String[] linkAttr = e.attr("onClick").split("'");
                MusicalInfo info = new MusicalInfo();
                info.Url = PLAYDB_DETAIL_URL + linkAttr[1];
                info.Title =  e.text();
                mInfos.add(info);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        ++mPage;
    }

    private void extractDetailAsync() {
        // 나머지 데이터를 비동기적으로 크롤링한다.
        CrawlingTask crawlingTask = new CrawlingTask();
        MusicalInfo[] musicalInfoArr = new MusicalInfo[mInfos.size()];
        musicalInfoArr = mInfos.toArray(musicalInfoArr);
        crawlingTask.execute(musicalInfoArr);
    }

    private class CrawlingTask extends AsyncTask<MusicalInfo, Integer, Long> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // 시작
        }

        @Override
        protected Long doInBackground(MusicalInfo... musicalInfos) {
            for (; mListIdx < musicalInfos.length; ++mListIdx) {
                MusicalInfo info = musicalInfos[mListIdx];
                extractDetail(info);
                publishProgress();
            }
            return 1L;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(Long aLong) {
            // 끝
            super.onPostExecute(aLong);
        }
    }

    private void extractDetail(MusicalInfo info) {
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

            String playtime = lastElemText.split("분")[0];
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

            // 추가
            info.Image = image;
            info.Duration = duration;
            info.Location = location;
            info.Playtime = playtime;
            info.Rating = rating;
            info.BookingSite = bookingSite;
            info.Information = information;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
