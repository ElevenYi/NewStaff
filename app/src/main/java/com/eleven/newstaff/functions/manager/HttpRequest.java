package com.eleven.newstaff.functions.manager;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.eleven.newstaff.basic.interfaces.VolleyHttpUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * http请求类
 * Created by eleven on 16/3/2.
 */
public class HttpRequest {

    public static void loginTest(final Context context){
        String url = "http://apitransfer.o3o.ac.cn/dev/api/?login2";



        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                Log.d("Test", s.toString());
                Toast.makeText(context,s.toString(),Toast.LENGTH_LONG);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                VolleyLog.d("volleyDebug","Error: " + volleyError.getMessage());
                Toast.makeText(context, volleyError.getMessage(), Toast.LENGTH_LONG);
            }
        }){

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> headers = new HashMap<>();
                headers.put("accept", "*/*");
                headers.put("connection", "Keep-Alive");
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                headers.put("apikey", "F846EC49077D80F27B955E908C14274F");
                headers.put("charset", "utf-8");
                return headers;
            }

//            @Override
//            public String getBodyContentType() {
//                return "application/x-www-form-urlencoded";
//            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<>();
                map.put("u","8888888888");
                map.put("p","8888888888");
                return map;
            }
        };
        VolleyHttpUtil.getInstance().addToRequestQueue(request);
    }
}
