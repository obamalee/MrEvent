package com.example.obama.school;

import android.content.Context;
import android.widget.RelativeLayout;

/**
 * Created by Obama on 2017/11/15.
 */

public abstract class PageView extends RelativeLayout {
    public PageView(Context context) {
        super(context);
    }
    public abstract void refreshView();
}

