package com.example.yanqijs.http;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yanqijs on 2016/2/22.
 */
public class HttpConnect {
    private Context context;

    public void connect() {
        HttpResponse httpResponse = null;
        HttpClient httpClient = new DefaultHttpClient();
//        HttpGet httpGet = new HttpGet("http://www.baidu.com");
        HttpPost httpPost = new HttpPost("http://10.255.209.67:8080/ddim//producer/query/list");
        List<NameValuePair> param = new ArrayList<>();
        param.add(new BasicNameValuePair("cursor", "0"));
        param.add(new BasicNameValuePair("page_size", "20"));
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(param, "utf-8"));
            httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = httpResponse.getEntity();
                String Stringresponse = EntityUtils.toString(entity, "utf-8");
                Log.i("bb", Stringresponse);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.i("bb", e.toString());
        }

    }

    public void connectVolley(Context context) {
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "http://ip.taobao.com/service/getIpInfo.php?ip=63.223.108.42",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.i("result----------volley", response.getInt("code") + "");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    public void connectUrl() {
        try {
            URL url = new URL("http://ip.taobao.com/service/getIpInfo.php?ip=63.223.108.42");
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
//            con.connect();
//            OutputStream out=con.getOutputStream();
//            OutputStreamWriter outputStreamWriter=new OutputStreamWriter(out);
//            outputStreamWriter.write("a");
            InputStream inputStream = con.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = null;
            StringBuffer result = new StringBuffer();
            while ((line = bufferedReader.readLine()) != null) {
                Log.i("------result----------", line);
                result.append(line);
            }
            Log.i("------result----------", result.toString() + "");

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
