package com.example.emile1.findaparty.Activity.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.parse.ParseUser;

public class DispatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (ParseUser.getCurrentUser() != null) {
            // Start an intent for the logged in activity
            Intent i = new Intent(getApplicationContext(),MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        } else {
            // Start and intent for the logged out activity
            Intent i = new Intent(getApplicationContext(),Login.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(i);
            finish();
        }
    }

}
