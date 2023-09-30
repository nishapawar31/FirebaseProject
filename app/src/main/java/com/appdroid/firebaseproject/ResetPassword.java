package com.appdroid.firebaseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class ResetPassword extends AppCompatActivity {

    EditText newPass,confPass;

    Button submitPass;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String  docID;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        newPass = findViewById(R.id.newPass);
        confPass = findViewById(R.id.confPass);
        submitPass = findViewById(R.id.submitPass);
        docID = getIntent().getStringExtra("docID");


        submitPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass = newPass.getText().toString();
                String newpassword = confPass.getText().toString();
                if (pass.equals(newpassword)) {
                    Map<String, Object> Login = new HashMap<>();
                    Login.put("password", newpassword);
                    db.collection("Login")
                            .document(docID)
                            .update("password",newpassword)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(ResetPassword.this, "Your Data has been Updated", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ResetPassword.this, "OOPS..Updation Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                }else{
                    Toast.makeText(ResetPassword.this, "OOPS...", Toast.LENGTH_SHORT).show();
                }


            }

        });
    }
    
}