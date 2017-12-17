package com.example.obama.school;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import org.json.JSONArray;
import org.json.JSONObject;

public class login extends AppCompatActivity {
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String stu_id = "stu_idlKey";
    SharedPreferences sharedpreferences;
    EditText id;
    EditText pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        Button button11 = (Button)findViewById(R.id.button11);
        button11.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://140.135.168.68/ajax/school/vertify/vertify.php"));
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        id = (EditText)findViewById(R.id.editText2);
        pwd = (EditText)findViewById(R.id.editText3);

        ImageButton imageButton = (ImageButton) findViewById(R.id.imageButton);

        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        String my_id = sharedpreferences.getString(stu_id, "F");

        if(my_id.equals("F") || my_id == null )
        {
        imageButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    @Override
                    public void run() {

                        try {
                            String result = LoginConnector.executeQuery(id.getText().toString(), pwd.getText().toString());
                            Log.v("456",id.getText().toString()+"+"+pwd.getText().toString());
                            Log.v("123",result);
                            JSONArray jsonArray = new JSONArray(result);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonData = jsonArray.getJSONObject(i);

                                if (jsonData.getString("stu_id") != null) {
                                    //TextView point = (TextView)findViewById(R.id.textView35);
                                    //point.setText("5656");
                                    SharedPreferences.Editor editor = sharedpreferences.edit();
                                    editor.putString(stu_id, jsonData.getString("stu_id"));
                                    editor.commit();

                                    //TextView point = (TextView)findViewById(R.id.textView35);
                                    //point.setText(sharedpreferences.getString(mb_id, "Not Value"));
                                    //point.setText(jsonData.getString("mb_id"));


                                    Intent intent = new Intent();
                                    intent.setClass(login.this, main_menu.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                                    login.this.finish();
                                }else{
                                    Looper.prepare();
                                    new AlertDialog.Builder(login.this)
                                            .setTitle("登入失敗")//設定視窗標題
                                            //.setIcon(R.mipmap.ic_launcher)//設定對話視窗圖示
                                            .setMessage("帳號密碼錯誤,請重新輸入\n請確認是否已驗證")//設定顯示的文字
                                            .setPositiveButton("關閉視窗",new DialogInterface.OnClickListener(){
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {

                                                }
                                            })//設定結束的子視窗
                                            .show();//呈現對話視窗
                                    Looper.loop();
                                }
                            }
                        } catch (Exception e) {
                            Log.e("log_tag", e.toString());
                            Looper.prepare();
                            new AlertDialog.Builder(login.this)
                                    .setTitle("登入失敗")//設定視窗標題
                                    //.setIcon(R.mipmap.ic_launcher)//設定對話視窗圖示
                                    .setMessage("帳號密碼錯誤,請重新輸入\n請確認是否已驗證")//設定顯示的文字
                                    .setPositiveButton("關閉視窗",new DialogInterface.OnClickListener(){
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })//設定結束的子視窗
                                    .show();//呈現對話視窗
                            Looper.loop();
                        }

                       /* Intent intent = new Intent();
                        intent.setClass(login.this, main_menu.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        login.this.finish();*/
                    }
                }.start();
            }
        });
        }else{

            Intent intent = new Intent();
            intent.setClass(login.this, main_menu.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            login.this.finish();
            Log.d("b",my_id);
        }


    }
}

