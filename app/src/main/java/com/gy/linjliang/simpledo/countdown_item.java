package com.gy.linjliang.simpledo;

// 定义返回数据的结构
public class countdown_item {
    private String c_content;
    private String c_day;
    private String date;

    public countdown_item(String c_content,String c_day,String date){
        this.c_content=c_content;
        this.c_day=c_day;
        this.date = date;
    }

    public String getContent(){return c_content;}
    public String getCountDay(){return c_day;}


    public String getDate() {
        return date;
    }
}
