package com.example.emile1.findaparty.Activity.Activity;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.emile1.findaparty.Activity.Lan;
import com.example.emile1.findaparty.Activity.Participant;
import com.example.emile1.findaparty.R;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class ParticipantProfileActivity extends AppCompatActivity {
    private String id;
    private String name;
    private CollapsingToolbarLayout collapsingToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_profile);

        Intent intent= getIntent();
        id = intent.getStringExtra("ParticipantId");
        getParticipantProfile();

        collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);


        final Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar_participant_profile);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getParticipantProfile(){
        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.getInBackground(id, new GetCallback<ParseUser>() {
            @Override
            public void done(ParseUser parseUser, ParseException e) {
                if(e==null){
                    String lastName = parseUser.getString("lastName");
                    String firstLetter = lastName.substring(0,1);
                    collapsingToolbar.setTitle(parseUser.getString("firstName") + " "+ firstLetter);
                }else{
                    Log.i("Error",e.getMessage());
                }
            }
        });
    }
}
