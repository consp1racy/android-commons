package net.xpece.android.dialog.threeten.app;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

import net.xpece.android.content.XpResources;
import net.xpece.android.dialog.threeten.R;
import net.xpece.android.picker.widget.XpDatePicker;

import java.time.LocalDate;
import java.time.ZoneId;

public class LocalDatePickerDialogFragment extends AppCompatDialogFragment implements
        DialogInterface.OnClickListener {

    public static final String TAG = LocalDatePickerDialogFragment.class.getSimpleName();

    private DatePicker mDatePicker;
    private LocalDate mDate;
    private LocalDate mMaxDate;
    private LocalDate mMinDate;

    private boolean mForceLegacy;

    @StyleRes
    private static int resolveDialogTheme(@NonNull final Context context, @StyleRes final int themeResId) {
        if (Build.VERSION.SDK_INT >= 21 && themeResId == 0) {
            final TypedValue outValue = new TypedValue();
            context.getTheme().resolveAttribute(R.attr.dialogTheme, outValue, true);
            return outValue.resourceId;
        } else {
            return themeResId;
        }
    }

    @NonNull
    public static LocalDatePickerDialogFragment newInstance(@Nullable final LocalDate date) {
        return newInstance(date, null, null);
    }

    @NonNull
    public static LocalDatePickerDialogFragment newInstanceWithMax(@Nullable final LocalDate date, @Nullable final LocalDate max) {
        return newInstance(date, null, max);
    }

    @NonNull
    public static LocalDatePickerDialogFragment newInstanceWithMin(@Nullable final LocalDate date, @Nullable final LocalDate min) {
        return newInstance(date, min, null);
    }

    @NonNull
    public static LocalDatePickerDialogFragment newInstance(@Nullable final LocalDate date, @Nullable final LocalDate min, @Nullable final LocalDate max) {
        Bundle args = new Bundle();
        args.putSerializable("mDate", date);
        args.putSerializable("mMinDate", min);
        args.putSerializable("mMaxDate", max);
        LocalDatePickerDialogFragment fragment = new LocalDatePickerDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    public LocalDatePickerDialogFragment forceLegacy(final boolean forceLegacy) {
        mForceLegacy = forceLegacy;
        return this;
    }

    public LocalDatePickerDialogFragment() {
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
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setOnDismissListener(null);
        }
        super.onDestroyView();
    }

    @Override
    @NonNull
    @SuppressLint("InflateParams")
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mDate = (LocalDate) savedInstanceState.getSerializable("mDate");
        }

        Context context = requireContext();
        final AlertDialog.Builder builder = new AlertDialog.Builder(context, resolveDialogTheme(context, getTheme()));
        context = builder.getContext();

        final LayoutInflater inflater = LayoutInflater.from(context);
        final View view;
        if (mForceLegacy) {
            view = inflater.inflate(R.layout.dialog_date_picker_legacy, null, false);
        } else {
            view = inflater.inflate(R.layout.dialog_date_picker, null, false);
        }

        final DatePicker datePicker = view.findViewById(R.id.datePicker);
        onBindDatePicker(datePicker);

        final LocalDate date = mDate;
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

    @SuppressWarnings("WeakerAccess")
    protected void onBindDatePicker(@NonNull final DatePicker datePicker) {
        if (Build.VERSION.SDK_INT < 21) {
            final Context context = datePicker.getContext();
            final ColorStateList color = XpResources.resolveColorStateList(context, R.attr.colorControlNormal);
            XpDatePicker.setSelectionDividerTintCompat(datePicker, color);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull final Bundle outState) {
        super.onSaveInstanceState(outState);
        final LocalDate date = getLocalDate();
        outState.putSerializable("mDate", date);
    }

    @NonNull
    private LocalDate getLocalDate() {
        final DatePicker datePicker = mDatePicker;
        return LocalDate.of(datePicker.getYear(), datePicker.getMonth() + 1, datePicker.getDayOfMonth());
    }

    @Override
    public void onClick(@NonNull final DialogInterface dialog, final int which) {
        if (which == DialogInterface.BUTTON_POSITIVE) {
            final Callbacks callbacks = getCallbacks();
            final LocalDate date = getLocalDate();
            callbacks.onDateSelected(this, date);
        } else if (which == DialogInterface.BUTTON_NEGATIVE) {
            onCancel(dialog);
        }
    }

    @Override
    public void onCancel(@NonNull final DialogInterface dialog) {
        getCallbacks().onCancel(this);
    }

    @NonNull
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
        void onDateSelected(@NonNull LocalDatePickerDialogFragment fragment, @NonNull LocalDate date);

        void onCancel(@NonNull LocalDatePickerDialogFragment fragment);
    }
}