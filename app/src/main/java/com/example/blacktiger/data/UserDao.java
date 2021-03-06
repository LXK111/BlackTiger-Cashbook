package com.example.blacktiger.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.blacktiger.data.Entity.User;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertUser(User... users);

    @Query("SELECT * FROM user ")
    LiveData<List<User>> getUserLive();
}
