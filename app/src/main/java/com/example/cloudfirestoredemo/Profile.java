package com.example.cloudfirestoredemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Profile extends AppCompatActivity {
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseFirestore db;
    TextView email;
    TextView name;
    TextView phnnum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        String uid = user.getUid();
        db = FirebaseFirestore.getInstance();
        email = findViewById(R.id.email);
        name = findViewById(R.id.name);
        phnnum = findViewById(R.id.phnnumber);

        db.collection("users").document(uid).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                Log.i("email", task.getResult().get("email").toString());
                email.setText(task.getResult().get("email").toString());
                name.setText(task.getResult().get("name").toString());
                phnnum.setText(task.getResult().get("phonenumber").toString());
            }
        });


    }
    public class UserModel{
        public String email;
        public String name;
        public String phonenumber;
        UserModel(String a,String b,String c){
            this.email = a;
            this.name = b;
            this.phonenumber = c;

        }
    }

    public void update(View view){
        String e = email.getText().toString();
        String n = name.getText().toString();
        String p = phnnum.getText().toString();
        UserModel um = new UserModel(e,n,p);
        db.collection("users").document(auth.getCurrentUser().getUid()).set(um);
    }

    public void delete(View view){
        db.collection("users").document(user.getUid()).delete();
        auth.getCurrentUser().delete();
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.logout){
            auth.signOut();
            Intent intent = new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
        else if(item.getItemId() == R.id.postpic){
            Intent intent = new Intent(this, Searchuser.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}
