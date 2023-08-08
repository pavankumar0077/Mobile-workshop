package com.example.myrandomgen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    Button btn;
    TextView generateNo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Random myRandom= new Random();

        btn= (Button)findViewById(R.id.btnGen);
        final TextView generateNo=(TextView) findViewById(R.id.genNo);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generateNo.setText(String.valueOf(myRandom.nextInt(1000)));
            }
        });
    }
}