package com.example.demosignin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;

public class TextRegisterActivity extends AppCompatActivity {

    private TextView tv_main_title;//标题
    private Button btn_rigister;//注册按钮
    //用户名、密码、再次输入密码控件
    private EditText et_user_name,et_psw,et_psw_again;
    //用户名，密码，再次输入密码控件的参考值
    private String userName,psw,pswAgain;
    //标题布局
    private RelativeLayout rl_title_bar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_register);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    private void init() {
        //从main_title_bar中获取的id
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_main_title.setText("注册");
        //布局根元素
        rl_title_bar=findViewById(R.id.title_bar);
        rl_title_bar.setBackgroundColor(Color.TRANSPARENT);
        //从activity_text_register.xml页面中获取对应的UI控件
        btn_rigister=findViewById(R.id.btn_register);
        et_user_name=findViewById(R.id.et_user_name);
        et_psw=findViewById(R.id.et_psw);
        et_psw_again=findViewById(R.id.et_psw_again);
        //注册按钮
        btn_rigister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入在相应控件中的字符串
                getEditString();
                //判断输入框内容
                if(TextUtils.isEmpty(userName)){
                    Toast.makeText(TextRegisterActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(psw)){
                    Toast.makeText(TextRegisterActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(TextUtils.isEmpty(pswAgain)){
                    Toast.makeText(TextRegisterActivity.this,"请再次输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!psw.equals(pswAgain)){
                    Toast.makeText(TextRegisterActivity.this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    Toast.makeText(TextRegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                    //把账号、密码和账号标识保存到sp中
                    /**
                     * 保存账号和密码到SharedPreferences中
                     */
                    saveRegisterInfo(userName,psw);
                    //注册成功后把账号传递到TextLoginActivity.java中
                    //返回值到TextLoginActivity中显示
                    Intent data = new Intent();
                    data.putExtra("userName",userName);
                    setResult(RESULT_OK,data);
                    //RESULT_OK为Activity系统常量，状态码为-1，
                    //表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                    TextRegisterActivity.this.finish();
                    return;
                }
            }
        });

    }

    /**
     * 获取控件中的字符串
     */
    private void getEditString(){
        userName=et_user_name.getText().toString().trim();
        psw=et_psw.getText().toString().trim();
        pswAgain=et_psw_again.getText().toString().trim();

    }

    /**
     *
     * @param userName 输入的用户名
     * @param psw 输入的密码
     * 保存账号和密码到SharedPreferences中SharedPreferences
     */
    private void saveRegisterInfo(String userName,String psw){
        String md5Psw = MD5Utils.md5(psw);//密码用MD5加密方式加密
        //loginInfo表示文件名，mode_private SharedPreferences sp = getSharedPreferences();
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor=sp.edit();
        //以用户名为key，密码为value保存在SharedPreferences中
        //key,value,如键值对，editor.putString(用户名，密码)
        editor.putString(userName,md5Psw);
        //提交修改
        editor.commit();

    }
}