package com.example.obama.school;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Obama on 2017/11/29.
 */

public class download {
    public static Drawable loadImage(String url){
        URL m;
        InputStream is = null;
        try {
            m = new URL(url);
            is = (InputStream) m.getContent();
            BufferedInputStream bis = new BufferedInputStream(is);
            //標記其實位置，供reset參考
            bis.mark(0);

            BitmapFactory.Options opts = new BitmapFactory.Options();
            //true,隻是讀圖片大小，不申請bitmap內存
            opts.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(bis, null, opts);
            Log.v("AsyncImageLoader", "width="+opts.outWidth+"; height="+opts.outHeight);

            int size = (opts.outWidth * opts.outHeight);
            if( size > 1024*1024*4){
                int zoomRate = 2;
                //zommRate縮放比，根據情況自行設定，如果為2則縮放為原來的1/2，如果為1不縮放
                if(zoomRate <= 0) zoomRate = 1;
                opts.inSampleSize = zoomRate;
                Log.v("AsyncImageLoader", "圖片過大，被縮放 1/"+zoomRate);
            }

            //設為false，這次不是預讀取圖片大小，而是返回申請內存，bitmap數據
            opts.inJustDecodeBounds = false;
            //緩沖輸入流定位至頭部，mark()
            bis.reset();
            Bitmap bm = BitmapFactory.decodeStream(bis, null, opts);

            is.close();
            bis.close();
            return (bm == null) ? null : new BitmapDrawable(bm);
        } catch (MalformedURLException e1) {
            Log.v("AsyncImageLoader", "MalformedURLException");
            e1.printStackTrace();
        } catch (IOException e) {
            Log.v("AsyncImageLoader", "IOException");
            e.printStackTrace();
        }
        return null;
    }
}
