package test.com.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者：chengxiangtong on 2016/11/10 09:32
 * 邮箱：528440900@qq.com
 * 滚动的线条
 */

public class CustomerRunLine extends View {
    private int mColor = 0;
    private int h = 0;
    private int w = 0;
    private Paint mPaint;
    private int space = 30;
    private int startX = 0;
    private int delayed = 5;

    public CustomerRunLine(Context context) {
        this(context, null);
    }

    public CustomerRunLine(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomerRunLine(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.Customerview, 0, 0);
        h = mTypedArray.getDimensionPixelSize(R.styleable.Customerview_c_h, h);
        w = mTypedArray.getDimensionPixelSize(R.styleable.Customerview_c_w, w);
        mColor = mTypedArray.getColor(R.styleable.Customerview_c_color, mColor);
        mTypedArray.recycle();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mColor);
        mPaint.setStrokeWidth(w);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float sw = this.getMeasuredWidth();
        if (startX >= sw + (h + space) - (sw % (h + space))) {
            startX = 0;
        } else {
            startX += delayed;
        }
        float start = startX;
        while (start < sw) {
            canvas.drawLine(start, 5, start + h, 5, mPaint);
            start += (h + space);
        }
        start = startX - h - space;
        while (start >= -h) {
            canvas.drawLine(start, 5, start + h, 5, mPaint);
            start -= (h + space);
        }
        invalidate();
    }

}
