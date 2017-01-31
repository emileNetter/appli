package com.example.emile1.findaparty.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.emile1.findaparty.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import static com.parse.ParseException.USERNAME_TAKEN;

public class Register extends AppCompatActivity  {

    private EditText passwordEditText;
    private EditText passwordAgainEditText;
    private EditText emailEditText;
    private EditText birthDateEditText;
    private EditText firstNameEditText;
    private EditText lastNameEditText;
    private Button register;
    private Button back;
    private TextInputLayout firstNameLayout;
    private TextInputLayout lastNameLayout;
    private TextInputLayout emailLayout;
    private TextInputLayout passwordLayout;
    private TextInputLayout passwordAgainLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //EditTexts
        firstNameEditText = (EditText) findViewById(R.id.etFirst_name);
        lastNameEditText =(EditText) findViewById(R.id.etLast_name);
        emailEditText =(EditText) findViewById(R.id.etEmail_register) ;
        passwordEditText = (EditText)findViewById(R.id.etPassword);
        passwordAgainEditText = (EditText) findViewById(R.id.etPasswordAgain);

        //Buttons
        back= (Button) findViewById(R.id.back_to_login);
        register = (Button) findViewById(R.id.btn_registerR) ;

        // TILs
        firstNameLayout = (TextInputLayout) findViewById(R.id.etFirstNameLayout);
        lastNameLayout = (TextInputLayout) findViewById(R.id.etLastNameLayout);
        emailLayout = (TextInputLayout) findViewById(R.id.etEmailLayout);
        passwordLayout =(TextInputLayout)findViewById(R.id.etPasswordLayout);
        passwordAgainLayout =(TextInputLayout) findViewById(R.id.etPasswordAgainLayout);

        showDatePicker();
        setTextChangedEvents();

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        passwordAgainEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    register();
                    return true;
                }
                return false;
            }
        });

    }

    //set all text listeners for the Edit Texts
    public void setTextChangedEvents(){
        firstNameEditText.addTextChangedListener(new CustomTextWatcher(firstNameLayout));
        lastNameEditText.addTextChangedListener(new CustomTextWatcher(lastNameLayout));
        emailEditText.addTextChangedListener(new CustomTextWatcher(emailLayout));
        passwordEditText.addTextChangedListener(new CustomTextWatcher(passwordLayout));
        passwordAgainEditText.addTextChangedListener(new CustomTextWatcher(passwordAgainLayout));

    }

    // display a date picker when the edittext is clicked on
    public void showDatePicker() {
        birthDateEditText = (EditText)findViewById(R.id.etDate_of_birth);
        birthDateEditText.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                final Calendar c = Calendar.getInstance();
                final int mYear = c.get(Calendar.YEAR);
                final int mMonth = c.get(Calendar.MONTH);
                final int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(Register.this,R.style.MyDatepicker, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedDay) {
                        String myFormat = "dd/MM/yy"; //In which you need put here
                        int mmonth = selectedmonth+1;
                        String mdate = selectedDay+"/"+mmonth+"/"+selectedyear;
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

                        birthDateEditText.setText(mdate);
                    }
                },mYear, mMonth, mDay);
                mDatePicker.show();  }
        });

    }

    // do all the verification before registering a new user
    public void register(){
        String email = emailEditText.getText().toString().trim();
        String firstName = firstNameEditText.getText().toString().trim();
        String lastName = lastNameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String passwordAgain = passwordAgainEditText.getText().toString().trim();
        String birthDate = birthDateEditText.getText().toString().trim();
        Boolean error = false;

        // Validate the sign up data
        if (firstName.length()==0){
            error=true;
            firstNameLayout.setErrorEnabled(true);
            firstNameLayout.setError("Enter a first name");
        }
        if (lastName.length()==0){
            error=true;
            lastNameLayout.setErrorEnabled(true);
            lastNameLayout.setError("Enter a last name");
        }
        if (!isValidEmail(email)) {
            error=true;
            emailLayout.setErrorEnabled(true);
            emailLayout.setError("Wrong email format");
        }
        if (TextUtils.isEmpty(password) || password.length() < 5) {
            error=true;
            passwordLayout.setErrorEnabled(true);
            passwordLayout.setError("Password is too small");
        }
        if (!password.equals(passwordAgain)) {
            error=true;
            passwordAgainLayout.setErrorEnabled(true);
            passwordAgainLayout.setError("Your passwords don\'t match");

        }

        // Set up a new Parse user
        ParseUser user = new ParseUser();
        user.setUsername(email);
        user.setPassword(password);
        user.setEmail(email);
        user.put("dateOfBirth",birthDate);
        user.put("firstName",firstName);
        user.put("lastName",lastName);

        // Call the Parse signup method
        if(!error){

            // Set up a progress dialog
            final ProgressDialog dialog = new ProgressDialog(Register.this);
            dialog.setMessage(getString(R.string.progress_signup));
            dialog.show();

            user.signUpInBackground(new SignUpCallback() {
                @Override
                public void done(ParseException e) {
                    dialog.dismiss();
                    if (e != null) {
                        // Show the error message
                        Toast.makeText(Register.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    } else {
                        // Start an intent for the dispatch activity
                        Intent intent = new Intent(Register.this, DispatchActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
            });
        }

    }

    public static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }
}


