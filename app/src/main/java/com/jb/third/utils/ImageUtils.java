package com.jb.third.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.text.TextUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by jianbin on 16/12/30.
 */

public class ImageUtils {
    public static final String SDCardRoot = Environment.getExternalStorageDirectory()
            .getAbsolutePath() + File.separator;
    public static final String DEFAULT_DIR_NAME = "ThirdAndroid";
    // 图片下载目录
    public static final String IMAGE_DIRECTORY = SDCardRoot + DEFAULT_DIR_NAME + File.separator + "images";

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

    public static Bitmap getCompressedBitmap(String path, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }

    public static String getIconPath(Context context) {
        String iconFileName = "icon.png";
        String iconFilePath = getImagePath(iconFileName);
        if (!TextUtils.isEmpty(iconFilePath)) {
            return iconFilePath;
        }
        Drawable iconDrawable = context.getApplicationInfo().loadIcon(context.getPackageManager());
        File file = saveBitmap(drawableToBitmap(iconDrawable), iconFileName, 100);
        if (file != null) {
            return file.getAbsolutePath();
        }
        return null;
    }

    public static String getImagePath(String fileName) {
        File f = new File(IMAGE_DIRECTORY + fileName);
        if (f.exists()) {
            return f.getAbsolutePath();
        }
        return null;
    }

    public static File saveBitmap(Bitmap bitmap, String fileName, int quality) {
        createSDDir(IMAGE_DIRECTORY);
        File f = new File(IMAGE_DIRECTORY + fileName);
        if (f.exists()) {
            f.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, quality, out);//90 是压缩率，表示压缩10%; 如果不压缩是100，表示压缩率为0
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return f;
    }

    /**
     * 创建路径
     *
     * @param dir 不带SDCardRoot和后面的分隔符\的路径
     */
    public static File createSDDir(String dir) {
        File dirFile = new File(dir);
        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        return dirFile;
    }

    public static Bitmap drawableToBitmap(Drawable drawable) {
        BitmapDrawable bd = (BitmapDrawable) drawable;
        return bd.getBitmap();
    }
}
