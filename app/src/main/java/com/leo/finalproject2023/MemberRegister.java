package com.leo.finalproject2023;

import static com.leo.finalproject2023.R.id.reName;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MemberRegister extends AppCompatActivity {
    EditText reName, reEmail, rePasse, rePhone;
    Button btnRegister;
    TextView reLogin;
    ProgressBar rePBar;
    FirebaseAuth fAuth;
    FirebaseFirestore firestore;
    String membreID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_register);
        //Values used
        reName = findViewById(R.id.reName);
        reEmail = findViewById(R.id.reEmail);
        rePasse = findViewById(R.id.rePass);
        rePhone = findViewById(R.id.rePhone);
        btnRegister = findViewById(R.id.btnRegister);
        rePBar = findViewById(R.id.rePBar);
        reLogin = findViewById(R.id.reLogin);
        fAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = reEmail.getText().toString().trim();
                String passe = rePasse.getText().toString().trim();
                String nom = reName.getText().toString();
                String phone = rePhone.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    reEmail.setError("Email Address is required");
                }
                if (TextUtils.isEmpty(passe)) {
                    rePasse.setError("Password is required");
                }
                if (passe.length() < 6) {
                    rePasse.setError("Minimum 6 characters are needed for the password");
                }
                rePBar.setVisibility(view.VISIBLE);
                //Used to save the date values
                fAuth.createUserWithEmailAndPassword(email, passe).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(MemberRegister.this, "Compte créé", Toast.LENGTH_SHORT).show();
                            //Database of values
                            membreID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = firestore.collection("membres").document(membreID);
                            Map<String, Object> member = new HashMap<>();
                            member.put("nom", nom);
                            member.put("email", reEmail);
                            member.put("phone", phone);
                            documentReference.set(member).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d("TAG", "Profile is created for the members" + membreID);
                                }
                            });
                            startActivity(new Intent(getApplicationContext(), MemberProfile.class));
                        } else {
                            Toast.makeText(MemberRegister.this, "An error has occurred" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            rePBar.setVisibility(View.GONE);
                        }
                    }
                });
            //Save
            }
        });
        reLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MemberLogin.class));
            }
        });
    }

    ;
}
