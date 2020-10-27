package com.example.blacktiger.ui.account;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.arch.core.util.Function;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.blacktiger.data.AccountRepository;
import com.example.blacktiger.data.Entity.Account;

import java.util.List;

public class AccountViewModel extends AndroidViewModel {
    private AccountRepository accountRepository;
    private MutableLiveData<Integer> mIndex = new MutableLiveData<>();

    public AccountViewModel(@NonNull Application application) {
        super(application);
        accountRepository = new AccountRepository(application);
    }

    private LiveData<String> mText = Transformations.map(mIndex, new Function<Integer, String>() {
        @Override
        public String apply(Integer input) {
            return "index:" + input;
        }
    });

    public void setIndex(int index) {
        mIndex.setValue(index);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<Account>> getAllAccountsLive() {
        return accountRepository.getAllAccountsLive();
    }

    public void insertAccount(Account... accounts) {
        accountRepository.insertAccount(accounts);
    }

    public void updateAccount(Account... accounts) {
        accountRepository.updateAccount(accounts);
    }

    public void deleteAccount(Account... accounts) {
        accountRepository.deleteAccount(accounts);
    }
}
