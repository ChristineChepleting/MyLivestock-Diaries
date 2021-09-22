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

public class ProteinActivity extends AppCompatActivity {
    private Button button;
    public Button btn;
    private Toolbar toolbar;
    private EditText editTextsunflower,editTextfish,editTextsoya,editTextgroundnut;
    private TextView textViewsunflower,textViewfish,textViewsoya,textViewgroundnut,textViewfeedweight1,textViewfeedweight2,textViewtotalcost1,TextViewtotalcost2;
    double pricesunflower,pricefish,pricesoya,pricegroundnut,sunflowerweight,fishweight,soyaweight,groundnutweight,totalcostsunflower,totalcostfish,totalcostsoya,totalcostgroundnut;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    String current_user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_protein);
        toolbar = findViewById(R.id.proteintoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("protein feed calculation");
        firebaseAuth=FirebaseAuth.getInstance();
        current_user_id=firebaseAuth.getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Feeds").child(current_user_id);
//        btn=(Button) findViewById(R.id.additives);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(ProteinActivity.this,feedadditives.class);
//                startActivity(intent);
//            }
//        });

    }
    public void tenp(View view1) {
        EditText editTextsunflower = (EditText) findViewById(R.id.sunflowercake2);
        EditText editTextfish= (EditText) findViewById(R.id.fishmealprice);
        EditText editTextsoya = (EditText) findViewById(R.id.soyaprice);
        EditText editTextgroundnut = (EditText) findViewById(R.id.groundnutprice);
        TextView textViewfeedweight1=(TextView) findViewById(R.id.feedweight1);
        TextView textViewfeedweight2=(TextView) findViewById(R.id.feedweight2);
        TextView textViewtotalcost1=(TextView) findViewById(R.id.totalcost1);
        TextView textViewtotalcost2=(TextView) findViewById(R.id.totalcost2);

        if (!editTextsunflower.getText().toString().equals("")&&!editTextfish.getText().toString().equals("")
                &&editTextsoya.getText().toString().equals("")&&editTextgroundnut.getText().toString().equals(""))
        {
            pricesunflower = Double.parseDouble(editTextsunflower.getText().toString());
            pricefish=Double.parseDouble(editTextfish.getText().toString());
            sunflowerweight=((0.17)*23);
            fishweight=((0.83)*23);
            totalcostsunflower=(pricesunflower*sunflowerweight);
            totalcostfish=(pricefish*fishweight);
            String feedweight1 = String.valueOf(sunflowerweight);
            String feedweight2 = String.valueOf(fishweight);
            String totalcost1 = String.valueOf(totalcostsunflower);
            String totalcost2 = String.valueOf(totalcostfish);
            textViewfeedweight1.setText("Sunflower weight is:" + feedweight1);
            textViewfeedweight2.setText("Fish weight is:" + feedweight2);
            textViewtotalcost1.setText("Total cost sunflower is:" + totalcost1);
            textViewtotalcost2.setText("Total cost fish is:" + totalcost2);
            saveDataToDatabase();
//            Toast.makeText(getApplicationContext(), "sunflower" + sunflowerweight, Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), "fish" + fishweight, Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), "tcsunflower" + totalcostsunflower, Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), "tcfish" + totalcostfish, Toast.LENGTH_SHORT).show();
        }
        else if (!editTextsunflower.getText().toString().equals("")&&editTextfish.getText().toString().equals("")
                &&!editTextsoya.getText().toString().equals("")&&editTextgroundnut.getText().toString().equals(""))
        {
            pricesunflower = Double.parseDouble(editTextsunflower.getText().toString());
            pricesoya=Double.parseDouble(editTextsoya.getText().toString());
            sunflowerweight=((0.11)*23);
            soyaweight=((0.89)*23);
            totalcostsunflower=(pricesunflower*sunflowerweight);
            totalcostsoya=(pricesoya*soyaweight);
            String feedweight1 = String.valueOf(sunflowerweight);
            String feedweight2 = String.valueOf(soyaweight);
            String totalcost1 = String.valueOf(totalcostsunflower);
            String totalcost2 = String.valueOf(totalcostsoya);
            textViewfeedweight1.setText("Sunflower weight is:" + feedweight1);
            textViewfeedweight2.setText("Soya weight is:" + feedweight2);
            textViewtotalcost1.setText("Total cost sunflower is:" + totalcost1);
            textViewtotalcost2.setText("Total cost soya is:" + totalcost2);
            saveDataToDatabase();
//            Toast.makeText(getApplicationContext(), "sunflower" + sunflowerweight, Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), "soya" + soyaweight, Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), "tcsunflower" + totalcostsunflower, Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), "tcsoya" + totalcostsoya, Toast.LENGTH_SHORT).show();
        }
        else if (!editTextsunflower.getText().toString().equals("")&&editTextfish.getText().toString().equals("")
                &&editTextsoya.getText().toString().equals("")&&!editTextgroundnut.getText().toString().equals(""))
        {
            pricesunflower = Double.parseDouble(editTextsunflower.getText().toString());
            pricegroundnut=Double.parseDouble(editTextgroundnut.getText().toString());
            sunflowerweight=((0.17)*23);
            groundnutweight=((0.83)*23);
            totalcostsunflower=(pricesunflower*sunflowerweight);
            totalcostgroundnut=(pricegroundnut*groundnutweight);
            String feedweight1 = String.valueOf(sunflowerweight);
            String feedweight2 = String.valueOf(groundnutweight);
            String totalcost1 = String.valueOf(totalcostsunflower);
            String totalcost2 = String.valueOf(totalcostgroundnut);
            textViewfeedweight1.setText("Sunflower weight is:" + feedweight1);
            textViewfeedweight2.setText("groundnut weight is:" + feedweight2);
            textViewtotalcost1.setText("Total cost sunflower is:" + totalcost1);
            textViewtotalcost2.setText("Total cost groundnut is:" + totalcost2);
            saveDataToDatabase();
//            Toast.makeText(getApplicationContext(), "sunflower" + sunflowerweight, Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), "groundnut" + groundnutweight, Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), "tcsunflower" + totalcostsunflower, Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), "tcgroundnut" + totalcostgroundnut, Toast.LENGTH_SHORT).show();
        }
        else if (editTextsunflower.getText().toString().equals("")&&!editTextfish.getText().toString().equals("")
                &&!editTextsoya.getText().toString().equals("")&&editTextgroundnut.getText().toString().equals(""))
        {
            pricesoya = Double.parseDouble(editTextsoya.getText().toString());
            pricefish=Double.parseDouble(editTextfish.getText().toString());
            soyaweight=((0.625)*23);
            fishweight=((0.375)*23);
            totalcostsoya=(pricesoya*soyaweight);
            totalcostfish=(pricefish*fishweight);
            String feedweight1 = String.valueOf(soyaweight);
            String feedweight2 = String.valueOf(fishweight);
            String totalcost1 = String.valueOf(totalcostsoya);
            String totalcost2 = String.valueOf(totalcostfish);
            textViewfeedweight1.setText("Soya weight is:" + feedweight1);
            textViewfeedweight2.setText("Fish weight is:" + feedweight2);
            textViewtotalcost1.setText("Total cost soya is:" + totalcost1);
            textViewtotalcost2.setText("Total cost fish is:" + totalcost2);
            saveDataToDatabase();
//            Toast.makeText(getApplicationContext(), "soya" + soyaweight, Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), "fish" + fishweight, Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), "tcsoya" + totalcostsoya, Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), "tcfish" + totalcostfish, Toast.LENGTH_SHORT).show();
        }
        else if (editTextsunflower.getText().toString().equals("")&&!editTextfish.getText().toString().equals("")
                &&editTextsoya.getText().toString().equals("")&&!editTextgroundnut.getText().toString().equals(""))
        {
            pricegroundnut = Double.parseDouble(editTextgroundnut.getText().toString());
            pricefish=Double.parseDouble(editTextfish.getText().toString());
            groundnutweight=((0.5)*23);
            fishweight=((0.5)*23);
            totalcostgroundnut=(pricegroundnut*groundnutweight);
            totalcostfish=(pricefish*fishweight);
            String feedweight1 = String.valueOf(groundnutweight);
            String feedweight2 = String.valueOf(fishweight);
            String totalcost1 = String.valueOf(totalcostgroundnut);
            String totalcost2 = String.valueOf(totalcostfish);
            textViewfeedweight1.setText("Groundnut weight is:" + feedweight1);
            textViewfeedweight2.setText("Fish weight is:" + feedweight2);
            textViewtotalcost1.setText("Total cost groundnut is:" + totalcost1);
            textViewtotalcost2.setText("Total cost fish is:" + totalcost2);
            saveDataToDatabase();
//            Toast.makeText(getApplicationContext(), "groundnut" + groundnutweight, Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), "fish" + fishweight, Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), "tcgroundnut" + totalcostgroundnut, Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), "tcfish" + totalcostfish, Toast.LENGTH_SHORT).show();
        }
        else if (editTextsunflower.getText().toString().equals("")&&editTextfish.getText().toString().equals("")
                &&!editTextsoya.getText().toString().equals("")&&!editTextgroundnut.getText().toString().equals(""))
        {
            pricegroundnut = Double.parseDouble(editTextgroundnut.getText().toString());
            pricesoya=Double.parseDouble(editTextsoya.getText().toString());
            groundnutweight=((0.375)*23);
            soyaweight=((0.625)*23);
            totalcostgroundnut=(pricegroundnut*groundnutweight);
            totalcostsoya=(pricesoya*soyaweight);
            String feedweight1 = String.valueOf(groundnutweight);
            String feedweight2 = String.valueOf(soyaweight);
            String totalcost1 = String.valueOf(totalcostgroundnut);
            String totalcost2 = String.valueOf(totalcostsoya);
            textViewfeedweight1.setText("Groundnut weight is:" + feedweight1);
            textViewfeedweight2.setText("Soya weight is:" + feedweight2);
            textViewtotalcost1.setText("Total cost groundnut is:" + totalcost1);
            textViewtotalcost2.setText("Total cost soya is:" + totalcost2);
            saveDataToDatabase();
//            Toast.makeText(getApplicationContext(), "groundnut" + groundnutweight, Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), "soya" + soyaweight, Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), "tcgroundnut" + totalcostgroundnut, Toast.LENGTH_SHORT).show();
//            Toast.makeText(getApplicationContext(), "tcsoya" + totalcostsoya, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), "Only select two feeds not one,three,or four", Toast.LENGTH_SHORT).show();
        }
    }
    private void saveDataToDatabase(){
        Calendar calForDate=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("dd-MMMM-yyyy");
        String date=currentDate.format(calForDate.getTime());
        HashMap hashMap=new HashMap();
        hashMap.put("sunflower_price",pricesunflower);
        hashMap.put("fish_price",pricefish);
        hashMap.put("soya_price",pricesoya);
        hashMap.put("groundnut_price",pricegroundnut);
        hashMap.put("sunflower_weight",sunflowerweight);
        hashMap.put("fish_weight",fishweight);
        hashMap.put("soya_weight",soyaweight);
        hashMap.put("groundnut_weight",groundnutweight);
        hashMap.put("cost_sunflower",totalcostsunflower);
        hashMap.put("cost_fish",totalcostfish);
        hashMap.put("cost_soya",totalcostsoya);
        hashMap.put("cost_groundnut",totalcostgroundnut);

        databaseReference.child(date).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull @NotNull Task task) {
                if (task.isSuccessful()){
                    Intent intent=new Intent(ProteinActivity.this, feedadditives.class);
                    startActivity(intent);

                    finish();
                }

                else {
                    String error=task.getException().getMessage().toString();
                    Toast.makeText(ProteinActivity.this, "Error Occurred"+error, Toast.LENGTH_SHORT).show();
                }


            }


        });
    }
}