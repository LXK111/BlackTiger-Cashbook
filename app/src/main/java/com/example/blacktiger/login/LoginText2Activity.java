package com.example.blacktiger.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.blacktiger.HomeActivity;
import com.example.blacktiger.R;

public class LoginText2Activity extends AppCompatActivity {

    private String psw,spPsw;//获取的用户名、密码、加密密码
    private EditText et_psw;//编辑框
    private TextView tv_makesure;
    private TextView tv_to_input_pic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_text2);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    private void init(){
        tv_makesure=findViewById(R.id.tv_makesure_input);
        et_psw=findViewById(R.id.et_input_psw);
        tv_to_input_pic=findViewById(R.id.tv_to_input_pic);

        //跳转到图案密码输入界面，跳转按钮点击事件
        tv_to_input_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginText2Activity.this,LoginPicActivity.class);
                startActivity(intent);
            }
        });

        //登录确认按钮的点击事件
        tv_makesure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入框内输入的用户名和密码
                psw=et_psw.getText().toString().trim();
                //对当前用户输入的密码进行MD5加密并进行比对判断，判断输入的密码与存入的密码是否一致
                String md5Psw= MD5Utils.md5(psw);
                //spPsw为从存储区SharedPreferences中读出的密码
                SharedPreferences sp = getSharedPreferences("loginInfo",MODE_PRIVATE);
                spPsw=sp.getString("psw","");
                if (TextUtils.isEmpty(psw)){
                    Toast.makeText(LoginText2Activity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!md5Psw.equals(spPsw)){
                    //密码不一致，登录失败
                    Toast.makeText(LoginText2Activity.this,"密码错误！",Toast.LENGTH_SHORT).show();
                }
                else if (md5Psw.equals(spPsw)){
                    //密码一致，登录成功
                    Toast.makeText(LoginText2Activity.this,"登录成功～",Toast.LENGTH_SHORT).show();
                    //保持登录状态，在界面保存登录的用户名 定义方法saveLoginStatus boolean 状态；
                    saveLoginStatus(true);
                    //销毁登录页面
                    LoginText2Activity.this.finish();
                    //跳转到欢迎界面，登录成功的状态传递到WelcomeActivity中
                    startActivity(new Intent(LoginText2Activity.this, HomeActivity.class));
                    return;
                }
            }
        });
    }
    /**
     * 保存登录状态和登录用户名到SharedPreferences中
     */
    private void saveLoginStatus(boolean status){
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor=sp.edit();
        //存入boolean类型的登录状态
        editor.putBoolean("isLogin",status);
        //提交修改
        editor.commit();
    }
}