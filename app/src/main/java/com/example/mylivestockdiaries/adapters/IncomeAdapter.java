package com.example.mylivestockdiaries.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylivestockdiaries.R;

public class IncomeAdapter extends RecyclerView.ViewHolder {
    public TextView incomeName, textViewdate, textAmount;
    public ImageView imageViewEdit;

    public IncomeAdapter(@NonNull View itemView) {
        super(itemView);
        incomeName = itemView.findViewById(R.id.incomeNames);
        textViewdate = itemView.findViewById(R.id.incomeDate);
        textAmount = itemView.findViewById(R.id.incomeCash);
        imageViewEdit = itemView.findViewById(R.id.incomeTouch);
    }
}
