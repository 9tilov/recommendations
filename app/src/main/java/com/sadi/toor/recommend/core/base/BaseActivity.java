package com.sadi.toor.recommend.core.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import com.sadi.toor.recommend.R;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public abstract class BaseActivity extends AppCompatActivity implements
        HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> dispatchingAndroidInjector;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        configureDagger();
        super.onCreate(savedInstanceState);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return dispatchingAndroidInjector;
    }

    protected abstract void configureDagger();

    public void setActionBarTitle(int title) {
        getSupportActionBar().setTitle(title);
    }

    public void showBackButton(boolean show) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(show);
    }

    @Override
    public void onBackPressed() {
        // Передача нажатия в текущее окно
        final BaseFragment contentFragment = getContentFragment();
        if (contentFragment != null && contentFragment.processBackButton()) {
            // Обработка нажатия перехвачена во фрагменте текущего окна
            return;
        }
        super.onBackPressed();
    }

    private BaseFragment getContentFragment() {
        final Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        if (fragment instanceof BaseFragment) {
            return (BaseFragment) fragment;
        }
        return null;
    }

}
