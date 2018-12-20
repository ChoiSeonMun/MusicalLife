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

public class InfoActivity extends AppCompatActivity {
    //변수
    TextView mtitleinfo;
    ImageView mImgInfo;
    TextView mTextSimple;
    TextView mTextInfo;
    //이미지 경로
    String imgpath = "img";
    //예매 사이트 경로
    String reservepath;
    //상세정보 사이트 경로
    String morepath;
    //DB
    SQLiteDatabase db;
    String dbName = "TestDB";
    String tableName = "TestTable";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        reservepath = "http://ticket.interpark.com/Ticket/Goods/GoodsInfo.asp?GoodsCode=18012338";
        morepath = "http://www.playdb.co.kr/playdb/playdbDetail.asp?sReqPlayno=131089";


        Intent intent = getIntent();

        String date = intent.getStringExtra("date");
        String title =intent.getStringExtra("title");
        String place = intent.getStringExtra("place");


        mtitleinfo = (TextView)findViewById(R.id.title_info);
        mtitleinfo.setText(title);

        mTextSimple = (TextView)findViewById(R.id.textview_simpleinfo);
        mTextSimple.setText(
                "\n일시 : " + date +
                "\n장소 : " + place +
                "\n출연 : 김땡땡, 최땡땡");

        mTextInfo = (TextView)findViewById(R.id.textview_info);
        mTextInfo.setText("Test\nTest\nTest\ntest\nt\nz\nz\nx\nx\nd\ne\ng\nh\nbv\n2\nd\ne\nf\nmdffd\nl\n\n\n\n\nffg\n");
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
