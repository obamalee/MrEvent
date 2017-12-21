package com.example.obama.school;

import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

public class main_menu extends AppCompatActivity {



    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;
    private BluetoothManager bluetoothManager;
    private BluetoothAdapter mBluetoothAdapter;
    private static final int REQUEST_ENABLE_BT = 1;
    private Handler mHandler;
    private static final long SCAN_PERIOD = 1000; //1 seconds


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        Intent intent1 = new Intent(main_menu.this, NoteService.class);
        startService(intent1);

        Log.d("MainActivity", "onCreate call");
        mHandler = new Handler();

        bluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();

        // 檢查手機硬體是否為BLE裝置
        if (!getPackageManager().hasSystemFeature
                (PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "硬體不支援", Toast.LENGTH_SHORT).show();
            finish();

        }

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        //沒有權限時
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(main_menu.this,
                    new String[]{Manifest.permission.CAMERA},
                    1);
        } else {
            Toast.makeText(this, "已經拿到權限囉!", Toast.LENGTH_SHORT).show();
        }

        // 檢查手機是否開啟藍芽裝置
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Toast.makeText(this, "請開啟藍芽裝置", Toast.LENGTH_SHORT).show();
            Intent enableBluetooth = new Intent(
                    BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBluetooth, REQUEST_ENABLE_BT);
            Intent intent = new Intent(main_menu.this, MyService.class);
            startService(intent);

        } else {
            Log.d("MainActivity", "beacon start");
            Intent intent = new Intent(main_menu.this, MyService.class);
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


        ImageButton imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
        new DownloadImageTask((ImageView) findViewById(R.id.imageButton1))
                .execute("http://140.135.168.101/school/Prose-Icon-Press-Release.png");
        imageButton1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(main_menu.this, Activity_list.class);
                intent.putExtra("active_type","7");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageButton imageButton2 = (ImageButton) findViewById(R.id.imageButton2);
        new DownloadImageTask((ImageView) findViewById(R.id.imageButton2))
                .execute("http://140.135.168.101/school/favourite512x512.png");
        imageButton2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(main_menu.this, Activity_list.class);
                intent.putExtra("active_type","6");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageButton imageButton3 = (ImageButton) findViewById(R.id.imageButton3);
        new DownloadImageTask((ImageView) findViewById(R.id.imageButton3))
                .execute("http://140.135.168.101/school/Music.png");
        imageButton3.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(main_menu.this, Activity_list.class);
                intent.putExtra("active_type","1");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageButton imageButton4 = (ImageButton) findViewById(R.id.imageButton4);
        new DownloadImageTask((ImageView) findViewById(R.id.imageButton4))
                .execute("http://140.135.168.101/school/401bacb05a711c6ceabcd9096b29ee1d-clapper.png");
        imageButton4.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(main_menu.this, Activity_list.class);
                intent.putExtra("active_type","2");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageButton imageButton5 = (ImageButton) findViewById(R.id.imageButton5);
        new DownloadImageTask((ImageView) findViewById(R.id.imageButton5))
                .execute("http://140.135.168.101/school/ICON-DANCE-300x3001.png");
        imageButton5.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(main_menu.this, Activity_list.class);
                intent.putExtra("active_type","3");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageButton imageButton6 = (ImageButton) findViewById(R.id.imageButton6);
        new DownloadImageTask((ImageView) findViewById(R.id.imageButton6))
                .execute("http://140.135.168.101/school/Icon_GuestSpeaker.png");
        imageButton6.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(main_menu.this, Activity_list.class);
                intent.putExtra("active_type","4");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageButton imageButton7 = (ImageButton) findViewById(R.id.imageButton7);
        new DownloadImageTask((ImageView) findViewById(R.id.imageButton7))
                .execute("http://140.135.168.101/school/add.png");
        imageButton7.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(main_menu.this, Activity_list.class);
                intent.putExtra("active_type","5");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        ImageButton imageButton8 = (ImageButton) findViewById(R.id.imageButton8);
        new DownloadImageTask(imageButton8)
                .execute("http://140.135.168.101/school/qr-code.png");
        imageButton8.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(main_menu.this, qrcode.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        new TestAsyncTask(this).execute();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[],
                                           int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d("MainActivity", "coarse location permission granted");
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                        }
                    });
                    builder.show();
                }
                return;
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        //scanLeDevice(false);

    }

}
