package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class Add_Reciever extends AppCompatActivity {

public String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__reciever);
        Intent intent=getIntent();
        email=intent.getStringExtra("email");
    }
}