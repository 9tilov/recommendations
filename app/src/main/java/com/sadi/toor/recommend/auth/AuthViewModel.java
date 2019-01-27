package com.sadi.toor.recommend.auth;

import android.arch.lifecycle.MutableLiveData;

import com.google.firebase.auth.FirebaseUser;
import com.sadi.toor.recommend.core.base.BaseViewModel;
import com.sadi.toor.recommend.core.base.LoadingStatus;
import com.sadi.toor.recommend.model.data.preferences.PreferenceRepo;
import com.sadi.toor.recommend.model.data.user.User;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static com.sadi.toor.recommend.core.base.LoadingStatus.ERROR;
import static com.sadi.toor.recommend.core.base.LoadingStatus.START_LOADING;

public class AuthViewModel extends BaseViewModel {

    private AuthInteractor mAuthInteractor;
    private PreferenceRepo mPreferenceRepo;

    private final MutableLiveData<User> userData = new MutableLiveData<>();

    @Inject
    AuthViewModel(AuthInteractor authInteractor, PreferenceRepo preferenceRepo) {
        mAuthInteractor = authInteractor;
        mPreferenceRepo = preferenceRepo;
    }

    public void login(FirebaseUser firebaseUser) {
        compositeDisposable.add(mAuthInteractor.login(firebaseUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> status.postValue(new LoadingStatus(START_LOADING)))
                .doOnError(throwable -> {
                    status.postValue(new LoadingStatus(ERROR, throwable));
                })
                .retryWhen(retryHandler -> retryHandler.flatMap(retryManager::observeRetries))
                .subscribe(user -> {
                    userData.postValue(user);
                    mPreferenceRepo.saveUserToken(user.getCustomAccessToken());
                }));
    }

    public MutableLiveData<User> getUserData() {
        return userData;
    }

}
