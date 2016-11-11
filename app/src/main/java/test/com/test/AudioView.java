package test.com.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

/**
 * 作者：chengxiangtong on 2016/11/10 14:44
 * 邮箱：528440900@qq.com
 * 仿音频条
 */

public class AudioView extends View {

    Random mttt = new Random();
    private Paint mPaint;
    private int width;
    private int height;
    private int mRectWidth = 10;
    private int mRectHeight = 60;
    private int offset = 3;

    public AudioView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public AudioView(Context context) {
        this(context, null);
    }

    public AudioView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    private void init() {
        mPaint = new Paint();

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        int left = getPaddingLeft();
        int top = getPaddingTop();
        int right = getPaddingRight();
        int bottom = getPaddingBottom();

        width = getMeasuredWidth() - left - right;
        height = getMeasuredHeight() - top - bottom;

        int mRectCount = 0;
        for (int count = 5; count < width; count += mRectWidth) {
            mRectCount++;
        }
        for (int i = 0; i < mRectCount; i++) {
            double mRandom = Math.random();
            int n = mttt.nextInt(4);
            if (n == 0) {
                mPaint.setColor(Color.BLUE);
            } else if (n == 1) {
                mPaint.setColor(Color.RED);
            } else if (n == 2) {
                mPaint.setColor(Color.LTGRAY);
            } else {
                mPaint.setColor(Color.GREEN);
            }
            mRectHeight = (int) (mRandom * height);
            canvas.drawRect(offset + mRectWidth * i, mRectHeight, mRectWidth * (i + 1), height, mPaint);
        }
        postInvalidateDelayed(500);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthsize = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(getMeasuredWidth() / 2, getMeasuredHeight() / 2);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(getMeasuredWidth() / 2, heightSize);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthsize, getMeasuredHeight() / 2);
        }

    }
}
