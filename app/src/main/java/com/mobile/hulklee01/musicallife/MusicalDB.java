package com.mobile.hulklee01.musicallife;

import android.graphics.drawable.Icon;
import android.media.Image;
import android.provider.BaseColumns;

public class MusicalDB {

    public static final class CreateDB implements BaseColumns {
        public static final String IMAGE = "image";
        public static final String TITLE = "title";
        public static final String LOCATION = "location";
        public static final String DATE = "date";
        public static final String ACTOR = "actor";
        public static final String PLOT = "plot";
        public static final String DETAILINFO = "detailInfo";
        public static final String BOOKINGSITE = "bookingSite";
        public static final String SUBSCRIBE = "subscribe";
        public static final String _TABLENAME = "musicalData";
        public static final String _CREATE = "create table " + _TABLENAME + "("
                + _ID + " integer not null primary key autoincrement, "
                + IMAGE + " BLOB not null , "
                + TITLE + " text not null , "
                + LOCATION + " text not null , "
                + DATE + " text not null , "
                + ACTOR + " text not null , "
                + PLOT + " text not null , "
                + DETAILINFO + " text not null , "
                + BOOKINGSITE + " text not null , "
                + SUBSCRIBE + " integer not null );";
    }
}
