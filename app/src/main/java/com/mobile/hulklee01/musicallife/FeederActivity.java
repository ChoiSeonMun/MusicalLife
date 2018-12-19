package com.mobile.hulklee01.musicallife;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class FeederActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeder_listview);

        final ArrayList<FeederListViewItem> items = new ArrayList<>();

        ListView feeder;
        FeederListViewAdapter feederAdapter;

        feederAdapter = new FeederListViewAdapter(getApplicationContext(), R.layout.activity_feeder, items);

        feeder = (ListView) findViewById(R.id.listview_newsfeed);
        feeder.setAdapter(feederAdapter);

        //임의로 넣은 값
        feederAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.elizabeth), "엘리자벳", "블루스퀘어 인터파크홀(구 삼성전자홀)", "2018/11/17 ~ 2019/02/10");
        feederAdapter.addItem(ContextCompat.getDrawable(this, R.drawable.cuza), "태양의 서커스 - 쿠자", "잠실종합운동장 내 빅탑", "2018/11/03 ~ 2019/01/06");


        feeder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //아이템 데이터 받아오기
                FeederListViewItem item = (FeederListViewItem) parent.getItemAtPosition(position);
                Drawable imageDrawable = item.getMusicalImage();
                //이미지를 Bitmap객채로 만들기(근데 프로젝트 안에 있는 이미지만 되는듯?)
                Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher_foreground);
                String titleStr = item.getMusicalTitle();
                String placeStr = item.getMusicalPlace();
                String dateStr = item.getMusicalDate();

                Intent getInfo = new Intent(getApplicationContext(), InfoActivity.class);//실행할 엑티비티명 입력
                getInfo.putExtra("image", imageBitmap); // 이미지 넘기기
                getInfo.putExtra("title", titleStr); // 제목 넘기기
                getInfo.putExtra("place", placeStr); // 장소 넘기기
                getInfo.putExtra("date", dateStr); // 기간 넘기기
                startActivity(getInfo);
            }
        });

    }

    //토글 버튼 클릭 이벤트 (클릭 이벤트 잘 처리되나 확인용)
    public void musicalSub(View view) {
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

