package test.com.test;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * 作者：chengxiangtong on 2016/11/10 17:15
 * 邮箱：528440900@qq.com
 * 复合视图--控件组合
 */

public class CompositeViews extends RelativeLayout {
    private TextView mTextViewleft;
    private TextView mTextViewcenter;
    private TextView mTextViewright;

    private LayoutParams mLayoutParamsleft;
    private LayoutParams mLayoutParamscenter;
    private LayoutParams mLayoutParamsright;

    private tabBarListener mTabBarListener;


    public CompositeViews(Context context) {
        this(context, null);
    }


    public CompositeViews(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CompositeViews(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);

    }

    private void init(Context context, AttributeSet attrs) {
        mTextViewleft = new TextView(context);
        mTextViewcenter = new TextView(context);
        mTextViewright = new TextView(context);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CompositeViews);

        //获取其他属性和背景颜色一样
        int colorleftBackgroundColor = typedArray.getColor(R.styleable.CompositeViews_leftTextBackground, 0);
        mLayoutParamsleft = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mLayoutParamsleft.addRule(RelativeLayout.ALIGN_PARENT_LEFT, TRUE);
        mTextViewleft.setText("左左左");
        mTextViewleft.setBackgroundColor(colorleftBackgroundColor);
        mTextViewleft.setTextSize(22);
        addView(this.mTextViewleft, mLayoutParamsleft);

        int colorcenterBackgroundColor = typedArray.getColor(R.styleable.CompositeViews_centerTextBackground, 0);
        mLayoutParamscenter = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mLayoutParamscenter.addRule(RelativeLayout.CENTER_HORIZONTAL, TRUE);
        mTextViewcenter.setText("标题栏");
        mTextViewcenter.setBackgroundColor(colorcenterBackgroundColor);
        mTextViewcenter.setTextSize(22);
        addView(mTextViewcenter, mLayoutParamscenter);

        int colorrightBackgroundColor = typedArray.getColor(R.styleable.CompositeViews_rightTextBackground, 0);
        mLayoutParamsright = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        mLayoutParamsright.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, TRUE);
        mTextViewright.setText("右右右");
        mTextViewright.setBackgroundColor(colorrightBackgroundColor);
        mTextViewright.setTextSize(22);
        addView(mTextViewright, mLayoutParamsright);

        setOnListener();
    }

    public void settabBarOnclcikLisenter(tabBarListener mtabBar) {
        mTabBarListener = mtabBar;
    }

    public void setOnListener() {
        mTextViewleft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mTabBarListener.mTextViewleftListener();
            }
        });
        mTextViewright.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mTabBarListener.mTextViewrightListener();
            }
        });
    }

    public interface tabBarListener {
        void mTextViewleftListener();

        void mTextViewrightListener();
    }

}
