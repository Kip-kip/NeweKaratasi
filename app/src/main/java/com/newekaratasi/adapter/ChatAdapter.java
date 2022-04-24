package com.newekaratasi.adapter;

import android.content.Context;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.newekaratasi.R;
import com.newekaratasi.model.ChatListItem;

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




if(listItem.getSender().equals("CUST")){
    holder.textViewTextCust.setText(listItem.getText());
    String date=listItem.getTime();
    //shorten date
    holder.Time2.setText(date.substring(0,date.length()-3));
    holder.agent.setVisibility(View.GONE);

}
else{
    holder.textViewTextAgent.setText(listItem.getText());
    String date=listItem.getTime();
    //shorten date
    holder.Time.setText(date.substring(0,date.length()-3));
    holder.cust.setVisibility(View.GONE);
}



    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewTextAgent;
        public TextView textViewTextCust;
        public TextView Time;
        public TextView Time2;


        public LinearLayout agent;
        public LinearLayout cust;
        //public TextView textViewTime;

        public ViewHolder(View itemView) {
            super(itemView);


            textViewTextAgent=itemView.findViewById(R.id.textViewTextAgent);
            textViewTextCust= itemView.findViewById(R.id.textViewTextCust);

            agent= itemView.findViewById(R.id.agent);
            cust= itemView.findViewById(R.id.customer);
            Time=itemView.findViewById(R.id.datetime);
            Time2=itemView.findViewById(R.id.datetime2);





        }
    }
}