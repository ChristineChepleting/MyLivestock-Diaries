package com.example.mylivestockdiaries.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.mylivestockdiaries.R;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainAdapter extends BaseAdapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private  String[]data;
    private int[] images;


    public MainAdapter(Context context, String[] data, int[] images) {
        this.context = context;
        this.data = data;
        this.images = images;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (layoutInflater==null){
            layoutInflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if (convertView==null){
            convertView=layoutInflater.inflate(R.layout.cardview_layout,null);

        }
        CircleImageView circleImageView=convertView.findViewById(R.id.cardImage);
        TextView textView=convertView.findViewById(R.id.cardText);
        circleImageView.setImageResource(images[position]);
        textView.setText(data[position]);
        return convertView;
    }
}
