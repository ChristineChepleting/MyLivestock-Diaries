package com.example.mylivestockdiaries.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.mylivestockdiaries.LoginActivity;
import com.example.mylivestockdiaries.R;
import com.example.mylivestockdiaries.RegisterActivity;
import com.example.mylivestockdiaries.adapters.HolderAdapter;
import com.example.mylivestockdiaries.models.DataModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CattleActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;
    private FirebaseRecyclerOptions<DataModel>options;
    private FirebaseRecyclerAdapter<DataModel, HolderAdapter>adapter;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private  String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cattle);
        toolbar=findViewById(R.id.cattleToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Cattle");

        firebaseAuth=FirebaseAuth.getInstance();
        currentUser=firebaseAuth.getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("CattleDetail").child(currentUser);

        recyclerView=findViewById(R.id.cattlesRecycler);
        floatingActionButton=findViewById(R.id.cattleFloat);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setStackFromEnd(true);
        linearLayoutManager.setReverseLayout(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cattleIntent=new Intent(CattleActivity.this, CattleAdd.class);
                cattleIntent.putExtra("title","Add New Cattle");
                cattleIntent.putExtra("key","null");
                startActivity(cattleIntent);
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        });

        displayCattles();


    }

    private void displayCattles() {
        options=new FirebaseRecyclerOptions.Builder<DataModel>().setQuery(databaseReference,DataModel.class).build();
        adapter=new FirebaseRecyclerAdapter<DataModel, HolderAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull HolderAdapter holder, int position, @NonNull DataModel model) {
                final String postKey=getRef(position).getKey();
                holder.textViewTag.setText(model.getTagNumber());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intentmove = new Intent(CattleActivity.this, CattleDetails.class);
                        intentmove.putExtra("key", postKey);
                        startActivity(intentmove);
                        Toast.makeText(CattleActivity.this, postKey, Toast.LENGTH_SHORT).show();

                    }
                });

                holder.imageViewFormat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popupMenu=new PopupMenu(CattleActivity.this,holder.imageViewFormat);
                        popupMenu.getMenuInflater().inflate(R.menu.pop_up,popupMenu.getMenu());
                        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            @Override
                            public boolean onMenuItemClick(MenuItem item) {
                                switch (item.getItemId()){
                                    case R.id.edit_record:
                                        addRecord(postKey);
                                        break;

                                    case R.id.delete_record:
                                        deleteRecord(postKey);
                                        break;
                                }
                                return true;
                            }
                        });
                        popupMenu.show();
                    }
                });


            }



            @NonNull
            @Override
            public HolderAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.cattle_design,parent,false);
                return new HolderAdapter(view);
            }
        };

        adapter.startListening();
        recyclerView.setAdapter(adapter);



    }

    private void deleteRecord(String postKey) {
        Toast.makeText(this, postKey, Toast.LENGTH_SHORT).show();
        databaseReference.child(postKey).removeValue();

    }

    private void addRecord(String postKey) {
        Intent intent=new Intent(CattleActivity.this, CattleAdd.class);
        intent.putExtra("title","Edit Cattle record");
        intent.putExtra("key",postKey);
        startActivity(intent);

    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
    }
}