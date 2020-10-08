package com.example.demosignin;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.andrognito.patternlockview.utils.ResourceUtils;

import java.util.List;

import io.paperdb.Paper;

public class SetCipherActivity extends AppCompatActivity {

    PatternLockView patternLockView;
    String save_pattern_key = "pattern_code";
    String final_pattern = "";
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        Paper.init(this);
        final String save_pattern = Paper.book().read(save_pattern_key);

        if(save_pattern != null && !save_pattern.equals("null")){
            setContentView(R.layout.activity_set_cipher);
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
                    final_pattern = PatternLockUtils.patternToString(patternLockView,pattern);
                    if(final_pattern.equals(save_pattern)){
                        Toast.makeText(SetCipherActivity.this,"password correct",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SetCipherActivity.this,SignInpictureActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(SetCipherActivity.this,"Password incorrect",Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCleared() {

                }
            });
        }
        else {
            setContentView(R.layout.activity_set_cipher);
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
                    final_pattern = PatternLockUtils.patternToString(patternLockView,pattern);
                }

                @Override
                public void onCleared() {

                }
            });
            Button btnSetup = findViewById(R.id.btn_make_sure);
            btnSetup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Paper.book().write(save_pattern_key,final_pattern);
                    Toast.makeText(SetCipherActivity.this,"第一次确认",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SetCipherActivity.this,SignInpictureActivity.class);
                    startActivity(intent);
                }
            });

        }

    }

}