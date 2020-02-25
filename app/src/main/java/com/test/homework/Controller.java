package com.test.homework;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.homework.Class.Define;
import com.test.homework.UI.DetailView;

import java.util.ArrayList;

public class Controller extends Handler{

    private static Controller instance = null;
    public static Controller getInstance() {
        if (instance == null) {
            instance = new Controller();
        }
        return instance;
    }

    public void checkFirstIn(Context context){
        SharedPreferences sp = context.getSharedPreferences(Define.APP_NAME, Context.MODE_PRIVATE);
        if (!sp.getBoolean(Define.APP_NAME, true)){
            Toast.makeText(context, Define.BACK_TO_APP, Toast.LENGTH_LONG).show();
        } else {
            sp.edit()
                    .putBoolean(Define.APP_NAME, false)
                    .apply();
        }
    }

    private RecyclerView.Adapter myAdapter = null;
    private ProgressDialog loading = null;
    public void updateData(Context context, RecyclerView.Adapter a) {
        myAdapter = a;
        loading = new ProgressDialog(context);
        loading.setTitle(Define.LOADING);
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.show();
        Data.getInstance().requestData(this);
    }


    @Override
    public void handleMessage(@NonNull Message msg) {
        super.handleMessage(msg);
        if (msg.what == Define.H_UPDATE){
            Controller.getInstance().updateUI();
        }
    }

    public int dataCountForCell() {
        ArrayList data = Data.getInstance().getData();
        if (data == null){
            return 0;
        } else {
            return data.size();
        }
    }

    public void detailPage(Context context,String startTime, String endTime, String temperature){
        Intent intent = new Intent(context, DetailView.class);
        intent.putExtra(Define.START, startTime);
        intent.putExtra(Define.END, endTime);
        intent.putExtra(Define.TEMP, temperature);
        context.startActivity(intent);
    }

    private void updateUI() {
        if (myAdapter != null) {
            loading.dismiss();
            loading = null;
            myAdapter.notifyDataSetChanged();
        }
    }
}
