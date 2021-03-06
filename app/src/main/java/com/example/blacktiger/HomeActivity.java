package com.example.blacktiger;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.blacktiger.data.Entity.Account;
import com.example.blacktiger.data.Entity.Category;
import com.example.blacktiger.data.Entity.User;
import com.example.blacktiger.login.LoginViewModel;
import com.example.blacktiger.login.SetCipherfornewActivity;
import com.example.blacktiger.ui.account.AccountViewModel;
import com.example.blacktiger.ui.category.CategoryViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private LoginViewModel loginViewModel;
    private CategoryViewModel categoryViewModel;
    private AccountViewModel accountViewModel;
    private static boolean isInitCategory=false;
    private static boolean isInitAccount=false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_detail, R.id.navigation_chart, R.id.navigation_set)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        loginViewModel= ViewModelProviders.of(this).get(LoginViewModel.class);
        loginViewModel.getAllUserLive().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                if(users==null||users.isEmpty()){
                   startActivity(new Intent(HomeActivity.this, SetCipherfornewActivity.class));
                }
            }
        });

        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        categoryViewModel.getAllCategoriesLive().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                Log.e("MainActivity","onChanged");
                if(categories.size()==0&&!isInitCategory) {
                    initCategory();
                }
            }
        });

        accountViewModel = ViewModelProviders.of(this).get(AccountViewModel.class);
        accountViewModel.getAllAccountsLive().observe(this, new Observer<List<Account>>() {
            @Override
            public void onChanged(List<Account> accounts) {
                Log.e("MainActivity","onChanged");
                if(accounts.size()==0&&!isInitAccount) {
                    initAccount();
                }
            }
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        Navigation.findNavController(findViewById(R.id.nav_host_fragment)).navigateUp();
        return super.onSupportNavigateUp();
    }


    //初始化category数据库
    private void initCategory() {
        isInitCategory = true;
        String[] categoryINName = {"搬砖","工资","奖金","还款","投资","兼职","其它"};
        String[] categoryOUTName = {"餐饮","购物","服饰","健身","交通","捐赠","社交","通信","房租","教育","医疗","生活","零食","旅行","水果","其它"};
        //支出
        for(int i=0;i<categoryOUTName.length;i++) {
            Category category = new Category();
            category.setIcon("ic_category_out_"+(i+1));
            category.setType(true);
            category.setName(categoryOUTName[i]);
            category.setOrder(i);
            categoryViewModel.insertCategory(category);
        }
        //收入
        for(int i=0;i<categoryINName.length;i++) {
            Category category = new Category();
            category.setIcon("ic_category_in_"+(i+1));
            category.setType(false);
            category.setName(categoryINName[i]);
            category.setOrder(i);
            categoryViewModel.insertCategory(category);
        }
    }
    //初始化account数据库
    private void initAccount() {
        isInitAccount = true;
        String[] accountName = {"平安银行","建设银行","工商银行","京东白条","蚂蚁花呗","校园卡"};
        for(int i=0;i<accountName.length;i++) {
            Account account = new Account();
            account.setName(accountName[i]);
            account.setOrder(i);
            accountViewModel.insertAccount(account);
        }
    }

    private long exitTime = 0;
    //两次返回，返回到home界面（System.exit决定是否退出当前界面，重新加载程序）
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if((System.currentTimeMillis()-exitTime) > 2000){
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                Intent home = new Intent(Intent.ACTION_MAIN);
                home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                home.addCategory(Intent.CATEGORY_HOME);
                startActivity(home);
                //退出系统，不保存之前页面
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
