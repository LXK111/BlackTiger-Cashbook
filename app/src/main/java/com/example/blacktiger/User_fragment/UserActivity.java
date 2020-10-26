package com.example.blacktiger.User_fragment;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.blacktiger.R;

public class UserActivity extends AppCompatActivity {

    private InitialScreen initialScreen;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.blacktiger.R.layout.activity_user);

        //实例化一个initialscreen，进入我的之后的第一个窗口
        initialScreen = new InitialScreen();
        //把initialscreen添加到useractivity中
        getSupportFragmentManager().beginTransaction().add(R.id.fl_user_interface,initialScreen,"initial").commitAllowingStateLoss();

    }
}