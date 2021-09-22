package com.example.mylivestockdiaries.adapters;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylivestockdiaries.R;

import org.jetbrains.annotations.NotNull;

public class DataHoldetr extends RecyclerView.ViewHolder {
    public TextView textViewDetailed;
    public DataHoldetr(@NonNull @NotNull View itemView) {
        super(itemView);
        textViewDetailed=itemView.findViewById(R.id.datasHolder);

    }
}
