package com.lankahardwared.lankahw.view.chat;


import android.os.Bundle;
import androidx.annotation.NonNull;
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

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.lankahardwared.lankahw.R;
import com.lankahardwared.lankahw.control.UtilityContainer;
import com.lankahardwared.lankahw.view.IconPallet;
import com.lankahardwared.lankahw.view.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

import static android.content.ContentValues.TAG;

/**
 * Created by Sathiyaraja on 12/6/2017.
 */

public class DeviceChat extends Fragment {

    View view;
    private ArrayList<objUser> users;
    private RecyclerView recyclerView;
    public static FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.layout_dchat, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
        getActivity().setTitle("Chat");
        toolbar.setLogo(R.drawable.dm_logo_64);
        setHasOptionsMenu(true);

        mAuth = FirebaseAuth.getInstance();

        recyclerView = (RecyclerView) view.findViewById(R.id.users_listView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());

                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    Login("ss@gmail.com", "123456");
                }
                // ...
            }
        };


        String url = "https://finacnew.firebaseio.com/users.json";
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                doOnSuccess(s);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);

            }
        });

        com.android.volley.RequestQueue rQueue = Volley.newRequestQueue(getActivity());
        rQueue.add(request);


        return view;
    }


    public void doOnSuccess(String s) {
        users = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";

            while (i.hasNext()) {
                key = i.next().toString();
                objUser user = new objUser();
                user.setUser(key);
                users.add(user);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        UserAdapter userAdapter = new UserAdapter(users, getActivity());
        recyclerView.setAdapter(userAdapter);

    }

    public void Login(String uname, String pw) {

        mAuth.signInWithEmailAndPassword(uname, pw)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        if (task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                         //   Signup("ss@gmail.com", "123456");

                        } else {
                            Signup("ss@gmail.com", "123456");
                        }

                        // ...
                    }

                });
    }

    public void Signup(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Login("ss@gmail.com", "123456");
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());

                            //  updateUI(null);
                        }

                        // ...
                    }
                });
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
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

}
