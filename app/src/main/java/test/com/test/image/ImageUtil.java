package test.com.test.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import static android.R.attr.path;

/**
 * 作者：chengxiangtong on 2016/11/14 10:09
 * 邮箱：528440900@qq.com
 */

public class ImageUtil {
    private static final int IMAGE_HALFWIDTH = 40;//宽度值，影响中间图片大小

    //将文字转换为图片
    public static void writeImage(String path, ArrayList<String> data) throws FileNotFoundException {
        int textHeight = data.size() * 20;//图片的高度
        int textWidth = 480;//图片的宽度
        Bitmap bitmap = Bitmap.createBitmap(textWidth, textHeight, Bitmap.Config.RGB_565);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);//背景颜色

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);//画笔的颜色
        paint.setTextSize(15);//画笔的粗细
        for (int i = 0; i < data.size(); i++) {
            canvas.drawText(data.get(i), 20, (i + 1) * 20, paint);
        }
        //将bitmap保存为png图片
        FileOutputStream fileOutputStream = new FileOutputStream(path);
        bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);

    }

    //将文字转换为图片
    public static Bitmap writeText(ArrayList<String> data) {
        int textWidth = 370;//图片的宽度
        int textHeight = data.size() * 30;//图片的高度
        int lines = textHeight / 20;//每一行字数
        Bitmap bitmap = Bitmap.createBitmap(textWidth, textHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.WHITE);//背景颜色

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);//画笔的颜色
        paint.setTextSize(20);//画笔的粗细
        for (int i = 0; i < data.size(); i++) {
            for (int j = 0; j * 20 < data.size(); j++) {
                canvas.drawText(data.get(i), (j + 1) * 30, (i / 20 + 1) * 30, paint);
            }
        }
        //将bitmap保存为png图片
//        FileOutputStream fileOutputStream = new FileOutputStream(path);
//        bitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
        return bitmap;

    }

    public static Bitmap writeText(String text) {
//        int textHeight = data.size() * 30;//图片的高度
        int textWidth = 480;//图片的宽度
        Bitmap newBitmap = Bitmap.createBitmap(365, 365, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(22.0F);
        StaticLayout sl = new StaticLayout(text, textPaint, newBitmap.getWidth(), Layout.Alignment.ALIGN_NORMAL, 1.5f, 0.0f, false);
        canvas.translate(6, 40);
        sl.draw(canvas);
        return newBitmap;
    }

    //合并两张图片
    public static void mergeBitmap(Bitmap first, Bitmap second) {
        Bitmap bitmap3 = Bitmap.createBitmap(first.getWidth(), first.getHeight(), first.getConfig());
        Canvas canvas = new Canvas(bitmap3);
        canvas.drawBitmap(first, new Matrix(), null);
        canvas.drawBitmap(second, 120, 350, null);  //120、350为bitmap2写入点的x、y坐标
        //将合并后的bitmap3保存为png图片到本地
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(path + File.separator + "image3.png");
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bitmap3.compress(Bitmap.CompressFormat.PNG, 90, out);
    }

    //压缩图片并将Bitmap保存到本地
    public static void saveFilePath(String filePath) {
        BitmapFactory.Options options1 = new BitmapFactory.Options();
        options1.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options1);
        options1.inSampleSize = calculateInSampleSize(options1, 110, 160);  //110,160：转换后的宽和高，具体值会有些出入
        options1.inJustDecodeBounds = false;
        Bitmap saveBitmap = BitmapFactory.decodeFile(filePath, options1);       //filePath:文件路径

        //压缩图片并将Bitmap保存到本地
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        saveBitmap.compress(Bitmap.CompressFormat.JPEG, 60, out);   //60代表压缩40%
    }

    //压缩图片
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            inSampleSize = heightRatio < widthRatio ? widthRatio : heightRatio;
        }
        return inSampleSize;
    }

    /**
     * 根据图片的url路径获得Bitmap对象
     *
     * @param url
     * @return
     */
    public static Bitmap returnBitmap(String url) {
        URL fileUrl = null;
        Bitmap bitmap = null;

        try {
            fileUrl = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            HttpURLConnection conn = (HttpURLConnection) fileUrl
                    .openConnection();
            conn.setDoInput(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();

            Log.i("returnBitmap", "returnBitmap+");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return bitmap;

    }

    /**
     * 纵向拼接
     * <功能详细描述>
     *
     * @param first
     * @param second
     * @return
     */
    public static Bitmap addBitmap(Bitmap first, Bitmap second) {
        int width = Math.max(first.getWidth(), second.getWidth());
        int height = first.getHeight() + second.getHeight();
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(first, 0, 0, null);
        canvas.drawBitmap(second, 0, first.getHeight(), null);
        return result;
    }

    /**
     * 纵横向拼接
     * <功能详细描述>
     *
     * @param first
     * @param second
     * @return
     */
    public static Bitmap addBitmap(Bitmap first, Bitmap second, Bitmap third, Bitmap logo) {
        int width = Math.max(first.getWidth(), second.getWidth());
        int height = first.getHeight() + second.getHeight();
        Random random = new Random();
        int logoX = random.nextInt(first.getWidth() - 100);
        int logoY = random.nextInt(first.getHeight() - 100);

        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(first, 0, 0, null);
        canvas.drawBitmap(logo, logoX, logoY, null);
        canvas.drawBitmap(second, 0, first.getHeight(), null);
        canvas.drawBitmap(third, second.getWidth(), first.getHeight(), null);
        canvas.save(Canvas.ALL_SAVE_FLAG);
        // 存储新合成的图片
        canvas.restore();
        return result;
    }

    /**
     * 横向拼接
     * <功能详细描述>
     *
     * @param first
     * @param second
     * @return
     */
    public static Bitmap add2Bitmap(Bitmap first, Bitmap second) {
        int width = first.getWidth() + second.getWidth();
        int height = Math.max(first.getHeight(), second.getHeight());
        Bitmap result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(first, 0, 0, null);
        canvas.drawBitmap(second, first.getWidth(), 0, null);
        return result;
    }

    /**
     * 生成二维码
     *
     * @param string  二维码中包含的文本信息
     * @param mBitmap logo图片
     * @param format  编码格式
     * @return Bitmap 位图
     * @throws WriterException
     */
    public static Bitmap createCode(String string, Bitmap mBitmap, BarcodeFormat format)
            throws WriterException {
//        Matrix m = new Matrix();
//        float sx = (float) 2 * IMAGE_HALFWIDTH / mBitmap.getWidth();
//        float sy = (float) 2 * IMAGE_HALFWIDTH
//                / mBitmap.getHeight();
//        m.setScale(sx, sy);//设置缩放信息
        //将logo图片按martix设置的信息缩放
//        mBitmap = Bitmap.createBitmap(mBitmap, 0, 0,
//                mBitmap.getWidth(), mBitmap.getHeight(), m, false);
        MultiFormatWriter writer = new MultiFormatWriter();
        Hashtable<EncodeHintType, String> hst = new Hashtable<EncodeHintType, String>();
        hst.put(EncodeHintType.CHARACTER_SET, "UTF-8");//设置字符编码
        BitMatrix matrix = writer.encode(string, format, 400, 400, hst);//生成二维码矩阵信息
        int width = matrix.getWidth();//矩阵高度
        int height = matrix.getHeight();//矩阵宽度
        int halfW = width / 2;
        int halfH = height / 2;
        int[] pixels = new int[width * height];//定义数组长度为矩阵高度*矩阵宽度，用于记录矩阵中像素信息
        for (int y = 0; y < height; y++) {//从行开始迭代矩阵
            for (int x = 0; x < width; x++) {//迭代列
//                if (x > halfW - IMAGE_HALFWIDTH && x < halfW + IMAGE_HALFWIDTH
//                        && y > halfH - IMAGE_HALFWIDTH
//                        && y < halfH + IMAGE_HALFWIDTH) {//该位置用于存放图片信息
//                    //记录图片每个像素信息
////                    pixels[y * width + x] = mBitmap.getPixel(x - halfW
////                            + IMAGE_HALFWIDTH, y - halfH + IMAGE_HALFWIDTH);
//                } else {
                if (matrix.get(x, y)) {//如果有黑块点，记录信息
                    pixels[y * width + x] = 0xff000000;//记录黑块信息
                }
//                }

            }
        }
        Bitmap bitmap = Bitmap.createBitmap(width, height,
                Bitmap.Config.ARGB_8888);
        // 通过像素数组生成bitmap
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }


}
