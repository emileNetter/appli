package com.example.emile1.findaparty.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        Button sendEmail = (Button) findViewById(R.id.btnSendEmail);
        etEmail = (EditText) findViewById(R.id.etPasswordReset);

        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPasswordReset();
            }
        });
    }

    public void requestPasswordReset(){
        String email = etEmail.getText().toString();
        if (!isValidEmail(email)){
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
    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}