package com.example.smsencrptandpt;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_SEND_SMS = 1;
    private EditText mMessageEditText;
    private EditText mPhoneNumberEditText;
    private Switch mEncryptionSwitch;
    private Button mSendEncryptedButton;
    private Button mSendPlainTextButton;
    private boolean mIsEncrypted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMessageEditText = findViewById(R.id.message_edittext);
        mPhoneNumberEditText = findViewById(R.id.phone_number_edittext);
        //mEncryptionSwitch = findViewById(R.id.encryption_switch);
        mSendEncryptedButton = findViewById(R.id.send_encrypted_button);
        mSendPlainTextButton = findViewById(R.id.send_plain_text_button);

        mSendEncryptedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsEncrypted = true;
                sendSMS();
            }
        });

        mSendPlainTextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsEncrypted = false;
                sendSMS();
            }
        });
    }

    private void sendSMS() {
        String message = mMessageEditText.getText().toString();
        String phoneNumber = mPhoneNumberEditText.getText().toString();
        if (!message.isEmpty() && !phoneNumber.isEmpty()) {
            if (checkSelfPermission(Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                SmsManager smsManager = SmsManager.getDefault();
                String encryptedMessage = mIsEncrypted ? encryptMessage(message) : message;
                ArrayList<String> messageParts = smsManager.divideMessage(encryptedMessage);
                smsManager.sendMultipartTextMessage(phoneNumber, null, messageParts, null, null);
                Toast.makeText(this, "SMS sent", Toast.LENGTH_SHORT).show();
            } else {
                requestPermissions(new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_SEND_SMS);
            }
        }
    }

    private String encryptMessage(String message) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(message.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String bytesToHex(byte[] hash) {
        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_SEND_SMS) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSMS();
            }
        }
    }
}
