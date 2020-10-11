package com.example.demosignin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class TextLoginActivity extends AppCompatActivity {

    private TextView tv_main_title;//标题
    private TextView tv_register;//注册文本
    private Button btn_login;//登录按钮
    private String userName,psw,spPsw;//获取的用户名、密码、加密密码
    private EditText et_user_name,et_psw;//编辑框
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_login);
        //设置此界面为竖屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
    }

    private void init() {
        //从main_title_bar中获取的id
        tv_main_title=findViewById(R.id.tv_main_title);
        tv_main_title.setText("登录");
        tv_register=findViewById(R.id.tv_register);
        btn_login=findViewById(R.id.btn_login);
        et_user_name=findViewById(R.id.et_user_name);
        et_psw=findViewById(R.id.et_psw);
        //立即注册控件的点击事件
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //转到注册界面实现注册功能
                Intent intent=new Intent(TextLoginActivity.this,TextRegisterActivity.class);
                startActivityForResult(intent,1);
            }
        });
        //登录按钮的点击事件
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入框内输入的用户名和密码
                userName=et_user_name.getText().toString().trim();
                psw=et_psw.getText().toString().trim();
                //对当前用户输入的密码进行MD5加密并进行比对判断，判断输入的密码与存入的密码是否一致
                String md5Psw=MD5Utils.md5(psw);
                //spPsw为根据用户名从SharedPreferences中读取密码
                //定义方法，readPsw为获取输入的用户名，得到密码
                spPsw=readPsw(userName);
                if (TextUtils.isEmpty(userName)){
                    Toast.makeText(TextLoginActivity.this,"请输入用户名",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(psw)){
                    Toast.makeText(TextLoginActivity.this,"请输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (md5Psw.equals(spPsw)){
                    //密码一致，登录成功
                    Toast.makeText(TextLoginActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    //保持登录状态，在界面保存登录的用户名 定义方法saveLoginStatus boolean 状态，userName用户名；
                    saveLoginStatus(true,userName);
                    //登录成功后关闭此页面进入主页
                    Intent data = new Intent();
                    data.putExtra("isLogin",true);
                    //RESULT_OK为activity系统常量，状态码为1
                    //表示此页面下的内容操作成功将data返回到上一页面，如果是用back返回过去的则不存在用setResult传递data值
                    setResult(RESULT_OK,data);
                    //销毁登录页面
                    TextLoginActivity.this.finish();
                    //跳转到欢迎界面，登录成功的状态传递到WelcomeActivity中
                    startActivity(new Intent(TextLoginActivity.this,WelcomeActivity.class));
                    return;
                }
                else if (spPsw!=null&&!TextUtils.isEmpty(spPsw)&&!md5Psw.equals(spPsw)){
                    Toast.makeText(TextLoginActivity.this,"输入的用户名和密码不一致",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Toast.makeText(TextLoginActivity.this,"此用户名不存在",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }
    /**
     * 从SharedPreferences中根据用户名读取密码
     */
    private String readPsw(String userName){
        //getSharePreferences("loginInfo",MODE_PRIVATE);
        //MODE_PRIVATE表示可以继续写入
        SharedPreferences sp = getSharedPreferences("loginInfo",MODE_PRIVATE);
        return sp.getString(userName,"");
    }
    /**
     * 保存登录状态和登录用户名到SharedPreferences中
     */
    private void saveLoginStatus(boolean status,String userName){
        SharedPreferences sp=getSharedPreferences("loginInfo",MODE_PRIVATE);
        //获取编辑器
        SharedPreferences.Editor editor=sp.edit();
        //存入boolean类型的登录状态
        editor.putBoolean("isLogin",status);
        //存入登录状态时的用户名
        editor.putString("loginUserName",userName);
        //提交修改
        editor.commit();
    }
    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        if(data!=null){
            //从注册界面回传过来的用户名
            String userName=data.getStringExtra("userName");
            if (!TextUtils.isEmpty(userName)){
                //设置用户名到et_user_name控件
                et_user_name.setText(userName);
                //et_user_name控件的setSelection()方法来设置光标位置
                et_user_name.setSelection(userName.length());
            }
        }
    }
}