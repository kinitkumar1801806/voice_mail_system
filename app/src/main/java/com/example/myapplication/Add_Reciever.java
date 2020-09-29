package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.myapplication.Adapter.GroupAdapter;

public class Add_Reciever extends AppCompatActivity {

public String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__reciever);
        Intent intent=getIntent();
        email=intent.getStringExtra("email");

    RecyclerView group=(RecyclerView)findViewById(R.id.recycler_view);
        group.setLayoutManager(new LinearLayoutManager(this));
        group.setAdapter(new GroupAdapter());
}}