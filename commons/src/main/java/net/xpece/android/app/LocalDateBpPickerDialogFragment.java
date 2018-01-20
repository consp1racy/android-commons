package net.xpece.android.app;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import net.xpece.android.R;
import net.xpece.android.widget.XpDatePicker;

import org.threeten.bp.LocalDate;
import org.threeten.bp.ZoneId;

import static net.xpece.android.content.XpResources.resolveColorStateList;

/**
 * Created by Eugen on 06.05.2016.
 */
public class LocalDateBpPickerDialogFragment extends AppCompatDialogFragment implements
    DialogInterface.OnClickListener {
    public static final String TAG = LocalDateBpPickerDialogFragment.class.getSimpleName();

    private DatePicker mDatePicker;
    private LocalDate mDate;
    private LocalDate mMaxDate;
    private LocalDate mMinDate;

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

    public static LocalDateBpPickerDialogFragment newInstance(LocalDate date) {
        return newInstance(date, null, null);
    }

    public static LocalDateBpPickerDialogFragment newInstanceWithMax(LocalDate date, LocalDate max) {
        return newInstance(date, null, max);
    }

    public static LocalDateBpPickerDialogFragment newInstanceWithMin(LocalDate date, LocalDate min) {
        return newInstance(date, min, null);
    }

    public static LocalDateBpPickerDialogFragment newInstance(LocalDate date, LocalDate min, LocalDate max) {
        Bundle args = new Bundle();
        args.putSerializable("mDate", date);
        args.putSerializable("mMinDate", min);
        args.putSerializable("mMaxDate", max);
        LocalDateBpPickerDialogFragment fragment = new LocalDateBpPickerDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public LocalDateBpPickerDialogFragment forceLegacy(final boolean forceLegacy) {
        mForceLegacy = forceLegacy;
        return this;
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
            mMinDate = (LocalDate) args.getSerializable("mMinDate");
            mMaxDate = (LocalDate) args.getSerializable("mMaxDate");
        }
        if (savedInstanceState != null) {
            mForceLegacy = savedInstanceState.getBoolean("mForceLegacy");
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
    @SuppressLint("InflateParams")
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mDate = (LocalDate) savedInstanceState.getSerializable("mDate");
        }

        Context context = getContext();
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, resolveDialogTheme(context, getTheme()));
        context = builder.getContext();
        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view;
        if (mForceLegacy) {
            view = inflater.inflate(R.layout.dialog_date_picker_legacy, null, false);
        } else {
            view = inflater.inflate(R.layout.dialog_date_picker, null, false);
        }

        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.datePicker);
        onCreateDatePicker(datePicker);

        LocalDate date = mDate;
        if (date != null) {
            datePicker.updateDate(date.getYear(), date.getMonthValue() - 1, date.getDayOfMonth());
            if (mMinDate != null) {
                long min = mMinDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
                datePicker.setMinDate(min);
            }
            if (mMaxDate != null) {
                long max = mMaxDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
                datePicker.setMaxDate(max);
            }
        }

        mDatePicker = datePicker;

        builder.setView(view);
        builder.setPositiveButton(android.R.string.ok, this);
        builder.setNegativeButton(android.R.string.cancel, this);
        return builder.create();
    }

    protected void onCreateDatePicker(DatePicker datePicker) {
        Context context = datePicker.getContext();
        XpDatePicker.setSelectionDividerTint(datePicker, resolveColorStateList(context, R.attr.colorControlNormal));
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
            callbacks.onDateSelected(this, date);
        }
    }

    public interface Callbacks {
        void onDateSelected(LocalDateBpPickerDialogFragment fragment, LocalDate date);
    }
}
