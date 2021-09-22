package com.example.mylivestockdiaries.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.mylivestockdiaries.R;

import java.util.Objects;

import static android.R.layout.simple_list_item_1;

public class EventsActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ListView listView;
    String[] events={"Treatment","Vaccine","Dewormer","Edit event","Remove event"};
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);
        toolbar=findViewById(R.id.eventstoolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Events");
        listView=findViewById(R.id.eventListView);
        adapter=new ArrayAdapter<String>(this, simple_list_item_1,events);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position==0){
                    Intent intentTreatment=new Intent(EventsActivity.this,Treatment.class);
                    startActivity(intentTreatment);

                }
                if (position==1){
                    Intent intentVaccine=new Intent(EventsActivity.this,Vaccine.class);
                    startActivity(intentVaccine);

                }
                if (position==2){
                    Intent intentDewormer=new Intent(EventsActivity.this,Dewormer.class);
                    startActivity(intentDewormer);

                }
                if (position==3){
                    Intent intentEdit=new Intent(EventsActivity.this,EventsActivity.class);
                    startActivity(intentEdit);

                }
                if (position==4){
                    Intent intentRemove=new Intent(EventsActivity.this,RemoveActivity.class);
                    startActivity(intentRemove);

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