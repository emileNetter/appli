package com.example.emile1.findaparty.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.emile1.findaparty.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.RequestPasswordResetCallback;

public class Password extends AppCompatActivity {
    private EditText etEmail;
    private ParseUser currentUser;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        currentUser = ParseUser.getCurrentUser();
        Button sendEmail = (Button) findViewById(R.id.btnSendEmail);
        etEmail = (EditText) findViewById(R.id.etPasswordReset);
        email = etEmail.getText().toString();
        Toast.makeText(Password.this,currentUser.getEmail(),Toast.LENGTH_LONG).show();

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPasswordReset();
            }
        });
    }

    public void requestPasswordReset(){
        if(currentUser.getEmail().equals(email) && currentUser != null){
            Toast.makeText(Password.this,"Please check your email address",Toast.LENGTH_LONG).show();
        } else {
            ParseUser.requestPasswordResetInBackground(email, new RequestPasswordResetCallback() {
                public void done(ParseException e) {
                    if (e == null) {
                        Toast.makeText(Password.this,"An email has been sent to your email address",Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(Password.this,e.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }
}


