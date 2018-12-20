package com.mobile.hulklee01.musicallife;

public class MusicalDB {
    public static final String _TABLENAME = "musicalData";
    public static final class Columns {
        public static final String ID = "_id";
        public static final String IMAGE = "image";
        public static final String TITLE = "title";
        public static final String LOCATION = "location";
        public static final String DURATION = "duration";
        public static final String RATING = "rating";
        public static final String INFORMATION = "information";
        public static final String PLAYTIME = "playtime";
        public static final String BOOKINGSITE = "bookingsite";
        public static final String URL = "url";
        public static final String SUBSCRIBE = "subscribe";
    }

    public static class ColumnsClass {
        public String Image;
        public String Title;
        public String Location;
        public String Duration;
        public String Rating;
        public String Information;
        public int Playtime;
        public String BookingSite;
        public String Url;
        public int Subscribe;
    }

    public static final class SQL {
        public static final String CREATE = "create table " + _TABLENAME + " ("
                + Columns.ID + " integer primary key autoincrement, "
                + Columns.IMAGE + " text not null , "
                + Columns.TITLE + " text not null , "
                + Columns.LOCATION + " text not null , "
                + Columns.DURATION + " text not null , "
                + Columns.RATING + " text not null , "
                + Columns.INFORMATION + " text not null , "
                + Columns.PLAYTIME + " int not null , "
                + Columns.BOOKINGSITE + " text not null , "
                + Columns.URL + " text not null ,  "
                + Columns.SUBSCRIBE + " integer not null );";

    }
}
