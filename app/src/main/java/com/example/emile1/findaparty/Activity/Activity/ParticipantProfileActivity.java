package com.example.emile1.findaparty.Activity.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.emile1.findaparty.Activity.Lan;
import com.example.emile1.findaparty.Activity.Participant;
import com.example.emile1.findaparty.R;

public class ParticipantProfileActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_participant_profile);
        Intent intent= getIntent();
        id = intent.getStringExtra("ParticipantId");
        Log.i("ParticipantId",id);
        toolbar=(Toolbar)findViewById(R.id.toolbar_participant_profile);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
}
