package com.example.emile1.findaparty.Activity;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.format.DateFormat;

import android.util.Log;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.emile1.findaparty.R;

import java.util.Calendar;

/**
 * Created by Emile1 on 07/02/2017.
 */

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener {

    private int mId;
    private TimePickerDialogListener mListener;
    public EditText startEditText;
    public EditText endEditText;

    public static TimePickerFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putInt("picker_id", id);
        TimePickerFragment fragment = new TimePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override public Dialog onCreateDialog(Bundle savedInstanceState) {
        // ... omitted
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        mId = getArguments().getInt("picker_id");
        mListener = getActivity() instanceof TimePickerDialogListener ? (TimePickerDialogListener) getActivity() : null;

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,DateFormat.is24HourFormat(getActivity()));
    }

     public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
         startEditText = (EditText) getActivity().findViewById(R.id.startsEditText);
         endEditText = (EditText) getActivity().findViewById(R.id.endsEditText);
        if (mListener != null) mListener.onTimeSet(mId, view, hourOfDay, minute);
         switch (mId){
             case 0 : startEditText.setText(hourOfDay + " : " + minute);
                 break;
             case 1 : endEditText.setText(hourOfDay + " : " + minute);
                 break;
         }
    }

    public  interface TimePickerDialogListener {
        void onTimeSet(int id, TimePicker view, int hourOfDay, int minute);
    }
}
