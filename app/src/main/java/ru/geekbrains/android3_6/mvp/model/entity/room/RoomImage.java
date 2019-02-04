package ru.geekbrains.android3_6.mvp.model.entity.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;


@Entity
public class RoomImage {

    @NonNull
    @PrimaryKey
    private String url;
    private String path;

    public String getPath() {
        return path;
    }

    @NonNull
    public String getUrl() {
        return url;
    }

    public void setUrl(@NonNull String url) {
        this.url = url;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
