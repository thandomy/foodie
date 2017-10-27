package com.team3009.foodie;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import android.app.ProgressDialog;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class User extends Fragment {

    ListView usersList;
    TextView noUsersText;
    ArrayList<String> al = new ArrayList<>();
    //Plan B ArrayList<String> keys = new ArrayList<>();
    Map<String,String> relationship = new HashMap<String,String>();
    int totalUsers = 0;
    ProgressDialog pd;

    public User() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_users,container,false);

        usersList = (ListView) v.findViewById(R.id.usersList);
        noUsersText = (TextView) v.findViewById(R.id.noUsersText);

        pd = new ProgressDialog(getActivity());
        pd.setMessage("Loading...");
        pd.show();

        String contactListRef = FirebaseDatabase.getInstance().getReference().child("contactList").toString();
        contactListRef = contactListRef +"/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+".json";

        StringRequest request = new StringRequest(Request.Method.GET, contactListRef, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(getActivity());
        rQueue.add(request);

        //Turn this into a fragment
        /*usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserDetails.chatWith = al.get(position);
                startActivity(new Intent(getActivity(), Chat.class));
            }
        });*/

        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Chats ProfileTab = new Chats();
                Bundle args = new Bundle();
                args.putString("userId",relationship.get(al.get(position)));
                ProfileTab.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(((ViewGroup)(getView().getParent())).getId(), ProfileTab,ProfileTab.getTag()).addToBackStack(null)
                        .commit();
            }
        });

        return v;
    }

    public void doOnSuccess(String s){
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";

            while(i.hasNext()){
                key = i.next().toString();

                //if(!key.equals(UserDetails.username)) {
                //    al.add(key);
                //}

                relationship.put(getUserName(key),key);
                al.add(getUserName(key));

                totalUsers++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(totalUsers <=0){
            noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        }
        else{
            noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
            usersList.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, al));
        }

        pd.dismiss();
    }

    public String getUserName(final String userId){
        String userName = null;
        FirebaseDatabase.getInstance().getReference().child("Users").child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(com.google.firebase.database.DataSnapshot dataSnapshot) {
                HashMap<String, Object> map = (HashMap<String, Object>) dataSnapshot.getValue();
                String userName = (String) map.get("name");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return userName;
    }
}
