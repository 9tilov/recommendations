package com.sadi.toor.recommend.filter.year.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.NumberPicker;

import com.sadi.toor.recommend.R;
import com.sadi.toor.recommend.core.base.BaseFragment;
import com.sadi.toor.recommend.filter.year.viewmodel.YearViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;

public class YearFragment extends BaseFragment<YearViewModel> {

    @BindView(R.id.year_np_start)
    NumberPicker npStart;
    @BindView(R.id.year_np_end)
    NumberPicker npEnd;

    private static final int MIN_YEAR = 1899;

    private String[] displayedValues;

    public static YearFragment newInstance() {
        return new YearFragment();
    }

    @Override
    protected Class<YearViewModel> getViewModel() {
        return YearViewModel.class;
    }

    @Override
    protected void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState, YearViewModel viewModel) {
        formDisplayedValues();
        initNumberPickers();
        npStart.setOnValueChangedListener((picker, oldVal, newVal) -> {
            int curVal = Integer.parseInt(displayedValues[newVal]);
            if (curVal > Integer.parseInt(displayedValues[npEnd.getValue()])) {
                npEnd.setValue(0);
            }
        });

        npEnd.setOnValueChangedListener((picker, oldVal, newVal) -> {
            int curVal = Integer.parseInt(displayedValues[newVal]);
            if (curVal < Integer.parseInt(displayedValues[npStart.getValue()])) {
                npStart.setValue(newVal);
            }
        });
    }

    private void formDisplayedValues() {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        List<String> startValues = new ArrayList<>();
        for (int i = currentYear; i >= MIN_YEAR; --i) {
            startValues.add(String.valueOf(i));
        }
        displayedValues = startValues.toArray(new String[0]);
    }

    private void initNumberPickers() {
        npStart.setWrapSelectorWheel(false);
        npEnd.setWrapSelectorWheel(false);
        npStart.setMaxValue(displayedValues.length - 1);
        npEnd.setMaxValue(displayedValues.length - 1);
        npStart.setDisplayedValues(displayedValues);
        npEnd.setDisplayedValues(displayedValues);
    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case android.R.id.home:
//                sharedViewModel.putPeriod(new Pair<>(Integer.parseInt(displayedValues[npStart.getValue()]), Integer.parseInt(displayedValues[npEnd.getValue()])));
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    protected int getTitle() {
        return R.string.fragment_title_time_period;
    }

    @Override
    protected boolean showBackButton() {
        return true;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.year_fragment;
    }
}
