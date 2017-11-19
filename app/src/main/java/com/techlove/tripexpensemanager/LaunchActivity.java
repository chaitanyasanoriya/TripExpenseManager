package com.techlove.tripexpensemanager;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lylc.widget.circularprogressbar.CircularProgressBar;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class LaunchActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    CircularProgressBar c3, c2;
    TextView tv1, tv2;
    String name = "Guest";
    String phone = "Guest ID";
    String trp = "0";
    String strdt = null;
    String enddt = null;
    String sid = null;
    int th1, we1;
    int gubug;
    Date date = new Date();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SQLiteDatabase db1= openOrCreateDatabase("ExpenseManager", MODE_APPEND, null);
        db1.execSQL("create table if not exists setting(sid1 INTEGER PRIMARY KEY AUTOINCREMENT,th INTEGER,we INTEGER,trp VARCHAR(255),phone VARCHAR(255),name VARCHAR(255),sid INTEGER,gubug BIGINT)");
        String que = "Select * from setting";
        Cursor c1 = db1.rawQuery(que,null);
        int count = c1.getCount();
        if(count>0)
        {
            c1.moveToLast();
            name = c1.getString(5);
            phone = c1.getString(4);
            trp = c1.getString(3);
            th1 = (int) c1.getLong(1);
            sid = c1.getString(6);
            if(name.equals("Guest"))
            {
                gubug = (int) c1.getLong(7);
                if(gubug==0)
                {
                    AlertDialog alertdialog = new AlertDialog.Builder(LaunchActivity.this).create();
                    alertdialog.setTitle("Alert");
                    alertdialog.setMessage("Please Enter your Monthly Budget");
                    alertdialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    });
                    alertdialog.show();
                }
            }
        }
        else
        {
            AlertDialog alertdialog = new AlertDialog.Builder(LaunchActivity.this).create();
            alertdialog.setTitle("Alert");
            alertdialog.setMessage("Please Enter your Monthly Budget");
            alertdialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alertdialog.show();
        }
        setContentView(R.layout.activity_launch);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        c3 = (CircularProgressBar) findViewById(R.id.circularprogressbar1);
        c2 = (CircularProgressBar) findViewById(R.id.circularprogressbar2);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(LaunchActivity.this, AddExpenseActivity.class);
                if(trp.equals("1"))
                {
                    SQLiteDatabase db2 = openOrCreateDatabase("ExpenseManager", MODE_APPEND, null);
                    String st1 = "create table if not exists "+name+"_"+phone+"_trip(sid1 INTEGER PRIMARY KEY AUTOINCREMENT,sid INTEGER,dat VARCHAR(255),strtdat VARCHAR(255),enddat VARCHAR(255),amt BIGINT(100),whr VARCHAR(255),com VARCHAR(255),cate VARCHAR(255),by1 VARCHAR(255),trpbudget INTEGER)";
                    db2.execSQL(st1);
                    Cursor c4 = db2.rawQuery("select * from "+name+"_"+phone+"_trip",null);
                    if(c4.getCount()>0)
                    {
                        c4.moveToLast();
                        strdt = c4.getString(3);
                        enddt = c4.getString(4);
                        sid =String.valueOf((int) c4.getLong(1));
                    }
                }
                intent1.putExtra("name",name);
                intent1.putExtra("phone",phone);
                intent1.putExtra("trp",trp);
                intent1.putExtra("strdt",strdt);
                intent1.putExtra("enddt",enddt);
                intent1.putExtra("sid",sid);
                startActivity(intent1);

            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.launch, menu);
        tv1 = (TextView) findViewById(R.id.textView_head_1);
        tv2 = (TextView) findViewById(R.id.textView_head_2);
        tv1.setText(phone);
        tv2.setText(name);
        if(trp.equals("1"))
        {
            tv2.append(" (Trip Mode)");
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent;
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            intent = new Intent(LaunchActivity.this, AboutUsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        tv1 = (TextView) findViewById(R.id.textView_head_1);
        tv2 = (TextView) findViewById(R.id.textView_head_2);
        Intent intent1,intent2;
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add) {
            intent1 = new Intent(LaunchActivity.this, AddExpenseActivity.class);
            if(trp.equals("1"))
            {
                SQLiteDatabase db2 = openOrCreateDatabase("ExpenseManager", MODE_APPEND, null);
                String st1 = "create table if not exists "+name+"_"+phone+"_trip(sid1 INTEGER PRIMARY KEY AUTOINCREMENT,sid INTEGER,dat VARCHAR(255),strtdat VARCHAR(255),enddat VARCHAR(255),amt BIGINT(100),whr VARCHAR(255),com VARCHAR(255),cate VARCHAR(255),by1 VARCHAR(255),trpbudget INTEGER)";
                db2.execSQL(st1);
                Cursor c4 = db2.rawQuery("select * from "+name+"_"+phone+"_trip",null);
                if(c4.getCount()>0)
                {
                    c4.moveToLast();
                    strdt = c4.getString(3);
                    enddt = c4.getString(4);
                    sid =String.valueOf((int) c4.getLong(1));
                }
            }
            intent1.putExtra("name",name);
            intent1.putExtra("phone",phone);
            intent1.putExtra("trp",trp);
            intent1.putExtra("strdt",strdt);
            intent1.putExtra("enddt",enddt);
            intent1.putExtra("sid",sid);
            startActivity(intent1);

        } else if (id == R.id.nav_strt_trp)
        {
            if(trp.equals("1"))
            {
                Toast.makeText(LaunchActivity.this,"Already in Trip Mode", Toast.LENGTH_SHORT).show();
            }
            else
            {trp = "1";
            intent2 = new Intent(LaunchActivity.this, StrtTripActivity.class);
            intent2.putExtra("name",name);
            intent2.putExtra("phone",phone);
            SQLiteDatabase db1= openOrCreateDatabase("ExpenseManager", MODE_APPEND, null);
            db1.execSQL("create table if not exists setting(sid1 INTEGER PRIMARY KEY AUTOINCREMENT,th INTEGER,we INTEGER,trp VARCHAR(255),phone VARCHAR(255),name VARCHAR(255),sid INTEGER,gubug BIGINT)");
            db1.execSQL("insert into setting(th,we,trp,phone,name,sid) values('"+th1+"','"+we1+"','"+trp+"','"+phone+"','"+name+"','"+sid+"')");
            startActivityForResult(intent2,9);
            }
        }
        else if (id == R.id.nav_history)
        {
            Intent intent3 = new Intent(LaunchActivity.this, HistoryActivity.class);
            startActivity(intent3);
        }
        else if(id == R.id.nav_add_bug)
        {
            Intent intent6 = new Intent(LaunchActivity.this,UpdateBugActivity.class);
            intent6.putExtra("name",name);
            intent6.putExtra("phone",phone);
            startActivity(intent6);
        }
        else if (id == R.id.nav_login) {
            if (name.equals("Guest") && phone.equals("Guest ID")) {
                intent1 = new Intent(LaunchActivity.this, LoginActivity.class);
                startActivityForResult(intent1, 3);
            } else {
                Toast.makeText(LaunchActivity.this, "Already Logged in", Toast.LENGTH_SHORT).show();
            }
        } else if (id == R.id.nav_logout) {
            name = "Guest";
            phone = "Guest ID";
            trp = "0";
            onActivityResult(3, 4, null);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
    protected void onPause() {
        super.onPause();
        SQLiteDatabase db = openOrCreateDatabase("ExpenseManager", MODE_APPEND, null);
        db.execSQL("create table if not exists setting(sid1 INTEGER PRIMARY KEY AUTOINCREMENT,th INTEGER,we INTEGER,trp VARCHAR(255),phone VARCHAR(255),name VARCHAR(255),sid INTEGER,gubug BIGINT)");
        String q1 = "select * from setting";
        Cursor c = db.rawQuery(q1,null);
        int n = c.getCount();
        if(n>0)
        {
            c.moveToLast();
            th1 = (int) c.getLong(1);
            we1 = (int) c.getLong(2);
            gubug = (int) c.getLong(7);
            c.close();
        }
            String q = "insert into setting(th,we,trp,phone,name,sid,gubug) values('"+th1+"','"+we1+"','"+trp+"','"+phone+"','"+name +"','"+sid+"','"+gubug+"')";
            db.execSQL(q);
            q1 = "select * from setting";
            c = db.rawQuery(q1,null);
            c.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        TextView tv1 = (TextView) findViewById(R.id.textview_cnt_1);
        TextView tv2 = (TextView) findViewById(R.id.textview_cnt_2);
        TextView tv3 = (TextView) findViewById(R.id.textview_cnt_3);
        TextView tv4 = (TextView) findViewById(R.id.textview_cnt_4);
        int dt3 = date.getDate();
        int yr = date.getYear();
        int mo1 = date.getMonth();
        yr = yr+1900;
        mo1=mo1+1;
        int a1 = 0;
        int a2;
        int sp=0;
        int su;
        Calendar mycal = new GregorianCalendar(yr,mo1,1);
        int dim = mycal.getActualMaximum(Calendar.DAY_OF_MONTH);
        float fr = Float.valueOf(0);
        int fr1 = 0;
        float fr2 = Float.valueOf(0);
        int fr3 = 0;
        int flag = 0;
        int flag1=0;
        float burn, burnn;
        int fun,dayl;
        SQLiteDatabase db1= openOrCreateDatabase("ExpenseManager", MODE_APPEND, null);
        if(trp.equals("0"))
        {
            if(name.equals("Guest"))
            {

                db1.execSQL("create table if not exists setting(sid1 INTEGER PRIMARY KEY AUTOINCREMENT,th INTEGER,we INTEGER,trp VARCHAR(255),phone VARCHAR(255),name VARCHAR(255),sid INTEGER,gubug BIGINT)");
                Cursor c1 = db1.rawQuery("select * from setting where name='Guest'",null);
                int n = c1.getCount();
                if(n>0)
                {
                    flag++;
                    c1.moveToLast();
                a1 = (int) c1.getLong(7);
                    if(a1==0)
                    {
                        flag1++;
                    }
                db1.execSQL("Create table if not exists Guest_GuestID(sid INTEGER PRIMARY KEY AUTOINCREMENT,dat VARCHAR(255),amt BIGINT(100),whr VARCHAR(255),com VARCHAR(255),cate VARCHAR(255),by1 VARCHAR(255))");
                c1 = db1.rawQuery("select * from Guest_GuestID",null);
                while(c1.moveToNext())
                {
                    sp = sp+((int) c1.getLong(2));
                }
                c1.close();
                fr = (float) sp / a1;
                fr = fr*100;
                fr1 = Math.round(fr);
                    if(fr1==0)
                    {
                        fr1=1;
                    }
                }
            }
            else
            {
                db1.execSQL("create table if not exists users(name varchar(255),email varchar(255),password varchar(255),phone varchar(255),budget varchar(100),saving varchar(255))");
                Cursor c2 = db1.rawQuery("select * from users where name='"+name+"' and phone='"+phone+"'",null);
                int n = c2.getCount();
                if(n>0)
                {flag++;
                    c2.moveToLast();
                a1 =Integer.parseInt(c2.getString(4));
                String st = "create table if not exists "+name+"_"+phone+"(sid INTEGER PRIMARY KEY AUTOINCREMENT,dat VARCHAR(255),amt BIGINT(100),whr VARCHAR(255),com VARCHAR(255),cate VARCHAR(255),by1 VARCHAR(255))";
                db1.execSQL(st);
                c2 = db1.rawQuery("select * from "+name+"_"+phone+"",null);
                while(c2.moveToNext())
                {
                    sp = sp + ((int) c2.getLong(2));
                }
                c2.close();
                fr = (float) sp/a1;
                fr=fr*100;
                fr1 = Math.round(fr);
                }
            }
            fr2 = (float) dt3/dim;
            fr2 = fr2*100;
            fr3 = Math.round(fr2);
            burn = (float) sp/dt3;
            burnn = (float) a1/dim;
            fun = a1-sp;
            dayl = dim-dt3;
            if(fr1==0)
            {
                fr1=1;
            }
        }
        else
        {
            if(name.equals("Guest"))
            {
                db1.execSQL("Create table if not exists Guest_GuestID_trip(sid1 INTEGER PRIMARY KEY AUTOINCREMENT,sid INTEGER,dat VARCHAR(255),strtdat VARCHAR(255),enddat VARCHAR(255),amt BIGINT(100),whr VARCHAR(255),com VARCHAR(255),cate VARCHAR(255),by1 VARCHAR(255),trpbudget INTEGER)");
                Cursor c3 = db1.rawQuery("select * from Guest_GuestID_trip",null);
                if(c3.getCount()>0)
                {flag++;
                    c3.moveToLast();
                a1 = (int) c3.getLong(10);
                int ch = (int) c3.getLong(1);
                String stdt = c3.getString(3);
                String endt = c3.getString(4);
                char [] ch1 = stdt.toCharArray();
                char [] ch2 = endt.toCharArray();
                char [] ch3 = new char[2];
                char [] ch4 = new char[2];
                int i=0;
                while(ch1[i]!='-')
                {
                    ch3[i]=ch1[i];
                    i++;;
                }
                i=0;
                while(ch2[i]!='-')
                {
                    ch4[i]=ch2[i];
                    i++;
                }
                stdt = String.valueOf(ch3);
                endt = String.valueOf(ch4);
                int day1 = Integer.parseInt(stdt);
                int day2 = Integer.parseInt(endt);
                dim = day2-day1;
                dim = dim +1;
                dt3 = dt3-day1;
                dt3 = dt3+1;
                fr2 = (float) dt3/dim;
                fr2 = fr2*100;
                fr3 = Math.round(fr2);
                c3 = db1.rawQuery("select * from Guest_GuestID_trip where sid='"+ch+"'",null);
                while(c3.moveToNext())
                {
                    sp = sp + ((int) c3.getLong(5));
                }
                c3.close();
                fr = (float) sp/a1;
                fr=fr*100;
                fr1 = Math.round(fr);}
                burn = (float) sp/dt3;
                burnn = (float) a1/dim;
                fun = a1-sp;
                dayl = dim-dt3;
                if(fr1==0)
                {
                    fr1=1;
                }
            }
            else
            {
                String st1 = "create table if not exists "+name+"_"+phone+"_trip(sid1 INTEGER PRIMARY KEY AUTOINCREMENT,sid INTEGER,dat VARCHAR(255),strtdat VARCHAR(255),enddat VARCHAR(255),amt BIGINT(100),whr VARCHAR(255),com VARCHAR(255),cate VARCHAR(255),by1 VARCHAR(255),trpbudget INTEGER)";
                db1.execSQL(st1);
                Cursor c3 = db1.rawQuery("select * from "+name+"_"+phone+"_trip",null);
                if(c3.getCount()>0)
                {
                    flag++;
                    c3.moveToLast();
                a1 = (int) c3.getLong(10);
                int ch = (int) c3.getLong(1);
                String stdt = c3.getString(3);
                String endt = c3.getString(4);
                char [] ch1 = stdt.toCharArray();
                char [] ch2 = endt.toCharArray();
                char [] ch3 = new char[2];
                char [] ch4 = new char[2];
                int i=0;
                while(ch1[i]!='-')
                {
                    ch3[i]=ch1[i];
                    i++;;
                }
                i=0;
                while(ch2[i]!='-')
                {
                    ch4[i]=ch2[i];
                    i++;
                }
                stdt = String.valueOf(ch3);
                endt = String.valueOf(ch4);
                int day1 = Integer.parseInt(stdt);
                int day2 = Integer.parseInt(endt);
                dim = day2-day1;
                dim = dim +1;
                dt3 = dt3-day1;
                dt3 = dt3+1;
                fr2 = (float) dt3/dim;
                fr2 = fr2*100;
                fr3 = Math.round(fr2);
                c3 = db1.rawQuery("select * from "+name+"_"+phone+"_trip where sid='"+ch+"'",null);
                while(c3.moveToNext())
                {
                    sp = sp + ((int) c3.getLong(5));
                }
                c3.close();
                fr = (float) sp/a1;
                fr=fr*100;
                fr1 = Math.round(fr);
                }
                burn = (float) sp/dt3;
                burnn = (float) a1/dim;
                fun = a1-sp;
                dayl = dim-dt3;
                if(fr1==0)
                {
                    fr1=1;
                }
            }
        }
        if(flag==0)
        {
            sp=99;
            a1=100;
            fr1=99;
        }
        if(flag1>0)
        {
            sp=99;
            a1=100;
            fr1=99;
        }
        final int finalSp = sp;
        final int finalA = a1;
        final int finalFlag = flag;
        final int finalFlag1 = flag1;

        c3.animateProgressTo(0, fr1, new CircularProgressBar.ProgressAnimationListener() {

            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationFinish()
            {
                if(finalFlag ==0 || finalFlag1 !=0)
                {
                    c3.setSubTitle("out of "+String.valueOf(finalA));
                }
                else {
                    c3.setSubTitle("out of " + finalA);
                }
            }

            @Override
            public void onAnimationProgress(int progress) {
                if(finalFlag ==0 || finalFlag1 !=0)
                {
                    c3.setTitle(String.valueOf(finalSp)+" steps");
                }
                else
                {c3.setTitle(String.valueOf(finalSp) + " spent");

                }
            }
        });//end of animate statement
        final int finalDim = dim;
        final int finalDt = dt3;
        c2.animateProgressTo(0, fr3, new CircularProgressBar.ProgressAnimationListener() {

            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationFinish() {
                c2.setSubTitle("out of "+ finalDim);
            }

            @Override
            public void onAnimationProgress(int progress) {
                c2.setTitle(String.valueOf(finalDt)+" days");
            }
        });//end of animate statement
        if(finalFlag ==0 || finalFlag1 !=0)
        {
            tv1.setText("Current Burn Rate : N/A");
            tv2.setText("Burn Rate needed : N/A");
            tv3.setText("Funds left : N/A");
            tv4.setText("Days Left : N/A");
        }
        else
        {
            tv1.setText("Current Burn Rate : "+burn+" /day");
            tv2.setText("Burn Rate needed : "+burnn+" /day");
            tv3.setText("Funds left : "+fun+"");
            tv4.setText("Days Left : "+dayl+"");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        tv1 = (TextView) findViewById(R.id.textView_head_1);
        tv2 = (TextView) findViewById(R.id.textView_head_2);
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==4)
        {
            try
            {
                name = data.getStringExtra("name");
                phone = data.getStringExtra("phone");
            }
            catch (Exception e) {}
            try
            {
                if (!name.isEmpty() && !phone.isEmpty())
                {
                    tv1.setText(phone);
                    tv2.setText(name);
                }
            }
            catch (Exception e1) {}
        }
        else if(resultCode==10)
        {
            try
            {
                strdt = data.getStringExtra("strdt");
                enddt = data.getStringExtra("enddt");
                sid = data.getStringExtra("sid");
            }
            catch (Exception e){}
        }
    }

}
