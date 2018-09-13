package com.sadi.toor.recommend;

import android.os.Bundle;

import com.sadi.toor.recommend.core.base.BaseActivity;
import com.sadi.toor.recommend.preparing.ui.MainFragment;

import dagger.android.AndroidInjection;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.nav_host_fragment, MainFragment.newInstance())
                    .commit();
        }
    }

    @Override
    protected void configureDagger() {
        AndroidInjection.inject(this);
    }
}
