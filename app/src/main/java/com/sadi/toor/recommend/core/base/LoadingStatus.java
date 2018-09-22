package com.sadi.toor.recommend.core.base;

import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public class LoadingStatus {

    @IntDef({SUCCESS, ERROR, START_LOADING})
    @Retention(SOURCE)
    public @interface LoadingType {
    }
    public static final int SUCCESS = 0;
    public static final int ERROR = 1;
    public static final int START_LOADING = 2;

    private int type;
    private Throwable throwable;

    public LoadingStatus(@LoadingType int type) {
        this(type, null);
    }

    public LoadingStatus(@LoadingType int type, Throwable throwable) {
        this.throwable = throwable;
        this.type = type;
    }

    @Nullable
    public Throwable getThrowable() {
        return throwable;
    }

    @LoadingType
    public int getType() {
        return type;
    }
}
