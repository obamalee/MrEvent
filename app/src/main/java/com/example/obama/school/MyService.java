package com.example.obama.school;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.IBinder;
import android.os.StrictMode;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.example.obama.school.beacon.bytesToHex;

/**
 * Created by Obama on 2017/12/8.
 */

public class MyService extends Service {


    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String beacon_check = "beacon_checklKey";
    public static final String mb_id = "mb_idlKey";
    SharedPreferences sharedpreferences;

    private Handler handler = new Handler();
    //beacon
    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_ENABLE_BT = 1;
    private Handler mHandler;
    private static final long SCAN_PERIOD = 1000; //1 seconds


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate()
    {
        super.onCreate();
        // TODO Auto-generated method stub
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build());
        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
                .detectLeakedSqlLiteObjects()
                .penaltyLog()
                .penaltyDeath()
                .build());
    }
    @Override
    public void onStart(Intent intent, int startId) {
        //handler.postDelayed(showTime, 1000);
        super.onStart(intent, startId);
        //beacon
        mHandler = new Handler();
        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        scanLeDevice(true);

        Log.d("testtest", "dennis123456789");
        //update beacon status to 1
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(beacon_check, "1");
        editor.commit();



    }


    @Override
    public void onDestroy() {
        //mHandler.removeCallbacks(showTime);

        //beacon server stop
        mBluetoothAdapter.stopLeScan(mLeScanCallback);
        //update beacon status to 1
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(beacon_check, "0");
        editor.commit();
        super.onDestroy();
    }

    //private Runnable showTime = new Runnable() {
    //    public void run() {
    //        //log目前時間

    //        Log.d("time:", new Date().toString());
    //        handler.postDelayed(this, 1000);
    //    }
    //};


    // 掃描藍芽裝置
    private void scanLeDevice(final boolean enable) {
        if (enable) {

            //Runnable showTime = new  Runnable(){
            //   @Override
            //   public void run() {
            //       Log.d("MainActivity", "satrattttttt");
            //mBluetoothAdapter.stopLeScan(mLeScanCallback);
            //       mBluetoothAdapter.startLeScan(mLeScanCallback);
            //       handler.postDelayed(this, 1000);
            //   }

            //};

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d("MainActivity", "satrattttttt");
                    //mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    mBluetoothAdapter.startLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD);
            Log.d("MainActivity", "satrrrrrrrrr");
            //mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            Log.d("MainActivity", "stopppppp");
            mBluetoothAdapter.stopLeScan(mLeScanCallback);

        }

    }


    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {



        @Override

        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            // 搜尋回饋
            Log.d("MainActivity", "BLE device : " + device.getName());

            int startByte = 2;
            boolean patternFound = false;
            // 尋找ibeacon
            // 先依序尋找第2到第8陣列的元素
            while (startByte <= 5) {
                // Identifies an iBeacon
                if (((int) scanRecord[startByte + 2] & 0xff) == 0x02 &&
                        // Identifies correct data length
                        ((int) scanRecord[startByte + 3] & 0xff) == 0x15) {

                    patternFound = true;
                    break;
                }
                startByte++;
            }

            // 如果找到了的话
            if (patternFound) {
                //mBluetoothAdapter.stopLeScan(mLeScanCallback);
                String asd = mBluetoothAdapter.getName();
                Log.d("4564s5454",asd);
                // 轉換16進制
                byte[] uuidBytes = new byte[16];
                // 來源、起始位置
                System.arraycopy(scanRecord, startByte + 4, uuidBytes, 0, 16);
                //String hexString = beacon.bytesToHex(uuidBytes);
                String hexString = bytesToHex(uuidBytes);

                // UUID
                String uuid = hexString.substring(0, 8) + "-"
                        + hexString.substring(8, 12) + "-"
                        + hexString.substring(12, 16) + "-"
                        + hexString.substring(16, 20) + "-"
                        + hexString.substring(20, 32);

                // Major
                int major = (scanRecord[startByte + 20] & 0xff) * 0x100
                        + (scanRecord[startByte + 21] & 0xff);

                // Minor
                int minor = (scanRecord[startByte + 22] & 0xff) * 0x100
                        + (scanRecord[startByte + 23] & 0xff);

                String mac = device.getAddress();
                // txPower
                int txPower = (scanRecord[startByte + 24]);
                double distance = beacon.calculateAccuracy(txPower, rssi);

                Log.d("MainActivity", "Name：" + "\nMac：" + mac
                        + " \nUUID：" + uuid + "\nMajor：" + major + "\nMinor："
                        + minor + "\nTxPower：" + txPower + "\nrssi：" + rssi);

                Log.d("MainActivity", "distance：" + distance);


                //mBluetoothAdapter.startLeScan(mLeScanCallback);

                //距離<5
                if (distance < 5){
                    try {
                        String result = DBConnector.executeQuery("SELECT * FROM beacon WHERE major='"+major+"'");

                        JSONArray jsonArray = new JSONArray(result);
                        for(int i = 0; i < jsonArray.length(); i++)
                        {
                            JSONObject jsonData = jsonArray.getJSONObject(i);
                            String beacon_id = jsonData.getString("beacon_id");
                            Log.d("MainActivity","beacon_id:"+beacon_id);
                            //major == 1 && minor == 0 &&
                            //抓取 mb_id
                            sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                            String my_id = sharedpreferences.getString(mb_id, "F");
                            String this_beacon = beacon_id;
                            String day ="2017-10-24";
                            //查詢

                            String result2 = DBConnector.executeQuery("SELECT * FROM beacon_record WHERE beacon_id='"+this_beacon+"' AND mb_id='"+my_id+"'");// AND day='"+day+"'

                            JSONArray jsonArray2 = new JSONArray(result2);
                            for(int j = 0; j < jsonArray2.length(); j++)
                            {
                                JSONObject jsonData2 = jsonArray2.getJSONObject(j);
                                String record = jsonData2.getString("beacon_record_id");

                                if(record != null){
                                    Log.d("MainActivity","got it!!");
                                }

                            }

                            // Log.e("log_tag", e.toString());

                            //Log.d("MainActivity","789:"+e.toString());
                            //insert

                        }
                    } catch(Exception e) {
                        // Log.e("log_tag", e.toString());
                        Log.d("MainActivity","456:"+e.toString());
                        //抓取 mb_id
                        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
                        String my_id = sharedpreferences.getString(mb_id, "F");
                        String day = "2017-10-24" ;

                        try {
                            String result = DBConnector.executeQuery("SELECT * FROM beacon WHERE major='" + major + "'");

                            JSONArray jsonArray = new JSONArray(result);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonData = jsonArray.getJSONObject(i);
                                String beacon_id = jsonData.getString("beacon_id");

                                //
                                //notify
                                NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                                //Step2. 設定當按下這個通知之後要執行的activity
                                Intent notifyIntent = new Intent(MyService.this, main_menu.class);
                                notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                PendingIntent appIntent = PendingIntent.getActivity(MyService.this, 0, notifyIntent, 0);
                                //Step3. 透過 Notification.Builder 來建構 notification，
                                //並直接使用其.build() 的方法將設定好屬性的 Builder 轉換
                                //成 notification，最後開始將顯示通知訊息發送至狀態列上。
                                Notification notification
                                        = new Notification.Builder(MyService.this)
                                        .setContentIntent(appIntent)
                                        .setSmallIcon(R.drawable.cycu) // 設置狀態列裡面的圖示（小圖示）　　
                                        .setLargeIcon(BitmapFactory.decodeResource(MyService.this.getResources(), R.drawable.cycu)) // 下拉下拉清單裡面的圖示（大圖示）
                                        .setTicker("VenUs 優惠通知") // 設置狀態列的顯示的資訊
                                        .setWhen(System.currentTimeMillis())// 設置時間發生時間
                                        .setAutoCancel(false) // 設置通知被使用者點擊後是否清除  //notification.flags = Notification.FLAG_AUTO_CANCEL;
                                        .setContentTitle("VenUs 優惠通知") // 設置下拉清單裡的標題
                                        .setContentText("威尼斯影城新片推出~")// 設置上下文內容
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


                                //INSERT
                                delete.executeQuery("INSERT INTO beacon_record(beacon_id,mb_id,day) VALUES ("+beacon_id+","+my_id+"," + day + ")");
                            }
                        }catch (Exception e1) {

                        }
                    }
                }
            }
        }
    };
}
