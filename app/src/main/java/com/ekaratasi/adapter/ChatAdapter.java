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
import com.ekaratasi.model.ChatListItem;
import com.ekaratasi.model.MessageListItem;

import java.util.List;

/**
 * Created by Cyrus on 4/5/2018.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ViewHolder> {



    private List<ChatListItem> listItems;
    private Context context;


    public ChatAdapter(List<ChatListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_list_item,parent,false);
        return new ViewHolder(v);



    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final ChatListItem listItem=listItems.get(position);


       // holder.textViewTime.setText(listItem.getTime());

//if(listItem.getReceiver().equals("AGE")){
    holder.textViewTextAgent.setText(listItem.getSender());
//}
//else{
    //holder.textViewTextCust.setText(listItem.getSender());
//}



    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewTextAgent;
        public TextView textViewTextCust;
        //public TextView textViewTime;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewTextAgent=itemView.findViewById(R.id.textViewTextAgent);
            textViewTextCust= itemView.findViewById(R.id.textViewTextCust);
           // textViewTime= itemView.findViewById(R.id.textViewTime);





        }
    }
}