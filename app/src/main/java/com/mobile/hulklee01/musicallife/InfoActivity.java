package com.mobile.hulklee01.musicallife;

import android.content.Intent;
import android.database.Cursor;
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

public class InfoActivity extends AppCompatActivity {
    //변수
    TextView mtitleinfo;
    ImageView mImgInfo;
    TextView mTextSimple;
    TextView mTextInfo;
    //이미지 경로
    String imgpath;
    //예매 사이트 경로
    String reservepath;
    //상세정보 사이트 경로
    String morepath;
    //DB
    SQLiteDatabase db;
    MusicalDBHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //값 받아오기
        Intent intent = getIntent();

        int id = intent.getIntExtra("id");
        String title = helper.getTitle(id);
        reservepath = helper.getBookingSite(id);
        morepath = helper.?;
        String date = helper.getDuration(id);
        String place = helper.getLocation(id);
        String content = helper.getInformation(id);

        //이미지
        imgpath = helper.getImageLink(id);
        mImgInfo = (ImageView) findViewById(R.id.image_info);
        Glide.with(this).load(imgpath).into(mImgInfo);

        //타이틀
        mtitleinfo = (TextView)findViewById(R.id.title_info);
        mtitleinfo.setText(title);

        //간단한 내용
        mTextSimple = (TextView)findViewById(R.id.textview_simpleinfo);
        mTextSimple.setText(
                "\n일시 : " + date +
                "\n장소\n : " + place);

        //세부내용
        mTextInfo = (TextView)findViewById(R.id.textview_info);
        mTextInfo.setText(content);
    }

    public void onClick(View v){
        Intent intent;
        //사이트 연결
        switch(v.getId()){
            case R.id.button_more:
                Toast. makeText (getApplicationContext(),morepath,Toast. LENGTH_SHORT ).show();
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(morepath));
                startActivity(intent);
                break;
            case R.id.button_reserve:
                Toast. makeText (getApplicationContext(),reservepath,Toast. LENGTH_SHORT ).show();
                intent = new Intent(Intent.ACTION_VIEW, Uri.parse(reservepath));
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
