package me.argha.tonu.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import me.argha.tonu.R;
import me.argha.tonu.helpers.MyPreferenceManager;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);
        Button b=(Button) findViewById(R.id.updateBtn);
        EditText nameEt= (EditText)findViewById(R.id.name_et);
        MyPreferenceManager preferenceManager= new MyPreferenceManager(this);
        String name= preferenceManager.pref.getString("name","user");
        nameEt.setText(name);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ProfileActivity.this,"User information has been updated",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
