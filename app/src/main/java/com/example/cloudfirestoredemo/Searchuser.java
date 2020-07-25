package com.example.cloudfirestoredemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Searchuser extends AppCompatActivity {

    TextView emailplaintext;
    TextView emailtextview;
    TextView nametextview;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchuser);
        emailplaintext = findViewById(R.id.email);
        emailtextview = findViewById(R.id.emailtextview);
        nametextview = findViewById(R.id.nametextview);
        emailtextview.setVisibility(View.INVISIBLE);
        nametextview.setVisibility(View.INVISIBLE);
        db= FirebaseFirestore.getInstance();
    }

    public void search(View view){
        final String e = emailplaintext.getText().toString();
        db.collection("users")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.i("Result", document.getData().toString());
                                if (e.equals(document.get("email").toString())) {
                                    emailtextview.setText(document.get("email").toString());
                                    nametextview.setText(document.get("name").toString());
                                    emailtextview.setVisibility(View.VISIBLE);
                                    nametextview.setVisibility(View.VISIBLE);
                                    break;
                                }
                            }
                        } else {

                        }
                    }


                });

    }
}
