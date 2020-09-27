package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.myapplication.Model.UserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
    }
    private boolean isValidEmailId(String email){

        return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches();
    }
    public void get_started(View view) {
        final EditText email,pass;
        final ProgressBar progressBar;
        progressBar=findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        email=findViewById(R.id.email);
        pass=findViewById(R.id.password);
        if(TextUtils.isEmpty(email.getText()))
        {
            email.setError("Enter the email");
            progressBar.setVisibility(View.GONE);
        }
        else if(!(isValidEmailId(email.getText().toString())))
        {
            email.setError("Please enter the valid email");
            progressBar.setVisibility(View.GONE);
        }
        else if(TextUtils.isEmpty(pass.getText()))
        {
            pass.setError("Enter the password");
            progressBar.setVisibility(View.GONE);
        }
        else
        {
            final String email1,pass1;
            email1=email.getText().toString();
            pass1=pass.getText().toString();
            mAuth.createUserWithEmailAndPassword(email1,pass1)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                userId = mAuth.getCurrentUser().getUid();
                                UserInfo userInfo = new UserInfo(
                                        email1,
                                        pass1,
                                        userId
                                );
                                Task<Void> firebaseDatabase=FirebaseDatabase.getInstance().getReference("Users").child(userId)
                                        .setValue(userInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                             progressBar.setVisibility(View.GONE);
                                             Intent intent=new Intent(MainActivity.this,Add_Reciever.class);
                                             intent.putExtra("email",email1);
                                             startActivity(intent);
                                            }
                                        });
                            } else {
                                // If sign in fails, display a message to the user.

                            }
                        }

                    });
    }
}
}