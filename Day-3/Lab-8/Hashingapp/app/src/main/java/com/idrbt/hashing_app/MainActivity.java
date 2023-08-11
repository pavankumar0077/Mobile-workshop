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
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    private EditText inputEditText;
    private TextView encryptionResultTextView;
    private TextView decryptionResultTextView;
    private TextView hashingResultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        inputEditText = findViewById(R.id.inputEditText);
        Button encryptButton = findViewById(R.id.encryptButton);
        Button decryptButton = findViewById(R.id.decryptButton);
        Button hashButton = findViewById(R.id.hashButton);
        encryptionResultTextView = findViewById(R.id.encryptionResult);
        decryptionResultTextView = findViewById(R.id.decryptionResult);
        hashingResultTextView = findViewById(R.id.hashingResult);

        // Set click listeners for buttons
        encryptButton.setOnClickListener(v -> performEncryption());
        decryptButton.setOnClickListener(v -> performDecryption());
        hashButton.setOnClickListener(v -> performHashing());

        // Set long-click listeners for copy functionality
        encryptionResultTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                copyToClipboard(encryptionResultTextView.getText().toString());
                return true;
            }
        });

        decryptionResultTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                copyToClipboard(decryptionResultTextView.getText().toString());
                return true;
            }
        });

        hashingResultTextView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                copyToClipboard(hashingResultTextView.getText().toString());
                return true;
            }
        });

    }

    private void performEncryption() {
        String inputText = inputEditText.getText().toString();
        if (inputText.length() != 10) {
            encryptionResultTextView.setText("Input text must be 10 characters long.");
            return;
        }

        String encryptedText = performAES256Encryption(inputText);
        encryptionResultTextView.setText("Encrypted Value: " + encryptedText);
        copyToClipboard(encryptedText);
    }

    private void performDecryption() {
        String encryptedText = encryptionResultTextView.getText().toString().substring(16); // Removing "Encrypted Value: "
        if (encryptedText.isEmpty()) {
            decryptionResultTextView.setText("Please encrypt a value first.");
            return;
        }

        String decryptedText = performAES256Decryption(encryptedText);
        decryptionResultTextView.setText("Decrypted: " + decryptedText);
        copyToClipboard(decryptedText);
    }

    private void performHashing() {
        String inputText = inputEditText.getText().toString();
        if (inputText.isEmpty()) {
            hashingResultTextView.setText("Please fill the text in the field.");
            return;
        } else if (inputText.length() != 10) {
            hashingResultTextView.setText("Input text must be 10 characters long.");
            return;
        }

        String hashedText = performSHA256Hashing(inputText);
        hashingResultTextView.setText("Hashed: " + hashedText);
        copyToClipboard(hashedText);
    }

    private String performAES256Encryption(String text) {
        try {
            String encryptionKey = "ThisIsASecretKey";
            byte[] keyBytes = encryptionKey.getBytes(StandardCharsets.UTF_8);
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            keyBytes = sha256.digest(keyBytes);
            byte[] iv = new byte[16]; // Initialization Vector
            SecureRandom random = new SecureRandom();
            random.nextBytes(iv);
            SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(iv));
            byte[] encryptedBytes = cipher.doFinal(text.getBytes(StandardCharsets.UTF_8));

            byte[] combinedIvCiphertext = new byte[iv.length + encryptedBytes.length];
            System.arraycopy(iv, 0, combinedIvCiphertext, 0, iv.length);
            System.arraycopy(encryptedBytes, 0, combinedIvCiphertext, iv.length, encryptedBytes.length);

            return Base64.encodeToString(combinedIvCiphertext, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
            return "Encryption Failed";
        }
    }

    private String performAES256Decryption(String encryptedText) {
        try {
            String encryptionKey = "ThisIsASecretKey";
            byte[] keyBytes = encryptionKey.getBytes(StandardCharsets.UTF_8);
            MessageDigest sha256 = MessageDigest.getInstance("SHA-256");
            keyBytes = sha256.digest(keyBytes);

            byte[] encryptedBytes = Base64.decode(encryptedText, Base64.DEFAULT);
            byte[] iv = new byte[16];
            System.arraycopy(encryptedBytes, 0, iv, 0, iv.length);
            byte[] ciphertext = new byte[encryptedBytes.length - iv.length];
            System.arraycopy(encryptedBytes, iv.length, ciphertext, 0, ciphertext.length);

            SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(iv));
            byte[] decryptedBytes = cipher.doFinal(ciphertext);

            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
            return "Decryption Failed";
        }
    }

    private String performSHA256Hashing(String text) {
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
