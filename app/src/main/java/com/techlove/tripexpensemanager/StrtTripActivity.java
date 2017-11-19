package com.techlove.tripexpensemanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class StrtTripActivity extends AppCompatActivity {

    String dt1,dt3;
    DatePicker dt,dt2;
    EditText et1, et2;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strt_trip);
        dt = (DatePicker) findViewById(R.id.datePicker_strt_1);
        dt2 = (DatePicker) findViewById(R.id.datePicker_strt_2);
        et1 = (EditText) findViewById(R.id.editText_strt_1);//budget
        et2 = (EditText) findViewById(R.id.editText_strt_2);//id
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

    public void save1(View view)
    {
        dt1 = String.valueOf(dt.getDayOfMonth());
        int a = dt.getMonth()+1;
        dt1 = dt1+"-"+String.valueOf(a);
        a = dt.getYear();
        dt1=dt1+"-"+String.valueOf(a);

        dt3 = String.valueOf(dt2.getDayOfMonth());
        a = dt2.getMonth()+1;
        dt3 = dt3+"-"+String.valueOf(a);
        a = dt2.getYear();
        dt3=dt3+"-"+String.valueOf(a);
        intent = getIntent();
        String name = intent.getStringExtra("name");
        String phone = intent.getStringExtra("phone");
        String st1 = et1.getText().toString();//budget
        String st2 = et2.getText().toString();//id
        int bug = Integer.parseInt(st1);

        int id = Integer.parseInt(st2);

        SQLiteDatabase db1= openOrCreateDatabase("ExpenseManager", MODE_APPEND, null);
        if(phone.equals("Guest ID") && name.equals("Guest"))
        {
            db1.execSQL("Create table if not exists Guest_GuestID_trip(sid1 INTEGER PRIMARY KEY,sid INTEGER,dat VARCHAR(255),strtdat VARCHAR(255),enddat VARCHAR(255),amt BIGINT(100),whr VARCHAR(255),com VARCHAR(255),cate VARCHAR(255),by1 VARCHAR(255),trpbudget INTEGER)");
            Cursor cursor = db1.rawQuery("select * from Guest_GuestID_trip where sid='"+id+"'",null);
            int no1 = cursor.getCount();
            if(no1>0)
            {
                Toast.makeText(StrtTripActivity.this,"A trip with Same id Already exists", Toast.LENGTH_SHORT).show();
            }
            else
            {
                db1.execSQL("insert into Guest_GuestID_trip(sid,strtdat,enddat,trpbudget) values('"+id+"','"+dt1+"','"+dt3+"','"+bug+"')");
                cursor = db1.rawQuery("select * from Guest_GuestID_trip where sid='"+id+"'",null);
                int no2 = cursor.getCount();
                if(no2>0)
                {
                    Toast.makeText(StrtTripActivity.this,"Trip Saved", Toast.LENGTH_SHORT).show();
                    intent.putExtra("strdt",dt1);
                    intent.putExtra("enddt",dt3);
                    intent.putExtra("sid",String.valueOf(id));
                    setResult(10,intent);
                    finish();
                }
                else
                {
                    Toast.makeText(StrtTripActivity.this, "Unable to Save trip", Toast.LENGTH_SHORT).show();
                }
            }
        }
        else
        {
            db1.execSQL("Create table if not exists "+name+"_"+phone+"_trip(sid INTEGER PRIMARY KEY,dat VARCHAR(255),strtdat VARCHAR(255),enddat VARCHAR(255),amt BIGINT(100),whr VARCHAR(255),com VARCHAR(255),cate VARCHAR(255),by1 VARCHAR(255),trpbudget INTEGER)");
            Cursor cursor = db1.rawQuery("select * from "+name+"_"+phone+"_trip where sid='"+id+"'",null);
            int no1 = cursor.getCount();
            if(no1>0)
            {
                Toast.makeText(StrtTripActivity.this,"A trip with Same id Already exists", Toast.LENGTH_SHORT).show();
            }
            else
            {
                db1.execSQL("insert into "+name+"_"+phone+"_trip(sid,strtdat,enddat,trpbudget) values('"+id+"','"+dt1+"','"+dt3+"','"+bug+"')");
                cursor = db1.rawQuery("select * from "+name+"_"+phone+"_trip where sid='"+id+"'",null);
                int no2 = cursor.getCount();
                if(no2>0)
                {
                    Toast.makeText(StrtTripActivity.this,"Trip Saved", Toast.LENGTH_SHORT).show();
                    intent.putExtra("strdt",dt1);
                    intent.putExtra("enddt",dt3);
                    intent.putExtra("sid",id);
                    setResult(10,intent);
                    finish();
                }
                else
                {
                    Toast.makeText(StrtTripActivity.this, "Unable to Save trip", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
