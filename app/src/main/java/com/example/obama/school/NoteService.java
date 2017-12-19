package com.example.obama.school;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;

/**
 * Created by Obama on 2017/12/14.
 */

public class NoteService extends Service {

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static String stu_id = "stu_idlKey";
    SharedPreferences sharedpreferences;
    String my_id;

    private Handler handler = new Handler();

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onStart(Intent intent, int startId) {
        handler.postDelayed(showTime, 1000);
        Log.v("123","456");
        super.onStart(intent, startId);
    }

    @Override
    public void onDestroy() {
        handler.removeCallbacks(showTime);
        super.onDestroy();
    }

    private Runnable showTime = new Runnable() {
        public void run() {
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
            Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間
            final String str = formatter.format(curDate);

            SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
            Date curDate1 = new Date(System.currentTimeMillis()) ; // 獲取當前時間
            final String str1 = formatter1.format(curDate1);
            //抓取 mb_id
            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            my_id = sharedpreferences.getString(stu_id, "F");
        if(str.equals("08:00")) {
            new Thread() {
                @Override
                public void run() {
                    try {
                        String result = DBConnector.executeQuery("SELECT * FROM active INNER JOIN note ON note.active_id = active.active_id WHERE stu_id = '" + my_id + "' and active_ending = '" + str1 + "'");
                        Log.v("result",result);
                        JSONArray jsonArray = new JSONArray(result);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonData = jsonArray.getJSONObject(i);
                            Log.v("id",jsonData.getString("active_id"));
                            //
                            //notify
                            Looper.prepare();
                            NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                            //Step2. 設定當按下這個通知之後要執行的activity
                            Intent notifyIntent = new Intent(NoteService.this, activity.class);
                            notifyIntent.putExtra("active_id", jsonData.getString("active_id"));
                            notifyIntent.putExtra("active_name", jsonData.getString("active_name"));
                            notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            PendingIntent appIntent = PendingIntent.getActivity(NoteService.this, 0, notifyIntent, FLAG_UPDATE_CURRENT);
                            //Step3. 透過 Notification.Builder 來建構 notification，
                            //並直接使用其.build() 的方法將設定好屬性的 Builder 轉換
                            //成 notification，最後開始將顯示通知訊息發送至狀態列上。
                            Notification notification
                                    = new Notification.Builder(NoteService.this)
                                    .setContentIntent(appIntent)
                                    .setSmallIcon(R.drawable.cycu) // 設置狀態列裡面的圖示（小圖示）　　
                                    .setLargeIcon(BitmapFactory.decodeResource(NoteService.this.getResources(), R.drawable.cycu)) // 下拉下拉清單裡面的圖示（大圖示）
                                    .setTicker("MR.Event 喜愛活動通知") // 設置狀態列的顯示的資訊
                                    .setWhen(System.currentTimeMillis())// 設置時間發生時間
                                    .setAutoCancel(false) // 設置通知被使用者點擊後是否清除  //notification.flags = Notification.FLAG_AUTO_CANCEL;
                                    .setContentTitle("您的最愛："+jsonData.getString("active_name") + "將在今天舉行") // 設置下拉清單裡的標題
                                    .setContentText("將在" + jsonData.getString("location") + "舉行")// 設置上下文內容
                                    .setOngoing(true)      //true使notification變為ongoing，用戶不能手動清除// notification.flags = Notification.FLAG_ONGOING_EVENT; notification.flags = Notification.FLAG_NO_CLEAR;
                                    .setDefaults(Notification.DEFAULT_ALL) //使用所有默認值，比如聲音，震動，閃屏等等
                                    .setDefaults(Notification.DEFAULT_VIBRATE) //使用默認手機震動提示
                                    .build();

                            // 將此通知放到通知欄的"Ongoing"即"正在運行"組中
                            notification.flags = Notification.FLAG_ONGOING_EVENT;

                            // 表明在點擊了通知欄中的"清除通知"後，此通知不清除，
                            // 經常與FLAG_ONGOING_EVENT一起使用
                            notification.flags = Notification.FLAG_NO_CLEAR;

                            //閃爍燈光
                            notification.flags = Notification.FLAG_SHOW_LIGHTS;


                            // 把指定ID的通知持久的發送到狀態條上.
                            mNotificationManager.notify(0, notification);

                            Looper.loop();


                        }
                    } catch (Exception e1) {

                    }
                }
            }.start();
        }
            Log.v("time",str);
            handler.postDelayed(this, 50*1000);
        }
    };

}
