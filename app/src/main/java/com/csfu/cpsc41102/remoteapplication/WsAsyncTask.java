package com.csfu.cpsc41102.remoteapplication;

import android.content.Context;
import android.os.AsyncTask;

public class WsAsyncTask extends AsyncTask<Void, Void, Void> {

    protected MainActivity activity;

    public WsAsyncTask(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        activity.testWebService_add();
        activity.testWebService();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        // Update the screen
        activity.updateScreen();
    }
}
