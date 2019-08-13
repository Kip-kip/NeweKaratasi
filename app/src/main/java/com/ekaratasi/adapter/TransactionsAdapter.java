package com.ekaratasi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ekaratasi.R;
import com.ekaratasi.activities.TransactionItem_Activity;
import com.ekaratasi.model.ListItem;

import java.util.List;

/**
 * Created by Cyrus on 4/5/2018.
 */

public class TransactionsAdapter extends RecyclerView.Adapter<TransactionsAdapter.ViewHolder> {



    private List<ListItem> listItems;
    private Context context;


    public TransactionsAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item,parent,false);
        return new ViewHolder(v);



    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ListItem listItem=listItems.get(position);
        holder.textViewName.setText(listItem.getAgent());
        holder.textViewPayment.setText(listItem.getPayment_status());
        holder.textViewTotalCost.setText("KES " +listItem.getTotal_cost());
        holder.textViewTime.setText(listItem.getTime_stamp());
        //DETERMINE STATUS BUTTONS
        if(listItem.getProgress().equals("Unseen")){
            holder.btnunseen.setVisibility(View.VISIBLE);
        }
        else if(listItem.getProgress().equals("Pending")) {
            holder.btnpending.setVisibility(View.VISIBLE);
        }
        else if(listItem.getProgress().equals("Completed")) {
            holder.btncompleted.setVisibility(View.VISIBLE);
        }
        else{

        }



        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, TransactionItem_Activity.class);

                intent.putExtra("DETAIL",listItem);

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewName;
        public TextView textViewPayment;
        public TextView textViewTotalCost;
        public TextView textViewTime;
        Button btnpending,btncompleted,btnunseen;
        public ConstraintLayout card;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName=itemView.findViewById(R.id.textViewName);
            textViewPayment= itemView.findViewById(R.id.textViewPayment);
            textViewTime= itemView.findViewById(R.id.textViewTime);
            textViewTotalCost=itemView.findViewById(R.id.textViewTotalCost);

            btncompleted=itemView.findViewById(R.id.btnCompleted);
            btnpending=itemView.findViewById(R.id.btnPending);
            btnunseen=itemView.findViewById(R.id.btnUnseen);

            card= itemView.findViewById(R.id.datalist);




        }
    }
}