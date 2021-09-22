package com.example.mylivestockdiaries.adapters;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylivestockdiaries.R;

public class HolderAdapter extends RecyclerView.ViewHolder {
    public TextView textViewTag;
    public ImageView imageViewFormat;
    public HolderAdapter(@NonNull View itemView) {
        super(itemView);
        textViewTag=itemView.findViewById(R.id.cattleLayoutTag);
        imageViewFormat=itemView.findViewById(R.id.edittingCow);
    }
}
