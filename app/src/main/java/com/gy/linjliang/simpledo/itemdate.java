package com.gy.linjliang.simpledo;

import java.io.Serializable;

/**
 * Created by lenovo on 2018/1/6.
 */

public class itemdate implements Serializable {
    private String date;
    private String datecon;
    private String datenum;

    public itemdate(String sdate,String sdatecon,String sdatenum){
        this.date = sdate;
        this.datecon = sdatecon;
        this.datenum = sdatenum;
    }

    public String getDate(){return date;}
    public String getDatecon(){return datecon;}
    public String getDatenum(){return datenum;}

    public void setDate(String sdate){date = sdate;}
    public void setDatecon(String sdatecon){datecon = sdatecon;}
    public void setDatenum(String sdatenum){datenum = sdatenum;}
}
