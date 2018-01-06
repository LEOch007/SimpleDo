package com.gy.linjliang.simpledo;

import java.io.Serializable;

/**
 * Created by lenovo on 2018/1/6.
 */

public class item implements Serializable {
    String id;
    String label;
    String content;
    String startyear;
    String startmonth;
    String startday;
    String finishyear;
    String finishmonth;
    String finishday;
    String isfinish;   //0:unfinished   1:finished
    String imgpath;
    String videopath;

    public item(String id,String label,String content,String startyear,String startmonth,String startday,String finishyear,String finishmonth,String finishday){
        this.id=id;
        this.label=label;
        this.content=content;
        this.startyear=startyear;
        this.startmonth=startmonth;
        this.startday=startday;
        this.finishyear=finishyear;
        this.finishmonth=finishmonth;
        this.finishday=finishday;
        isfinish="0";
        imgpath=null;
        videopath=null;
    }

    public String getLabel(){return label;}
    public String getContent(){return content;}

    public String getStartyear(){return startyear;}
    public String getStartmonth() {return startmonth;}
    public String getStartday() {return startday;}

    public String getFinishyear() {return finishyear;}
    public String getFinishmonth() {return finishmonth;}
    public String getFinishday() {return finishday;}

    public String getIsfinish() {return isfinish;}

    public String getImgpath() {return imgpath;}
    public String getVideopath() {return videopath;}

    public void setImgpath(String imgpath) {this.imgpath = imgpath;}
    public void setVideopath(String videopath) {this.videopath = videopath;}

    public String getId() {return id;}
    public void setIsfinish(String isfinish){this.isfinish=isfinish;}
}

