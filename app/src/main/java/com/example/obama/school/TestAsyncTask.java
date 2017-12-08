package com.example.obama.school;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.net.URL;

/**
 * Created by Obama on 2017/11/16.
 */

public class TestAsyncTask extends AsyncTask<URL, Integer, String> {

    private Context mContext;
    private ProgressDialog mDialog;

    public TestAsyncTask(Context mContext) {
        this.mContext = mContext;
    }



    protected void onPreExecute() {

        mDialog = new ProgressDialog(mContext);
        mDialog.setMessage("Loading...");
        mDialog.setCancelable(false);
        mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mDialog.show();
    }

    protected String doInBackground(URL... urls) {
        // TODO Auto-generated method stub
        int progress =0;
        while(progress<=100){

            publishProgress(Integer.valueOf(progress));
            progress++;
        }

        return "ok";
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        // TODO Auto-generated method stub
        mDialog.setProgress(progress[0]);
    }

    protected void onPostExecute(String result) {
        if(result.equals("ok")){
            mDialog.dismiss();
        }
    }

}
