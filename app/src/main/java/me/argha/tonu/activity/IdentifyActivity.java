package me.argha.tonu.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.argha.tonu.R;

public class IdentifyActivity extends AppCompatActivity {
    @Bind(R.id.identify_submit_btn)
    Button submitBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify);
        ButterKnife.bind(this);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(IdentifyActivity.this, "Your data is being analyzed. You can come " +
                        "back anytime and edit your answers.", Toast
                        .LENGTH_SHORT).show();
            }
        });
    }

}
