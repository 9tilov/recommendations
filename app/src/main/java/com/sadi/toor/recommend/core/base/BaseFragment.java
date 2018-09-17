package com.sadi.toor.recommend.core.base;

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

import com.sadi.toor.recommend.Analytics;
import com.sadi.toor.recommend.core.common.SharedViewModel;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;

public abstract class BaseFragment<M extends BaseViewModel> extends Fragment {

    @Inject
    protected ViewModelProvider.Factory viewModelFactory;
    protected SharedViewModel sharedViewModel;
    protected Analytics analytics;
    private M viewModel;
    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        configureDagger();
        super.onAttach(context);
        analytics = new Analytics(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);
        this.viewModel = ViewModelProviders.of(this, viewModelFactory).get(getViewModel());
        onCreate(savedInstanceState, viewModel);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        unbinder = ButterKnife.bind(this, view);
        analytics.setCurrentScreen(getActivity(), getFragmentTag());
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((BaseActivity) getActivity()).showBackButton(showBackButton());
        ((BaseActivity) getActivity()).setActionBarTitle(getTitle());
    }

    protected abstract void onCreate(@Nullable Bundle savedInstanceState, M viewModel);

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        this.viewModel.unsubscribeFromDestroy(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().getSupportFragmentManager().popBackStack();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    private void configureDagger() {
        AndroidSupportInjection.inject(this);
    }

    protected abstract Class<M> getViewModel();

    @StringRes
    protected abstract int getTitle();

    protected abstract String getFragmentTag();

    protected abstract boolean showBackButton();

    @LayoutRes
    protected abstract int getLayoutResId();

    public final boolean processBackButton() {
        final List<Fragment> childFragments = getChildFragmentManager().getFragments();
        if (childFragments != null) {

            // Перебираем дочерние фрагменты до первого, кто поглотит событие
            for (Fragment childFragment : childFragments) {
                if (childFragment instanceof BaseFragment
                        && ((BaseFragment) childFragment).processBackButton()) {
                    return true;
                }
            }
        }

        // Дочерних фрагментов нет, или никто из них событие не поглотил - передаём обработку в
        // текущий фрагмент
        return onBackPressed();
    }

    protected boolean onBackPressed() {
        return false;
    }
}
