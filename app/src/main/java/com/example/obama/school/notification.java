package com.example.obama.school;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class notification extends AppCompatActivity {

    //session
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String mb_id = "mb_idlKey";
    public static final String beacon_check = "beacon_checklKey";
    SharedPreferences sharedpreferences;

    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_ENABLE_BT = 1;
    private Handler mHandler;
    private static final long SCAN_PERIOD = 10000; //10 seconds
    RadioButton trun_on;
    RadioButton trun_off;
    RadioGroup group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        ImageButton imageButton12 = (ImageButton) findViewById(R.id.imageButton12);
        imageButton12.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                notification.this.finish();
            }
        });

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        //SharedPreferences.Editor editor = sharedpreferences.edit();
        //editor.putString(beacon_check, "0");
        //editor.commit();

        //beacon
        trun_on = (RadioButton)findViewById(R.id.radioButton4);
        trun_off = (RadioButton)findViewById(R.id.radioButton3);
        group = (RadioGroup) findViewById(R.id.group1);
        group.setOnCheckedChangeListener(radGrpRegionOnCheckedChange);
        String my_beacon = sharedpreferences.getString(beacon_check, "0");

        if(my_beacon.equals("0"))
        {
            Log.d("radio State=", "0");
            group.check(R.id.radioButton3);
        }
        if(my_beacon.equals("1"))
        {
            Log.d("radio State=", "1");
            group.check(R.id.radioButton4);
        }
        new TestAsyncTask(this).execute();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //scanLeDevice(false);

    }


    public void func(){
        mHandler = new Handler();

        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // 檢查手機硬體是否為BLE裝置
        if (!getPackageManager().hasSystemFeature
                (PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "硬體不支援", Toast.LENGTH_SHORT).show();
            finish();

        }

        // 檢查手機是否開啟藍芽裝置
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Toast.makeText(this, "請開啟藍芽裝置", Toast.LENGTH_SHORT).show();
            Intent enableBluetooth = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, REQUEST_ENABLE_BT);


        } else {
            Log.d("MainActivity", "beacon start");
            Intent intent = new Intent(notification.this, MyService.class);
            startService(intent);

        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("This app needs location access");
                builder.setMessage("Please grant location access so this app can detect beacons.");
                builder.setPositiveButton(android.R.string.ok, null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dislog) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_COARSE_LOCATION);
                    }
                });
                builder.show();
            }
        }
    }

    private RadioGroup.OnCheckedChangeListener radGrpRegionOnCheckedChange =
            new RadioGroup.OnCheckedChangeListener()
            {


                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId)
                {

                    switch (checkedId)
                    {
                        //SharedPreferences.Editor editor = sharedpreferences.edit();
                        case R.id.radioButton4:
                            Log.d("MainActivity", "onCreate call");
                            func();
                            //啟動服務
                            Log.d("MainActivity","start");
                            //update beacon status to 1
                            SharedPreferences.Editor editor = sharedpreferences.edit();
                            editor.putString(beacon_check, "1");
                            editor.commit();
                            //start beacon server
                            Intent intent = new Intent(notification.this, MyService.class);
                            startService(intent);
                            break;

                        case R.id.radioButton3:
                            Log.d("MainActivity","stop");
                            //update beacon status to 0
                            SharedPreferences.Editor editor1 = sharedpreferences.edit();
                            editor1.putString(beacon_check, "0");
                            editor1.commit();
                            //stop beacon server
                            Intent intent1 = new Intent(notification.this, MyService.class);
                            stopService(intent1);
                            stopService(intent1);
                            stopService(intent1);
                            stopService(intent1);
                            break;

                    }
                }
            };
}
