package com.newekaratasi.adapter;

import android.content.Context;
import android.content.Intent;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.newekaratasi.R;
import com.newekaratasi.activities.NotificationItem_Activity;
import com.newekaratasi.model.NotificationListItem;

import java.util.List;

/**
 * Created by Cyrus on 4/5/2018.
 */

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {



    private List<NotificationListItem> listItems;
    private Context context;


    public NotificationAdapter(List<NotificationListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_list_item,parent,false);
        return new ViewHolder(v);



    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final NotificationListItem listItem=listItems.get(position);
        holder.textViewAgent.setText(listItem.getAgent_name());
        holder.textViewText.setText(listItem.getMessage());

        String date=listItem.getTime();
        //shorten date
        holder.textViewTime.setText(date.substring(0,date.length()-3));



        //DETERMINE STATUS BUTTONS
        if(listItem.getStatus().equals("Unread")) {
            holder.btnunread.setVisibility(View.VISIBLE);
        }
        else{

        }


        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, NotificationItem_Activity.class);

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

        public TextView textViewText;
        public TextView textViewAgent;
        public TextView textViewTime;
        public ConstraintLayout card;
        public Button btnread,btnunread;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewText=itemView.findViewById(R.id.textViewText);
            textViewAgent= itemView.findViewById(R.id.textViewAgent);
            textViewTime= itemView.findViewById(R.id.textViewTime);
            card= itemView.findViewById(R.id.messagedatalist);


            btnunread=itemView.findViewById(R.id.btnUnread);



        }
    }

}