package com.sadi.toor.recommend.model.network;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;

public interface RetryManager {
    Observable<RetryEvent> observeRetries(@NonNull Throwable error);

    void retry();
}
