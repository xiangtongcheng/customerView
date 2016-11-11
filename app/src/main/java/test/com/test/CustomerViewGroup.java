package test.com.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 作者：chengxiangtong on 2016/11/10 14:00
 * 邮箱：528440900@qq.com
 */

public class CustomerViewGroup extends ViewGroup {

    int paddingleft = 0;
    int paddingtop = 0;
    int paddingright = 0;
    int paddingbottom = 0;


    public CustomerViewGroup(Context context) {
        this(context, null);
    }

    public CustomerViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomerViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    //ViewGroup它是一个容器，它是用来存放和管理子控件的，并且子控件的测量方式是根据它的测量模式来进行的，
    // 所以我们必须重写它的onMeasure()，在该方法中进行对子View的大小进行测量

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        paddingleft = getPaddingLeft();
        paddingtop = getPaddingTop();
        paddingright = getPaddingRight();
        paddingbottom = getPaddingBottom();


        int childcount = getChildCount();
        for (int i = 0; i < childcount; i++) {
            View view = getChildAt(i);
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
        }


        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int mHeight = 0;
        int mWidth = 0;
        int mMaxWidth = 0;
        if (widthMode == MeasureSpec.AT_MOST && heightMode == MeasureSpec.AT_MOST) {
            for (int i = 0; i < childcount; i++) {
                View view = getChildAt(i);
                mWidth += view.getMeasuredWidth();
                mHeight += view.getMeasuredHeight();
            }
            setMeasuredDimension(mWidth + paddingleft + paddingright, mHeight + paddingtop + paddingbottom);
        } else if (widthMode == MeasureSpec.AT_MOST) {
            for (int i = 0; i < childcount; i++) {
                View view = getChildAt(i);
                mMaxWidth = Math.max(mMaxWidth, view.getMeasuredWidth());
//                mHeight += view.getMeasuredHeight();
            }
            setMeasuredDimension(mMaxWidth+ paddingleft + paddingright, heightSize+ paddingtop + paddingbottom);
        } else if (heightMode == MeasureSpec.AT_MOST) {
            for (int i = 0; i < childcount; i++) {
                View view = getChildAt(i);
//                mWidth += view.getMeasuredWidth();
                mHeight += view.getMeasuredHeight();
            }
            setMeasuredDimension(widthSize+ paddingleft + paddingright, mHeight+ paddingtop + paddingbottom);
        }


    }


    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int childcount = getChildCount();

        int preheight = paddingtop;

        for (int k = 0; k < childcount; k++) {
            View view = getChildAt(k);
            int cheight = view.getMeasuredHeight();
            if (view.getVisibility() != View.GONE || view.getVisibility() != View.INVISIBLE) {
                view.layout(i + paddingleft, preheight, i2 + paddingright, preheight += cheight);
            }
        }
    }
}
