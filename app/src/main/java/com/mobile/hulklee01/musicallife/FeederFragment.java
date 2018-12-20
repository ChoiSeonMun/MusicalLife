package com.mobile.hulklee01.musicallife;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class FeederFragment extends Fragment {
    private final String PLAYDB_URL = "http://www.playdb.co.kr/playdb/playdblist.asp?sReqMainCategory=000001&Page=";
    private final String PLAYDB_DETAIL_URL = "http://www.playdb.co.kr/playdb/playdbDetail.asp?sReqPlayno=";
    private int mPage = 1;

    private FeederListViewAdapter mAdapter;
    private ArrayList<MusicalInfo> mInfos = ListManager.getList();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 페이지의 Url을 크롤링한다.
        mAdapter = new FeederListViewAdapter(getContext().getApplicationContext(), R.layout.activity_feeder, mInfos);
        Thread thread = new Thread(() -> crawlPage());
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View feederFragment = inflater.inflate(
                R.layout.activity_feeder_listview, container, false
        );

        // 리스트를 뷰를 업데이트 한다.
        ListView feeder = (ListView) feederFragment.findViewById(R.id.listview_newsfeed);
        feeder.setAdapter(mAdapter);
        feeder.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getContext().getApplicationContext(), InfoActivity.class);//실행할 엑티비티명 입력
            intent.putExtra("idx", position);
            startActivity(intent);
        });

        // 버튼을 세팅한다.
        View listViewItemLayout = inflater.inflate(R.layout.activity_feeder, feeder, false);
        Button buttonSub = (Button) listViewItemLayout.findViewById(R.id.musical_subscribe_button);
        buttonSub.setOnClickListener(v -> {
            boolean bOn = ((ToggleButton) v).isChecked();
            if (bOn) {
                Toast.makeText(getContext().getApplicationContext(), "Sub",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext().getApplicationContext(), "unSub",
                        Toast.LENGTH_SHORT).show();
            }
        });

        CrawlingTask crawlingTask = new CrawlingTask();
        MusicalInfo[] musicalInfoArr = new MusicalInfo[mInfos.size()];
        musicalInfoArr = mInfos.toArray(musicalInfoArr);
        crawlingTask.execute(musicalInfoArr);

        return feederFragment;
    }

    public void crawlPage() {
        // 페이지에서 Url을 따온다.
        Document doc;
        Elements elements;
        try {
            doc = Jsoup.connect(PLAYDB_URL + mPage).get();
            elements = doc.getElementsByAttribute("onClick");
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

    private void crawlMusical(MusicalInfo info) {
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
            info.bSubscribe = false;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class CrawlingTask extends AsyncTask<MusicalInfo, Integer, Long> {
        @Override
        protected Long doInBackground(MusicalInfo... musicalInfos) {
            for (MusicalInfo info : musicalInfos) {
                crawlMusical(info);
            }
            return 1L;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            super.onPostExecute(aLong);
        }
    }
}