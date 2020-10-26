package com.example.blacktiger.ui.chart;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.blacktiger.data.Entity.WasteBook;
import com.example.blacktiger.data.WasteBookRepository;

import java.util.List;

public class ChartViewModel extends AndroidViewModel {

    private WasteBookRepository wasteBookRepository;

    public ChartViewModel(@NonNull Application application) {
        super(application);
        wasteBookRepository = new WasteBookRepository(application);
    }

    public LiveData<List<WasteBook>> getAllWasteBookLive() {
        return wasteBookRepository.getAllWasteBooksLiveByAmount();
    }
}