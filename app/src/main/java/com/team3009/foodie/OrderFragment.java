package com.team3009.foodie;


import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.exceptions.InvalidArgumentException;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment implements OnMapReadyCallback {
    String key,amount;
    private Button mOrder;
    private TextView mIngredients, mUserName,mTitle;
    private ImageView mProfilepic;
    GoogleMap googleMap;
    Serve model;
    private Button mMessage;



    // [START define_database_reference]
    private DatabaseReference mDatabase;
    private DatabaseReference mCustomer;
    // [END define_database_reference]


    final int REQUEST_CODE = 1;


    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile2, container, false);
        key = getArguments().getString("key");
        String userId = getArguments().getString("userId");

        //hide send message button from the person who sent the post
        mMessage = (Button) view.findViewById(R.id.sendMessage);
        if(userId.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())){
            mMessage.setVisibility(View.INVISIBLE);
        }else{
            mMessage.setVisibility(View.VISIBLE);
        }

        final float[] lastLocation = getArguments().getFloatArray("lastLocation");

        amount = getArguments().getString("amount");
        Toast.makeText(getActivity(),userId,Toast.LENGTH_SHORT).show();
        mDatabase = FirebaseDatabase.getInstance().getReference("Serving").child(key);
        mCustomer= FirebaseDatabase.getInstance().getReference("Users").child(userId);
        /*if(FirebaseDatabase.getInstance().getReference().child("contactList")== null){
            Toast.makeText(getActivity(),"Yayo ft August Child iSpillion",Toast.LENGTH_LONG).show();
        }*/

        mCustomer.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if(map.get("name") != null){
                        ((TextView) view.findViewById(R.id.userName)).setText(map.get("name").toString());
                    }
                    if(map.get("profileImageUrl")!=null) {
                        Picasso.with(getActivity())
                                .load(map.get("profileImageUrl").toString())
                                .error(R.drawable.common_google_signin_btn_text_light_disabled)
                                .into((ImageView) view.findViewById(R.id.profilePic));
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        ValueEventListener valueEventListener = mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                model = dataSnapshot.getValue(Serve.class);
                //((TextView) view.findViewById(R.id.foodDesc)).setText(model.description);
                ((TextView) view.findViewById(R.id.title)).setText(model.title);
                Picasso.with(getActivity())
                        .load(model.downloadUrl)
                        .error(R.drawable.common_google_signin_btn_text_light_disabled)
                        .into((ImageView) view.findViewById(R.id.userPic));

                ((TextView) view.findViewById(R.id.ingredientslist)).setText(model.description);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        view.findViewById(R.id.profileTab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Life Sucks",Toast.LENGTH_SHORT).show();
                profileTab ProfileTab = new profileTab();
                Bundle args = new Bundle();
                args.putString("userId",getArguments().getString("userId"));
                ProfileTab.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(((ViewGroup)(getView().getParent())).getId(), ProfileTab,ProfileTab.getTag()).addToBackStack(null)
                        .commit();
            }
        });

        mOrder = (Button) view.findViewById(R.id.Order);

            mOrder.setVisibility(View.VISIBLE);
            mOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(model.userId)){
                        mOrder.setClickable(false);
                    }
                    else{
                        PaymentOptions fragment = new PaymentOptions();
                        Bundle args = new Bundle();
                        fragment.setArguments(args);
                       // Toast.makeText(getActivity(), model.key, Toast.LENGTH_LONG).show();
                        args.putString("Key", key);
                        args.putString("amount",model.price.toString());
                        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.containerView, fragment).addToBackStack("t").commit();
                    }
                }
            });


        final SupportMapFragment mapFragment = ((SupportMapFragment) getFragmentManager()
                .findFragmentById(R.id.map));
        mapFragment.getMapAsync(this);

        Button see = (Button) view.findViewById(R.id.seeonmap);
        see.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Pathing drawPath = new Pathing(googleMap);
                assert lastLocation != null;
                //System.out.println("LLLLLLLLLLLLLCAAATIOOOOOO00000000000ON " +" " + lastLocation[0]+" " + lastLocation[1] +" " + model.latitude +" " + model.longitude);
                drawPath.Draw(lastLocation[0],lastLocation[1],model.latitude, model.longitude);
                removeFragment();
            }
        });

        //functionality for send button
        ((Button)view.findViewById(R.id.sendMessage)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Chats chats = new Chats();
                Bundle args = new Bundle();
                args.putString("userId",getArguments().getString("userId"));
                Toast.makeText(getActivity(),"Message",Toast.LENGTH_LONG).show();
                chats.setArguments(args);
                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(((ViewGroup)(getView().getParent())).getId(), chats,chats.getTag()).addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }




    public void removeFragment(){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(this);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        ft.commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    //for send button

}
