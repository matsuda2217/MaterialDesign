package com.example.tt.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tt.myapplication.Information;
import com.example.tt.myapplication.MainActivity;
import com.example.tt.myapplication.R;

import java.util.Collections;
import java.util.List;

/**
 * Created by TT
 */
public class AdapterDrawer extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
   private List<Information> data = Collections.emptyList();
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private LayoutInflater layoutInflater;
    private Context context;

    public AdapterDrawer(Context context,List<Information> data) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_HEADER) {
            View v = layoutInflater.inflate(R.layout.drawer_header, parent, false);
            MyViewHeaderHolder holder = new MyViewHeaderHolder(v);
            return holder;
        }else {
            View v = layoutInflater.inflate(R.layout.custom_row, parent, false);
            MyViewHolder holder = new MyViewHolder(v);
            return holder;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder,final int position) {
        if (holder instanceof MyViewHeaderHolder) {

        } else {
            MyViewHolder itemHolder = (MyViewHolder) holder;
            Information currData = data.get(position-1);
            itemHolder.title.setText(currData.title);
            itemHolder.icon.setImageResource(currData.iconId);
        }
    }

    @Override
    public int getItemCount() {
        return data.size()+1;
    }

    public class MyViewHeaderHolder extends RecyclerView.ViewHolder {

        public MyViewHeaderHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView icon;
        public MyViewHolder(View itemView) {
            super(itemView);

            icon =(ImageView) itemView.findViewById(R.id.icon);
            title = (TextView) itemView.findViewById(R.id.textView);
            //  icon.setOnClickListener(this);

        }
    }
}
