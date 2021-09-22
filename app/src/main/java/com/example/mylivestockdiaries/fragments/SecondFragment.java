package com.example.mylivestockdiaries.fragments;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylivestockdiaries.R;
import com.example.mylivestockdiaries.activities.ExpenseActivity;
import com.example.mylivestockdiaries.activities.IncomeActivity;
import com.example.mylivestockdiaries.adapters.ExpenseAdapter;
import com.example.mylivestockdiaries.adapters.IncomeAdapter;
import com.example.mylivestockdiaries.models.ExpenseModel;
import com.example.mylivestockdiaries.models.IncomeModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class SecondFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String myParam1;
    private String myParam2;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private String currentUserId;
    private FirebaseRecyclerOptions<ExpenseModel> options;
    private FirebaseRecyclerAdapter<ExpenseModel, ExpenseAdapter> adapter;
    private RecyclerView recyclerView;

 public SecondFragment() {
//        //Required empty public constructor
}
//
public static SecondFragment newInstance(String param1, String param2) {
SecondFragment fragment = new SecondFragment();
Bundle args = new Bundle();
args.putString(ARG_PARAM1, param1);
args.putString(ARG_PARAM2, param2);
fragment.setArguments(args);
return fragment;
}
@Override
public void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
if (getArguments() != null) {
myParam1 = getArguments().getString(ARG_PARAM1);
myParam2 = getArguments().getString(ARG_PARAM2);
    }
 }
 @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
  // Inflate the layout for this fragment
   View view = inflater.inflate(R.layout.fragment_second, container, false);
     //firebase initialization
  firebaseAuth = FirebaseAuth.getInstance();
  currentUserId = firebaseAuth.getCurrentUser().getUid();
  databaseReference = FirebaseDatabase.getInstance().getReference().child("Expense").child(currentUserId);

    recyclerView = view.findViewById(R.id.expenseRecordRecycler);
    recyclerView.setHasFixedSize(true);
    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
    linearLayoutManager.setReverseLayout(true);
    linearLayoutManager.setStackFromEnd(true);
    recyclerView.setLayoutManager(linearLayoutManager);
    displayItems();
    return view;
  }
  private void displayItems() {
      options = new FirebaseRecyclerOptions.Builder<ExpenseModel>().setQuery(databaseReference, ExpenseModel.class).build();
      adapter = new FirebaseRecyclerAdapter<ExpenseModel, ExpenseAdapter>(options) {
          @Override
          protected void onBindViewHolder(@NonNull @NotNull ExpenseAdapter holder, int position, @NonNull @NotNull ExpenseModel model) {
              final String postKey=getRef(position).getKey();
              holder.ExpenseName.setText(model.getExpensename());
              holder.textViewdate.setText(model.getDate());
              holder.textHowMuch.setText(model.getHowmuch());
              holder.imageViewEdit.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                      PopupMenu popupMenu = new PopupMenu(getContext(), holder.imageViewEdit);
                      popupMenu.getMenuInflater().inflate(R.menu.pop_up, popupMenu.getMenu());
                      popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                          @Override
                          public boolean onMenuItemClick(MenuItem item) {
                              switch (item.getItemId()) {
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
          public ExpenseAdapter onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
              View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.expense_layout, parent, false);
              return new ExpenseAdapter(view);
          }
      };
      adapter.startListening();
      recyclerView.setAdapter(adapter);
  }
  private void deleteRecord(String postkey){
//      databaseReference.child(postKey).removeValue();
  }
  private void addRecord(String postkey){
//      Intent intent=new Intent(getContext(), ExpenseActivity.class);
//      intent.putExtra("title","Edit Income ");
//      intent.putExtra("key",postKey);
//      startActivity(intent);
  }

}



