package com.wonderful.framework.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Created by wonderful on 2017/7/17.
 */

public class WonderfulFileUtils {
    /**
     * 判断是否存在SDK卡
     */
    public static boolean hasSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) || !Environment.isExternalStorageRemovable();
    }

    /**
     * 获取APP专属文件(长时间保存)  这类文件是随着app删除而一起删除的
     * 它们可以被存储在两个地方：internal storage 和 external storage 。
     * internal storage就是手机自带的一块存储区域，通常很小；shared preference文件，数据库文件，都存储在这里
     * external storage就是通常所说的SD卡，通常很大，有16GB,32GB等。
     * <p>
     * 对应目录：代码中的注释
     */
    public static File getLongSaveFile(Context context, String dirname, String filename) {
        File file;
        if (hasSDCard()) {
            file = new File(context.getExternalFilesDir(dirname), filename);//  mnt/sdcard/Android/data/< package name >/files/…
        } else {
            file = new File(context.getFilesDir(), filename);//  data/data/< package name >/files/...
        }
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            WonderfulLogUtils.loge("新建文件", "新建文件时出现IO异常");
        }
        return null;
    }

    public static File getLongSaveDir(Context context, String dirname) throws IOException {
        File file;
        if (hasSDCard()) {
            file = context.getExternalFilesDir(dirname);//  mnt/sdcard/Android/data/< package name >/files/…
        } else {
            file = context.getFilesDir();//  data/data/< package name >/files/...
        }
        return file;
    }

    /**
     * 获取APP专属文件(临时缓存)  这类文件也是随着app删除而一起删除的
     * 它们可以被存储在两个地方：internal storage 和 external storage 。
     * internal storage 同上
     * external storage  同上
     * <p>
     * 对应目录：代码中的注释
     */
    public static File getCacheSaveFile(Context context, String filename) throws IOException {
        File file;
        if (hasSDCard()) {
            file = new File(context.getExternalCacheDir(), filename);//  mnt/sdcard/Android/data/< package name >/cache/…
        } else {
            file = new File(context.getCacheDir(), filename);//  data/data/< package name >/cache/...
        }
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();
        return file;
    }

    public static File getCacheSaveDir(Context context) throws IOException {
        File file;
        if (hasSDCard()) {
            file = context.getExternalCacheDir();//  mnt/sdcard/Android/data/< package name >/cache/…
        } else {
            file = context.getCacheDir();//  data/data/< package name >/cache/...
        }
        return file;
    }

    /**
     * 文件拷贝
     *
     * @param f1
     * @param f2
     * @return
     * @throws Exception
     */
    public static File forChannel(File f1, File f2) throws Exception {
        int length = 1024 * 1024 * 2;
        FileInputStream in = new FileInputStream(f1);
        FileOutputStream out = new FileOutputStream(f2);
        FileChannel inC = in.getChannel();
        FileChannel outC = out.getChannel();
        ByteBuffer b = null;
        while (true) {
            if (inC.position() == inC.size()) {
                inC.close();
                outC.close();
                return f2;
            }
            if ((inC.size() - inC.position()) < length) {
                length = (int) (inC.size() - inC.position());
            } else
                length = 1024 * 1024 * 2;
            b = ByteBuffer.allocateDirect(length);
            inC.read(b);
            b.flip();
            outC.write(b);
            outC.force(false);
        }
    }

}
