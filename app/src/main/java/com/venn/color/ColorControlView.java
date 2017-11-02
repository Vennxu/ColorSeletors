package com.venn.color;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author Venn
 * @Email 164942747@qq.com
 * */

public class ColorControlView extends View {

    private Paint mPaint;
    private Paint mControlPaint;
    private int mSpace;
    private int mSelectColor = 0;
    private RectF mRect = new RectF(0, 0, 0,dpTopx(24));
    private SingleTouchListener mListener;
    private int[] mColors = new int[]{0xFFFFFFFF, 0xFFC4FF2F, 0xFF00B200, 0xFF00F3D5,
            0xFF00B1F5, 0xFF0034C5, 0xFF9459FF, 0xFFFF9EFF,
            0xFFFF6C98, 0xFFE84057, 0xFFBA0100, 0xFFFF6E3F,
            0xFFFCFF2C, 0xFF999999};


    public ColorControlView(Context context) {
        this(context, null);
    }

    public ColorControlView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorControlView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mControlPaint = new Paint();
        mPaint = new Paint();

    }

    public void setSingeTouchListener(SingleTouchListener mListener) {
        this.mListener = mListener;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        mSpace = getWidth() / 14;
        mPaint.setStrokeWidth(dpTopx(9));
        for (int i = 0; i < mColors.length; i++) {
            mPaint.setColor(mColors[i]);
            canvas.drawLine(mSpace * i, dpTopx(12), mSpace * (i + 1), dpTopx(12), mPaint);
        }

        if (mSelectColor < mColors.length) {
            mRect.left = mSpace * mSelectColor;
            mRect.right = mSpace * (mSelectColor == 0 ? 1 : mSelectColor + 1);
            mControlPaint.setColor(mColors[mSelectColor]);
            canvas.drawRoundRect(mRect, 7, 7, mControlPaint);
            Log.d("selecter", mSelectColor + "");
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int x = 0;
        int y = 0;

        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                if (mListener != null && mSelectColor < mColors.length){
                    mListener.onChangeColor(mColors[mSelectColor]);
                }
                break;

            case MotionEvent.ACTION_DOWN:
                float location = event.getX() / mSpace;
                int selectColor = location < 1 ? 0 : (int) location;
                if (mSelectColor != selectColor) {
                    mSelectColor = selectColor;
                    postInvalidate();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                float moveLocation = event.getX() / mSpace;
                int selectMoveColor = moveLocation < 1 ? 0 : (int) moveLocation;
                if (selectMoveColor < mColors.length) {
                    mSelectColor = selectMoveColor;
                    postInvalidate();
                }
                break;

            default:
                break;
        }
        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public interface SingleTouchListener {
        public void onChangeColor(int color);
    }

    private int dpTopx(float dp){
        return DensityUtils.dp2px(getContext(), dp);
    }
}
