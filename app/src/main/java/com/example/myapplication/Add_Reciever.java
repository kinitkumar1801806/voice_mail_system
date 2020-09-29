package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapplication.Model.AddGroup;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

public class Add_Reciever extends AppCompatActivity {

public String uid;
public String Group_Name;
RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__reciever);
        Intent intent=getIntent();
        uid=intent.getStringExtra("uid");
        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void new_group_btn(View view) {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialogue_box, null);
        final EditText GroupName = (EditText) dialogView.findViewById(R.id.edit_txt);
        Button Cancel = (Button) dialogView.findViewById(R.id.btn_cancel);
        Button Save = (Button) dialogView.findViewById(R.id.btn_save);
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(GroupName.getText()))
                {
                    Toast.makeText(Add_Reciever.this,"Please enter the name of group",Toast.LENGTH_SHORT).show();
                }
                else
                {
                   Group_Name=GroupName.getText().toString();
                   MakeGroup();
                }
                dialogBuilder.dismiss();

            }
        });
        Cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBuilder.dismiss();
            }
        });
    }
    public void MakeGroup()
    {
        AddGroup addGroup=new AddGroup(
                Group_Name
        );
        Task<Void> firebaseDatabase= FirebaseDatabase.getInstance().getReference("Groups").child(uid)
                .setValue(addGroup).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                    }
                });
    }
}