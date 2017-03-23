package com.beanu.l3_guide.video;

import android.content.Context;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 文件操作
 * Created by Beanu on 16/9/10.
 */
public class FileUtil {

    //assets和raw中的单个资源文件不能超过1MB,所以需要拷贝到data/data/下面
    public static File copyAssetsFileToDisk(Context context, String fileName) {
        FileOutputStream fos = null;
        InputStream in = null;
        File videoFile;

        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            in = context.getAssets().open(fileName);
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = in.read(buff)) != -1) {
                fos.write(buff, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭信息流
            try {
                if (fos != null) {
                    fos.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        videoFile = context.getFileStreamPath(fileName);
        if (!videoFile.exists())
            throw new RuntimeException("video file has problem, are you sure you have welcome_video.mp4 in res/raw folder?");

        return videoFile;
    }

    public static File copyRawFileToDisk(Context context, String fileName, int rawResource) {
        FileOutputStream fos = null;
        InputStream in = null;
        File videoFile;

        try {
            fos = context.openFileOutput(fileName, Context.MODE_PRIVATE);
            in = context.getResources().openRawResource(rawResource);
            byte[] buff = new byte[1024];
            int len = 0;
            while ((len = in.read(buff)) != -1) {
                fos.write(buff, 0, len);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭信息流
            try {
                if (fos != null) {
                    fos.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        videoFile = context.getFileStreamPath(fileName);
        if (!videoFile.exists())
            throw new RuntimeException("video file has problem, are you sure you have welcome_video.mp4 in res/raw folder?");

        return videoFile;
    }
}
