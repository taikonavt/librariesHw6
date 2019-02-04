package ru.geekbrains.android3_6.mvp.model.image.android;

import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.File;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ru.geekbrains.android3_6.App;
import ru.geekbrains.android3_6.NetworkStatus;
import ru.geekbrains.android3_6.mvp.model.cache.imageCache.AbstractImageCache;
import ru.geekbrains.android3_6.mvp.model.image.ImageLoader;
import timber.log.Timber;

public class ImageLoaderGlide implements ImageLoader<ImageView> {

    @Named("room")
    @Inject
    AbstractImageCache abstractImageCache;

    public ImageLoaderGlide(){
        App.getInstance().getAppComponent().inject(this);
    }

    @Override
    public void loadInto(@Nullable String url, ImageView container) {
        if (NetworkStatus.isOnline()) {
            GlideApp.with(container.getContext()).asBitmap().load(url).listener(new RequestListener<Bitmap>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                    Timber.e(e, "Image load failed");
                    return false;
                }

                @Override
                public boolean onResourceReady(Bitmap resource,
                                               Object model,
                                               Target<Bitmap> target,
                                               DataSource dataSource,
                                               boolean isFirstResource) {
                    abstractImageCache.saveImage(url, resource);
                    return false;
                }
            }).into(container);
        } else {
                abstractImageCache.getFile(url)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new SingleObserver<File>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onSuccess(File file) {
                                if (file != null){
                                    GlideApp.with(container.getContext())
                                            .load(file)
                                            .into(container);
                                }
                            }

                            @Override
                            public void onError(Throwable throwable) {
                                Timber.e(throwable, "Failed to read image");
                            }
                        });
        }
    }
}
