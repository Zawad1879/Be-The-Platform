package me.argha.tonu.activity;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;
import me.argha.tonu.R;
import me.argha.tonu.app.Config;
import me.argha.tonu.app.EndPoints;
import me.argha.tonu.gcm.GcmIntentService;
import me.argha.tonu.helpers.EmergencyContactsDataSource;
import me.argha.tonu.helpers.MyPreferenceManager;
import me.argha.tonu.model.Contact;
import me.argha.tonu.utils.Util;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    Bitmap bmp;
    @Bind(R.id.mainHelpBtn)
    ImageView mainHelpBtn;
    String filename;
    String username;
    EmergencyContactsDataSource contactsDataSource;

    BroadcastReceiver broadcastReceiver;
    MyPreferenceManager preferenceManager;
    boolean clicked=false;
    GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);
        setUpGoogleApiClient();
        preferenceManager= new MyPreferenceManager(this);
        Log.e(TAG, String.valueOf(preferenceManager.pref.getBoolean(getResources().getString(R.string
                .is_user_logged_in),false)));
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        handleBroadcastReceivers();
        contactsDataSource= new EmergencyContactsDataSource(this);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Intent intent = getIntent();
        Bitmap bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");

        username = getIntent().getStringExtra("userName");

    //    imageViewpropic.setImageBitmap(bitmap);
        View hView =  navigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.textView);
        ImageView nav_propic=(ImageView)hView.findViewById(R.id.profilePic);
        nav_user.setText(username);
        nav_propic.setImageBitmap(bitmap);
    //    Picasso.with(this).load("http://graph.facebook.com/" + "10153459358326496" + "/picture?type=small").into(nav_propic);
    //    nav_propic.setVisibility(View.VISIBLE);


    }

    private void handleBroadcastReceivers() {
        broadcastReceiver= new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if(intent.getAction().equals(Config.REGISTRATION_COMPLETE)){
                    String token= intent.getStringExtra("token");
                    Util.showToast(MainActivity.this, token);
                    Log.i(TAG,"GCM Token "+token);
                }else if(intent.getAction().equals(Config.SENT_TOKEN_TO_SERVER)){
                    Util.showToast(MainActivity.this, "GCM Token stored in server");
                }else if(intent.getAction().equals(Config.PUSH_NOTIFICATION)){
                    Util.showToast(MainActivity.this, "Push Notification has been received");
                }
            }
        };
        if(checkPlayServices()){
            registerGCM();
        }

    }

    private void registerGCM() {
        Intent intent= new Intent(this, GcmIntentService.class);
        intent.putExtra("key","register");
        startService(intent);
    }

    private boolean checkPlayServices(){
        GoogleApiAvailability googleApiAvailability= GoogleApiAvailability.getInstance();
        int resultCode= googleApiAvailability.isGooglePlayServicesAvailable(this);
        if(resultCode != ConnectionResult.SUCCESS){
            if(googleApiAvailability.isUserResolvableError(resultCode)){
                googleApiAvailability.getErrorDialog(this, resultCode, Config.PLAY_SERVICES_RESOLUTION_REQUEST).show();

            }else{
                Log.i(TAG, "Device does not support google play services");
                Util.showToast(this, "This device does not support google play services!");
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter(Config.REGISTRATION_COMPLETE));
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter(Config.PUSH_NOTIFICATION));
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver);
    }

    @OnClick({R.id.mainReportBtn,R.id.mainDangerZoneBtn,R.id.mainHelpBtn,R.id.mainExpertHelpBtn})
    public void mainBtnClicks(View view){
        switch (view.getId()){
            case R.id.mainReportBtn:
                startActivity(new Intent(this,SharedLocationActivity.class));
                break;
            case R.id.mainDangerZoneBtn:
                Toast.makeText(MainActivity.this, "Showing area-crime densities",
                        Toast
                        .LENGTH_SHORT).show();
                startActivity(new Intent(this,DangerZone.class));
                break;
            case R.id.mainHelpBtn:
                if(!clicked){

                    //TODO get location
                    String name= preferenceManager.pref.getString("name","User");
                    LatLng myLocation=getMyLocation();
                    double lat=myLocation.latitude,lon= myLocation.longitude;
                    String messageToSend = name+" is in an emergency and would like your help" +
                            ".\nAddress: Map coordinates\nLocation: "+lat+", "+lon+"\n" +
                            "http://maps.google.com/?q="+lat+", "+lon;
//                    String number = "01621209959";
                    List<Contact> numbers= contactsDataSource.getAllContacts();
                    for(Contact n: numbers){
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            // Call some material design APIs here

                        } else {
                            // Implement this feature without material design
//                            SmsManager.getDefault().sendTextMessage(n, null, messageToSend, null,null);
                        }
                        String [] numArray= {n.getNumber()};
                        AsyncHttpClient asyncHttpClient= new AsyncHttpClient();
                        RequestParams params= new RequestParams();
                        params.put("user_id",preferenceManager.pref.getString("user_id",
                                "default_user"));
                        params.put("location",lat+","+lon);
                        params.put("receiver_numbers",numArray);
                        asyncHttpClient.post(EndPoints.SENDLOCATIONBYNUMBER,params,new JsonHttpResponseHandler(){
                            @Override
                            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                                super.onSuccess(statusCode, headers, response);
                                try {
                                    Log.e(TAG,response.toString(4));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        Log.e("MainActivity","Sending a message to "+n);
                    }

                  //  showBeeAnimation();
                    mainHelpBtn.setImageResource(R.drawable.bebutton);
                    clicked=true;
                    Util.showToast(this, "Alert has been sent to emergency contacts and nearest " +
                            "police stations");
                    android.os.Handler handler= new android.os.Handler();
                    mainHelpBtn.setImageResource(R.drawable.afterbee);


                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //mainHelpBtn.setImageResource(R.drawable.afterbee);
                            mainHelpBtn.setImageResource(R.drawable.bebutton);

                        }
                    }, 200);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mainHelpBtn.setImageResource(R.drawable.afterbee);


                        }
                    }, 700);

                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            mainHelpBtn.setImageResource(R.drawable.bebutton);


                        }
                    }, 1200);




                }else {
                    clicked=false;
                    mainHelpBtn.setImageResource(R.drawable.bebutton);
                }
              //  showBeeAnimation();
                break;
            case R.id.mainExpertHelpBtn:
                showHelpOptionDialog();
                break;
            /*case R.id.mainForumBtn:
                startActivity(new Intent(this,ForumActivity.class));
                break;*/

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
            Log.e(TAG,"myLoc is null");
            loc= new LatLng(24.372062,88.624140);
        }

        return loc;
    }

    private void showBeeAnimation() {
        mainHelpBtn.setImageResource(R.drawable.bebutton);
        clicked=true;
        Util.showToast(this,"Alert has been sent to emergency contacts and nearest " +
                "police stations");
        android.os.Handler handler= new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mainHelpBtn.setImageResource(R.drawable.afterbee);
            }
        }, 500);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mainHelpBtn.setImageResource(R.drawable.bebutton);
            }
        }, 500);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mainHelpBtn.setImageResource(R.drawable.afterbee);
            }
        }, 500);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mainHelpBtn.setImageResource(R.drawable.bebutton);
            }
        }, 500);
    }


    private void showHelpOptionDialog() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this)
                .setSingleChoiceItems(new String[]{"CHAT","TALK"}, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                startActivity(new Intent(MainActivity.this,ChatActivity.class));
                                break;
                            case 1:
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:1234"));
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    // Call some material design APIs here

                                } else {
                                    // Implement this feature without material design
                                    startActivity(callIntent);
                                }

                                break;
                        }
                        dialog.dismiss();
                    }
                })
                .setTitle("Please select - ");

        Dialog dialog=builder.create();
        dialog.show();
    }


    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
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
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_help:
                startActivity(new Intent(this,IdentifyActivity.class));
                break;
            case R.id.menu_item_notification:
                startActivity(new Intent(this,NotificationActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent intent=null;
        int id = item.getItemId();
        if (id == R.id.settings) {
            intent= new Intent(MainActivity.this,SettingsActivity.class);

        } else if (id == R.id.emergency_contacts) {
            intent= new Intent(MainActivity.this,EmergencyContactsActivity.class);
        } else if (id == R.id.profile) {
            intent= new Intent(MainActivity.this,ProfileActivity.class);
        } else if (id == R.id.faq) {
            intent= new Intent(MainActivity.this,FAQActivity.class);
        } else if (id == R.id.logout) {
            preferenceManager.clear();
            preferenceManager.editor.putBoolean(getResources().getString(R.string
                    .is_user_logged_in),false);
            preferenceManager.editor.clear();
            preferenceManager.editor.commit();

            intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
            finish();
        }
        else if (id == R.id.laws) {

        } else if (id == R.id.terms_and_conditions) {

        }
        if(intent!=null) startActivity(intent);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setUpGoogleApiClient() {
        googleApiClient= new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                    @Override
                    public void onConnected(Bundle bundle) {

                    }

                    @Override
                    public void onConnectionSuspended(int i) {

                    }
                })
                .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                    @Override
                    public void onConnectionFailed(ConnectionResult connectionResult) {

                    }
                })
                .addApi(LocationServices.API)
                .build();

    }

    private void googleApiClientConnect(){
        if(!googleApiClient.isConnected()) {
            googleApiClient.connect();
        }
    }


}
