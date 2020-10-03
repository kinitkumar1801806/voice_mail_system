package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
    private int MULTIPLE_PERMISSION = 1;
    String[] permission={Manifest.permission.RECORD_AUDIO};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO )== PackageManager.PERMISSION_GRANTED)
        {

        }
        else {
            requestPhonePermission();
        }
    }
    private void requestPhonePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.RECORD_AUDIO)){
            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed because of this and that")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    permission, MULTIPLE_PERMISSION);
                        }

                    })
                    .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create().show();
        } else {
            ActivityCompat.requestPermissions(this,
                    permission,MULTIPLE_PERMISSION);
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MULTIPLE_PERMISSION)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {


            } else {
                Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }
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
                                             intent.putExtra("uid",userId);
                                             intent.putExtra("email",email1);
                                             intent.putExtra("pass",pass1);
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