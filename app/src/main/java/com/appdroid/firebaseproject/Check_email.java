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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Check_email extends AppCompatActivity {
    EditText otpEd;
    Button submit;

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    String verificationId, docID;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_email);

        verificationId = getIntent().getStringExtra("OTPID");
        docID = getIntent().getStringExtra("docID");

        otpEd = findViewById(R.id.verifyemail);
        submit = findViewById(R.id.submit);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String OTPValue = otpEd.getText().toString();
                Log.w("sssssssssssssss", "Edit Text Value :"+OTPValue);
                verifyOTP(OTPValue);
            }
        });
    }

    private void verifyOTP(String otpValue) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otpValue);
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            //Log.d(sssssssssssssss, "signInWithCredential:success");

                            //FirebaseUser user = task.getResult().getUser();
                            // Update UI
                            Toast.makeText(Check_email.this, "OTP Verified...", Toast.LENGTH_SHORT).show();
                           Intent i = new Intent(Check_email.this, ResetPassword.class);
                            i.putExtra("docID",docID);
                            startActivity(i);
                            finish();
                        } else {
                            // Sign in failed, display a message and update the UI
                            //Log.w(sssssssssssssss, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                // The verification code entered was invalid
                            }
                        }
                    }
                });
    }
}