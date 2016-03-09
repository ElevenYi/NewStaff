package com.eleven.newstaff.functions.ui;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.eleven.newstaff.R;

/**
 * Created by eleven on 16/2/24.
 */
public class SlideMenuActivity extends Activity implements View.OnTouchListener {

    //滚动显示和隐藏menu时，手指滑动需要达到的速度
    public static final int SNAP_VELOCITY = 200;
    //屏幕的宽度值
    private int screenWidth;
    //menu最多可以滑动到的左边缘，值有menu的布局的宽度来决定，marginleft到达此值之后，不能再减少
    private int leftEdge;
    //menu最多可以滑动到的右边缘，值恒为0，即marginLeft到达0之后，不能增加
    private int rightEdge;
    //menu完全显示时，留给content的宽度值
    private int menuPadding = 200;
    //主内容
    private View content;
    //菜单
    private View menu;
    //menu的布局参数，通过此参数来修改marginleft的值
    private LinearLayout.LayoutParams menuParams;
    //手指按下时的x坐标
    private float xDown;
    private float xUp;
    //手指移动时的x坐标
    private float xMove;
    //menu当前是显示还是隐藏，只有完全显示或隐藏menu时才会更改此值，滑动过程中此值无效
    private boolean isMenuVisible;
    //用于计算手指滑动的速度。
    private VelocityTracker mVelocityTracker;
    private int scrollVelocity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slide_menu);

        initValues();
        content.setOnTouchListener(this);
    }

    /**
     * 初始化一些关键数据。包括获取屏幕宽度，给content布局重新设置宽度，给menu布局重新设置宽度和偏移距离等
     */
    private void initValues() {
        WindowManager window = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        screenWidth = window.getDefaultDisplay().getWidth();
        content = findViewById(R.id.ll_content);
        menu = findViewById(R.id.ll_menu);
        menuParams = (LinearLayout.LayoutParams) menu.getLayoutParams();
        //将menu的宽度设置为屏幕宽度减去menuPadding
        menuParams.width = screenWidth - menuPadding;
        //左边缘的值赋值为menu宽度的负值，代表偏移量
        leftEdge = -menuParams.width;
        menuParams.leftMargin = leftEdge;
        //将content的宽度设置为屏幕的宽度
        content.getLayoutParams().width = screenWidth;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        createVelocityTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //按下时记录下横坐标
                xDown = event.getRawX();
                Log.d("TAG", "手指按下时的x坐标是： " + xDown);
                Log.d("TAG", "手指按下时的坐标是： " + xDown + "," + event.getRawY());
                break;
            case MotionEvent.ACTION_MOVE:
                xMove = event.getRawX();
                Log.d("TAG", "手指移动时的x坐标是： " + String.valueOf(xDown));
                int distanceX = (int) (xMove - xDown);
                if (isMenuVisible) {
                    menuParams.leftMargin = distanceX;
                } else {
                    menuParams.leftMargin = leftEdge + distanceX;
                }
                if (menuParams.leftMargin < leftEdge) {
                    menuParams.leftMargin = leftEdge;
                } else if (menuParams.leftMargin > rightEdge) {
                    menuParams.leftMargin = rightEdge;
                }
                Log.d("TAG", "手指移动时menu的leftMargin值：" + menuParams.leftMargin);
                menu.setLayoutParams(menuParams);
                break;
            case MotionEvent.ACTION_UP:
                xUp = event.getRawX();
                Log.d("TAG", "手指松开时x的坐标是：" + xUp);
                Log.d("TAG", "手指松开时的坐标是： " + xDown + "," + event.getRawY());
                if (wantToShowMenu()) {
                    if (shouldScrollToMenu()) {
                        scrollToMenu();
                    } else {
                        scrollToContent();
                    }
                } else if (wantToShowContent()) {
                    if (shouldScrollToContent()) {
                        scrollToContent();
                    } else
                        scrollToMenu();
                }
                recycleVelocityTracker();
                break;
        }
        return true;
    }


    /**
     * 判断是否应该滚动将content展示出来，如果手指移动的距离加上menuPadding大于屏幕的1/2，
     * 或者手指移动速度大于SNAP_VELOCITY，就认为应该滚动将content展示出来
     *
     * @return
     */
    private boolean shouldScrollToContent() {
        return xDown - xUp + menuPadding > screenWidth / 2 || getScrollVelocity() > SNAP_VELOCITY;
    }

    /**
     * 判断当前手势操作是不是想显示Content：如果手指移动的距离是负数，且当前menu是可见的，则认为当前手势是想要显示Content
     *
     * @return
     */
    private boolean wantToShowContent() {
        return xUp - xDown < 0 && isMenuVisible;
    }

    /**
     * 判断当前手势的意图是不是想显示menu。如果手指移动的距离是正数，且当前menu是不可见的，则认为当前手势是想要显示menu
     *
     * @return
     */
    private boolean wantToShowMenu() {
        return xUp - xDown > 0 && !isMenuVisible;
    }


    private void scrollToContent() {
        new ScrollTask().execute(-30);
    }

    private void scrollToMenu() {
        new ScrollTask().execute(30);
    }

    /**
     * 判断是否应该显示menu：如果手指移动的距离超过屏幕的1/2 或者 手指移动的速度大于SNAP_VELOCITY，
     * 就认为应该显示menu
     *
     * @return
     */
    private boolean shouldScrollToMenu() {
        return xUp - xDown > screenWidth / 2 || getScrollVelocity() > SNAP_VELOCITY;
    }


    /**
     * 获取VelocityTracker的实例
     *
     * @param event
     */
    private void createVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * 回收VelocityTracker
     */
    private void recycleVelocityTracker() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }

    public int getScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getXVelocity();
        return Math.abs(velocity);
    }


    class ScrollTask extends AsyncTask<Integer, Integer, Integer> {
        @Override
        protected Integer doInBackground(Integer... speed) {
            int leftMargin = menuParams.leftMargin;
            //根据传入的速度来滚动界面，当滚动到达左边界或右边界时，跳出循环
            while (true) {
                leftMargin = leftMargin + speed[0];
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
            if (speed[0] > 0) {
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

    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
