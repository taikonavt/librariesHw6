package ru.geekbrains.android3_6.mvp.model.cache.imageCache;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;

import io.reactivex.Single;
import io.realm.Realm;
import ru.geekbrains.android3_6.App;
import ru.geekbrains.android3_6.mvp.model.entity.realm.CachedImage;
import timber.log.Timber;

public class RealmImageCache extends AbstractImageCache{

    @Override
    public Single<File> getFile(String url) {
        return Single.create(emitter -> {
            CachedImage cachedImage =
                    Realm.getDefaultInstance()
                            .where(CachedImage.class)
                            .equalTo("url", url)
                            .findFirst();
            if (cachedImage != null) {
                emitter.onSuccess(new File(cachedImage.getPath()));
            }
            emitter.onSuccess(null);
        });
    }

    @Override
    public boolean contains(String url) {
        return Realm.getDefaultInstance()
                .where(CachedImage.class)
                .equalTo("url", url)
                .count() > 0;
    }

    @Override
    public void clear() {
        Realm.getDefaultInstance().executeTransaction(realm -> realm.delete(CachedImage.class));
        deleteFileOrDirRecursive(getImageDir());
    }

    @Override
    public File saveImage(final String url, Bitmap bitmap) {
        if (!getImageDir().exists() && !getImageDir().mkdirs()) {
            throw new RuntimeException("Failed to create directory: " + getImageDir().toString());
        }

        final String fileFormat = url.contains(".jpg") ? ".jpg" : ".png";
        final File imageFile = new File(getImageDir(), SHA1(url) + fileFormat);
        FileOutputStream fos;

        try {
            fos = new FileOutputStream(imageFile);
            bitmap.compress(fileFormat.equals("jpg") ? Bitmap.CompressFormat.JPEG : Bitmap.CompressFormat.PNG, 100, fos);
            fos.close();
        } catch (Exception e) {
            Timber.d("Failed to save image");
            return null;
        }

        Realm.getDefaultInstance().executeTransactionAsync(realm -> {
            CachedImage cachedImage = new CachedImage();
            cachedImage.setUrl(url);
            cachedImage.setPath(imageFile.toString());
            realm.copyToRealm(cachedImage);
        });

        return imageFile;
    }
}
