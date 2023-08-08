package com.example.question3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

public class MainActivity extends AppCompatActivity
{
    EditText efName,emName,elName,egen,epEmail,eaEmail,epMobile,eaMobile,erAddress,ewAddress;
    Button bsub;
    AwesomeValidation awesomeValidation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        efName = findViewById(R.id.first_name);
        emName = findViewById(R.id.Middle_name);
        elName = findViewById(R.id.Last_name);
        egen = findViewById(R.id.gender1);
        epEmail = findViewById(R.id.primaryid);
        eaEmail = findViewById(R.id.alternativeid);
        epMobile = findViewById(R.id.Primary_number);
        eaMobile = findViewById(R.id.Alternative_Number);
        erAddress = findViewById(R.id.Residence);
        ewAddress = findViewById(R.id.office);
        bsub = findViewById(R.id.button);


        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        awesomeValidation.addValidation(this,R.id.first_name, RegexTemplate.NOT_EMPTY,R.string.invalid_fname);
        awesomeValidation.addValidation(this,R.id.Last_name, RegexTemplate.NOT_EMPTY,R.string.invalid_lname);
        
        awesomeValidation.addValidation(this,R.id.gender1, RegexTemplate.NOT_EMPTY,R.string.invalidgender);
        awesomeValidation.addValidation(this,R.id.Primary_number, "[5-9]{1}[0-9]{9}$",R.string.invalidpmobile);
        awesomeValidation.addValidation(this,R.id.Alternative_Number, "[5-9]{1}[0-9]{9}$",R.string.invalid_mobile);

        awesomeValidation.addValidation(this,R.id.primaryid, Patterns.EMAIL_ADDRESS,R.string.invalid_email);
        awesomeValidation.addValidation(this,R.id.alternativeid, Patterns.EMAIL_ADDRESS,R.string.invalid_aemail);

        awesomeValidation.addValidation(this,R.id.Residence, RegexTemplate.NOT_EMPTY,R.string.invalid_aname);
        


        bsub.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if (awesomeValidation.validate())
                {
                    Toast.makeText(getApplicationContext()
                            , "Form Validation Successfully...", Toast.LENGTH_SHORT).show();
                } else
                {
                    Toast.makeText(getApplicationContext(), "Validation Failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
