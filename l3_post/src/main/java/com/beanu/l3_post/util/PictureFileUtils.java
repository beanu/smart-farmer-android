package com.beanu.l3_post.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.media.MediaMetadataRetriever;
import android.support.annotation.NonNull;
import android.support.annotation.Size;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import com.beanu.arad.utils.CloseUtils;
import com.yanzhenjie.album.AlbumFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author lizhi
 * @date 2017/11/14.
 */

public class PictureFileUtils {

    private PictureFileUtils() {
    }

    /**
     * 读取图片属性：旋转的角度
     *
     * @param path 图片绝对路径
     * @return degree旋转的角度
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    public static Bitmap rotaingImageView(int angle, Bitmap bitmap) {
        //旋转图片 动作
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        System.out.println("angle2=" + angle);
        // 创建新的图片
        return Bitmap.createBitmap(bitmap, 0, 0,
                bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    public static void saveBitmapFile(Bitmap bitmap, File file) {
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static AlbumFile createAlbumFile(File file, boolean isVideo){
        AlbumFile albumFile = new AlbumFile();
        albumFile.setMediaType(isVideo ? AlbumFile.TYPE_VIDEO : AlbumFile.TYPE_IMAGE);
        albumFile.setPath(file.getAbsolutePath());
        String name = file.getName();
        albumFile.setName(name);
        int lastPoint = name.lastIndexOf(".");
        if (lastPoint != -1){
            albumFile.setTitle(name.substring(0, lastPoint));
        } else {
            albumFile.setTitle(name);
        }
        albumFile.setBucketName(file.getParent());
        if (lastPoint != -1 && lastPoint != name.length() - 1){
            albumFile.setMimeType(MimeTypeMap.getSingleton().getMimeTypeFromExtension(name.substring(lastPoint + 1)));
        }
        long date = System.currentTimeMillis();
        albumFile.setAddDate(date);
        albumFile.setModifyDate(date);
        albumFile.setLatitude(0);
        albumFile.setLongitude(0);
        albumFile.setSize(file.length());
        albumFile.setThumbPath(null);
        albumFile.setChecked(true);
        albumFile.setEnable(true);

//        if (!isVideo) {
//            int degree = PictureFileUtils.readPictureDegree(file.getAbsolutePath());
//            rotateImage(degree, file);
//        }

        if (isVideo) {
            int[] size = readVideoInfo(file.getPath());
            albumFile.setWidth(size[0]);
            albumFile.setHeight(size[1]);
            albumFile.setDuration(size[2]);
        }
        return albumFile;
    }

    @Size(3)
    @NonNull
    private static int[] readVideoInfo(String videoPath) {
        int[] info = {-1, -1, 0};
        MediaMetadataRetriever retr = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(videoPath);
            retr = new MediaMetadataRetriever();
            retr.setDataSource(fis.getFD());
            // 视频宽度
            String widthStr = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);
            // 视频高度
            String heightStr = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);
            // 视频长度
            String videoDuration = retr.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION);
            info[0] = TextUtils.isEmpty(widthStr) ? 0 : (int) Float.parseFloat(widthStr);
            info[1] = TextUtils.isEmpty(heightStr) ? 0 : (int) Float.parseFloat(heightStr);
            info[2] = TextUtils.isEmpty(videoDuration) ? 0 : (int) Float.parseFloat(videoDuration);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (retr != null) {
                    retr.release();
                }
            } catch (Exception e) {
                //
            }

            CloseUtils.closeIOQuietly(fis);
        }
        return info;
    }

}
