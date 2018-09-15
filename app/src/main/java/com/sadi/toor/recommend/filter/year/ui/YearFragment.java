package com.sadi.toor.recommend.filter.year.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;
import android.view.View;
import android.widget.NumberPicker;

import com.sadi.toor.recommend.R;
import com.sadi.toor.recommend.core.base.BaseFragment;
import com.sadi.toor.recommend.core.utils.DateUtils;
import com.sadi.toor.recommend.filter.year.viewmodel.YearViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static com.sadi.toor.recommend.core.utils.DateUtils.DEFAULT_YEAR;
import static com.sadi.toor.recommend.core.utils.DateUtils.MIN_YEAR;

public class YearFragment extends BaseFragment<YearViewModel> {

    public static final String TAG = "YearFragment";

    @BindView(R.id.year_np_start)
    NumberPicker npStart;
    @BindView(R.id.year_np_end)
    NumberPicker npEnd;

    private String[] displayedValues;
    private YearViewModel viewModel;

    public static YearFragment newInstance() {
        return new YearFragment();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState, YearViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    protected Class<YearViewModel> getViewModel() {
        return YearViewModel.class;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initNumberPickers();
        sharedViewModel.getSelectedPeriod().observe(this, integerIntegerPair -> {
            if (integerIntegerPair == null) {
                return;
            }
            int currentYear = DateUtils.getCurrentYear();
            npStart.setValue(currentYear - integerIntegerPair.first);
            npEnd.setValue(currentYear - integerIntegerPair.second);
        });
        npStart.setOnValueChangedListener((picker, oldVal, newVal) -> {
            int curVal = Integer.parseInt(displayedValues[newVal]);
            if (curVal > Integer.parseInt(displayedValues[npEnd.getValue()])) {
                npEnd.setValue(0);
            }
            sharedViewModel.putPeriod(new Pair<>(curVal, Integer.parseInt(displayedValues[npEnd.getValue()])));
        });

        npEnd.setOnValueChangedListener((picker, oldVal, newVal) -> {
            int curVal = Integer.parseInt(displayedValues[newVal]);
            if (curVal < Integer.parseInt(displayedValues[npStart.getValue()])) {
                npStart.setValue(newVal);
            }
            sharedViewModel.putPeriod(new Pair<>(Integer.parseInt(displayedValues[npStart.getValue()]), curVal));
        });
    }

    private void initNumberPickers() {
        formDisplayedValues();
        npStart.setWrapSelectorWheel(false);
        npEnd.setWrapSelectorWheel(false);
        npStart.setMaxValue(displayedValues.length - 1);
        npEnd.setMaxValue(displayedValues.length - 1);
        npStart.setDisplayedValues(displayedValues);
        npEnd.setDisplayedValues(displayedValues);
        int currentYear = DateUtils.getCurrentYear();
        npStart.setValue(currentYear - DEFAULT_YEAR);
    }

    private void formDisplayedValues() {
        int currentYear = DateUtils.getCurrentYear();
        List<String> startValues = new ArrayList<>();
        for (int i = currentYear; i >= MIN_YEAR; --i) {
            startValues.add(String.valueOf(i));
        }
        displayedValues = startValues.toArray(new String[0]);
    }

    @Override
    protected int getTitle() {
        return R.string.fragment_title_time_period;
    }

    @Override
    protected String getFragmentTag() {
        return TAG;
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
