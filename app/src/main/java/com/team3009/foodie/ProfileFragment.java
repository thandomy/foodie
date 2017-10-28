package com.team3009.foodie;


import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

//import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static android.app.Activity.RESULT_OK;



/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {

  private EditText mNameField, mPhoneField,mAddressField,mAgeField,mEmailField,mBioField;
    private ImageView mProfileImage;
    private Button msave,mback;

    private FirebaseAuth mAuth;
    private DatabaseReference mCustomerDatabase;

    private String userID;
    private String mName;
    private String mPhone;
    private String mAge;
    private String mAddress;

    private String mBio;
    private String mEmail;
    private String mProfileImageUrl;

    private Uri resultUri;



    View v;
    FragmentManager mFragmentManager;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_profile1, container, false);
        v = inflater.inflate(R.layout.fragment_profile,container,false);
        mNameField=(EditText) v.findViewById(R.id.name);
        mPhoneField=(EditText) v.findViewById(R.id.phone);
        mAddressField=(EditText) v.findViewById(R.id.address);

        mEmailField=(EditText) v.findViewById(R.id.email);
        mBioField=(EditText) v.findViewById(R.id.bio);


        msave=(Button) v.findViewById(R.id.save);


        mProfileImage= (ImageView) v.findViewById(R.id.profileImage);
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getCurrentUser().getUid();
        mCustomerDatabase= FirebaseDatabase.getInstance().getReference().child("Users").child(userID);
       getUserInfo();
        msave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveuserInformation();
            }
        });

        mProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,1);

            }
        });

        return v;

    }
    private void getUserInfo(){
        mCustomerDatabase.addValueEventListener(new ValueEventListener() {
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK){
            final Uri imageUri= data.getData();
            resultUri=imageUri;
            mProfileImage.setImageURI(resultUri);
        }
    }

    private void saveuserInformation(){

        mName= mNameField.getText().toString();
        mPhone= mPhoneField.getText().toString();
        mAddress= mAddressField.getText().toString();
        mEmail= mEmailField.getText().toString();
        mAge= mAgeField.getText().toString();
        mBio= mBioField.getText().toString();

        Map userInfo = new HashMap();
        userInfo.put("name",mName);
        userInfo.put("phone",mPhone);

        userInfo.put("address",mAddress);
        userInfo.put("email",mEmail);
        userInfo.put("bio",mBio);

        //userInfo.put("profileImageUrl",mProfileImageUrl);
        mCustomerDatabase.updateChildren(userInfo);

        if(resultUri != null){
            StorageReference filePath = FirebaseStorage.getInstance().getReference().child("profile_images").child(userID);
            Bitmap bitmap =null;
            try {
                bitmap= MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),resultUri);
            }catch (IOException e){
                e.printStackTrace();
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG,20,baos);
            byte [] data= baos.toByteArray();
            UploadTask uploadTask= filePath.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    return;
                }
            });
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Uri downloadUrl = taskSnapshot.getDownloadUrl();
                    Map newImage = new HashMap();
                    newImage.put("profileImageUrl",downloadUrl.toString());
                    mCustomerDatabase.updateChildren(newImage);
                    return;
                }
            });


        }

    }

}
