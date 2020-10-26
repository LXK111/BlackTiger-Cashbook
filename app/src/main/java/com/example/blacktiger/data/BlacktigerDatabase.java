package com.example.blacktiger.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.blacktiger.data.Entity.Blacktiger;


@Database(entities = {Blacktiger.class}, version = 1, exportSchema = false)
public abstract class BlacktigerDatabase extends RoomDatabase {
    private static BlacktigerDatabase INSTANCE;

    static synchronized BlacktigerDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), BlacktigerDatabase.class, "blacktiger_database")
                    .build();
        }
        return INSTANCE;
    }

    public abstract BlacktigerDao getBlacktigerDao();
}
