package com.example.schoolapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
//import android.support.design.widget.TextInputLayout;
//import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.regex.Pattern;

public class UserLoginActivity extends AppCompatActivity {
    DatabaseHelp databaseHelp;

    EditText textInputUsername;
    EditText textInputPassword;
    Button loginBtn;

    private static final Pattern PASSWORD_PATTERN=
            Pattern.compile("^" +
                    "(?=.*[0-9])" +
                    "(?=.*[a-zA-Z])" +
                    "(?=\\S+$)" +
                    ".{6,}" +
                    "$");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        databaseHelp = new DatabaseHelp(this);

        textInputUsername = (EditText) findViewById(R.id.input_username);
        textInputPassword = (EditText) findViewById(R.id.input_password);
        loginBtn = (Button) findViewById(R.id.loginBtn);

        textInputUsername.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validateUsername();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(validateUsername()){
                    if(validatePassword()){
                        loginBtn.setEnabled(true);
                    }else loginBtn.setEnabled(false);
                } else loginBtn.setEnabled(false);
            }
        });
        textInputPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                validatePassword();
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(validateUsername()){
                    if(validatePassword()){
                        loginBtn.setEnabled(true);
                    }else loginBtn.setEnabled(false);
                } else loginBtn.setEnabled(false);
            }
        });

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = textInputUsername.getText().toString();
                String password = textInputPassword.getText().toString();

                if(userName.equalsIgnoreCase("Admin") && password.equalsIgnoreCase("123456") ){
                    Intent RegistrationActivity = new Intent(UserLoginActivity.this, AdminActivity.class);
                    startActivity(RegistrationActivity);
                }
                else {
                    try{
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.putAll(databaseHelp.getStudent(userName,password));
                        if(hashMap.isEmpty()){
                            hashMap.putAll(databaseHelp.getStaff(userName,password));
                            if (hashMap.isEmpty()){
                                Toast.makeText(getApplicationContext(),"You are  not registered", Toast.LENGTH_SHORT);
                            } else {
                                if(userName.equalsIgnoreCase(hashMap.get("regNumb")) && password.equalsIgnoreCase(hashMap.get("password")) ) {
                                    Intent staffActivity = new Intent(UserLoginActivity.this, StaffActivity.class);
                                    startActivity(staffActivity);
                                } else {
                                    Toast.makeText(getApplicationContext(),"Wrong password", Toast.LENGTH_SHORT).show();
                                }

                            }


                        } else{

                            if(userName.equalsIgnoreCase(hashMap.get("regNumb")) && password.equalsIgnoreCase(hashMap.get("password"))  ) {
                                Intent studentActivity = new Intent(UserLoginActivity.this, MainActivity.class);
                                startActivity(studentActivity);
                            } else {
                                Toast.makeText(getApplicationContext(),"Wrong password", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "The login crash", Toast.LENGTH_SHORT).show();
                    }


                }
            }
        });
    }





    private boolean validateUsername() {

        String usernameInput = textInputUsername.getText().toString().trim();

        if (usernameInput.isEmpty()) {
            textInputUsername.setError("failed,can't be emty");
            return false;
        } else if (usernameInput.length() < 3) {
            textInputUsername.setError("Please Enter valid name");
            return false;

        } else {
            textInputUsername.setError(null);
            return true;
        }
    }
    private boolean validatePassword() {

        String passwordInput = textInputPassword.getText().toString().trim();

        if (passwordInput.isEmpty()) {
            textInputPassword.setError("failed,can't be emty");
            return false;
        } else if(passwordInput.length() < 4){
            textInputPassword.setError("Weak password");
            return false;
        }else {
            textInputPassword.setError(null);
            return true;
        }
    }

}
