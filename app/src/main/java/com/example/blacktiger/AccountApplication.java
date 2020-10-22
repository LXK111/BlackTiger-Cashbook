package com.example.blacktiger;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.blacktiger.db.AccountCategoryUtil;
import com.example.blacktiger.db.AccountDao;

import android.app.Application;
import android.util.Log;

import com.example.blacktiger.R;

/**
 * 应用程序类
 */
public class AccountApplication extends Application {
	public final static String TAG = "TinyAccount";

	public String currentDateMonth = "2019-06";
	private AccountDao mDatabaseManager;

	public AccountCategoryUtil accountCategoryUtil;

	// 类别图标资源ID
	public static int[] categoryIncomeIconResourceId = new int[]{
			R.drawable.baby_icon,R.drawable.fund_icon,
			R.drawable.hobby_icon};
	public static int[] categoryOutlayIconResourceId = new int[]{
			R.drawable.book_icon,R.drawable.breakfast_icon,
			R.drawable.closet_icon,R.drawable.drink_icon,
			R.drawable.film_icon,R.drawable.fruit_icon,
			R.drawable.hobby_icon,R.drawable.housing_loan_icon,
			R.drawable.internet_icon,R.drawable.lunch_icon,
			R.drawable.music_icon,R.drawable.partner_icon,R.drawable.pet_icon,
			R.drawable.snack_icon,R.drawable.sport_icon};
	@Override
	public void onCreate() {
		super.onCreate();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		currentDateMonth = sdf.format(new Date());
		Log.d(TAG, currentDateMonth);

		mDatabaseManager = new AccountDao(this);

		accountCategoryUtil = new AccountCategoryUtil(this);
	}


	public AccountDao getDatabaseManager(){
		return mDatabaseManager;
	}

	//用于分享的统计数据
	public String getStaticsInfo(){
		double incomeSum = mDatabaseManager.getIncomeSummary(currentDateMonth);
		double outlaySum = mDatabaseManager.getOutlaySummary(currentDateMonth);
		return String.format("您的收入汇总为：%f; 您的支出汇总为：%f.",incomeSum,outlaySum);
	}
}
