package com.example.blacktiger.ui.person;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.blacktiger.data.CategoryRepository;
import com.example.blacktiger.data.Entity.Blacktiger;
import com.example.blacktiger.data.Entity.Category;
import com.example.blacktiger.data.BlacktigerRepository;

import java.util.List;

public class PersonViewModel extends AndroidViewModel {
    private BlacktigerRepository blacktigerRepository;
    private CategoryRepository categoryRepository;

    public PersonViewModel(@NonNull Application application) {
        super(application);
        blacktigerRepository = new BlacktigerRepository(application);
        categoryRepository = new CategoryRepository(application);
    }

    public LiveData<List<Blacktiger>> getAllWasteBookLive() {
        return blacktigerRepository.getAllBlacktigerLive();
    }

    public LiveData<List<Category>> getAllCategoriesLive() {
        return categoryRepository.getAllCategoriesLive();
    }

    public void insertWasteBook(Blacktiger... blacktigers) {
        blacktigerRepository.insertBlacktiger(blacktigers);
    }

    public void deleteAllWasteBooks() {
        blacktigerRepository.deleteAllBlacktiger();
    }
}