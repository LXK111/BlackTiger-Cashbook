package com.example.demosignin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

import io.paperdb.Paper;

public class SignInpictureActivity extends AppCompatActivity {

    PatternLockView patternLockView;
    String save_pattern_key = "pattern_code";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_inpicture);

        //读取paper中存储的图形密码
        final String save_pattern = Paper.book().read(save_pattern_key);


        patternLockView = findViewById(R.id.pattern_signin);
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
                    Toast.makeText(SignInpictureActivity.this,"设置完成",Toast.LENGTH_LONG).show();
                    //图形密码设置成功，转入文本密码设置界面
                    Intent intent = new Intent(SignInpictureActivity.this,TextLoginActivity.class);
                    startActivity(intent);
                }
                else{
                    patternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                    Toast.makeText(SignInpictureActivity.this,"Incorrect password",Toast.LENGTH_LONG).show();
                    Paper.book().delete(save_pattern_key);
                    Intent intent = new Intent(SignInpictureActivity.this,SetCipherActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onCleared() {

            }
        });
    }
}