package com.example.mylivestockdiaries;

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

import com.example.mylivestockdiaries.activities.CattleActivity;
import com.example.mylivestockdiaries.activities.CattleAdd;
import com.example.mylivestockdiaries.activities.MilkAdd;
import com.example.mylivestockdiaries.activities.MilkRecordActivity;
import com.example.mylivestockdiaries.activities.NewAIRecord;
import com.example.mylivestockdiaries.adapters.AIAdapter;
import com.example.mylivestockdiaries.adapters.MilkAdapter;
import com.example.mylivestockdiaries.models.AIModel;
import com.example.mylivestockdiaries.models.MilkModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class Artificialinsemination extends AppCompatActivity {
    private Toolbar toolbar;
    private FloatingActionButton floatingActionButton;
    private RecyclerView recyclerView;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private String currentUserId;
    private FirebaseRecyclerOptions<AIModel>options;
    private FirebaseRecyclerAdapter<AIModel, AIAdapter> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artificialinsemination);
        toolbar=findViewById(R.id.artificialtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("AI records");
        firebaseAuth=FirebaseAuth.getInstance();
        currentUserId=firebaseAuth.getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("AIrecords").child(currentUserId);
        floatingActionButton=findViewById(R.id.artificialFloat);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent artificialIntent=new Intent(Artificialinsemination.this, NewAIRecord.class);
//                cattleIntent.putExtra("title","Add New Cattle");
//                cattleIntent.putExtra("key","null");
                startActivity(artificialIntent);
                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            }
        });
        recyclerView=findViewById(R.id.airecordRecycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        displayItems();

    }
    private void displayItems() {
        options=new FirebaseRecyclerOptions.Builder<AIModel>().setQuery(databaseReference,AIModel.class).build();
        adapter=new FirebaseRecyclerAdapter<AIModel, AIAdapter>(options) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull AIAdapter holder, int position, @NonNull @NotNull AIModel model) {
                final String postKey=getRef(position).getKey();
                holder.textViewTag.setText(model.getTag());
                holder.textViewdate.setText(model.getDatefbreeding());
                holder.imageViewEdit.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View v) {
                        PopupMenu popupMenu=new PopupMenu(Artificialinsemination.this,holder.imageViewEdit);
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
            public AIAdapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.ai_layout,parent,false);
                return new AIAdapter(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
        }
    private void deleteRecord(String postKey) {
        databaseReference.child(postKey).removeValue();

    }
    private void addRecord(String postKey) {
        Intent intent=new Intent(Artificialinsemination.this, NewAIRecord.class);
        intent.putExtra("title","Edit AI record");
        intent.putExtra("key",postKey);
        startActivity(intent);

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
    }


}