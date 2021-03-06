package com.example.blacktiger.login;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.blacktiger.R;
import com.example.blacktiger.ui.person.InitialScreen;

import java.util.List;

import io.paperdb.Paper;

public class CheckPicCipher extends Fragment {

    PatternLockView patternLockView;
    String save_pattern_key = "pattern_code";
    private InitialScreen initialScreen;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_check_pic_cipher,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //读取paper中存储的图形密码
        final String save_pattern = Paper.book().read(save_pattern_key);
        patternLockView = view.findViewById(R.id.pattern_lock_view);
        patternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }

            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                Log.d(getClass().getName(),"Pattern complete: " +
                        PatternLockUtils.patternToString(patternLockView,pattern));
                if(PatternLockUtils.patternToString(patternLockView,pattern).equalsIgnoreCase(save_pattern)){//检测密码是否匹配
                    patternLockView.setViewMode(PatternLockView.PatternViewMode.CORRECT);
                    Toast.makeText(getActivity(),"设置完成",Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(patternLockView).navigate(R.id.action_checkPicCipher_to_navigation_set);
                }
                else{
                    patternLockView.setViewMode(PatternLockView.PatternViewMode.WRONG);
                    Toast.makeText(getActivity(),"两次输入不一致，请仔细查看之前绘制内容",Toast.LENGTH_SHORT).show();
                    Paper.book().delete(save_pattern_key);
                    //销毁当前fragment,跳转回上一级设置密码
                    getActivity().onBackPressed();
                }

            }

            @Override
            public void onCleared() {

            }
        });

    }
}
