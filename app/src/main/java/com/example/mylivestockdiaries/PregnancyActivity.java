package com.example.mylivestockdiaries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mylivestockdiaries.activities.BreedingActivity;
import com.example.mylivestockdiaries.activities.CattleAdd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class PregnancyActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextInputEditText dateEdit, notesEdit;
    private AutoCompleteTextView autoCompleteTextViewselectcow, autoCompleteTextViewpgstatus;
    private Button buttonSaveButton;
    private String selectedStage;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private String currentUserId;
    private ProgressDialog progressDialog;
    private ArrayAdapter<String> adapterSelectcow, adapterpgstatus;
    String[] selectcow = {"C001","C002","C003","C004","C005","C006","C007"};
    String[] pgstatus = {"Positive", "Negative"};
    String title, key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pregnancy);
        toolbar = findViewById(R.id.pgToolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Pregnancy Record");

        progressDialog = new ProgressDialog(this);

        firebaseAuth = FirebaseAuth.getInstance();
        currentUserId = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Pregnancy details").child(currentUserId);

        autoCompleteTextViewselectcow = findViewById(R.id.selectcow);
        autoCompleteTextViewpgstatus = findViewById(R.id.status);
        dateEdit = findViewById(R.id.dateofpg);
        notesEdit = findViewById(R.id.Notes);
        buttonSaveButton = findViewById(R.id.pregnancybtn);

        adapterSelectcow = new ArrayAdapter<String>(PregnancyActivity.this, android.R.layout.simple_spinner_item, selectcow);
        adapterpgstatus = new ArrayAdapter<String>(PregnancyActivity.this, android.R.layout.simple_spinner_item, pgstatus);
        autoCompleteTextViewselectcow.setAdapter(adapterSelectcow);
        autoCompleteTextViewpgstatus.setAdapter(adapterpgstatus);

        buttonSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
        dateEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(dateEdit);
            }
        });
    }

    private void showDateDialog(final EditText date_in) {
        final Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yy-MM-dd");
                date_in.setText(simpleDateFormat.format(calendar.getTime())); }
        };

        new DatePickerDialog(PregnancyActivity.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    private void saveData() {
        String selectdate= dateEdit.getText().toString();
        String notes=notesEdit.getText().toString();
        String selectcow=autoCompleteTextViewselectcow.getText().toString();
        String pregnancystatus=autoCompleteTextViewpgstatus.getText().toString();
        if (selectcow.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please select cattle",Toast.LENGTH_SHORT).show();
        }
        else if (selectdate.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter date",Toast.LENGTH_SHORT).show();
        }
        else if (notes.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter notes ",Toast.LENGTH_SHORT).show();
        }
        else if (pregnancystatus.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter pregnancy status",Toast.LENGTH_SHORT).show();
        }
        else{
            progressDialog.setTitle("Adding pregnancy Records");
            progressDialog.setMessage("Please wait while we are adding animal records...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
      enterDataToDatabase(selectcow,selectdate,notes,pregnancystatus);
        }
}

    private void enterDataToDatabase(String selectcow, String selectdate, String notes, String pregnancystatus) {
        HashMap hashmap =new  HashMap();
        hashmap.put("selectcow",selectcow);
        hashmap.put("selectdate",selectdate);
        hashmap.put("notes",notes);
        hashmap.put("pregnancy status",pregnancystatus);
        databaseReference.child(selectdate).updateChildren(hashmap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Record Added Successfully",Toast.LENGTH_SHORT).show();
                    Intent intentAdd= new Intent(PregnancyActivity.this, BreedingActivity.class);
                    startActivity(intentAdd);
                    finish();
                    progressDialog.dismiss();

                }
                else{
                    String error= task.getException().getMessage().toString();
                    Toast.makeText(getApplicationContext(),"Error occured:"+error,Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }

            }
        });

    }}


