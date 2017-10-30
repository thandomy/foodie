package com.team3009.foodie;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.icu.math.BigDecimal;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.braintreepayments.api.BraintreeFragment;
import com.braintreepayments.api.Card;
import com.braintreepayments.api.DataCollector;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.braintreepayments.api.exceptions.InvalidArgumentException;
import com.braintreepayments.api.interfaces.BraintreeResponseListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Transaction;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;


public class PaymentOptions extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    private StorageReference mStorageRef;

    public String downloadUrl;

    private Button payCash,payCredit;
    String setter;
    Float nPrice;

    BraintreeFragment mBraintreeFragment;
    String mAuthorization = "sandbox_bc77j8r3_n27kq9hxz3s2wqqt";
    final int REQUEST_CODE = 4949;
    String token = "eyJ2ZXJzaW9uIjoyLCJhdXRob3JpemF0aW9uRmluZ2VycHJpbnQiOiJjOTM3Yzc3ZmIwMzc3MDIyZjI4ZjJlYjkwMWQxMTI1NGJlZGNiNmYxMDZhMDJmMWU4NTAyZTkxYTZhMWU0YTc4fGNyZWF0ZWRfYXQ9MjAxNy0xMC0xNVQxMzoxODowNy4yNjIwNTkwNjErMDAwMFx1MDAyNm1lcmNoYW50X2lkPTM0OHBrOWNnZjNiZ3l3MmJcdTAwMjZwdWJsaWNfa2V5PTJuMjQ3ZHY4OWJxOXZtcHIiLCJjb25maWdVcmwiOiJodHRwczovL2FwaS5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tOjQ0My9tZXJjaGFudHMvMzQ4cGs5Y2dmM2JneXcyYi9jbGllbnRfYXBpL3YxL2NvbmZpZ3VyYXRpb24iLCJjaGFsbGVuZ2VzIjpbXSwiZW52aXJvbm1lbnQiOiJzYW5kYm94IiwiY2xpZW50QXBpVXJsIjoiaHR0cHM6Ly9hcGkuc2FuZGJveC5icmFpbnRyZWVnYXRld2F5LmNvbTo0NDMvbWVyY2hhbnRzLzM0OHBrOWNnZjNiZ3l3MmIvY2xpZW50X2FwaSIsImFzc2V0c1VybCI6Imh0dHBzOi8vYXNzZXRzLmJyYWludHJlZWdhdGV3YXkuY29tIiwiYXV0aFVybCI6Imh0dHBzOi8vYXV0aC52ZW5tby5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tIiwiYW5hbHl0aWNzIjp7InVybCI6Imh0dHBzOi8vY2xpZW50LWFuYWx5dGljcy5zYW5kYm94LmJyYWludHJlZWdhdGV3YXkuY29tLzM0OHBrOWNnZjNiZ3l3MmIifSwidGhyZWVEU2VjdXJlRW5hYmxlZCI6dHJ1ZSwicGF5cGFsRW5hYmxlZCI6dHJ1ZSwicGF5cGFsIjp7ImRpc3BsYXlOYW1lIjoiQWNtZSBXaWRnZXRzLCBMdGQuIChTYW5kYm94KSIsImNsaWVudElkIjpudWxsLCJwcml2YWN5VXJsIjoiaHR0cDovL2V4YW1wbGUuY29tL3BwIiwidXNlckFncmVlbWVudFVybCI6Imh0dHA6Ly9leGFtcGxlLmNvbS90b3MiLCJiYXNlVXJsIjoiaHR0cHM6Ly9hc3NldHMuYnJhaW50cmVlZ2F0ZXdheS5jb20iLCJhc3NldHNVcmwiOiJodHRwczovL2NoZWNrb3V0LnBheXBhbC5jb20iLCJkaXJlY3RCYXNlVXJsIjpudWxsLCJhbGxvd0h0dHAiOnRydWUsImVudmlyb25tZW50Tm9OZXR3b3JrIjp0cnVlLCJlbnZpcm9ubWVudCI6Im9mZmxpbmUiLCJ1bnZldHRlZE1lcmNoYW50IjpmYWxzZSwiYnJhaW50cmVlQ2xpZW50SWQiOiJtYXN0ZXJjbGllbnQzIiwiYmlsbGluZ0FncmVlbWVudHNFbmFibGVkIjp0cnVlLCJtZXJjaGFudEFjY291bnRJZCI6ImFjbWV3aWRnZXRzbHRkc2FuZGJveCIsImN1cnJlbmN5SXNvQ29kZSI6IlVTRCJ9LCJtZXJjaGFudElkIjoiMzQ4cGs5Y2dmM2JneXcyYiIsInZlbm1vIjoib2ZmIn0=";


    private static final int G_I = 2, R_I_C = 1;
    private Uri url;
    View v;
    Context contxt;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);

        try {
            mBraintreeFragment = BraintreeFragment.newInstance(getActivity(),mAuthorization);
            // mBraintreeFragment is ready to use!
        } catch (InvalidArgumentException e) {
            // There was an issue with your authorization string.
        }
        mStorageRef = FirebaseStorage.getInstance().getReference();

        Bundle loc = this.getArguments();
        final float[] locData = loc.getFloatArray("location");

        v = inflater.inflate(R.layout.fragment_payment_options, container, false);
        TextView text = (TextView) v.findViewById(R.id.amount);
        text.setText("R "+getArguments().getString("amount"));

        /*payCash = (Button) v.findViewById(R.id.cash);
        payCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Order order = new Order();
                //Toast.makeText(getActivity(),getArguments().getShort("Key"),Toast.LENGTH_LONG).show();
                order.sendData(getArguments().getString("Key"));
                removeFragment();
            }
        });*/

        payCredit = (Button) v.findViewById(R.id.credit_card);
        payCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(getDropInRequest().getIntent(getActivity()), REQUEST_CODE);
            }
        });
        return v;
    }

    private DropInRequest getDropInRequest() {
        return new DropInRequest()
                // Use the Braintree sandbox for dev/demo purposes
                .clientToken(token)
                .collectDeviceData(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                String paymentMethodNonce = result.getPaymentMethodNonce().getNonce();

                String deviceData = result.getDeviceData();
                //mBraintreeFragment.onPause();
                //String paymentNonce = result.getPaymentMethodNonce().getNonce();
               // Toast.makeText(getActivity(),"Purchase Successful",Toast.LENGTH_LONG).show();
               // removeFragment();
                //CardBuilder cardBuilder = new CardBuilder().
                //Log.d(TAG, "Testing the app here");
                Order order = new Order();
                //Toast.makeText(getActivity(),getArguments().getShort("Key"),Toast.LENGTH_LONG).show();
                order.sendData(getArguments().getString("Key"));

                Toast.makeText(getActivity(),"Purchase Successful",Toast.LENGTH_LONG).show();
                removeFragment();
            }
        }


       // Toast.makeText(getActivity(),"Purchase Successful",Toast.LENGTH_LONG).show();
    }
    public void removeFragment(){
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.remove(this);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
        ft.commit();
    }

}
