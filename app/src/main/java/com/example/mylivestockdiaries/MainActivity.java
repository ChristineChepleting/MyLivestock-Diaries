package com.example.mylivestockdiaries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;

import com.example.mylivestockdiaries.activities.BreedingActivity;
import com.example.mylivestockdiaries.activities.CattleActivity;
import com.example.mylivestockdiaries.activities.CattleAdd;
import com.example.mylivestockdiaries.activities.EventsActivity;
import com.example.mylivestockdiaries.activities.FeedFormulation;
import com.example.mylivestockdiaries.activities.Finance;
import com.example.mylivestockdiaries.activities.MilkRecordActivity;
import com.example.mylivestockdiaries.activities.TasksActivity;
import com.example.mylivestockdiaries.adapters.MainAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private Toolbar toolbar;
    private GridView gridView;

     String[] data={"Cattle","MilkRecords","Breeding","Feed formulation","Events/Tasks","Finance"};
    int[]pictures={R.drawable.cowimage,R.drawable.jellicans,R.drawable.insem,R.drawable.feedings,R.drawable.farmevents,R.drawable.finance};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        firebaseAuth=FirebaseAuth.getInstance();
        toolbar=findViewById(R.id.mainToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        gridView=findViewById(R.id.mainGridView);


        MainAdapter adapter=new MainAdapter(MainActivity.this,data,pictures);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (position==0){
                    Intent intent=new Intent(MainActivity.this,CattleActivity.class);
                    startActivity(intent);


                }
                else if (position==1){
                    Intent intent=new Intent(MainActivity.this, MilkRecordActivity.class);
                    startActivity(intent);

                }
                else if (position==2){
                    Intent intent=new Intent(MainActivity.this, BreedingActivity.class);
                    startActivity(intent);

                }
                else if (position==3){
                    Intent intent=new Intent(MainActivity.this, FeedTrialActivity.class);
                    startActivity(intent);

                }
                else if (position==4){
                    Intent intent=new Intent(MainActivity.this, EventsActivity.class);
                    startActivity(intent);
                }
                else if (position==5){
                    Intent intent=new Intent(MainActivity.this, Finance.class);
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        if (currentUser==null){

            Intent loginIntent=new Intent(MainActivity.this,LoginActivity.class);
            startActivity(loginIntent);
            finish();
        }
    }
     private void Logout(){
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(MainActivity.this,LoginActivity.class));
     }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menubar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.logoutMenu:firebaseAuth.signOut();
            finish();
            startActivity(new Intent());
        }
        return super.onOptionsItemSelected(item);
    }
}