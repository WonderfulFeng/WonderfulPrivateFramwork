package com.wonderful.framework.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wonderful on 2017/6/20.
 * 基本理论：
 * 图片所占内存大小：图片长 x 图片宽 x 一个像素点占用的字节数组
 * 图片压缩格式：
 * Bitmap.Config.ARGB_8888  一个像素4个字节
 * Bitmap.Config.ARGB_565 一个像素2个字节
 **/

public class WonderfulBitmapUtils {
    /**
     * 计算图片文件所占内存大小 单位Kb
     */
    public static int getBitmapMemory(Bitmap bitmap) {
        return bitmap.getByteCount() / 1024;
    }

    /**
     * 质量压缩：
     * 图片的大小是不会改变的，因为质量压缩不会减少图片的像素，它是在保持像素的前提下改变图片的位深及透明度等，来达到压缩图片的目的。
     * 那么，图片的长，宽，像素都不变，那么bitmap所占内存大小是不会变的。他适合图片做网络传输支用。
     * <p>
     * 这里要说，如果是bit.compress(CompressFormat.PNG, quality, baos);这样的png格式，quality就没有作用了，
     * bytes.length不会变化，因为png图片是无损的，不能进行压缩。
     *
     * @param bitmap  位图
     * @param quality 压缩质量 0 - 100
     * @return Map 0 位置是网络传输的字节长度  1 位置是质量压缩后的Bitmap对象
     */
    public static Map<Integer, Object> getQualityBitmap(Bitmap bitmap, int quality) {
        Map<Integer, Object> map = new HashMap<>();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.WEBP, quality, baos);
        byte[] bytes = baos.toByteArray();
        int length = bytes.length;
        bitmap = BitmapFactory.decodeByteArray(bytes, 0, length);
        WonderfulStreamCloseUtils.closeStream(baos);
        map.put(0, length);
        map.put(1, bitmap);
        return map;
    }

    /**
     * 采样率压缩：
     * 按照指定 宽高压缩图片 保留图片比例的压缩图片
     * 默认 780 * 460
     *
     * @param is
     * @param width
     * @param height
     * @return
     * @throws IOException
     */
    public static Bitmap loadBitmap(InputStream is, int width, int height, int maxTarget) throws IOException {
        if (maxTarget == 0) {
            maxTarget = 780;
        }
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 5];
        int length = 0;
        while ((length = is.read(buffer)) != -1) {
            os.write(buffer, 0, length);
            os.flush();
        }
        byte[] bytes = os.toByteArray();
        is.close();
        os.close();
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
        double rate;
        // 780 460
        if (Math.max(width, height) > maxTarget) {
            if (width > height) {
                rate = width * 1.0 / maxTarget;
                width = maxTarget;
                height = (int) (height / rate);
            } else {
                rate = height * 1.0 / maxTarget;
                height = maxTarget;
                width = (int) (width / rate);
            }
        }
        int w = opts.outWidth / width;
        int h = opts.outHeight / height;
        int scale = w > h ? w : h;
        if (scale < 1) {
            scale = 1;
        }
        opts.inJustDecodeBounds = false;
        opts.inSampleSize = scale;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
    }

    /**
     * 采样率压缩：
     * 按照指定 宽高压缩图片
     * 默认 780 * 460
     *
     * @param is
     * @param width
     * @param height
     * @return
     * @throws IOException
     */
    public static Bitmap loadBitmap(InputStream is, int width, int height) throws IOException {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 5];
        int length = 0;
        while ((length = is.read(buffer)) != -1) {
            os.write(buffer, 0, length);
            os.flush();
        }
        byte[] bytes = os.toByteArray();
        is.close();
        os.close();
        BitmapFactory.Options opts = new BitmapFactory.Options();
        opts.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
        int w = opts.outWidth / width;
        int h = opts.outHeight / height;
        int scale = w > h ? w : h;
        if (scale < 1) {
            scale = 1;
        }
        opts.inJustDecodeBounds = false;
        opts.inSampleSize = scale;
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, opts);
    }

    /**
     * RGB_565压缩：
     * 一个像素占用两个字节 比ARGB_8888 节省一半内存
     */
    public static Bitmap getRGB_565Bitmap(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.WEBP, 100, baos);
        byte[] bytes = baos.toByteArray();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
        WonderfulStreamCloseUtils.closeStream(baos);
        return bitmap;
    }

    /**
     * 缩放发压缩  按照图片原始比例
     *
     * @param bitmap 对象
     * @param w      要缩放的宽度
     * @param h      要缩放的高度
     * @return newBmp 新 Bitmap对象
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h, int maxTarget) {
        if (maxTarget == 0) maxTarget = 780;
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        double rate;
        if (Math.max(width, height) > maxTarget) {
            if (w > h) {
                rate = w * 1.0 / maxTarget;
                w = maxTarget;
                h = (int) (h / rate);
            } else {
                rate = h * 1.0 / maxTarget;
                h = maxTarget;
                w = (int) (w / rate);
            }
        }
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);

        matrix.postScale(scaleWidth, scaleHeight);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return bitmap;
    }

    /**
     * 缩放发压缩  按照指定比例
     *
     * @param bitmap 对象
     * @param w      要缩放的宽度
     * @param h      要缩放的高度
     * @return newBmp 新 Bitmap对象
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return bitmap;
    }

    /**
     * 将指定bitmap对象存到文件中
     *
     * @param quality 质量压缩比率 取值 0--100 有些无损格式如png将忽略此设置不被压缩
     */
    public static void saveBitmapToFile(Bitmap bitmap, File file, int quality) throws IOException {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        if (file.exists()) {
            file.delete();
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        bitmap.compress(Bitmap.CompressFormat.WEBP, quality, fos);
        WonderfulStreamCloseUtils.closeStream(fos);
    }

    /**
     * 根据指定的图片在图片中间添加多行文字
     */
    public static Bitmap addTextToBitmap(Context context, Bitmap bm, int textSize, String textColor, int dpPadding, int offsetY, String... texts) {
        Bitmap bitmap = bm.copy(Bitmap.Config.ARGB_8888, true);
        Canvas canvas = new Canvas(bitmap);//创建一个空画布，并给画布设置位图
        Paint p = new Paint();
        p.setColor(Color.parseColor(textColor));
        p.setAntiAlias(true);
        p.setTextSize(textSize);
        p.setTextAlign(Paint.Align.CENTER);
        int len = texts.length;
        float y;
//        if (len % 2 == 0) {
        for (int i = 0; i < len; i++) {
            if (i <= (len / 2)) {
                y = bitmap.getHeight() / 2.0f - (((len - 2 * i - 2) / 2.0f) * textSize + ((len - 2 * i - 1) / 2.0f) * WonderfulDpPxUtils.dip2px(context, dpPadding)) - offsetY;
//                } else {
//                    y = bitmap.getHeight() / 2.0f + (((2 * i - len + 2) / 2.0f) * textSize + ((2 * i + 1 - len) / 2.0f) * BitmapUtils.dip2px(this, dpPadding));
//                }
                canvas.drawText(texts[i], bitmap.getWidth() / 2, y, p);
            }
//        } else {
//            for (int i = 0; i < len; i++) {
//                if (i <= (len / 2 + 1)) {
//                    y = bitmap.getHeight() / -((len - 2 * i - 2) / 2.0f * textSize + (len - 1 - 2 * i) / 2.0f * BitmapUtils.dip2px(this, dpPadding));
//                } else {
//                    y = bitmap.getHeight() / 2.0f + (((2 * i - len + 2) / 2.0f) * textSize + ((2 * i + 1 - len) / 2.0f) * BitmapUtils.dip2px(this, dpPadding));
//                }
//                canvas.drawText(texts[i], bitmap.getWidth() / 2, y, p);
//            }
        }
        return bitmap;
    }

    /**
     * base64格式转化成Bitmap
     *
     * @param imgStr
     * @return
     */
    public static Bitmap base64ToBitmap(String imgStr) {
        if (imgStr == null) { //图像数据为空
            return null;
        }
        byte[] bytes = Base64.decode(imgStr, Base64.DEFAULT);
        for (int i = 0; i < bytes.length; ++i) {
            if (bytes[i] < 0) {//调整异常数据
                bytes[i] += 256;
            }
        }
        // 生成jpeg图片
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return bitmap;
    }

    /**
     * 转换为base64格式
     */
    public static String imgToBase64(Bitmap bitmap) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
        WonderfulStreamCloseUtils.closeStream(out);
        byte[] imgBytes = out.toByteArray();
        return Base64.encodeToString(imgBytes, Base64.DEFAULT);//Android 类库
//        return BASE64Encoder.encode(imgBytes);// 解决换行问题
    }

    /**
     * 旋转图片
     *
     * @param origin
     * @param alpha
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap origin, float alpha) {
        if (origin == null) return null;
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.setRotate(alpha);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) return newBM;
        origin.recycle();
        return newBM;
    }


}
