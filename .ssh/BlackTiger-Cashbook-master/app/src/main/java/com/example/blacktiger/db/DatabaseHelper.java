package com.example.atry.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.atry.R;

public class DatabaseHelper extends SQLiteOpenHelper{
    private static final String DATABASE_NAME="account.db";
    private static final int DATABASE_VERSION=1;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //收入类别表
        String sql="create table accountincometype(id integer primary key autoincrement, category text,icon integer)";
        db.execSQL(sql);
        initData(db);
    }

    private void initData(SQLiteDatabase db) {
        //收入类别
        String sql=String.format("insert into accountincometype(category,icon) values('工资',%d)", R.drawable.fund_icon);
        db.execSQL(sql);
        sql=String.format("insert into accountincometype(category,icon) values('奖金',%d)", R.drawable.insurance_icon);
        db.execSQL(sql);
        sql=String.format("insert into accountincometype(category,icon) values('兼职',%d)", R.drawable.baby_icon);
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
