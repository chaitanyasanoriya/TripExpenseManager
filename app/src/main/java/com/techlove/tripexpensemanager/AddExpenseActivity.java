package com.techlove.tripexpensemanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AddExpenseActivity extends AppCompatActivity {

    EditText et2,et3,et4;
    TextView et1;
    DatePicker dt;
    String dt1;
    Spinner sp;
    String cate[] = {"Food","Bills And Utilities","Transportation","Shopping","Entertainment","Health and Fitness","Eduction"};
    String cate1;
    String f = null;
    Intent intent;
    String name,phone,trp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        et1 = (TextView) findViewById(R.id.textView_add_1);//amount
        et2 = (EditText) findViewById(R.id.editText_add_2);//where
        et3 = (EditText) findViewById(R.id.editText_add_3);//payment
        et4 = (EditText) findViewById(R.id.editText_add_4);//comment
        sp = (Spinner) findViewById(R.id.spinner_add_2);//Spinner
        dt = (DatePicker) findViewById(R.id.datePicker_add_1);//DatePicker
        ArrayAdapter adapter = new ArrayAdapter(AddExpenseActivity.this,android.R.layout.simple_spinner_item,cate);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                cate1 = cate[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        intent = getIntent();
        name = intent.getStringExtra("name");
        phone = intent.getStringExtra("phone");
        trp = intent.getStringExtra("trp");
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

    public void cal(View view)
    {
        Intent intent = new Intent(AddExpenseActivity.this,CalculatorActivity.class);
        startActivityForResult(intent,7);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try
        {
            f = data.getStringExtra("result");

        }
        catch (Exception e)
        {}
        et1.setText(f);
    }

    public void save(View v)
    {
        dt1 = String.valueOf(dt.getDayOfMonth());
        int a = dt.getMonth()+1;
        dt1 = dt1+"-"+String.valueOf(a);
        a = dt.getYear();
        dt1=dt1+"-"+String.valueOf(a);
        String ch = "NULL";
        ch = et1.getText().toString();
        String ch1 = "NULL";
        ch1 = et2.getText().toString();
        String ch2 = "NULL";
        ch2 = et4.getText().toString();
        String ch3 = "NULL";
        ch3 = et3.getText().toString();
        Float f = Float.valueOf(0);
        try
        {
            f = Float.parseFloat(ch);
        }
        catch (Exception e){}
        int amt = Math.round(f);
        SQLiteDatabase db1= openOrCreateDatabase("ExpenseManager", MODE_APPEND, null);
        if(trp.equals("0"))
        {
            if(name.equals("Guest") && phone.equals("Guest ID"))
            {
                db1.execSQL("Create table if not exists Guest_GuestID(sid INTEGER PRIMARY KEY AUTOINCREMENT,dat VARCHAR(255),amt BIGINT(100),whr VARCHAR(255),com VARCHAR(255),cate VARCHAR(255),by1 VARCHAR(255))");
                Cursor c2 = db1.rawQuery("select * from Guest_GuestID",null);
                int co = c2.getCount();
                db1.execSQL("insert into Guest_GuestID(dat,amt,whr,com,cate,by1) values('"+dt1+"','"+amt+"','"+ch1+"','"+ch2+"','"+cate1+"','"+ch3+"')");
                Cursor c3 = db1.rawQuery("select * from Guest_GuestID",null);
                int co1 = c3.getCount();
                if(co1==co+1)
                {
                    Toast.makeText(AddExpenseActivity.this,"Saved", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(AddExpenseActivity.this,"Unable to Save", Toast.LENGTH_SHORT).show();
                }
                c2.close();
                c3.close();
            }
            else
            {
                db1.execSQL("Create table if not exists "+name+"_"+phone+"(sid INTEGER PRIMARY KEY AUTOINCREMENT,dat VARCHAR(255),amt BIGINT(100),whr VARCHAR(255),com VARCHAR(255),cate VARCHAR(255),by1 VARCHAR(255))");
                Cursor c2 = db1.rawQuery("select * from "+name+"_"+phone+"",null);
                int co = c2.getCount();
                db1.execSQL("insert into "+name+"_"+phone+"(dat,amt,whr,com,cate,by1) values('"+dt1+"','"+amt+"','"+ch1+"','"+ch2+"','"+cate1+"','"+ch3+"')");
                Cursor c3 = db1.rawQuery("select * from "+name+"_"+phone+"",null);
                int co1 = c3.getCount();
                if(co1==co+1)
                {
                    Toast.makeText(AddExpenseActivity.this,"Saved", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(AddExpenseActivity.this,"Unable to Save", Toast.LENGTH_SHORT).show();
                }
                c2.close();
                c3.close();
            }
        }
        else
        {
            String strdt = "NULL";
            strdt = intent.getStringExtra("strdt");
            String enddt = "NULL";
            enddt = intent.getStringExtra("enddt");
            String sid = "NULL";
            sid = intent.getStringExtra("sid");
            int sid1 = Integer.parseInt(sid);
            if(name.equals("Guest") && phone.equals("Guest ID"))
            {
                db1.execSQL("Create table if not exists Guest_GuestID_trip(sid1 INTEGER PRIMARY KEY AUTOINCREMENT,sid INTEGER,dat VARCHAR(255),strtdat VARCHAR(255),enddat VARCHAR(255),amt BIGINT(100),whr VARCHAR(255),com VARCHAR(255),cate VARCHAR(255),by1 VARCHAR(255),trpbudget INTEGER)");
                Cursor c2 = db1.rawQuery("select * from Guest_GuestID_trip where sid='"+sid+"'",null);
                int co = c2.getCount();
                c2.moveToLast();
                int bug =(int) c2.getLong(10);
                db1.execSQL("insert into Guest_GuestID_trip(sid,dat,strtdat,enddat,amt,whr,com,cate,by1,trpbudget) values('"+sid1+"','"+dt1+"','"+strdt+"','"+enddt+"','"+amt+"','"+ch1+"','"+ch2+"','"+cate1+"','"+ch3+"','"+bug+"')");
                Cursor c3 = db1.rawQuery("select * from Guest_GuestID_trip",null);
                int co1 = c3.getCount();
                if(co1==co+1)
                {
                    Toast.makeText(AddExpenseActivity.this,"Saved", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(AddExpenseActivity.this,"Unable to Save", Toast.LENGTH_SHORT).show();
                }
                c2.close();
                c3.close();
            }
            else
            {
                db1.execSQL("Create table if not exists "+name+"_"+phone+"_trip(sid1 INTEGER PRIMARY KEY AUTOINCREMENT,sid INTEGER,dat VARCHAR(255),strtdat VARCHAR(255),enddat VARCHAR(255),amt BIGINT(100),whr VARCHAR(255),com VARCHAR(255),cate VARCHAR(255),by1 VARCHAR(255),trpbudget INTEGER)");
                Cursor c2 = db1.rawQuery("select * from "+name+"_"+phone+"_trip where sid='"+sid+"'",null);
                int co = c2.getCount();
                c2.moveToLast();
                int bug =(int) c2.getLong(10);
                db1.execSQL("insert into "+name+"_"+phone+"_trip(sid,dat,strtdat,enddat,amt,whr,com,cate,by1,trpbudget) values('"+sid1+"','"+dt1+"','"+strdt+"','"+enddt+"','"+amt+"','"+ch1+"','"+ch2+"','"+cate1+"','"+ch3+"','"+bug+"')");
                Cursor c3 = db1.rawQuery("select * from "+name+"_"+phone+"_trip",null);
                int co1 = c3.getCount();
                if(co1==co+1)
                {
                    Toast.makeText(AddExpenseActivity.this,"Saved", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(AddExpenseActivity.this,"Unable to Save", Toast.LENGTH_SHORT).show();
                }
                c2.close();
                c3.close();
            }
        }

    }
}