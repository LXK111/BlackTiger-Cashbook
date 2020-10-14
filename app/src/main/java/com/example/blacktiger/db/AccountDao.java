package com.example.atry.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class AccountDao {
    private DatabaseHelper helper;
    private SQLiteDatabase db;
    public AccountDao(Context context){
        helper=new DatabaseHelper(context);
        db= helper.getWritableDatabase();
    }
}
