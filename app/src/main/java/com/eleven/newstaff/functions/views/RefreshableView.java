package com.eleven.newstaff.functions.views;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by eleven on 16/2/26.
 */
public class RefreshableView extends LinearLayout implements View.OnTouchListener {

    public static final int STATUS_PULL_TO_REFRESH = 0;
    public static final int STATUS_RELEASE_TO_REFRESH = 1;
    public static final int STATUS_REFRESHING = 2;
    public static final int STATUS_REFRESH_FINISHED = 3;
    public static final int SCROLL_SPEED = -20;
    public static final long ONE_MINUTE = 60 * 1000;
    public static final long ONE_HOUR = 60 * ONE_MINUTE;
    public static final long ONE_DAY = 24 * ONE_HOUR;
    public static final long ONE_MONTH = 30 * ONE_DAY;
    private static final String UPDATED_AT = "updated_at";
    private PullToRefreshListener mListener;
    private SharedPreferences preferences;

    private View header;
    private ListView listView;
    private ProgressBar progressBar;
    private ImageView arrow;
    private TextView description;
    private TextView updatedAt;
    private MarginLayoutParams headerLayoutParams;
    private long lastUpdateTime;

    private int mId = -1;
    private int hideHeaderHeight;
    private int currentStatus = STATUS_REFRESH_FINISHED;
    private int lastStatus = currentStatus;
    private float yDown;
    private int touchSlop;
    private boolean ableToPull;


    public RefreshableView(Context context, AttributeSet attrs) {
        super(context, attrs);
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
//        header = LayoutInflater.from(context).inflate();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    public interface PullToRefreshListener {
        void onRefresh();
    }
}
