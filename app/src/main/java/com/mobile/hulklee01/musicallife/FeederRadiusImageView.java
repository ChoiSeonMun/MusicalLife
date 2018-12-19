package com.mobile.hulklee01.musicallife;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

public class FeederRadiusImageView extends android.support.v7.widget.AppCompatImageView {
    // 라운드처리 강도 값을 크게하면 라운드 범위가 커짐
    public static float radius = 180.0f;

    public FeederRadiusImageView(Context context) {
        super(context);
    }

    public FeederRadiusImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FeederRadiusImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path clipPath = new Path();
        RectF rect = new RectF(5, 0, this.getWidth()-5, this.getHeight());
        clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }
}
