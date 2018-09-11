package com.sadi.toor.recommend.model.network;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.subjects.PublishSubject;

public class NetworkRetryManager implements RetryManager {

    @NonNull
    private final PublishSubject<RetryEvent> retrySubject = PublishSubject.create();

    @Override
    public Observable<RetryEvent> observeRetries(Throwable error) {
        return retrySubject;
    }

    @Override
    public void retry() {
        retrySubject.onNext(new RetryEvent());
    }
}
