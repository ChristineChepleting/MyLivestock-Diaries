package com.example.mylivestockdiaries.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mylivestockdiaries.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class PricesAdapter extends RecyclerView.Adapter<PricesAdapter.ViewHolder> {
    ArrayList<String> mData;
    public PricesAdapter(ArrayList<String> Data) {
        mData = Data;

    }
    private OnItemClickListener mListener;
    public interface OnItemClickListener{
        void OnItemClick(int position);
}
public void setOnItemClickListener(OnItemClickListener listener){
        mListener=listener;
}
    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pricecardview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull PricesAdapter.ViewHolder holder, int position) {
        holder.feedNameTextView.setText(mData.get(position));
        //holder.feedPriceTextview.setText(mData.get(position));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView feedNameTextView;
        public TextView feedPriceTextview;
        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            feedNameTextView = (TextView) itemView.findViewById(R.id.feed_name);
            feedPriceTextview = (TextView) itemView.findViewById(R.id.feed_price);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
