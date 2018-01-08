package com.gy.linjliang.simpledo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by lenovo on 2018/1/6.
 */

public class Datetask extends AppCompatActivity {
    private RecyclerView rview;
    private MyAdapter myAdapter;
    private List<itemdate> itemdates;
    private DrawerLayout mdrawerLayout;
    private NavigationView navigationView;
    private ActionBar actionBar;

    private MyDatabase myDatabase=new MyDatabase(this,"Finalitem.db",null,1);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datetask);
        init();


        mdrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        actionBar = getSupportActionBar();

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
                        intent = new Intent(Datetask.this,Datetask.class);
                        startActivity(intent);
                        break;
                    case R.id.activityCountdown:
                        intent = new Intent(Datetask.this,countdown.class);
                        startActivity(intent);
                        break;
                    case R.id.activitySummary:
                        intent = new Intent(Datetask.this,Summary.class);
                        startActivity(intent);
                        break;
                    case R.id.activitypotato:
                        intent = new Intent(Datetask.this,Workclock.class);
                        startActivity(intent);
                        break;
                    case R.id.activityMain:
                        intent = new Intent(Datetask.this,MainActivity.class);
                        startActivity(intent);
                        break;
                }
                mdrawerLayout.closeDrawers();
                return true;
            }
        });
        // -------------------------------------------------

        /* -- RecyclerView -- */
        itemdates = new ArrayList<>();
        /*  计算当前时刻数据库情况 */
        List<String> ldate = new ArrayList<>(); //所有的日期
        SQLiteDatabase db=myDatabase.getReadableDatabase();
        Cursor cursor;
        cursor = db.rawQuery("select * from item order by startyear, startmonth, startday",null); //排序
        for (cursor.moveToFirst();!(cursor.isAfterLast());cursor.moveToNext()) {
            String startyear = cursor.getString(cursor.getColumnIndex("startyear"));
            String startmonth = cursor.getString(cursor.getColumnIndex("startmonth"));
            String startday = cursor.getString(cursor.getColumnIndex("startday"));

            String date = startyear + "-" + startmonth + "-" +  startday;
            ldate.add(date);
        }
        cursor.close();
        removeDuplicateWithOrder(ldate); //去重
        //遍历每一天
        for(int i=0;i<ldate.size();i++){
            itemdate idate = new itemdate(); //新一天

            String date = ldate.get(i);
            String dates[] = date.split("-");
            idate.setDate(dates[0]+"年"+dates[1]+"月"+dates[2]+"日"); //日期属性

            int sum = 0;
            String text = "";
            String sql = String.format("select * from item where startyear='%s' and startmonth='%s' and startday='%s'",dates[0],dates[1],dates[2]);
            cursor = db.rawQuery(sql,null);
            for (cursor.moveToFirst();!(cursor.isAfterLast());cursor.moveToNext()) {
                sum += 1;
                String cont = cursor.getString(cursor.getColumnIndex("content"));
                if(cont.length()>12){cont = cont.substring(0,12)+"...";} //过长
                if((sum==1)||(sum==2)){text = text+cont+"\n";}
                else if(sum==3){text = text+cont;}
                idate.setDatecon(text); //内容属性
            }
            idate.setDatenum(""+sum); //数量属性
            itemdates.add(idate); //加入列表
        }
        /* -------------------------------- */
        myAdapter = new MyAdapter(this,itemdates);
        rview.setLayoutManager(new LinearLayoutManager(this)); //垂直布局
        rview.setAdapter(myAdapter);
        myAdapter.setOnItemClickLitener(new MyAdapter.OnItemClickLitener() {
            //点击事件
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(Datetask.this,MainActivity.class);
                String sdate = itemdates.get(position).getDate();
                sdate = sdate.substring(0,4)+"-"+sdate.substring(5,7)+"-"+sdate.substring(8,10);
                intent.putExtra("date",sdate);
                startActivity(intent);
            }
            //长按事件
            @Override
            public boolean onItemLongClick(View view, int position) {
                return true;
            }
        });
    }
    //找到控件
    public void init(){
        rview = (RecyclerView)findViewById(R.id.rview);
        mdrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        actionBar = getSupportActionBar();
    }
    // 删除ArrayList中重复元素，保持顺序
    public static void removeDuplicateWithOrder(List list)  {
        Set set  =   new HashSet();
        List newList  =   new  ArrayList();
        for(Iterator iter = list.iterator(); iter.hasNext();)  {
            Object element  =  iter.next();
            if  (set.add(element)){newList.add(element);}
        }
        list.clear();
        list.addAll(newList);
    }
}
