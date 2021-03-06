package com.gy.linjliang.simpledo;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.content.Context;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import android.content.SharedPreferences;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private List<Map<String,Object>> arrayList;
    private MyDatabase myDatabase=new MyDatabase(this,"Finalitem.db",null,1);
    private List<item> list;

    private DrawerLayout mdrawerLayout;
    private NavigationView navigationView;
    private ActionBar actionBar;
    private FloatingActionButton buttonadd;
    private Madapter madapter;
    private RecyclerView recyclerView;
    private String curyear;
    private String curmonth;
    private String curday;
    private TextView showcurrenttime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        // 检测是否是第一次启动
        SharedPreferences sharePreferences = this.getSharedPreferences("guideActivity", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharePreferences.edit();

        // todo 实际发布前注释即可
        if(sharePreferences.getBoolean("firstStart", true)){
            editor.putBoolean("firstStart",false);
            editor.commit();
            Intent intent = new Intent(MainActivity.this,startupView.class);
            startActivity(intent);
        }

        //data();

//        myDatabase.insert(new item("101","working","完成安卓proj","2018","01","06","00","00","2018","01","08","22","00"));
//        myDatabase.insert(new item("102","studying","看书","2018","01","10","00","00","2018","1","14","22","00"));
//        myDatabase.insert(new item("103","living","购物","2017","12","06","00","00","2017","12","08","12","00"));
//        myDatabase.insert(new item("104","living","聚餐","2017","12","06","08","00","2017","12","08","11","30"));
//        myDatabase.insert(new item("105","living","看电影","2017","12","06","22","00","2017","12","06","23","40"));
//        myDatabase.insert(new item("106","living","拜访好友","2018","01","06","00","00","2018","01","08","12","00"));
//        myDatabase.insert(new item("107","working","写报告","2017","12","06","08","00","2017","12","06","09","00"));
//        myDatabase.insert(new item("108","studying","复习AI","2018","01","10","09","00","2018","1","10","10","00"));
//        myDatabase.insert(new item("109","studying","看Web课件","2018","01","10","10","00","2018","1","10","11","30"));
//        myDatabase.insert(new item("110","living","听音乐","2018","01","10","12","00","2018","1","10","12","20"));
        show(myDatabase);
        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,additem.class);
                startActivityForResult(i,1);
            }
        });

        // 菜单栏
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);

        }

        navigationView.setCheckedItem(R.id.activityMain);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Intent intent;
                navigationView.setCheckedItem(id);
                switch (id){
                    case R.id.activityday:
                        intent = new Intent(MainActivity.this,Datetask.class);
                        startActivity(intent);
                        break;
                    case R.id.activityCountdown:
                        intent = new Intent(MainActivity.this,countdown.class);
                        startActivity(intent);
                        break;
                    case R.id.activitySummary:
                        intent = new Intent(MainActivity.this,Summary.class);
                        startActivity(intent);
                        break;
                    case R.id.activitypotato:
                        intent = new Intent(MainActivity.this,Workclock.class);
                        startActivity(intent);
                        break;
                }
                mdrawerLayout.closeDrawers();
                return true;
            }
        });
        // -------------------------------------------------

        // 列表

        // -------------------------------------------------
    }

    //找到控件
    public void init(){
        listView=(ListView) findViewById(R.id.list_item);
        mdrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView=(NavigationView)findViewById(R.id.nav_view);
        actionBar=getSupportActionBar();
        buttonadd=(FloatingActionButton) findViewById(R.id.buttonaddone);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        showcurrenttime=(TextView) findViewById(R.id.showcurrenttime);
    }

    public void show(final MyDatabase myDatabase){
        data();
        list=new ArrayList<item>();
        SQLiteDatabase db=myDatabase.getWritableDatabase();
        Cursor cursor=db.query("item",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String id=cursor.getString(cursor.getColumnIndex("id"));
                String content=cursor.getString(cursor.getColumnIndex("content"));
                String label=cursor.getString(cursor.getColumnIndex("label"));
                String startyear=cursor.getString(cursor.getColumnIndex("startyear"));
                String startmonth=cursor.getString(cursor.getColumnIndex("startmonth"));
                String startday=cursor.getString(cursor.getColumnIndex("startday"));
                String starthour=cursor.getString(cursor.getColumnIndex("starthour"));
                String startmin=cursor.getString(cursor.getColumnIndex("startmin"));
                String finishyear=cursor.getString(cursor.getColumnIndex("finishyear"));
                String finishmonth=cursor.getString(cursor.getColumnIndex("finishmonth"));
                String finishday=cursor.getString(cursor.getColumnIndex("finishday"));
                String finishhour=cursor.getString(cursor.getColumnIndex("finishhour"));
                String finishmin=cursor.getString(cursor.getColumnIndex("finishmin"));
                String isfinish=cursor.getString(cursor.getColumnIndex("isfinish"));
                String imagepath=cursor.getString(cursor.getColumnIndex("imgpath"));
                String videopath=cursor.getString(cursor.getColumnIndex("videopath"));

                if(curyear.equals(startyear)&&curmonth.equals(startmonth)&&curday.equals(startday)){
                    item temp_item=new item(id,label,content,startyear,startmonth,startday,starthour,startmin,
                            finishyear,finishmonth,finishday,finishhour,finishmin);
                    temp_item.setImgpath(imagepath);temp_item.setVideopath(videopath);temp_item.setIsfinish(isfinish);
                    list.add(temp_item);
                }
            }while(cursor.moveToNext());

        }
        madapter=new Madapter(list);
        recyclerView.setAdapter(madapter);
        madapter.setOnitemClickListener(new Madapter.OnItemClickListener() {
            @Override
            public void onClick(int position) {
                final item temp_item=list.get(position);
                Intent intent=new Intent(MainActivity.this,UpdateActivity.class);
                intent.putExtra("item",temp_item);
                startActivityForResult(intent,1);
            }

            @Override
            public void onLongClick(final int postion) {
                final item temp_item=list.get(postion);
                AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                dialog.setMessage("是否删除");
                dialog.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myDatabase.deleteByid(temp_item.id);
                        list.remove(postion);
                        show(myDatabase);
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case 1:
                show(myDatabase);
                break;
            default:
                break;
        }
    }

    public void data(){
        final String temp_i=(String) getIntent().getSerializableExtra("date");
        if(temp_i==null){
            SimpleDateFormat formatter=new SimpleDateFormat("yyyy MM dd HH mm ss");
            Date curdate= new Date(System.currentTimeMillis());
            String str=formatter.format(curdate);
            String s[]=str.split(" ");
            curyear=s[0];
            curmonth=s[1];
            curday=s[2];
            showcurrenttime.setText(curyear+"年"+curmonth+"月"+curday+"日");

        }else{
            String s[]=temp_i.split("-");
            curyear=s[0];
            curmonth=s[1];
            curday=s[2];
            showcurrenttime.setText(curyear+"年"+curmonth+"月"+curday+"日");
        }

    }

    @Override
    protected void onResume() {
        show(myDatabase);
        navigationView.setCheckedItem(R.id.activityMain);
        super.onResume();
    }

}
