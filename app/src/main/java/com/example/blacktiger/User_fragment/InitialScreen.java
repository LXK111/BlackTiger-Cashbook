package com.example.blacktiger.User_fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.blacktiger.R;

import static android.content.Context.MODE_PRIVATE;

public class InitialScreen extends Fragment {

    private TextView mBtnUsername;
    //初始界面的4个按钮，设置、修改密码、关于我们、帮助
    private Button mBtnSetting,mBtnChangeTextCipher,mBtnChangePicCipher,mBtnAboutDeveloper,mBtnHelp;
    private ChangeTextCipher changeTextCipher;
    private ChangePicCipher changePicCipher;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_initial_screen,container,false);
        Log.d("FragementInitialScreen","----onCreateView----");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //读取用户名信息
        mBtnUsername=view.findViewById(R.id.btn_username);
        //getsharedpreferences要在context中使用，fragment不属于，故要this.getactitiy后使用
        SharedPreferences sp = this.getActivity().getSharedPreferences("loginInfo",MODE_PRIVATE);
        String userName=sp.getString("userName","");
        mBtnUsername.setText(userName);

        //设置
        mBtnSetting = view.findViewById(R.id.btn_setting);

        //修改文本密码
        mBtnChangeTextCipher = view.findViewById(R.id.btn_change_text_cipher);
        mBtnChangeTextCipher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changeTextCipher== null){
                    changeTextCipher = new ChangeTextCipher();//实例化一个修改密码fragment
                }
                Fragment fragment = getFragmentManager().findFragmentByTag("initial");
                if (fragment != null){
                    getFragmentManager().beginTransaction().hide(fragment).add(R.id.fl_user_interface,changeTextCipher,"textcipher").addToBackStack(null).commitAllowingStateLoss();
                }
                 else {
                     getFragmentManager().beginTransaction().hide(fragment).replace(R.id.fl_user_interface,changeTextCipher,"textcipher").addToBackStack(null).commitAllowingStateLoss();
                }

            }
        });

        //修改图形密码
        mBtnChangePicCipher = view.findViewById(R.id.btn_change_pic_cipher);
        mBtnChangePicCipher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (changePicCipher == null){
                    changePicCipher = new ChangePicCipher();//实例化一个修改图形密码fragment
                }
                Fragment fragment = getFragmentManager().findFragmentByTag("initial");
                if (fragment != null){
                    getFragmentManager().beginTransaction().hide(fragment).add(R.id.fl_user_interface,changePicCipher,"piccipher").addToBackStack(null).commitAllowingStateLoss();
                }
                else {
                    getFragmentManager().beginTransaction().hide(fragment).replace(R.id.fl_user_interface,changePicCipher,"piccipher").addToBackStack(null).commitAllowingStateLoss();
                }

            }
        });

        //关于我们
        mBtnAboutDeveloper = view.findViewById(R.id.btn_about_developer);

        //帮助
        mBtnHelp = view.findViewById(R.id.btn_help);
    }
    
}
