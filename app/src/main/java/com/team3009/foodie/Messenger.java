package com.team3009.foodie;

import android.support.v4.app.Fragment;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

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
        if (getArguments() != null) {
            for (String key : getArguments().keySet()) {
                Object value =getArguments().get(key);
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
                FirebaseMessaging.getInstance().subscribeToTopic("news");
                // [END subscribe_topics]

                // Log and toast
                String msg = "subbed";
                Log.d(TAG, msg);
                Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();



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
