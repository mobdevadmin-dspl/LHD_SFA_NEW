package com.lankahardwared.lankahw.PushNotification;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.adapter.MsgAdapter;
import com.lankahardwared.lankahw.control.UtilityContainer;
import com.lankahardwared.lankahw.data.MessageDS;
import com.lankahardwared.lankahw.view.IconPallet;
import com.lankahardwared.lankahw.view.MainActivity;

import java.util.ArrayList;

/**
 * Created by Sathiyaraja on 12/6/2017.
 */

public class MessagesView extends Fragment implements View.OnClickListener {

    View view;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_messages, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        getActivity().setTitle("Messages");
        toolbar.setLogo(R.drawable.dm_logo_64);
        setHasOptionsMenu(true);

        //get all messages
        MessageDS messageDS = new MessageDS(getActivity());
        ArrayList<objMessage> messages = messageDS.getAllMessages();
        MsgAdapter msgAdapter = new MsgAdapter(messages,getActivity());

        recyclerView = (RecyclerView) view.findViewById(R.id.messages_list);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(msgAdapter);

        return view;
    }

    public void NavigateOff() {
        UtilityContainer.mLoadFragment(new IconPallet(), getActivity());
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

    @Override
    public void onClick(View v) {

    }
}
