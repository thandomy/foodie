package com.team3009.foodie;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static com.team3009.foodie.R.id.imageView;


/**
 * Created by clubless on 2017/08/23.
 */

public class UploadFoodFrag extends Fragment {
    private StorageReference mStorageRef;

    public String downloadUrl;
    private ProgressBar progressBar;
    private Button selImage,takePic,upload;
    private TextView textView;
    private EditText titl,descrip,ingre,pric;
    private ImageView image;
    String title,description,url1,ingredients,price;
    Float nPrice;

    private static final int G_I = 2, R_I_C = 1;
    private Uri url;
    View v;
    Context contxt;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        Bundle loc = this.getArguments();
        final float[] locData = loc.getFloatArray("location");

        v = inflater.inflate(R.layout.uploadfrag,container,false);
        selImage = (Button) v.findViewById(R.id.getPic);
        image = (ImageView) v.findViewById(imageView);
        takePic = (Button) v.findViewById(R.id.takePic);
        upload = (Button) v.findViewById(R.id.subImage);

        textView = (TextView) v.findViewById(R.id.picUrl);
        titl = (EditText) v.findViewById(R.id.mealtitle);
        descrip = (EditText) v.findViewById(R.id.mealDescription);
        ingre = (EditText) v.findViewById(R.id.Ingredients);
        pric =(EditText) v.findViewById(R.id.price);

        selImage.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                title = titl.getText().toString();
                description = descrip.getText().toString();
                ingredients = ingre.getText().toString();
                price = pric.getText().toString();
                nPrice = Float.parseFloat(price);

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, G_I);
            }
        });

        takePic.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent takePicIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePicIntent.resolveActivity(contxt.getPackageManager()) != null) {
                    startActivityForResult(takePicIntent, R_I_C);
                }
                startActivityForResult(takePicIntent, G_I);
            }
        });
        upload.setOnClickListener(new OnClickListener(){
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

               // progressBar.setVisibility(View.VISIBLE);
                ProgressDialog progressDialog= new ProgressDialog(getActivity());
                progressDialog.setTitle("Upload");
                progressDialog.setMessage("Uploading...");
                progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressDialog.show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        StorageReference riversRef = mStorageRef.child(url.toString());

                        riversRef.putFile(url)
                                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        // Get a URL to the uploaded content

                                        downloadUrl = taskSnapshot.getMetadata().getDownloadUrl().toString();
                                        Post p = new Post();
                                        assert locData != null;
                                        p.sendData(title,description,ingredients,locData[0],locData[1],downloadUrl,nPrice);
                                    }



                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        // Handle unsuccessful uploads
                                        //...
                                    }
                                });



                    }

                });
                progressDialog.dismiss();
                    }
                });

        return v;
    }

    /*public void toast(){

                //.makeText(UploadFoodFrag.this, "Invalid Username, Please Try Again", Toast.LENGTH_LONG).show();
    }*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == G_I && resultCode == RESULT_OK){
            url = data.getData();
            // Bitmap bitmap = MediaStore.Images.Media.getBitmap(UploadFoodFrag.this.getContentResolver(), url);
            //url =  Uri.parse("/storage/emulated/0/Pictures/imgen/new.png");


            url1 = url.toString();
            textView.setText(url1);
            textView.setTextColor(Color.BLUE);

            Picasso.with(getActivity()).load(url)
                    .error(R.drawable.common_google_signin_btn_icon_dark)
                    .into(image);
           /* final InputStream pic =getActivity().getContentResolver().openInputStream(url);
            final Bitmap selectedImage = BitmapFactory.decodeStream(pic);
            image.setImageBitmap(selectedImage);*/
        }

        if (requestCode == R_I_C && resultCode == RESULT_OK) {

            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(imageBitmap);
        }

    }

    public void removeFragment(){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(this);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        ft.commit();
    }

}
