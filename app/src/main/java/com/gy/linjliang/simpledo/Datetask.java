package com.gy.linjliang.simpledo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2018/1/6.
 */

public class Datetask extends AppCompatActivity {
    private RecyclerView rview;
    private MyAdapter myAdapter;
    private List<itemdate> itemdates;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datetask);
        init();
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
    }
}
