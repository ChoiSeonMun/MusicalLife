package com.mobile.hulklee01.musicallife;

public interface ICrawlingCallback {
    void    onPageCrawlingCompleted();
    void    onPageCrawlingFailed(String url, int errorCode);
    void    onCrawlingCompleted();
}
