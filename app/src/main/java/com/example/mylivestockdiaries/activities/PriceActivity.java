package com.example.mylivestockdiaries.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylivestockdiaries.R;
import com.example.mylivestockdiaries.adapters.PricesAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class PriceActivity extends AppCompatActivity {
    private Toolbar toolbar;
    ArrayList<String> mData;
    private String Tag = "Price Activity";
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mlayoutManager;
    RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);
        Intent intent = getIntent();
        String [] stringArray = intent.getStringArrayExtra("string-array");
        toolbar=findViewById(R.id.priceToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Prices");

        mData = new ArrayList<>(Arrays.asList(stringArray));
        mRecyclerView = (RecyclerView)findViewById(R.id.pricesRecyclerView);
        mlayoutManager = new LinearLayoutManager(this);
        mAdapter = new PricesAdapter(mData);
        mRecyclerView.setLayoutManager(mlayoutManager);
        mRecyclerView.setAdapter(mAdapter);



    }
}