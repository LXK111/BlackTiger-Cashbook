package com.example.blacktiger;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class LoginTextActivity extends AppCompatActivity {

    private TextView tv_login_word;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_text);

        tv_login_word=findViewById(R.id.tv_login_word);
        tv_login_word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginTextActivity.this,LoginText2Activity.class);
                startActivity(intent);
            }
        });
    }
}