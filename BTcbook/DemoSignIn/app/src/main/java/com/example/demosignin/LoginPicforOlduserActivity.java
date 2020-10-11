package com.example.demosignin;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;
import java.util.Set;

import io.paperdb.Paper;

public class LoginPicforOlduserActivity extends AppCompatActivity {

    PatternLockView patternLockView;//图形密码控件
    Button  mBtn_jumptotextlogin;//选择输入文本密码按钮
    String save_pattern_key = "pattern_code";//存储图形密码的路径
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login_picfor_olduser);
        Paper.init(this);
        final String save_pattern = Paper.book().read(save_pattern_key);

        if(save_pattern != null && !save_pattern.equals("null")){
            setContentView(R.layout.activity_login_picfor_olduser);
            patternLockView = findViewById(R.id.pattern_lock_view);
            //创建一个图形密码输入控件
            patternLockView.addPatternLockListener(new PatternLockViewListener() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {

                }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {
                    Log.d(getClass().getName(),"Pattern complete: " +
                            PatternLockUtils.patternToString(patternLockView,pattern));
                    if(PatternLockUtils.patternToString(patternLockView,pattern).equalsIgnoreCase(save_pattern)){//检测密码是否匹配
                        patternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
                        Toast.makeText(LoginPicforOlduserActivity.this,"密码正确",Toast.LENGTH_SHORT).show();
                        //图形密码输入成功，进入欢迎界面，正式开始使用
                        Intent intent = new Intent(LoginPicforOlduserActivity.this,WelcomeActivity.class);
                        startActivity(intent);
                    }
                    else{
                        patternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                        //提示密码错误，但可以继续输入直到正确
                        Toast.makeText(LoginPicforOlduserActivity.this,"密码错误,请再次输入",Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onCleared() {

                }
            });
            //找到按钮控件，跳转到文本密码登录界面
            mBtn_jumptotextlogin=findViewById(R.id.btn_jumptotextlogin);
            mBtn_jumptotextlogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginPicforOlduserActivity.this,TextLoginActivity.class);
                    startActivity(intent);
                }
            });

        }
    }

}