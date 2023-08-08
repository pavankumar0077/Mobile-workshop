package com.example.sqlitedatabasedemo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText name,gender,age,contact,address,email;
    Button insert,update,delete,show;
    DBHandler dbh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=findViewById(R.id.first_name);
        gender=findViewById(R.id.gen);
        age=findViewById(R.id.AgeId);
        contact=findViewById(R.id.Mobile_Number);
        address=findViewById(R.id.Residence);
        email=findViewById(R.id.primaryid);

        insert=findViewById(R.id.btinsert);
        update=findViewById(R.id.btupdate);
        delete=findViewById(R.id.btdelete);
        show=findViewById(R.id.btshow);
        dbh=new DBHandler(this);

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT= name.getText().toString();
                String genTXT=gender.getText().toString();
                String ageTXT=age.getText().toString();
                String contactTXT=contact.getText().toString();
                String addTXT=address.getText().toString();
                String emailTXT=email.getText().toString();

                Boolean checkinsertdata= dbh.insertcustomer(nameTXT,genTXT,ageTXT,contactTXT,addTXT,emailTXT );
                if(checkinsertdata==true)
                    Toast.makeText(MainActivity.this,"New Record Inserted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "New Record not inserted",Toast.LENGTH_SHORT).show();


            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT= name.getText().toString();
                String genTXT=gender.getText().toString();
                String ageTXT=age.getText().toString();
                String contactTXT=contact.getText().toString();
                String addTXT=address.getText().toString();
                String emailTXT=email.getText().toString();

                Boolean checkupdatedata= dbh.updatecustomer(nameTXT,genTXT,ageTXT,contactTXT,addTXT,emailTXT );
                if(checkupdatedata==true)
                    Toast.makeText(MainActivity.this,"Record Updated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Record not Updated",Toast.LENGTH_SHORT).show();


            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT= name.getText().toString();

                Boolean checkdeletedata= dbh.deletedata(nameTXT);
                if(checkdeletedata==true)
                    Toast.makeText(MainActivity.this,"Record Deleted", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(MainActivity.this, "Record not Deleted",Toast.LENGTH_SHORT).show();


            }
        });
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res =dbh.getdata();
                if(res.getCount()==0){
                    Toast.makeText(MainActivity.this, "No record exists",Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer buffer=new StringBuffer();
                while(res.moveToNext()){
                    buffer.append("Nominee Name :"+ res.getString(0)+"\n");
                    buffer.append("Customer Name :"+ res.getString(1)+"\n");
                    buffer.append("Relationship:"+ res.getString(2)+"\n");
                }

                AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Customer records");
                builder.setMessage(buffer.toString());
                builder.show();
            }
        });





    }
}