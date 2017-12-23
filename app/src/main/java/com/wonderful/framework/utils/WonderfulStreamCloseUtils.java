package com.wonderful.framework.utils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Administrator on 2017/8/16.
 */

public class WonderfulStreamCloseUtils {
    /**
     * 关闭当前流
     * @param closeable
     */
    public static void closeStream(Closeable closeable) {
        if (closeable == null) return;
        if (closeable instanceof OutputStream) {
            OutputStream out = (OutputStream) closeable;
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
              e.printStackTrace();
            }
        } else if (closeable instanceof InputStream) {
            InputStream in = (InputStream) closeable;
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断流是否关闭 （仅限测试使用）
     */
    public static boolean isClose(Closeable closeable) {
        if (closeable instanceof OutputStream) {
            OutputStream out = (OutputStream) closeable;
            try {
                out.write(new byte[1]);
            } catch (IOException e) {
                return true;
            }
        } else if (closeable instanceof InputStream) {
            InputStream in = (InputStream) closeable;
            try {
                in.read();
            } catch (IOException e) {
                return true;
            }
        }
        return false;
    }
}
