package com.example.blacktiger.ui.person;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.blacktiger.R;
import com.example.blacktiger.login.ChangePicCipher;
import com.example.blacktiger.login.ChangeTextCipher;

public class InitialScreen extends Fragment {

    private TextView mBtnUsername;
    //初始界面的4个按钮，设置、修改密码、关于我们、帮助
    private Button mBtnChangeTextCipher,mBtnChangePicCipher,mBtnAboutDeveloper,mBtnHelp;
    private EditText mBtnSetting_account;
    private EditText mBtnSetting_member;
    private ChangeTextCipher changeTextCipher;
    private ChangePicCipher changePicCipher;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_initial_screen,container,false);
        Log.d("FragementInitialScreen","----onCreateView----");
        return view;
    }

    public void addaccount(View view){
        final EditText et1 = new EditText(getContext());
        //AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        /*
        builder.setCancelable(true);
        builder.setView(et1);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String account = Settings.getString("account", String.valueOf(0));
                String new_account = account + " " + et1.getText().toString();
                SharedPreferences.Editor editor = Settings.edit();
                editor.putString("account",new_account);
                editor.apply();
            }
        }).setNegativeButton("取消",null).show();

         */
        //builder.show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //读取用户名信息
        mBtnUsername=view.findViewById(R.id.btn_username);
        //getsharedpreferences要在context中使用，fragment不属于，故要this.getactitiy后使用
        SharedPreferences sp = this.getActivity().getSharedPreferences("loginInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        String userName=sp.getString("userName","");
        mBtnUsername.setText(userName);

        //添加账户
        mBtnSetting_account = view.findViewById(R.id.btn_setting_account);
        mBtnSetting_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sp.getString("account","其他").contains(mBtnSetting_account.getText().toString())){
                    Toast toast = Toast.makeText(getContext(),"账户已存在",Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    String account =  sp.getString("account","其他") + " " + mBtnSetting_account.getText().toString();
                    editor.putString("account",account);
                    editor.commit();
                    Toast toast = Toast.makeText(getContext(),"添加账户成功",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        //添加账户
        mBtnSetting_member = view.findViewById(R.id.btn_setting_member);
        mBtnSetting_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sp.getString("member","其他").contains(mBtnSetting_member.getText().toString())){
                    Toast toast = Toast.makeText(getContext(),"成员已存在",Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    String member =  sp.getString("member","其他") + " " + mBtnSetting_member.getText().toString();
                    editor.putString("member",member);
                    editor.commit();
                    Toast toast = Toast.makeText(getContext(),"添加成员成功",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });


        /**mBtnSetting_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText input = new EditText();
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("添加备注").setView(input)
                        .setNegativeButton(R.string.cancel, null);
                builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String tmp;
                        tmp = input.getText().toString().trim();
                        if (!tmp.isEmpty()) {
                            setmDesc(tmp);
                        }
                    }
                }).show();
            }
        });**/

        //修改文本密码
        mBtnChangeTextCipher = view.findViewById(R.id.btn_change_text_cipher);
        mBtnChangeTextCipher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_set_to_changeTextCipher);
            }
        });

        //修改图形密码
        mBtnChangePicCipher = view.findViewById(R.id.btn_change_pic_cipher);
        mBtnChangePicCipher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_navigation_set_to_changePicCipher);
            }
        });

        //关于我们
        mBtnAboutDeveloper = view.findViewById(R.id.btn_about_developer);

        //帮助
        mBtnHelp = view.findViewById(R.id.btn_help);
    }
    
}
