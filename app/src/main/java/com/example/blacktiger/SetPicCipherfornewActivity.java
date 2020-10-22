package com.example.blacktiger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

import io.paperdb.Paper;

public class SetPicCipherfornewActivity extends AppCompatActivity {

    PatternLockView patternLockView;
    String save_pattern_key = "pattern_code";
    String final_pattern = "";
    private TextView tv_makesure_pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pic_cipherfornew);
        //初始化paper存储
        Paper.init(this);
        final String save_pattern = Paper.book().read(save_pattern_key);
        //读取用户输入
        patternLockView = findViewById(R.id.pattern_lock_view);
        patternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }
            //读取用户绘制的图形密码
            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                final_pattern = PatternLockUtils.patternToString(patternLockView,pattern);
            }

            @Override
            public void onCleared() {

            }
        });
        //按下确认后
        tv_makesure_pic=findViewById(R.id.tv_makesure_pic);
        tv_makesure_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().write(save_pattern_key,final_pattern);
                Toast.makeText(SetPicCipherfornewActivity.this,"确认成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SetPicCipherfornewActivity.this,SetPicCipherAgainActivity.class);
                startActivity(intent);
            }
        });

    }
}