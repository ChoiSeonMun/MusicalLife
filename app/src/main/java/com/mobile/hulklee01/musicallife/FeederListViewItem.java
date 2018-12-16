package com.mobile.hulklee01.musicallife;

import android.graphics.drawable.Drawable;

public class FeederListViewItem {

    private Drawable musicalImage;
    private String musicalTitle;
    private String musicalPlace;
    private String musicalDate;

    public Drawable getMusicalImage() {
        return musicalImage;
    }

    public void setMusicalImage(Drawable musicalImage) {
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
