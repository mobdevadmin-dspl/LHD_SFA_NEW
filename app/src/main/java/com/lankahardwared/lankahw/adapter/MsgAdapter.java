package com.lankahardwared.lankahw.adapter;

import android.app.Dialog;
import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lankahardwared.lankahw.PushNotification.objMessage;
import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.data.MessageDS;

import java.util.List;

/**
 * Created by Sathiyaraja on 12/6/2017.
 */

public class MsgAdapter extends RecyclerView.Adapter<MsgAdapter.MyViewHolder> {

    private List<objMessage> msgList;
    private Context mcontext;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView from, subject, msg, date_time;
        public LinearLayout container;

        public MyViewHolder(View view) {
            super(view);
            from = (TextView) view.findViewById(R.id.from);
            subject = (TextView) view.findViewById(R.id.subject);
            msg = (TextView) view.findViewById(R.id.message);
            date_time = (TextView) view.findViewById(R.id.date_time);
            container = (LinearLayout) view.findViewById(R.id.container);

        }
    }


    public MsgAdapter(List<objMessage> messageList, Context context) {
        this.msgList = messageList;
        this.mcontext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.message_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        final objMessage message = msgList.get(position);

        holder.from.setText(message.getFrom());
        holder.subject.setText(message.getSubject());
        holder.msg.setText(message.getMessage());
        holder.date_time.setText(message.getDate_time());

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog = new Dialog(mcontext);
                dialog.setTitle(message.getSubject());
                dialog.setContentView(R.layout.msg_dialog);

                TextView lblmessage = (TextView) dialog.findViewById(R.id.message);
                lblmessage.setText(message.getMessage());

                ((ViewGroup) dialog.getWindow().getDecorView()).startAnimation(AnimationUtils.loadAnimation(
                        mcontext, R.anim.expand_in));

                dialog.show();

                MessageDS messageDS = new MessageDS(mcontext);
                messageDS.isMsgRed(message.getID());

            }
        });
    }

    @Override
    public int getItemCount() {
        return msgList.size();
    }
}
