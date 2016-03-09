package com.eleven.newstaff.functions.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.eleven.newstaff.R;
import com.eleven.newstaff.functions.model.Images;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by eleven on 16/3/1.
 */
public class PhotoWallAdapter extends ArrayAdapter<String> implements OnScrollListener {

    private Set<BitmapWorkerTask> taskCollection;

    private LruCache<String, Bitmap> mMemoryCache;

    private GridView mPhotoWall;

    private int mFirstVisibleItem;

    private int mVisibleItemCount;

    private boolean isFirstEnter = true;

    public PhotoWallAdapter(Context context, int textViewResourceId, String[] objects, GridView photoWall) {
        super(context, textViewResourceId, objects);
        mPhotoWall = photoWall;
        taskCollection = new HashSet<BitmapWorkerTask>();
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        int cacheSize = maxMemory / 8;
        mMemoryCache = new LruCache<String,Bitmap>(cacheSize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                return value.getByteCount();
            }
        };
        mPhotoWall.setOnScrollListener(this);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final String url = getItem(position);
        View view;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(R.layout.layout_photo,null);
        }else{
            view = convertView;
        }
        final ImageView photo = (ImageView)view.findViewById(R.id.iv_photo);
        photo.setTag(url);
        setImageView(url, photo);
        return view;
    }

    public void setImageView(String imageUrl, ImageView imageView){
        Bitmap bitmap = getBitmapFromMemoryCache(imageUrl);
        if (bitmap != null){
            imageView.setImageBitmap(bitmap);
        }else{
            imageView.setImageResource(R.mipmap.empty_photo);
        }
    }

    private Bitmap getBitmapFromMemoryCache(String imageUrl) {
        return mMemoryCache.get(imageUrl);
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == SCROLL_STATE_IDLE){
            loadBitmaps(mFirstVisibleItem,mVisibleItemCount);
        }else{
            cancelAllTask();
        }
    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        mFirstVisibleItem = firstVisibleItem;
        mVisibleItemCount = visibleItemCount;
        if (isFirstEnter && visibleItemCount>0){
            loadBitmaps(mFirstVisibleItem,mVisibleItemCount);
            isFirstEnter = false;
        }
    }


    private void loadBitmaps(int mFirstVisibleItem, int mVisibleItemCount) {
        for (int i = mFirstVisibleItem; i < mFirstVisibleItem + mVisibleItemCount; i++) {
            String imageUrl = Images.imageThumbUrls[i];
            Bitmap bitmap = getBitmapFromMemoryCache(imageUrl);
            if (bitmap ==null){
                BitmapWorkerTask task = new BitmapWorkerTask();
                taskCollection.add(task);
                task.execute(imageUrl);
            }else{
                ImageView imageView = (ImageView) mPhotoWall.findViewWithTag(imageUrl);
                if (imageUrl != null && bitmap != null){
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }

    public void cancelAllTask() {
        if (taskCollection != null){
            for (BitmapWorkerTask bitmapWorkerTask : taskCollection) {
                bitmapWorkerTask.cancel(false);
            }
        }
    }

    class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {

        private String imageUrl;

        @Override
        protected Bitmap doInBackground(String... params) {
            imageUrl = params[0];
            Bitmap bitmap = downloadBitmap(params[0]);
            return null;
        }


        private Bitmap downloadBitmap(String imageUrl) {
            Bitmap bitmap = null;
            HttpURLConnection con = null;
            try {
                URL url = new URL(imageUrl);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(5 * 1000);
                con.setReadTimeout(10 * 1000);
                bitmap = BitmapFactory.decodeStream(con.getInputStream());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (con != null)
                    con.disconnect();
            }
            return bitmap;
        }
    }
}
