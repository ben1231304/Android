package com.test.homework.UI;


import android.os.Bundle;

import com.test.homework.R;
import com.test.homework.Controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class MainView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("MyApplication");
        setSupportActionBar(toolbar);

        initRecyclerView();
        Controller.getInstance().checkFirstIn(this);
        Controller.getInstance().updateData(this, adapter);
    }

    private RecyclerAdapter adapter = null;
    private RecyclerAdapter initRecyclerView(){
        if (adapter == null){
            RecyclerView rv = this.findViewById(R.id.recyclerView);
            adapter = new RecyclerAdapter(this);
            rv.setLayoutManager(new LinearLayoutManager(this));
            rv.setAdapter(adapter);
        }
        return adapter;
    }
}
