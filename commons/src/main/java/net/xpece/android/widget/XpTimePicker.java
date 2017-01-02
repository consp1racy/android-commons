package net.xpece.android.widget;

import android.content.res.ColorStateList;
import android.os.Build;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import java.lang.reflect.Field;

/**
 * Created by Eugen on 06.05.2016.
 */
public final class XpTimePicker {
    private static final Class<?> CLASS_SPINNER_DELEGATE;
    private static final Field FIELD_DELEGATE;

    private static final Field FIELD_HOUR_SPINNER;
    private static final Field FIELD_MINUTE_SPINNER;
    private static final Field FIELD_AM_PM_SPINNER;

    static {
        if (Build.VERSION.SDK_INT >= 11) {
            Field f;
            Class<?> cls = TimePicker.class;
            if (Build.VERSION.SDK_INT < 21) {
                f = null;
                try {
                    f = cls.getDeclaredField("mAmPmSpinner");
                    f.setAccessible(true);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                FIELD_AM_PM_SPINNER = f;

                f = null;
                try {
                    f = cls.getDeclaredField("mMinuteSpinner");
                    f.setAccessible(true);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                FIELD_MINUTE_SPINNER = f;

                f = null;
                try {
                    f = cls.getDeclaredField("mHourSpinner");
                    f.setAccessible(true);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                FIELD_HOUR_SPINNER = f;

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

                if (Build.VERSION.SDK_INT == 21) {
                    try {
                        cls = Class.forName("android.widget.TimePickerClockDelegate");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    CLASS_SPINNER_DELEGATE = cls;
                } else {
                    try {
                        cls = Class.forName("android.widget.TimePickerSpinnerDelegate");
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                    CLASS_SPINNER_DELEGATE = cls;
                }

                f = null;
                try {
                    f = cls.getDeclaredField("mAmPmSpinner");
                    f.setAccessible(true);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                FIELD_AM_PM_SPINNER = f;

                f = null;
                try {
                    f = cls.getDeclaredField("mMinuteSpinner");
                    f.setAccessible(true);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                FIELD_MINUTE_SPINNER = f;

                f = null;
                try {
                    f = cls.getDeclaredField("mHourSpinner");
                    f.setAccessible(true);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
                FIELD_HOUR_SPINNER = f;
            }
        } else {
            FIELD_AM_PM_SPINNER = null;
            FIELD_HOUR_SPINNER = null;
            FIELD_MINUTE_SPINNER = null;
            FIELD_DELEGATE = null;
            CLASS_SPINNER_DELEGATE = null;
        }
    }

    private XpTimePicker() {}

    private static NumberPicker getAmPmSpinner(Object picker) {
        try {
            return (NumberPicker) FIELD_AM_PM_SPINNER.get(picker);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static NumberPicker getMinuteSpinner(Object picker) {
        try {
            return (NumberPicker) FIELD_MINUTE_SPINNER.get(picker);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static NumberPicker getHourSpinner(Object picker) {
        try {
            return (NumberPicker) FIELD_HOUR_SPINNER.get(picker);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Object getDelegate(TimePicker picker) {
        try {
            return FIELD_DELEGATE.get(picker);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void setSelectionDividerTint(TimePicker picker, ColorStateList color) {
        if (Build.VERSION.SDK_INT < 11) return;

        if (Build.VERSION.SDK_INT < 21) {
            NumberPicker np;
            np = getAmPmSpinner(picker);
            if (np != null) {
                XpNumberPicker.setSelectionDividerTintInt(np, color);
            }
            np = getMinuteSpinner(picker);
            if (np != null) {
                XpNumberPicker.setSelectionDividerTintInt(np, color);
            }
            np = getHourSpinner(picker);
            if (np != null) {
                XpNumberPicker.setSelectionDividerTintInt(np, color);
            }
        } else {
            Object delegate = getDelegate(picker);
            if (delegate != null && delegate.getClass() == CLASS_SPINNER_DELEGATE) {
                NumberPicker np;
                np = getAmPmSpinner(delegate);
                if (np != null) {
                    XpNumberPicker.setSelectionDividerTintInt(np, color);
                }
                np = getMinuteSpinner(delegate);
                if (np != null) {
                    XpNumberPicker.setSelectionDividerTintInt(np, color);
                }
                np = getHourSpinner(delegate);
                if (np != null) {
                    XpNumberPicker.setSelectionDividerTintInt(np, color);
                }
            }
        }
    }
}
