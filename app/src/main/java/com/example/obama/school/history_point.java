package com.example.obama.school;

import android.app.ProgressDialog;
import android.content.Context;
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

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class history_point extends AppCompatActivity {

    private TabLayout mTablayout;
    private ViewPager mViewPager;
    private List<PageView> pageList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_point);

        initData();
        initView();

        ImageButton imageButton12 = (ImageButton) findViewById(R.id.imageButton12);
        imageButton12.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                history_point.this.finish();
            }
        });
    }

    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new SamplePagerAdapter());


        mTablayout = (TabLayout) findViewById(R.id.tabs);
        mTablayout.addTab(mTablayout.newTab().setText("活動紀錄"));
        mTablayout.addTab(mTablayout.newTab().setText("兌換紀錄"));
        initListener();
    }
    private void initData() {
        pageList = new ArrayList<>();
        pageList.add(new PageOneView(history_point.this));
        pageList.add(new PageTwoView(history_point.this));
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
        public PageOneView(Context context) {
            super(context);

            View view = LayoutInflater.from(context).inflate(R.layout.activity_record, null);
            LinearLayout.LayoutParams layoutParams0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            addView(view,layoutParams0);

            record_list = (LinearLayout)view.findViewById(R.id.record_list);
            sql = "SELECT * FROM active INNER JOIN point_record ON active.active_id = point_record.active_id WHERE stu_id = '1' ORDER BY update_at DESC";
            new TestAsyncTask(context).execute(sql);


        }

        public void findViews(String str)
        {

            try {
                final String result = str;

                JSONArray jsonArray = new JSONArray(result);
                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonData = jsonArray.getJSONObject(i);

                    LinearLayout gift_aaa = new LinearLayout(history_point.this);
                    gift_aaa.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    layoutParams.setMargins(60,0,60,0);
                    gift_aaa.setBackground(this.getResources().getDrawable(R.drawable.buttom_line));

                    LinearLayout img = new LinearLayout(history_point.this);
                    img.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,0.5f);
                    layoutParams2.setMargins(0,30,0,30);

                    TextView gift_name = new TextView(history_point.this);
                    gift_name.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                    gift_name.setPadding(0,40,0,30);
                    gift_name.setTextSize(17);
                    gift_name.setTextColor(Color.BLACK);
                    gift_name.setText(jsonData.getString("update_at"));

                    TextView gift_time = new TextView(history_point.this);
                    gift_time.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                    gift_time.setTextSize(17);
                    gift_time.setPadding(0,0,0,30);
                    gift_time.setTextColor(Color.BLACK);
                    gift_time.setText(jsonData.getString("active_name"));

                    LinearLayout text = new LinearLayout(history_point.this);
                    text.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,2f);
                    layoutParams3.gravity = Gravity.CENTER;


                    ImageView coin_img = new ImageView(history_point.this);
                    coin_img.setBackground(this.getResources().getDrawable(R.drawable.coin));
                    coin_img.setScaleType(ImageView.ScaleType.FIT_XY);
                    LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(70, 70);
                    layoutParams4.gravity = Gravity.CENTER_VERTICAL;

                    TextView gift_coin = new TextView(history_point.this);
                    gift_coin.setTextSize(18);
                    gift_coin.setTextColor(Color.GRAY);
                    gift_coin.setPadding(20,0,0,0);
                    LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
                    layoutParams5.gravity = Gravity.CENTER_VERTICAL;
                    gift_coin.setText(jsonData.getString("add_point"));

                    img.addView(gift_name);
                    img.addView(gift_time);

                    text.addView(coin_img,layoutParams4);
                    text.addView(gift_coin,layoutParams5);

                    gift_aaa.addView(img,layoutParams2);
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

    public class PageTwoView extends PageView{
        LinearLayout record_list;
        String sql;
        public PageTwoView(Context context) {
            super(context);
            View view = LayoutInflater.from(context).inflate(R.layout.exchange_record, null);
            LinearLayout.LayoutParams layoutParams0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            addView(view,layoutParams0);

            record_list = (LinearLayout)view.findViewById(R.id.record_list);
            sql = "SELECT * FROM gift_record INNER JOIN gift ON gift_record.gift_id = gift.gift_id WHERE stu_id = '1' ORDER BY updated_at DESC";
            new TestAsyncTask(context).execute(sql);

        }

        public void findViews(String str)
        {

            try {
                final String result = str;

                JSONArray jsonArray = new JSONArray(result);
                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonData = jsonArray.getJSONObject(i);

                    LinearLayout gift_aaa = new LinearLayout(history_point.this);
                    gift_aaa.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    layoutParams.setMargins(60,0,60,0);
                    gift_aaa.setBackground(this.getResources().getDrawable(R.drawable.buttom_line));

                    LinearLayout img = new LinearLayout(history_point.this);
                    img.setOrientation(LinearLayout.VERTICAL);
                    LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,0.5f);
                    layoutParams2.setMargins(0,30,0,30);

                    TextView gift_name = new TextView(history_point.this);
                    gift_name.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                    gift_name.setPadding(0,40,0,30);
                    gift_name.setTextSize(17);
                    gift_name.setTextColor(Color.BLACK);
                    gift_name.setText(jsonData.getString("updated_at"));

                    TextView gift_time = new TextView(history_point.this);
                    gift_time.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                    gift_time.setTextSize(17);
                    gift_time.setPadding(0,0,0,30);
                    gift_time.setTextColor(Color.BLACK);
                    gift_time.setText(jsonData.getString("gift_name"));

                    LinearLayout text = new LinearLayout(history_point.this);
                    text.setOrientation(LinearLayout.HORIZONTAL);
                    LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,2f);
                    layoutParams3.gravity = Gravity.CENTER;


                    ImageView coin_img = new ImageView(history_point.this);
                    coin_img.setBackground(this.getResources().getDrawable(R.drawable.coin));
                    coin_img.setScaleType(ImageView.ScaleType.FIT_XY);
                    LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(70, 70);
                    layoutParams4.gravity = Gravity.CENTER_VERTICAL;

                    TextView gift_coin = new TextView(history_point.this);
                    gift_coin.setTextSize(18);
                    gift_coin.setTextColor(Color.GRAY);
                    gift_coin.setPadding(20,0,0,0);
                    LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f);
                    layoutParams5.gravity = Gravity.CENTER_VERTICAL;
                    gift_coin.setText(jsonData.getString("less_point"));

                    img.addView(gift_name);
                    img.addView(gift_time);

                    text.addView(coin_img,layoutParams4);
                    text.addView(gift_coin,layoutParams5);

                    gift_aaa.addView(img,layoutParams2);
                    gift_aaa.addView(text,layoutParams3);
                    record_list.addView(gift_aaa,layoutParams);

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
}
