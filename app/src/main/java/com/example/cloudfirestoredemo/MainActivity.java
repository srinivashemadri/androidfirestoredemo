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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    TextView email;
    TextView pwd;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        email = (TextView) findViewById(R.id.email);
        pwd = (TextView) findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        /*user = auth.getCurrentUser();
        if(user != null){
            Intent intent = new Intent(this, Profile.class);
            startActivity(intent);
        }*/


    }

    public class User{
        public String email;
        public String name;
        public String phonenumber;

        User(String e) {
            this.email = e;
            this.name ="";
            this.phonenumber="";
        }
    }

    public void signup(View view){
        final String em = email.getText().toString();
        String pw = pwd.getText().toString();
        Log.i("Log", em+"  "+pw);
        if(em.length()>0 && pw.length()>6){
            auth.createUserWithEmailAndPassword(em, pw)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                user = auth.getCurrentUser();
                                Toast.makeText(getApplicationContext(),"Signup success", Toast.LENGTH_LONG).show();
                                User us = new User(em);
                                db.collection("users").document(user.getUid()).set(us);
                                Intent intent = new Intent(getApplicationContext(), Profile.class);
                                startActivity(intent);
                                user.sendEmailVerification();

                                 //idhokat add chey direct mail vachestad signup avvagane


                            } else {
                                Toast.makeText(getApplicationContext(),"Signup failed", Toast.LENGTH_LONG).show();

                            }
                        }
                    });
        }
        else{
            Toast.makeText(this, "Email and password should be valid", Toast.LENGTH_SHORT).show();
        }

    }

    public void login(View view){
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
    }
}
