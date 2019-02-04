package ru.geekbrains.android3_6.di.modules;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.android3_6.mvp.model.cache.imageCache.AbstractImageCache;
import ru.geekbrains.android3_6.mvp.model.cache.imageCache.RealmImageCache;
import ru.geekbrains.android3_6.mvp.model.cache.imageCache.RoomImageCache;

@Module
public class ImageCacheModule {

    @Named("realm")
    @Provides
    public AbstractImageCache provideRealmImageCache(){
        return new RealmImageCache();
    }

    @Named("room")
    @Provides
    public AbstractImageCache provideRoomImageCache(){
        return new RoomImageCache();
    }
}
