package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Adapter.GroupAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FormGroup extends AppCompatActivity {
    public String uid;
    public String email,Group_Name;
    RecyclerView recyclerView;
    ProgressDialog progressDialog;
    ArrayList<String> Email_List;
    GroupAdapter groupAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_group);
        Intent intent=getIntent();
        uid=intent.getStringExtra("uid");
        Group_Name=intent.getStringExtra("group_name");
        recyclerView=findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        Email_List=new ArrayList<>();
        progressDialog =new ProgressDialog(this);
        progressDialog.show();
        progressDialog.setContentView(R.layout.progress_dialogue);
        progressDialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        retrieveData();
    }
    public void add_email_btn(View view) {
        final AlertDialog dialogBuilder = new AlertDialog.Builder(this).create();
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.add_emails, null);
        final EditText Email = (EditText) dialogView.findViewById(R.id.edit_txt);
        Button Cancel = (Button) dialogView.findViewById(R.id.btn_cancel);
        Button Save = (Button) dialogView.findViewById(R.id.btn_save);
        dialogBuilder.setView(dialogView);
        dialogBuilder.show();
        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(Email.getText()))
                {
                    Toast.makeText(FormGroup.this,"Please enter the name of group",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    email=Email.getText().toString();
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
        DatabaseReference firebaseDatabase= FirebaseDatabase.getInstance().getReference("GroupsDetails").child(uid).child(Group_Name);
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("email",email);
        firebaseDatabase.push().setValue(hashMap);
        FirebaseDatabase firebaseDatabase1=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase1.getReference().child("GroupsDetails").child(uid).child(Group_Name);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    Email_List.clear();
                    HashMap<String,Object> hashMap=(HashMap<String, Object>)dataSnapshot.getValue();
                    for(String key:hashMap.keySet()) {
                        Object data = hashMap.get(key);
                        HashMap<String, Object> userData = (HashMap<String, Object>) data;
                        String email_address=(String)userData.get("email");
                        Email_List.add(email_address);
                    }
                    groupAdapter.notifyItemRangeChanged(0,Email_List.size());
                    groupAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void retrieveData()
    {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference databaseReference=firebaseDatabase.getReference().child("GroupsDetails").child(uid).child(Group_Name);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                {
                    HashMap<String,Object> hashMap=(HashMap<String, Object>)dataSnapshot.getValue();
                    for(String key:hashMap.keySet()) {
                        Object data = hashMap.get(key);
                        HashMap<String, Object> userData = (HashMap<String, Object>) data;
                        String email_address=(String)userData.get("email");
                        Email_List.add(email_address);
                        initRecyclerView();
                    }
                    TextView textView=findViewById(R.id.text_view);
                    textView.setVisibility(View.GONE);
                }
                else
                {
                    TextView textView=findViewById(R.id.text_view);
                    textView.setVisibility(View.VISIBLE);
                    progressDialog.dismiss();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public void initRecyclerView()
    {
        groupAdapter =new GroupAdapter(FormGroup.this,Email_List);
        recyclerView.setAdapter(groupAdapter);
        progressDialog.dismiss();
        groupAdapter.setOnItemClickListener(new GroupAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position){
            }

            @Override
            public void removeItem(int position) {
                final int pos=position;
                final String email_address=Email_List.get(position);
                final AlertDialog dialogBuilder = new AlertDialog.Builder(FormGroup.this).create();
                LayoutInflater inflater = FormGroup.this.getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.delete_emails, null);
                Button No = (Button) dialogView.findViewById(R.id.btn_cancel);
                Button Yes = (Button) dialogView.findViewById(R.id.btn_save);
                dialogBuilder.setView(dialogView);
                dialogBuilder.show();
                Yes.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                        final DatabaseReference databaseReference=firebaseDatabase.getReference().child("GroupsDetails").child(uid).child(Group_Name);
                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.exists())
                                {
                                    HashMap<String,Object> hashMap=(HashMap<String, Object>)dataSnapshot.getValue();
                                    for(String key:hashMap.keySet()) {
                                        Object data = hashMap.get(key);
                                        HashMap<String, Object> userData = (HashMap<String, Object>) data;
                                        String email_name=(String)userData.get("email");
                                        if(email_name.equals(email_address))
                                        {
                                            databaseReference.child(key).removeValue();
                                        }
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                        Email_List.remove(pos);
                        groupAdapter.notifyItemRemoved(pos);
                    }
                });
                No.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialogBuilder.dismiss();
                    }
                });
            }

        });
    }
}