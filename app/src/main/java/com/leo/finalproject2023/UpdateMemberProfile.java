package com.leo.finalproject2023;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class UpdateMemberProfile<DatabaseReference, FirebaseDatabase> extends AppCompatActivity {
    EditText editname,editemail,editphonenumber,editpassword;
    Button button;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference;
    DocumentReference documentReference;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_member_profile);

        editname = findViewById(R.id.et_name_up);
        editemail= findViewById(R.id.et_email_up);
        editphonenumber = findViewById(R.id.et_Phonenumber_up);
        editpassword = findViewById(R.id.et_Password_up);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                updateProfile();
            }
        }
        );
    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user= FirebaseAuth.getInstance().getCurrentUser();
        String currentuid = user.getUid();
        documentReference.collection("user").document(currentuid);

        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.getResult().exists()){
                            String nameResult = task.getResult().getString("Name");
                            String emailResult = task.getResult().getString("Email");
                            String phonenumberResult = task.getResult().getString("Phone Number");
                            String passwordResult = task.getResult().getString("Password");


                            editname.setText(nameResult);
                            editemail.setText(emailResult);
                            editpassword.setText(passwordResult);
                            editphonenumber.setText(phonenumberResult);
                        } else {
                            Toast.makeText(updateProfile.this, "No profile",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    private void updateProfile() {
        String name = editname.getText().toString();
        String email = editemail.getText().toString();
        String password = editpassword.getText().toString();
        String phonenumber = editphonenumber.getText().toString();

        final DocumentReference sDoc = db.collection("user").document(currentuid);
    }
}