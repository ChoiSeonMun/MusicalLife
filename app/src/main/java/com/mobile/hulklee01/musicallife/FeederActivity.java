package com.mobile.hulklee01.musicallife;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class FeederActivity extends AppCompatActivity {
    private static final String TAG = "FeederActivity";
    private DBHelper mDbOpenHelper;
    private FeederListViewItem listViewItem;
    private Cursor mCursor;
    private ListView feeder;
    private FeederListViewAdapter feederAdapter;
    private ArrayList<FeederListViewItem> items;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeder_listview);

        // 크롤링 시작
        Intent intent = new Intent(this, CrawlingService.class);
        startService(intent);

        mDbOpenHelper = new DBHelper(this);
        try {
            mDbOpenHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        items = new ArrayList<>();


        feederAdapter = new FeederListViewAdapter(getApplicationContext(), R.layout.activity_feeder, items);

        feeder = (ListView) findViewById(R.id.listview_newsfeed);
        feeder.setAdapter(feederAdapter);

        doWhileCursorToArray();

        for(FeederListViewItem i : items){
            Log.d(TAG, "name = " + i.getMusicalTitle());
        }




        feeder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //아이템 데이터 받아오기
                FeederListViewItem item = (FeederListViewItem) parent.getItemAtPosition(position);
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

    private void doWhileCursorToArray() {

        mCursor = null;
        //DB에 있는 모든 컬럼을 가져옴
        mCursor = mDbOpenHelper.getAllColumns();
        //컬럼의 갯수 확인
        Log.i(TAG, "Count = " + mCursor.getCount());

        while (mCursor.moveToNext()) {
            //InfoClass에 입력된 값을 압력
            listViewItem = new FeederListViewItem(
                    mCursor.getBlob(mCursor.getColumnIndex("image")),
                    mCursor.getString(mCursor.getColumnIndex("title")),
                    mCursor.getString(mCursor.getColumnIndex("location")),
                    mCursor.getString(mCursor.getColumnIndex("date"))
            );
            //입력된 값을 가지고 있는 InfoClass를 InfoArray에 add
            items.add(listViewItem);
            feederAdapter.addItem(listViewItem.getMusicalImage(),listViewItem.getMusicalTitle(),listViewItem.getMusicalPlace(),listViewItem.getMusicalDate());
        }
        //Cursor 닫기
        mCursor.close();
    }

    public byte[] getByteArrayFromDrawable(Drawable d) {
        Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] data = stream.toByteArray();

        return data;
    }

    public void onDestory() {
        mDbOpenHelper.close();
        super.onDestroy();
    }
}

