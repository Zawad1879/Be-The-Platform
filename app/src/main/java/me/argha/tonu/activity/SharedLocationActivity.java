package me.argha.tonu.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.AsyncHttpClient;

import butterknife.ButterKnife;
import me.argha.tonu.R;
import me.argha.tonu.app.Config;
import me.argha.tonu.gcm.GcmIntentService;
import me.argha.tonu.gcm.MyGcmPushReceiver;
import me.argha.tonu.helpers.MyPreferenceManager;
import me.argha.tonu.model.Message;

public class SharedLocationActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, OnMapReadyCallback {


    private String TAG = SharedLocationActivity.class.getSimpleName();
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private BroadcastReceiver mRegistrationBroadcastReceiver;
    GoogleApiClient googleApiClient;
    GoogleMap googleMap;
    private MyPreferenceManager preferenceManager;
    private Context context;
    private AsyncHttpClient asyncHttpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        ButterKnife.bind(this);
        setUpMaps();
        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                // checking for type intent filter
                if (intent.getAction().equals(Config.REGISTRATION_COMPLETE)) {
                    // gcm successfully registered
                    // now subscribe to `global` topic to receive app wide notifications
                    String token = intent.getStringExtra("token");

//                    Toast.makeText(getApplicationContext(), "GCM registration token: " + token, Toast.LENGTH_LONG).show();

                } else if (intent.getAction().equals(Config.SENT_TOKEN_TO_SERVER)) {
                    // gcm registration id is stored in our server's MySQL

                    Toast.makeText(getApplicationContext(), "GCM registration token is stored in server!", Toast.LENGTH_LONG).show();

                } else if (intent.getAction().equals(Config.PUSH_NOTIFICATION)) {
                    // new push notification is received
                    handlePushNotification(intent);
                    Toast.makeText(getApplicationContext(), "Push notification is received!", Toast.LENGTH_LONG).show();
                }
            }
        };

        if (checkPlayServices()) {
            registerGCM();
        }else{
            finish();
        }
    }

    private void setUpMaps() {
        setUpGoogleApiClient();
    }
    private void setUpGoogleApiClient() {
        googleApiClient= new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        if(!googleApiClient.isConnected()) {
            googleApiClient.connect();
        }
    }

    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, this,
                        1000).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    // starting the service to register with GCM
    private void registerGCM() {
        Intent intent = new Intent(this, GcmIntentService.class);
        intent.putExtra("key", "register");
        startService(intent);
    }

    /**
     * Handles new push notification
     */
    private void handlePushNotification(Intent intent) {
        int type = intent.getIntExtra("type", -1);

        // if the push is of chat room message
        // simply update the UI unread messages count
        if (type == Config.PUSH_TYPE_USER) {
            // push belongs to user alone
            // just showing the message in a toast
            Message message = (Message) intent.getSerializableExtra("message");
            Toast.makeText(getApplicationContext(), "New push: " + message.getMessage(), Toast.LENGTH_LONG).show();
            TextView receivedMsgTv= new TextView(this);
            receivedMsgTv.setText(message.getMessage());
            LinearLayout.LayoutParams lp= new LinearLayout.LayoutParams(ViewGroup.LayoutParams
                    .MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.gravity= Gravity.START;
            receivedMsgTv.setLayoutParams(lp);
//            receivedMsgTv.setPadding(10, 5, 50, 5);
            receivedMsgTv.setBackgroundColor(0xfff);
//            receivedMsgTv.setTextColor(0x000);

        }


    }

    private void initMap() {

        if (googleMap == null) {
            MapFragment mapFragment = (MapFragment) getFragmentManager()
                    .findFragmentById(R.id.mapFragment);
            mapFragment.getMapAsync(this);
            googleMap= mapFragment.getMap();


//            if (googleMap == null) {
//                Toast.makeText(MapActivity.this, "Failed to create google map", Toast.LENGTH_SHORT).show();
//                return;
//            }
        }
    }

    private void init() {

        preferenceManager= new MyPreferenceManager(this);
        context=this;
        asyncHttpClient= new AsyncHttpClient();
    }

    @Override
    protected void onResume() {
        super.onResume();

        // register GCM registration complete receiver
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.REGISTRATION_COMPLETE));

        // register new push message receiver
        // by doing this, the activity will be notified each time a new message arrives
        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,
                new IntentFilter(Config.PUSH_NOTIFICATION));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mRegistrationBroadcastReceiver);
        super.onPause();
    }

    @Override
    public void onConnected(Bundle bundle) {
        initMap();
//        setUpMarkersAndMap();
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
//        googleMap.setMyLocationEnabled(true);
//        LatLng dangerCoordinates= new LatLng(23.8103,90.4125);
        setUpMarkersAndMap();
        init();
    }

    private void setUpMarkersAndMap() {

        LatLng myLocation=getMyLocation();
        Log.e(TAG, myLocation.latitude+","+myLocation.longitude);
        Bundle extras= getIntent().getExtras();
        LatLng friendLoc=null;
        if(MyGcmPushReceiver.staticLatLng!=null){
            friendLoc= MyGcmPushReceiver.staticLatLng;
        }
        else if(extras==null){
            Toast.makeText(SharedLocationActivity.this, "No location has been shared with you"
                    , Toast.LENGTH_SHORT).show();
            return;
        }else {
            double lat = extras.getDouble("latitude");
            double lon = extras.getDouble("longitude");
            Log.e(TAG, lat + "," + lon);
            friendLoc = new LatLng(lat, lon);
        }
        googleMap.addMarker(new MarkerOptions()
                .position(friendLoc)
                .draggable(false)
                .visible(true)
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.custom_marker))
                );
        CameraPosition camPos = new CameraPosition.Builder()

                .target(friendLoc)

                .zoom(14)

                .build();

        CameraUpdate camUpdate = CameraUpdateFactory.newCameraPosition(camPos);
        googleMap.animateCamera(camUpdate);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // Call some material design APIs here

        } else {
            // Implement this feature without material design
            googleMap.setMyLocationEnabled(true);
        }
    }

    private LatLng getMyLocation() {
        Location myLoc= LocationServices.FusedLocationApi
                .getLastLocation(googleApiClient);
        LatLng loc=null;

        if (myLoc != null) {
            double latitude = myLoc.getLatitude();
            double longitude = myLoc.getLongitude();
            loc= new LatLng(latitude, longitude);
        }else{
            Log.e("DANGERZONE","myLoc is null");
            loc= new LatLng(24.369498, 88.626178);
        }

        return loc;
    }

}
