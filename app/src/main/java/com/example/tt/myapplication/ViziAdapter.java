package com.example.tt.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Collections;
import java.util.List;

/**
 * Created by TT
 */
public class ViziAdapter extends RecyclerView.Adapter<ViziAdapter.MyViewHolder> {
    private LayoutInflater layoutInflater;
    Context context;
    List<Information> data = Collections.emptyList();
    Clicklistener clicklistener;
    public ViziAdapter(Context context,List<Information>data) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.data = data;
    }

    public void setClicklistener(Clicklistener clicklistener) {
        this.clicklistener = clicklistener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public void delete(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View view = layoutInflater.inflate(R.layout.custom_row,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        Log.d("TTV", "oncreate called");
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        Information currData = data.get(position);
        holder.title.setText(currData.title);
        holder.icon.setImageResource(currData.iconId);
        Log.d("TTV", "onBind called " + position);


    }
    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView title;
        ImageView icon;
        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            icon =(ImageView) itemView.findViewById(R.id.icon);
            title = (TextView) itemView.findViewById(R.id.textView);
          //  icon.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //delete(getPosition());
           // context.startActivity(new Intent(context, SubActivity.class));
            if (clicklistener != null) {
                clicklistener.itemClick(v,getPosition());
            }
           // Toast.makeText(context, "Item clicked at: "+getAdapterPosition(), Toast.LENGTH_SHORT).show();
        }
    }
    public  interface  Clicklistener{
        public void itemClick(View v, int pos);
    }
}
