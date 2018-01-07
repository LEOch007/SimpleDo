package com.gy.linjliang.simpledo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by admin on 2018/1/6.
 */

public class UpdateActivity extends Activity {
    String id;
    String label;
    String content;
    String startyear;
    String startmonth;
    String startday;
    String starthour;
    String startmin;
    String finishyear;
    String finishmonth;
    String finishday;
    String finishhour;
    String finishmin;
    String isfinish;   //0:unfinished   1:finished
    String imgpath;
    String videopath;
    private RadioGroup radioGroup;
    private Button buttonsubmit;
    private EditText exitcontent;
    private RadioButton buttonworking;
    private RadioButton buttonstudying;
    private RadioButton buttonliving;
    private MyDatabase myDatabase=new MyDatabase(this,"Finalitem.db",null,1);
    private TextView starttime;
    private TextView finishtime;
    private TextView starttiemshow;
    private TextView finishtimeshow;
    private ImageView finishicon;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        final item i=(item) getIntent().getSerializableExtra("item");
        id=i.getId();label=i.getLabel();content=i.getContent();startyear=i.getStartyear();
        startmonth=i.getStartmonth();startday=i.getStartday();finishyear=i.getFinishyear();
        finishmonth=i.getFinishmonth();finishday=i.getFinishday();
        starthour=i.getStarthour();startmin=i.getStartmin();
        finishhour=i.getFinishhour();finishmin=i.getFinishmin();
        isfinish=i.getIsfinish();imgpath=i.getImgpath();
        videopath=i.getVideopath();
        //Toast.makeText(UpdateActivity.this,id,Toast.LENGTH_SHORT).show();

        radioGroup=(RadioGroup)findViewById(R.id.updatelabel);
        buttonsubmit=(Button) findViewById(R.id.updatesubmit);
        exitcontent=(EditText)findViewById(R.id.updatecontnet);
        buttonworking=(RadioButton) findViewById(R.id.updatelabelworking);
        buttonstudying=(RadioButton)findViewById(R.id.updatelabelstudying);
        buttonliving=(RadioButton)findViewById(R.id.updatelabelliving);
        starttime=(TextView) findViewById(R.id.updatestarttime);
        starttiemshow=(TextView)findViewById(R.id.updatestarttimeshow);
        finishtime=(TextView)findViewById(R.id.updatefinishtime);
        finishtimeshow=(TextView)findViewById(R.id.updatefinishtimeshow);
        finishicon=(ImageView) findViewById(R.id.finishicon);


        switch(label){
            case "working":
                buttonworking.setChecked(true);
                break;
            case "studying":
                buttonstudying.setChecked(true);
                break;
            case "living":
                buttonliving.setChecked(true);
                break;
        }

        exitcontent.setText(content);
        starttiemshow.setText(startyear+"-"+startmonth+"-"+startday+" "+starthour+"时:"+startmin+"分");
        finishtimeshow.setText(finishyear+"-"+finishmonth+"-"+finishday+" "+finishhour+"时:"+finishmin+"分");

        if(isfinish.equals("0")){
            //Toast.makeText(UpdateActivity.this,isfinish,Toast.LENGTH_SHORT).show();
            finishicon.setImageResource(R.mipmap.finish1);
        }else{
            //Toast.makeText(UpdateActivity.this,isfinish,Toast.LENGTH_SHORT).show();
            finishicon.setImageResource(R.mipmap.finish2);
        }

        finishicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isfinish.equals("0")){
                    isfinish="1";
                    finishicon.setImageResource(R.mipmap.finish2);
                }else{
                    isfinish="0";
                    finishicon.setImageResource(R.mipmap.finish1);
                }
            }
        });


        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateChooseWheelViewDialog mDateChooseDialog = new DateChooseWheelViewDialog(UpdateActivity.this, new DateChooseWheelViewDialog.DateChooseInterface(){
                    @Override
                    public void getDateTime(String time, boolean longTimeChecked) {
                        starttiemshow.setText(time);
                    }
                });
                mDateChooseDialog.setDateDialogTitle("choose_time");
                mDateChooseDialog.showDateChooseDialog();
            }
        });

        finishtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateChooseWheelViewDialog mDateChooseDialog = new DateChooseWheelViewDialog(UpdateActivity.this, new DateChooseWheelViewDialog.DateChooseInterface(){
                    @Override
                    public void getDateTime(String time, boolean longTimeChecked) {
                        finishtimeshow.setText(time);
                    }
                });
                mDateChooseDialog.setDateDialogTitle("choose_time");
                mDateChooseDialog.showDateChooseDialog();
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int radioButtonId=group.getCheckedRadioButtonId();
                RadioButton rb=(RadioButton)UpdateActivity.this.findViewById(radioButtonId);
                label=rb.getText().toString();
            }
        });

        buttonsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content=exitcontent.getText().toString();
                boolean flag=true;
                final String timestart=starttiemshow.getText().toString();
                final String timefinish=finishtimeshow.getText().toString();

                if(content.equals("")){
                    Toast.makeText(UpdateActivity.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                    flag=false;
                }else{
                    if(timestart.equals("")){
                        Toast.makeText(UpdateActivity.this,"开始时间不能为空",Toast.LENGTH_SHORT).show();
                        flag=false;
                    }else{

                        String s1[]=timestart.split(" ");
                        String s2[]=s1[0].split("-");
                        startyear=s2[0];
                        if(s2[1].length()<2){startmonth="0"+s2[1];}else{startmonth=s2[1];}
                        if(s2[2].length()<2){startday="0"+s2[2];}else{startday=s2[2];}

                        String s3[]=s1[1].split(":");
                        String s4=s3[0].substring(0,s3[0].length()-1);
                        String s5=s3[1].substring(0,s3[1].length()-1);
                        if(s4.length()<2){starthour="0"+s4;}else{starthour=s4;}
                        if(s5.length()<2){startmin="0"+s5;}else{startmin=s5;}

                    }
                    if(timefinish.equals("")){
                        Toast.makeText(UpdateActivity.this,"结束时间不能为空",Toast.LENGTH_SHORT).show();
                        flag=false;
                    }else{

                        String s11[]=timefinish.split(" ");
                        String s22[]=s11[0].split("-");
                        finishyear=s22[0];
                        if(s22[1].length()<2){finishmonth="0"+s22[1];}else{finishmonth=s22[1];}
                        if(s22[2].length()<2){finishday="0"+s22[2];}else{finishday=s22[2];}

                        String s33[]=s11[1].split(":");
                        String s44=s33[0].substring(0,s33[0].length()-1);
                        String s55=s33[1].substring(0,s33[1].length()-1);
                        if(s44.length()<2){finishhour="0"+s44;}else{finishhour=s44;}
                        if(s55.length()<2){finishmin="0"+s55;}else{finishmin=s55;}
                        //Toast.makeText(additem.this,finishyear+" "+finishmonth+" "+finishday+" "+finishhour+" "+finishmin,Toast.LENGTH_SHORT).show();

                    }
                }

                if(flag==true){
                    final item item_temp=new item(id,label,content,startyear,startmonth,startday,starthour,startmin,
                            finishyear,finishmonth,finishday,finishhour,finishmin);
                    item_temp.setIsfinish(isfinish);item_temp.setImgpath(imgpath);item_temp.setVideopath(videopath);
                    myDatabase.update(item_temp);
                    finish();
                }


            }
        });

    }
}

