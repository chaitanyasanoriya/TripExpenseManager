package com.techlove.tripexpensemanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class UpdateBugActivity extends AppCompatActivity {
    TextView tv1,tv2;
    EditText et1;
    String val;
    int val1;
    String name = "Guest";
    String phone = "GuestID";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Intent intent7 = getIntent();
        name = intent7.getStringExtra("name");
        phone = intent7.getStringExtra("phone");
        if(name.equals("Guest"))
        {
            phone = "GuestID";
        }
        SQLiteDatabase db = openOrCreateDatabase("ExpenseManager", MODE_APPEND, null);
        db.execSQL("create table if not exists setting(sid1 INTEGER PRIMARY KEY AUTOINCREMENT,th INTEGER,we INTEGER,trp VARCHAR(255),phone VARCHAR(255),name VARCHAR(255),sid INTEGER,gubug BIGINT)");
        Cursor c1 = db.rawQuery("select * from setting",null);
        if(c1.getCount()>0){}
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_bug);
        tv1 = (TextView) findViewById(R.id.textView_up_2);
        tv2 = (TextView) findViewById(R.id.textView_up_3);
        tv1.append(" "+name);
        tv2.append(" "+phone);
        et1 = (EditText) findViewById(R.id.editText_up_1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void save2(View view)
    {
        val = et1.getText().toString();
        val1 = Integer.parseInt(val);
        SQLiteDatabase db = openOrCreateDatabase("ExpenseManager", MODE_APPEND, null);
        if(name.equals("Guest"))
        {
            db.execSQL("create table if not exists setting(sid1 INTEGER PRIMARY KEY AUTOINCREMENT,th INTEGER,we INTEGER,trp VARCHAR(255),phone VARCHAR(255),name VARCHAR(255),sid INTEGER,gubug BIGINT)");
            Cursor c8 = db.rawQuery("select * from setting where name='Guest'",null);
            c8.moveToLast();
            int a = (int) c8.getLong(0);
            c8.close();
            db.execSQL("UPDATE setting set gubug='"+val1+"' where sid1='"+a+"'");
        }
        else
        {
            db.execSQL("create table if not exists users(name varchar(255),email varchar(255),password varchar(255),phone varchar(255),budget varchar(100),saving varchar(255))");
            db.execSQL("UPDATE users set budget='"+val+"' where name='"+name+"' and phone='"+phone+"'");
        }
        finish();
    }
}
