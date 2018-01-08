package com.gy.linjliang.simpledo;

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

import java.util.ArrayList;
import java.util.List;

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
        //
        itemdates.add(new itemdate("8月27日","跑步健身\n下馆子逛超市\n看《芳华》","4"));
        itemdates.add(new itemdate("8月28日","朗读VOA的英语文章\n一周足球\n","2"));
        itemdates.add(new itemdate("8月28日","朗读VOA的英语文章\n一周足球\n","2"));
        itemdates.add(new itemdate("8月28日","朗读VOA的英语文章\n一周足球\n","2"));
        itemdates.add(new itemdate("8月28日","朗读VOA的英语文章\n一周足球\n","2"));
        itemdates.add(new itemdate("8月28日","朗读VOA的英语文章\n一周足球\n","2"));
        itemdates.add(new itemdate("8月28日","朗读VOA的英语文章\n一周足球\n","2"));
        itemdates.add(new itemdate("8月28日","朗读VOA的英语文章\n一周足球\n","2"));
        itemdates.add(new itemdate("8月28日","朗读VOA的英语文章\n一周足球\n","2"));
        itemdates.add(new itemdate("8月28日","朗读VOA的英语文章\n一周足球\n","2"));
        itemdates.add(new itemdate("8月28日","朗读VOA的英语文章\n一周足球\n","2"));
        itemdates.add(new itemdate("8月28日","朗读VOA的英语文章\n一周足球\n","2"));
        itemdates.add(new itemdate("8月28日","朗读VOA的英语文章\n一周足球\n","2"));
        //
        myAdapter = new MyAdapter(this,itemdates);
        rview.setLayoutManager(new LinearLayoutManager(this)); //垂直布局
        rview.setAdapter(myAdapter);

    }
    //找到控件
    public void init(){
        rview = (RecyclerView)findViewById(R.id.rview);
        mdrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        actionBar = getSupportActionBar();
    }
}
