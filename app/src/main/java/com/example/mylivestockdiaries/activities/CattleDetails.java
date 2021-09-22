package com.example.mylivestockdiaries.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mylivestockdiaries.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CattleDetails extends AppCompatActivity {
    private TextView textViewTag,textViewbreed,textViewAge,textViewGender,textVieweight,textViewdob,textViewJoined,textViewSource,textViewStage;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private String currentUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cattle_details);

        firebaseAuth=FirebaseAuth.getInstance();
        currentUserId=firebaseAuth.getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("CattleDetail").child(currentUserId);
        String data=getIntent().getExtras().get("key").toString();
        textViewTag=findViewById(R.id.detailstagnumber);
        textViewAge=findViewById(R.id.detailstagAge);
        textViewbreed=findViewById(R.id.detailstagBreed);
        textViewGender=findViewById(R.id.detailstagGender);
        textVieweight=findViewById(R.id.detailstagWeight);
        textViewdob=findViewById(R.id.detailstagDob);
        textViewJoined=findViewById(R.id.detailstagJoined);
        textViewSource=findViewById(R.id.detailstagSource);
        textViewStage=findViewById(R.id.detailstagStage);
        showData(data);

    }

    private void showData(String data) {
        databaseReference.child(data).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    String tagNumber=snapshot.child("tagNumber").getValue().toString();
                    String tagbreed=snapshot.child("breedName").getValue().toString();
                    String taggender=snapshot.child("gender").getValue().toString();
                    String tagage=snapshot.child("age").getValue().toString();
                    String tagdob=snapshot.child("dob").getValue().toString();
                    String tagweight=snapshot.child("weight").getValue().toString();
                    String tagjoin=snapshot.child("joinedOn").getValue().toString();
                    String tagstage= snapshot.child("stage").getValue().toString();
                    String tagsource=snapshot.child("source").getValue().toString();

                    textViewAge.setText(tagage);
                    textViewbreed.setText(tagbreed);
                    textViewdob.setText(tagdob);
                    textVieweight.setText(tagweight);
                    textViewGender.setText(taggender);
                    textViewTag.setText(tagNumber);
                    textViewJoined.setText(tagjoin);
                    textViewStage.setText(tagstage);
                    textViewSource.setText(tagsource);
                }
                else{
                    Toast.makeText(getApplicationContext(),"Error occurred",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
    }
}