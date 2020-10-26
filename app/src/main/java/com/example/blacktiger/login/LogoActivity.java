package com.example.blacktiger.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.blacktiger.R;

public class LogoActivity extends AppCompatActivity {

    private LinearLayout body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        body=findViewById(R.id.ll_body);
        body.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogoActivity.this,LoginTextActivity.class);
                startActivity(intent);
            }
        });
    }
}