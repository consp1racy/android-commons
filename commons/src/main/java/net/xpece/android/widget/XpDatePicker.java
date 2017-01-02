package net.xpece.android.widget;

import android.content.res.ColorStateList;
import android.os.Build;
import android.widget.DatePicker;
import android.widget.NumberPicker;

import java.lang.reflect.Field;

/**
 * Created by Eugen on 06.05.2016.
 */
public final class XpDatePicker {
    private static final Class<?> CLASS_SPINNER_DELEGATE;
    private static final Field FIELD_DELEGATE;

    private static final Field FIELD_DAY_SPINNER;
    private static final Field FIELD_MONTH_SPINNER;
    private static final Field FIELD_YEAR_SPINNER;

    static {
        if (Build.VERSION.SDK_INT >= 11) {
            Field f;
            Class<?> cls = DatePicker.class;
            if (Build.VERSION.SDK_INT < 21) {
                CLASS_SPINNER_DELEGATE = null;
                FIELD_DELEGATE = null;
            } else {
                f = null;
                try {
                    f = cls.getDeclaredField("mDelegate");
                    f.setAccessible(true);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                FIELD_DELEGATE = f;

                if (Build.VERSION.SDK_INT < 24) {
                    try {
                        cls = Class.forName("android.widget.DatePicker$DatePickerSpinnerDelegate");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    CLASS_SPINNER_DELEGATE = cls;
                } else {
                    try {
                        cls = Class.forName("android.widget.DatePickerSpinnerDelegate");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    CLASS_SPINNER_DELEGATE = cls;
                }
            }

            f = null;
            try {
                f = cls.getDeclaredField("mYearSpinner");
                f.setAccessible(true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            FIELD_YEAR_SPINNER = f;

            f = null;
            try {
                f = cls.getDeclaredField("mMonthSpinner");
                f.setAccessible(true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            FIELD_MONTH_SPINNER = f;

            f = null;
            try {
                f = cls.getDeclaredField("mDaySpinner");
                f.setAccessible(true);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            FIELD_DAY_SPINNER = f;
        } else {
            FIELD_DAY_SPINNER = null;
            FIELD_MONTH_SPINNER = null;
            FIELD_YEAR_SPINNER = null;
            FIELD_DELEGATE = null;
            CLASS_SPINNER_DELEGATE = null;
        }
    }

    private XpDatePicker() {}

    private static NumberPicker getYearSpinner(Object picker) {
        try {
            return (NumberPicker) FIELD_YEAR_SPINNER.get(picker);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static NumberPicker getMonthSpinner(Object picker) {
        try {
            return (NumberPicker) FIELD_MONTH_SPINNER.get(picker);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static NumberPicker getDaySpinner(Object picker) {
        try {
            return (NumberPicker) FIELD_DAY_SPINNER.get(picker);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Object getDelegate(DatePicker picker) {
        try {
            return FIELD_DELEGATE.get(picker);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setSelectionDividerTint(DatePicker picker, ColorStateList color) {
        if (Build.VERSION.SDK_INT < 11) return;

        if (Build.VERSION.SDK_INT < 21) {
            NumberPicker np;
            np = getYearSpinner(picker);
            if (np != null) {
                XpNumberPicker.setSelectionDividerTintInt(np, color);
            }
            np = getMonthSpinner(picker);
            if (np != null) {
                XpNumberPicker.setSelectionDividerTintInt(np, color);
            }
            np = getDaySpinner(picker);
            if (np != null) {
                XpNumberPicker.setSelectionDividerTintInt(np, color);
            }
        } else {
            Object delegate = getDelegate(picker);
            if (delegate != null && delegate.getClass() == CLASS_SPINNER_DELEGATE) {
                NumberPicker np;
                np = getYearSpinner(delegate);
                if (np != null) {
                    XpNumberPicker.setSelectionDividerTintInt(np, color);
                }
                np = getMonthSpinner(delegate);
                if (np != null) {
                    XpNumberPicker.setSelectionDividerTintInt(np, color);
                }
                np = getDaySpinner(delegate);
                if (np != null) {
                    XpNumberPicker.setSelectionDividerTintInt(np, color);
                }
            }
        }
    }
}
