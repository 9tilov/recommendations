package com.sadi.toor.recommend.auth;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.sadi.toor.recommend.R;
import com.sadi.toor.recommend.core.base.BaseFragment;
import com.sadi.toor.recommend.preparing.ui.MainFragment;

import java.util.Arrays;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class LoginFragment extends BaseFragment<AuthViewModel> {

    private static final String TAG = "LoginFragment";

    private AuthViewModel mAuthViewmodel;

    private List<AuthUI.IdpConfig> mProviders = Arrays.asList(
            new AuthUI.IdpConfig.EmailBuilder().build(),
            new AuthUI.IdpConfig.PhoneBuilder().build(),
            new AuthUI.IdpConfig.GoogleBuilder().build());

    private FirebaseAuth mAuth;

    public static final int RC_SIGN_IN = 100;

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState, AuthViewModel viewModel) {
        mAuth = FirebaseAuth.getInstance();
        mAuthViewmodel = viewModel;
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(mProviders)
                        .build(),
                RC_SIGN_IN);
        mAuthViewmodel.getUserData().observe(this, user -> {
            if (user != null) {
                mAuth.signInWithCustomToken(user.getCustomAccessToken())
                        .addOnCompleteListener(getActivity(), runnable -> {
                            FirebaseUser loginUser = mAuth.getCurrentUser();
                            if (loginUser != null) {
                                login(loginUser);
                            }
                        });
            }
        });
    }

    @Override
    protected Class<AuthViewModel> getViewModel() {
        return AuthViewModel.class;
    }

    @Override
    protected int getTitle() {
        return R.string.fragment_title_login;
    }

    @Override
    protected String getFragmentTag() {
        return TAG;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.login_fragment;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                login(user);
            }
        }
    }

    private void login(@Nullable FirebaseUser user) {
        if (user == null) {
            return;
        }
        user.getIdToken(false).addOnSuccessListener(new OnSuccessListener<GetTokenResult>() {
            @Override
            public void onSuccess(GetTokenResult runnable) {
                mAuthViewmodel.login(user);
                openHomeScreen();
            }
        });
    }

    private void openHomeScreen() {
        getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.nav_host_fragment, MainFragment.newInstance())
                .commit();
    }
}
