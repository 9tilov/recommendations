package com.sadi.toor.recommend.view.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;

import com.sadi.toor.recommend.R;
import com.sadi.toor.recommend.core.base.BaseActivity;

import dagger.android.AndroidInjection;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
    }

    @Override
    protected void configureDagger() {
        AndroidInjection.inject(this);
    }
}
