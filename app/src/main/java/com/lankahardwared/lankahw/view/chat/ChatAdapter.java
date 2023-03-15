package com.lankahardwared.lankahw.view.chat;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;

import java.util.List;

/**
 * Created by Sathiyaraja on 12/15/2017.
 */

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MyViewHolder> {

    private List<ChatMessage> chatList;
    private Context mcontext;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView user, message, time;
        public LinearLayout Chatbaloon;

        public MyViewHolder(View view) {
            super(view);
            user = (TextView) view.findViewById(R.id.message_user);
            message = (TextView) view.findViewById(R.id.message_text);
            Chatbaloon = (LinearLayout) view.findViewById(R.id.Chatbaloon);

        }
    }

    public ChatAdapter(List<ChatMessage> messageList, Context context) {
        this.chatList = messageList;
        this.mcontext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_balon, parent, false);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 0, 0, 5);


        itemView.setLayoutParams(layoutParams);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final ChatMessage chatMessage = chatList.get(position);

        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp2.weight = 1.0f;

        if (chatMessage.getStatus() == 1) {
            holder.Chatbaloon.setGravity(Gravity.RIGHT);
        }else{
            holder.Chatbaloon.setGravity(Gravity.LEFT);
        }

        holder.user.setText(chatMessage.getMessageUser());
        holder.message.setText(chatMessage.getMessageText());


    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }
}
