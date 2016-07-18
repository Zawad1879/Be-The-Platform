package me.argha.tonu.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.argha.tonu.R;

public class NotificationActivity extends AppCompatActivity {

    @Bind(R.id.fab)
    FloatingActionButton fabBtn;

    @Bind(R.id.forumMainList)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_layout);
        ButterKnife.bind(this);
        fabBtn.setVisibility(View.GONE);

        String items[]={"Someone has commented in your post.",
        "Anonymous has upvoted your post",
        "Fariha has asked for your help"};

        listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position<2){
                    startActivity(new Intent(NotificationActivity.this,ForumActivity.class));
                }else {
                    Toast.makeText(NotificationActivity.this, "Fariha is here", Toast.LENGTH_SHORT)
                            .show();
                    startActivity(new Intent(NotificationActivity.this,DangerZone.class));
                }
            }
        });
    }
}
