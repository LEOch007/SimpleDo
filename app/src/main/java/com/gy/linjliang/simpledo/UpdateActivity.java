package com.gy.linjliang.simpledo;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

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

        radioGroup=(RadioGroup)findViewById(R.id.updatelabel);
        buttonsubmit=(Button) findViewById(R.id.updatesubmit);
        exitcontent=(EditText)findViewById(R.id.updatecontnet);
        buttonworking=(RadioButton) findViewById(R.id.updatelabelworking);
        buttonstudying=(RadioButton)findViewById(R.id.updatelabelstudying);
        buttonliving=(RadioButton)findViewById(R.id.updatelabelliving);


        exitcontent.setText(content);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int radioButtonId=group.getCheckedRadioButtonId();
                RadioButton rb=(RadioButton)UpdateActivity.this.findViewById(radioButtonId);
                label=rb.getText().toString();
            }
        });

    }
}

