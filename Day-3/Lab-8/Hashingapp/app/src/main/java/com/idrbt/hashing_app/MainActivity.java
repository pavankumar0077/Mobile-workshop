package com.idrbt.hashing_app;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    private EditText inputEditText;
    private EditText decryptEditText;
    private TextView encryptionResultTextView;
    private TextView decryptionResultTextView;
    private TextView hashingResultTextView;
    private TextView encryptedValueTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputEditText = findViewById(R.id.inputEditText);
        decryptEditText = findViewById(R.id.decryptEditText);
        encryptedValueTextView = findViewById(R.id.encryptedValueTextView);
        Button encryptButton = findViewById(R.id.encryptButton);
        Button decryptButton = findViewById(R.id.decryptButton);
        Button hashButton = findViewById(R.id.hashButton);

        encryptionResultTextView = findViewById(R.id.encryptionResult);
        decryptionResultTextView = findViewById(R.id.decryptionResult);
        hashingResultTextView = findViewById(R.id.hashingResult);

        // Using a hardcoded key for demonstration purposes.
        String encryptionKey = "ThisIsASecretKey";

        // Set click listeners for buttons
        encryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = inputEditText.getText().toString();
                if (inputText.length() != 10) {
                    encryptionResultTextView.setText("Input text must be 10 characters long.");
                    return;
                }

                String encryptedText = performEncryption(inputText);
                encryptionResultTextView.setText("Encrypted Value: " + encryptedText);
                copyToClipboard(encryptedText);
            }
        });

        decryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String encryptedText = decryptEditText.getText().toString();
                if (encryptedText.isEmpty()) {
                    decryptionResultTextView.setText("Please fill the encrypted value field.");
                    return;
                }

                String decryptedText = performDecryption(encryptedText);
                if (decryptedText.equals("Decryption Failed")) {
                    decryptionResultTextView.setText("Decryption Failed. Invalid encrypted value.");
                } else {
                    decryptionResultTextView.setText("Decrypted: " + decryptedText);
                    copyToClipboard(decryptedText);
                }
            }
        });


        hashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputText = inputEditText.getText().toString();
                if (inputText.isEmpty()) {
                    hashingResultTextView.setText("Please fill the text in the field.");
                    return;
                } else if (inputText.length() != 10) {
                    hashingResultTextView.setText("Input text must be 10 characters long.");
                    return;
                }

                String hashedText = performHashing(inputText);
                hashingResultTextView.setText("Hashed: " + hashedText);
                copyToClipboard(hashedText); // Copy hashed value to clipboard
            }
        });

        // Set long-click listeners for copy functionality
        encryptionResultTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String encryptedText = encryptionResultTextView.getText().toString();
                if (!encryptedText.isEmpty() && encryptedText.startsWith("Encrypted: ")) {
                    copyToClipboard(encryptedText.substring(11));
                }
                return true;
            }
        });

        decryptionResultTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String decryptedText = decryptionResultTextView.getText().toString();
                if (!decryptedText.isEmpty() && decryptedText.startsWith("Decrypted: ")) {
                    copyToClipboard(decryptedText.substring(11));
                }
                return true;
            }
        });

        hashingResultTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String hashedText = hashingResultTextView.getText().toString();
                if (!hashedText.isEmpty() && hashedText.startsWith("Hashed: ")) {
                    copyToClipboard(hashedText.substring(8));
                }
                return true;
            }
        });
    }

    private String performEncryption(String text) {
        try {
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] key = sha256.digest(text.getBytes());
            Key secretKey = new SecretKeySpec(key, "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] encryptedBytes = cipher.doFinal(text.getBytes());

            return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return "Encryption Failed";
        }
    }

    private String performDecryption(String encryptedText) {
        try {
            String inputText = decryptEditText.getText().toString();
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            byte[] key = sha256.digest(inputText.getBytes());
            Key secretKey = new SecretKeySpec(key, "AES");

            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] encryptedBytes = Base64.decode(encryptedText, Base64.DEFAULT);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

            return new String(decryptedBytes);
        } catch (Exception e) {
            e.printStackTrace();
            return "Decryption Failed";
        }
    }



    private String performHashing(String text) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = messageDigest.digest(text.getBytes(StandardCharsets.UTF_8));

            return Base64.encodeToString(hashBytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return "Hashing Failed";
        }
    }

    private void copyToClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("Copied Text", text);
        clipboard.setPrimaryClip(clip);
        Toast.makeText(MainActivity.this, "Copied to clipboard", Toast.LENGTH_SHORT).show();
    }
}