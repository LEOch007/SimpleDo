package com.gy.linjliang.simpledo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
    String finishyear;
    String finishmonth;
    String finishday;
    String isfinish;   //0:unfinished   1:finished
    String imgpath;
    String videopath;
    private RadioGroup radioGroup;
    private Button buttonsubmit;
    private EditText exitcontent;
    private RadioButton buttonworking;
    private RadioButton buttonstudying;
    private RadioButton buttonliving;
    private MyDatabase myDatabase=new MyDatabase(this,"Item.db",null,1);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        final item i=(item) getIntent().getSerializableExtra("item");
        id=i.getId();label=i.getLabel();content=i.getContent();startyear=i.getStartyear();
        startmonth=i.getStartmonth();startday=i.getStartday();finishyear=i.getFinishyear();
        finishmonth=i.getFinishmonth();finishday=i.getFinishday();
        isfinish=i.getIsfinish();imgpath=i.getImgpath();
        videopath=i.getVideopath();
        Toast.makeText(UpdateActivity.this,id,Toast.LENGTH_SHORT).show();

        radioGroup=(RadioGroup)findViewById(R.id.updatelabel);
        buttonsubmit=(Button) findViewById(R.id.updatesubmit);
        exitcontent=(EditText)findViewById(R.id.updatecontnet);
        buttonworking=(RadioButton) findViewById(R.id.updatelabelworking);
        buttonstudying=(RadioButton)findViewById(R.id.updatelabelstudying);
        buttonliving=(RadioButton)findViewById(R.id.updatelabelliving);

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
                final item item_temp=new item(id,label,content,startyear,startmonth,startday,finishyear,finishmonth,finishday);
                item_temp.setIsfinish(isfinish);item_temp.setImgpath(imgpath);item_temp.setVideopath(videopath);
                myDatabase.update(item_temp);
                finish();

            }
        });

    }
}

