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
import android.widget.DatePicker;

import net.xpece.android.R;
import net.xpece.android.content.res.XpResources;
import net.xpece.android.widget.XpDatePicker;

import org.threeten.bp.LocalDate;

/**
 * Created by Eugen on 06.05.2016.
 */
public class LocalDateBpPickerDialogFragment extends AppCompatDialogFragment implements
    DialogInterface.OnClickListener {
    public static final String TAG = LocalDateBpPickerDialogFragment.class.getSimpleName();

    private DatePicker mDatePicker;
    private LocalDate mDate;

    public static LocalDateBpPickerDialogFragment newInstance(LocalDate date) {
        Bundle args = new Bundle();
        args.putSerializable("mDate", date);
        LocalDateBpPickerDialogFragment fragment = new LocalDateBpPickerDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public LocalDateBpPickerDialogFragment() {
        setRetainInstance(true);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle args = getArguments();
        if (args != null) {
            mDate = (LocalDate) args.getSerializable("mDate");
        }
    }

    @Override
    public void onDestroyView() {
        mDatePicker = null;
        if (getDialog() != null && getRetainInstance())
            getDialog().setOnDismissListener(null);
        super.onDestroyView();
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mDate = (LocalDate) savedInstanceState.getSerializable("mDate");
        }

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        final Context context = builder.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view = inflater.inflate(R.layout.dialog_date_picker, null, false);

        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        XpDatePicker.setSelectionDividerTint(datePicker, XpResources.resolveColorStateList(context, R.attr.colorControlNormal));
        LocalDate date = mDate;
        datePicker.updateDate(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
        mDatePicker = datePicker;

        builder.setView(view);
        builder.setPositiveButton(android.R.string.ok, this);
        builder.setNegativeButton(android.R.string.cancel, this);
        return builder.create();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LocalDate date = getLocalDate();
        outState.putSerializable("mDate", date);
    }

    @NonNull
    private LocalDate getLocalDate() {
        final DatePicker datePicker = mDatePicker;
        return LocalDate.of(datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth());
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
            LocalDate date = getLocalDate();
            callbacks.onDateSelected(date);
        }
    }

    public interface Callbacks {
        void onDateSelected(LocalDate date);
    }
}
