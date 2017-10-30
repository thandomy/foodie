package com.team3009.foodie;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
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

import java.util.ArrayList;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment implements OnMapReadyCallback {
    String key,amount;
    private Button mOrder, Rate;
    private TextView mIngredients, mUserName,mTitle;
    private ImageView mProfilepic;
    GoogleMap googleMap;
    Serve model;
    private Button mMessage;
    private String   mRating;
    private Float number;
    private Float num = 0f;
    Map singleUser;
    ArrayList <String>  numbers= new ArrayList<>();
    String token = "eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiJjOTM3Yzc3ZmIwMzc3MDIyZjI4ZjJlYjkwMWQxMTI1NGJlZGNiNmYxMDZhMDJmMWU4NTAyZTkxYTZhMWU0YTc4fGNyZWF0ZWRfYXQ9MjAxNy0xMC0xNVQxMzoxODowNy4yNjIwNTkwNjErMDAwMFx1MDAyNm1lcmNoYW50X2lkPTM0OHBrOWNnZjNiZ3l3MmJcdTAwMjZwdWJsaWNfa2V5PTJuMjQ3ZHY4OWJxOXZtcHIiLCJjb25maWdVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvMzQ4cGs5Y2dmM2JneXcyYi9jbGllbnRfYXBpL3YxL2NvbmZpZ3VyYXRpb24iLCJjaGFsbGVuZ2VzIjpbXSwiZW52aXJvbm1lbnQiOiJzYW5kYm94IiwiY2xpZW50QXBpVXJsIjoiaHR0cHM6Ly9hcGkuc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbTo0NDMvbWVyY2hhbnRzLzM0OHBrOWNnZjNiZ3l3MmIvY2xpZW50X2FwaSIsImFzc2V0c1VybCI6Imh0dHBzOi8vYXNzZXRzLmJyYWludHJlZWdhdGV3YXkuY29tIiwiYXV0aFVybCI6Imh0dHBzOi8vYXV0aC52ZW5tby5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tIiwiYW5hbHl0aWNzIjp7InVybCI6Imh0dHBzOi8vY2xpZW50LWFuYWx5dGljcy5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tLzM0OHBrOWNnZjNiZ3l3MmIifSwidGhyZWVEU2VjdXJlRW5hYmxlZCI6dHJ1ZSwicGF5cGFsRW5hYmxlZCI6dHJ1ZSwicGF5cGFsIjp7ImRpc3BsYXlOYW1lIjoiQWNtZSBXaWRnZXRzLCBMdGQuIChTYW5kYm94KSIsImNsaWVudElkIjpudWxsLCJwcml2YWN5VXJsIjoiaHR0cDovL2V4YW1wbGUuY29tL3BwIiwidXNlckFncmVlbWVudFVybCI6Imh0dHA6Ly9leGFtcGxlLmNvbS90b3MiLCJiYXNlVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhc3NldHNVcmwiOiJodHRwczovL2NoZWNrb3V0LnBheXBhbC5jb20iLCJkaXJlY3RCYXNlVXJsIjpudWxsLCJhbGxvd0h0dHAiOnRydWUsImVudmlyb25tZW50Tm9OZXR3b3JrIjp0cnVlLCJlbnZpcm9ubWVudCI6Im9mZmxpbmUiLCJ1bnZldHRlZE1lcmNoYW50IjpmYWxzZSwiYnJhaW50cmVlQ2xpZW50SWQiOiJtYXN0ZXJjbGllbnQzIiwiYmlsbGluZ0FncmVlbWVudHNFbmFibGVkIjp0cnVlLCJtZXJjaGFudEFjY291bnRJZCI6ImFjbWV3aWRnZXRzbHRkc2FuZGJveCIsImN1cnJlbmN5SXNvQ29kZSI6IlVTRCJ9LCJtZXJjaGFudElkIjoiMzQ4cGs5Y2dmM2JneXcyYiIsInZlbm1vIjoib2ZmIn0=";






    RatingBar ratingBar;



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
                                .error(R.drawable.bt_ic_vaulted_venmo)
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
                        .error(R.drawable.bt_ic_vaulted_venmo)
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
                        Toast.makeText(getActivity(), model.key, Toast.LENGTH_LONG).show();
                        args.putString("Key", key);
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
                assert lastLocation != null;
                MapElements drawPath = new MapElements(googleMap, lastLocation[0],lastLocation[1],model.latitude, model.longitude);
                drawPath.Draw();
                drawPath.distanceBox();
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


        Rate = (Button) view.findViewById(R.id.ratefrag);


        if (userId.equals(FirebaseAuth.getInstance().getCurrentUser().getUid())) {

            Rate.setVisibility(View.INVISIBLE);
        } else {
            Rate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RatingFrag ratingfrag = new RatingFrag();
                    Bundle args = new Bundle();
                    args.putString("userId", getArguments().getString("userId"));
                    ratingfrag.setArguments(args);
                    FragmentManager fragmentManager = getFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(((ViewGroup) (getView().getParent())).getId(), ratingfrag, ratingfrag.getTag()).addToBackStack("v")
                            .commit();
                }
            });
        }


        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Rating").child(userId);



        userRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    collectLocationsAndPutOnMap((Map<String, Object>) dataSnapshot.getValue(), view);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return view;
    }


    private void collectLocationsAndPutOnMap(Map<String, Object> servings, View v) {


        ratingBar = (RatingBar) v.findViewById(R.id.indicatorRatingBar);
        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : servings.entrySet()) {

            //Get user map
            singleUser = (Map) entry.getValue();
            //Get phone field and append to list

            numbers.add((String) singleUser.get("rating"));
        }
        for( int i= 0; i < numbers.size(); i++ ) {

            number = Float.parseFloat(numbers.get(i));
            num = num + number;
        }
        num= num/numbers.size();


        ratingBar.setRating(num);
        ratingBar.invalidate();
        ratingBar.setIsIndicator(true);
        num = 0f;




    }
    public void onBraintreeSubmit(View v) {
        DropInRequest dropInRequest = new DropInRequest()
                .clientToken(token/*clientToken*/).amount(amount);
        startActivityForResult(dropInRequest.getIntent(getActivity()), REQUEST_CODE);
        removeFragment();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                //CardBuilder cardBuilder = new CardBuilder().
            }
        }
    }

    public void removeFragment() {
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
