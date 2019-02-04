package ru.geekbrains.android3_6.mvp.model.cache.imageCache;

import android.graphics.Bitmap;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.android3_6.App;
import ru.geekbrains.android3_6.mvp.common.Utils;
import ru.geekbrains.android3_6.mvp.model.entity.room.RoomImage;
import ru.geekbrains.android3_6.mvp.model.entity.room.db.UserDatabase;

public class RoomImageCache extends AbstractImageCache{

    public RoomImageCache(){
    }

    @Override
    public Single<File> getFile(String url) {
        return Single.create(emitter -> {
            RoomImage roomImage = UserDatabase.getInstance().getImageDao()
                    .findByUrl(url);
            if (roomImage != null){
                String path = roomImage.getPath();
                emitter.onSuccess(new File(path));
            } else {
                emitter.onSuccess(null);
            }
        });
    }

    @Override
    public boolean contains(String url) {

        return false;
    }

    @Override
    public File saveImage(String url, Bitmap bitmap) {
        final String fileFormat = url.contains(".jpg") ? ".jpg" : ".png";
        String fileName = Utils.SHA1(url);
        String path = App.getInstance().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                + File.separator + fileName + fileFormat;

        Completable.fromAction(() -> {
            RoomImage roomImage = UserDatabase.getInstance().getImageDao()
                    .findByUrl(url);

            if (roomImage == null) {
                roomImage = new RoomImage();
                roomImage.setUrl(url);
            }
            roomImage.setPath(path);

            try (FileOutputStream outputStream = new FileOutputStream(path)){
                bitmap.compress(
                        fileFormat.equals(".jpg") ? Bitmap.CompressFormat.JPEG : Bitmap.CompressFormat.PNG,
                        100, outputStream);
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }

            UserDatabase.getInstance().getImageDao()
                    .insert(roomImage);
        })
                .subscribeOn(Schedulers.io())
                .subscribe();
        return new File(path);
    }

    @Override
    public void clear() {

    }
}
