package com.example.captcha_app;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.Random;

public class CaptchaGenerator {

    public static Bitmap generateCaptchaImage(String captchaText) {
        int width = 200;
        int height = 200;
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(Color.parseColor("#F0F0F0"));
        canvas.drawRect(0, 0, width, height, backgroundPaint);

        Paint textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(40);
        textPaint.setAntiAlias(true);

        Rect textBounds = new Rect();
        textPaint.getTextBounds(captchaText, 0, captchaText.length(), textBounds);

        int x = (width - textBounds.width()) / 2;
        int y = (height + textBounds.height()) / 2;

        canvas.drawText(captchaText, x, y, textPaint);

        return bitmap;
    }

    public static String generateRandomCaptchaText(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder captchaBuilder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            char randomChar = chars.charAt(random.nextInt(chars.length()));
            captchaBuilder.append(randomChar);
        }

        return captchaBuilder.toString();
    }
}
