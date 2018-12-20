package com.mobile.hulklee01.musicallife;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MusicalDBHelper {
    private static final String DATABASE_NAME = "Musical.db";
    private static final int DATABASE_VERSION = 1;
    private static SQLiteDatabase mDB;
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
            db.execSQL("DROP TABLE IF EXISTS "+ MusicalDB._TABLENAME);
            onCreate(db);
        }
    }

    public MusicalDBHelper(Context context){
        this.mCtx = context;
    }

    public MusicalDBHelper open() throws SQLException {
        mDBHelper = new DatabaseHelper(mCtx, DATABASE_NAME, null, DATABASE_VERSION);
        mDB = mDBHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        mDB.close();
    }

    public InsertHelper insert() {
        return new InsertHelper();
    }

    //커서 전체를 선택하는 메소드
    public Cursor getAllColumns() {
        return mDB.query(MusicalDB._TABLENAME, null, null, null, null, null, null);
    }

    //ID 컬럼 얻어오기
    public Cursor getId(long id) {
        Cursor c = mDB.query(MusicalDB._TABLENAME, null,
                "_id="+id, null, null, null, null);
        //받아온 컬럼이 null이 아니고 0번째가 아닐경우 제일 처음으로 보냄
        if (c != null && c.getCount() != 0)
            c.moveToFirst();
        return c;
    }

    public String getImageLink(long id) {
        Cursor c = mDB.query(MusicalDB._TABLENAME,
                new String[]{ MusicalDB.Columns.IMAGE },
                null,null,null,null,null);
        String image = c.getColumnName((int) id);
        return image;
    }

    public String getTitle(long id){
         Cursor c = mDB.query(MusicalDB._TABLENAME,
                 new String[]{ MusicalDB.Columns.TITLE },
                 null,null,null,null,null);
         String title = c.getColumnName((int) id);
         return title;
    }

    public String getLocation(long id){
        Cursor c = mDB.query(MusicalDB._TABLENAME,
                new String[]{ MusicalDB.Columns.LOCATION },
                null,null,null,null,null);
        String location = c.getColumnName((int) id);
        return location;
    }

    public String getDuration(long id){
        Cursor c = mDB.query(MusicalDB._TABLENAME,
                new String[]{ MusicalDB.Columns.DURATION },
                null,null,null,null,null);
        String duration = c.getColumnName((int) id);
        return duration;
    }

    public String getRating(long id){
        Cursor c = mDB.query(MusicalDB._TABLENAME,
                new String[]{ MusicalDB.Columns.RATING },
                null,null,null,null,null);
        String rating = c.getColumnName((int) id);
        return rating;
    }

    public String getInformation(long id){
        Cursor c = mDB.query(MusicalDB._TABLENAME,
                new String[]{ MusicalDB.Columns.INFORMATION },
                null,null,null,null,null);
        String information = c.getColumnName((int) id);
        return information;
    }

    public String getBookingSite(long id){
        Cursor c = mDB.query(MusicalDB._TABLENAME,
                new String[]{ MusicalDB.Columns.BOOKINGSITE },
                null,null,null,null,null);
        String bookingSite = c.getColumnName((int) id);
        return bookingSite;
    }

    public String getUrl(long id) {
        Cursor c = mDB.query(MusicalDB._TABLENAME,
                new String[]{ MusicalDB.Columns.URL},
                null,null,null,null,null);
        String url = c.getColumnName((int) id);
        return url;
    }

    public int getPlaytime(long id) {
         Cursor c = mDB.query(MusicalDB._TABLENAME,
                 new String[] { MusicalDB.Columns.PLAYTIME },
                 null, null, null, null, null);
         String playtime = c.getColumnName((int) id);
         return Integer.parseInt(playtime);
    }

    public int getSubs(long id){
        Cursor c = mDB.query(MusicalDB._TABLENAME,
                new String[] { MusicalDB.Columns.SUBSCRIBE },
                null, null, null, null, null);
        String subs = c.getColumnName((int) id);
        return Integer.parseInt(subs);
    }

    public int getNumberOfSubs() {
        Cursor c = mDB.query(MusicalDB._TABLENAME,
                new String[] { MusicalDB.Columns.SUBSCRIBE },
                "1", null, null, null, null);
        return c.getColumnCount();
    }

    public class InsertHelper {
        private MusicalDB.ColumnsClass col;

        public InsertHelper image(String img) {
            col.Image = img;
            return this;
        }

        public InsertHelper title(String tlt) {
            col.Title = tlt;
            return this;
        }

        public InsertHelper location(String lctn) {
            col.Location = lctn;
            return this;
        }


        public InsertHelper duration(String drtn) {
            col.Duration = drtn;
            return this;
        }

        public InsertHelper rating (String rtng) {
            col.Rating = rtng;
            return this;
        }

        public InsertHelper information(String info) {
            col.Information = info;
            return this;
        }

        public InsertHelper bookingsite(String bkst) {
            col.BookingSite = bkst;
            return this;
        }

        public InsertHelper playtime(int playtime) {
            col.Playtime = playtime;
            return this;
        }

        public InsertHelper subscribe(boolean sub) {
            int subs = (sub) ? 1 : 0;
            col.Subscribe = subs;
            return this;
        }

        public InsertHelper url(String url) {
            col.Url = url;
            return this;
        }

        public long done() {
            ContentValues values = new ContentValues();
            values.put(MusicalDB.Columns.IMAGE, col.Image);
            values.put(MusicalDB.Columns.TITLE, col.Title);
            values.put(MusicalDB.Columns.LOCATION, col.Location);
            values.put(MusicalDB.Columns.DURATION, col.Duration);
            values.put(MusicalDB.Columns.RATING, col.Rating);
            values.put(MusicalDB.Columns.INFORMATION, col.Information);
            values.put(MusicalDB.Columns.PLAYTIME, col.Playtime);
            values.put(MusicalDB.Columns.BOOKINGSITE, col.BookingSite);
            values.put(MusicalDB.Columns.URL, col.Url);
            values.put(MusicalDB.Columns.SUBSCRIBE, col.Subscribe);
            return mDB.insert(MusicalDB._TABLENAME, null, values);
        }
    }
}
