package com.mobile.hulklee01.musicallife;

import android.content.Intent;
import android.os.AsyncTask;
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

public class FeederActivity extends AppCompatActivity {
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
        Toast.makeText(this, "Click", Toast.LENGTH_SHORT).show();
    }
}
