package com.example.obama.school;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.Integer.parseInt;

public class gift_item extends AppCompatActivity {
    //session
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static String stu_id = "stu_idlKey";
    SharedPreferences sharedpreferences;
    String my_id;

    String gift_id;
    String name;
    int coin;
    int point;
    String sql;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gift_item);

        //抓取 mb_id
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        my_id = sharedpreferences.getString(stu_id, "F");

        ImageButton imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
        imageButton1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                gift_item.this.finish();
            }
        });

        Intent intent_type = this.getIntent();
        gift_id = intent_type.getStringExtra("gift_id");

        sql = "SELECT * FROM gift WHERE gift_id = '"+gift_id+"' ";
        new TestAsyncTask1(this).execute(sql);

    }

    private void findViews(String str)
    {

        try {
            final String result = str;

            JSONArray jsonArray = new JSONArray(result);
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonData = jsonArray.getJSONObject(i);

                ImageView gift_pic = (ImageView)findViewById(R.id.imageView10);
                new DownloadImageTask(gift_pic)
                        .execute(jsonData.getString("gift_pic"));

                TextView gift_name = (TextView)findViewById(R.id.textView13);
                gift_name.setText(jsonData.getString("gift_name"));
                name = jsonData.getString("gift_name");

                Button gift_coin = (Button)findViewById(R.id.button4);
                gift_coin.setText(jsonData.getString("gift_coin"));
                coin = parseInt(jsonData.getString("gift_coin"));


                gift_coin.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new Thread() {
                            @Override
                            public void run() {

                                try {
                                    final String result = DBConnector.executeQuery("SELECT * FROM student WHERE stu_id = '"+my_id+"'");
                                    JSONArray jsonArray = new JSONArray(result);
                                    for(int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonData = jsonArray.getJSONObject(i);

                                        point = parseInt(jsonData.getString("stu_point"));
                                    }
                                } catch(Exception e) {
                                     Log.e("log_tag", e.toString());
                                }

                                if (point>coin)
                                {
                                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                    Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間
                                    final String str = formatter.format(curDate);

                                    Looper.prepare();
                                    new AlertDialog.Builder(gift_item.this)
                                            .setTitle("確定兌換")//設定視窗標題
                                            //.setIcon(R.mipmap.ic_launcher)//設定對話視窗圖示
                                            .setMessage("確定要兌換"+name+"?\n"+"兌換禮品之後，將不在退還點數呦!\n獎品需在7日內換取")//設定顯示的文字
                                            .setNegativeButton("確定兌換",new DialogInterface.OnClickListener(){
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    delete.executeQuery("INSERT INTO gift_box (stu_id,gift_id,used,updated_at) VALUES ('"+my_id+"',"+gift_id+",'F','"+str+"')");
                                                    update.executeQuery("student SET stu_point = '"+(point-coin)+"' WHERE stu_id = '"+my_id+"' ");
                                                    delete.executeQuery("INSERT INTO gift_record (gift_id,stu_id,less_point,updated_at) VALUES ('"+gift_id+"','"+my_id+"','"+coin+"','"+str+"')");
                                                    Intent intent = new Intent();
                                                    intent.setClass(gift_item.this,gift_box.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            })//設定結束的子視窗
                                            .setPositiveButton("下次再換",new DialogInterface.OnClickListener(){
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    finish();
                                                }
                                            })//設定結束的子視窗
                                            .show();//呈現對話視窗
                                    Looper.loop();
                                }else
                                {
                                    Looper.prepare();
                                    new AlertDialog.Builder(gift_item.this)
                                            .setTitle("兌換失敗")//設定視窗標題
                                            //.setIcon(R.mipmap.ic_launcher)//設定對話視窗圖示
                                            .setMessage("積點不足，無法兌換")//設定顯示的文字
                                            .setPositiveButton("關閉視窗",new DialogInterface.OnClickListener(){
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    finish();
                                                }
                                            })//設定結束的子視窗
                                            .show();//呈現對話視窗
                                    Looper.loop();
                                }

                            }
                        }.start();

                    }
                });

            }
        } catch(Exception e) {
            // Log.e("log_tag", e.toString());
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
