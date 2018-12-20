package com.mobile.hulklee01.musicallife;

import java.time.LocalDate;
import java.util.Scanner;

public class CrawledContent {
    private final String mUrl;
    private final String mImg;
    private final String mTitle;
    private final String mDuration;
    private final String mLocation;
    private final String mActors;
    private final int mPlayTime;
    private final String mPlot;
    private final String mBookingSite;

    private CrawledContent(String url, String img, String title, String duration, String location, String actors, int playtime, String plot, String bookingSite) {
        this.mUrl = url;
        this.mImg = img;
        this.mTitle = title;
        this.mDuration = duration;
        this.mLocation = location;
        this.mActors = actors;
        this.mPlayTime = playtime;
        this.mPlot = plot;
        this.mBookingSite = bookingSite;
    }

    public static class Builder {
        private String mUrl = "";
        private String mImg = null;
        private String mTitle = "";
        private String mDuration = "";
        private String mLocation = "";
        private String mActors = "";
        private int mPlayTime = 0;
        private String mInformation = "";
        private String mBookingSite = "";

        public Builder url(String u) {
            this.mUrl = u;
            return this;
        }
        public Builder img(String i) {
            this.mImg = i;
            return this;
        }

        public Builder title(String t) {
            this.mTitle = t;
            return this;
        }

        public Builder duration(String d) {
            this.mDuration = d;
            return this;
        }

        public Builder location(String l) {
            this.mLocation = l;
            return this;
        }

        public Builder actors(String a) {
            this.mActors = a;
            return this;
        }

        public Builder playtime(int p) {
            this.mPlayTime = p;
            return this;
        }

        public Builder playtime(String p) {
            Scanner s = new Scanner(p);
            int i = s.nextInt();
            this.mPlayTime = i;
            s.close();
            return this;
        }

        public Builder information(String p) {
            this.mInformation = p;
            return this;
        }

        public Builder bookingSite(String b) {
            this.mBookingSite = b;
            return this;
        }

        public CrawledContent build() {
            return new CrawledContent(mUrl, mImg, mTitle, mDuration, mLocation, mActors, mPlayTime, mInformation, mBookingSite);
        }
    }
}
