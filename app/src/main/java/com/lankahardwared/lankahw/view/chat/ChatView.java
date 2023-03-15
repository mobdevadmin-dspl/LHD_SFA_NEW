package com.lankahardwared.lankahw.view.chat;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ScrollView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.control.UtilityContainer;
import com.lankahardwared.lankahw.view.IconPallet;
import com.lankahardwared.lankahw.view.MainActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Sathiyaraja on 12/15/2017.
 */

public class ChatView extends Fragment {

    View view;
    private RecyclerView recyclerView;
    private String chatWith;
    private ArrayList<ChatMessage> chatMessages;
    private ScrollView chatScroll;
    private EditText input;
    private ChatAdapter chatAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_chatview, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        getActivity().setTitle(FirebaseAuth.getInstance().getCurrentUser().getEmail());
        toolbar.setLogo(R.drawable.dm_logo_64);
        setHasOptionsMenu(true);

        Bundle bundle = this.getArguments();
        if (bundle != null) {
            chatWith = bundle.getString("chatWith", "na");

        }

        input = (EditText) view.findViewById(R.id.input);

        chatScroll = (ScrollView) view.findViewById(R.id.chatScroll);
        chatMessages = new ArrayList<ChatMessage>();
        recyclerView = (RecyclerView) view.findViewById(R.id.list_of_messages);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        chatAdapter = new ChatAdapter(chatMessages, getActivity());
        recyclerView.setAdapter(chatAdapter);

        //send chat
        Firebase.setAndroidContext(getActivity());
        final Firebase reference1 = new Firebase("https://finacnew.firebaseio.com/messages/");
        view.findViewById(R.id.Btnsend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!input.getText().equals("")) {
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("message", input.getText().toString());
                    map.put("user", FirebaseAuth.getInstance().getCurrentUser().getEmail());
                    map.put("chatWith", chatWith);
                    reference1.push().setValue(map);
                }


                input.setText("");
                input.setFocusable(true);
                chatScroll.fullScroll(View.FOCUS_DOWN);
            }
        });

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                Map map = dataSnapshot.getValue(Map.class);
                String message = map.get("message").toString();
                String user = map.get("user").toString();
                String mchatWith = map.get("chatWith").toString();

                Log.e("user", user +"-"+ mchatWith +"-"+ chatWith);

                ChatMessage chatMessage = new ChatMessage();
                chatMessage.setMessageText(message);
                chatMessage.setMessageUser(user);

                if (mchatWith.equalsIgnoreCase(chatWith)) {
                    chatMessage.setStatus(1);
                } else if(user.equalsIgnoreCase(FirebaseAuth.getInstance().getCurrentUser().getEmail())) {
                    chatMessage.setStatus(2);

                }
                chatMessages.add(chatMessage);
                SyncMessage(chatMessages);

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        return view;

    }

    public void SyncMessage(ArrayList<ChatMessage> chatMessages1) {

        chatAdapter.notifyDataSetChanged();
        chatScroll.fullScroll(View.FOCUS_DOWN);

    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        for (int i = 0; i < menu.size(); ++i) {
            menu.removeItem(menu.getItem(i).getItemId());
        }
        inflater.inflate(R.menu.mnu_close, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.close:
                UtilityContainer.mLoadFragment(new IconPallet(), getActivity());
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
