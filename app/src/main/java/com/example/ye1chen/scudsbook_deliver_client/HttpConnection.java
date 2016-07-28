package com.example.ye1chen.scudsbook_deliver_client;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by ye1.chen on 3/1/16.
 */
public class HttpConnection {

    private static final String URL_SERVER = "http://scudsbook.x10host.com/BackServer/Server_Scudsbook_User_Handler.php";
    private static int respCode = -1;

    public static String postRequest(HashMap<String, String> postDataParams, int readTimeout, int connectTimeout) {
        String response = "";
        try {
            byte[] postDataBytes = getPostDataString(postDataParams).getBytes("UTF-8");
            URL url = new URL(URL_SERVER);

            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setReadTimeout(readTimeout);
            httpUrlConnection.setConnectTimeout(connectTimeout);
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setInstanceFollowRedirects( false );
            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpUrlConnection.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
            httpUrlConnection.getOutputStream().write(postDataBytes);
            httpUrlConnection.connect();
            int responseCode=httpUrlConnection.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {
                String line;
                BufferedReader br=new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response+=line;
                }
            }
            else {
                response="";
            }
            httpUrlConnection.disconnect();
        } catch (Exception e) {
            Log.e("HttpConnection", "Error in parsing IMPACT response: \n" + Log.getStackTraceString(e));
        }
        return response;
    }

    private static String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
