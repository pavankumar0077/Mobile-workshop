package com.idrbt.registration_login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class RegistrationActivity extends AppCompatActivity {

    private EditText usernameEditText;
    private EditText passwordEditText;
    private EditText confirmPasswordEditText;
    private EditText mobileEditText;
    private RadioGroup genderRadioGroup;
    private EditText emailEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);
        mobileEditText = findViewById(R.id.mobileEditText);
        genderRadioGroup = findViewById(R.id.genderRadioGroup);
        emailEditText = findViewById(R.id.emailEditText);



        // Set OnFocusChangeListener for each EditText
        usernameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateUsername();
                }
            }
        });

        passwordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validatePassword();
                }
            }
        });

        confirmPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateConfirmPassword();
                }
            }
        });

        mobileEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateMobile();
                }
            }
        });

        emailEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateEmail();
                }
            }
        });

        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateForm()) {
                    registerUser();
                }
            }
        });
    }

    private void validateUsername() {
        String username = usernameEditText.getText().toString().trim();
        if (!username.matches("^(?=.*[a-z])(?=.*[0-9])[a-z0-9]+$")) {
            usernameEditText.setError("Username must contain at least one small letter and one digit (0-9).");
        } else {
            usernameEditText.setError(null);
        }
    }


    private void validatePassword() {
        String password = passwordEditText.getText().toString();
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            passwordEditText.setError("Password must contain small and capital letters, a number, and a symbol.");
        } else {
            passwordEditText.setError(null);
        }
    }

    private void validateConfirmPassword() {
        String confirmPassword = confirmPasswordEditText.getText().toString();
        if (!confirmPassword.equals(passwordEditText.getText().toString())) {
            confirmPasswordEditText.setError("Passwords do not match.");
        } else {
            confirmPasswordEditText.setError(null);
        }
    }

    private void validateMobile() {
        String mobile = mobileEditText.getText().toString().trim();

        if (!mobile.matches("^[6-9]\\d{9}$") && !mobile.matches("^\\+91[6-9]\\d{9}$")) {
            mobileEditText.setError("Invalid mobile number.");
        } else {
            mobileEditText.setError(null);
        }
    }


    private void validateEmail() {
        String email = emailEditText.getText().toString().trim();
        if (!email.matches("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,4}$")) {
            emailEditText.setError("Invalid email.");
        } else {
            emailEditText.setError(null);
        }
    }

    private boolean validateForm() {
        validateUsername();
        validatePassword();
        validateConfirmPassword();
        validateMobile();
        validateEmail();

        // Additional validation checks can be added here

        return usernameEditText.getError() == null &&
                passwordEditText.getError() == null &&
                confirmPasswordEditText.getError() == null &&
                mobileEditText.getError() == null &&
                emailEditText.getError() == null;
    }

    private void registerUser() {
        // Perform registration logic here

        showToast("Registration successful!");

        // Navigate to LoginActivity after successful registration
        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(intent);
        finish(); // Close the current activity
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }



}
