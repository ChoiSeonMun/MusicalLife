package com.mobile.hulklee01.musicallife;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.CalendarContract;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.URLUtil;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayout;
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;

public class FeederActivity extends AppCompatActivity {
    private final String TAG = "FeederActivity";
    private final int ADDED = 1;

    private FeederListViewAdapter mAdapter;
    private Crawler mCrawler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeder_listview);

        mAdapter = new FeederListViewAdapter(this, R.layout.activity_feeder, ListManager.getList());
        mCrawler = new Crawler(mAdapter);

        // 리스트뷰를 세팅한다.
        ListView feeder = (ListView) findViewById(R.id.listview_newsfeed);
        feeder.setAdapter(mAdapter);
        feeder.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(getApplicationContext(), InfoActivity.class);//실행할 엑티비티명 입력
            intent.putExtra("idx", position);
            startActivity(intent);
        });

        // RefreshLayout을 세팅한다.
        SwipyRefreshLayout refreshLayout = (SwipyRefreshLayout) findViewById(R.id.refresh_layout);
        refreshLayout.setOnRefreshListener(direction -> {
            Thread thread1 = new Thread(() -> {
                mCrawler.crawl();
            });
            thread1.start();

            try {
                thread1.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            refreshLayout.setRefreshing(false);
        });

        mCrawler.crawl();
    }

    public void onClickAdd(View v) {
        // 해당 뮤지컬 가져오기
        int position = (Integer) v.getTag();
        MusicalInfo musicalInfo = ListManager.getList().get(position);

        // 날짜 구하기
        String[] dates = musicalInfo.Duration.split("~");
        String[] startDate = dates[0].trim().split("/");
        String[] endDate = dates[1].trim().split("/");

        // 날짜 설정하기
        Calendar start = Calendar.getInstance();
        Calendar end = Calendar.getInstance();
        setTime(start, startDate);
        setTime(end, endDate);

        // 구글 캘린더에 날짜 추가하기
        Intent intent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, start.getTimeInMillis())
                .putExtra(CalendarContract.EXTRA_EVENT_END_TIME, end.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, musicalInfo.Title)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, musicalInfo.Location);
        startActivity(intent);
    }

    private void setTime(Calendar time, String[] date) {
        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);
        time.set(year, month, day);
    }
}
