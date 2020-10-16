package com.example.atry.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class AccountDao<AccountItem> {
    private DatabaseHelper helper;
    private SQLiteDatabase db;

    public AccountDao(Context context) {
        helper = new DatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

//查询
    public List<AccountItem> getIncomeList() {
        ArrayList<AccountItem> result = new ArrayList<AccountItem>();
        Cursor cursor;
        cursor = db.query("AccountIncome", null, null, null, null, null, null);//这里是搜索对象我没创建
        while (cursor.moveToNext()) {
            //AccountItem item=new AccountItem();
            //item.seID(cursor.getInt(cursor.getcolumnName("**");此为添加查询记录，并且在表中体现，item为建立的收入对象具有不同的性质，这里没有建立
            //result.add(item);
        }
        cursor.close();
        return result;
    }

    //添加
    public void addIncome(AccountItem item) {
        db.beginTransaction();
        try {
            //db.execSQL("insert into accountincome(id,category,money,date,remark) values(null,?,?,?,?)",new Object[](item.**,item.**));//item是一个收入对象，它具有诸多性质如时间等
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

}