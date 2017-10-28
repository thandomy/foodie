package com.team3009.foodie;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Zanele on 27-Oct-17.
 */

public class SignIn extends AppCompatActivity{
    final String TAG = "state";
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
        final Button sign_up = (Button) findViewById(R.id.butn_signup);
        sign_up.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email = ((EditText) findViewById(R.id.txt_email)).getText().toString();
                String password = ((EditText) findViewById(R.id.txt_pass)).getText().toString();
                createAccount(email,password);
            }
        });
        final Button login = (Button) findViewById(R.id.butn_login);
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String email = ((EditText) findViewById(R.id.txt_email)).getText().toString();
                String password = ((EditText) findViewById(R.id.txt_pass)).getText().toString();
                loginAccount(email,password);

            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }




    public void createAccount(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignIn.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SignIn.this, "success",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
    public void loginAccount(String email, String password){
      /*mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(MainActivity.this, R.string.auth_failed,
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "success",
                                    Toast.LENGTH_SHORT).show();*/
        Intent home = new Intent(SignIn.this, HomeActivity.class);
        startActivity(home);
        //}

        //}
        //  });
    }

}
