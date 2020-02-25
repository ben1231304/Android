package com.test.homework;


import android.os.Handler;

import com.test.homework.Class.Define;
import com.test.homework.Class.WeatherData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class Data {

    private static Data instance = null;
    public static Data getInstance() {
        if (instance == null) {
            instance = new Data();
        }
        return instance;
    }

    private String dataUrl = Define.URL;
    private ArrayList<WeatherData> dataArray = null;
    private ArrayList<WeatherData> arrayToShow = null;
    private android.os.Handler handler = null;

    public void requestData(Handler h) {
        handler = h;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    URL url = new URL(dataUrl);
                    URLConnection conn = url.openConnection();

                    HttpURLConnection httpConn = (HttpURLConnection) conn;
                    httpConn.setRequestMethod("GET");
                    httpConn.connect();

                    if (httpConn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                        InputStream inputStream = httpConn.getInputStream();
                        if (inputStream != null) {
                            parseData(inputStream);
                        }
                    }
                } catch (MalformedURLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void parseData(InputStream stream) {
        clearArray();
        String result = "";

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
            String line = "";
            while ((line = bufferedReader.readLine()) != null){
                result += line;
            }
            stream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (result.length() > 0) {
            try {
                JSONArray jsonarray = new JSONObject(result).getJSONObject("records")
                        .getJSONArray("location")
                        .getJSONObject(0)
                        .getJSONArray("weatherElement")
                        .getJSONObject(0)
                        .getJSONArray("time");

                for (int i = 0; i < jsonarray.length(); i++) {
                    WeatherData data = new WeatherData();
                    data.startTime = jsonarray.getJSONObject(i).get("startTime").toString();
                    data.endTime = jsonarray.getJSONObject(i).get("endTime").toString();
                    JSONObject obj = jsonarray.getJSONObject(i).getJSONObject("parameter");
                    data.temperature = obj.get("parameterName").toString() + obj.get("parameterUnit").toString();
                    dataArray.add(data);
                }
                makeArrayToShow();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void makeArrayToShow(){
        /* 偶數位置以圖片分開 */
        int size =  dataArray.size() * 2 - 1;
        int count = 0;
        for (int i = 1; i <= size; i++) {
            if (i % 2 != 0) {
                arrayToShow.add(dataArray.get(count));
                count++;
            } else {
                arrayToShow.add(null);
            }
        }
        handler.sendEmptyMessage(Define.H_UPDATE);
    }

    private void clearArray(){
        if (dataArray == null){
            dataArray = new ArrayList<>();
            arrayToShow = new ArrayList<>();
        } else {
            dataArray.clear();
            arrayToShow.clear();
        }
    }

    public ArrayList<WeatherData> getData(){
        return arrayToShow;
    }
}
