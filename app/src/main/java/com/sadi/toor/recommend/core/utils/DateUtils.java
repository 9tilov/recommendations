package com.sadi.toor.recommend.core.utils;

import java.util.Calendar;

public class DateUtils {

    private DateUtils() {
        throw new AssertionError("No instances.");
    }

    public static final int MIN_YEAR = 1899;
    public static final int DEFAULT_YEAR = 1990;

    public static int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }
}
