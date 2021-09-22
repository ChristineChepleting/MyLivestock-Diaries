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
import android.widget.CalendarView;
import android.widget.DatePicker;
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

public class Treatment extends AppCompatActivity {
    private TextInputEditText textInputDate, textInputTag, textInputTreatment, textInputDiagnostic, textInputCost;
    private Button button;
    private Toolbar toolbar;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    String current_user_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_treatment);
        toolbar = findViewById(R.id.Treatmenttoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Treatment");
        //firebase initialization
        firebaseAuth=FirebaseAuth.getInstance();
        current_user_id=firebaseAuth.getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Treatments").child(current_user_id);

        textInputDate = findViewById(R.id.treatmentDate);
        textInputTag = findViewById(R.id.treatmentTag);
        textInputTreatment = findViewById(R.id.treatmentTreat);
        textInputDiagnostic = findViewById(R.id.treatmentDiagnosis);
        textInputCost = findViewById(R.id.treatmentCost);
        button=findViewById(R.id.treatmentSavebtn);
          progressDialog=new ProgressDialog(this);
        textInputDate.setOnClickListener(new View.OnClickListener() {
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
        String tag=textInputTag.getText().toString();
        String treatment=textInputTreatment.getText().toString();
        String diagnosis=textInputDiagnostic.getText().toString();
        String cost=textInputCost.getText().toString();

        if (date.isEmpty()){
            Toast.makeText(this, "Please enter the date", Toast.LENGTH_SHORT).show();
        }

        else if (tag.isEmpty()){
            Toast.makeText(this, "Please enter tag number", Toast.LENGTH_SHORT).show();
        }

        else if (treatment.isEmpty()){
            Toast.makeText(this, "Please enter the treatment", Toast.LENGTH_SHORT).show();
        }

        else if (diagnosis.isEmpty()){
            Toast.makeText(this, "Please enter diagnosis", Toast.LENGTH_SHORT).show();
        }

        if (cost.isEmpty()){
            Toast.makeText(this, "Please enter the cost", Toast.LENGTH_SHORT).show();
        }
        else{
            progressDialog.setTitle("Adding Treatment");
            progressDialog.setMessage("Please wait while we are adding treatment");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            saveDataToDatabase(date,tag,treatment,diagnosis,cost);
        }



    }

    private void saveDataToDatabase(String date, String tag, String treatment, String diagnosis, String cost) {
        HashMap hashMap=new HashMap();
        hashMap.put("date",date);
        hashMap.put("tag",tag);
        hashMap.put("treatment",treatment);
        hashMap.put("diagnosis",diagnosis);
        hashMap.put("cost",cost);

        databaseReference.child(tag).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull @NotNull Task task) {
                if (task.isSuccessful()){
                    Intent intent=new Intent(Treatment.this, MainActivity.class);
                    startActivity(intent);
                    progressDialog.dismiss();

                    finish();

                }
                else {
                    String error=task.getException().getMessage().toString();
                    Toast.makeText(Treatment.this, "Error Occurred"+error, Toast.LENGTH_SHORT).show();
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
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yy-MM-dd");
                textInputDate.setText(simpleDateFormat.format(calendar.getTime()));
            }

        };


        new DatePickerDialog(Treatment.this, dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();


    }
}