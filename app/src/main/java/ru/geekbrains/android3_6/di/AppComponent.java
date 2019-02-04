package ru.geekbrains.android3_6.di;

import dagger.Component;
import ru.geekbrains.android3_6.di.modules.*;
import ru.geekbrains.android3_6.mvp.model.image.android.ImageLoaderGlide;
import ru.geekbrains.android3_6.mvp.presenter.MainPresenter;
import ru.geekbrains.android3_6.ui.activity.MainActivity;
import ru.geekbrains.android3_6.ui.fragment.MainFragment;

import javax.inject.Singleton;

@Singleton
@Component(modules = {
        AppModule.class,
        ApiModule.class,
        CacheModule.class,
        RepoModule.class,
        CiceroneModule.class,
        ImageLoaderModule.class,
        ImageCacheModule.class
})
public interface AppComponent {
    void inject(MainPresenter mainPresenter);
    void inject(MainActivity mainActivity);
    void inject(MainFragment mainFragment);
    void inject(ImageLoaderGlide imageLoaderGlide);
}
