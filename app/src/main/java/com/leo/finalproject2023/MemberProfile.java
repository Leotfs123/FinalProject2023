package com.leo.finalproject2023;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MemberProfile extends AppCompatActivity {

Button EditInfobtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_profile);

         EditInfobtn = (Button)findViewById(R.id.EditInfobtn);

    }
}