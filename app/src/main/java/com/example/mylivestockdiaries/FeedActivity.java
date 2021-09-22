package com.example.mylivestockdiaries;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FeedActivity extends AppCompatActivity {
    private Button button;
    private Toolbar toolbar;
    private EditText editTextmaizebran,editTextwheatbran,editTextsunflowercake,editTextfishmeal,editTextsodiumchloride,editTextlimestone;
    private TextView textViewmaize,textViewwheat,textViewsunflower,textViewfish,textViewsodium,textViewlimestone;
    double pricemaize,pricewheat,pricesunflowercake,pricefishmeal,pricesodiumchloride,pricelimestone;
    double wheatweight,maizeweight,sunflowerweight,fishweight,sodiumchlorideweight,limestoneweight;
    double totalcostmaize,totalcostwheat,totalcostsunflower,totalcostfish,totalcostsodiumchloride,totalcostlimestone;
    double totalfeedcost;
    double totalfeedweight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        toolbar = findViewById(R.id.Feedingtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("feed calculation");

    }
    public void tenp(View view1) {
        EditText editTextmaizebran = (EditText) findViewById(R.id.maizebran);
        EditText editTextwheatbran =(EditText) findViewById(R.id.wheatbran);
        EditText editTextsunflowercake =(EditText) findViewById(R.id.sunflowercake);
        EditText editTextfishmeal=(EditText) findViewById(R.id.fishmeal);
        EditText editTextsodiumchloride=(EditText) findViewById(R.id.sodiumchloride);
        EditText editTextlimestone=(EditText) findViewById(R.id.limestone);


        if (!editTextmaizebran.getText().toString().equals("")&&!editTextwheatbran.getText().toString().equals("")
                &&!editTextsunflowercake.getText().toString().equals("")&&!editTextfishmeal.getText().toString().equals("")&&
                (!editTextsodiumchloride.getText().toString().equals("")&&!editTextlimestone.getText().toString().equals(""))){
            double pricemaize = Double.parseDouble(editTextmaizebran.getText().toString());
            double pricewheat=Double.parseDouble(editTextwheatbran.getText().toString());
            double pricesunflowercake = Double.parseDouble(editTextsunflowercake.getText().toString());
            double pricefishmeal=Double.parseDouble(editTextfishmeal.getText().toString());
            double pricesodiumchloride = Double.parseDouble(editTextsodiumchloride.getText().toString());
            double pricelimestone=Double.parseDouble(editTextlimestone.getText().toString());
            double maizeweight=((0.25)*75);
            double wheatweight=((0.75)*75);
            double totalcostmaize=(pricemaize*maizeweight);
            double totalcostwheat=(pricewheat*wheatweight);
            double sunflowerweight=((0.17)*23);
            double fishweight=((0.83)*23);
            double totalcostsunflower=(pricesunflowercake*sunflowerweight);
            double totalcostfish=(pricefishmeal*fishweight);
            double sodiumchlorideweight=1;
            double limestoneweight=1;
            double totalcostsodiumchloride=(pricesodiumchloride*sodiumchlorideweight);
            double totalcostlimestone=(pricelimestone*limestoneweight);
            double totalfeedweight=(maizeweight + wheatweight + fishweight + sunflowerweight + sodiumchlorideweight + limestoneweight);
            double totalfeedcost=(totalcostmaize + totalcostwheat + totalcostsunflower + totalcostfish + totalcostsodiumchloride + totalcostlimestone);
            Toast.makeText(getApplicationContext(), "mw" + maizeweight, Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "ww" + wheatweight, Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "tcm" + totalcostmaize, Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "tcw" + totalcostwheat, Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "sw" + sunflowerweight, Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "fw" + fishweight, Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "tcs" + totalcostsunflower, Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "tcw" + totalcostfish, Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "sow" + sodiumchlorideweight, Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "lw" + limestoneweight, Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "tcso" + totalcostsodiumchloride, Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "tcl" + totalcostlimestone, Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "total feed cost" + totalfeedcost, Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "total feed weight" + totalfeedweight, Toast.LENGTH_SHORT).show();
        }

        else {
            if (editTextmaizebran.getText().toString().equals("")&&editTextwheatbran.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Maizebran and wheatbran cannot be empty", Toast.LENGTH_SHORT).show();
            }
            if (editTextsunflowercake.getText().toString().equals("")&&editTextfishmeal.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "sunflowercake and fishmeal cannot be empty", Toast.LENGTH_SHORT).show();
            }
        }
    }

}