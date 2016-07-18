package me.argha.tonu.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import me.argha.tonu.R;

/**
 * Created by sbsatter on 4/6/2016.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<String> itemHeader;
    private HashMap<String,String> items;

    public ExpandableListAdapter(Context context, List<String> itemHeader, HashMap item){
        this.context=context;
        this.itemHeader=itemHeader;
        this.items =item;
    }

    @Override
    public int getGroupCount() {
        return this.itemHeader.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
//        return this.items.get(itemHeader.get(groupPosition)).size();
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.itemHeader.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.items.get(this.itemHeader.get(groupPosition));
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expandable_list_title, null);
        }

        TextView headerTitleText = (TextView) convertView
                .findViewById(R.id.expandable_list_header);
        headerTitleText.setTypeface(null, Typeface.BOLD);
        headerTitleText.setText(headerTitle);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String itemDetailText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expandable_list_item, null);
        }

        TextView itemText = (TextView) convertView
                .findViewById(R.id.expandable_list_text);

        itemText.setText(itemDetailText);
        return convertView;

    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
