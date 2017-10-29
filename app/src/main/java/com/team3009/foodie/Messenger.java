package com.team3009.foodie;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.RemoteMessage;

import static android.content.ContentValues.TAG;

/**
 * Created by Zanele on 29-Oct-17.
 */

public class Messenger extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_message, container, false);


        if(getArguments() != null){
            //String recievedMessage = loc.getString("message");
        }
        Bundle loc = this.getArguments();
        final String key = loc.getString("key");
        final String userId = loc.getString("userId");

        final TextView mes = (TextView) v.findViewById(R.id.message_box);


        if (getArguments() != null) {
            for (String i : getArguments().keySet()) {
                Object value =getArguments().get(i);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId  = getString(R.string.gcm_defaultSenderId);
            String channelName = getString(R.string.project_id);
            NotificationManager notificationManager =
                    getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }

        Button send = v.findViewById(R.id.send_button);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String token = FirebaseInstanceId.getInstance().getToken();
                DatabaseReference refDatabase = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("token").push();
                refDatabase.setValue(token);

                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!   "+ FirebaseAuth.getInstance().getCurrentUser().getUid()) ;


                String my_message = mes.getText().toString();
                FirebaseMessaging fm = FirebaseMessaging.getInstance();
                fm.send(new RemoteMessage.Builder("AAAA4EgTb48:APA91bGYHTTpoBhyIcN4DucK0OEtcY2U7hAoTgsWkpW1ANr2nBPBUeP2xOXR091e1BHD-XOjK2HURq7nnsy7Bj7EfbuWLDVrvth4a2lhFhC5eGhNFaGBoD6xC9STorKOjkQ1C3wz2zaE" + "@gcm.googleapis.com")
                        .setMessageId(Integer.toString(113))
                        .addData("message", my_message)
                        .addData("userId",userId)
                        .addData("key",key)
                        .build());
            }
        });

        Button logTokenButton = v.findViewById(R.id.logTokenButton);
        logTokenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get token
                String token = FirebaseInstanceId.getInstance().getToken();
                // Log and toast
                Log.d(TAG, token);
                Toast.makeText(getActivity(), token, Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }
}