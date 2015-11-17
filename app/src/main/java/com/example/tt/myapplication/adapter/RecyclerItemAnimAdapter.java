package com.example.tt.myapplication.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.tt.myapplication.R;
import com.example.tt.myapplication.anim.AnimationUtils;

import java.util.ArrayList;

/**
 * Created by TT
 */
public class RecyclerItemAnimAdapter extends RecyclerView.Adapter<RecyclerItemAnimAdapter.MyRecyclerItemViewHolder> {
    public ArrayList<String> mList = new ArrayList<>();
    public LayoutInflater mLayoutInflater;
    private int prePos =0;
    public RecyclerItemAnimAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public MyRecyclerItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = mLayoutInflater.inflate(R.layout.custom_row_item_animation, parent, false);
        MyRecyclerItemViewHolder holder = new MyRecyclerItemViewHolder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyRecyclerItemViewHolder holder, final int position) {
        String data = mList.get(position);
        holder.textItem.setText(data);
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeItem(position);
            }
        });
        if (position > prePos) {
            AnimationUtils.animate(holder, true);
        } else {
            AnimationUtils.animate(holder, false);
        }
        prePos = position;

    }

    public void addItem(String item) {
        mList.add(item);
       notifyItemInserted(mList.size());
    }
    public void removeItem(String item) {
        int pos = mList.indexOf(item);
        if (pos != -1) {
            mList.remove(item);
            notifyItemRemoved(pos);
        }
    }

    public void removeItem(int pos) {
        mList.remove(pos);
        notifyItemRemoved(pos);
    }
    @Override
    public int getItemCount() {
        if (mList.size()>=1) {
            return mList.size();
        }else
            return 0;

    }

    public static class MyRecyclerItemViewHolder extends RecyclerView.ViewHolder{
        TextView textItem;
        ImageButton btnDelete;
        public MyRecyclerItemViewHolder(View itemView) {
            super(itemView);
            textItem = (TextView) itemView.findViewById(R.id.text_item);
            btnDelete = (ImageButton) itemView.findViewById(R.id.btn_delete);

        }
    }
}
