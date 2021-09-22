package com.example.mylivestockdiaries.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.mylivestockdiaries.Artificialinsemination;
import com.example.mylivestockdiaries.PregnancyActivity;
import com.example.mylivestockdiaries.R;
import com.example.mylivestockdiaries.calculation;
import com.example.mylivestockdiaries.christine;

import java.util.Objects;

import static android.R.layout.simple_list_item_1;

public class BreedingActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView;
    String[] events = {"Artificial insemination", "Drying", "Pregnancy confirmation", "HDR,CR,PR"};
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breeding);
        toolbar = findViewById(R.id.breedingToolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Breeding");
        listView = findViewById(R.id.breedingListView);
        adapter = new ArrayAdapter<String>(this, simple_list_item_1, events);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    Intent intentArtificialinsemination = new Intent(BreedingActivity.this, Artificialinsemination.class);
                    startActivity(intentArtificialinsemination);

                }
                if (position == 1) {
                    Intent intentDrying = new Intent(BreedingActivity.this, christine.class);
                    startActivity(intentDrying);
                }
                if (position == 2) {
                    Intent intentPregnancy = new Intent(BreedingActivity.this, PregnancyActivity.class);
                    startActivity(intentPregnancy);
                }
                if (position == 3) {
                    Intent intentCalculations = new Intent(BreedingActivity.this, calculation.class);
                    startActivity(intentCalculations);

                }



/*
                if (position==2){
                    Intent intentTreatment=new Intent(BreedingActivity.this, CrossbreedingActivity.class);
                    startActivity(intentTreatment);
                }
                if (position==3){
                    Intent intentTreatment=new Intent(BreedingActivity.this, PgActivity.class);
                    startActivity(intentTreatment);

                }
                if (position==4){
                    Intent intentTreatment=new Intent(BreedingActivity.this, HeatActivity.class);
                    startActivity(intentTreatment);

                }

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
    }
}
            });
        }
*/

            }
        });
    }
}