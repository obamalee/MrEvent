package com.example.obama.school;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Activity_list extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //session
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static String stu_id = "stu_idlKey";
    SharedPreferences sharedpreferences;

    String type;
    String sql;
    String my_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //抓取 mb_id
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        my_id = sharedpreferences.getString(stu_id, "F");
        Log.v("23",my_id);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent_type = this.getIntent();
        type = intent_type.getStringExtra("active_type");





        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Activity_list.this, Activity_list.class);
                intent.putExtra("active_type","6");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                Activity_list.this.finish();
            }
        });

        Button button2 = (Button)findViewById(R.id.button2);
        button2.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Activity_list.this, qrcode.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        Button button3 = (Button)findViewById(R.id.button3);
        button3.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Activity_list.this, Activity_list.class);
                intent.putExtra("active_type","5");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                Activity_list.this.finish();
            }
        });

        Button button4 = (Button)findViewById(R.id.button4);
        button4.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Activity_list.this, Activity_list.class);
                intent.putExtra("active_type","4");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                Activity_list.this.finish();
            }
        });

        Button button5 = (Button)findViewById(R.id.button5);
        button5.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Activity_list.this, Activity_list.class);
                intent.putExtra("active_type","3");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                Activity_list.this.finish();
            }
        });

        Button button6 = (Button)findViewById(R.id.button6);
        button6.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Activity_list.this, Activity_list.class);
                intent.putExtra("active_type","2");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                Activity_list.this.finish();
            }
        });

        Button button7 = (Button)findViewById(R.id.button7);
        button7.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Activity_list.this, Activity_list.class);
                intent.putExtra("active_type","1");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                Activity_list.this.finish();
            }
        });

        Button button8 = (Button)findViewById(R.id.button8);
        button8.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Activity_list.this, Activity_list.class);
                intent.putExtra("active_type","7");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                Activity_list.this.finish();
            }
        });

        Button button9 = (Button) findViewById(R.id.button9);
        button9.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(intent.ACTION_VIEW);
                intent.setData(Uri.parse("http://cge.cycu.edu.tw/"));
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        Button button10 = (Button) findViewById(R.id.button10);
        button10.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Activity_list.this, student_center.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });


        /*new Thread(new Runnable() {

            @Override
            public void run() {

                //延迟两秒
                try {
                    Thread.sleep( 50 );
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        findViews();
                        Toast.makeText(Activity_list.this, "載入完成", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).start();*/

        SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate1 = new Date(System.currentTimeMillis()) ; // 獲取當前時間
        final String str1 = formatter1.format(curDate1);

        if(type.equals("6"))
        {
            sql = "SELECT * FROM active INNER JOIN note ON active.active_id = note.active_id INNER JOIN type ON active.active_type_id = type.active_type_id WHERE note.stu_id = '"+my_id+"' " +
                    "and active_ending >= '"+str1+"' ORDER BY active_ending ASC";
        }
        else if(type.equals("7"))
        {
            sql = "SELECT * FROM type INNER JOIN active ON type.active_type_id = active.active_type_id WHERE hot = 'T' " +
                    "and active_ending >= '"+str1+"' ORDER BY active_ending ASC";
        }else
        {
            sql = "SELECT * FROM type INNER JOIN active ON type.active_type_id = active.active_type_id WHERE active.active_type_id = '"+type+"' " +
                    "and active_ending >= '"+str1+"' ORDER BY active_ending ASC";
        }

       new TestAsyncTask1(this).execute(sql);
    }

    public void findViews(String str) {

        LinearLayout list = (LinearLayout)findViewById(R.id.list);



        try {


            String result = str;

            JSONArray jsonArray = new JSONArray(result);
            for(int i = 0; i < jsonArray.length(); i++) {
                final JSONObject jsonData = jsonArray.getJSONObject(i);

                final String active_id = jsonData.getString("active_id");

                LinearLayout list_aaa = new LinearLayout(Activity_list.this);
                list_aaa.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0,40,0,10);
                list_aaa.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(Activity_list.this, activity.class);
                        try {
                            intent.putExtra("active_id",jsonData.getString("active_id"));
                            intent.putExtra("active_name",jsonData.getString("active_name"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });

                LinearLayout list_img = new LinearLayout(Activity_list.this);
                list_img.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1.2f);

                ImageView acv_img = new ImageView(Activity_list.this);
                acv_img.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 580));
                acv_img.setScaleType(ImageView.ScaleType.FIT_XY);

               // String url = jsonData.getString("active_pic");
                //acv_img.setImageDrawable(loadImage(url));
                    Picasso.with(this)
                            .load(jsonData.getString("active_pic"))
                            .resize(500, 580)
                            .onlyScaleDown()
                            .placeholder(R.drawable.giphy)
                            .error(R.drawable.sad)
                            .into(acv_img);




                LinearLayout list_text = new LinearLayout(Activity_list.this);
                list_img.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1.0f);
                layoutParams2.setMargins(30,0,30,0);

                LinearLayout list_text_v = new LinearLayout(Activity_list.this);
                list_text_v.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

                TextView date = new TextView(Activity_list.this);
                date.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                date.setPadding(0,0,0,30);
                date.setText(jsonData.getString("active_ending"));
                date.setTextSize(18);
                date.setTextColor(Color.BLACK);

                TextView title = new TextView(Activity_list.this);
                title.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                title.setPadding(0,0,0,30);
                title.setText(jsonData.getString("active_name"));
                title.setTextSize(18);
                title.setTextColor(Color.BLACK);

                TextView speaker = new TextView(Activity_list.this);
                speaker.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                speaker.setPadding(0,0,0,0);
                if(jsonData.getString("active_performer").equals("null")){
                speaker.setText(" ");}
                else {speaker.setText(jsonData.getString("active_performer"));}
                speaker.setTextSize(18);
                speaker.setTextColor(Color.GRAY);

                LinearLayout list_end = new LinearLayout(Activity_list.this);
                list_end.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                layoutParams4.gravity = Gravity.RIGHT;
                list_end.setGravity(Gravity.RIGHT);

                final ImageButton like = new ImageButton(Activity_list.this);
                LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(100,100);
                layoutParams5.gravity = Gravity.BOTTOM;
                layoutParams5.setMargins(0,0,30,0);
                like.setBackgroundColor(Color.TRANSPARENT);
                like.setScaleType(ImageView.ScaleType.FIT_CENTER);

                final Handler mHandler = new Handler() {
                    @Override
                    public void handleMessage(Message msg) {
                        if(msg.what == 1) {
                            like.setBackground(getResources().getDrawable(R.drawable.love));
                        }
                        if (msg.what == 2){
                            like.setBackground(getResources().getDrawable(R.drawable.heart));
                        }
                        super.handleMessage(msg);
                    }
                };

                new Thread() {
                    @Override
                    public void run() {
                        try {
                            String result1 = DBConnector.executeQuery("SELECT * FROM note INNER JOIN student ON note.stu_id = student.stu_id WHERE active_id= '" + active_id + "' AND note.stu_id = '"+my_id+"'");
                            JSONArray jsonArray1 = new JSONArray(result1);
                            Message msg = mHandler.obtainMessage();
                            msg.what = 1;
                            msg.sendToTarget();

                        } catch (Exception e) {
                            Message msg = mHandler.obtainMessage();
                            msg.what = 2;
                            msg.sendToTarget();
                        }

                        like.setOnClickListener(new Button.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                new Thread() {
                                    @Override
                                    public void run() {
                                        try {
                                            String result1 = DBConnector.executeQuery("SELECT * FROM student INNER JOIN note ON student.stu_id = note.stu_id WHERE active_id= '" + active_id + "' AND note.stu_id = '"+my_id+"'");
                                            JSONArray jsonArray1 = new JSONArray(result1);
                                            delete.executeQuery("DELETE FROM note WHERE active_id = " + active_id + " AND stu_id = '"+my_id+"'");
                                            Message msg = mHandler.obtainMessage();
                                            msg.what = 2;
                                            msg.sendToTarget();

                                        } catch (Exception e) {

                                            delete.executeQuery("INSERT INTO note(stu_id,active_id) VALUES ('"+my_id+"'," + active_id + ")");
                                            Message msg = mHandler.obtainMessage();
                                            msg.what = 1;
                                            msg.sendToTarget();
                                        }

                                    }
                                }.start();
                            }
                        });
                    }
                }.start();




                Button tag = new Button(Activity_list.this);
                LinearLayout.LayoutParams layoutParams6 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,120);
                layoutParams6.gravity = Gravity.BOTTOM;
                tag.setBackgroundColor(0xFF008888);
                tag.setText("#"+jsonData.getString("active_type"));
                tag.setTextColor(Color.WHITE);
                tag.setTextSize(15);
                tag.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.setClass(Activity_list.this, Activity_list.class);
                        try {
                            intent.putExtra("active_type",jsonData.getString("active_type_id"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        Activity_list.this.finish();
                    }
                });

                list_img.addView(acv_img);

                list_end.addView(like,layoutParams5);
                list_end.addView(tag,layoutParams6);

                list_text_v.addView(date);
                list_text_v.addView(title);
                list_text_v.addView(speaker);
                list_text_v.addView(list_end,layoutParams4);

                list_text.addView(list_text_v,layoutParams3);

                list_aaa.addView(list_img,layoutParams1);
                list_aaa.addView(list_text,layoutParams2);

                list.addView(list_aaa,layoutParams);

            }
        } catch(Exception e) {
             Log.e("log_tag", e.toString());

            TextView title_nothing = new TextView(Activity_list.this);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT);
            layoutParams.setMargins(0,50,0,0);
            title_nothing.setText("此類別暫時無任何活動");
            title_nothing.setTextSize(18);
            title_nothing.setTextColor(Color.GRAY);
            title_nothing.setGravity(Gravity.CENTER_VERTICAL);
            title_nothing.setGravity(Gravity.CENTER_HORIZONTAL);
            layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
            layoutParams.gravity = Gravity.CENTER_VERTICAL;

            list.addView(title_nothing,layoutParams);

        }

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
