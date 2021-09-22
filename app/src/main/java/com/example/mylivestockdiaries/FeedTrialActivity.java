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

import com.example.mylivestockdiaries.activities.Treatment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class FeedTrialActivity extends AppCompatActivity {
    private Button button;
    public Button btn;
    private Toolbar toolbar;
    private EditText editTextmaizebran,editTextwheatbran,editTextoats,editTextsorghum;
    private TextView textViewmaize,textViewwheat,textViewoats,textViewsorghum,textViewfeedweight1,textViewfeedweight2,textViewtotalcost1,getTextViewtotalcost2;
    double pricemaize,pricewheat,priceoats,pricesorghum;
    double wheatweight,maizeweight,oatsweight,sorghumweight,totalcostmaize,totalcostwheat,totalcostoats,totalcostsorghum;
    double totalfeedcost, totalfeedweight ;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    String current_user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_trial);
        toolbar = findViewById(R.id.feedingtrialtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("feed energy calculation");
        //firebase initialization
        firebaseAuth=FirebaseAuth.getInstance();
        current_user_id=firebaseAuth.getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Feeds").child(current_user_id);
//
//        btn=(Button) findViewById(R.id.protein);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent=new Intent(FeedTrialActivity.this,ProteinActivity.class);
//                startActivity(intent);
//            }
//        });
    }
    public void tenp(View view1) {
        EditText editTextmaizebran = (EditText) findViewById(R.id.maizebran);
        EditText editTextwheatbran = (EditText) findViewById(R.id.wheatbran);
        EditText editTextoats = (EditText) findViewById(R.id.oats);
        EditText editTextsorghum = (EditText) findViewById(R.id.sorghum);
        TextView textViewfeedweight1=(TextView) findViewById(R.id.feedweight1);
        TextView textViewfeedweight2=(TextView) findViewById(R.id.feedweight2);
        TextView textViewtotalcost1=(TextView) findViewById(R.id.totalcost1);
        TextView textViewtotalcost2=(TextView) findViewById(R.id.totalcost2);


            if (!editTextmaizebran.getText().toString().equals("")&&!editTextwheatbran.getText().toString().equals("")
                    &&editTextoats.getText().toString().equals("")&&editTextsorghum.getText().toString().equals(""))
            {
                pricemaize = Double.parseDouble(editTextmaizebran.getText().toString());
                pricewheat=Double.parseDouble(editTextwheatbran.getText().toString());
                maizeweight=((0.25)*75);
                wheatweight=((0.75)*75);
                totalcostmaize=(pricemaize*maizeweight);
                totalcostwheat=(pricewheat*wheatweight);
                String feedweight1 = String.valueOf(maizeweight);
                String feedweight2 = String.valueOf(wheatweight);
                String totalcost1 = String.valueOf(totalcostmaize);
                String totalcost2 = String.valueOf(totalcostwheat);
                textViewfeedweight1.setText("Maize weight is:" + feedweight1);
                textViewfeedweight2.setText("Wheat weight is:" + feedweight2);
                textViewtotalcost1.setText("Total cost maize is:" + totalcost1);
                textViewtotalcost2.setText("Total cost wheat is:" + totalcost2);
                saveDataToDatabase();
//                Toast.makeText(getApplicationContext(), "mw" + maizeweight, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "ww" + wheatweight, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "tcm" + totalcostmaize, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "tcw" + totalcostwheat, Toast.LENGTH_SHORT).show();

            }
            else if (!editTextmaizebran.getText().toString().equals("")&&editTextwheatbran.getText().toString().equals("")
                    &&!editTextoats.getText().toString().equals("")&&editTextsorghum.getText().toString().equals(""))
            {
                pricemaize = Double.parseDouble(editTextmaizebran.getText().toString());
                priceoats = Double.parseDouble(editTextoats.getText().toString());
                maizeweight=((0.33)*75);
                oatsweight=((0.67)*75);
                totalcostmaize=(pricemaize*maizeweight);
                totalcostoats=(priceoats*oatsweight);
                String feedweight1 = String.valueOf(maizeweight);
                String feedweight2 = String.valueOf(oatsweight);
                String totalcost1 = String.valueOf(totalcostmaize);
                String totalcost2 = String.valueOf(totalcostoats);
                textViewfeedweight1.setText("Maize weight is:" + feedweight1);
                textViewfeedweight2.setText("Oats weight is:" + feedweight2);
                textViewtotalcost1.setText("Total cost maize is:" + totalcost1);
                textViewtotalcost2.setText("Total cost oats is:" + totalcost2);
                saveDataToDatabase();
//                Toast.makeText(getApplicationContext(), "ow" + oatsweight, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "mw" + maizeweight, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "tcm" + totalcostmaize, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "tco" + totalcostoats, Toast.LENGTH_SHORT).show();
            }
            else if (!editTextmaizebran.getText().toString().equals("")&& editTextwheatbran.getText().toString().equals("")
                    &&editTextoats.getText().toString().equals("")&&!editTextsorghum.getText().toString().equals(""))
            {
                pricemaize = Double.parseDouble(editTextmaizebran.getText().toString());
                pricesorghum=Double.parseDouble(editTextsorghum.getText().toString());
                maizeweight=((0.45)*75);
                sorghumweight=((0.55)*75);
                totalcostmaize=(pricemaize*maizeweight);
                totalcostsorghum=(pricesorghum*sorghumweight);

                String feedweight1 = String.valueOf(maizeweight);
                String feedweight2 = String.valueOf(sorghumweight);
                String totalcost1 = String.valueOf(totalcostmaize);
                String totalcost2 = String.valueOf(totalcostsorghum);
                textViewfeedweight1.setText("Maize weight is:" + feedweight1);
                textViewfeedweight2.setText("Sorghum weight is:" + feedweight2);
                textViewtotalcost1.setText("Total cost maize is:" + totalcost1);
                textViewtotalcost2.setText("Total cost sorghum is:" + totalcost2);
                saveDataToDatabase();
//                Toast.makeText(getApplicationContext(), "sw" + sorghumweight, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "mw" + maizeweight, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "tcm" + totalcostmaize, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "tcs" + totalcostsorghum, Toast.LENGTH_SHORT).show();
            }
            else if (editTextmaizebran.getText().toString().equals("")&& !editTextwheatbran.getText().toString().equals("")
                    &&!editTextoats.getText().toString().equals("")&& editTextsorghum.getText().toString().equals(""))
            {
                priceoats = Double.parseDouble(editTextoats.getText().toString());
                pricewheat=Double.parseDouble(editTextwheatbran.getText().toString());
                oatsweight=((0.4)*75);
                wheatweight=((0.6)*75);
                totalcostoats=(priceoats*oatsweight);
                totalcostwheat=(pricewheat*wheatweight);
                String feedweight1 = String.valueOf(oatsweight);
                String feedweight2 = String.valueOf(wheatweight);
                String totalcost1 = String.valueOf(totalcostoats);
                String totalcost2 = String.valueOf(totalcostwheat);
                textViewfeedweight1.setText("Oats weight is:" + feedweight1);
                textViewfeedweight2.setText("Wheat weight is:" + feedweight2);
                textViewtotalcost1.setText("Total cost of oats is:" + totalcost1);
                textViewtotalcost2.setText("Total cost of wheat is:" + totalcost2);
                saveDataToDatabase();
//                Toast.makeText(getApplicationContext(), "ow" + oatsweight, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "ww" + wheatweight, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "tco" + totalcostoats, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "tcw" + totalcostwheat, Toast.LENGTH_SHORT).show();

            }
            else if (editTextmaizebran.getText().toString().equals("")&& !editTextwheatbran.getText().toString().equals("")
                    &&editTextoats.getText().toString().equals("")&& !editTextsorghum.getText().toString().equals(""))
            {
                pricesorghum = Double.parseDouble(editTextsorghum.getText().toString());
                pricewheat=Double.parseDouble(editTextwheatbran.getText().toString());
                sorghumweight=((0.29)*75);
                wheatweight=((0.71)*75);
                totalcostsorghum=(pricesorghum*sorghumweight);
                totalcostwheat=(pricewheat*wheatweight);
                String feedweight1 = String.valueOf(sorghumweight);
                String feedweight2 = String.valueOf(wheatweight);
                String totalcost1 = String.valueOf(totalcostsorghum);
                String totalcost2 = String.valueOf(totalcostwheat);
                textViewfeedweight1.setText("Sorghum weight is:" + feedweight1);
                textViewfeedweight2.setText("Wheat weight is:" + feedweight2);
                textViewtotalcost1.setText("Total cost of sorghum is:" + totalcost1);
                textViewtotalcost2.setText("Total cost of wheat is:" + totalcost2);
                saveDataToDatabase();
//                Toast.makeText(getApplicationContext(), "sw" + sorghumweight, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "ww" + wheatweight, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "tcs" + totalcostsorghum, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "tcw" + totalcostwheat, Toast.LENGTH_SHORT).show();
            }
            else if (editTextmaizebran.getText().toString().equals("")&& editTextwheatbran.getText().toString().equals("")
                    &&!editTextoats.getText().toString().equals("")&& !editTextsorghum.getText().toString().equals(""))
            {
                pricesorghum = Double.parseDouble(editTextsorghum.getText().toString());
                priceoats=Double.parseDouble(editTextoats.getText().toString());
                sorghumweight=((0.375)*75);
                oatsweight=((0.625)*75);
                totalcostsorghum=(pricesorghum*sorghumweight);
                totalcostoats=(priceoats*oatsweight);

                String feedweight1 = String.valueOf(sorghumweight);
                String feedweight2 = String.valueOf(oatsweight);
                String totalcost1 = String.valueOf(totalcostsorghum);
                String totalcost2 = String.valueOf(totalcostoats);
                textViewfeedweight1.setText("Sorghum weight is:" + feedweight1);
                textViewfeedweight2.setText("Oats weight is:" + feedweight2);
                textViewtotalcost1.setText("Total cost sorghum is:" + totalcost1);
                textViewtotalcost2.setText("Total cost oats is:" + totalcost2);
                saveDataToDatabase();

//                Toast.makeText(getApplicationContext(), "sw" + sorghumweight, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "ow" + oatsweight, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "tcs" + totalcostsorghum, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getApplicationContext(), "tco" + totalcostoats, Toast.LENGTH_SHORT).show();
            }

            else {
                    Toast.makeText(getApplicationContext(), "Only select two feeds not one,three,or four", Toast.LENGTH_SHORT).show();
                }

            }
    private void saveDataToDatabase() {
        Calendar calForDate=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("dd-MMMM-yyyy");
        String date=currentDate.format(calForDate.getTime());


        HashMap hashMap=new HashMap();
        hashMap.put("price_maize",pricemaize);
        hashMap.put("price_wheat",pricewheat);
        hashMap.put("price_oats",priceoats);
        hashMap.put("price_sorghum",pricesorghum);
        hashMap.put("maize _weight",maizeweight);
        hashMap.put("wheat_weight",wheatweight);
        hashMap.put("oat_weight",oatsweight);
        hashMap.put("sorghum_weight",sorghumweight);
        hashMap.put("maize_cost",totalcostmaize);
        hashMap.put("wheat_cost",totalcostwheat);
        hashMap.put("oats_cost",totalcostoats);
        hashMap.put("sorghum_cost",totalcostsorghum);

        databaseReference.child(date).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull @NotNull Task task) {
                if (task.isSuccessful()){
                    Intent intent=new Intent(FeedTrialActivity.this, ProteinActivity.class);
                        startActivity(intent);
//                        progressDialog.dismiss();

                        finish();
                }

                else {
                    String error=task.getException().getMessage().toString();
                    Toast.makeText(FeedTrialActivity.this, "Error Occurred"+error, Toast.LENGTH_SHORT).show();
                }


    }


        });
}}

