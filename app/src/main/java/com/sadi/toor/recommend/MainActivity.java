package com.sadi.toor.recommend;

import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.sadi.toor.recommend.auth.LoginFragment;
import com.sadi.toor.recommend.core.base.BaseActivity;
import com.sadi.toor.recommend.preparing.ui.MainFragment;

import dagger.android.AndroidInjection;

public class MainActivity extends BaseActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user == null) {
            openLoginScreen();
        } else {
            openHomeScreen();
        }
    }

    private void openHomeScreen() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.nav_host_fragment, MainFragment.newInstance())
                .commit();
    }

    private void openLoginScreen() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.nav_host_fragment, LoginFragment.newInstance())
                .commit();
    }

    @Override
    protected void configureDagger() {
        AndroidInjection.inject(this);
    }

}
