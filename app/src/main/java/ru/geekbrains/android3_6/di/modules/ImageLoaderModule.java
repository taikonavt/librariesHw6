package ru.geekbrains.android3_6.di.modules;


import android.widget.ImageView;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.android3_6.mvp.model.image.ImageLoader;
import ru.geekbrains.android3_6.mvp.model.image.android.ImageLoaderGlide;
import ru.geekbrains.android3_6.mvp.model.image.android.ImageLoaderPicasso;

@Module
public class ImageLoaderModule {

    @Named("glide")
    @Provides
    public ImageLoader<ImageView> provideGlideImageLoader(){
        return new ImageLoaderGlide();
    }

    @Named("picasso")
    @Provides
    public ImageLoader<ImageView> providePicassoImageLoader(){
        return new ImageLoaderPicasso();
    }
}
