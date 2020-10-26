package com.example.blacktiger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.blacktiger.login.LogoActivity;
import com.example.blacktiger.login.SetCipherfornewActivity;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    private ImageView firstview;//用户第一眼见到的图案
    String save_pattern_key = "pattern_code";//存储图形密码的路径
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获取图形密码
        Paper.init(this);
        final String save_pattern = Paper.book().read(save_pattern_key);

        firstview = findViewById(R.id.iv_blacktiger);
        firstview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //若图形密码非空，表示是老用户
                if(save_pattern != null && !save_pattern.equals("null")){
                    Intent intent = new Intent(MainActivity.this, LogoActivity.class);
                    startActivity(intent);

                }
                //表示是新用户，跳入初次设置密码界面
                else{
                    Intent intent = new Intent(MainActivity.this, SetCipherfornewActivity.class);
                    startActivity(intent);
                }

//                Intent intent = new Intent(MainActivity.this,SetCipherfornewActivity.class);
//                    startActivity(intent);
            }
        });

    }
}