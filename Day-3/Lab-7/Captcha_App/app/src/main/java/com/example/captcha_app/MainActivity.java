package com.example.captcha_app;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView captchaImageView;
    private EditText captchaEditText;
    private Button verifyButton;
    private TextView resultTextView;

    private String generatedCaptcha;
    private String hiddenText = "\uD83D\uDCBB IDRBT-MOBILE-WORKSHOP \uD83D\uDCF1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        captchaImageView = findViewById(R.id.captchaImageView);
        captchaEditText = findViewById(R.id.captchaEditText);
        verifyButton = findViewById(R.id.verifyButton);
        resultTextView = findViewById(R.id.resultTextView);

        generateCaptcha();
        updateCaptchaImage();

        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyCaptcha();
            }
        });
    }

    private void generateCaptcha() {
        generatedCaptcha = generateRandomCaptcha();
    }

    private String generateRandomCaptcha() {
        Random random = new Random();
        StringBuilder captcha = new StringBuilder();
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789"; // Include letters and digits
        int charactersLength = characters.length();

        for (int i = 0; i < 6; i++) {
            char randomChar = characters.charAt(random.nextInt(charactersLength));
            captcha.append(randomChar);
        }
        return captcha.toString();
    }




    private void updateCaptchaImage() {
        Bitmap captchaBitmap = generateCaptchaImage(generatedCaptcha);
        captchaImageView.setImageBitmap(captchaBitmap);
    }

    private Bitmap generateCaptchaImage(String captchaText) {
        int width = 400;
        int height = 150;

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        // Set background color
        canvas.drawColor(Color.WHITE);

        // Set text color, size, and style
        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(60);
        textPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        // Draw the captcha text
        float xPos = (canvas.getWidth() - textPaint.measureText(captchaText)) / 2;
        float yPos = (canvas.getHeight() / 2) - ((textPaint.descent() + textPaint.ascent()) / 2);
        canvas.drawText(captchaText, xPos, yPos, textPaint);

        return bitmap;
    }


    private void verifyCaptcha() {
        String enteredCaptcha = captchaEditText.getText().toString().toUpperCase();
        if (enteredCaptcha.equals(generatedCaptcha)) {
            resultTextView.setText(hiddenText);
            resultTextView.setTextColor(getResources().getColor(android.R.color.white));
        } else {
            resultTextView.setText("CAPTCHA is incorrect");
            resultTextView.setTextColor(getResources().getColor(android.R.color.holo_red_dark));
        }
    }
}