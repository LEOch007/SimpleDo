package com.gy.linjliang.simpledo;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.content.Context;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import android.content.SharedPreferences;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private List<Map<String,Object>> arrayList;
    private MyDatabase myDatabase=new MyDatabase(this,"Finalitem.db",null,1);
    private List<item> list;

    private DrawerLayout mdrawerLayout;
    private NavigationView navigationView;
    private ActionBar actionBar;
    private Button buttonadd;
    private Madapter madapter;
    private RecyclerView recyclerView;
    //private ImageView icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();

        // 检测是否是第一次启动
        SharedPreferences sharePreferences = this.getSharedPreferences("guideActivity", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sharePreferences.edit();

        // todo 实际发布前注释即可
//        if(sharePreferences.getBoolean("firstStart", true)){
//            editor.putBoolean("firstStart",false);
//            editor.commit();
//            Intent intent = new Intent(MainActivity.this,startupView.class);
//            startActivity(intent);
//        }



        show(myDatabase);

        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,additem.class);
                startActivityForResult(i,1);
            }
        });;

        // 菜单栏
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);

        }

        navigationView.setCheckedItem(R.id.activityday);
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
        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final item temp_item=list.get(position);
                Intent intent=new Intent(MainActivity.this,UpdateActivity.class);
                intent.putExtra("item",temp_item);
                startActivityForResult(intent,1);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final item temp_item=list.get(position);
                AlertDialog.Builder dialog=new AlertDialog.Builder(MainActivity.this);
                dialog.setMessage("shifoushanchu");
                dialog.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                dialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        myDatabase.deleteByid(temp_item.id);
                        show(myDatabase);
                    }
                });
                dialog.show();
                return true;
            }
        });
        */
        // -------------------------------------------------
    }

    //找到控件
    public void init(){
        listView=(ListView) findViewById(R.id.list_item);
        mdrawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView=(NavigationView)findViewById(R.id.nav_view);
        actionBar=getSupportActionBar();
        buttonadd=(Button)findViewById(R.id.buttonadd);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerview);
    }

    public void show(MyDatabase myDatabase){
        list=new ArrayList<item>();
        /*arrayList=new ArrayList<>();
        simpleAdapter=new SimpleAdapter(this,arrayList,R.layout.item_detail,new String[]{"content","time","labeltype"},
                new int[]{R.id.textconent,R.id.textlabel,R.id.itemicon});
        listView.setAdapter(simpleAdapter);*/
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
                String icontype="";
                /*switch(label){
                    case "working":
                        icontype="W";
                        break;
                    case "studying":
                        icontype="S";
                        break;
                    case "living":
                        icontype="L";
                        break;
                }
                Map<String,Object> temp=new LinkedHashMap<>();
                temp.put("content",content);
                temp.put("time",finishyear+"-"+finishmonth+"-"+finishday+" "+finishhour+"时: "+finishmin+"分");
                temp.put("labeltype",icontype);
                //arrayList.add(temp);*/
                item temp_item=new item(id,label,content,startyear,startmonth,startday,starthour,startmin,
                        finishyear,finishmonth,finishday,finishhour,finishmin);
                temp_item.setImgpath(imagepath);temp_item.setVideopath(videopath);temp_item.setIsfinish(isfinish);
                list.add(temp_item);
            }while(cursor.moveToNext());

        }

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        madapter=new Madapter(list);
        recyclerView.setAdapter(madapter);
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

    @Override
    protected void onRestart() {
        navigationView.setCheckedItem(R.id.activityday);
        super.onRestart();
    }
}
