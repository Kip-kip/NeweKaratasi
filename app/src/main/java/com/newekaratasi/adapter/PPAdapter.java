package com.newekaratasi.adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.newekaratasi.R;
import com.newekaratasi.model.PPItem;

import java.util.List;

/**
 * Created by Cyrus on 4/5/2018.
 */

public class PPAdapter extends RecyclerView.Adapter<PPAdapter.ViewHolder> {



    private List<PPItem> listItems;
    private Context context;


    public PPAdapter(List<PPItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main,parent,false);
        return new ViewHolder(v);



    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final PPItem listItem=listItems.get(position);


    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView aa;


        public ViewHolder(View itemView) {
            super(itemView);





        }
    }
}