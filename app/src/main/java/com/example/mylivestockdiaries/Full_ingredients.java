package com.example.mylivestockdiaries;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylivestockdiaries.adapters.DataHoldetr;
import com.example.mylivestockdiaries.models.DataRetrive;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Full_ingredients extends AppCompatActivity {
    private Toolbar toolbar;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    String current_user_id;
    private RecyclerView recyclerView;
    private FirebaseRecyclerOptions<DataRetrive>options;
    private FirebaseRecyclerAdapter<DataRetrive, DataHoldetr>adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_ingredients);
        toolbar = findViewById(R.id.ingredientstoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Full ingredients per 100kg");
        recyclerView=findViewById(R.id.fullIngridients);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayout=new LinearLayoutManager(this);
        linearLayout.setStackFromEnd(true);
        linearLayout.setReverseLayout(true);
        recyclerView.setHasFixedSize(true);

        firebaseAuth=FirebaseAuth.getInstance();
        current_user_id=firebaseAuth.getCurrentUser().getUid();
        Calendar calForDate=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("dd-MMMM-yyyy");
        String date=currentDate.format(calForDate.getTime());
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Feeds").child(current_user_id);

        displayData();

    }

    private void displayData() {
        options=new FirebaseRecyclerOptions.Builder<DataRetrive>().setQuery(databaseReference,DataRetrive.class).build();
        adapter=new FirebaseRecyclerAdapter<DataRetrive, DataHoldetr>(options) {
            @Override
            protected void onBindViewHolder(@NonNull @NotNull DataHoldetr holder, int position, @NonNull @NotNull DataRetrive model) {
                holder.textViewDetailed.setText(model.getFish_price());
                Toast.makeText(Full_ingredients.this, model.getMagnesium_price(), Toast.LENGTH_LONG).show();
            }

            @NonNull
            @NotNull
            @Override
            public DataHoldetr onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.datas_layout,parent,false);

                return new DataHoldetr(view);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);

    }


}