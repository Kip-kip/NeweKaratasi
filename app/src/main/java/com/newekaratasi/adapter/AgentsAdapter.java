package com.newekaratasi.adapter;

import android.content.Context;
import android.content.Intent;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newekaratasi.R;
import com.newekaratasi.activities.Agent_Details_Activity;
import com.newekaratasi.model.AgentListItem;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Cyrus on 4/5/2018.
 */

public class AgentsAdapter extends RecyclerView.Adapter<AgentsAdapter.ViewHolder> {



    private List<AgentListItem> listItems;
    private Context context;


    public AgentsAdapter(List<AgentListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.agents_list_item,parent,false);
        return new ViewHolder(v);



    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final AgentListItem listItem=listItems.get(position);
        holder.textViewName.setText(listItem.getAgent_name()+" - "+listItem.getAgent_refno());
        holder.textViewPhone.setText(listItem.getLocation());
        Picasso.with(context)
                .load(listItem.getImage())
                .resize(635,415)
                .into(holder.imageViewImage);


        holder.cardd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Agent_Details_Activity.class);

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
        public TextView textViewPhone;

        public ImageView imageViewImage;
        public LinearLayout cardd;
        public ViewHolder(View itemView) {
            super(itemView);

            textViewName=itemView.findViewById(R.id.textViewAgentName);
            textViewPhone= itemView.findViewById(R.id.textViewPhone);
            imageViewImage= itemView.findViewById(R.id.imageViewImage);

            cardd= itemView.findViewById(R.id.cardd);





        }
    }
}