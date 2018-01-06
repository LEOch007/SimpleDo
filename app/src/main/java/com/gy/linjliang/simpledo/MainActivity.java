package com.gy.linjliang.simpledo;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button datetask; private Button working;
    private Button buttonadd;
    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private List<Map<String,Object>> arrayList;
    private MyDatabase myDatabase=new MyDatabase(this,"Item.db",null,1);
    private List<item> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        datetask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Datetask.class);
                startActivity(intent);
            }
        });
        show(myDatabase);

        buttonadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,additem.class);
                startActivityForResult(i,1);
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
                return false;
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final item temp_item=list.get(position);
                Intent intent=new Intent(MainActivity.this,UpdateActivity.class);
                intent.putExtra("item",temp_item);
                startActivityForResult(intent,1);
            }
        });
    }
    //找到控件
    public void init(){
        datetask = (Button)findViewById(R.id.datetask);
        working = (Button)findViewById(R.id.working);
        listView=(ListView) findViewById(R.id.list_item);
        buttonadd=(Button)findViewById(R.id.buttonadd);
    }
    public void show(MyDatabase myDatabase){
        list=new ArrayList<item>();
        arrayList=new ArrayList<>();
        simpleAdapter=new SimpleAdapter(this,arrayList,R.layout.item_detail,new String[]{"content","label"},
                new int[]{R.id.textconent,R.id.textlabel});
        listView.setAdapter(simpleAdapter);
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
                String finishyear=cursor.getString(cursor.getColumnIndex("finishyear"));
                String finishmonth=cursor.getString(cursor.getColumnIndex("finishmonth"));
                String finishday=cursor.getString(cursor.getColumnIndex("finishday"));
                String isfinish=cursor.getString(cursor.getColumnIndex("isfinish"));
                String imagepath=cursor.getString(cursor.getColumnIndex("imgpath"));
                String videopath=cursor.getString(cursor.getColumnIndex("videopath"));

                Map<String,Object> temp=new LinkedHashMap<>();
                temp.put("content",content);
                temp.put("label",label);
                arrayList.add(temp);
                item temp_item=new item(id,label,content,startyear,startmonth,startday,finishyear,finishmonth,finishday);
                temp_item.setImgpath(imagepath);temp_item.setVideopath(videopath);temp_item.setIsfinish(isfinish);
                list.add(temp_item);
            }while(cursor.moveToNext());

        }
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
}
