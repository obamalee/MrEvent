package com.example.obama.school;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class gift extends AppCompatActivity {
String sql;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gift);

        ImageButton imageButton1 = (ImageButton) findViewById(R.id.imageButton1);
        imageButton1.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                gift.this.finish();
            }
        });
        sql = "SELECT * FROM gift ";

        new TestAsyncTask1(this).execute(sql);

    }

    private void findViews(String str) {
        LinearLayout gift_list = (LinearLayout)findViewById(R.id.gift_list);
        try {
            final String result = str;

            JSONArray jsonArray = new JSONArray(result);
            for(int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonData = jsonArray.getJSONObject(i);

                final String gift_id = jsonData.getString("gift_id");
                LinearLayout gift_aaa = new LinearLayout(gift.this);
                gift_aaa.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.setMargins(0,20,0,20);
                gift_aaa.setPadding(0,0,0,0);
                gift_aaa.setOnClickListener(new Button.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra("gift_id",gift_id);
                        intent.setClass(gift.this, gift_item.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });

                LinearLayout img = new LinearLayout(gift.this);
                img.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1.2f);
                layoutParams1.setMargins(0,0,0,0);
                img.setPadding(0,0,0,0);

                ImageView gift_img = new ImageView(gift.this);
                Picasso.with(this)
                        .load(jsonData.getString("gift_pic"))
                        .fit()
                        .placeholder(R.drawable.giphy)
                        .error(R.drawable.oops)
                        .into(gift_img);
                gift_img.setScaleType(ImageView.ScaleType.FIT_XY);
                LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 300);
                layoutParams2.setMargins(0,0,0,0);

                LinearLayout text = new LinearLayout(gift.this);
                text.setOrientation(LinearLayout.VERTICAL);
                LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT,1.0f);
                layoutParams3.setMargins(20,0,0,0);
                text.setPadding(0,0,0,0);
                text.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_VERTICAL);
                layoutParams3.gravity = Gravity.CENTER_VERTICAL;

                TextView gift_name = new TextView(gift.this);
                gift_name.setText(jsonData.getString("gift_name"));
                gift_name.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                gift_name.setPadding(0,0,0,20);
                gift_name.setTextSize(18);
                gift_name.setTextColor(Color.BLACK);

                LinearLayout gift_bbb = new LinearLayout(gift.this);
                gift_bbb.setOrientation(LinearLayout.HORIZONTAL);
                LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                gift_bbb.setPadding(0,0,0,20);
                gift_bbb.setGravity(Gravity.CENTER_VERTICAL|Gravity.CENTER_VERTICAL);
                layoutParams4.gravity = Gravity.CENTER_VERTICAL;

                ImageView coin_img = new ImageView(gift.this);
                coin_img.setBackground(this.getResources().getDrawable(R.drawable.coin));
                gift_img.setScaleType(ImageView.ScaleType.FIT_XY);
                LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(60, 60);
                layoutParams5.setMargins(0,0,0,0);

                TextView gift_coin = new TextView(gift.this);
                gift_coin.setText(jsonData.getString("gift_coin"));
                gift_coin.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));
                gift_coin.setTextSize(16);
                gift_coin.setTextColor(Color.BLACK);

                img.addView(gift_img,layoutParams2);
                gift_aaa.addView(img,layoutParams1);
                text.addView(gift_name);
                gift_bbb.addView(coin_img,layoutParams5);
                gift_bbb.addView(gift_coin);
                text.addView(gift_bbb,layoutParams4);
                gift_aaa.addView(text,layoutParams3);
                gift_list.addView(gift_aaa,layoutParams);


            }
        } catch(Exception e) {
            // Log.e("log_tag", e.toString());
        }
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
