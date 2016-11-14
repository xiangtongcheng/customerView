package test.com.test;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import test.com.test.image.DownImage;
import test.com.test.image.ImageUtil;

public class BitmapActivity extends AppCompatActivity implements DownImage.getBitmap {

    private String url = "http://shopimg.weimob.com/100526549/Goods/1609121421489673.jpg";
    private String url1 = "http://shopimg.weimob.com/100526549/Goods/1609121421470313.jpg";
    private String text = "http://shopimg.weimob.com/100526549/Goods/1609121421489673.jpg";
    private Bitmap mBitmap1;
    private Bitmap mBitmap2;
    private Bitmap mBitmap;
    private Bitmap QR_CODE;
    private Bitmap mBitmapText;
    private ArrayList<String> data = new ArrayList<>();//文本数组
    private Bitmap logo;

    private ImageView iamge;
    //    Runnable runnableUI = new Runnable() {
//        public void run() {
//            // (2014.5.1第一种方法)通过服务器返回的图片url，再次向服务器请求，添加动态新闻图片
//            // 读取Bitmap图片
//            // 加载到布局文件中
//            mBitmap = ImageUtil.addBitmap(ImageUtil.returnBitmap(url), ImageUtil.returnBitmap(url1));
//            iamge.setImageBitmap(mBitmap);
//        }
//    };
    private Context mContext;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.activity_bitmap);
        iamge = (ImageView) findViewById(R.id.iamge);
//        new Thread(runnableUI).start();

        logo = BitmapFactory.decodeResource(super.getResources(), R.mipmap.ic_launcher);

        try {
            QR_CODE = ImageUtil.createCode(text, logo, BarcodeFormat.QR_CODE);
        } catch (WriterException e) {
            e.printStackTrace();
        }

//        data.add("android拼接多张bitmap图片android拼接多张bitmap图片android拼接多张bitmap图片");
//        mBitmapText = ImageUtil.writeText(data);
        mBitmapText = ImageUtil.writeText("android拼接多张bitmap图片android拼接多张bitmap图片android拼接多张bitmap图片");

        new DownImage(this, 1).execute(url);
//        new DownImage(this, 2).execute(url1);

        iamge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DownImage(BitmapActivity.this, 1).execute(url);
            }
        });

//        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
//        Uri uri = Uri.fromFile(file);
//        intent.setData(uri);
//        sendBroadcast(intent);

        /**
         * 读取图片的子线程post给主线程的runnable对象，内含各种更新UI的操作
         *
         * @author GloryZSG
         */


    }


    @Override
    public void getMBitmap(Bitmap tmBitmap, int flag) {
        switch (flag) {
            case 1:
                mBitmap1 = tmBitmap;
                mBitmap = ImageUtil.addBitmap(mBitmap1, QR_CODE, mBitmapText,logo);
                iamge.setImageBitmap(mBitmap);
                SavePicture(mBitmap);
                break;
            case 2:
                mBitmap2 = tmBitmap;
                iamge.setImageBitmap(ImageUtil.addBitmap(mBitmap1, mBitmap2));
                break;
        }
    }

    private void SavePicture(Bitmap tbitmap) {
        Log.d("debug", "SavePicture 1");
        Log.d("debug", "SavePicture 2");
        Bitmap obmp = tbitmap;
        Log.d("debug", "SavePicture 3");

        if (obmp != null) {
            // 图片存储路径
            String SavePath = getSDCardPath();
            // 保存Bitmap
            Log.d("debug", "SavePath = " + SavePath);
            try {
                File path = new File(SavePath);
                // 文件
                String filepath = SavePath + "/Screen_1.png";
                Log.d("debug", "filepath = " + filepath);
                File file = new File(filepath);
                if (!path.exists()) {
                    Log.d("debug", "path is not exists");
                    path.mkdirs();
                }
                if (!file.exists()) {
                    Log.d("debug", "file create new ");
                    file.createNewFile();
                }
                FileOutputStream fos = null;
                fos = new FileOutputStream(file);
                if (null != fos) {
                    obmp.compress(Bitmap.CompressFormat.PNG, 60, fos);
                    fos.flush();
                    fos.close();
                    Log.d("debug", "save ok");
                }
                Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                Uri uri = Uri.fromFile(file);
                intent.setData(uri);
                mContext.sendBroadcast(intent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 获取SDCard的目录路径功能
     *
     * @return
     */
    private String getSDCardPath() {
//        File sdcardDir = null;
//        // 判断SDCard是否存在
//        boolean sdcardExist = Environment.getExternalStorageDirectory().equals(
//                android.os.Environment.MEDIA_MOUNTED);
//        if (sdcardExist) {
//            sdcardDir = Environment.getExternalStorageDirectory();
//        }
        File outDir = null;
        String state = Environment.getExternalStorageState();
        if (state.equals(Environment.MEDIA_MOUNTED)) {
            // 这个路径，在手机内存下创建一个pictures的文件夹，把图片存在其中。
            outDir = Environment
                    .getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
        } else {
            if (mContext != null) {
                outDir = mContext.getFilesDir();
            }
        }
        if (!outDir.exists()) {
            outDir.mkdirs();
        }
//        return sdcardDir.toString();
        return outDir.toString();
    }
}


