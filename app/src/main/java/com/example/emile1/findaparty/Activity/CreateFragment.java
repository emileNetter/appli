package com.example.emile1.findaparty.Activity;


import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.app.ListFragment;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BaseTransientBottomBar;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.emile1.findaparty.R;
import com.parse.DeleteCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CreateFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CreateFragment extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final int START_TIME = 0;
    private static final int END_TIME = 1;
    private JSONObject address;


    private EditText startEditText;
    private EditText endEditText;
    private EditText nbrPeopleEditText;
    private EditText dateEditText;
    private Button createButton;
    private RelativeLayout relativeLayout;
    private android.support.v4.app.FragmentTransaction ft;

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
        address = ParseUser.getCurrentUser().getJSONObject("address");

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_create, container, false);
        instantiateUI(v);
        showDatePicker();
        try{
            if(address.getString("lane").isEmpty()
                    || address.getString("zipcode").isEmpty()
                    || address.getString("city").isEmpty()
                    || address.getString("state").isEmpty()){
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(getContext());
                builder.setTitle(getString(R.string.dialog_info_title));
                builder.setMessage(getString(R.string.dialog_info_message));
                builder.setPositiveButton(
                        R.string.dialog_button_settings_ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                SettingsFragment settingsFragment = SettingsFragment.newInstance();
                                ft = getFragmentManager().beginTransaction();
                                ft.replace(R.id.content_main, settingsFragment);
                                ft.commit();

                            }
                        });
                builder.setNegativeButton(
                        R.string.dialog_button_settings_back,
                        new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                HomeFragment homeFragment = HomeFragment.newInstance();
                                ft = getFragmentManager().beginTransaction();
                                ft.replace(R.id.content_main, homeFragment);
                                ft.commit();
                            }
                        }
                );
                android.support.v7.app.AlertDialog alert1 = builder.create();
                alert1.show();
            }
        }catch(JSONException e){
            Log.i("CRASH",e.getMessage());
        }
        startEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputStartTime();
            }
        });
        endEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputEndTime();
            }
        });
        nbrPeopleEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    showNumbersList();
            }
        });
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createLan();
            }
        });

        return v;
    }

    // show a clickable dialog to pick a number
    private void showNumbersList(){
        final String numbers[] = getResources().getStringArray(R.array.Numbers);
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
        dialog.setTitle(getString(R.string.dialog_title));
        dialog.setItems(numbers, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                nbrPeopleEditText.setText(numbers[i]);
            }
        });
        dialog.setNegativeButton(getString(R.string.cancel_button), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog alert = dialog.create();
        alert.show();
    }

    private void inputStartTime() {
        DialogFragment newFragment = TimePickerFragment.newInstance(START_TIME);
        newFragment.show(getActivity().getFragmentManager(), "timePicker");
    }

    private void inputEndTime() {
        DialogFragment newFragment = TimePickerFragment.newInstance(END_TIME);
        newFragment.show(getActivity().getFragmentManager(), "timePicker1");
    }

    private void showDatePicker() {
        final DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int selectedyear, int selectedmonth, int selectedDay) {
                    String mdate = checkDigit(selectedDay)+"/"+checkDigit(selectedmonth+1)+"/"+checkDigit(selectedyear);
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

    private void instantiateUI(View v){
        dateEditText =(EditText)v.findViewById(R.id.dateEditText);
        startEditText = (EditText) v.findViewById(R.id.startsEditText);
        endEditText = (EditText) v.findViewById(R.id.endsEditText);
        createButton = (Button) v.findViewById(R.id.createButton);
        nbrPeopleEditText = (EditText) v.findViewById(R.id.nbrPeopleEditText);
        relativeLayout = (RelativeLayout) v.findViewById(R.id.relativeLayout);
        dateEditText.addTextChangedListener(textWatcher);
        startEditText.addTextChangedListener(textWatcher);
        endEditText.addTextChangedListener(textWatcher);
        nbrPeopleEditText.addTextChangedListener(textWatcher);
    }

    //add a 0 before a day or month with only 1 digit
    private String checkDigit(int number)
    {
        return number<=9?"0"+number:String.valueOf(number);
    }

    //textwatcher to check when text is entered
    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3)
        {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
            checkFieldsForEmptyValues();
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if(checkHours()){
                final Snackbar snackbar = Snackbar.make(relativeLayout,getString(R.string.snackbar_text),BaseTransientBottomBar.LENGTH_INDEFINITE);
                View snackbar_view = snackbar.getView();
                snackbar_view.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.snackbar_warning));
                snackbar.setActionTextColor(Color.WHITE);
                snackbar.setAction(getString(R.string.snackbar_dismiss), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        snackbar.dismiss();
                    }
                });
                snackbar.show();
            }
        }
    };

    //enable / disable button if info is missing
    private void checkFieldsForEmptyValues(){
        String e1 = dateEditText.getText().toString().trim();
        String e2 = startEditText.getText().toString().trim();
        String e3 = endEditText.getText().toString().trim();
        String e4 = nbrPeopleEditText.getText().toString().trim();
        if(e1.isEmpty()|| e2.isEmpty()||
                e3.isEmpty()|| e4.isEmpty() || checkHours()){
            createButton.setEnabled(false);
        }else{
            createButton.setEnabled(true);
        }
    }

    //checks whereas the end time is before or after the start time
    private boolean checkHours(){
        boolean error = false;
        String timeStart = startEditText.getText().toString();
        String timeEnd = endEditText.getText().toString();
        if(!TextUtils.isEmpty(timeStart)&& !TextUtils.isEmpty(timeEnd)){
            if(timeStart.compareTo(timeEnd)>-1){
                error=true;
            }
        }
        return error;
    }

    //Create a Lan and add it in the Database
    private void createLan(){
        ParseUser currentUser = ParseUser.getCurrentUser();
        JSONObject address = ParseUser.getCurrentUser().getJSONObject("address");
        String firstName = currentUser.getString("firstName");
        String lastName = currentUser.getString("lastName");
        String date = dateEditText.getText().toString();

        String startTime = startEditText.getText().toString();
        String endTime = endEditText.getText().toString();
        int maxPeople = Integer.parseInt(nbrPeopleEditText.getText().toString());

        final ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage(getString(R.string.progress_dialog_create));
        dialog.show();

        ParseObject lan = new ParseObject("Lans");
        lan.put("IdOwner",ParseUser.getCurrentUser().getObjectId());
        lan.put("Owner", firstName + " " + lastName);
        lan.put("Address", "");
        lan.put("Date",date);
        lan.put("Start",startTime);
        lan.put("End",endTime);
        lan.put("MaxPeople",maxPeople);
        lan.put("Participants",0);
        lan.put("address", address);
        lan.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                dialog.dismiss();
                if (e !=null){
                    //Error Message
                    Toast.makeText(getActivity(),
                            "There was an error : " +e.getMessage(),
                            Toast.LENGTH_LONG)
                            .show();
                } else {
                    Toast.makeText(getActivity(),"Lan created !",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
