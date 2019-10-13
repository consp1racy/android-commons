package net.xpece.android.app;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import net.xpece.android.R;
import net.xpece.android.picker.widget.XpTimePicker;

import org.threeten.bp.LocalTime;

import static net.xpece.android.content.XpResources.resolveColorStateList;

public class LocalTimeBpPickerDialogFragment extends AppCompatDialogFragment implements
    DialogInterface.OnClickListener {
    public static final String TAG = LocalTimeBpPickerDialogFragment.class.getSimpleName();

    private TimePicker mTimePicker;
    private LocalTime mTime;
    private boolean mIs24HourFormat;

    private boolean mForceLegacy;

    @StyleRes
    private static int resolveDialogTheme(@NonNull Context context, @StyleRes int themeResId) {
        if (Build.VERSION.SDK_INT >= 21 && themeResId == 0) {
            final TypedValue outValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.dialogTheme, outValue, true);
            return outValue.resourceId;
        } else {
            return themeResId;
        }
    }

    public static LocalTimeBpPickerDialogFragment newInstance(LocalTime time, boolean is24HourFormat) {
        LocalTimeBpPickerDialogFragment f = newInstance(time);
        Bundle args = f.getArguments();
        args.putBoolean("mIs24HourFormat", is24HourFormat);
        return f;
    }

    public static LocalTimeBpPickerDialogFragment newInstance(LocalTime time) {
        Bundle args = new Bundle();
        args.putSerializable("mTime", time);
        LocalTimeBpPickerDialogFragment fragment = new LocalTimeBpPickerDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public LocalTimeBpPickerDialogFragment forceLegacy(final boolean forceLegacy) {
        mForceLegacy = forceLegacy;
        return this;
    }

    public LocalTimeBpPickerDialogFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        if (savedInstanceState != null) {
            mTime = (LocalTime) savedInstanceState.getSerializable("mTime");
            mIs24HourFormat = savedInstanceState.getBoolean("mIs24HourFormat");
            mForceLegacy = savedInstanceState.getBoolean("mForceLegacy");
        }
        if (args != null) {
            mTime = (LocalTime) args.getSerializable("mTime");
            if (args.containsKey("mIs24HourFormat")) {
                mIs24HourFormat = args.getBoolean("mIs24HourFormat");
            } else {
                mIs24HourFormat = DateFormat.is24HourFormat(getContext());
            }
        }
    }

    @Override
    public void onDestroyView() {
        mTimePicker = null;
        if (getDialog() != null && getRetainInstance())
            getDialog().setOnDismissListener(null);
        super.onDestroyView();
    }

    @Override
    @NonNull
    @SuppressWarnings("deprecation")
    @SuppressLint("InflateParams")
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getContext();
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, resolveDialogTheme(context, getTheme()));
        context = builder.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view;
        if (mForceLegacy) {
            view = inflater.inflate(R.layout.dialog_time_picker_legacy, null, false);
        } else {
            view = inflater.inflate(R.layout.dialog_time_picker, null, false);
        }

        final TimePicker timePicker = (TimePicker) view.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(mIs24HourFormat);
        onBindTimePicker(timePicker);

        LocalTime time = mTime;
        if (time != null) {
            timePicker.setCurrentHour(time.getHour());
            timePicker.setCurrentMinute(time.getMinute());
        }

        mTimePicker = timePicker;

        builder.setView(view);
        builder.setPositiveButton(android.R.string.ok, this);
        builder.setNegativeButton(android.R.string.cancel, this);
        return builder.create();
    }

    @SuppressWarnings("WeakerAccess")
    protected void onBindTimePicker(@NonNull TimePicker timePicker) {
        if (Build.VERSION.SDK_INT < 21) {
            final Context context = timePicker.getContext();
            final ColorStateList color = resolveColorStateList(context, R.attr.colorControlNormal);
            XpTimePicker.setSelectionDividerTintCompat(timePicker, color);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LocalTime time = getLocalTime();
        outState.putSerializable("mTime", time);
        outState.putBoolean("mIs24HourFormat", mIs24HourFormat);
    }

    @NonNull
    @SuppressWarnings("deprecation")
    private LocalTime getLocalTime() {
        final TimePicker timePicker = mTimePicker;
        return LocalTime.of(timePicker.getCurrentHour(), timePicker.getCurrentMinute());
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            Callbacks callbacks;
            if (getTargetFragment() instanceof Callbacks) {
                callbacks = (Callbacks) getTargetFragment();
            } else if (getActivity() instanceof Callbacks) {
                callbacks = (Callbacks) getActivity();
            } else {
                throw new IllegalStateException("Activity does not implement Callbacks.");
            }
            LocalTime time = getLocalTime();
            callbacks.onTimeSelected(this, time);
        }
    }

    public interface Callbacks {
        void onTimeSelected(LocalTimeBpPickerDialogFragment fragment, LocalTime time);
    }
}
