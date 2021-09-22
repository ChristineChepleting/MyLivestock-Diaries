package com.example.mylivestockdiaries.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylivestockdiaries.R;

public class ExpenseAdapter extends  RecyclerView.ViewHolder {
    public TextView ExpenseName, textViewdate, textHowMuch;
    public ImageView imageViewEdit;
    public ExpenseAdapter(@NonNull View itemView) {
        super(itemView);
        ExpenseName = itemView.findViewById(R.id.expenseNames);
        textViewdate = itemView.findViewById(R.id.expenseDate);
        textHowMuch = itemView.findViewById(R.id.expenseCash);
        imageViewEdit = itemView.findViewById(R.id.expenseTouch);
    }
}
