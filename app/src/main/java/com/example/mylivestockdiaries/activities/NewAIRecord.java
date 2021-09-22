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
import android.widget.Toast;

import com.example.mylivestockdiaries.Artificialinsemination;
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

public class NewAIRecord extends AppCompatActivity {
    private Toolbar toolbar;
    private TextInputEditText textInputTagno, textInputdateofbreeding, textInputtimeofbreeding,textInputNotes,textInputAIType;
    private Button button;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    String current_user_id;
    String title,key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_airecord);
        toolbar=findViewById(R.id.AIToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add new AI record");
        //firebase initialization
        firebaseAuth=FirebaseAuth.getInstance();
        current_user_id=firebaseAuth.getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("AIrecords").child(current_user_id);
        textInputTagno = findViewById(R.id.cattleAITag);
        textInputdateofbreeding = findViewById(R.id.DateOfBreeding);
        textInputtimeofbreeding = findViewById(R.id.time);
        textInputAIType = findViewById(R.id.cattleAIType);
        textInputNotes= findViewById(R.id.Notes);
        button=findViewById(R.id.AISavebtn);
        progressDialog=new ProgressDialog(this);
        textInputdateofbreeding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {setDate(textInputdateofbreeding);}
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { verifyData();}
        });
    }
    private void verifyData(){
        String tag=textInputTagno.getText().toString();
        String dateofbreeding=textInputdateofbreeding.getText().toString();
        String timeofbreeding=textInputtimeofbreeding .getText().toString();
        String AItype= textInputAIType .getText().toString();
        String notes=textInputNotes.getText().toString();
        if (tag.isEmpty()){
            Toast.makeText(this, "Please enter the tagnumber", Toast.LENGTH_SHORT).show();
        }

        else if (dateofbreeding.isEmpty()){
            Toast.makeText(this, "Please enter date", Toast.LENGTH_SHORT).show();
        }

        else if (timeofbreeding.isEmpty()){
            Toast.makeText(this, "Please enter time", Toast.LENGTH_SHORT).show();
        }

        else if (AItype.isEmpty()){
            Toast.makeText(this, "Please enter AItype", Toast.LENGTH_SHORT).show();
        }

        else{
            progressDialog.setTitle("Adding new AI Record");
            progressDialog.setMessage("Please wait while we are adding record");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            saveDataToDatabase(tag,dateofbreeding,timeofbreeding,AItype,notes);
        }

    }
    private void saveDataToDatabase(String tag, String dateofbreeding, String timeofbreeding, String AIType, String notes) {
        HashMap hashMap=new HashMap();
        hashMap.put("tag",tag);
        hashMap.put("datefbreeding",dateofbreeding);
        hashMap.put("timeofbreeding",timeofbreeding);
        hashMap.put("AIType",AIType);
        hashMap.put("notes",notes);

        databaseReference.child(tag).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull @NotNull Task task) {
                if (task.isSuccessful()){
                    Intent intent=new Intent(NewAIRecord.this, Artificialinsemination.class);
                    startActivity(intent);
                    progressDialog.dismiss();

                    finish();

                }
                else {
                    String error=task.getException().getMessage().toString();
                    Toast.makeText(NewAIRecord.this, "Error Occurred"+error, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }
        });
    }
    private void setDate(TextInputEditText textInputdateofbreeding){
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
                textInputdateofbreeding.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };
        new DatePickerDialog(NewAIRecord.this, dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();

    }


    }
