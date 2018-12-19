package com.mobile.hulklee01.musicallife;

import android.graphics.drawable.Drawable;

public class FeederListViewItem {

    private byte[] musicalImage;
    private String musicalTitle;
    private String musicalPlace;
    private String musicalDate;

    public FeederListViewItem(){};

    public FeederListViewItem(byte[] img, String name, String location, String date){
        this.musicalImage = img;
        this.musicalTitle = name;
        this.musicalPlace = location;
        this.musicalDate = date;
    }

    public byte[] getMusicalImage() {
        return musicalImage;
    }

    public void setMusicalImage(byte[] musicalImage) {
        this.musicalImage = musicalImage;
    }

    public String getMusicalTitle() {
        return musicalTitle;
    }

    public void setMusicalTitle(String musicalTitle) {
        this.musicalTitle = musicalTitle;
    }

    public String getMusicalPlace() {
        return musicalPlace;
    }

    public void setMusicalPlace(String musicalPlace) {
        this.musicalPlace = musicalPlace;
    }

    public String getMusicalDate() {
        return musicalDate;
    }

    public void setMusicalDate(String musicalDate) {
        this.musicalDate = musicalDate;
    }

}
