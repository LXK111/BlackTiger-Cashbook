package com.example.blacktiger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.blacktiger.User_fragment.UserActivity;

public class WelcomeActivity extends AppCompatActivity {

    private Button btn_jumptoMine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        btn_jumptoMine=findViewById(R.id.jumptoMine);
        btn_jumptoMine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WelcomeActivity.this, UserActivity.class);
                startActivity(intent);
            }
        });

    }
}