package com.example.mylivestockdiaries.activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

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
import com.google.firebase.database.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

public class MilkAdd extends AppCompatActivity {
    private Toolbar toolbar;
    private TextInputEditText textInputDate,textInputTag,textInputTotal,textInputUsed;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private String currentUserId,title,key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milk_add);
        title=getIntent().getExtras().get("title").toString();
        key=getIntent().getExtras().get("key").toString();
        toolbar=findViewById(R.id.newMilkToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        //firebase initialization
        firebaseAuth=FirebaseAuth.getInstance();
        currentUserId=firebaseAuth.getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("MilkRecords").child(currentUserId);

        textInputDate=findViewById(R.id.newMilkDate);
        textInputTag=findViewById(R.id.newMilktagnumber);
        textInputTotal=findViewById(R.id.newMilktotalMilk);
        textInputUsed=findViewById(R.id.newMilkused);


        textInputDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                showDateDialog(textInputDate);

            }
        });
      dataToshow(key);

    }

    private void dataToshow(String key) {
        databaseReference.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @org.jetbrains.annotations.NotNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String date=snapshot.child("date").getValue().toString();
                    String consumed=snapshot.child("consumed").getValue().toString();
                    String tag=snapshot.child("tagNumber").getValue().toString();
                    String produced=snapshot.child("totalProduced").getValue().toString();

                    textInputDate.setText(date);
                    textInputTag.setText(tag);
                    textInputUsed.setText(consumed);
                    textInputTotal.setText(produced);


                }

            }

            @Override
            public void onCancelled(@NonNull @org.jetbrains.annotations.NotNull DatabaseError error) {

            }
        });
    }

    private void showDateDialog(final TextInputEditText date_in) {
        final Calendar calendar=Calendar.getInstance();
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR,year);
                calendar.set(Calendar.MONTH,month);
                calendar.set(Calendar.DAY_OF_MONTH,dayOfMonth);
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yy-MM-dd");
                date_in.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };


        new DatePickerDialog(MilkAdd.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.save_milk,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.checked:
                saveMilkRecord();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveMilkRecord() {
        String date= Objects.requireNonNull(textInputDate.getText()).toString();
        String tagNumber=textInputTag.getText().toString();
        String produced=textInputTotal.getText().toString();
        String used=textInputUsed.getText().toString();

        String sold=String.valueOf(Integer.parseInt(produced)-Integer.parseInt(used));


        if (date.isEmpty()&&tagNumber.isEmpty()&&produced.isEmpty()&&used.isEmpty()){
            Toast.makeText(this, "Please fill out fields with (*)", Toast.LENGTH_SHORT).show();
        }
        else if (Integer.parseInt(used)>Integer.parseInt(produced)){
            Toast.makeText(this, "Invalid consumed amount", Toast.LENGTH_SHORT).show();
        }
        else{
            HashMap hashMap=new HashMap();
            hashMap.put("date",date);
            hashMap.put("tagNumber",tagNumber);
            hashMap.put("totalProduced",produced);
            hashMap.put("consumed",used);
            hashMap.put("sold",sold);
            databaseReference.child(tagNumber).updateChildren(hashMap)
            .addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull @NotNull Task task) {
                    if (task.isSuccessful()){
                        Intent intent=new Intent(MilkAdd.this, MilkRecordActivity.class);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                        finish();
                    }
                }
            });


        }

    }
}