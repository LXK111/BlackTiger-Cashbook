package com.example.blacktiger.ui.detail;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.blacktiger.data.Entity.Blacktiger;
import com.example.blacktiger.data.BlacktigerRepository;

import java.util.List;

public class DetailViewModel extends AndroidViewModel {

    private BlacktigerRepository blacktigerRepository;
    public DetailViewModel(@NonNull Application application) {
        super(application);
        blacktigerRepository = new BlacktigerRepository(application);
    }
    public LiveData<List<Blacktiger>> getAllBlacktigerLive(){
        return blacktigerRepository.getAllBlacktigerLive();
    }
    public LiveData<List<Blacktiger>> findBlacktigerWithPattern(String pattern){
        return blacktigerRepository.findBlacktigerWithPattern(pattern);
    }

    public void insertBlacktiger(Blacktiger... blacktigers) {
        blacktigerRepository.insertBlacktiger(blacktigers);
    }

    public void updateBlacktiger(Blacktiger... blacktigers){
        blacktigerRepository.updateBlacktiger(blacktigers);
    }
    public void deleteBlacktiger(Blacktiger... blacktigers){
        blacktigerRepository.deleteBlacktiger(blacktigers);
    }

}