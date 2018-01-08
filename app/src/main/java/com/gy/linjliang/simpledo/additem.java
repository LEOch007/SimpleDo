package com.gy.linjliang.simpledo;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by admin on 2018/1/6.
 */

public class additem extends Activity {
    private RadioGroup radioGroup;
    private ImageButton buttonsubmit;
    private EditText exitcontent;
    String id;
    String label="working";
    String content;
    String startyear="2017";
    String startmonth="1";
    String startday="1";
    String starthour="12";
    String startmin="10";
    String finishyear="2017";
    String finishmonth="1";
    String finishday="2";
    String finishhour="1";
    String finishmin="2";
    String isfinish;   //0:unfinished   1:finished
    String imgpath;
    String videopath;
    private MyDatabase mydb;
    private TextView starttime;
    private TextView starttimwshow;
    private TextView finishtime;
    private TextView finishtimwshow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        radioGroup=(RadioGroup) findViewById(R.id.radiolabel);
        buttonsubmit=(ImageButton) findViewById(R.id.buttonsubmit);
        exitcontent=(EditText) findViewById(R.id.editcontnet);
        mydb=new MyDatabase(this,"Finalitem.db",null,1);
        starttime=(TextView) findViewById(R.id.starttime);
        starttimwshow=(TextView)findViewById(R.id.starttimeshow);
        finishtime=(TextView) findViewById(R.id.finishtime);
        finishtimwshow=(TextView)findViewById(R.id.finishtimeshow);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int radioButtonId=group.getCheckedRadioButtonId();
                RadioButton rb=(RadioButton)additem.this.findViewById(radioButtonId);
                label=rb.getText().toString();
            }
        });

        starttime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateChooseWheelViewDialog mDateChooseDialog = new DateChooseWheelViewDialog(additem.this, new DateChooseWheelViewDialog.DateChooseInterface(){
                    @Override
                    public void getDateTime(String time, boolean longTimeChecked) {
                        starttimwshow.setText(time);
                    }
                });
                mDateChooseDialog.setDateDialogTitle("选择开始时间");
                mDateChooseDialog.showDateChooseDialog();
            }
        });

        finishtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DateChooseWheelViewDialog mDateChooseDialog = new DateChooseWheelViewDialog(additem.this, new DateChooseWheelViewDialog.DateChooseInterface(){
                    @Override
                    public void getDateTime(String time, boolean longTimeChecked) {
                        finishtimwshow.setText(time);
                    }
                });
                mDateChooseDialog.setDateDialogTitle("选择结束时间");
                mDateChooseDialog.showDateChooseDialog();
            }
        });

        buttonsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean flag=true;
                SharedPreferences pref=getSharedPreferences("itemid",MODE_PRIVATE);
                int count=pref.getInt("id",0);
                id=""+count;

                content=exitcontent.getText().toString();
                final String timestart=starttimwshow.getText().toString();
                final String timefinish=finishtimwshow.getText().toString();

                if(content.equals("")){
                    Toast.makeText(additem.this,"内容不能为空",Toast.LENGTH_SHORT).show();
                    flag=false;
                }else{
                    if(timestart.equals("")){
                        Toast.makeText(additem.this,"开始时间不能为空",Toast.LENGTH_SHORT).show();
                        flag=false;
                    }else{

                        String s1[]=timestart.split(" ");
                        String s2[]=s1[0].split("-");
                        startyear=s2[0];
                        if(Integer.parseInt(s2[1])/10==0){startmonth="0"+s2[1];}else{startmonth=s2[1];}
                        if(Integer.parseInt(s2[2])/10==0){startday="0"+s2[2];}else{startday=s2[2];}

                        String s3[]=s1[1].split(":");
                        String s4=s3[0].substring(0,s3[0].length()-1);
                        String s5=s3[1].substring(0,s3[1].length()-1);
                        if(Integer.parseInt(s4)/10==0){starthour="0"+s4;}else{starthour=s4;}
                        if(Integer.parseInt(s5)/10==0){startmin="0"+s5;}else{startmin=s5;}

                    }
                    if(timefinish.equals("")){
                        Toast.makeText(additem.this,"结束时间不能为空",Toast.LENGTH_SHORT).show();
                        flag=false;
                    }else{

                        String s11[]=timefinish.split(" ");
                        String s22[]=s11[0].split("-");
                        finishyear=s22[0];
                        if(Integer.parseInt(s22[1])/10==0){finishmonth="0"+s22[1];}else{finishmonth=s22[1];}
                        if(Integer.parseInt(s22[2])/10==0){finishday="0"+s22[2];}else{finishday=s22[2];}

                        String s33[]=s11[1].split(":");
                        String s44=s33[0].substring(0,s33[0].length()-1);
                        String s55=s33[1].substring(0,s33[1].length()-1);
                        if(Integer.parseInt(s44)/10==0){finishhour="0"+s44;}else{finishhour=s44;}
                        if(Integer.parseInt(s55)/10==0){finishmin="0"+s55;}else{finishmin=s55;}
                        //Toast.makeText(additem.this,finishyear+" "+finishmonth+" "+finishday+" "+finishhour+" "+finishmin,Toast.LENGTH_SHORT).show();

                    }
                }


                if(flag==true){
                    final item i=new item(id,label,content,startyear,startmonth,startday,starthour,startmin,
                            finishyear,finishmonth,finishday,finishhour,finishmin);
                    mydb.insert(i);

                    count++;
                    SharedPreferences.Editor editor=getSharedPreferences("itemid",MODE_PRIVATE).edit();
                    editor.putInt("id",count);
                    editor.apply();
                    finish();
                }


            }
        });

    }


}