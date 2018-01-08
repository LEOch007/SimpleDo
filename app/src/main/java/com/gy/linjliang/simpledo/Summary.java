package com.gy.linjliang.simpledo;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Summary extends AppCompatActivity {

    private ViewPager mIn_vp;
    private LinearLayout mIn_ll;
    private List<View> mViewList;
    private ImageView mLight_dots;
    private int mDistance;
    private ImageView mOne_dot;
    private ImageView mTwo_dot;
    private ImageView mThree_dot;
    private Button mBtn_next;
    private TextView text1;
    private TextView text2;
    private TextView text3;
    private String[] text_page1;
    private String[] text_page2;
    private String[] text_page3;
    private MyDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary);
        initView();
        initData();
        myDatabase = new MyDatabase(this,"Finalitem.db",null,1);
        myDatabase.insert(new item("10","1","这是一条今天数据1这是一条测试数据1这是一条测试数据1","2018","01","08","08","02","2018","01","11","09","02","0"));
        myDatabase.insert(new item("11","1","这是一条明天数据2","2018","01","09","08","02","2018","01","11","09","02","0"));
        myDatabase.insert(new item("12","1","这是一条昨天数据3","2018","01","07","08","02","2018","01","11","09","02","0"));
        myDatabase.insert(new item("13","1","22:55完成数据","2018","01","10","08","02","2018","01","11","22","55","1"));
        myDatabase.insert(new item("14","1","4:00完成数据","2018","01","10","08","02","2018","01","11","04","02","1"));

        SQLiteDatabase db = myDatabase.getReadableDatabase();
        Cursor cursor;
        String finalContent = "";


        // String 日期 DateStatics 存储该日期的任务情况，包括任务总数、未完成任务数、已完成任务数
        Map<String,DateStatics> dateMap = new HashMap<>();

        cursor = db.rawQuery("select * from item",null);
        for (cursor.moveToFirst();!(cursor.isAfterLast());cursor.moveToNext()) {
            String content = cursor.getString(cursor.getColumnIndex("content"));
            String startyear = cursor.getString(cursor.getColumnIndex("startyear"));
            String startmonth = cursor.getString(cursor.getColumnIndex("startmonth"));
            String startday = cursor.getString(cursor.getColumnIndex("startday"));
            String isfinish = cursor.getString(cursor.getColumnIndex("isfinish"));

            String date = startyear + "-" + startmonth + "-" +  startday;
            DateStatics info = new DateStatics();
            if(dateMap.containsKey(date)){
                DateStatics old_info = dateMap.get(date);
                if(isfinish.equals("0")){
                    old_info.undoAddOne();
                }else {
                    old_info.finishedAddOne();
                }
                dateMap.put(date,old_info);
            }
            else {
                if(isfinish.equals("0")){
                    info.undoAddOne();
                }else {
                    info.finishedAddOne();
                }

                dateMap.put(date,info);
            }
        }

        int maxFinishCnt = 0;
        int maxUndoCnt = 0;
        int maxCnt = 0;
        int allUndoCnt = 0;
        int allCnt = 0;
        String maxFinishDate = "";
        String maxUndoDate = "";
        int dateCnt = 0;

        for (Map.Entry<String, DateStatics> entry : dateMap.entrySet()) {
            dateCnt++;
            DateStatics info = entry.getValue();
            if(info.getCount() > maxCnt){
                maxCnt = info.getCount();
            }
            if(info.getCount() > maxFinishCnt){
                maxFinishCnt = info.getFinishedCnt();
                maxFinishDate = entry.getKey();
            }
            if(info.getCount() > maxUndoCnt){
                maxUndoCnt = info.getUndoCnt();
                maxUndoDate = entry.getKey();
                maxUndoDate = entry.getKey();
            }
            allUndoCnt += info.getUndoCnt();
            allCnt += info.getCount();
        }

        cursor = db.rawQuery("select content from item order by startyear DESC,startmonth DESC,startday DESC limit 1",null);
        cursor.moveToFirst();
        if(!cursor.isAfterLast()){
            finalContent = cursor.getString(cursor.getColumnIndex("content"));
        }

        String lateHour = "";
        String lateMin = "";
        String lateMonth = "";
        String lateDay = "";
        cursor = db.rawQuery("select * from item where finishhour < \"05\" or finishhour > \"21\" order by finishhour ASC",null);
        cursor.moveToFirst();
        if(!cursor.isAfterLast()){
            lateHour = cursor.getString(cursor.getColumnIndex("finishhour"));
            lateMin = cursor.getString(cursor.getColumnIndex("finishmin"));
            lateMonth = cursor.getString(cursor.getColumnIndex("finishmonth"));
            lateDay = cursor.getString(cursor.getColumnIndex("finishday"));
        }

        String sample_text1 = "这一年\n" +
                "有" + String.valueOf(dateCnt) + "天你都在SimpleDo上努力完成任务";

        String sample_text2 = "这一年里\n" +
                "你一共完成了" + String.valueOf(allCnt - allUndoCnt) + "个任务\n" +
                "很可惜的是，有" + String.valueOf(allUndoCnt) + "个任务没有完成";

        if(allUndoCnt == 0){
            sample_text2 = "这一年里\n" +
                    "你一共完成了" + String.valueOf(allCnt - allUndoCnt) + "个任务\n";
        }

        String sample_text3 = "这一年里\n" +
                "你每天都在晚上十点前完成了任务\n" +
                "很棒哦";

        if(!lateHour.equals("")){
            sample_text3 = lateMonth +"月" + lateDay + "号大概是比较辛苦的一天\n" +
                    "这一天你在 "+ lateHour + ":" + lateMin +" 完成了任务\n" +
                    "带着疲惫感和满足感开始入睡";
        }

        String sample_text4 = "在整一年中\n" +
                "你没有一件任务没有完成\n" +
                "简直棒呆！";

        if(allUndoCnt > 0){
            String month = maxUndoDate.split("-")[1];
            String day = maxUndoDate.split("-")[2];
            sample_text4 = month +"月" + day + "号\n" +
                    "可能是你悄悄偷懒的一天\n" +
                    "这一天，你有" + String.valueOf(maxUndoCnt) + "件任务没有完成";
        }

        String sample_text5 = "1月1号\n" +
                "这一天你效率超高\n" +
                "完成了" + String.valueOf(maxFinishCnt) + "个任务";

        if(!maxFinishDate.equals("")){
            String month = maxFinishDate.split("-")[1];
            String day = maxFinishDate.split("-")[2];
            sample_text5 = month +"月" + day + "号\n" +
                    "这一天你效率超高\n" +
                    "完成了" + String.valueOf(maxFinishCnt) + "个任务";
        }

        String sample_text6 = "你今年的最后一个任务是\n" +
                finalContent +
                "\n新年快乐~";

        String sample_text7 = "愿你的锦绣年华\n" +
                "在SimpleDo的陪伴下\n" +
                "越来越好~";
        String sample_text_empty = "";

        text_page1 = new String[]{sample_text1,sample_text2,sample_text3};
        text_page2 = new String[]{sample_text4,sample_text5,sample_text6};
        text_page3 = new String[]{sample_text7,sample_text_empty,sample_text_empty};

        mIn_vp.setAdapter(new ViewPagerAdapter(mViewList));
        addDots();
        moveDots();
        mIn_vp.setPageTransformer(true,new DepthPageTransformer());

        mBtn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void moveDots() {
        mLight_dots.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //获得两个圆点之间的距离
                mDistance = mIn_ll.getChildAt(1).getLeft() - mIn_ll.getChildAt(0).getLeft();
                mLight_dots.getViewTreeObserver()
                        .removeGlobalOnLayoutListener(this);
            }
        });
        mIn_vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //页面滚动时小白点移动的距离，并通过setLayoutParams(params)不断更新其位置
                float leftMargin = mDistance * (position + positionOffset);
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mLight_dots.getLayoutParams();
                params.leftMargin = (int) leftMargin;
                mLight_dots.setLayoutParams(params);
                if(position==2){
                    mBtn_next.setVisibility(View.VISIBLE);
                }
                if(position!=2&&mBtn_next.getVisibility()==View.VISIBLE){
                    mBtn_next.setVisibility(View.GONE);
                }

                switch (position){
                    case 0:
                        text1.setText(text_page1[0]);
                        text2.setText(text_page1[1]);
                        text3.setText(text_page1[2]);
                        break;
                    case 1:
                        text1.setText(text_page2[0]);
                        text2.setText(text_page2[1]);
                        text3.setText(text_page2[2]);
                        break;
                    case 2:
                        text1.setText(text_page3[0]);
                        text2.setText(text_page3[1]);
                        text3.setText(text_page3[2]);
                        break;
                }
            }

            @Override
            public void onPageSelected(int position) {
                //页面跳转时，设置小圆点的margin
                float leftMargin = mDistance * position;
                RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mLight_dots.getLayoutParams();
                params.leftMargin = (int) leftMargin;
                mLight_dots.setLayoutParams(params);
                if(position==2){
                    mBtn_next.setVisibility(View.VISIBLE);
                }
                if(position != 2 && mBtn_next.getVisibility() == View.VISIBLE){
                    mBtn_next.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void addDots() {
        mOne_dot = new ImageView(this);
        mOne_dot.setImageResource(R.drawable.gray_dot);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(0, 0, 40, 0);
        mIn_ll.addView(mOne_dot, layoutParams);
        mTwo_dot = new ImageView(this);
        mTwo_dot.setImageResource(R.drawable.gray_dot);
        mIn_ll.addView(mTwo_dot, layoutParams);
        mThree_dot = new ImageView(this);
        mThree_dot.setImageResource(R.drawable.gray_dot);
        mIn_ll.addView(mThree_dot, layoutParams);
        setClickListener();
    }

    private void setClickListener() {
        mOne_dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIn_vp.setCurrentItem(0);
            }
        });
        mTwo_dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIn_vp.setCurrentItem(1);
            }
        });
        mThree_dot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIn_vp.setCurrentItem(2);
            }
        });
    }


    private void initData() {
        mViewList = new ArrayList<View>();
        LayoutInflater lf = getLayoutInflater().from(Summary.this);
        View view1 = lf.inflate(R.layout.viewpager4, null);
        View view2 = lf.inflate(R.layout.viewpager5, null);
        View view3 = lf.inflate(R.layout.viewpager6, null);
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
    }

    private void initView() {
        mIn_vp = (ViewPager) findViewById(R.id.in_viewpager);
        mIn_ll = (LinearLayout) findViewById(R.id.in_ll);
        mLight_dots = (ImageView) findViewById(R.id.iv_light_dots);
        mBtn_next = (Button) findViewById(R.id.bt_next);
        text1 = (TextView) findViewById(R.id.Stext1);
        text2 = (TextView) findViewById(R.id.Stext2);
        text3 = (TextView) findViewById(R.id.Stext3);
    }

}
