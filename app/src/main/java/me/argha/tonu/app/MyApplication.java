package me.argha.tonu.app;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import me.argha.tonu.R;
import me.argha.tonu.activity.LoginActivity;
import me.argha.tonu.helpers.MyPreferenceManager;

/**
 * Author: ARGHA K ROY
 * Date: 4/6/2016.
 */
public class MyApplication extends Application {
    public static final String TAG = MyApplication.class
            .getSimpleName();

    private static MyApplication mInstance;

    private MyPreferenceManager pref;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                        .setDefaultFontPath("fonts/ubuntu_normal.ttf")
//                        .setFontAttrId(R.attr.fontPath)
//                        .build()
//        );
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(/*CalligraphyContextWrapper.wrap*/(base));
    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }


    public MyPreferenceManager getPrefManager() {
        if (pref == null) {
            pref = new MyPreferenceManager(this);
        }

        return pref;
    }

    public void logout() {
        getPrefManager();
        pref.clear();
        pref.editor.putBoolean(getResources().getString(R.string.is_user_logged_in),false);
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
