package com.example.ye1chen.scudsbook_deliver_client;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ye1.chen on 3/1/16.
 */
public class HttpConnection {

    private static final String URL_SERVER = "";
    private static int respCode = -1;

    public void makePostRequest(String d_lat, String d_lan) {
        try {
            URL url = new URL(URL_SERVER);
            HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection();
            httpUrlConnection.setChunkedStreamingMode(0);
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setRequestProperty("lat", d_lat);
            httpUrlConnection.setRequestProperty("lan", d_lan);
            httpUrlConnection.connect();
            OutputStream request = new BufferedOutputStream(httpUrlConnection.getOutputStream());

            if (request != null) {
                request.write(getDataString(address).getBytes());
                request.flush();
                request.close();
            }

            if (httpUrlConnection != null) {
                respCode = httpUrlConnection.getResponseCode();
            }
            String response = null;
            if (respCode == 200) {
                InputStream responseStream = new BufferedInputStream(
                        httpUrlConnection.getInputStream());
                response = readString(responseStream);
            }
            if (request != null) {
                request.close();
                request = null;
            }
            if (httpUrlConnection != null) {
                httpUrlConnection.disconnect();
                httpUrlConnection = null;
            }
        } catch (Exception e) {

            Log.e(this.getClass().toString(), "Error in parsing IMPACT response: \n" + Log.getStackTraceString(e));
        }

    }

    private static String readString(InputStream responseStream) throws IOException {
        BufferedReader responseStreamReader = new BufferedReader(new InputStreamReader(
                responseStream));
        String line = "";
        StringBuilder stringBuilder = new StringBuilder();
        while ((line = responseStreamReader.readLine()) != null) {
            stringBuilder.append(line).append("\n");
        }
        responseStreamReader.close();
        return stringBuilder.toString();
    }
}
