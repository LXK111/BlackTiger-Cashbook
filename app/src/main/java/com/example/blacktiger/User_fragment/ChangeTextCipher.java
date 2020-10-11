package com.example.blacktiger.User_fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.blacktiger.MD5Utils;
import com.example.blacktiger.R;
import com.example.blacktiger.SetCipherfornewActivity;

import static android.content.Context.MODE_PRIVATE;

public class ChangeTextCipher extends Fragment {

    private EditText etPassword,etPasswordAgain;
    private TextView tvMakeSure,title;
    private String password,passwordAgain;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_change_text_cipher,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        title=view.findViewById(R.id.tv_main_title);
        title.setText("修改文本密码");
        etPassword=view.findViewById(R.id.et_psw);
        etPasswordAgain=view.findViewById(R.id.et_pswagain);
        tvMakeSure=view.findViewById(R.id.tv_makesure);
        tvMakeSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取输入在相应控件中的字符串
                getEditString();
                if (TextUtils.isEmpty(password)){
                    Toast.makeText(getActivity(),"请输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if (TextUtils.isEmpty(passwordAgain)){
                    Toast.makeText(getActivity(),"请再次输入密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                else if(!password.equals(passwordAgain)){
                    Toast.makeText(getActivity(),"两次输入的密码不一致，请重新输入",Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    Toast.makeText(getActivity(),"修改成功！",Toast.LENGTH_SHORT).show();
                    /**
                     * 保存账号和密码到SharedPreferences中
                     */
                    SharedPreferences sp=getActivity().getSharedPreferences("loginInfo",MODE_PRIVATE);
//                    SharedPreferences sp = this.getActivity().getSharedPreferences("loginInfo",MODE_PRIVATE);
                    //获取编辑器
                    SharedPreferences.Editor editor=sp.edit();
                    String md5Psw = MD5Utils.md5(password);//密码用MD5加密方式加密
                    //存储新密码
                    editor.putString("psw",md5Psw);
                    //提交修改
                    editor.commit();
                    //销毁当前fragment
                    getActivity().onBackPressed();
                    return;
                }

            }
        });

    }
    /**
     * 获取控件中的字符串
     */
    private void getEditString(){
        password=etPassword.getText().toString().trim();
        passwordAgain=etPasswordAgain.getText().toString().trim();
    }

}
