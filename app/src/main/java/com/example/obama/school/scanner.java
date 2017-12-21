package com.example.obama.school;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class scanner extends AppCompatActivity {
    //session
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static String stu_id = "stu_idlKey";
    SharedPreferences sharedpreferences;

    private Activity mainactivity;
    private TextView scan_content;
    private ImageView pic;

    private Button submit;
    String my_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scanner);

        //檢查是否取得權限




        //抓取 mb_id
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        my_id = sharedpreferences.getString(stu_id, "F");

        pic = (ImageView)findViewById(R.id.imageView2);

        init_view();

        IntentIntegrator scanIntegrator = new IntentIntegrator(mainactivity);
        scanIntegrator.initiateScan();

    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        final String[] name = new String[2];
        final IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if(scanningResult!=null){


            final Handler mHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    if(msg.what == 1) {
                        scan_content.setText(name[0]);

                        Picasso.with(scanner.this)
                                .load(name[1])
                                .resize(200, 200)
                                .onlyScaleDown()
                                .placeholder(R.drawable.giphy)
                                .error(R.drawable.sad)
                                .into(pic);
                    }
                    super.handleMessage(msg);
                }
            };


            new Thread() {
                @Override
                public void run() {
                    final String scanContent=scanningResult.getContents();
                    try {
                        String result = DBConnector.executeQuery("SELECT active_name,active_pic FROM active WHERE active_id = '"+scanContent+"'");
                        JSONArray jsonArray = new JSONArray(result);
                        for(int i = 0; i < jsonArray.length(); i++) {
                            final JSONObject jsonData = jsonArray.getJSONObject(i);

                            Message msg = mHandler.obtainMessage();
                            msg.what = 1;
                            msg.sendToTarget();

                            name[0] = jsonData.getString("active_name");
                            name[1] = jsonData.getString("active_pic");

                            submit.setOnClickListener(new Button.OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    new Thread() {
                                        @Override
                                        public void run() {
                                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                                            Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間
                                            final String date = formatter.format(curDate);
                                            delete.executeQuery("INSERT INTO point_record (stu_id,active_id,add_point,update_at) VALUES ('"+my_id+"','"+scanContent+"','5','"+date+"')");
                                            delete.executeQuery("INSERT INTO proof (student_id,active_id) VALUES ('"+my_id+"','"+scanContent+"')");
                                            update.executeQuery("student SET stu_point = stu_point+5 WHERE stu_id = '"+my_id+"' ");
                                            Intent intent = new Intent();
                                            intent.setClass(scanner.this, history_point.class);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                            scanner.this.finish();
                                            }
                                        }.start();
                                }
                            });

                        }

                    }catch (Exception e) {
                        Log.e("log_tag123", e.toString());
                    }
                }
            }.start();
            //scan_content.setText(scanContent);
        }else{
            Toast.makeText(getApplicationContext(),"nothing",Toast.LENGTH_SHORT).show();
        }
    }
    private void init_view(){
        this.scan_content=(TextView)findViewById(R.id.scan_content);
        this.submit = (Button)findViewById(R.id.submit);
        this.mainactivity=this;
    }
}
