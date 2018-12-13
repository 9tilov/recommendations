package com.sadi.toor.recommend;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.sadi.toor.recommend.core.base.BaseActivity;
import com.sadi.toor.recommend.preparing.ui.MainFragment;

import java.util.Arrays;
import java.util.List;

import dagger.android.AndroidInjection;

public class MainActivity extends BaseActivity {

    List<AuthUI.IdpConfig> providers = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.PhoneBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build());

    private static final int RC_SIGN_IN = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN);
        } else {
            if (savedInstanceState == null) {
                openHomeScreen();
                Log.d("moggot", "uid = " + user.getUid());
                Log.d("moggot", "privider_id = " + user.getProviderId());
            }
        }
    }

    private void openHomeScreen() {
        getSupportFragmentManager().beginTransaction()
                .add(R.id.nav_host_fragment, MainFragment.newInstance())
                .commit();
    }

    @Override
    protected void configureDagger() {
        AndroidInjection.inject(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                openHomeScreen();
                Log.d("moggot", "id = " + user.getUid());
                Log.d("moggot", "privider_id = " + user.getProviderId());
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }
}
