package com.example.obama.school;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

public class point extends AppCompatActivity {
String sql;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.point);

        ImageButton imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
        imageButton1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                point.this.finish();
            }
        });

        Button button22 = (Button) findViewById(R.id.button22);
        button22.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(point.this, history_point.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        Button button24 = (Button) findViewById(R.id.button24);
        button24.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(point.this, qrcode.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        Button button23 = (Button) findViewById(R.id.button23);
        button23.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(point.this, gift.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        Button button25 = (Button) findViewById(R.id.button25);
        button25.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(point.this, gift_box.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        sql = "SELECT * FROM student WHERE stu_id = '1'";
        new TestAsyncTask1(this).execute(sql);
    }

    private void findViews(String str){
        try {
            final String result = str;

            JSONArray jsonArray = new JSONArray(result);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonData = jsonArray.getJSONObject(i);
                TextView point = (TextView)findViewById(R.id.textView21);
                point.setText(jsonData.getString("stu_point"));
            }
        }catch(Exception e) {
            Log.e("log_tag", e.toString());
        }
    }

    public class TestAsyncTask1 extends AsyncTask<String, Integer, String> {

        private Context mContext;
        private ProgressDialog mDialog;

        public TestAsyncTask1(Context mContext) {
            this.mContext = mContext;
        }



        protected void onPreExecute() {

            mDialog = new ProgressDialog(mContext);
            mDialog.setMessage("Loading...");
            mDialog.setCancelable(false);
            mDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mDialog.show();

        }

        protected String doInBackground(String...strings) {
            // TODO Auto-generated method stub
            int progress =0;
            String result = DBConnector.executeQuery(sql) ;
            while(progress<=100){

                publishProgress(Integer.valueOf(progress));
                progress++;
            }
            return result;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            // TODO Auto-generated method stub
            mDialog.setProgress(progress[0]);

        }

        protected void onPostExecute(String result) {

            findViews(result);
            mDialog.dismiss();

        }

    }
}
