package com.techlove.tripexpensemanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    String str[] = {"Light Theme", "Dark Theme"};
    String str1[] = {"Monday", "Sunday"};
    Spinner sp1, sp2;
    Integer st,st1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        sp1 = (Spinner) findViewById(R.id.spinner_set_1);
        ArrayAdapter adapter = new ArrayAdapter(SettingActivity.this, android.R.layout.simple_spinner_item, str);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adapter);

        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                st=i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(SettingActivity.this, "Nothing Selected", Toast.LENGTH_SHORT).show();
            }
        });//end of spinner Listener statement
        sp2 = (Spinner) findViewById(R.id.spinner_set_2);
        ArrayAdapter adapter1 = new ArrayAdapter(SettingActivity.this, android.R.layout.simple_spinner_item, str1);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(adapter1);

        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                st1 = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                Toast.makeText(SettingActivity.this, "Nothing Selected", Toast.LENGTH_SHORT).show();
            }
        });//end of spinner 2 Listener Statement
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

    public void about(View v)
    {
        Intent intent = new Intent(SettingActivity.this, AboutUsActivity.class);
        startActivity(intent);
    }

    public void save(View v)
    {
        Intent intent = getIntent();
        String strt = intent.getStringExtra("name");
        String strt1 = intent.getStringExtra("phone");
        String strt2 = intent.getStringExtra("trp");
        SQLiteDatabase db = openOrCreateDatabase("ExpenseManager", MODE_APPEND, null);
        db.execSQL("create table if not exists setting(sid1 INTEGER PRIMARY KEY AUTOINCREMENT,th INTEGER,we INTEGER,trp VARCHAR(255),phone VARCHAR(255),name VARCHAR(255),sid INTEGER,gubug BIGINT)");
        String q = "insert into setting(th,we,trp,phone,name) values('"+st+"','"+st1+"','"+strt2+"','"+strt1+"','"+strt+"')";
        db.execSQL(q);
        String q1 = "select * from setting";
        Cursor c = db.rawQuery(q1,null);
        int a = c.getCount();
        if(a>0)
        {
            Toast.makeText(SettingActivity.this,"Settings Saved", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(SettingActivity.this,"Unable to Save Settings", Toast.LENGTH_SHORT).show();
        }
        c.close();
    }
}
