package com.example.blacktiger.User_fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;
import com.example.blacktiger.R;

import java.util.List;

import io.paperdb.Paper;

public class ChangePicCipher extends Fragment {

    PatternLockView patternLockView;
    String save_pattern_key = "pattern_code";
    String final_pattern = "";
    private CheckPicCipher checkPicCipher;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_change_pic_cipher,container,false);
        Log.d("change_pic_cipher","--onCreateView--");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //初始化paper存储
        Paper.init(getActivity());
        final String save_pattern = Paper.book().read(save_pattern_key);
        patternLockView = view.findViewById(R.id.pattern_lock_view);
        patternLockView.addPatternLockListener(new PatternLockViewListener() {
            @Override
            public void onStarted() {

            }

            @Override
            public void onProgress(List<PatternLockView.Dot> progressPattern) {

            }
            //读取用户绘制的图形密码
            @Override
            public void onComplete(List<PatternLockView.Dot> pattern) {
                final_pattern = PatternLockUtils.patternToString(patternLockView,pattern);
            }

            @Override
            public void onCleared() {

            }
        });
        //按下确认后
        TextView tv_makesure_pic = view.findViewById(R.id.tv_makesure_pic);
        tv_makesure_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Paper.book().write(save_pattern_key,final_pattern);
                Toast.makeText(getActivity(),"确认成功",Toast.LENGTH_SHORT).show();
                Fragment fragment = getFragmentManager().findFragmentByTag("piccipher");
                if (checkPicCipher == null){
                    checkPicCipher = new CheckPicCipher();
                }
                //当前fragment不为空，则隐藏起来添加新的确认密码的fragment
                if (fragment != null) {
                    getFragmentManager().beginTransaction().hide(fragment).add(R.id.fl_user_interface,checkPicCipher,"checkpiccipher").addToBackStack(null).commitAllowingStateLoss();
                }
                else {
                    getFragmentManager().beginTransaction().replace(R.id.fl_user_interface,checkPicCipher,"checkpiccipher").addToBackStack(null).commitAllowingStateLoss();
                }
            }
        });

    }
}
