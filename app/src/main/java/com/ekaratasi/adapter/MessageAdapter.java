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

import java.util.List;

/**
 * Created by Cyrus on 4/5/2018.
 */

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {



    private List<MessageListItem> listItems;
    private Context context;


    public MessageAdapter(List<MessageListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_list_item,parent,false);
        return new ViewHolder(v);



    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final MessageListItem listItem=listItems.get(position);
        holder.textViewSender.setText(listItem.getAgent_name());
        holder.textViewText.setText(listItem.getText());
        holder.textViewTime.setText(listItem.getTime());


        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MessageItem_Activity.class);

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
        public TextView textViewSender;
        public TextView textViewTime;
        public ConstraintLayout card;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewText=itemView.findViewById(R.id.textViewText);
            textViewSender= itemView.findViewById(R.id.textViewSender);
            textViewTime= itemView.findViewById(R.id.textViewTime);
            card= itemView.findViewById(R.id.messagedatalist);




        }
    }
}