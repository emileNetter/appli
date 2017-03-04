package com.example.emile1.findaparty.Activity;

import android.app.Application;
import android.os.Bundle;

import com.example.emile1.findaparty.R;
import com.parse.Parse;
import com.parse.ParseObject;
import com.parse.ParseUser;

public class InitializeApplication extends Application {

    @Override
    public void onCreate() {
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId(getString(R.string.app_id))
                .clientKey(getString(R.string.client_key))
                .server("https://find-a-lan.herokuapp.com/parse") // The trailing slash is important.
                .build());
        ParseUser.enableRevocableSessionInBackground();
    }
}
