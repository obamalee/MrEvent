package com.example.obama.school;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

import java.util.ArrayList;
import java.util.List;

public class activity extends AppCompatActivity {

    private TabLayout mTablayout;
    private ViewPager mViewPager;
    private List<PageView> pageList;
    String active_id;
    String sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity);


        Intent intent = this.getIntent();
        active_id = intent.getStringExtra("active_id");
        Log.v("id",active_id);

        final String active_name = intent.getStringExtra("active_name");
        TextView name = (TextView)findViewById(R.id.textView11);

        initData();
        initView();



        ImageButton imageButton12 = (ImageButton) findViewById(R.id.imageButton12);
        imageButton12.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                activity.this.finish();
            }
        });
    }
    private void initView() {
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(new SamplePagerAdapter());


        mTablayout = (TabLayout) findViewById(R.id.tabs);
        mTablayout.addTab(mTablayout.newTab().setText("活動介紹"));
        mTablayout.addTab(mTablayout.newTab().setText("活動海報"));
        initListener();
    }
    private void initData() {
        pageList = new ArrayList<>();
        pageList.add(new PageOneView(activity.this));
        pageList.add(new PageTwoView(activity.this));
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
        TextView date;
        TextView location;
        TextView info;
        TextView active_name;
        public PageOneView(Context context) {
            super(context);
            View view = LayoutInflater.from(context).inflate(R.layout.activity_intro, null);
            LinearLayout.LayoutParams layoutParams0 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            date = (TextView)view.findViewById(R.id.textView17);
            location = (TextView)view.findViewById(R.id.textView18);
            info = (TextView)view.findViewById(R.id.textView20);
            active_name = (TextView)view.findViewById(R.id.textView12);
            sql = "SELECT * FROM active WHERE active_id = '"+active_id+"'";
            new TestAsyncTask(context).execute(sql);

            addView(view,layoutParams0);
        }

         public void findViews(String str){
            try {


                String result = str;
                Log.v("123",result);
                JSONArray jsonArray = new JSONArray(result);
                for(int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonData = jsonArray.getJSONObject(i);

                    date.setText(jsonData.getString("active_ending"));
                    location.setText(jsonData.getString("location"));
                    info.setText(jsonData.getString("active_info"));
                    active_name.setText(jsonData.getString("active_name"));

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
        ImageView pic;
        public PageTwoView(Context context) {
            super(context);
            View view = LayoutInflater.from(context).inflate(R.layout.activity_img, null);
            pic = (ImageView)view.findViewById(R.id.imageView5);

            sql = "SELECT * FROM active WHERE active_id = '"+active_id+"'";
            new TestAsyncTask(context).execute(sql);



            addView(view);
        }
        public void findViews(String str){
            try {
                String result = str;

                JSONArray jsonArray = new JSONArray(result);
                for(int i = 0; i < jsonArray.length(); i++) {
                    final JSONObject jsonData = jsonArray.getJSONObject(i);


                    Picasso.with(getContext())
                            .load(jsonData.getString("active_pic"))
                            .placeholder(R.drawable.giphy)
                            .error(R.drawable.sad)
                            .resize(1500,1500)
                            .onlyScaleDown()
                            .into(pic);

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
