package com.team3009.foodie;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import static android.app.Activity.RESULT_OK;

/**
 * Created by clubless on 2017/08/23.
 */

public class UploadFoodFrag extends Fragment {

    private Button selImage,takePic;
    private TextView textView;
    private EditText titl,descrip;
    private ImageView image;
    String title,description,url1 ;
    private static final int G_I = 2, R_I_C = 1;
    private Uri url;
    View v;
    Context contxt;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        v = inflater.inflate(R.layout.uploadfrag,container,false);
        selImage = (Button) v.findViewById(R.id.getPic);
        textView = (TextView) v.findViewById(R.id.picUrl);
        titl = (EditText) v.findViewById(R.id.mealtitle);
        descrip = (EditText) v.findViewById(R.id.mealDescription);
        image = (ImageView) v.findViewById(R.id.img);
        takePic = (Button) v.findViewById(R.id.takePic);
       // contxt =
        //Toast.makeText(this,"Man",Toast.LENGTH_LONG).show();
        selImage.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View view) {
                     title = titl.getText().toString();
                     description = descrip.getText().toString();

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
            url1 = url.toString();
            textView.setText(url1);
            textView.setTextColor(Color.BLUE);
           /* final InputStream pic =getActivity().getContentResolver().openInputStream(url);
            final Bitmap selectedImage = BitmapFactory.decodeStream(pic);
            image.setImageBitmap(selectedImage);*/
        }

        if (requestCode == R_I_C && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(imageBitmap);
        }
        Post post = new Post(title,description,2.22,2.365,url1);
        // ZaneleFunc(title,description,url);
    }

}
