package com.sadi.toor.recommend.auth;

import com.google.firebase.auth.FirebaseUser;
import com.sadi.toor.recommend.model.data.user.User;
import com.sadi.toor.recommend.model.repo.DataRepository;

import javax.inject.Inject;

import io.reactivex.Observable;

public class AuthInteractor {

    private DataRepository mDataRepository;

    @Inject
    public AuthInteractor(DataRepository dataRepository) {
        mDataRepository = dataRepository;
    }

    public Observable<User> login(FirebaseUser firebaseUser) {
        return mDataRepository.login(firebaseUser.getUid());
    }
}
