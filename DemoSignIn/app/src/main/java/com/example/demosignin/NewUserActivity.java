package com.example.demosignin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class NewUserActivity extends AppCompatActivity {

    private Button WelcomeButton1,WelcomeButton2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        WelcomeButton1 = findViewById(R.id.btn_1);
        WelcomeButton2 = findViewById(R.id.btn_2);
        setListeners();

    }
    private void setListeners(){
        NewUserActivity.OnClick onClick = new NewUserActivity.OnClick();
        WelcomeButton1.setOnClickListener(onClick);
        WelcomeButton2.setOnClickListener(onClick);
    }

    private class OnClick implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent = null;
            switch (v.getId()){
                case R.id.btn_1:
                    intent = new Intent(NewUserActivity.this,SetCipherActivity.class);
                    break;
                case R.id.btn_2:
                    intent = new Intent(NewUserActivity.this,WelcomeActivity.class);
                    break;
            }
            startActivity(intent);
        }
    }
}