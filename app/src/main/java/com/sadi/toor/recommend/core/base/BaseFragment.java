package com.sadi.toor.recommend.core.base;

import android.annotation.SuppressLint;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.sadi.toor.recommend.viewmodel.SharedViewModel;

import javax.inject.Inject;

import androidx.navigation.Navigation;
import dagger.android.support.AndroidSupportInjection;

public abstract class BaseFragment<M extends ViewModel> extends Fragment {

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;
    protected SharedViewModel sharedViewModel;

    @Override
    public void onAttach(Context context) {
        configureDagger();
        super.onAttach(context);
        ((BaseActivity) getActivity()).setActionBarTitle(getString(getTitle()));
    }

    @SuppressWarnings("unchecked")
    @SuppressLint("CheckResult")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(getLayoutResId(), container, false);
    }

    @SuppressWarnings("unchecked")
    @SuppressLint("CheckResult")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ViewModel viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModel());
        sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        onViewCreated(view, savedInstanceState, (M) viewModel);
        ((BaseActivity) getActivity()).showBackButton(showBackButton());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Navigation.findNavController(getView()).popBackStack();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void configureDagger() {
        AndroidSupportInjection.inject(this);
    }

    protected abstract Class<M> getViewModel();

    protected abstract void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState, M viewModel);

    @StringRes
    protected abstract int getTitle();

    protected abstract boolean showBackButton();

    @LayoutRes
    protected abstract int getLayoutResId();
}
