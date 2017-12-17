package com.example.obama.school;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class gift_box extends AppCompatActivity {

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static String stu_id = "stu_idlKey";
    SharedPreferences sharedpreferences;

    private TabLayout mTablayout;
    private ViewPager mViewPager;
    private List<PageView> pageList;
    String my_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gift_box);

        //抓取 mb_id
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        my_id = sharedpreferences.getString(stu_id, "F");

        initData();
        initView();

        ImageButton imageButton1 = (ImageButton) findViewById(R.id.imageButton12);
        imageButton1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                gift_box.this.finish();
            }
        });

    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new SamplePagerAdapter());


        mTablayout = (TabLayout) findViewById(R.id.tabs);
        mTablayout.addTab(mTablayout.newTab().setText("未兌換"));
        mTablayout.addTab(mTablayout.newTab().setText("已兌換"));
        initListener();
    }
    private void initData() {
        pageList = new ArrayList<>();
        pageList.add(new PageOneView(gift_box.this));
        pageList.add(new PageTwoView(gift_box.this));
    }
    private void initListener() {
        mTablayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTablayout));
    }
    private class SamplePagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pageList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(pageList.get(position));
            return pageList.get(position);
        }
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

    }
    public class PageOneView extends PageView{
        LinearLayout record_list;
        String sql;
        String date;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis()) ; // 獲取當前時間
        Calendar rightNow = Calendar.getInstance();
        public PageOneView(Context context) {
            super(context);
            View view = LayoutInflater.from(context).inflate(R.layout.activity_record, null);
            LinearLayout.LayoutParams layoutParams0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            addView(view,layoutParams0);

            record_list = (LinearLayout)view.findViewById(R.id.record_list);

            sql = "SELECT * FROM gift_box INNER JOIN gift ON gift.gift_id = gift_box.gift_id WHERE stu_id = '"+my_id+"' and used = 'F' ";

                date = formatter.format(curDate);
            new TestAsyncTask(context).execute(sql);
        }

        public void findViews(String str)
        {
            try {
                final String result = str;

                JSONArray jsonArray = new JSONArray(result);
                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonData = jsonArray.getJSONObject(i);

                    rightNow.setTime(formatter.parse(jsonData.getString("updated_at")));
                    rightNow.add(Calendar.DAY_OF_YEAR,7);
                    String dt1=formatter.format(rightNow.getTime());

                    if((int)((formatter.parse(dt1).getTime()-formatter.parse(date).getTime())/(24*60*60*1000)) >= 0) {

                        LinearLayout gift_aaa = new LinearLayout(gift_box.this);
                        gift_aaa.setOrientation(LinearLayout.HORIZONTAL);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(0, 20, 0, 20);
                        gift_aaa.setPadding(0, 0, 0, 0);


                        LinearLayout img = new LinearLayout(gift_box.this);
                        img.setOrientation(LinearLayout.VERTICAL);
                        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.2f);
                        layoutParams1.setMargins(0, 0, 0, 0);
                        img.setPadding(0, 0, 0, 0);

                        ImageView gift_img = new ImageView(gift_box.this);
                        Picasso.with(getContext())
                                .load(jsonData.getString("gift_pic"))
                                .fit()
                                .placeholder(R.drawable.giphy)
                                .error(R.drawable.oops)
                                .into(gift_img);
                        gift_img.setScaleType(ImageView.ScaleType.FIT_XY);
                        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 300);
                        layoutParams2.setMargins(0, 0, 0, 0);

                        LinearLayout text = new LinearLayout(gift_box.this);
                        text.setOrientation(LinearLayout.VERTICAL);
                        LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.0f);
                        layoutParams3.setMargins(20, 0, 0, 0);
                        text.setPadding(0, 0, 0, 0);
                        text.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_VERTICAL);
                        layoutParams3.gravity = Gravity.CENTER_VERTICAL;

                        TextView gift_name = new TextView(gift_box.this);
                        gift_name.setText(jsonData.getString("gift_name"));
                        gift_name.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        gift_name.setPadding(0, 0, 0, 10);
                        gift_name.setTextSize(18);
                        gift_name.setTextColor(Color.BLACK);

                        TextView less_date = new TextView(gift_box.this);
                        less_date.setText("兌換日期剩餘"+(int)((formatter.parse(dt1).getTime()-formatter.parse(date).getTime())/(24*60*60*1000))+"天");
                        less_date.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        less_date.setPadding(0, 0, 0, 10);
                        less_date.setTextSize(14);
                        less_date.setTextColor(Color.GRAY);

                        Button change = new Button(gift_box.this);
                        change.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                        change.setTextAlignment(TEXT_ALIGNMENT_TEXT_START);
                        change.setText("立即兌換  ＞");

                        change.setTextColor(0xFFEE7700);
                        change.setBackgroundColor(Color.TRANSPARENT);
                        change.setTextSize(18);
                        change.setOnClickListener(new Button.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent();
                                intent.setClass(gift_box.this, qrcode.class);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                            }
                        });


                        img.addView(gift_img, layoutParams2);
                        gift_aaa.addView(img, layoutParams1);
                        text.addView(gift_name);
                        text.addView(less_date);
                        text.addView(change);
                        gift_aaa.addView(text, layoutParams3);
                        record_list.addView(gift_aaa, layoutParams);

                    }
                }
            } catch(Exception e) {
                // Log.e("log_tag", e.toString());
            }
        }

        public class TestAsyncTask extends AsyncTask<String, Integer, String> {

            private Context mContext;
            private ProgressDialog mDialog;

            public TestAsyncTask(Context mContext) {
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

        @Override
        public void refreshView() {

        }
    }

    public class PageTwoView extends PageView{
        LinearLayout record_list;
        String sql;
        public PageTwoView(Context context) {
            super(context);
            View view = LayoutInflater.from(context).inflate(R.layout.exchange_record, null);
            LinearLayout.LayoutParams layoutParams0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            addView(view,layoutParams0);

            record_list = (LinearLayout)view.findViewById(R.id.record_list);

            sql = "SELECT * FROM gift_box INNER JOIN gift ON gift.gift_id = gift_box.gift_id WHERE stu_id = '"+my_id+"' and used = 'T' ";
            new TestAsyncTask(context).execute(sql);
        }
        public void findViews(String str)
        {
            try {
                final String result = str;

                JSONArray jsonArray = new JSONArray(result);
                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonData = jsonArray.getJSONObject(i);

                    LinearLayout gift_aaa = new LinearLayout(gift_box.this);
                    gift_aaa.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0,20,0,20);
                    gift_aaa.setPadding(0,0,0,0);


                    LinearLayout img = new LinearLayout(gift_box.this);
                    img.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1.2f);
                    layoutParams1.setMargins(0,0,0,0);
                    img.setPadding(0,0,0,0);


                    ImageView gift_img = new ImageView(gift_box.this);
                    Picasso.with(getContext())
                            .load(jsonData.getString("gift_pic"))
                            .fit()
                            .placeholder(R.drawable.giphy)
                            .error(R.drawable.oops)
                            .into(gift_img);
                    gift_img.setScaleType(ImageView.ScaleType.FIT_XY);
                    gift_img.setAlpha(75);
                    LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 300);
                    layoutParams2.setMargins(0,0,0,0);

                    LinearLayout text = new LinearLayout(gift_box.this);
                    text.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1.0f);
                    layoutParams3.setMargins(20,0,0,0);
                    text.setPadding(0,0,0,0);
                    text.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_VERTICAL);
                    layoutParams3.gravity = Gravity.CENTER_VERTICAL;

                    TextView gift_name = new TextView(gift_box.this);
                    gift_name.setText(jsonData.getString("gift_name"));
                    gift_name.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                    gift_name.setPadding(0,0,0,20);
                    gift_name.setTextSize(18);
                    gift_name.setTextColor(Color.BLACK);

                    Button change = new Button(gift_box.this);
                    change.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                    change.setTextAlignment(TEXT_ALIGNMENT_TEXT_START);
                    change.setText("已兌換");
                    change.setTextColor(Color.GRAY);
                    change.setBackgroundColor(Color.TRANSPARENT);
                    change.setTextSize(18);




                    img.addView(gift_img,layoutParams2);
                    gift_aaa.addView(img,layoutParams1);
                    text.addView(gift_name);
                    text.addView(change);
                    gift_aaa.addView(text,layoutParams3);
                    record_list.addView(gift_aaa,layoutParams);


                }
            } catch(Exception e) {
                 Log.e("log_tag", e.toString());
            }
        }

        public class TestAsyncTask extends AsyncTask<String, Integer, String> {

            private Context mContext;
            private ProgressDialog mDialog;

            public TestAsyncTask(Context mContext) {
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

        @Override
        public void refreshView() {

        }
    }
}
