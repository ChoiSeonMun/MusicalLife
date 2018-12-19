package com.mobile.hulklee01.musicallife;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;

import java.sql.Blob;

public class DBHelper {
    private static final String DATABASE_NAME = "Musical.db";
    private static final int DATABASE_VERSION = 1;
    public static SQLiteDatabase mDB;
    private DatabaseHelper mDBHelper;
    private Context mCtx;

    private class DatabaseHelper extends SQLiteOpenHelper {

        // 생성자
        public DatabaseHelper(Context context, String name,
                              SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        // 최초 DB를 만들때 한번만 호출된다.
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(MusicalDB.CreateDB._CREATE);

        }

        // 버전이 업데이트 되었을 경우 DB를 다시 만들어 준다.
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS "+MusicalDB.CreateDB._TABLENAME);
            onCreate(db);
        }
    }

    public DBHelper(Context context){
        this.mCtx = context;
    }

    public DBHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        mDB.close();
    }

    public long insertColumn(byte[] img, String title, String location, String date, String actor, String plot, String detailInfo, String bookingSite, int subsbcribe){


        ContentValues values = new ContentValues();
        values.put(MusicalDB.CreateDB.IMAGE,img);
        values.put(MusicalDB.CreateDB.TITLE,title);
        values.put(MusicalDB.CreateDB.LOCATION,location);
        values.put(MusicalDB.CreateDB.DATE,date);
        values.put(MusicalDB.CreateDB.ACTOR,actor);
        values.put(MusicalDB.CreateDB.PLOT,plot);
        values.put(MusicalDB.CreateDB.DETAILINFO,detailInfo);
        values.put(MusicalDB.CreateDB.BOOKINGSITE,bookingSite);
        values.put(MusicalDB.CreateDB.SUBSCRIBE,subsbcribe);
        return mDB.insert(MusicalDB.CreateDB._TABLENAME,null,values);
    }


    //커서 전체를 선택하는 메소드
    public Cursor getAllColumns() {
        return mDB.query(MusicalDB.CreateDB._TABLENAME, null, null, null, null, null, null);
    }

    //ID 컬럼 얻어오기
    public Cursor getId(long id) {
        Cursor c = mDB.query(MusicalDB.CreateDB._TABLENAME, null,
                "_id="+id, null, null, null, null);
        //받아온 컬럼이 null이 아니고 0번째가 아닐경우 제일 처음으로 보냄
        if (c != null && c.getCount() != 0)
            c.moveToFirst();
        return c;
    }

   public String getTitle(long id){
        Cursor c = mDB.query(MusicalDB.CreateDB._TABLENAME, new String[]{"title"},null,null,null,null,null);
        String title = c.getColumnName((int) id);
        return title;
   }
    public String getLocation(long id){
        Cursor c = mDB.query(MusicalDB.CreateDB._TABLENAME, new String[]{"location"},null,null,null,null,null);
        String location = c.getColumnName((int) id);
        return location;
    }
    public String getDate(long id){
        Cursor c = mDB.query(MusicalDB.CreateDB._TABLENAME, new String[]{"date"},null,null,null,null,null);
        String date = c.getColumnName((int) id);
        return date;
    }
    public String getActor(long id){
        Cursor c = mDB.query(MusicalDB.CreateDB._TABLENAME, new String[]{"actor"},null,null,null,null,null);
        String actor = c.getColumnName((int) id);
        return actor;
    }
    public String getPlot(long id){
        Cursor c = mDB.query(MusicalDB.CreateDB._TABLENAME, new String[]{"plot"},null,null,null,null,null);
        String plot = c.getColumnName((int) id);
        return plot;
    }
    public String getDetailInfo(long id){
        Cursor c = mDB.query(MusicalDB.CreateDB._TABLENAME, new String[]{"detailInfo"},null,null,null,null,null);
        String detailInfo = c.getColumnName((int) id);
        return detailInfo;
    }
    public String getBookingSite(long id){
        Cursor c = mDB.query(MusicalDB.CreateDB._TABLENAME, new String[]{"bookingSite"},null,null,null,null,null);
        String bookingSite = c.getColumnName((int) id);
        return bookingSite;
    }


}
