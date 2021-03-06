package com.team3009.foodie;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;

import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.Manifest;
import android.view.View;
import android.widget.Toast;
import java.util.Map;
import java.lang.*;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import static android.widget.Toast.LENGTH_LONG;


public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener,GoogleMap.OnMarkerClickListener{

    FragmentManager mFragmentManager;
    private static final long REQUEST_INTERVAL = 1000L;
    private static final float ZOOM_LEVEL = 18f;
    private static final int LOCATION_REQUEST_CODE = 123;

    private FirebaseAuth mAuth;
    private GoogleMap googleMap;
    private GoogleApiClient googleApiClient;
    private Location lastLocation;
    private Marker currentLocationMarker;
    private ArrayList<Double> latitudes = new ArrayList<>();
    private ArrayList<Double> longitudes = new ArrayList<>();
    private ArrayList<String> keys = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> userIds = new ArrayList<>();
    private ArrayList<Double> amount = new ArrayList<>();

    Map singleUser;

    private volatile boolean FLAG = false;

    final CountDownLatch done = new CountDownLatch(1);

    View mapView;
    String email;
    public HomeActivity() throws InterruptedException {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodie_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        checkLocationPermission();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);


        mapView = mapFragment.getView();
        mapView.setContentDescription("MAP NOT READY");
        mapFragment.getMapAsync(this);
        recieveData();

    }





        @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.foodie_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the HomeActivity/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {

        } else if (id == R.id.nav_gallery) {
            HistoryFragment chats = new HistoryFragment();
            //Bundle args = new Bundle();
            //args.putString("userId",getArguments().getString("userId"));
            Toast.makeText(this,"call users",Toast.LENGTH_SHORT).show();
            //chats.setArguments(args);
            FragmentTransaction fragmentManager= getSupportFragmentManager().beginTransaction();
            fragmentManager.replace(R.id.containerView, chats,chats.getTag()).addToBackStack(null)
                    .commit();

        } else if (id == R.id.nav_list_view) {
            setTitle("Food");
            PostListFragment fragment = new PostListFragment();
            mFragmentManager = getSupportFragmentManager();

            Bundle loc = new Bundle();

            float[] location = new float[2];
            //location[0] = Float.parseFloat(Double.toString(temp.getLatitude()));
            //location[1] = Float.parseFloat(Double.toString(temp.getLongitude()));

            location[0] = Float.parseFloat(Double.toString(lastLocation.getLatitude()));
            location[1] = Float.parseFloat(Double.toString(lastLocation.getLongitude()));
            loc.putFloatArray("lastLocation", location);
            fragment.setArguments(loc);
            mFragmentManager.beginTransaction().replace(R.id.containerView, fragment).addToBackStack("t").commit();
        } else if (id == R.id.nav_serve) {
           setTitle("Serve");
            UploadFoodFrag fragment = new UploadFoodFrag();
            mFragmentManager = getSupportFragmentManager();

            Bundle loc = new Bundle();

            float[] location = new float[2];
            location[0] = Float.parseFloat(Double.toString(lastLocation.getLatitude()));
            location[1] = Float.parseFloat(Double.toString(lastLocation.getLongitude()));
            loc.putFloatArray("location", location);
            fragment.setArguments(loc);
            mFragmentManager.beginTransaction().replace(R.id.containerView, fragment).addToBackStack("v").commit();
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {
            setTitle("Profile");

            Bundle loc = new Bundle();
            loc.putString("Email", email);

            ProfileFragment fragment= new ProfileFragment();
            FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.containerView,fragment,"ProfileFragment");
            fragmentTransaction.commit();


        }else if (id == R.id.nav_chats) {
            setTitle("Chats");
            UserList fragment= new UserList();
            FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.containerView,fragment,"UserList");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_logout){

             FirebaseAuth.getInstance().signOut();

// this listener will be called when there is change in firebase user session

                        // user auth state is changed - user is null
                        // launch login activity
                        startActivity(new Intent(HomeActivity.this, MainActivity.class));
                        finish();


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(REQUEST_INTERVAL);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Request location updates from the Google API client
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }
    }


    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        // Store the current location of the user
        lastLocation = location;
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (currentLocationMarker == null) {
            // Move the camera to the user's current location on the first location update
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, ZOOM_LEVEL));
        }
        //replaceMarker(latLng);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mapView.setContentDescription("MAP");
        this.googleMap = googleMap;
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            setUpGoogleApiClient();
            // Draw an indication of the user's current location on the map
            this.googleMap.setMyLocationEnabled(true);
        }
        googleMap.setOnMarkerClickListener(this);
    }



    /**
     * Sets up the Google API client to use the location services API and relevant callbacks.
     */
    private synchronized void setUpGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        googleApiClient.connect();
    }

    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        if (requestCode == LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    if (googleApiClient == null) {
                        setUpGoogleApiClient();
                    }
                    googleMap.setMyLocationEnabled(true);
                }
            } else {
                // Show a message that the position has not been granted
                Toast.makeText(this, R.string.permission_not_granted, LENGTH_LONG).show();
            }
        }

    }
    /**
     * Adds a marker to the current position.
     */
    private void replaceMarker(LatLng latLng) {
        // Remove the previous marker
        if (currentLocationMarker != null) {
            currentLocationMarker.remove();
        }
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        // Add a marker indicating the user's current position to the Google Map
        currentLocationMarker = googleMap.addMarker(markerOptions);
    }


    private void recieveData() {
        // Get the Firebase node to write the  read data from
        DatabaseReference refDatabase = FirebaseDatabase.getInstance().getReference("Serving");
        refDatabase.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                collectLocationsAndPutOnMap((Map<String, Object>) dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }


        });
    }

    private void collectLocationsAndPutOnMap(Map<String, Object> servings) {

        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : servings.entrySet()) {

            //Get user map
            singleUser = (Map) entry.getValue();
            //Get phone field and append to list

            latitudes.add((Double) singleUser.get("latitude"));
            longitudes.add((Double) singleUser.get("longitude"));
            keys.add((String) singleUser.get("key"));
            userIds.add((String) singleUser.get("userId"));
            //amount.add((Double) singleUser.get("price"));


            titles.add((String) singleUser.get("title"));
        }

        for (int i = 0; i < latitudes.size(); i++) {
            LatLng aLocation = new LatLng(
                    latitudes.get(i), longitudes.get(i)
            );


            BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.wii);
            Bitmap b=bitmapdraw.getBitmap();
            Bitmap smallMarker = Bitmap.createScaledBitmap(b, 150, 150, false);

            Marker marker =  googleMap.addMarker(new MarkerOptions()
                    .position(aLocation)
                    .title(titles.get(i))
                    .icon(BitmapDescriptorFactory.
                            fromBitmap(smallMarker))

            );
            marker.setTag(keys.get(i));
            marker.showInfoWindow();
        /*googleMap.addMarker(new MarkerOptions()
                .position(new LatLng(
                        20, -25))
                .title("fake location"));*/

        }

    }


    @Override
    public boolean onMarkerClick(Marker marker) {
       int i = keys.indexOf(marker.getTag());
        Bundle args = new Bundle();
        args.putString("key",keys.get(i));
        args.putString("userId",userIds.get(i));
        args.putString("amount", "2");             //!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

        OrderFragment fragment= new OrderFragment();
        fragment.setArguments(args);
        FragmentTransaction fragmentTransaction= getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.containerView,fragment,"OrderFragment").addToBackStack("ADD");
        fragmentTransaction.commit();
        return false;
    }
}




