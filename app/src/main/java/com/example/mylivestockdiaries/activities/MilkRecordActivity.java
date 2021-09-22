package com.example.mylivestockdiaries.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.mylivestockdiaries.MainActivity;
import com.example.mylivestockdiaries.R;
import com.example.mylivestockdiaries.adapters.MilkAdapter;
import com.example.mylivestockdiaries.models.DataModel;
import com.example.mylivestockdiaries.models.MilkModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class MilkRecordActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private String currentUserId;
    private FirebaseRecyclerOptions<MilkModel>options;
    private FirebaseRecyclerAdapter<MilkModel, MilkAdapter>adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milk_record);
        toolbar=findViewById(R.id.milkToolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Milk Records");

        //firebase initialization
        firebaseAuth=FirebaseAuth.getInstance();
        currentUserId=firebaseAuth.getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("MilkRecords").child(currentUserId);


        floatingActionButton=findViewById(R.id.milkRecordFloating);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MilkRecordActivity.this, MilkAdd.class);
                intent.putExtra("title","New milk record");
                intent.putExtra("key","null");
                startActivity(intent);
            }
        });
        recyclerView=findViewById(R.id.milkRecordRecycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        displayItems();

    }

    private void displayItems() {
        options=new FirebaseRecyclerOptions.Builder<MilkModel>().setQuery(databaseReference,MilkModel.class).build();
        adapter=new FirebaseRecyclerAdapter<MilkModel, MilkAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull MilkAdapter holder, int position, @NonNull @NotNull MilkModel model) {
                final String postKey=getRef(position).getKey();
                holder.textViewTag.setText(model.getTagNumber());
                holder.textViewdate.setText(model.getDate());
                holder.textViewtotal.setText(model.getTotalProduced());
                holder.textViewused.setText(model.getConsumed());
                holder.textViewSold.setText(model.getSold());
                holder.imageViewEdit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PopupMenu popupMenu=new PopupMenu(MilkRecordActivity.this,holder.imageViewEdit);
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
            @NotNull
            @Override
            public MilkAdapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.milk_layout,parent,false);
                return new MilkAdapter(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    private void deleteRecord(String postKey) {
     databaseReference.child(postKey).removeValue();

    }

    private void addRecord(String postKey) {
        Intent intent=new Intent(MilkRecordActivity.this, MilkAdd.class);
        intent.putExtra("title","Edit milk record");
        intent.putExtra("key",postKey);
        startActivity(intent);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
    }
}