package com.eleven.newstaff.functions.ui;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.eleven.newstaff.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 针对具体的Activity实现侧滑菜单
 * Created by eleven on 16/2/24.
 */
public class SwipeMenuDemoActivity extends Activity implements View.OnTouchListener {

    @Bind(R.id.ll_menu)
    LinearLayout menu;
    @Bind(R.id.ll_content)
    LinearLayout content;

    public static int SNAP_VELOCITY = 30;
    private int xDown;
    private int xUp;
    private int xMove;
    private boolean isMenuVisible;

    private int screenWidth;
    private int leftEdge;
    private int rightEdge;
    private int menuPadding = 300;
    private LinearLayout.LayoutParams menuParams;
    private VelocityTracker mVelocityTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swipe_menu);
        ButterKnife.bind(this);
        initValues();
        content.setOnTouchListener(this);
    }

    private void initValues() {
        WindowManager window = (WindowManager) getBaseContext().getSystemService(Context.WINDOW_SERVICE);
        screenWidth = window.getDefaultDisplay().getWidth();
        menuParams = (LinearLayout.LayoutParams) menu.getLayoutParams();
        menuParams.width = screenWidth - menuPadding;
        leftEdge = -menuParams.width;
        menuParams.leftMargin = leftEdge;
        menu.setLayoutParams(menuParams);
        content.getLayoutParams().width = screenWidth;
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
                if (isMenuVisible){
                    menuParams.leftMargin = rightEdge;
                }else{
                    menuParams.leftMargin = distanceX + leftEdge;
                }
                if (menuParams.leftMargin < leftEdge){
                    menuParams.leftMargin = leftEdge;
                }else if (menuParams.leftMargin >rightEdge){
                    menuParams.leftMargin = rightEdge;
                }
                menu.setLayoutParams(menuParams);
                break;
            case MotionEvent.ACTION_UP:
                xUp = (int) event.getRawX();
                if (wantToShowMenu()){
                    if(shouldScrollToMenu()){
                        scrollToMenu();
                    }else if (shouldScrollToContent()){
                        scrollToContent();
                    }
                }else if (wantToShowContent()){
                    if (shouldScrollToContent()){
                        scrollToContent();
                    }else if (shouldScrollToMenu()){
                        scrollToMenu();
                    }
                }
                break;
        }
        return true;
    }

    private void scrollToContent() {
        new ScrollTask().execute(-30);
    }

    private void scrollToMenu() {
        new ScrollTask().execute(30);
    }

    /**
     * 是否显示Menu:
     * 1. 移动距离为正数，且超过屏幕的1/2
     * 2. 或者移动速度超过原定速度
     * @return
     */
    private boolean shouldScrollToMenu() {
        return xUp - xDown > screenWidth/2 || getScrollVelocity() > SNAP_VELOCITY;
    }

    private boolean shouldScrollToContent(){
        return xDown - xUp + menuPadding > screenWidth/2 || getScrollVelocity() > SNAP_VELOCITY;
    }

    /**
     * 判断当前手势是否想显示菜单：
     * a. 手指移动距离为正数
     * b. 当前的menu没有显示
     * @return
     */
    private boolean wantToShowMenu() {
        return xUp - xDown > 0 && !isMenuVisible;
    }

    /**
     * 判断当前是否想显示Content：
     * 1. 移动的距离为负数
     * 2. 菜单正在显示
     * @return
     */
    private boolean wantToShowContent(){
        return xUp - xDown < 0 && isMenuVisible;
    }

    private void createVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    private int getScrollVelocity(){
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getXVelocity();
        return Math.abs(velocity);
    }

    class ScrollTask extends AsyncTask<Integer,Integer,Integer>{
        @Override
        protected Integer doInBackground(Integer... params) {
            int leftMargin = menuParams.leftMargin;
            //根据传入的速度来滚动界面，当滚动到达左边界或右边界时，跳出循环
            while (true) {
                leftMargin = leftMargin + params[0];
                if (leftMargin > rightEdge) {
                    leftMargin = rightEdge;
                    break;
                }
                if (leftMargin < leftEdge) {
                    leftMargin = leftEdge;
                    break;
                }
                publishProgress(leftMargin);
                //为了要有滚动效果，每次循环线程睡眠20毫秒，这样肉眼才能够看到滚动动画
                sleep(20);
            }
            if (params[0] > 0) {
                isMenuVisible = true;
            } else {
                isMenuVisible = false;
            }
            return leftMargin;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            menuParams.leftMargin = values[0];
            menu.setLayoutParams(menuParams);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            menuParams.leftMargin = integer;
            menu.setLayoutParams(menuParams);
        }
    }

    public void sleep(int millis){
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
