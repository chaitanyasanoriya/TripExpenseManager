package com.techlove.tripexpensemanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    EditText et1, et2, et3, et4, et5,et6,et7;
    String str1, str2,str3,str4,str5,str6,str7;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        et1 = (EditText) findViewById(R.id.editText_sign_1);//email
        et2 = (EditText) findViewById(R.id.editText_sign_2);//password
        et3 = (EditText) findViewById(R.id.editText_sign_3);//re password
        et4 = (EditText) findViewById(R.id.editText_sign_4);//phone number
        et5 = (EditText) findViewById(R.id.editText_sign_5);//name
        et6 = (EditText) findViewById(R.id.editText_sign_6);//monthly budget
        et7 = (EditText) findViewById(R.id.editText_sign_7);//Old Savings
    }

    public void signup1(View v)
    {
        intent = getIntent();
        str1 = et1.getText().toString();//email
        str2 = et2.getText().toString();//password
        str3 = et3.getText().toString();//re password
        str4 = et4.getText().toString();//phone
        str5 = et5.getText().toString();//name
        str6 = et6.getText().toString();//monthly budget
        str7 = et7.getText().toString();//Old Savings
        if (!str2.equals(str3) || str2.length()<6)
        {
            if(!str2.equals(str3))
            {
                Toast.makeText(SignUpActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            }

            else
            {
                Toast.makeText(SignUpActivity.this, "Password is too Short", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            SQLiteDatabase db = openOrCreateDatabase("ExpenseManager", MODE_APPEND, null);
            db.execSQL("create table if not exists users(name varchar(255),email varchar(255),password varchar(255),phone varchar(255),budget varchar(100),saving varchar(255))");
            String str = "Select * from users where name='" + str1 + "' or phone='" + str4 + "'";
            Cursor c = db.rawQuery(str, null);
            int a = c.getCount();
            c.close();
            if(a>0)
            {
                Toast.makeText(SignUpActivity.this, "Id with Same Email Address or Phone Number Already Exists", Toast.LENGTH_LONG).show();
            }
            else
            {
                db.execSQL("insert into users(name,email,password,phone,budget,saving) values('"+str5+"','"+str1+"','"+str2+"','"+str4+"','"+str6+"','"+str7+"')");
                str="select * from users where name='"+str5+"' and email='"+str1+"'";
                c = db.rawQuery(str,null);
                a = c.getCount();
                if(a>0)
                {
                    Toast.makeText(SignUpActivity.this,"Account Created", Toast.LENGTH_SHORT).show();
                    SQLiteDatabase db1 = openOrCreateDatabase("ExpenseManager", MODE_APPEND, null);
                    String st = "create table if not exists "+str5+"_"+str4+"(sid INTEGER PRIMARY KEY AUTOINCREMENT,dat VARCHAR(255),amt BIGINT(100),whr VARCHAR(255),com VARCHAR(255),cate VARCHAR(255),by1 VARCHAR(255))";
                    String st1 = "create table if not exists "+str5+"_"+str4+"_trip(sid1 INTEGER PRIMARY KEY AUTOINCREMENT,sid INTEGER,dat VARCHAR(255),strtdat VARCHAR(255),enddat VARCHAR(255),amt BIGINT(100),whr VARCHAR(255),com VARCHAR(255),cate VARCHAR(255),by1 VARCHAR(255),trpbudget INTEGER)";
                    db1.execSQL(st);
                    db1.execSQL(st1);
                    intent.putExtra("name",str5);
                    intent.putExtra("phone",str4);
                    setResult(2,intent);
                    c.close();
                    finish();
                }
                else
                {
                    Toast.makeText(SignUpActivity.this, "Unable to Create Account", Toast.LENGTH_LONG).show();
                    c.close();
                }
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
}
