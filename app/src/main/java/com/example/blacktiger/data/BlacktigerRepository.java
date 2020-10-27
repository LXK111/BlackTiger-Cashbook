package com.example.blacktiger.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.example.blacktiger.data.Entity.Blacktiger;

import java.util.List;

public class BlacktigerRepository {
    private LiveData<List<Blacktiger>> allBlacktigerLive;
    private BlacktigerDao blacktigerDao;

   public BlacktigerRepository(Context context){
        BlacktigerDatabase blacktigerDatabase = BlacktigerDatabase.getDatabase(context.getApplicationContext());
        blacktigerDao = blacktigerDatabase.getBlacktigerDao();
        allBlacktigerLive = blacktigerDao.getAllBlacktigerLive();
    }

    public void insertBlacktiger(Blacktiger... blacktigers){
        new InsertAsyncTask(blacktigerDao).execute(blacktigers);
    }

    public void updateBlacktiger(Blacktiger... blacktigers){
        new UpdateAsyncTask(blacktigerDao).execute(blacktigers);
    }
    public void deleteBlacktiger(Blacktiger... blacktigers){
        new DeleteAsyncTask(blacktigerDao).execute(blacktigers);
    }
    public void deleteAllBlacktiger(){
        new DeleteAllAsyncTask(blacktigerDao).execute();
    }



    public LiveData<List<Blacktiger>> findBlacktigerWithPattern(String pattern){
        return blacktigerDao.findWordsWithPattern("%" + pattern + "%");
    }
    public LiveData<List<Blacktiger>> getAllBlacktigerLive(){
        return allBlacktigerLive;
    }
    public LiveData<List<Blacktiger>> getAllBlacktigersLiveByAmount(){
        return blacktigerDao.getAllBlacktigerLiveByAmount();
    }

    private static class InsertAsyncTask extends AsyncTask<Blacktiger,Void,Void> {
        private BlacktigerDao blacktigerDao;
        public InsertAsyncTask(BlacktigerDao blacktigerDao) {
            this.blacktigerDao = blacktigerDao;
        }

        @Override
        protected Void doInBackground(Blacktiger... blacktigers) {
            blacktigerDao.insertBlacktiger(blacktigers);
            return null;
        }
    }
    private static class UpdateAsyncTask extends AsyncTask<Blacktiger,Void,Void> {
        private BlacktigerDao blacktigerDao;
        public UpdateAsyncTask(BlacktigerDao blacktigerDao) {
            this.blacktigerDao = blacktigerDao;
        }

        @Override
        protected Void doInBackground(Blacktiger... blacktigers) {
            blacktigerDao.updateBlacktiger(blacktigers);
            return null;
        }
    }
    private static class DeleteAsyncTask extends AsyncTask<Blacktiger,Void,Void> {
        private BlacktigerDao blacktigerDao;
        public DeleteAsyncTask(BlacktigerDao blacktigerDao) {
            this.blacktigerDao = blacktigerDao;
        }

        @Override
        protected Void doInBackground(Blacktiger... blacktigers) {
            blacktigerDao.deleteBlacktiger(blacktigers);
            return null;
        }
    }
    private static class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void> {
        private BlacktigerDao blacktigerDao;
        public DeleteAllAsyncTask(BlacktigerDao blacktigerDao) {
            this.blacktigerDao = blacktigerDao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            blacktigerDao.deleteAllBlacktiger();
            return null;
        }
    }

}
