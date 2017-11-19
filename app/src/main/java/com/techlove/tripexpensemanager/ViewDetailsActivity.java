package com.techlove.tripexpensemanager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class ViewDetailsActivity extends AppCompatActivity {

    TextView tv1,tv2,tv3,tv4,tv5,tv6,tv7;
    ImageView img;
    String name = null;
    String phone = null;
    String trp = null;
    String id = null;
    int id1;
    Intent intent5;
    String dt1,whr1,com1,cate1,by2;
    int amt1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        SQLiteDatabase db = openOrCreateDatabase("ExpenseManager", MODE_APPEND, null);
        intent5 = getIntent();
        name = intent5.getStringExtra("name");
        phone = intent5.getStringExtra("phone");
        trp = intent5.getStringExtra("trp");
        id = intent5.getStringExtra("id");
        id1 = Integer.parseInt(id);
        if(trp.equals("0"))
        {
            String st = "create table if not exists "+name+"_"+phone+"(sid INTEGER PRIMARY KEY AUTOINCREMENT,dat VARCHAR(255),amt BIGINT(100),whr VARCHAR(255),com VARCHAR(255),cate VARCHAR(255),by1 VARCHAR(255))";
            db.execSQL(st);
            String q2 = "select * from "+name+"_"+phone+" where sid='"+id1+"'";
            Cursor c3 = db.rawQuery(q2,null);
            c3.moveToNext();
            dt1 = c3.getString(1);
            amt1 = (int) c3.getLong(2);
            whr1 = c3.getString(3);
            com1 = c3.getString(4);
            cate1 = c3.getString(5);
            by2 = c3.getString(6);
            c3.close();
        }
        else
        {
            String st1 = "create table if not exists "+name+"_"+phone+"_trip(sid1 INTEGER PRIMARY KEY AUTOINCREMENT,sid INTEGER,dat VARCHAR(255),strtdat VARCHAR(255),enddat VARCHAR(255),amt BIGINT(100),whr VARCHAR(255),com VARCHAR(255),cate VARCHAR(255),by1 VARCHAR(255),trpbudget INTEGER)";
            db.execSQL(st1);
            String q2 = "select * from "+name+"_"+phone+"_trip where sid1='"+id1+"'";
            Cursor c4 = db.rawQuery(q2,null);
            c4.moveToNext();
            dt1 = c4.getString(2);
            amt1 = (int) c4.getLong(5);
            whr1 = c4.getString(6);
            com1 = c4.getString(7);
            cate1 = c4.getString(8);
            by2 = c4.getString(9);
            c4.close();
        }

        setContentView(R.layout.activity_view_details);

        tv1 = (TextView) findViewById(R.id.textView_view_1);//amount
        tv2 = (TextView) findViewById(R.id.textView_view_2);//cate
        tv3 = (TextView) findViewById(R.id.textView_view_3);//date
        tv4 = (TextView) findViewById(R.id.textView_view_4);//place
        tv5 = (TextView) findViewById(R.id.textView_view_5);//payment method
        tv6 = (TextView) findViewById(R.id.textView_view_6);//comment
        img = (ImageView) findViewById(R.id.imageView_view_2);
        tv1.append(String.valueOf(amt1));
        tv2.append(cate1);
        tv3.append(dt1);
        tv4.append(whr1);
        tv5.append(by2);
        tv6.append(com1);
        try{
            if(cate1.equals("Food"))
            {
                img.setImageResource(R.drawable.food);
            }
            else if(cate1.equals("Bills And Utilities"))
            {
                img.setImageResource(R.drawable.bill);
            }
            else if(cate1.equals("Transportation"))
            {
                img.setImageResource(R.drawable.trans);
            }
            else if(cate1.equals("Shopping"))
            {
                img.setImageResource(R.drawable.shop);
            }
            else if(cate1.equals("Entertainment"))
            {
                img.setImageResource(R.drawable.enter);
            }
            else if(cate1.equals("Health and Fitness"))
            {
                img.setImageResource(R.drawable.med);
            }
            else if(cate1.equals("Eduction"))
            {
                img.setImageResource(R.drawable.edu);
            }
            else
            {
                img.setImageResource(android.R.drawable.btn_star);
            }
        }catch (Exception e){}
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
