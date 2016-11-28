package net.xpece.android.app;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import net.xpece.android.R;
import net.xpece.android.content.XpContext;
import net.xpece.android.widget.XpTimePicker;

import org.threeten.bp.LocalTime;

/**
 * Created by Eugen on 06.05.2016.
 */
public class LocalTimeBpPickerDialogFragment extends AppCompatDialogFragment implements
    DialogInterface.OnClickListener {
    public static final String TAG = LocalTimeBpPickerDialogFragment.class.getSimpleName();

    private TimePicker mTimePicker;
    private LocalTime mTime;
    private boolean mIs24HourFormat;

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
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final Context context = builder.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.dialog_time_picker, null, false);

        final TimePicker timePicker = (TimePicker) view.findViewById(R.id.timePicker);
        timePicker.setIs24HourView(mIs24HourFormat);
        onCreateTimePicker(timePicker);

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

    protected void onCreateTimePicker(TimePicker timePicker) {
        Context context = timePicker.getContext();
        XpTimePicker.setSelectionDividerTint(timePicker, XpContext.resolveColorStateList(context, R.attr.colorControlNormal));
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
