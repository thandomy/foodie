package com.team3009.foodie;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

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
public class profileTab extends Fragment {


    public profileTab() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile_tab, container, false);
        String userId = getArguments().getString("userId");
        Toast.makeText(getActivity(),userId,Toast.LENGTH_SHORT).show();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        getUserInfo(view,userRef);
        return view;
    }

    private void getUserInfo(final View view, DatabaseReference userRef){
        userRef.addValueEventListener(new ValueEventListener() {

            TextView mNameField=(TextView) view.findViewById(R.id.name);
            TextView mPhoneField=(TextView) view.findViewById(R.id.phone);
            TextView mAddressField=(TextView) view.findViewById(R.id.address);

            TextView mEmailField=(TextView) view.findViewById(R.id.email);
            TextView mBioField=(TextView) view.findViewById(R.id.bio);
            private ImageView mProfileImage = (ImageView) view.findViewById(R.id.profileImage);

            //private String userID;
            private String mName;
            private String mPhone;

            private String mAddress;

            private String mBio;
            private String mEmail;
            private String mProfileImageUrl;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists() && dataSnapshot.getChildrenCount()>0){
                    Map<String, Object> map = (Map<String, Object>) dataSnapshot.getValue();
                    if(map.get("name") != null){
                        mName= map.get("name").toString();
                        mNameField.setText(mName);
                    }
                    if(map.get("phone") != null){
                        mPhone= map.get("phone").toString();
                        mPhoneField.setText(mPhone);
                    }

                    if(map.get("address") != null){
                        mAddress= map.get("address").toString();
                        mAddressField.setText(mAddress);
                    }
                    if(map.get("email") != null){
                        mEmail= map.get("email").toString();
                        mEmailField.setText(mEmail);

                    }
                    if(map.get("bio") != null){
                        mBio= map.get("bio").toString();
                        mBioField.setText(mBio);

                    }
                    if(map.get("profileImageUrl")!= null){
                        mProfileImageUrl = map.get("profileImageUrl").toString();
                        Picasso.with(getActivity()).load(mProfileImageUrl)
                                .error(R.drawable.common_google_signin_btn_icon_dark)
                                .into(mProfileImage);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
