package com.example.mylivestockdiaries.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylivestockdiaries.R;

public class MilkAdapter extends RecyclerView.ViewHolder {
    public TextView textViewTag,textViewdate,textViewtotal,textViewused,textViewSold;
    public ImageView imageViewEdit;
    public MilkAdapter(@NonNull View itemView) {
        super(itemView);
        textViewTag=itemView.findViewById(R.id.milkTag);
        textViewdate=itemView.findViewById(R.id.milkDate);
        textViewtotal=itemView.findViewById(R.id.milkProduced);
        textViewused=itemView.findViewById(R.id.milkConsumed);
        textViewSold=itemView.findViewById(R.id.milkSold);
        imageViewEdit=itemView.findViewById(R.id.editting);
    }
}
