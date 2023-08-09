package com.idrbt.basic_banking_ops;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText amountEditText;
    private EditText interestRateEditText;
    private EditText durationEditText;
    private Button depositButton;
    private Button withdrawButton;
    private Button interestButton;


    private double accountBalance = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        amountEditText = findViewById(R.id.amountEditText);
        interestRateEditText = findViewById(R.id.interestRateEditText);
        durationEditText = findViewById(R.id.durationEditText);
        depositButton = findViewById(R.id.depositButton);
        withdrawButton = findViewById(R.id.withdrawButton);
        interestButton = findViewById(R.id.interestButton);

        depositButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amountStr = amountEditText.getText().toString();
                if (!amountStr.isEmpty()) {
                    double depositAmount = Double.parseDouble(amountStr);
                    accountBalance += depositAmount;
                    showBalance();
                }
            }
        });

        withdrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String amountStr = amountEditText.getText().toString();
                if (!amountStr.isEmpty()) {
                    double withdrawAmount = Double.parseDouble(amountStr);
                    if (withdrawAmount <= accountBalance) {
                        accountBalance -= withdrawAmount;
                        showBalance();
                    } else {
                        Toast.makeText(MainActivity.this, "Insufficient funds", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        interestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateInterest();
            }
        });

    }



    private void calculateInterest() {
        String interestRateStr = interestRateEditText.getText().toString();
        String durationStr = durationEditText.getText().toString();

        if (!interestRateStr.isEmpty() && !durationStr.isEmpty()) {
            double interestRate = Double.parseDouble(interestRateStr);
            double duration = Double.parseDouble(durationStr);

            double interest = (accountBalance * interestRate * duration) / 100.0;
            accountBalance += interest;

            showBalance();
        } else {
            Toast.makeText(MainActivity.this, "Please enter valid interest rate and duration", Toast.LENGTH_SHORT).show();
        }
    }


    private void showBalance() {
        // Create a custom Toast view
        View toastView = getLayoutInflater().inflate(R.layout.toast_layout, null);
        TextView toastText = toastView.findViewById(R.id.toastText);
        ImageView toastIcon = toastView.findViewById(R.id.toastIcon);

        // Set the Toast text and icon
        toastText.setText("Account Balance: " + accountBalance);
        Drawable icon = getResources().getDrawable(R.drawable.ic_toast_icon, null);
        toastIcon.setImageDrawable(icon);

        // Show the custom Toast for 10 seconds
        final Toast customToast = new Toast(getApplicationContext());
        customToast.setView(toastView);
        customToast.setDuration(Toast.LENGTH_LONG);

        // Set gravity and offset to position the Toast
        customToast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 2 * getWindowManager().getDefaultDisplay().getHeight() / 3);

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                customToast.cancel(); // Hide the Toast after 10 seconds
            }
        }, 10000); // 10000 milliseconds = 10 seconds

        customToast.show();
    }

}
