package com.example.blacktiger.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.blacktiger.data.Entity.Account;

import java.util.ArrayList;
import java.util.List;

public class AccountRepository {
    private LiveData<List<Account>> allAccountsLive;
    private List<String> allAccountsName;
    private AccountDao accountDao;

    public AccountRepository(Context context) {
        AccountDatabase accountDatabase = AccountDatabase.getDatabase(context);
        accountDao = accountDatabase.getAccountDao();
        allAccountsLive = accountDao.getAllAccountsLive();
        //allAccountsName = accountDao.AllAccountsName();
    }

    public void insertAccount(Account... accounts) {
        new InsertAsyncTask(accountDao).execute(accounts);
    }

    public void updateAccount(Account... accounts) {
        new UpdateAsyncTask(accountDao).execute(accounts);
    }

    public void deleteAccount(Account... accounts) {
        new DeleteAsyncTask(accountDao).execute(accounts);
    }

    public LiveData<List<Account>> getAllAccountsLive() {
        return allAccountsLive;
    }

    public List<String> getAllAccountsName() {
        return allAccountsName;
    }

    private static class InsertAsyncTask extends AsyncTask<Account, Void, Void> {
        private AccountDao accountDao;

        public InsertAsyncTask(AccountDao accountDao) {
            this.accountDao = accountDao;
        }

        @Override
        protected Void doInBackground(Account... accounts) {
            accountDao.insertAccount(accounts);
            return null;
        }
    }

    private static class UpdateAsyncTask extends AsyncTask<Account, Void, Void> {
        private AccountDao accountDao;

        public UpdateAsyncTask(AccountDao accountDao) {
            this.accountDao = accountDao;
        }

        @Override
        protected Void doInBackground(Account... accounts) {
            accountDao.updateAccount(accounts);
            return null;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Account, Void, Void> {
        private AccountDao accountDao;

        public DeleteAsyncTask(AccountDao accountDao) {
            this.accountDao = accountDao;
        }

        @Override
        protected Void doInBackground(Account... accounts) {
            accountDao.deleteAccount(accounts);
            return null;
        }
    }
}
