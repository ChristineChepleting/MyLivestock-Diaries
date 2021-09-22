package com.example.mylivestockdiaries;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Button;


public class AdminDashboard extends AppCompatActivity {
    private Button farmersbtn,feedsbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
         farmersbtn = findViewById(R.id.farmersbtn);
         feedsbtn = findViewById(R.id.Addfeedbtn);

        farmersbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent farmers = new Intent(AdminDashboard.this, MainActivity.class);
                startActivity(farmers);
            }
        });
        feedsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent feed = new Intent(AdminDashboard.this, ProteinActivity.class);
                startActivity(feed);
            }
        });
    }
}