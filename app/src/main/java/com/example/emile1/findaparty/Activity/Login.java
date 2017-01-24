package com.example.emile1.findaparty.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.text.TextUtils;

import com.example.emile1.findaparty.R;

import org.w3c.dom.Text;

public class Login extends AppCompatActivity {

    private EditText passwordEditText;
    private EditText emailEditText;
    private TextInputLayout emailLayout;
    private TextInputLayout passwordLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //set edit text and buttons
        emailEditText = (EditText) findViewById(R.id.etEmailLogin);
        passwordEditText = (EditText) findViewById(R.id.etPasswordLogin);
        emailLayout = (TextInputLayout) findViewById(R.id.emailLoginLayout);
        passwordLayout = (TextInputLayout) findViewById(R.id.passwordLoginLayout);

        Button register = (Button) findViewById(R.id.btn_registerL);
        Button login = (Button) findViewById(R.id.btn_login);

        setTextChanged();

        login.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                login();
            }

        });

        register.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Register.class);
                startActivity(i);
            }
        });
        }

    public void setTextChanged(){
        passwordEditText.addTextChangedListener(new CustomTextWatcher(passwordLayout));
        emailEditText.addTextChangedListener(new CustomTextWatcher(emailLayout));
    }

    public void login(){
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();

        //Validate login data
        if(email.length()==0){
            emailLayout.setErrorEnabled(true);
            emailLayout.setError("You must enter an email");
        }
        if(!isValidEmail(email)){
            emailLayout.setErrorEnabled(true);
            emailLayout.setError("Wrong email format");
        }
        if(password.length()==0){
            passwordLayout.setErrorEnabled(true);
            passwordLayout.setError("You must enter a password");
        }

    }

    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
    }


