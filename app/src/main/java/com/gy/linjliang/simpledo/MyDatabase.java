package com.gy.linjliang.simpledo;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by admin on 2018/1/6.
 */

public class MyDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME="Item.db";
    private static final int DB_VERSION=1;
    private static final String TABLE_NAME="item";
    private static final String SQL_CREATE_TABLE="create table "+TABLE_NAME
            +"(id text not null primary key,"
            +"label text not null,"
            +"content text not null,"
            +"startyear text not null,"
            +"startmonth text not null,"
            +"startday text not null,"
            +"finishyear text not null,"
            +"finishmonth text not null,"
            +"finishday text not null,"
            +"isfinish text,"
            +"imgpath text,"
            +"videopath text);";

    public MyDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void insert(item i){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values=new ContentValues();

        values.put("id",i.getId());
        values.put("label",i.getLabel());values.put("content",i.getContent());
        values.put("startyear",i.getStartyear());values.put("startmonth",i.getStartmonth());
        values.put("startday",i.getStartday());values.put("finishyear",i.getFinishyear());
        values.put("finishmonth",i.getFinishmonth());values.put("finishday",i.getFinishday());
        values.put("isfinish",i.getIsfinish());values.put("imgpath",i.getImgpath());
        values.put("videopath",i.getVideopath());

        db.insert(TABLE_NAME,null,values);
        Log.d("123","hhh");
        db.close();
    }

    public void deleteByid(String id){
        SQLiteDatabase db=getWritableDatabase();
        String whereClause="id like ?";
        String[] whereArgs={id};
        db.delete(TABLE_NAME,whereClause,whereArgs);
    }

    public void update(item i){
        SQLiteDatabase db=getWritableDatabase();
        String whereClause="id like?";
        String[] whereArgs={i.getId()};
        ContentValues values=new ContentValues();

        values.put("label",i.getLabel());values.put("content",i.getContent());
        values.put("startyear",i.getStartyear());values.put("startmonth",i.getStartmonth());
        values.put("startday",i.getStartday());values.put("finishyear",i.getFinishyear());
        values.put("finishmonth",i.getFinishmonth());values.put("finishday",i.getFinishday());
        values.put("isfinish",i.getIsfinish());values.put("imgpath",i.getImgpath());
        values.put("videopath",i.getVideopath());
        db.update(TABLE_NAME,values,whereClause,whereArgs);
        db.close();
    }

}
