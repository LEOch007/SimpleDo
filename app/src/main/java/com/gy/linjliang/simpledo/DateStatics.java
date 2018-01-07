package com.gy.linjliang.simpledo;

/**
 * Created by howe on 2018/1/7.
 */

public class DateStatics {
    private int count;
    private int finishedCnt;
    private int undoCnt;

    public DateStatics(){
        count = 0;
        finishedCnt = 0;
        undoCnt = 0;
    }

    public void finishedAddOne(){
        finishedCnt++;
        count++;
    }

    public int getFinishedCnt(){
        return finishedCnt;
    }

    public void undoAddOne(){
        undoCnt++;
        count++;
    }

    public int getUndoCnt(){
        return undoCnt;
    }

    public int getCount(){
        return count;
    }
}
