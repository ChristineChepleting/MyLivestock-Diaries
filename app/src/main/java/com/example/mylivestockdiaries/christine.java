package com.example.mylivestockdiaries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.example.mylivestockdiaries.activities.BreedingActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class christine extends AppCompatActivity {
    private TextInputEditText textInputDate, textInputselectcow;
    private Button button;
    private Toolbar toolbar;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    String current_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_christine);
        toolbar = findViewById(R.id.DryOfftoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Drying record");

        firebaseAuth=FirebaseAuth.getInstance();
        current_user_id=firebaseAuth.getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Drying").child(current_user_id);

        textInputDate = findViewById(R.id.dryoffDate);
        textInputselectcow = findViewById(R.id.selectCow);
        button=findViewById(R.id.dryingSavebtn);
        progressDialog=new ProgressDialog(this);
        textInputDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                setDate(textInputDate);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { verifyData();
            }
        });

    }

    private void verifyData() {
        String date=textInputDate.getText().toString();
        String selectcow=textInputselectcow.getText().toString();

        if (date.isEmpty()){
            Toast.makeText(this, "Please enter the date", Toast.LENGTH_SHORT).show();
        }

        else if (selectcow.isEmpty()){
            Toast.makeText(this, "Please select cow", Toast.LENGTH_SHORT).show();
        }

        else{
            progressDialog.setTitle("Adding Drying");
            progressDialog.setMessage("Please wait while we are adding drying");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            saveDataToDatabase(date,selectcow);
        }

    }

    private void saveDataToDatabase(String date, String selectcow) {
        HashMap hashMap=new HashMap();
        hashMap.put("date",date);
        hashMap.put("selectcow",selectcow);


        databaseReference.child(date).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull @NotNull Task task) {
                if (task.isSuccessful()){
                    Intent intent=new Intent(christine.this, BreedingActivity.class);
                    startActivity(intent);
                    progressDialog.dismiss();

                    finish();

                }
                else {
                    String error=task.getException().getMessage().toString();
                    Toast.makeText(christine.this, "Error Occurred"+error, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }
        });
    }

    private void setDate(TextInputEditText textInputDate) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                calendar.setMinDate(long minDate);
//                calendar.setMaxDate(long maxDate)
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
                textInputDate.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };
        new DatePickerDialog(christine.this, dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();



    }
}
