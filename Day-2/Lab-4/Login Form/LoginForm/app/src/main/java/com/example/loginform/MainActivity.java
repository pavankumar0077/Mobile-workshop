package com.example.loginform;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.text.method.PasswordTransformationMethod;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;



public class MainActivity extends AppCompatActivity {
    //Initialize Variables
    EditText name,pwd,rpwd;
    Button sub,show;
    Boolean ischar=false;
    AwesomeValidation awesomeValidation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Assign variables
        name=findViewById(R.id.name);
        pwd=findViewById(R.id.enter_password);
        rpwd=findViewById(R.id.confirm_password);
        sub=findViewById(R.id.submit);
        show=findViewById(R.id.showHideBtn);
        //Initialize validation Style
        awesomeValidation =new AwesomeValidation(ValidationStyle.BASIC);
        //Add Validation For Name
        awesomeValidation.addValidation(this,R.id.name, RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        //Add Validation For Password
        awesomeValidation.addValidation(this,R.id.enter_password,".{9,}",R.string.invalid_pwd);
        //Add Validation For Renter_password
        awesomeValidation.addValidation(this,R.id.confirm_password,".{9,}",R.string.invalid_rpwd);
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(show.getText().toString().equals("Show")){
                    pwd.setTransformationMethod(null);
                    show.setText("Hide");
                } else{
                    pwd.setTransformationMethod(new PasswordTransformationMethod());
                    show.setText("Show");
                }
            }
        });

        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Check Validation
                if (awesomeValidation.validate()){
                    //On Success
                    String pswd=pwd.getText().toString();
                    String confpswd=rpwd.getText().toString();
                    if(pswd.matches("(.*[A-Z].*)") && pswd.matches("^(?=.*[_.()$&@]).*$") && pswd.matches("(.*[0-9].*)") )
                    {
                        ischar = true;
                        if(!pswd.equals(confpswd)){
                            Toast.makeText(getApplicationContext(),"Make sure that ur pwd matches",Toast.LENGTH_SHORT).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Validation successful & pwd matches" +" Welcome "+name.getText().toString()+ "!!!  ur pwd is "+pwd.getText().toString(),Toast.LENGTH_SHORT).show();

                        }


                    }else{
                        ischar = false;
                        Toast.makeText(getApplicationContext(),"make sure to follow pwd requirements",Toast.LENGTH_SHORT).show();
                    }

                } else{
                    Toast.makeText(getApplicationContext(),"Validation Failed",Toast.LENGTH_SHORT).show();                }           }
        });    }}
