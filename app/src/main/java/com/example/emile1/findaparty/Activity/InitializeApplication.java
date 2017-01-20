package com.example.emile1.findaparty.Activity;

import android.app.Application;
import android.os.Bundle;

import com.parse.Parse;
import com.parse.ParseObject;

public class InitializeApplication extends Application {

    @Override
    public void onCreate() {
        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("110914")
                .clientKey("&emilenetter33!")
                .server("https://find-a-lan.herokuapp.com/parse") // The trailing slash is important.
                .build());
    }
}
