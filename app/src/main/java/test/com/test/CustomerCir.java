package test.com.test;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者：chengxiangtong on 2016/11/11 15:42
 * 邮箱：528440900@qq.com
 */

public class CustomerCir extends View {
    private Paint mPaint;


    public CustomerCir(Context context) {
        this(context, null);
    }

    public CustomerCir(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomerCir(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint =new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setStrokeWidth(25);
        mPaint.setStyle(Paint.Style.STROKE);
        //设置虚线效果
        mPaint.setPathEffect ( new DashPathEffect( new float [ ] { 100, 5 }, 0 ) ) ;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getMeasuredWidth()/2,getMeasuredHeight()/2,getMeasuredWidth()/4,mPaint);
    }
}
