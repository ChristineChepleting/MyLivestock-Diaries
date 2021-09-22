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

import com.example.mylivestockdiaries.MainActivity;
import com.example.mylivestockdiaries.R;
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

public class ExpenseActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextInputEditText textInputDate, textInputExpenseName, textInputHowMuch, textInputReceipt,textInputNotes;
    private Button button;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    String current_user_id;
    String title,key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        title=getIntent().getExtras().get("title").toString();
        key=getIntent().getExtras().get("key").toString();
        toolbar=findViewById(R.id.ExpenseToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);


        //firebase initialization
        firebaseAuth = FirebaseAuth.getInstance();
        current_user_id = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Expense").child(current_user_id);

        textInputDate = findViewById(R.id.ExpenseDate);
        textInputExpenseName= findViewById(R.id.ExpenseName);
        textInputHowMuch= findViewById(R.id.HowMuch);
        textInputReceipt = findViewById(R.id.ReceiptNumber);
        textInputNotes=findViewById(R.id.Notes);
        button = findViewById(R.id.ExpenseSavebtn);

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
        displayData(key);
    }
    private void displayData(String key) {
        databaseReference.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String date=snapshot.child("date").getValue().toString();
                    String expensename=snapshot.child("expensename").getValue().toString();
                    String howmuch=snapshot.child("howmuch").getValue().toString();
                    String receipt=snapshot.child("receipt").getValue().toString();
                    String notes=snapshot.child("notes").getValue().toString();

                    textInputDate.setText(date);
                    textInputExpenseName.setText(expensename);
                    textInputHowMuch.setText(howmuch);
                    textInputReceipt.setText(receipt);
                    textInputNotes.setText(notes);

                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    private void verifyData() {
        String date = textInputDate.getText().toString();
        String expensename = textInputExpenseName.getText().toString();
        String howmuch = textInputHowMuch.getText().toString();
        String receipt= textInputReceipt.getText().toString();
        String notes=textInputNotes.getText().toString();

        if (date.isEmpty()) {
            Toast.makeText(this, "Please enter the date", Toast.LENGTH_SHORT).show();
        }
        if (expensename.isEmpty()) {
            Toast.makeText(this, "Please enter the name of expense", Toast.LENGTH_SHORT).show();
        }
        if (howmuch.isEmpty()) {
            Toast.makeText(this, "Please enter how much you spent", Toast.LENGTH_SHORT).show();
        }
        if (notes.isEmpty()) {
            Toast.makeText(this, "Please enter a few notes", Toast.LENGTH_SHORT).show();
        } else {
            progressDialog.setTitle("Adding Expense");
            progressDialog.setMessage("Please wait while we are adding expense input");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            saveDataToDatabase(date, expensename, howmuch, receipt,notes);
        }

    }
    private void saveDataToDatabase(String date, String expensename, String howmuch, String receipt, String notes) {
        HashMap hashMap = new HashMap();
        hashMap.put("date", date);
        hashMap.put("expensename",expensename);
        hashMap.put("howmuch", howmuch);
        hashMap.put("receipt", receipt);
        hashMap.put("notes", notes);

        databaseReference.child(date).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull @NotNull Task task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(ExpenseActivity.this, MainActivity.class);
                    startActivity(intent);
                    progressDialog.dismiss();

                    finish();
                } else {
                    String error = task.getException().getMessage().toString();
                    Toast.makeText(ExpenseActivity.this, "Error Occurred" + error, Toast.LENGTH_SHORT).show();
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
        new DatePickerDialog(ExpenseActivity.this, dateSetListener, calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();


    }

}