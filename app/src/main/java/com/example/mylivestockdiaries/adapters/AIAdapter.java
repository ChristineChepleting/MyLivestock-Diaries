package com.example.mylivestockdiaries.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylivestockdiaries.R;

import org.jetbrains.annotations.NotNull;

public class AIAdapter extends RecyclerView.ViewHolder {
    public TextView textViewTag,textViewdate;
    public ImageView imageViewEdit;

    public AIAdapter(@NonNull @NotNull View itemView) {
        super(itemView);
        textViewTag=itemView.findViewById(R.id.TagNo);
        textViewdate=itemView.findViewById(R.id.aiDate);
        imageViewEdit=itemView.findViewById(R.id.aiTouch);
    }
}
