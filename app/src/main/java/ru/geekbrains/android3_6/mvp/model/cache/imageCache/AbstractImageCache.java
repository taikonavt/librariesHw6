package ru.geekbrains.android3_6.mvp.model.cache.imageCache;

import android.graphics.Bitmap;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import io.reactivex.Single;
import ru.geekbrains.android3_6.App;

public abstract class AbstractImageCache {

    private static final String IMAGE_FOLDER_NAME = "image";

    public abstract Single<File> getFile(String url);
    public abstract boolean contains(String url);
    public abstract File saveImage(String url, Bitmap bitmap);
    public abstract void clear();

    public float getSizeKb() {
        return getFileOrDirSize(getImageDir()) / 1024f;
    }

    File getImageDir() {
        return new File(App.getInstance().getExternalFilesDir(null) + "/" + IMAGE_FOLDER_NAME);
    }

    String SHA1(String s) {
        MessageDigest m = null;

        try {
            m = MessageDigest.getInstance("SHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        m.update(s.getBytes(), 0, s.length());
        String hash = new BigInteger(1, m.digest()).toString(16);
        return hash;
    }

    void deleteFileOrDirRecursive(File fileOrDirectory) {
        if (fileOrDirectory.isDirectory()) {
            for (File child : fileOrDirectory.listFiles()) {
                deleteFileOrDirRecursive(child);
            }
        }
        fileOrDirectory.delete();
    }

    long getFileOrDirSize(File f) {
        long size = 0;
        if (f.isDirectory()) {
            for (File file : f.listFiles()) {
                size += getFileOrDirSize(file);
            }
        } else {
            size = f.length();
        }
        return size;
    }
}
