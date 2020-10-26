package com.example.blacktiger.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.blacktiger.data.Entity.Blacktiger;

import java.util.List;

@Dao
public interface BlacktigerDao {
    @Insert
    void insertWasteBook(Blacktiger... blacktiger);

    @Update
    void updateWasteBook(Blacktiger... blacktiger);

    //删除单条记录
    @Delete
    void deleteWasteBook(Blacktiger... blacktiger);

    @Query("DELETE FROM Blacktiger")
    void deleteAllWasteBooks();

    //获取所有的记录按时间排序
    @Query("SELECT * FROM Blacktiger ORDER BY create_datetime DESC")
    LiveData<List<Blacktiger>> getAllWasteBookLive();

    //获取所有的记录按金额大小排序
    @Query("SELECT * FROM Blacktiger ORDER BY amount DESC")
    LiveData<List<Blacktiger>> getAllWasteBookLiveByAmount();

    //搜索数据库，范围包括备注和类型
    @Query("SELECT * FROM Blacktiger WHERE note LIKE :pattern or category LIKE :pattern ORDER BY ID DESC")
    LiveData<List<Blacktiger>> findWordsWithPattern(String pattern);

}
