package com.mobile.hulklee01.musicallife;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class InfoActivity extends AppCompatActivity {
    private ArrayList<MusicalInfo> mList = ListManager.getList();

    MusicalInfo mMusicalInfo;

    TextView mTitle;
    ImageView mImage;
    TextView mSimpleInfo;
    TextView mDetailInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        // 변수 참조
        mTitle = (TextView) findViewById(R.id.title);
        mImage = (ImageView) findViewById(R.id.image);
        mSimpleInfo = (TextView) findViewById(R.id.simple_info);
        mDetailInfo = (TextView) findViewById(R.id.detail_info);

        // 인덱스 받아오기
        Intent intent = getIntent();
        int idx = intent.getIntExtra("idx", 0);

        // 값 반영
        mMusicalInfo = mList.get(idx);

        mTitle.setText(mMusicalInfo.Title);
        Glide.with(this).load(mMusicalInfo.Image).into(mImage);
        String simpleInfo = String.format("일시 : %s\n장소 : %s\n관람등급 : %s\n관람시간 : %s분\n",
                mMusicalInfo.Duration, mMusicalInfo.Location, mMusicalInfo.Rating, mMusicalInfo.Playtime);
        mSimpleInfo.setText(simpleInfo);
        mDetailInfo.setText(mMusicalInfo.Information);
    }

    public void onClick(View v){
        Intent intent;

        //사이트 연결
        switch(v.getId()){
            case R.id.button_more:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mMusicalInfo.Url));
                startActivity(intent);
                break;
            case R.id.button_reserve:
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mMusicalInfo.BookingSite));
                startActivity(intent);
                break;
        }
    }

    //구독
    public void SubClick(View view) {
        boolean on = ((ToggleButton) view).isChecked();
        if (on) {
            Toast.makeText(getApplicationContext(), "Sub",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "unSub",
                    Toast.LENGTH_SHORT).show();
        }
    }


}
