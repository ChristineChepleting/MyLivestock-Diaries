package com.example.mylivestockdiaries.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
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

public class CattleAdd extends AppCompatActivity {

    private Toolbar toolbar;
    private TextInputEditText tagNoEdit,dobEdit,ageEdit,weightEdit, joinedOnEdit,sourceEdit;
    private AutoCompleteTextView autoCompleteTextViewBreed,autoCompleteTextViewGender,autoCompleteTextViewStage;
    private Button buttonSaveButton;
    private  String selectedStage;
    private DatabaseReference databaseReference;
    private  FirebaseAuth firebaseAuth;
    private String currentUserId;
    private ProgressDialog progressDialog;
    private ArrayAdapter <String>adapterStage,adapterBreeds,adapterGender;
    String []stages ={ "Calf","Weaner","Heifer","culled","Cow","Bull"};
    String []breeds={ "Friesian", "Aryshire", "Guernsey","Jersey"};
    String []gender={"male","Female"};
    String title,key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cattle_add);
        toolbar=findViewById(R.id.AddAnimalToolbar);
        title=getIntent().getExtras().get("title").toString();
        key=getIntent().getExtras().get("key").toString();
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(title);
        progressDialog= new ProgressDialog(this);
        firebaseAuth= FirebaseAuth.getInstance();
        currentUserId=firebaseAuth.getCurrentUser().getUid();
       databaseReference= FirebaseDatabase.getInstance().getReference().child("CattleDetail").child(currentUserId);

       autoCompleteTextViewBreed=findViewById(R.id.cattleAddBreed);
       autoCompleteTextViewGender=findViewById(R.id.cattleAddGender);
       autoCompleteTextViewStage=findViewById(R.id.cattleAddStage);
        tagNoEdit=findViewById(R.id.cattleAdddTag);
       dobEdit=findViewById(R.id.catteAddBirth);
        ageEdit=findViewById(R.id.cattleAddAge);
        weightEdit=findViewById(R.id.cattleAddWeight);
        joinedOnEdit=findViewById(R.id.cattleAddJoined);
        sourceEdit=findViewById(R.id.cattleAddSource);
        buttonSaveButton=findViewById(R.id.cattleSave);

      adapterStage = new ArrayAdapter<String>(CattleAdd.this, android.R.layout.simple_spinner_item, stages);
      adapterBreeds = new ArrayAdapter<String>(CattleAdd.this, android.R.layout.simple_spinner_item, breeds);
      adapterGender = new ArrayAdapter<String>(CattleAdd.this, android.R.layout.simple_spinner_item, gender);
      autoCompleteTextViewStage.setAdapter(adapterStage);
      autoCompleteTextViewGender.setAdapter(adapterGender);
      autoCompleteTextViewBreed.setAdapter(adapterBreeds);


        buttonSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });
        dobEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(dobEdit);
            }
        });

        joinedOnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDateDialog(joinedOnEdit);
            }
        });
        displayAllData(key);

    }

    private void displayAllData(String key) {
        databaseReference.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                if (snapshot.exists()){
                    String tags =snapshot.child("tagNumber").getValue().toString();
                    String breeds=snapshot.child("breedName").getValue().toString();
                    String genders=snapshot.child("gender").getValue().toString();
                    String ages=snapshot.child("age").getValue().toString();
                    String dobs=snapshot.child("dob").getValue().toString();
                    String weights=snapshot.child("weight").getValue().toString();
                    String joins=snapshot.child("joinedOn").getValue().toString();
                    String stages=snapshot.child("stage").getValue().toString();
                    String sources=snapshot.child("source").getValue().toString();

                    tagNoEdit.setText(tags);
                    dobEdit.setText(dobs);
                    weightEdit.setText(weights);
                    joinedOnEdit.setText(joins);
                    ageEdit.setText(ages);
                    sourceEdit.setText(sources);
                    autoCompleteTextViewBreed.setText(breeds);
                    autoCompleteTextViewGender.setText(genders);
                    autoCompleteTextViewStage.setText(stages);





                }

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

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
                date_in.setText(simpleDateFormat.format(calendar.getTime()));

            }
        };

        new DatePickerDialog(CattleAdd.this,dateSetListener,calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void saveData() {
        String tagNumber=tagNoEdit.getText().toString();
        String dateBirth=dobEdit.getText().toString();
        String age=ageEdit.getText().toString();
        String weight=weightEdit.getText().toString();
        String joinedOn=joinedOnEdit.getText().toString();
        String  source=sourceEdit.getText().toString();
        String breedName=autoCompleteTextViewBreed.getText().toString();
        String genderCow=autoCompleteTextViewGender.getText().toString();
        String stage=autoCompleteTextViewStage.getText().toString();

        if (tagNumber.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter tag number",Toast.LENGTH_SHORT).show();
        }
        else if (breedName.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter breed",Toast.LENGTH_SHORT).show();
        }
        else if (genderCow.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter gender ",Toast.LENGTH_SHORT).show();
        }
        else if (stage.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter stage",Toast.LENGTH_SHORT).show();
        }

        else if (dateBirth.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter date of birth",Toast.LENGTH_SHORT).show();
        }
        else if (age.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter age ",Toast.LENGTH_SHORT).show();
        }
        else if (weight.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter weight",Toast.LENGTH_SHORT).show();
        }
        else if (joinedOn.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter joinedOn date ",Toast.LENGTH_SHORT).show();
        }
        else if (source.isEmpty()){
            Toast.makeText(getApplicationContext(),"Please enter source",Toast.LENGTH_SHORT).show();
        }
        else{
            progressDialog.setTitle("Adding animal Records");
            progressDialog.setMessage("Please wait while we are adding animal records...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            enterDataToDatabase(tagNumber,breedName,genderCow,dateBirth,age,weight,stage,joinedOn,source);
        }

    }

    private void  enterDataToDatabase(String tagNumber,String breedName, String genderCow, String dateBirth, String
                                    age,String weight,String selectedStage,String joinedOn,String source)
    {

       HashMap hashmap =new  HashMap();
        hashmap.put("tagNumber",tagNumber);
        hashmap.put("breedName",breedName);
        hashmap.put("gender",genderCow);
        hashmap.put("age",age);
        hashmap.put("dob",dateBirth);
        hashmap.put("weight",weight);
        hashmap.put("joinedOn",joinedOn);
        hashmap.put("stage",selectedStage);
        hashmap.put("source",source);
        databaseReference.child(tagNumber).updateChildren(hashmap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Record Added Successfully",Toast.LENGTH_SHORT).show();
                    Intent intentAdd= new Intent(CattleAdd.this, MainActivity.class);
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



    }



}