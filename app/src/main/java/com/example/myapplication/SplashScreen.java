package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class SplashScreen extends AppCompatActivity {
public String CurrentUserId;
public FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        mAuth=FirebaseAuth.getInstance();
        LogoLauncher logoLauncher=new LogoLauncher();
        logoLauncher.start();
    }
    private class LogoLauncher extends Thread
    {
        public void run()
        {
            try
            {
                sleep(3000);
            }
            catch(InterruptedException e)
            {
                e.printStackTrace();
            }
            if(mAuth.getCurrentUser()!=null)
            {
                CurrentUserId=mAuth.getCurrentUser().getUid();
                FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
                DatabaseReference databaseReference=firebaseDatabase.getReference().child("Users");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists())
                        {
                            HashMap<String,Object> hashMap=(HashMap<String, Object>)dataSnapshot.getValue();
                            for(String key:hashMap.keySet())
                            {
                                Object data = hashMap.get(key);
                                HashMap<String, Object> userData = (HashMap<String, Object>) data;
                                String uid=(String)userData.get("id");
                                if(uid.equals(CurrentUserId))
                                {
                                    Intent intent=new Intent(SplashScreen.this,Add_Reciever.class);
                                    String email=(String)userData.get("email");
                                    String pass=(String)userData.get("pass");
                                    intent.putExtra("uid",uid);
                                    intent.putExtra("email",email);
                                    intent.putExtra("pass",pass);
                                    startActivity(intent);
                                    SplashScreen.this.finish();
                                }
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
            else
            {
                Intent intent=new Intent(SplashScreen.this,MainActivity.class);
                startActivity(intent);
                SplashScreen.this.finish();
            }


        }
    }
}