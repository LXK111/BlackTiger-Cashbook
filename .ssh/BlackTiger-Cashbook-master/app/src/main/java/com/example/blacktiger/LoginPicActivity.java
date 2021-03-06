package com.example.blacktiger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

import io.paperdb.Paper;

public class LoginPicActivity extends AppCompatActivity {

    PatternLockView patternLockView;
    String save_pattern_key = "pattern_code";
    private TextView tv_to_input_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_pic);

        //读取paper中存储的图形密码
        final String save_pattern = Paper.book().read(save_pattern_key);
        patternLockView = findViewById(R.id.pattern_lock_view);

        tv_to_input_text=findViewById(R.id.tv_to_input_text);
        tv_to_input_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginPicActivity.this,LoginText2Activity.class);
                startActivity(intent);
            }
        });

        //识别图案密码
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
                    Toast.makeText(LoginPicActivity.this,"密码正确",Toast.LENGTH_SHORT).show();
                    LoginPicActivity.this.finish();
                    //图形密码设置成功，转入欢迎界面
                    Intent intent = new Intent(LoginPicActivity.this,WelcomeActivity.class);
                    startActivity(intent);
                }
                else{
                    patternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                    Toast.makeText(LoginPicActivity.this,"密码错误，请重新绘制",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCleared() {

            }
        });

    }
}