package com.example.blacktiger.data.Entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Blacktiger")
public class Blacktiger {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    @ColumnInfo(name = "id")
    private int id;

    //用户ID
    @ColumnInfo(name = "user_id")
    private long accountId;
    //款项金额
    @ColumnInfo(name = "amount")
    private double amount;
    //false:支出、true:收入
    @ColumnInfo(name = "type")
    private boolean type;
    //具体类型
    @ColumnInfo(name = "category")
    private String category;
    //二级类型
    @ColumnInfo(name = "category2")
    private String category2;
    //账户
    @ColumnInfo(name = "account")
    private String account;
    //成员
    @ColumnInfo(name = "members")
    private String members;
    //图片
    @ColumnInfo(name = "icon")
    private String icon;
    //时间
    @ColumnInfo(name = "create_datetime")
    private long time;
    //备注
    private String note;

    public Blacktiger(boolean type, double amount, String category, String category2,String account,String members, String icon, long time, String note) {
        this.type = type;
        this.amount = amount;
        this.category = category;
        this.category2 = category2;
        this.account = account;
        this.members = members;
        this.icon=icon;
        this.time = time;
        this.note = note;
    }
    @Ignore
    public Blacktiger(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isType() {
        return type;
    }

    public void setType(boolean type) {
        this.type = type;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCategory2() {
        return category2;
    }

    public void setCategory2(String category2) {
        this.category2 = category2;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getMembers() {
        return members;
    }

    public void setMembers(String members) {
        this.members = members;
    }
}
