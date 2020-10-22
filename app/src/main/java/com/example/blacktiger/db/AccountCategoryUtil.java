package com.example.blacktiger.db;

import com.example.blacktiger.AccountApplication;
import com.example.blacktiger.entity.AccountCategory;

import java.util.List;

/**
 * 根据类别名查找类别图标的工具类
 */
public class AccountCategoryUtil {
    private List<AccountCategory> mIncomeCategory;
    private List<AccountCategory> mOutlayCategory;

    public AccountCategoryUtil(AccountApplication app){
        mIncomeCategory = app.getDatabaseManager().getIncomeType();
        mOutlayCategory = app.getDatabaseManager().getOutlayType();
    }

    public int getIncomeCategoryIcon(String category){
        if (mIncomeCategory==null){
            return 0;
        }
        if (category==null){
            return 0;
        }
        for (AccountCategory c: mIncomeCategory){
            if (c.getCategory().equals(category)){
                return c.getIcon();
            }
        }
        return 0;
    }

    public int getOutlayCategoryIcon(String category){
        if (mOutlayCategory==null){
            return 0;
        }
        if (category==null){
            return 0;
        }
        for (AccountCategory c: mOutlayCategory){
            if (c.getCategory().equals(category)){
                return c.getIcon();
            }
        }
        return 0;
    }
}
