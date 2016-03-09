package com.eleven.newstaff.basic.interfaces;

import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by eleven on 16/3/2.
 */
public class VolleyHttpUtil {

    public static final String TAG = "VolleyHttpUtil";

    private RequestQueue mRequestQueue;

    private Context context;

    public VolleyHttpUtil() {
    }

    private static VolleyHttpUtil sInstance;

    public static synchronized VolleyHttpUtil getInstance(){
        if (sInstance == null){
            sInstance = new VolleyHttpUtil();
        }
        return sInstance;
    }

    public RequestQueue getRequestQueue(){
        if (mRequestQueue == null){
            mRequestQueue = Volley.newRequestQueue(context);
        }
        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request, String tag){
        request.setTag(TextUtils.isEmpty(tag)?TAG:tag);
        getRequestQueue().add(request);
    }

    public <T> void addToRequestQueue(Request<T> request){
        request.setTag(TAG);
        getRequestQueue().add(request);
    }

    public void cancelPedingRequest(Object tag){
        if (mRequestQueue != null){
            mRequestQueue.cancelAll(tag);
        }
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
