package com.example.blacktiger.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.blacktiger.data.Entity.Account;

@Database(entities = {Account.class}, version = 1, exportSchema = false)
public abstract class AccountDatabase extends RoomDatabase {
    private static AccountDatabase INSTANCE;

    static synchronized AccountDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AccountDatabase.class, "account").build();
        }
        return INSTANCE;
    }

    public abstract AccountDao getAccountDao();
}