package com.example.blacktiger.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.blacktiger.data.Entity.Account;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Dao
public interface AccountDao {
    @Insert
    void insertAccount(Account...accounts);

    @Update
    void updateAccount(Account...accounts);

    @Delete
    void deleteAccount(Account...accounts);

    @Query("SELECT * FROM ACCOUNT ORDER BY account_order")
    LiveData<List<Account>>getAllAccountsLive();

    @Query("SELECT account_name FROM Account")
    List<String> AllAccountsName();
}
