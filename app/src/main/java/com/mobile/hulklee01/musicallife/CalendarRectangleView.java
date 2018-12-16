package com.mobile.hulklee01.musicallife;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

public class CalendarRectangleView extends View {
    Paint mPaint;

    public CalendarRectangleView(Context context) {
        super(context);
        mPaint = new Paint();
    }

    protected void onDraw(Canvas canvas) {
        // mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(3);
        canvas.drawRect(10, 10, 70, 25, mPaint);
    }
}

