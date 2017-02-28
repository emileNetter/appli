package com.example.emile1.findaparty.Activity;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.emile1.findaparty.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SettingsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SettingsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText firstname;
    private EditText lastname;
    private EditText email;
    private EditText lane;
    private EditText zipcode;
    private EditText city;
    private EditText state;
    private Button save_button;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public SettingsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SettingsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SettingsFragment newInstance() {
        SettingsFragment fragment = new SettingsFragment();
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        firstname = (EditText) v.findViewById(R.id.firstname_profile_edittext);
        lastname = (EditText) v.findViewById(R.id.lastname_profile_edittext);
        email = (EditText) v.findViewById(R.id.email_profile_edittext);
        lane = (EditText) v.findViewById(R.id.address_profile_edittext);
        zipcode = (EditText) v.findViewById(R.id.zipcode_profile_edittext);
        city = (EditText) v.findViewById(R.id.city_profile_edittext);
        state = (EditText)v.findViewById(R.id.state_profile_edittext);
        save_button = (Button) v.findViewById(R.id.profile_save);


        firstname.setText(ParseUser.getCurrentUser().get("firstName").toString());
        lastname.setText(ParseUser.getCurrentUser().get("lastName").toString());
        email.setText(ParseUser.getCurrentUser().get("email").toString());

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateUserInfo();
            }
        });

        return  v;
    }

    private void updateUserInfo(){
        ParseQuery<ParseObject> query = ParseQuery.getQuery("_User");
        String userId = ParseUser.getCurrentUser().getObjectId();
        final String mLane = lane.getText().toString();
        final int mZipCode = Integer.parseInt(zipcode.getText().toString());
        final String mCity = city.getText().toString();
        final String mState = state.getText().toString();

// Retrieve the object by id
        query.getInBackground(userId, new GetCallback<ParseObject>() {
            public void done(ParseObject user, ParseException e) {
                if (e == null) {
                    // Now let's update it with some new data. In this case, only cheatMode and score
                    // will get sent to the Parse Cloud. playerName hasn't changed.
                    try{
                        JSONObject address = new JSONObject();
                        address.put("lane",mLane);
                        address.put("zipcode",mZipCode);
                        address.put("city",mCity);
                        address.put("state",mState);
                        user.put("address", address);
                        user.saveInBackground();
                        Toast.makeText(getContext(),"SAved Successfully",Toast.LENGTH_SHORT).show();
                    } catch (JSONException j){
                        Toast.makeText(getContext(),"Error " + j,Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }

}
