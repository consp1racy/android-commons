package net.xpece.android.app;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import net.xpece.android.R;
import net.xpece.android.content.res.XpResources;
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
        if (args != null) {
            mTime = (LocalTime) args.getSerializable("mTime");
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
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mTime = (LocalTime) savedInstanceState.getSerializable("mTime");
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final Context context = builder.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.dialog_time_picker, null, false);

        final TimePicker timePicker = (TimePicker) view.findViewById(R.id.timePicker);
        XpTimePicker.setSelectionDividerTint(timePicker, XpResources.resolveColorStateList(context, R.attr.colorControlNormal));
        LocalTime time = mTime;
        timePicker.setCurrentHour(time.getHour());
        timePicker.setCurrentMinute(time.getMinute());
        mTimePicker = timePicker;

        builder.setView(view);
        builder.setPositiveButton(android.R.string.ok, this);
        builder.setNegativeButton(android.R.string.cancel, this);
        return builder.create();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LocalTime time = getLocalTime();
        outState.putSerializable("mTime", time);
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
