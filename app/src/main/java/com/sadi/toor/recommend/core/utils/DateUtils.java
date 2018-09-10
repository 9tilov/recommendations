package com.sadi.toor.recommend.core.utils;

import java.util.Calendar;

public class DateUtils {

    public static final int MIN_YEAR = 1899;

    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }
}
