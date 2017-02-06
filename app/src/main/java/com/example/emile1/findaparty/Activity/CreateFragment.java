package com.example.emile1.findaparty.Activity;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.emile1.findaparty.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText dateEditText;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public CreateFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CreateFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CreateFragment newInstance() {
        CreateFragment fragment = new CreateFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create, container, false);
        dateEditText =(EditText)v.findViewById(R.id.dateEditText);
        showDatePicker();
        return v;
    }

    public void showDatePicker() {
        final DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedyear, int selectedmonth, int selectedDay) {
                    //String myFormat = "dd/MM/yy"; //In which you need put here
                    int mmonth = selectedmonth+1;
                    String mdate = selectedDay+"/"+mmonth+"/"+selectedyear;
                    //SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);
                    dateEditText.setText(mdate);
            }
        };
        dateEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                final Calendar c = Calendar.getInstance();
                final int mYear = c.get(Calendar.YEAR);
                final int mMonth = c.get(Calendar.MONTH);
                final int mDay = c.get(Calendar.DAY_OF_MONTH);
                final DatePickerDialog mDatePicker=new DatePickerDialog(getActivity(),R.style.MyDatepicker, listener,mYear, mMonth, mDay);
                mDatePicker.getDatePicker().setMinDate(new Date().getTime());
                mDatePicker.show();
            }
        });
    }
}
