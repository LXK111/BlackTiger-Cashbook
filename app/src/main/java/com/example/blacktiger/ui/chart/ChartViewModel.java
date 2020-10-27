package com.example.blacktiger.ui.chart;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.blacktiger.data.Entity.Blacktiger;
import com.example.blacktiger.data.BlacktigerRepository;

import java.util.List;

public class ChartViewModel extends AndroidViewModel {

    private BlacktigerRepository blacktigerRepository;

    public ChartViewModel(@NonNull Application application) {
        super(application);
        blacktigerRepository = new BlacktigerRepository(application);
    }

    public LiveData<List<Blacktiger>> getAllBlacktigerLive() {
        return blacktigerRepository.getAllBlacktigersLiveByAmount();
    }
}