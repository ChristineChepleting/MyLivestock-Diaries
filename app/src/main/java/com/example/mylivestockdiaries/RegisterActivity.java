package com.example.mylivestockdiaries;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextFirstname,editTextLastname,editTextEmail,editTextPassword,editTextconPassword;
    private Button buttonRegister;
    private TextView textViewSignIn,textViewSignIn2;
    private ProgressDialog progressDialog;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth=FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Farmers");

        editTextconPassword=findViewById(R.id.registerConfirmPassword);
        editTextFirstname=findViewById(R.id.registerFirstname);
        editTextLastname=findViewById(R.id.registerLastname);
        editTextEmail=findViewById(R.id.registerEmail);
        editTextPassword=findViewById(R.id.registerPassword);

        buttonRegister=findViewById(R.id.registerButton);
        textViewSignIn=findViewById(R.id.registerSignIn);
        textViewSignIn2=findViewById(R.id.adminSignIn);
        textViewSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(registerIntent);
                overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
            }
        });

            buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname=editTextFirstname.getText().toString();
                String lastname=editTextLastname.getText().toString();
                String email=editTextEmail.getText().toString();
                String password=editTextPassword.getText().toString();
                String conPassword=editTextconPassword.getText().toString();

                if (firstname.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "please enter your firstname", Toast.LENGTH_SHORT).show();
                }
                else if (lastname.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "please enter your lastname", Toast.LENGTH_SHORT).show();
                }
                else if (email.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "please enter your email address", Toast.LENGTH_SHORT).show();
                }
                else if (password.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "please enter your password", Toast.LENGTH_SHORT).show();
                }
                else if (conPassword.isEmpty()){
                    Toast.makeText(RegisterActivity.this, "please confirm password", Toast.LENGTH_SHORT).show();
                }
                else if (!password.equals(conPassword)){
                    Toast.makeText(RegisterActivity.this, "password mismatched", Toast.LENGTH_SHORT).show();
                }
                else {
                    progressDialog.setTitle("Creating your Account");
                    progressDialog.setMessage("Please wait while we are creating your Account");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    firebaseAuth.createUserWithEmailAndPassword(email,password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                String currentUserId=firebaseAuth.getCurrentUser().getUid();
                                HashMap hashMap=new HashMap();
                                hashMap.put("firstname",firstname);
                                hashMap.put("lastname",lastname);
                                hashMap.put("email",email);
                                databaseReference.child(currentUserId).updateChildren(hashMap).addOnCompleteListener(new OnCompleteListener() {
                                    @Override
                                    public void onComplete(@NonNull Task task) {
                                        if (task.isSuccessful()){
                                            Toast.makeText(RegisterActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                                            Intent loged=new Intent(RegisterActivity.this,MainActivity.class);
                                            startActivity(loged);
                                            finish();
                                            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                                            progressDialog.dismiss();
                                        }
                                        else{
                                            String error=task.getException().getMessage().toString();
                                            Toast.makeText(RegisterActivity.this, "Error occured:"+error, Toast.LENGTH_SHORT).show();
                                            progressDialog.dismiss();
                                        }

                                        }
                                });

                            }
                            else{
                                String error=task.getException().getMessage().toString();
                                Toast.makeText(RegisterActivity.this, "Error occured:"+error, Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                        }
                    });

                }
            }
        });
            textViewSignIn2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent registerIntent=new Intent(RegisterActivity.this,AdminDashboard.class);
                    startActivity(registerIntent);
                    overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                }
            });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
    }
}