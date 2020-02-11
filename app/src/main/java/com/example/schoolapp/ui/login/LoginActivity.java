package com.example.schoolapp.ui.login;

import android.app.Activity;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.schoolapp.AdminActivity;
import com.example.schoolapp.DatabaseHelp;
import com.example.schoolapp.MainActivity;
import com.example.schoolapp.R;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    DatabaseHelp databaseHelp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = findViewById(R.id.login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    loginViewModel.login(usernameEditText.getText().toString(),
                            passwordEditText.getText().toString());
                }
                return false;
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingProgressBar.setVisibility(View.VISIBLE);
                loginViewModel.login(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
                String userName = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                if(userName.equalsIgnoreCase("Admin") && password.equalsIgnoreCase("123456") ){
                    Intent RegistrationActivity = new Intent(LoginActivity.this, AdminActivity.class);
                    startActivity(RegistrationActivity);
                }
                else {
                    try{
                        HashMap<String, String> hashMap = new HashMap<>();
                        hashMap.putAll(databaseHelp.getStudent(userName,password));
                        if(hashMap.isEmpty()){
                            Toast.makeText(getApplicationContext(),"You are  not registered", Toast.LENGTH_SHORT);
                        } else{
                            if (hashMap.get("role").equalsIgnoreCase("student")) {
                                if(userName.equalsIgnoreCase(hashMap.get("regNumb")) && password.equalsIgnoreCase(hashMap.get("password")) ) {
                                    Intent RegistrationActivity = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(RegistrationActivity);
                                } else {
                                    Toast.makeText(getApplicationContext(),"Wrong password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                if(userName.equalsIgnoreCase(hashMap.get("regNumb")) && password.equalsIgnoreCase(hashMap.get("password")) ) {
                                    Intent RegistrationActivity = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(RegistrationActivity);
                                } else {
                                    Toast.makeText(getApplicationContext(),"Wrong password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }
                    }catch (Exception e){
                        Toast.makeText(getApplicationContext(), "The login crash", Toast.LENGTH_SHORT).show();
                    }


                }

                }


        });
    }

    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}
