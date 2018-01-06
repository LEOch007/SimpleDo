package com.gy.linjliang.simpledo;

import android.app.Activity;
import android.content.SharedPreferences;
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

public class additem extends Activity {
    private RadioGroup radioGroup;
    private Button buttonsubmit;
    private EditText exitcontent;
    String id;
    String label="working";
    String content;
    String startyear="2017";
    String startmonth="1";
    String startday="1";
    String finishyear="2017";
    String finishmonth="1";
    String finishday="2";
    String isfinish;   //0:unfinished   1:finished
    String imgpath;
    String videopath;
    private MyDatabase mydb;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        radioGroup=(RadioGroup) findViewById(R.id.radiolabel);
        buttonsubmit=(Button)findViewById(R.id.buttonsubmit);
        exitcontent=(EditText) findViewById(R.id.editcontnet);
        mydb=new MyDatabase(this,"Item.db",null,1);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                int radioButtonId=group.getCheckedRadioButtonId();
                RadioButton rb=(RadioButton)additem.this.findViewById(radioButtonId);
                label=rb.getText().toString();
                //Toast.makeText(additem.this,label,Toast.LENGTH_SHORT).show();
            }
        });

        buttonsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref=getSharedPreferences("itemid",MODE_PRIVATE);
                int count=pref.getInt("id",0);
                id=""+count;
                Toast.makeText(additem.this,id,Toast.LENGTH_SHORT).show();

                content=exitcontent.getText().toString();

                final item i=new item(id,label,content,startyear,startmonth,startday,finishyear,finishmonth,finishday);
                mydb.insert(i);

                count++;
                SharedPreferences.Editor editor=getSharedPreferences("itemid",MODE_PRIVATE).edit();
                editor.putInt("id",count);
                editor.apply();
                finish();
            }
        });

    }


}