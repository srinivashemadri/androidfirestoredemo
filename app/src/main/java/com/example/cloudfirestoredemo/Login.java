package com.example.cloudfirestoredemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    TextView email;
    TextView pwd;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (TextView) findViewById(R.id.email);
        pwd = (TextView) findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();
    }

    public void login(View view){

        String e = email.getText().toString();
        String p = pwd.getText().toString();

        auth.signInWithEmailAndPassword(e, p)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            if(task.getResult().getUser().isEmailVerified()){
                                Toast.makeText(getApplicationContext(), "Login Success", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(getApplicationContext(),Profile.class);
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(Login.this, "Please verify your email, before logging in", Toast.LENGTH_SHORT).show();
                            }

                        }
                        else{
                            Toast.makeText(getApplicationContext(), "Login Fail", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public void fgtpwd(View view){


        //Normal ga aithe.. inkoka intent create cheyali ee button nokkagane..
        //akkada email enter cheyamani adugtam
        //nen adhi cheyatle.. hardcoded email teskuntuna ok na

        //haaa anthe

        auth.sendPasswordResetEmail("srinivashemadri2000@gmail.com").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(Login.this, "Password reset link has been sent to your mail", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(Login.this, "Some failure occured", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
