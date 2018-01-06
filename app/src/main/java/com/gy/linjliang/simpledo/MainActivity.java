package com.gy.linjliang.simpledo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button datetask; private Button working;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        datetask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Datetask.class);
                startActivity(intent);
            }
        });
    }
    //找到控件
    public void init(){
        datetask = (Button)findViewById(R.id.datetask);
        working = (Button)findViewById(R.id.working);
    }
}
