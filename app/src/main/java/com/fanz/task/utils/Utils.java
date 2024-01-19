package com.fanz.task.utils;

import android.content.Context;
import android.util.TypedValue;

public class Utils {

    public static int dpToPx(Context context, float dp) {
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, context.getResources().getDisplayMetrics()));
    }
}