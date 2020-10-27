package com.example.blacktiger.data.Entity;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "Account")
public class Account {

    /**
     * 账户ID
     */
    @ColumnInfo(name = "account_id")
    @PrimaryKey(autoGenerate = true)
    private long id;


    /**
     * 账户名称
     */
    @ColumnInfo(name = "account_name")
    private String name = "";


    /**
     * 排序
     */
    @ColumnInfo(name = "account_order")
    private int order;


    /**
     * 用户 ID
     */
    @ColumnInfo(name = "account_user_id")
    private long accountId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }



    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }
}
