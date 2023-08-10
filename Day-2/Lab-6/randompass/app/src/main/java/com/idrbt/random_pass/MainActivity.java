package com.idrbt.random_pass;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import java.security.SecureRandom;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private Button generatePasswordButton, generateOTPButton, generateKeyButton;
    private EditText resultEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        generatePasswordButton = findViewById(R.id.generatePasswordButton);
        generateOTPButton = findViewById(R.id.generateOTPButton);
        generateKeyButton = findViewById(R.id.generateKeyButton);
        resultEditText = findViewById(R.id.resultEditText);

        generatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = generatePassword();
                resultEditText.setText(password);
            }
        });

        generateOTPButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String otp = generateOTP();
                resultEditText.setText(otp);
            }
        });

        generateKeyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = generateKey();
                resultEditText.setText(key);
            }
        });
    }

    private String generatePassword() {
        String upperChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerChars = "abcdefghijklmnopqrstuvwxyz";
        String numberChars = "0123456789";
        String specialChars = "!@#$%^&*()-_=+[]{}|;:,.<>?";

        String allChars = upperChars + lowerChars + numberChars + specialChars;

        Random random = new SecureRandom();
        StringBuilder password = new StringBuilder();

        for (int i = 0; i < 12; i++) {
            int randomIndex = random.nextInt(allChars.length());
            char randomChar = allChars.charAt(randomIndex);
            password.append(randomChar);
        }

        return password.toString();
    }


    private String generateOTP() {
        Random random = new SecureRandom();
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int randomDigit = random.nextInt(10);
            otp.append(randomDigit);
        }

        return otp.toString();
    }

    private String generateKey() {
        byte[] keyBytes = new byte[16];
        new SecureRandom().nextBytes(keyBytes);

        StringBuilder key = new StringBuilder();
        for (byte b : keyBytes) {
            key.append(String.format("%02X", b));
        }

        return key.toString();
    }
}
