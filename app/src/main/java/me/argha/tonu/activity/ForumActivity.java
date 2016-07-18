package me.argha.tonu.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.argha.tonu.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Author: ARGHA K ROY
 * Date: 4/7/2016.
 */
public class ForumActivity extends AppCompatActivity {

    @Bind(R.id.forumMainList)
    ListView forumListView;
    ArrayList<String> listTitle,listDetail;
    HashMap<String,String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forum_layout);
        ButterKnife.bind(this);
        prepareList();
        forumListView.setAdapter(new ForumListAdapter());
    }


    public void prepareList(){
        listTitle = new ArrayList<String>();
        listDetail = new ArrayList<String>();
        map= new HashMap<>();
        listTitle.add("I made it!");
        listTitle.add("Thanks for your support.");
        listTitle.add("Help ME!!");
        listTitle.add("Support me!");
        listTitle.add("Don't turn away from me.");
        listDetail.add("I would have lost it by now, too, without thhis forum-especially in the early dayss.I think my ex is wondering why I haven't naively fallen for his games, or crumbled, or seem to be one step ahead of him.Little does he know, I have found amazing support and wisdom both here and at my local DV support group. He is probably wodering where all my strength has come from! Three cheers for this forum, and for all beautiful and kind women on here.");
        listDetail.add("All I can say is thanks to this forum. I would not be able to express all" +
                " of my feelings without it. Moreover, I have learned about help and support. Again thanks to this forum, somebody atleast takes me to consideration.\n");
        listDetail.add("I took this photo from facebook. This guy was harrassing me throuh facebook by posting his comments on my photo. Need your help badly.");
        listDetail.add("I don't know what to do, a bunch of guys are bullying me when I go back to home after my class. I seek for help but nobody pays any heeds. \n" +
        listDetail.add("Please help me from these rascals. I am attaching some images of them " +
                "which I have taken in very tight situation. Please ! Please !"));

        int i=0;
        for(String h1:listTitle){
            map.put(h1,listDetail.get(i));
            i++;
        }

    }

    @OnClick(R.id.fab)
    public void fabClick(){
        startActivity(new Intent(this,NewForumPostActivity.class));
    }

    class ForumListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return listTitle.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View row=getLayoutInflater().inflate(R.layout.single_forum_list_item,null, false);
            TextView h1= (TextView) row.findViewById(R.id.tv_title);
            TextView p1= (TextView) row.findViewById(R.id.tv_list);
            h1.setText(listTitle.get(position));
            p1.setText(map.get(listTitle.get(position)));
            return row;
        }

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(base));
    }
}
