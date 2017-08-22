package com.team3009.foodie;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.team3009.foodie.Serve;

/**
 * Created by Zanele on 8/22/2017.
 */

public class PostActivity extends AppCompatActivity {

    private static final String REQUIRED = "Required";


    private EditText mTitleField;
    private EditText mBodyField;
    private FloatingActionButton mSubmitButton;
    private Double Latitude;
    private Double Longitude;
    private Button post_pic;
    private String uri_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_activity);

        Bundle bundle =getIntent().getExtras();

        Latitude = bundle.getDouble("latitude");
        Longitude = bundle.getDouble("longitude");


        mTitleField = (EditText) findViewById(R.id.field_title);
        mBodyField = (EditText) findViewById(R.id.field_body);
        mSubmitButton = (FloatingActionButton) findViewById(R.id.fab_submit_post);
        post_pic = (Button) findViewById(R.id.gallary_post);


        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String TitleField = mTitleField.getText().toString();
                String BodyField = mBodyField.getText().toString();
                post(TitleField,BodyField,Latitude,Longitude,uri_string);


            }
        });

        post_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent picture = new Intent (Intent.ACTION_PICK);
                picture.setType("image/");
                startActivityForResult(picture,2);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 2) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.
                Uri uri = data.getData();
                String uri_string = uri.toString();



                // Do something with the contact here (bigger example below)
            }
        }
    }



    private void post(String title, String body, Double latitude, Double longitude, String uri) {
        // Get the Firebase node to write the data to
        DatabaseReference node = FirebaseDatabase.getInstance().getReference().child("Serving").push();
        // Write an entry containing the location and payment data of the user to the Firebase node


        // Title is required
        if (TextUtils.isEmpty(title)) {
            mTitleField.setError(REQUIRED);
            return;
        }

        // Body is required
        if (TextUtils.isEmpty(body)) {
            mBodyField.setError(REQUIRED);
            return;
        }

        setEditingEnabled(false);


        String userEmail = geEmail();
        node.setValue(new Serve(userEmail,title,body,latitude,longitude,uri));

        setEditingEnabled(true);

    }


    private void setEditingEnabled(boolean enabled) {
        mTitleField.setEnabled(enabled);
        mBodyField.setEnabled(enabled);
        if (enabled) {
            mSubmitButton.setVisibility(View.VISIBLE);
        } else {
            mSubmitButton.setVisibility(View.GONE);
        }
    }
    public String geEmail() {

        return FirebaseAuth.getInstance().getCurrentUser().getEmail();
    }

}
