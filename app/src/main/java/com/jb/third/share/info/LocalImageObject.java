package com.jb.third.share.info;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 本地图片分享实体
 * Created by jianbin on 16/12/21.
 */
public class LocalImageObject implements MediaObject {

    public String imagePath; // 文件路径
    public byte[] bytes;

    public LocalImageObject(Bitmap bitmap) {
        bytes = bmpToByteArray(bitmap, true);
    }

    public LocalImageObject(String path) {
        this.imagePath = path;
        File file = new File(path);
        bytes = getBytesFromFile(file);
    }

    public LocalImageObject(byte[] bytes) {
        this.bytes = bytes;
    }

    /**
     * bitmap转换成为byte[]，传输需要
     *
     * @param bmp
     * @param needRecycle
     * @return
     */
    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static byte[] getBytesFromFile(File f) {
        if (f == null) {
            return null;
        }
        try {
            FileInputStream stream = new FileInputStream(f);
            ByteArrayOutputStream out = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = stream.read(b)) != -1)
                out.write(b, 0, n);
            stream.close();
            out.close();
            return out.toByteArray();
        } catch (IOException e) {

        }
        return null;
    }

    @Override
    public int type() {
        return TYPE_LOCAL_IMAGE;
    }
}
