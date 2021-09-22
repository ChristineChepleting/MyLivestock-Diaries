package com.example.mylivestockdiaries.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.mylivestockdiaries.R;

import java.util.ArrayList;
import java.util.List;

public class FeedFormulation extends AppCompatActivity {
    private Toolbar toolbar;
    private Button buttonSaveFeed;
    private CheckBox checkBoxMaize,checkBoxWheat,checkBoxOat,checkBoxsorghum,checkBoxsunflower,checkBoxfish ,checkBoxsoya,
            checkBoxGroundnut,checkBoxSodium,checkBoxMagnisium, checkBoxLimestone,checkBoxPhosphate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_formulation);
        toolbar=findViewById(R.id.ingridientsToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Select ingredients");

        checkBoxMaize = findViewById(R.id.feedmaizeBran);
        checkBoxWheat = findViewById(R.id.feedwheatBran);
        checkBoxOat = findViewById(R.id.feedoatBran);
        checkBoxsorghum =findViewById(R.id.feedsorghumBran);

        checkBoxsunflower=findViewById(R.id.feedsunflowercake);
        checkBoxfish = findViewById(R.id.feedfishmeal);
        checkBoxsoya =findViewById(R.id.feedsoyabeans);
        checkBoxGroundnut =findViewById(R.id.feedgroundnuts);

        checkBoxSodium= findViewById(R.id.feedsodiumChloride);
        checkBoxMagnisium = findViewById(R.id.feedmagnesium);
        checkBoxLimestone = findViewById(R.id.feedlimestone);
        checkBoxPhosphate = findViewById(R.id.feedphosphate);

        buttonSaveFeed=findViewById(R.id.feedSaveButton);

        buttonSaveFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConditions();
            }
        });
    }

    private void checkConditions() {
        if (!(checkBoxMaize.isChecked()&&checkBoxWheat.isChecked()
                ||checkBoxMaize.isChecked()&&checkBoxOat.isChecked()
                ||checkBoxMaize.isChecked()&&checkBoxsorghum.isChecked()
                ||checkBoxWheat.isChecked()&&checkBoxOat.isChecked()
                ||checkBoxWheat.isChecked()&&checkBoxsorghum.isChecked()
                ||checkBoxOat.isChecked()&&checkBoxsorghum.isChecked())){
            Toast.makeText(this, "select atleast two energy", Toast.LENGTH_SHORT).show();
        }

        else if (!(checkBoxsunflower.isChecked()&&checkBoxfish .isChecked()
                ||checkBoxsunflower.isChecked()&&checkBoxsoya.isChecked()
                ||checkBoxsunflower.isChecked()&& checkBoxGroundnut.isChecked()
                ||checkBoxfish.isChecked()&&checkBoxsoya.isChecked()
                ||checkBoxfish.isChecked()&& checkBoxGroundnut.isChecked()
                ||checkBoxsoya.isChecked()&& checkBoxGroundnut.isChecked())){
            Toast.makeText(this, "select atleast two protein", Toast.LENGTH_SHORT).show();
        }


        else if (!(checkBoxSodium.isChecked()&&checkBoxLimestone.isChecked()
                || checkBoxSodium.isChecked()&&checkBoxMagnisium.isChecked()
                ||checkBoxSodium.isChecked()&&checkBoxPhosphate.isChecked()
                || checkBoxLimestone.isChecked()&&checkBoxMagnisium.isChecked()
                ||checkBoxLimestone.isChecked()&&checkBoxPhosphate.isChecked()
                ||checkBoxMagnisium.isChecked()&&checkBoxPhosphate.isChecked())){
            Toast.makeText(this, "select atleast two mineral", Toast.LENGTH_SHORT).show();
        }

        else{

            List<String> cars = new ArrayList<String>();
            if (checkBoxMaize.isChecked()){
                cars.add(checkBoxMaize.getText().toString());
            }
            if (checkBoxWheat.isChecked()){
                cars.add(checkBoxWheat.getText().toString());
            }
            if (checkBoxOat.isChecked()){
                cars.add(checkBoxOat.getText().toString());
            }if (checkBoxsorghum.isChecked()){
                cars.add(checkBoxsorghum.getText().toString());
            }
            if (checkBoxsunflower.isChecked()){
                cars.add(checkBoxsunflower.getText().toString());
            }
            if (checkBoxfish.isChecked()){
                cars.add(checkBoxfish.getText().toString());
            }
            if (checkBoxsoya.isChecked()){
                cars.add(checkBoxsoya.getText().toString());
            }
            if (checkBoxGroundnut.isChecked()){
                cars.add(checkBoxGroundnut.getText().toString());
            }
            if (checkBoxSodium.isChecked()){
                cars.add(checkBoxSodium.getText().toString());
            }
            if (checkBoxLimestone.isChecked()){
                cars.add(checkBoxLimestone.getText().toString());
            }
            if (checkBoxMagnisium.isChecked()){
                cars.add(checkBoxMagnisium.getText().toString());
            }
            if (checkBoxPhosphate.isChecked()){
                cars.add(checkBoxPhosphate.getText().toString());
            }
            else{

                String[] items = cars.toArray(new String[cars.size()]);
                Intent intent=new Intent(FeedFormulation.this,PriceActivity.class);
                intent.putExtra("string-array", items);
                startActivity(intent);

            }





        }



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
    }
}