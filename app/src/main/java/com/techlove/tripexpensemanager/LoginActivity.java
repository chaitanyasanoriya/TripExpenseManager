package com.techlove.tripexpensemanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText et1,et2;
    String str1,str2;
    Intent intent,intent1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et1 = (EditText) findViewById(R.id.editText_login_1);
        et2 = (EditText) findViewById(R.id.editText_login_2);
    }

    public void login(View v)
    {
        String name1 = null;
        String phone1 = null;
        str1 = et1.getText().toString();
        str2 = et2.getText().toString();
        String str = "select * from users users where email='"+str1+"' and password='"+str2+"'";
        SQLiteDatabase db = openOrCreateDatabase("ExpenseManager", MODE_APPEND, null);
        db.execSQL("create table if not exists users(name varchar(255),email varchar(255),password varchar(255),phone varchar(255),budget varchar(100),saving varchar(255))");
        Cursor c = db.rawQuery(str,null);
        int a = c.getCount();
        if(a>0)
        {
            Toast.makeText(LoginActivity.this,"Login Successful", Toast.LENGTH_SHORT).show();
            intent1 = getIntent();
            while(c.moveToNext())
            {
                name1 = c.getString(0);
                phone1 = c.getString(3);
            }

            intent1.putExtra("phone",phone1);
            intent1.putExtra("name",name1);
            setResult(4,intent1);
            c.close();
            finish();
        }
        else
        {
            Toast.makeText(LoginActivity.this,"Email or Password Incorrect", Toast.LENGTH_SHORT).show();
        }
        c.close();
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

    public void signup(View v)
    {
        intent = new Intent(LoginActivity.this,SignUpActivity.class);
        startActivityForResult(intent,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        intent1 = getIntent();
        String name = null;
        String phone = null;
        super.onActivityResult(requestCode, resultCode, data);
        try
        {
            name = data.getStringExtra("name");
            phone = data.getStringExtra("phone");
        }
        catch (Exception e) {}
        try
        {
            if (!name.isEmpty() && !phone.isEmpty()) {
                intent1.putExtra("phone", phone);
                intent1.putExtra("name", name);
                setResult(4, intent1);
                finish();
            }
        }
        catch (Exception e1) {}
    }
}
