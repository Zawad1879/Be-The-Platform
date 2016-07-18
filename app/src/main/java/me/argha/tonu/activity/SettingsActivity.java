package me.argha.tonu.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import me.argha.tonu.R;
import me.argha.tonu.app.EndPoints;

public class SettingsActivity extends AppCompatActivity {

    EditText address;
    Button update;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        address=(EditText)findViewById(R.id.ipBox);
        update=(Button)findViewById(R.id.updateButton);
        address.setText(EndPoints.BASE);
        address.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {

                }
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EndPoints.BASE=address.getText().toString();
                Toast.makeText(SettingsActivity.this,"IP address updated",Toast.LENGTH_SHORT).show();
            }
        });


    }

}
