package com.example.obama.school;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import static com.example.obama.school.beacon.bytesToHex;


public class student_center extends AppCompatActivity {

    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_ENABLE_BT = 1;
    private Handler mHandler;
    private static final long SCAN_PERIOD = 0; //1 seconds



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_center);



        ImageButton imageButton12 = (ImageButton) findViewById(R.id.imageButton12);
        imageButton12.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                student_center.this.finish();
            }
        });

        ImageButton imageButton11 = (ImageButton) findViewById(R.id.imageButton11);
        imageButton11.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(student_center.this, notification.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageButton imageButton13 = (ImageButton) findViewById(R.id.imageButton13);
        imageButton13.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(student_center.this, qrcode.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageButton imageButton14 = (ImageButton) findViewById(R.id.imageButton14);
        imageButton14.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(student_center.this, point.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageButton scan = (ImageButton) findViewById(R.id.scan);
        scan.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1 = new Intent(student_center.this, MyService.class);
                stopService(intent1);
                mHandler = new Handler();
                bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
                mBluetoothAdapter = bluetoothManager.getAdapter();
                 scanLeDevice(true);

                /*Intent intent = new Intent();
                intent.setClass(student_center.this, scanner.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);*/
            }
        });


    }



    // 掃描藍芽裝置
    private void scanLeDevice(final boolean enable) {
        if (enable) {

            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.d("MainActivity", "satrattttttt");
                    mBluetoothAdapter.startLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD);
            Log.d("MainActivity", "satrrrrrrrrr");
            //mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            Log.d("MainActivity", "stopppppp");
            mBluetoothAdapter.stopLeScan(mLeScanCallback);

           // scanLeDevice(false);

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
                scanLeDevice(false);
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
                final int major = (scanRecord[startByte + 20] & 0xff) * 0x100
                        + (scanRecord[startByte + 21] & 0xff);

                // Minor
                final int minor = (scanRecord[startByte + 22] & 0xff) * 0x100
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



                if (major == 1 )
                {
                    scanLeDevice(false);
                    Intent intent = new Intent();
                    intent.setClass(student_center.this, scanner.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    scanLeDevice(false);

                }else
                    {

                        new AlertDialog.Builder(student_center.this)
                                .setTitle("搜尋失敗")//設定視窗標題
                                //.setIcon(R.mipmap.ic_launcher)//設定對話視窗圖示
                                .setMessage("搜尋失敗，請在試一次，major error")//設定顯示的文字
                                .setPositiveButton("關閉視窗",new DialogInterface.OnClickListener(){
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                })//設定結束的子視窗
                                .show();//呈現對話視窗

                        scanLeDevice(false);
                    }


            }else
            {


                new AlertDialog.Builder(student_center.this)
                        .setTitle("搜尋失敗")//設定視窗標題
                        //.setIcon(R.mipmap.ic_launcher)//設定對話視窗圖示
                        .setMessage("搜尋失敗，請在試一次")//設定顯示的文字
                        .setPositiveButton("關閉視窗",new DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })//設定結束的子視窗
                        .show();//呈現對話視窗

                scanLeDevice(false);
            }
        }
    };


}

