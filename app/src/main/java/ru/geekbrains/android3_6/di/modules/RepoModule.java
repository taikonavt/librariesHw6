package ru.geekbrains.android3_6.di.modules;

import dagger.Module;
import dagger.Provides;
import ru.geekbrains.android3_6.mvp.model.api.IDataSource;
import ru.geekbrains.android3_6.mvp.model.cache.ICache;
import ru.geekbrains.android3_6.mvp.model.repo.UsersRepo;

import javax.inject.Singleton;

@Module
public class RepoModule {

    @Singleton
    @Provides
    public UsersRepo usersRepo(ICache cache, IDataSource dataSource){
        return new UsersRepo(cache, dataSource);
    }

}
