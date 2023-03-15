package com.lankahardwared.lankahw.view.chat;

import android.content.Context;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.control.UtilityContainer;

import java.util.List;

/**
 * Created by Sathiyaraja on 12/15/2017.
 */

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> {

    private List<objUser> userList;
    private Context mcontext;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, position;
        public LinearLayout container;

        public MyViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.uName);
            position = (TextView) view.findViewById(R.id.uPosition);
            container = (LinearLayout) view.findViewById(R.id.container);

        }

    }


    public UserAdapter(List<objUser> messageList, Context context) {
        this.userList = messageList;
        this.mcontext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.chat_user_row, parent, false);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        layoutParams.setMargins(0, 0, 0, 1);

        itemView.setLayoutParams(layoutParams);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final objUser user = userList.get(position);

        holder.name.setText(user.getUser());
        holder.position.setText(user.getPosition());


        holder.position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // Toast.makeText(mcontext, user.getUser(), Toast.LENGTH_SHORT).show();

                ChatView chatView = new ChatView();
                Bundle bundle = new Bundle();
                bundle.putBoolean("Active", true);
                bundle.putString("chatWith", user.getUser());
                chatView.setArguments(bundle);
                UtilityContainer.mLoadFragment(chatView, mcontext);

            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }
}
