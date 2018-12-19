package com.mobile.hulklee01.musicallife;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.apache.http.HttpStatus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class WebCrawler {

    interface CrawlingCallback {
        void    onPageCrawlingCompleted();
        void    onPageCrawlingFailed(String url, int errorCode);
        void    onCrawlingCompleted();
    }

    private Context mContext;
    private CrawlerDB mCrawlerDB;
    private HashSet<String> mCrawledUrl;
    BlockingQueue<String> mUncrawledUrl;
    RunnableManager mManager;
    CrawlingCallback mCallback;
    Object lock;

    public WebCrawler(Context context, CrawlingCallback callback) {
        this.mContext = context;
        this.mCallback = callback;
        mCrawlerDB = new CrawlerDB(mContext);
        mCrawledUrl = new HashSet<>();
        mUncrawledUrl = new LinkedBlockingDeque<>();
        lock = new Object();
    }

    // Add crawler runnable in ThreadPoolExecutor workQueue
    public void startCrawlerTask(String Url, boolean isRootUrl) {
        // If it's root Url, we clear previous lists and DB table content
        if (isRootUrl) {
            mCrawledUrl.clear();
            mUncrawledUrl.clear();
            clearDB();
            mManager = new RunnableManager();
        }
        // If ThreadPoolExecuter is not shutting down, add runnable to workQueue
        if (!mManager.isShuttingDown()) {
            CrawlerRunnable mTask = new CrawlerRunnable(mCallback, Url);
            mManager.addToCrawlingQueue(mTask);
        }
    }

    // Shutdown ThreadPoolExecutor
    public void stopCrawlerTasks() {
        mManager.cancelAllRunnable();
    }

    // Clear previous content of crawler DB table
    public void clearDB() {
        try {
            SQLiteDatabase db = mCrawlerDB.getWritableDatabase();
            db.delete(mCrawlerDB.TABLE_NAME, null, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Insert crawled url info in database
    public void insertIntoCrawlerDB(String mUrl, String result) {

        if (TextUtils.isEmpty(result))
            return;

        SQLiteDatabase db = mCrawlerDB.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CrawlerDB.COLUMNS_NAME.CRAWLED_URL, mUrl);
        values.put(CrawlerDB.COLUMNS_NAME.CRAWLED_PAGE_CONTENT, result);

        db.insert(CrawlerDB.TABLE_NAME, null, values);
    }

    private class CrawlerRunnable implements Runnable {

        CrawlingCallback mCallback;
        String mUrl;

        public CrawlerRunnable(CrawlingCallback callback, String Url) {
            this.mCallback = callback;
            this.mUrl = Url;
        }

        @Override
        public void run() {
            String pageContent = retreiveHtmlContent(mUrl);

            if (!TextUtils.isEmpty(pageContent.toString())) {
                insertIntoCrawlerDB(mUrl, pageContent);
                synchronized (lock) {
                    mCrawledUrl.add(mUrl);
                }
                mCallback.onPageCrawlingCompleted();
            } else {
                mCallback.onPageCrawlingFailed(mUrl, -1);
            }

            if (!TextUtils.isEmpty(pageContent.toString())) {
                // START
                // JSoup Library used to filter urls from html body
                Document doc = Jsoup.parse(pageContent.toString());
                Elements links = doc.select("a[href]");
                for (Element link : links) {
                    String extractedLink = link.attr("href");
                    if (!TextUtils.isEmpty(extractedLink)) {
                        synchronized (lock) {
                            if (!mCrawledUrl.contains(extractedLink)) {
                                mUncrawledUrl.add(extractedLink);
                            }
                        }

                    }
                }
                // End JSoup
            }
            // Send msg to handler that crawling for this url is finished
            // start more crawling tasks if queue is not empty
            mHandler.sendEmptyMessage(0);

        }

        private String retreiveHtmlContent(String Url) {
            URL httpUrl = null;
            try {
                httpUrl = new URL(Url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            int responseCode = HttpStatus.SC_OK;
            StringBuilder pageContent = new StringBuilder();
            try {
                if (httpUrl != null) {
                    HttpURLConnection conn = (HttpURLConnection) httpUrl
                            .openConnection();
                    conn.setConnectTimeout(5000);
                    conn.setReadTimeout(5000);
                    responseCode = conn.getResponseCode();
                    if (responseCode != HttpStatus.SC_OK) {
                        throw new IllegalAccessException(
                                " http connection failed");
                    }
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(conn.getInputStream()));
                    String line = null;
                    while ((line = br.readLine()) != null) {
                        pageContent.append(line);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
                mCallback.onPageCrawlingFailed(Url, -1);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                mCallback.onPageCrawlingFailed(Url, responseCode);
            }

            return pageContent.toString();
        }

    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        public void handleMessage(android.os.Message msg) {
            synchronized (lock) {
                if (mUncrawledUrl != null && mUncrawledUrl.size() > 0) {
                    int availableTasks = mManager.getUnusedPoolSize();
                    while (availableTasks > 0 && !mUncrawledUrl.isEmpty()) {
                        startCrawlerTask(mUncrawledUrl.remove(), false);
                        availableTasks--;
                    }
                }
            }
        };
    };

    private class RunnableManager {
        // Sets the amount of time an idle thread will wait for a task before
        // terminating
        private static final int KEEP_ALIVE_TIME = 1;

        // Sets the Time Unit to seconds
        private final TimeUnit KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;

        // Sets the initial threadpool size to 5
        private static final int CORE_POOL_SIZE = 5;

        // Sets the maximum threadpool size to 8
        private static final int MAXIMUM_POOL_SIZE = 8;

        // A queue of Runnables for crawling url
        private final BlockingQueue<Runnable> mCrawlingQueue;

        // A managed pool of background crawling threads
        private final ThreadPoolExecutor mCrawlingThreadPool;

        public RunnableManager() {
            mCrawlingQueue = new LinkedBlockingQueue<>();
            mCrawlingThreadPool = new ThreadPoolExecutor(CORE_POOL_SIZE,
                    MAXIMUM_POOL_SIZE, KEEP_ALIVE_TIME, KEEP_ALIVE_TIME_UNIT,
                    mCrawlingQueue);
        }

        private void addToCrawlingQueue(Runnable runnable) {
            mCrawlingThreadPool.execute(runnable);
        }

        private void cancelAllRunnable() {
            mCrawlingThreadPool.shutdownNow();
        }

        private int getUnusedPoolSize() {
            return MAXIMUM_POOL_SIZE - mCrawlingThreadPool.getActiveCount();
        }

        private boolean isShuttingDown() {
            return mCrawlingThreadPool.isShutdown()|| mCrawlingThreadPool.isTerminating();
        }
    }
}
