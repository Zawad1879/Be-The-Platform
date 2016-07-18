package me.argha.tonu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import me.argha.tonu.R;
import me.argha.tonu.helpers.MyPreferenceManager;
import me.argha.tonu.utils.AppConst;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);
        final MyPreferenceManager preferenceManager= new MyPreferenceManager(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(preferenceManager.pref.getBoolean(getResources().getString(R.string
                        .is_user_logged_in),false)==true){
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    finish();
                }
                else{
                    startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                    finish();
                }
            }
        }, AppConst.SPLASH_DELAY);


    }
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
