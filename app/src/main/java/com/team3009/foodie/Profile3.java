package com.team3009.foodie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
public class Profile3 extends Fragment {
    String key;
    private Button mOrder;
    private TextView mIngredients, mUserName,mTitle;
    private ImageView mProfilepic;


    // [START define_database_reference]
    private DatabaseReference mDatabase;
    private DatabaseReference mCustomer;
    // [END define_database_reference]


    public Profile3() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_profile2, container, false);
        String key = getArguments().getString("key");
        String userId = getArguments().getString("userId");
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
                Serve model = dataSnapshot.getValue(Serve.class);
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
        return view;
    }
}
