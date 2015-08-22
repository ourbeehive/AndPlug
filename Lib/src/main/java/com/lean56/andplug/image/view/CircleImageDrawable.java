package com.lean56.andplug.image.view;

import android.graphics.*;
import android.graphics.Shader.TileMode;
import android.graphics.drawable.Drawable;

/**
 * CircleImageDrawable
 * ref {http://blog.csdn.net/lmj623565791/article/details/43752383}
 *
 * @author zhy
 * @author Charles
 */
public class CircleImageDrawable extends Drawable {

    private Paint mPaint;
    private int mWidth;

    public CircleImageDrawable(Bitmap bitmap) {
        BitmapShader bitmapShader = new BitmapShader(bitmap, TileMode.CLAMP, TileMode.CLAMP);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setShader(bitmapShader);
        mWidth = Math.min(bitmap.getWidth(), bitmap.getHeight());
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(mWidth / 2, mWidth / 2, mWidth / 2, mPaint);
    }

    @Override
    public int getIntrinsicWidth() {
        return mWidth;
    }

    @Override
    public int getIntrinsicHeight() {
        return mWidth;
    }

    @Override
    public void setAlpha(int alpha) {
        mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

}
