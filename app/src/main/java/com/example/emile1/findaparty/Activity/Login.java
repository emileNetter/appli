package com.example.emile1.findaparty.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.text.TextUtils;
import android.widget.Toast;

import com.example.emile1.findaparty.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

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
        Button password =(Button) findViewById(R.id.btn_retrieve_password);

        setTextChanged();
        password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(),Password.class);
                startActivity(i);
            }
        });
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
        Boolean error = false;

        //Validate login data
        if(TextUtils.isEmpty(email)){
            error=true;
            emailLayout.setErrorEnabled(true);
            emailLayout.setError("You must enter an email");
        }
        if(!isValidEmail(email)){
            error = true;
            emailLayout.setErrorEnabled(true);
            emailLayout.setError("Wrong email format");
        }
        if(TextUtils.isEmpty(password)){
            error = true;
            passwordLayout.setErrorEnabled(true);
            passwordLayout.setError("You must enter a password");
        }
        if (!error){

            if(!isOnline()){
                Toast.makeText(Login.this, "Check your internet connection and try again", Toast.LENGTH_SHORT).show();
            } else {
                // Set up a progress dialog
                final ProgressDialog dialog = new ProgressDialog(Login.this);
                dialog.setMessage(getString(R.string.progress_login));
                dialog.show();

                ParseUser.logInInBackground(email, password, new LogInCallback() {
                    @Override
                    public void done(ParseUser user, ParseException e) {
                        dialog.dismiss();
                        if (e != null) {
                            loginUnSuccessful();
                        } else {
                            // Start an intent for the dispatch activity
                            Intent intent = new Intent(Login.this, DispatchActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }
                });
            }
        }
    }

    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    public void loginUnSuccessful() {
        // TODO Auto-generated method stub
        Toast.makeText(getApplicationContext(), "Email or Password is invalid", Toast.LENGTH_SHORT).show();

    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo!=null && netInfo.isConnected();
    }
}


