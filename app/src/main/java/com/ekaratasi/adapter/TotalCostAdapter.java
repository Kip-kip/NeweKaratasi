package com.ekaratasi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ekaratasi.R;
import com.ekaratasi.activities.MessageItem_Activity;
import com.ekaratasi.activities.TransactionItem_Activity;
import com.ekaratasi.model.MessageListItem;
import com.ekaratasi.model.TotalCostItem;
import com.ekaratasi.model.TotalTransactionsItem;

import java.util.List;

/**
 * Created by Cyrus on 4/5/2018.
 */

public class TotalCostAdapter extends RecyclerView.Adapter<TotalCostAdapter.ViewHolder> {



    private List<TotalCostItem> listItems;
    private Context context;


    public TotalCostAdapter(List<TotalCostItem> listItems, Context context) {
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
        final TotalCostItem listItem=listItems.get(position);
        holder.txttotalcost.setText(listItem.getCost());



    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView txttotalcost;


        public ViewHolder(View itemView) {
            super(itemView);

            txttotalcost=itemView.findViewById(R.id.totalcost);




        }
    }
}