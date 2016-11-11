package test.com.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements CompositeViews.tabBarListener {

    private CompositeViews mCompositeViews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout);
        mCompositeViews = (CompositeViews) findViewById(R.id.CompositeViews);
        mCompositeViews.settabBarOnclcikLisenter(this);

    }

    @Override
    public void mTextViewleftListener() {
        Toast.makeText(this,"mTextViewleftListener",Toast.LENGTH_LONG).show();
    }

    @Override
    public void mTextViewrightListener() {
        Toast.makeText(this,"mTextViewrightListener",Toast.LENGTH_LONG).show();

    }
}
