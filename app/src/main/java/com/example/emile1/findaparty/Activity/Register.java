package com.example.emile1.findaparty.Activity;

import android.app.DatePickerDialog;
import android.content.pm.ActivityInfo;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.emile1.findaparty.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Register extends AppCompatActivity  {

    private EditText passwordEditText;
    private EditText passwordAgainEditText;
    private EditText emailEditText;
    private EditText dateBirth;
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

    }

    public void setTextChangedEvents(){
        firstNameEditText.addTextChangedListener(new CustomTextWatcher(firstNameEditText,firstNameLayout));
        lastNameEditText.addTextChangedListener(new CustomTextWatcher(lastNameEditText,lastNameLayout));
        emailEditText.addTextChangedListener(new CustomTextWatcher(emailEditText,emailLayout));
        passwordEditText.addTextChangedListener(new CustomTextWatcher(passwordEditText,passwordLayout));
        passwordAgainEditText.addTextChangedListener(new CustomTextWatcher(passwordAgainEditText,passwordAgainLayout));

    }

    // display a date picker when the edittext is clicked on
    public void showDatePicker() {
        dateBirth = (EditText)findViewById(R.id.etDate_of_birth);
        dateBirth.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                //To show current date in the datepicker
                final Calendar c = Calendar.getInstance();
                int mYear = c.get(Calendar.YEAR);
                int mMonth = c.get(Calendar.MONTH);
                int mDay = c.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker=new DatePickerDialog(Register.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        String myFormat = "dd/MM/yy"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.ENGLISH);

                        dateBirth.setText(sdf.format(c.getTime()));
                    }
                },mYear, mMonth, mDay);
                mDatePicker.setTitle("Select Date");
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

        // Validate the sign up data
        if (firstName.length()==0){
            firstNameLayout.setErrorEnabled(true);
            firstNameLayout.setError("Enter a first name");
        }
        if (lastName.length()==0){
            lastNameLayout.setErrorEnabled(true);
            lastNameLayout.setError("Enter a last name");
        }
        if (!isValidEmail(email)) {
            emailLayout.setErrorEnabled(true);
            emailLayout.setError("Wrong email format");
        }
        if (TextUtils.isEmpty(password) || password.length() < 5) {
            passwordLayout.setErrorEnabled(true);
            passwordLayout.setError("Password is too small");
        }
        if (!password.equals(passwordAgain)) {
            passwordAgainLayout.setErrorEnabled(true);
            passwordAgainLayout.setError("Your passwords don\'t match");

        }

    }

    public final static boolean isValidEmail(CharSequence target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

}


