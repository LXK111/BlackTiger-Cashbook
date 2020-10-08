package com.example.demosignin;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {


    Button mBtn_login;//老用户登录按钮
    Button mBtn_register;//新用户注册按钮
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtn_login = findViewById(R.id.btn_login);
        //进入老用户登录界面
        mBtn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,LoginPicforOlduserActivity.class);
                startActivity(intent);
            }
        });

        mBtn_register=findViewById(R.id.btn_register);
        //进入新用户注册界面
        mBtn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SetCipherActivity.class);
                startActivity(intent);
            }
        });




    }




}