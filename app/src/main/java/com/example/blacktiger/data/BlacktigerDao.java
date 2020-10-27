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
    void insertBlacktiger(Blacktiger... blacktiger);

    @Update
    void updateBlacktiger(Blacktiger... blacktiger);

    //删除单条记录
    @Delete
    void deleteBlacktiger(Blacktiger... blacktiger);

    @Query("DELETE FROM Blacktiger")
    void deleteAllBlacktiger();

    //获取所有的记录按时间排序
    @Query("SELECT * FROM Blacktiger ORDER BY create_datetime DESC")
    LiveData<List<Blacktiger>> getAllBlacktigerLive();

    //获取所有的记录按金额大小排序
    @Query("SELECT * FROM Blacktiger ORDER BY amount DESC")
    LiveData<List<Blacktiger>> getAllBlacktigerLiveByAmount();

    //搜索数据库，范围包括备注、一二级类型、账户、成员
    @Query("SELECT * FROM Blacktiger WHERE note LIKE :pattern or category LIKE :pattern or category2 LIKE :pattern or account LIKE :pattern or members LIKE :pattern ORDER BY ID DESC")
    LiveData<List<Blacktiger>> findWordsWithPattern(String pattern);

}
