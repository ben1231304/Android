package com.test.homework.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.test.homework.Class.WeatherData;
import com.test.homework.Controller;
import com.test.homework.Data;
import com.test.homework.R;
import com.test.homework.Class.Define;

public class RecyclerAdapter extends RecyclerView.Adapter {

    private Context myContext = null;
    public RecyclerAdapter(Context c){
        myContext = c;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case Define.TYPE_TEXT:
                holder = new textHolder(LayoutInflater.from(myContext).inflate(R.layout.textcell, parent, false));
                break;
            case Define.TYPE_IMG:
                holder = new imgHolder(LayoutInflater.from(myContext).inflate(R.layout.imgcell, parent, false));
                break;
        }
        return holder;
    }

    @Override
    public int getItemViewType(int position) {
        /* 偶數位置以圖片分開 */
        if ((position + 1) % 2 == 0){
            return Define.TYPE_IMG;
        } else {
            return Define.TYPE_TEXT;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if ((position + 1) % 2 != 0) {
            textHolder myholder = (textHolder) holder;
            WeatherData data = Data.getInstance().getData().get(position);
            myholder.startTime.setText(data.startTime);
            myholder.endTime.setText(data.endTime);
            myholder.temperature.setText(data.temperature);
        }
    }

    @Override
    public int getItemCount() {
        return Controller.getInstance().dataCountForCell();
    }

    private class textHolder extends RecyclerView.ViewHolder{
        public TextView startTime = null;
        public TextView endTime = null;
        public TextView temperature = null;
        public textHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(onclick);
            startTime = itemView.findViewById(R.id.startTime);
            endTime = itemView.findViewById(R.id.endTime);
            temperature = itemView.findViewById(R.id.temperature);
        }

        private View.OnClickListener onclick = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Controller.getInstance().detailPage(myContext,
                        startTime.getText().toString(),
                        endTime.getText().toString(),
                        temperature.getText().toString());
            }
        };
    }

    private class imgHolder extends RecyclerView.ViewHolder{
        public ImageView img = null;
        public imgHolder(@NonNull View itemView) {
            super(itemView);
            img =  itemView.findViewById(R.id.imgView);

        }
    }
}
