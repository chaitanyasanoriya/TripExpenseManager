package com.techlove.tripexpensemanager;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HistoryActivity extends ListActivity {
    ArrayList dt, amt, cate, sid;
    Intent intent;
    String name = null;
    String phone = null;
    String trp = null;
    int i = 0;
    int j =0;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        SQLiteDatabase db = openOrCreateDatabase("ExpenseManager", MODE_APPEND, null);
        db.execSQL("create table if not exists setting(sid1 INTEGER PRIMARY KEY AUTOINCREMENT,th INTEGER,we INTEGER,trp VARCHAR(255),phone VARCHAR(255),name VARCHAR(255),sid INTEGER,gubug BIGINT)");
        String q1 = "select * from setting";
        Cursor c = db.rawQuery(q1,null);
        int n = c.getCount();
        if(n>0)
        {
            c.moveToLast();
            name = c.getString(5);
            phone = c.getString(4);
            trp = c.getString(3);
        }
            super.onCreate(savedInstanceState);
        if(trp.equals("0"))
        {
            if(name.equals("Guest"))
            {
                phone="GuestID";
            }
            String st = "create table if not exists "+name+"_"+phone+"(sid INTEGER PRIMARY KEY AUTOINCREMENT,dat VARCHAR(255),amt BIGINT(100),whr VARCHAR(255),com VARCHAR(255),cate VARCHAR(255),by1 VARCHAR(255))";
            db.execSQL(st);
            String q2 = "select * from "+name+"_"+phone+" ORDER BY sid DESC";
            Cursor c3 = db.rawQuery(q2,null);
            int n1 = c3.getCount();
            dt = new ArrayList();
            cate = new ArrayList();
            amt = new ArrayList();
            sid = new ArrayList();
            if(n1>0)
            {
                while(c3.moveToNext())
                {
                    int sid1 = (int) c3.getLong(0);
                    String st3 = String.valueOf(sid1);
                    sid.add(st3);
                    int amt1 = (int) c3.getLong(2);
                    st3 = String.valueOf(amt1);
                    amt.add(st3);
                    cate.add(c3.getString(5));
                    dt.add(c3.getString(1));
                }
            }
            c3.close();
        }
        else
        {
            String st1 = "create table if not exists "+name+"_"+phone+"_trip(sid1 INTEGER PRIMARY KEY AUTOINCREMENT,sid INTEGER,dat VARCHAR(255),strtdat VARCHAR(255),enddat VARCHAR(255),amt BIGINT(100),whr VARCHAR(255),com VARCHAR(255),cate VARCHAR(255),by1 VARCHAR(255),trpbudget INTEGER)";
            db.execSQL(st1);
            String q2 = "select * from "+name+"_"+phone+"_trip ORDER BY sid1 DESC";
            Cursor c4 = db.rawQuery(q2,null);
            int n2 = c4.getCount();
            dt = new ArrayList();
            cate = new ArrayList();
            amt = new ArrayList();
            sid = new ArrayList();
            if(n2>0)
            {
                while(c4.moveToNext())
                {
                    int sid1 = (int) c4.getLong(0);
                    String st2 = String.valueOf(sid1);
                    sid.add(st2);
                    int amt1 = (int) c4.getLong(5);
                    st2 = String.valueOf(amt1);
                    amt.add(st2);
                    cate.add(c4.getString(8));
                    dt.add(c4.getString(2));
                }
            }
            c4.close();
        }
        //setContentView(R.layout.activity_history);
        ArrayAdapter adpt = null;
        adpt = new MyAdapter(HistoryActivity.this,android.R.layout.simple_list_item_1,sid);
        setListAdapter(adpt);
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

    private class MyAdapter extends ArrayAdapter
    {
        public MyAdapter(Context context, int resource, ArrayList objects)
        {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater l1 = (LayoutInflater) getSystemService(getApplicationContext().LAYOUT_INFLATER_SERVICE);
            View row = l1.inflate(R.layout.activity_history,parent,false);
            TextView tv1 = (TextView) row.findViewById(R.id.textView_hi_1);
            TextView tv2 = (TextView) row.findViewById(R.id.textView_hi_2);
            TextView tv3 = (TextView) row.findViewById(R.id.textView_hi_3);
            TextView tv4 = (TextView) row.findViewById(R.id.textView_hi_4);
            img = (ImageView) row.findViewById(R.id.imageView_hi_1);
            tv1.setText(String.valueOf("ID :"+sid.get(position)));
            tv2.setText("Amount : "+amt.get(position));
            tv3.setText("Category : "+cate.get(position));
            tv4.setText("Date : "+dt.get(position));
            try{
                if(cate.get(position).equals("Food"))
                {
                    img.setImageResource(R.drawable.food);
                }
                else if(cate.get(position).equals("Bills And Utilities"))
                {
                    img.setImageResource(R.drawable.bill);
                }
                else if(cate.get(position).equals("Transportation"))
                {
                    img.setImageResource(R.drawable.trans);
                }
                else if(cate.get(position).equals("Shopping"))
                {
                    img.setImageResource(R.drawable.shop);
                }
                else if(cate.get(position).equals("Entertainment"))
                {
                    img.setImageResource(R.drawable.enter);
                }
                else if(cate.get(position).equals("Health and Fitness"))
                {
                    img.setImageResource(R.drawable.med);
                }
                else if(cate.get(position).equals("Eduction"))
                {
                    img.setImageResource(R.drawable.edu);
                }
                else
                {
                    img.setImageResource(android.R.drawable.btn_star);
                }
            }catch (Exception e){}
            return row;
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        String stt1 = sid.get(position).toString();
        Intent intent4 = new Intent(HistoryActivity.this,ViewDetailsActivity.class);
        intent4.putExtra("name",name);
        intent4.putExtra("phone",phone);
        intent4.putExtra("trp",trp);
        intent4.putExtra("id",stt1);
        startActivity(intent4);
    }
}
