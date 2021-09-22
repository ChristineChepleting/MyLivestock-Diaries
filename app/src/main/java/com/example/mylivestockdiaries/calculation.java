package com.example.mylivestockdiaries;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.mylivestockdiaries.activities.CattleAdd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class calculation extends AppCompatActivity {
    private Toolbar toolbar;
//    private TextInputEditText monthEdit;
//    private AutoCompleteTextView autoCompleteTextViewMonth;
//    private ArrayAdapter<String> adapterMonth;
//    String []months ={ "January","February","March","April","May","June","July","August","September","October","November","December"};
    private EditText editTextelegible,editTextcowonheat,editTextpgcattle,editTextcattleinseminated;
    private TextView textVieweligible,textViewcowonheat,textViewHDR,textViewCR,textViewPR;
    double eligible,cowsonheat, cowspregnant,cattleinseminated, HDR,CR,PR;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    String current_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculation);
        toolbar = findViewById(R.id.calculationToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Breeding calculation");
        firebaseAuth = FirebaseAuth.getInstance();
        current_user_id = firebaseAuth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("breeding calculation").child(current_user_id);
//        autoCompleteTextViewMonth=findViewById(R.id.selectmonth);
//        monthEdit=findViewById(R.id.months);
//        adapterMonth = new ArrayAdapter<String>(calculation.this, android.R.layout.simple_spinner_item, months);
//        autoCompleteTextViewMonth.setAdapter(adapterMonth);
    }


    public void tenp(View view1) {

        EditText editTextelegible = (EditText) findViewById(R.id.cowseligible);
        EditText editTextcowonheat =(EditText) findViewById(R.id.heatedittext);
        EditText editTextpgcattle =(EditText) findViewById(R.id.pgcattleedittext);
        EditText editTextcattleinseminated=(EditText) findViewById(R.id.inseminatedcattleedittext);

        TextView textVieweligible=(TextView) findViewById(R.id.eligibletextView);
        TextView textViewHDR=(TextView) findViewById(R.id.HDR);
        TextView textViewCR=(TextView) findViewById(R.id.CR);
        TextView textViewPR=(TextView) findViewById(R.id.PR);

        if (!editTextelegible.getText().toString().equals("") && !editTextcowonheat.getText().toString().equals("") &&
                !editTextpgcattle.getText().toString().equals("") && !editTextcattleinseminated.getText().toString().equals("")){
            eligible = Double.parseDouble(editTextelegible.getText().toString());
            cowsonheat = Double.parseDouble(editTextcowonheat.getText().toString());
            cowspregnant = Double.parseDouble(editTextpgcattle.getText().toString());
            cattleinseminated = Double.parseDouble(editTextcattleinseminated.getText().toString());

            HDR =(cowsonheat / eligible) * 100;
            CR = (cowspregnant / cattleinseminated) * 100;
            PR = (HDR * CR) / 100;

            String HDRTXT = String.valueOf(HDR);
            String CRTXT = String.valueOf(CR);
            String PRTXT = String.valueOf(PR);

            textViewHDR.setText("Heat Detection rate is:" + HDRTXT);
            textViewCR.setText("Conception  rate is:" + CRTXT);
            textViewPR.setText("Pregnancy  rate is:" + PRTXT);
            saveDataToDatabase();
        }
        else{
            if (editTextelegible.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Eligible cannot be empty", Toast.LENGTH_SHORT).show();
            }
            if (editTextcowonheat.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "cows on heat cannot be empty", Toast.LENGTH_SHORT).show();
            }
            if (editTextpgcattle.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Cows pregnant cannot be empty", Toast.LENGTH_SHORT).show();
            }
            if (editTextcattleinseminated.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Cattle inseminated cannot be empty", Toast.LENGTH_SHORT).show();
            }

        }

    }
    private void saveDataToDatabase()
    {
          HashMap hashMap=new HashMap();
            hashMap.put("eligible",eligible);
            hashMap.put("cowsonheat",cowsonheat);
            hashMap.put("cowspregnant",cowspregnant);
            hashMap.put("cattleinseminated",cattleinseminated);
            hashMap.put("hdr",HDR);
            hashMap.put("cr",CR);
            hashMap.put("pr",PR);

            databaseReference.updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull @NotNull Task task) {
                    if (task.isSuccessful()){

//                        Intent intent=new Intent(calculation.this, MainActivity.class);
//                        startActivity(intent);
////                        progressDialog.dismiss();
//
//                        finish();

                    }
                    else {
                        String error=task.getException().getMessage().toString();
                        Toast.makeText(calculation.this, "Error Occurred"+error, Toast.LENGTH_SHORT).show();
//                        progressDialog.dismiss();
                    }
                }
            });
    }
}
