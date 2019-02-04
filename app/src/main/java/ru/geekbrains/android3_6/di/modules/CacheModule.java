package ru.geekbrains.android3_6.di.modules;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.android3_6.mvp.model.cache.ICache;
import ru.geekbrains.android3_6.mvp.model.cache.RoomCache;

@Module
public class CacheModule {

    @Provides
    public ICache cache(){
        return new RoomCache();
    }
}
