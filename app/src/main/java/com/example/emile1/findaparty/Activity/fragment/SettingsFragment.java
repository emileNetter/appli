package com.example.emile1.findaparty.Activity.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emile1.findaparty.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;

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
    private TextView profile_name;

    private Toolbar toolbar;


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
        ParseUser user = ParseUser.getCurrentUser();
        user.fetchInBackground();
        String firstName = ParseUser.getCurrentUser().get("firstName").toString();
        String lastName = ParseUser.getCurrentUser().get("lastName").toString();
        instantiateUI(v);

        toolbar.setTitle(getString(R.string.toolbar_title_settings));
        profile_name.setText(firstName + " "+ lastName);
        firstname.setText(firstName);
        lastname.setText(lastName);
        email.setText(ParseUser.getCurrentUser().get("email").toString());
        getData();

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInfo();
            }
        });

        return  v;
    }

    private void instantiateUI(View v){
        toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        firstname = (EditText) v.findViewById(R.id.firstname_profile_edittext);
        lastname = (EditText) v.findViewById(R.id.lastname_profile_edittext);
        email = (EditText) v.findViewById(R.id.email_profile_edittext);
        lane = (EditText) v.findViewById(R.id.address_profile_edittext);
        zipcode = (EditText) v.findViewById(R.id.zipcode_profile_edittext);
        city = (EditText) v.findViewById(R.id.city_profile_edittext);
        state = (EditText)v.findViewById(R.id.state_profile_edittext);
        save_button = (Button) v.findViewById(R.id.profile_save);
        profile_name = (TextView)v.findViewById(R.id.profile_name_settings_textview);
    }
    public void saveUserInfo(){
        final String mLane = lane.getText().toString();
        final String mZipCode = zipcode.getText().toString();
        final String mCity = city.getText().toString();
        final String mState = state.getText().toString();
        final String mFirstName = firstname.getText().toString();
        final String mLastName = lastname.getText().toString();
        final String mEmail = email.getText().toString();

        ParseUser user = ParseUser.getCurrentUser();
        JSONObject address = new JSONObject();
        try{
            address.put("lane",mLane);
            address.put("zipcode",mZipCode);
            address.put("city",mCity);
            address.put("state",mState);
        } catch (JSONException j){
            Toast.makeText(getContext(),"Error : " + j.toString(),Toast.LENGTH_SHORT).show();
        }
        user.put("address", address);
        user.setEmail(mEmail);
        user.setUsername(mEmail);
        user.put("firstName",mFirstName);
        user.put("lastName",mLastName);
        user.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if(e==null){
                    Toast.makeText(getContext(),"Saved Successfully",Toast.LENGTH_SHORT).show();
                } else{
                    Toast.makeText(getContext(),"Error : " +e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void getData(){
        String json = ParseUser.getCurrentUser().getJSONObject("address").toString();
        if(json!=null){
            try{
                JSONObject data = new JSONObject(json);
                lane.setText(data.getString("lane"));
                zipcode.setText(data.getString("zipcode"));
                city.setText(data.getString("city"));
                state.setText(data.getString("state"));
            }catch (JSONException j){
                Toast.makeText(getContext(),"Error : "+j.toString(),Toast.LENGTH_SHORT).show();
            }
        }
    }
}
