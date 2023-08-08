package com.example.sqlitedatabasedemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHandler extends SQLiteOpenHelper {
    public DBHandler(Context context) {
        super(context, "Customerdata.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("create Table Customer(Name TEXT primary key,Gender TEXT,Age TEXT,Emailid TEXT,Contact TEXT,Address TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("DROP TABLE IF EXISTS Customer");
    }

    public Boolean insertcustomer(String name,String gender,String age,String email,String contact,String address)
    {
        SQLiteDatabase DB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("Name",name);
        contentValues.put("Gender",gender);
        contentValues.put("Age",age);
        contentValues.put("Emailid",email);
        contentValues.put("Contact",contact);
        contentValues.put("Address",address);
        long result= DB.insert("Customer",null,contentValues);
        if(result==-1)
        {return false;}
        else{
             return true;
        }

    }

    public Boolean updatecustomer(String name,String gender,String age,String email,String contact,String address) {
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Gender", gender);
        contentValues.put("Age", age);
        contentValues.put("Emailid", email);
        contentValues.put("Contact", contact);
        contentValues.put("Address", address);
        Cursor cursor = DB.rawQuery("Select * from Customer where Name=?", new String[]{name});
        if (cursor.getCount() > 0) {

            long result = DB.update("Customer", contentValues, "Name=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }

    }

    public Boolean deletedata(String name) {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Customer where Name=?", new String[]{name});
        if (cursor.getCount() > 0) {

            long result = DB.delete("Customer",  "Name=?", new String[]{name});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        }else{
            return false;
        }

    }

    public Cursor getdata() {
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor cursor = DB.rawQuery("Select * from Customer ", null);
        return cursor;


    }
}
