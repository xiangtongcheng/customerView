package test.com.test.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.InputStream;
import java.net.URL;

/**
 * 作者：chengxiangtong on 2016/11/14 14:12
 * 邮箱：528440900@qq.com
 */

public class DownImage extends AsyncTask<String, Void, Bitmap> {


    private getBitmap mGetBitmap;
    private int flag;


    public DownImage(getBitmap mGetBitmap, int flag) {
        this.mGetBitmap = mGetBitmap;
        this.flag = flag;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected void onPostExecute(Bitmap s) {
        super.onPostExecute(s);
        mGetBitmap.getMBitmap(s, flag);
    }

    @Override
    protected Bitmap doInBackground(String... strings) {
        String url = strings[0];
        Bitmap bitmap = null;
        try {
            //加载一个网络图片
            InputStream is = new URL(url).openStream();
            bitmap = BitmapFactory.decodeStream(is);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }

    public interface getBitmap {
        void getMBitmap(Bitmap mBitmap, int flag);
    }
}

