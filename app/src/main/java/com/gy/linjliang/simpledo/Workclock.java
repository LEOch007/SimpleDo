package com.gy.linjliang.simpledo;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by lenovo on 2018/1/6.
 */

public class Workclock extends AppCompatActivity {
    private TextView myclk;
    private CountDownTimer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workclock);
        init();
        //设置时间
        myclk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DateChooseWheelViewDialog mDateChooseDialog = new DateChooseWheelViewDialog(Workclock.this, new DateChooseWheelViewDialog.DateChooseInterface(){
                    @Override
                    public void getDateTime(String time, boolean longTimeChecked) {
                        String times[] = time.split(":");
                        if(Integer.parseInt(times[0])/10==0){times[0] = "0"+times[0];}
                        if(Integer.parseInt(times[1])/10==0){times[1] = "0"+times[1];}
                        String clocktime = times[0]+":"+times[1]+":"+"00";
                        myclk.setText(clocktime);
                    }
                });
                mDateChooseDialog.setTimePickerGone(true);
                mDateChooseDialog.setDateDialogTitle("倒计时长");
                mDateChooseDialog.showDateChooseDialog();
            }
        });
        //取时间
        String lefttime = myclk.getText().toString();
        String[] stime = lefttime.split(":");
        int sumsec = 0; //总秒数
        int weight[] = {3600,60,1}; //加权
        for(int i=0;i<stime.length;i++){
            sumsec += Integer.parseInt(stime[i])*weight[i];
        }
        //倒计时
        timer = new CountDownTimer(sumsec*1000, 1000){ //第一个参数开始到结束时长 第二个参数每次onTick时长
            @Override
            public void onTick(long millisUntilFinished) {
                int nowtime = (int)millisUntilFinished/1000;
                int h = nowtime/3600; nowtime = nowtime%3600;
                int m = nowtime/60;
                int s = nowtime%60;
                String hour = h/10==0 ? "0"+h : ""+h ;
                String minute = m/10==0 ? "0"+m : ""+m ;
                String second = s/10==0 ? "0"+s : ""+s ;
                String all = hour+":"+minute+":"+second;
                myclk.setText(all);
            }
            @Override
            public void onFinish() {
                Toast.makeText(Workclock.this,"不可思议",Toast.LENGTH_SHORT).show();
            }
        };
        //timer.start();
    }
    //找到控件
    public void init(){
        myclk = (TextView)findViewById(R.id.myclk);

    }
}
