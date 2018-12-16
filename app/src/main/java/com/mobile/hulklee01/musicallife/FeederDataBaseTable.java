package com.mobile.hulklee01.musicallife;

import android.provider.BaseColumns;

public class FeederDataBaseTable {

    public static final class CreateDB implements BaseColumns {
        public static final String TITLE = "title";
        public static final String LOCATION = "location";
        public static final String DATE = "date";
        public static final String _TABLENAME = "feeder";
        public static final String _CREATE = "create table " + _TABLENAME + "("
                + _ID + " integer primary key autoincrement, "
                + TITLE + " text not null , "
                + LOCATION + " text not null , "
                + DATE + " text not null );";
    }

}
