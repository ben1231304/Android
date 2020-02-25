package com.test.homework.UI;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.test.homework.Class.Define;
import com.test.homework.R;

public class DetailView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);
        init();
    }

    private void init(){
        Intent intent = this.getIntent();
        TextView startTime = findViewById(R.id.startTime);
        TextView endTime = findViewById(R.id.endTime);
        TextView temperature = findViewById(R.id.temperature);

        startTime.setText(intent.getStringExtra(Define.START));
        endTime.setText(intent.getStringExtra(Define.END));
        temperature.setText(intent.getStringExtra(Define.TEMP));
    }
}
