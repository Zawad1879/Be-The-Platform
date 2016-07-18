package me.argha.tonu.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import me.argha.tonu.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Author: ARGHA K ROY
 * Date: 4/7/2016.
 */
public class ChatActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.expert_chat_layout);
        getSupportActionBar().setTitle("Sabina Apa");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
