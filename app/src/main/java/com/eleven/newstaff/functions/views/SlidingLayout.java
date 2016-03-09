package com.eleven.newstaff.functions.views;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

/**
 * 侧滑实现
 * Created by eleven on 16/2/25.
 */
public class SlidingLayout extends LinearLayout implements View.OnTouchListener {

    //手指滑动的速度
    public static int SNAP_VELOCITY = 300;
    //计算当前滑动速度的辅助类
    private VelocityTracker mVelocityTracker;

    private View leftLayout;
    private View rightLayout;
    private View mBindView;
    private MarginLayoutParams leftLayoutParams;
    private MarginLayoutParams rightLayoutParams;

    private int leftEdge;
    private int rightEdge;
    private int leftLayoutEdge;
    private int leftLayoutPadding = 80;

    private int xDown;
    private int xMove;
    private int xUp;
    private boolean isLeftLayoutVisible;


    private int screenWidth;
    private int velocityTracker;

    public SlidingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        WindowManager window = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        screenWidth = window.getDefaultDisplay().getWidth();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        if (changed) {
            //获取左侧布局
            leftLayout = getChildAt(0);
            leftLayoutParams = (MarginLayoutParams) leftLayout.getLayoutParams();
            leftLayoutParams.width = screenWidth - leftLayoutPadding;
            leftEdge = -leftLayoutParams.width;
            leftLayoutParams.leftMargin = leftEdge;
            //一开始隐藏左侧布局
            leftLayout.setLayoutParams(leftLayoutParams);

            rightLayout = getChildAt(1);
            rightLayoutParams = (MarginLayoutParams) rightLayout.getLayoutParams();
            rightLayoutParams.width = screenWidth;
            rightLayout.setLayoutParams(rightLayoutParams);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        createVelocityTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = (int) event.getRawX();
                break;
            case MotionEvent.ACTION_MOVE:
                xMove = (int) event.getRawX();
                int distanceX = xMove - xDown;
                if (isLeftLayoutVisible) {
                    leftLayoutParams.leftMargin = distanceX;
                } else {
                    leftLayoutParams.leftMargin = leftEdge + distanceX;
                }
                if (leftLayoutParams.leftMargin < leftEdge) {
                    leftLayoutParams.leftMargin = leftEdge;
                } else if (leftLayoutParams.leftMargin > rightEdge) {
                    leftLayoutParams.leftMargin = rightEdge;
                }
                break;
            case MotionEvent.ACTION_UP:
                if (wantToShowLeftLayout()) {
                    if (shouldScrollToLeftLayout()){
                        scrollToLeftLayout();
                    }else{
                        scrollToContentLayout();
                    }
                }else if (wantToShowContentLayout()){
                    if (shouldScrollToContentLayout()){
                        scrollToContentLayout();
                    }else {
                        scrollToLeftLayout();
                    }
                }
                break;
        }
        return false;
    }

    private boolean shouldScrollToContentLayout() {
        return xDown - xUp + leftLayoutPadding > screenWidth /2 || getVelocityTracker() > SNAP_VELOCITY;
    }

    private boolean wantToShowContentLayout() {
        return false;
    }

    private void scrollToContentLayout() {

    }

    private void scrollToLeftLayout() {

    }

    private boolean shouldScrollToLeftLayout() {
        return xUp - xDown > screenWidth/2 || getVelocityTracker() > SNAP_VELOCITY;
    }

    private boolean wantToShowLeftLayout() {
        return xUp - xDown > 0 && !isLeftLayoutVisible;
    }

    private void createVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    private void recycleVelocityTracker() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }

    public int getVelocityTracker() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getXVelocity();
        return velocity;
    }
}
