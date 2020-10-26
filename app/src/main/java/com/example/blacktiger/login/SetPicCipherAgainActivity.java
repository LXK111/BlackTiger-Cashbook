package com.example.blacktiger.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.blacktiger.HomeActivity;
import com.example.blacktiger.R;

import java.util.List;

import io.paperdb.Paper;

public class SetPicCipherAgainActivity extends AppCompatActivity {

    PatternLockView patternLockView;
    String save_pattern_key = "pattern_code";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pic_cipher_again);

        //读取paper中存储的图形密码
        final String save_pattern = Paper.book().read(save_pattern_key);
        patternLockView = findViewById(R.id.pattern_lock_view);
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
                    Toast.makeText(SetPicCipherAgainActivity.this,"设置完成",Toast.LENGTH_SHORT).show();
                    //图形密码设置成功，转入欢迎界面
                    Intent intent = new Intent(SetPicCipherAgainActivity.this, HomeActivity.class);
                    startActivity(intent);

                }
                else{
                    patternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                    Toast.makeText(SetPicCipherAgainActivity.this,"两次输入不一致",Toast.LENGTH_SHORT).show();
                    Paper.book().delete(save_pattern_key);
                    Intent intent = new Intent(SetPicCipherAgainActivity.this,SetPicCipherfornewActivity.class);
                    startActivity(intent);
                }

            }

            @Override
            public void onCleared() {

            }
        });

    }
}