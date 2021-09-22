package com.example.mylivestockdiaries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class feedadditives extends AppCompatActivity {
    private Button button;
    public Button btn;
    private Toolbar toolbar;
    private EditText editTextsodiumchloride,editTextlimestone,editTextmagnesiumoxide;
    private TextView textViewsodiumchloride,textViewlimestone,textViewmagnesiumoxide,textViewmineralweight1,textViewmineralweight2,textViewtotalcost1,TextViewtotalcost2;
    double pricesodiumchloride,pricelimestone,pricemagnesiumoxide,sodiumchlorideweight,limestoneweight,magnesiumoxideweight,totalcostsodiumchloride,totalcostlimestone,totalcostmagnesiumoxide;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    String current_user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedadditives);
        toolbar = findViewById(R.id.additivestoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("minerals");
        firebaseAuth=FirebaseAuth.getInstance();
        current_user_id=firebaseAuth.getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Feeds").child(current_user_id);
//        btn=(Button) findViewById(R.id.ingredients);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(feedadditives.this,Full_ingredients.class);
//                startActivity(intent);
//            }
//        });

    }
    public void tenp(View view1) {
        EditText editTextsodiumchloride = (EditText) findViewById(R.id.Nacl);
        EditText editTextlimestone= (EditText) findViewById(R.id.limestoneprice);
        EditText editTextmagnesiumoxide = (EditText) findViewById(R.id.MaOprice);

        TextView textViewmineralweight1=(TextView) findViewById(R.id.mineralweight1);
        TextView textViewmineralweight2=(TextView) findViewById(R.id.mineralweight2);
        TextView textViewtotalcost1=(TextView) findViewById(R.id.totalcost1);
        TextView textViewtotalcost2=(TextView) findViewById(R.id.totalcost2);

        if (!editTextsodiumchloride.getText().toString().equals("")&&!editTextlimestone.getText().toString().equals("")
                &&editTextmagnesiumoxide.getText().toString().equals(""))
        {
            pricesodiumchloride = Double.parseDouble(editTextsodiumchloride.getText().toString());
            pricelimestone=Double.parseDouble(editTextlimestone.getText().toString());
            sodiumchlorideweight=1;
            limestoneweight=1;
            totalcostsodiumchloride=(pricesodiumchloride*sodiumchlorideweight);
            totalcostlimestone=(pricelimestone*limestoneweight);
            String mineralweight1 = String.valueOf(sodiumchlorideweight);
            String mineralweight2 = String.valueOf(limestoneweight);
            String totalcost1 = String.valueOf(totalcostsodiumchloride);
            String totalcost2 = String.valueOf(totalcostlimestone);
            textViewmineralweight1.setText("Sodium chloride weight is:" + mineralweight1);
            textViewmineralweight2.setText("Limestone weight is:" + mineralweight2);
            textViewtotalcost1.setText("Total cost of sodium chloride is:" + totalcost1);
            textViewtotalcost2.setText("Total cost of limestone is:" + totalcost2);
            saveDataToDatabase();

        }
        else if (!editTextsodiumchloride.getText().toString().equals("")&&editTextlimestone.getText().toString().equals("")
                &&!editTextmagnesiumoxide.getText().toString().equals(""))
        {
            pricesodiumchloride = Double.parseDouble(editTextsodiumchloride.getText().toString());
            pricemagnesiumoxide=Double.parseDouble(editTextmagnesiumoxide.getText().toString());
            sodiumchlorideweight=1;
            magnesiumoxideweight=1;
            totalcostsodiumchloride=(pricesodiumchloride*sodiumchlorideweight);
            totalcostmagnesiumoxide=(pricemagnesiumoxide*magnesiumoxideweight);
            String mineralweight1 = String.valueOf(sodiumchlorideweight);
            String mineralweight2 = String.valueOf(magnesiumoxideweight);
            String totalcost1 = String.valueOf(totalcostsodiumchloride);
            String totalcost2 = String.valueOf(totalcostmagnesiumoxide);
            textViewmineralweight1.setText("Sodium chloride weight is:" + mineralweight1);
            textViewmineralweight2.setText("Magnesium oxide weight is:" + mineralweight2);
            textViewtotalcost1.setText("Total cost of sodium chloride is:" + totalcost1);
            textViewtotalcost2.setText("Total cost of Magnesium oxide is:" + totalcost2);
            saveDataToDatabase();
        }
        else if (editTextsodiumchloride.getText().toString().equals("")&&!editTextlimestone.getText().toString().equals("")
                &&!editTextmagnesiumoxide.getText().toString().equals(""))
        {
           pricelimestone = Double.parseDouble(editTextlimestone.getText().toString());
           pricemagnesiumoxide=Double.parseDouble(editTextmagnesiumoxide.getText().toString());
           limestoneweight=1;
           magnesiumoxideweight=1;
           totalcostlimestone=(pricelimestone*limestoneweight);
           totalcostmagnesiumoxide=(pricemagnesiumoxide*magnesiumoxideweight);
            String mineralweight1 = String.valueOf(limestoneweight);
            String mineralweight2 = String.valueOf(magnesiumoxideweight);
            String totalcost1 = String.valueOf(totalcostlimestone);
            String totalcost2 = String.valueOf(totalcostmagnesiumoxide);
            textViewmineralweight1.setText("Limestone weight is:" + mineralweight1);
            textViewmineralweight2.setText("Magnesium oxide weight is:" + mineralweight2);
            textViewtotalcost1.setText("Total cost of limestone is:" + totalcost1);
            textViewtotalcost2.setText("Total cost of Magnesium oxide is:" + totalcost2);
            saveDataToDatabase();
        }
        else {
            Toast.makeText(getApplicationContext(), "Only select two minerals not one or three", Toast.LENGTH_SHORT).show();
        }
    }
    private void saveDataToDatabase(){
        Calendar calForDate=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("dd-MMMM-yyyy");
        String date=currentDate.format(calForDate.getTime());
        HashMap hashMap=new HashMap();
        hashMap.put("sodium_price",pricesodiumchloride);
        hashMap.put("limestone_price",pricelimestone);
        hashMap.put("magnesium_price",pricemagnesiumoxide);
        hashMap.put("sodium_weight",sodiumchlorideweight);
        hashMap.put("limestone_weight",limestoneweight);
        hashMap.put("magnesium_weight",magnesiumoxideweight);
        hashMap.put("sodium_cost",totalcostsodiumchloride);
        hashMap.put("limestone_cost",totalcostlimestone);
        hashMap.put("magnesium_cost",totalcostmagnesiumoxide);

        databaseReference.child(date).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull @NotNull Task task) {
                if (task.isSuccessful()){
                    Intent intent=new Intent(feedadditives.this,Full_ingredients.class);
                    startActivity(intent);

                    finish();
                }

                else {
                    String error=task.getException().getMessage().toString();
                    Toast.makeText(feedadditives.this, "Error Occurred"+error, Toast.LENGTH_SHORT).show();
                }


            }


        });
    }
}