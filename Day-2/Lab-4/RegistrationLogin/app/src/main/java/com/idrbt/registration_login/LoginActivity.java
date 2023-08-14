package com.idrbt.registration_login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private EditText loginUsernameEditText;
    private EditText loginPasswordEditText;
    private TextView accountDetailsTextView;

    private List<BankAccount> bankAccounts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginUsernameEditText = findViewById(R.id.loginUsernameEditText);
        loginPasswordEditText = findViewById(R.id.loginPasswordEditText);
        accountDetailsTextView = findViewById(R.id.accountDetailsTextView);

        loginUsernameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validateUsername();
                }
            }
        });

        loginPasswordEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    validatePassword();
                }
            }
        });

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {
        String username = loginUsernameEditText.getText().toString().trim();
        String password = loginPasswordEditText.getText().toString();

        if (!isValidUsername(username)) {
            loginUsernameEditText.setError("Invalid username format.");
            return;
        }

        if (!isValidPassword(password)) {
            loginPasswordEditText.setError("Invalid password format.");
            return;
        }

        // Hard-coded bank account details for example
        bankAccounts = new ArrayList<>();
        bankAccounts.add(new BankAccount("123456789", 5000.00));
        bankAccounts.add(new BankAccount("987654321", 10000.00));
        bankAccounts.add(new BankAccount("567890123", 750.50));

        // Display bank account details
        StringBuilder accountDetails = new StringBuilder("Bank Account Details:\n\n");
        for (BankAccount account : bankAccounts) {
            accountDetails.append("Account Number: ").append(account.getAccountNumber()).append("\n")
                    .append("Balance: ").append(account.getBalance()).append("\n\n");
        }
        accountDetailsTextView.setText(accountDetails.toString());
    }

    private boolean isValidUsername(String username) {
        // Username validation logic (modify as needed)
        return username.matches("^(?=.*[a-z])(?=.*[0-9])[a-z0-9]+$");
    }

    private boolean isValidPassword(String password) {
        // Password validation logic (modify as needed)
        return password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");
    }

    private void validateUsername() {
        String username = loginUsernameEditText.getText().toString().trim();
        if (!username.matches("^(?=.*[a-z])(?=.*[0-9])[a-z0-9]+$")) {
            loginUsernameEditText.setError("Username must contain at least one small letter and one digit (0-9).");
        } else {
            loginUsernameEditText.setError(null);
        }
    }

    private void validatePassword() {
        String password = loginPasswordEditText.getText().toString();
        if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")) {
            loginPasswordEditText.setError("Password must contain small and capital letters, a number, and a symbol.");
        } else {
            loginPasswordEditText.setError(null);
        }
    }

    private class BankAccount {
        private String accountNumber;
        private double balance;

        public BankAccount(String accountNumber, double balance) {
            this.accountNumber = accountNumber;
            this.balance = balance;
        }

        public String getAccountNumber() {
            return accountNumber;
        }

        public double getBalance() {
            return balance;
        }
    }
}
