package net.xpece.android.dialog.threeten.app;

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

import net.xpece.android.content.XpResources;
import net.xpece.android.dialog.threeten.R;
import net.xpece.android.picker.widget.XpTimePicker;

import java.time.LocalTime;

public class LocalTimePickerDialogFragment extends AppCompatDialogFragment implements
        DialogInterface.OnClickListener {

    public static final String TAG = LocalTimePickerDialogFragment.class.getSimpleName();

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

    @NonNull
    public static LocalTimePickerDialogFragment newInstance(@Nullable LocalTime time, boolean is24HourFormat) {
        final LocalTimePickerDialogFragment f = newInstance(time);
        final Bundle args = f.requireArguments();
        args.putBoolean("mIs24HourFormat", is24HourFormat);
        return f;
    }

    @NonNull
    public static LocalTimePickerDialogFragment newInstance(@Nullable LocalTime time) {
        Bundle args = new Bundle();
        args.putSerializable("mTime", time);
        LocalTimePickerDialogFragment fragment = new LocalTimePickerDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    public LocalTimePickerDialogFragment forceLegacy(final boolean forceLegacy) {
        mForceLegacy = forceLegacy;
        return this;
    }

    public LocalTimePickerDialogFragment() {
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
        } else if (args != null) {
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
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setOnDismissListener(null);
        }
        super.onDestroyView();
    }

    @Override
    @NonNull
    @SuppressLint("InflateParams")
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mTime = (LocalTime) savedInstanceState.getSerializable("mTime");
        }

        Context context = requireContext();
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, resolveDialogTheme(context, getTheme()));
        context = builder.getContext();

        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view;
        if (mForceLegacy) {
            view = inflater.inflate(R.layout.dialog_time_picker_legacy, null, false);
        } else {
            view = inflater.inflate(R.layout.dialog_time_picker, null, false);
        }

        final TimePicker timePicker = view.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(mIs24HourFormat);
        onBindTimePicker(timePicker);

        LocalTime time = mTime;
        if (time != null) {
            timePicker.setHour(time.getHour());
            timePicker.setMinute(time.getMinute());
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
            final ColorStateList color = XpResources.resolveColorStateList(context, R.attr.colorControlNormal);
            XpTimePicker.setSelectionDividerTintCompat(timePicker, color);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        LocalTime time = getLocalTime();
        outState.putSerializable("mTime", time);
        outState.putBoolean("mIs24HourFormat", mIs24HourFormat);
    }

    @NonNull
    private LocalTime getLocalTime() {
        final TimePicker timePicker = mTimePicker;
        return LocalTime.of(timePicker.getHour(), timePicker.getMinute());
    }

    @Override
    public void onClick(@NonNull DialogInterface dialog, int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            Callbacks callbacks = getCallbacks();
            LocalTime time = getLocalTime();
            callbacks.onTimeSelected(this, time);
        } else if (which == DialogInterface.BUTTON_NEGATIVE) {
            onCancel(dialog);
        }
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        getCallbacks().onCancel(this);
    }

    private Callbacks getCallbacks() {
        if (getTargetFragment() instanceof Callbacks) {
            return (Callbacks) getTargetFragment();
        } else if (getActivity() instanceof Callbacks) {
            return (Callbacks) getActivity();
        } else {
            throw new IllegalStateException("Activity does not implement Callbacks.");
        }
    }

    public interface Callbacks {
        void onTimeSelected(@NonNull LocalTimePickerDialogFragment fragment, @NonNull LocalTime time);

        void onCancel(@NonNull LocalTimePickerDialogFragment fragment);
    }
}
