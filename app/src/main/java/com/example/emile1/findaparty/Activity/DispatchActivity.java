package com.example.emile1.findaparty.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.parse.ParseUser;

public class DispatchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startActivity(new Intent(this, MainActivity.class));
//        // Check if there is current user info
//        if (ParseUser.getCurrentUser() != null) {
//            // Start an intent for the logged in activity
//            startActivity(new Intent(this, MainActivity.class));
//        } else {
//            // Start and intent for the logged out activity
//            startActivity(new Intent(this, Login.class));
//        }
    }

}
