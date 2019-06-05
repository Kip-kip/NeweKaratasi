package com.ekaratasi.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ekaratasi.R;
import com.ekaratasi.activities.TransactionItem_Activity;
import com.ekaratasi.model.ListItem;

import java.util.List;

/**
 * Created by Cyrus on 4/5/2018.
 */

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {



    private List<ListItem> listItems;
    private Context context;


    public MainAdapter(List<ListItem> listItems, Context context) {
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
        holder.textViewPrice.setText("KES " +listItem.getProgress());


        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
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
        public TextView textViewPrice;
        public LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName=itemView.findViewById(R.id.textViewName);
            textViewPrice= itemView.findViewById(R.id.textViewPrice);





        }
    }
}