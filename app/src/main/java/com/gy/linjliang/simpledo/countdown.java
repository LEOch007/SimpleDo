package com.gy.linjliang.simpledo;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class countdown extends AppCompatActivity {
    private MyAdapter adapter;
    private List<countdown_item> countdownList;
    private MyDatabase myDatabase;
    private DrawerLayout mdrawerLayout;
    private NavigationView navigationView;
    private ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.countdown);

        // 界面控件
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.countdown_listview);
        final ProgressBar progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        mdrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        actionBar = getSupportActionBar();

        // 菜单栏
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_launcher);
        }

        navigationView.setCheckedItem(R.id.activityCountdown);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                Intent intent;
                navigationView.setCheckedItem(id);
                switch (id){
                    case R.id.activityday:
                        intent = new Intent(countdown.this,Datetask.class);
                        startActivity(intent);
                        break;
                    case R.id.activityCountdown:
                        intent = new Intent(countdown.this,countdown.class);
                        startActivity(intent);
                        break;
                    case R.id.activitySummary:
                        intent = new Intent(countdown.this,Summary.class);
                        startActivity(intent);
                        break;
                    case R.id.activitypotato:
                        intent = new Intent(countdown.this,Workclock.class);
                        startActivity(intent);
                        break;
                    case R.id.activityMain:
                        intent = new Intent(countdown.this,MainActivity.class);
                        startActivity(intent);
                        break;
                }
                mdrawerLayout.closeDrawers();
                return true;
            }
        });
        // -------------------------------------------------

        // RecyclerView 相关设置
        countdownList = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new MyAdapter(countdownList);
        recyclerView.setAdapter(adapter);

        myDatabase = new MyDatabase(this,"Finalitem.db",null,1);
        SQLiteDatabase db = myDatabase.getReadableDatabase();

        Cursor cursor = db.rawQuery("select * from item order by startyear ASC,startmonth ASC,startday ASC",null);

        int contentIndex = cursor.getColumnIndex("content");
        int startyearIndex = cursor.getColumnIndex("startyear");
        int startmonthIndex = cursor.getColumnIndex("startmonth");
        int startdayIndex = cursor.getColumnIndex("startday");

        Calendar Ctoday = Calendar.getInstance();
        Date Dtoday = new Date();
        Ctoday.setTime(Dtoday);

        for (cursor.moveToFirst();!(cursor.isAfterLast());cursor.moveToNext()) {
            String content = cursor.getString(contentIndex);
            String startyear = cursor.getString(startyearIndex);
            String startmonth = cursor.getString(startmonthIndex);
            String startday = cursor.getString(startdayIndex);
            //
            Calendar date = Calendar.getInstance();
            date.set(Integer.parseInt(startyear),Integer.parseInt(startmonth) - 1,Integer.parseInt(startday));

            // 得微秒级时间差
            long val = date.getTimeInMillis() - Ctoday.getTimeInMillis();

            if(val < 0){
                continue;
            }
            // 换算后得到天数
            int day = (int) (val / (1000 * 60 * 60 * 24));

            String paran_date = startyear + "-" + startmonth + "-" +  startday;
            countdown_item c_item = new countdown_item(content,String.valueOf(day),paran_date);
            countdownList.add(c_item);
        }
    }

    private class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        private List<countdown_item> countdownList;

        public MyAdapter(List<countdown_item> countdownList){
            this.countdownList = countdownList;
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            private View item_view;
            private TextView c_content;
            private TextView c_day;

            public ViewHolder(View view)
            {
                super(view);
                item_view = view;
                c_content = (TextView) view.findViewById(R.id.c_content);
                c_day = (TextView) view.findViewById(R.id.c_day);
            }
        }

        //创建item视图，并返回相应的viewHolder
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.countdown_item,parent,false);
            final ViewHolder holder = new ViewHolder(view);

            holder.item_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(countdown.this,MainActivity.class);
                    int position = holder.getAdapterPosition();
                    countdown_item item = countdownList.get(position);
                    intent.putExtra("date",item.getDate());
                    startActivity(intent);
                }
            });

            return holder;
        }

        //绑定数据到正确的item视图上
        @Override
        public void onBindViewHolder(ViewHolder holder,int position)
        {
            countdown_item c_item = countdownList.get(position);
            holder.c_content.setText(c_item.getContent());
            holder.c_day.setText(c_item.getCountDay());
        }

        @Override
        public int getItemCount() {
            return countdownList.size();
        }
    }

}
