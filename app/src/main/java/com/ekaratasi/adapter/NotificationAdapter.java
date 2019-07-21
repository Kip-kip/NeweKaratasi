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
import com.ekaratasi.activities.NotificationItem_Activity;
import com.ekaratasi.activities.Notification_Activity;
import com.ekaratasi.activities.TransactionItem_Activity;
import com.ekaratasi.model.MessageListItem;
import com.ekaratasi.model.NotificationListItem;

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
        holder.textViewAgent.setText(listItem.getAgent());
        holder.textViewText.setText(listItem.getMessage());
        holder.textViewTime.setText(listItem.getTime());


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

        public ViewHolder(View itemView) {
            super(itemView);

            textViewText=itemView.findViewById(R.id.textViewText);
            textViewAgent= itemView.findViewById(R.id.textViewAgent);
            textViewTime= itemView.findViewById(R.id.textViewTime);
            card= itemView.findViewById(R.id.messagedatalist);




        }
    }
}