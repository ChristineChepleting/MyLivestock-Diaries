package com.example.mylivestockdiaries.activities;

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
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.mylivestockdiaries.MainActivity;
import com.example.mylivestockdiaries.R;
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

public class Vaccine extends AppCompatActivity {
    private TextInputEditText textInputDate, textInputTag, textInputVaccine, textInputCost;
    private Button button;
    private Toolbar toolbar;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    String current_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vaccine);
        toolbar = findViewById(R.id.VaccineToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Vaccination");
        //firebase initialization
        firebaseAuth = FirebaseAuth.getInstance();
        current_user_id = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Vaccines").child(current_user_id);
        textInputDate = findViewById(R.id.vaccineDate);
        textInputTag = findViewById(R.id.vaccineTag);
        textInputVaccine = findViewById(R.id.selectVaccine);
        textInputCost = findViewById(R.id.VaccineCost);
        button = findViewById(R.id.vaccineSavebtn);
        progressDialog = new ProgressDialog(this);
        textInputDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setDate(textInputDate);
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyData();
            }
        });
    }

    private void verifyData() {
        String date = textInputDate.getText().toString();
        String tag = textInputTag.getText().toString();
        String vaccine = textInputVaccine.getText().toString();
        String cost = textInputCost.getText().toString();

        if (date.isEmpty()) {
            Toast.makeText(this, "Please enter the date", Toast.LENGTH_SHORT).show();
        }
        if (tag.isEmpty()) {
            Toast.makeText(this, "Please enter the tag number", Toast.LENGTH_SHORT).show();
        }
        if (vaccine.isEmpty()) {
            Toast.makeText(this, "Please enter the vaccine", Toast.LENGTH_SHORT).show();
        }
        if (cost.isEmpty()) {
            Toast.makeText(this, "Please enter the vaccine cost", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setTitle("Adding Vaccine");
            progressDialog.setMessage("Please wait while we are adding vaccine");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            saveDataToDatabase(date, tag, vaccine, cost);
        }

    }

    private void saveDataToDatabase(String date, String tag, String vaccine, String cost) {
        HashMap hashMap = new HashMap();
        hashMap.put("date", date);
        hashMap.put("tag", tag);
        hashMap.put("vaccine", vaccine);
        hashMap.put("cost", cost);

        databaseReference.child(tag).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull @NotNull Task task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(Vaccine.this, MainActivity.class);
                    startActivity(intent);
                    progressDialog.dismiss();

                    finish();
                } else {
                    String error = task.getException().getMessage().toString();
                    Toast.makeText(Vaccine.this, "Error Occurred" + error, Toast.LENGTH_SHORT).show();
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
        new DatePickerDialog(Vaccine.this, dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();


    }

}