package com.example.shopmylist;
import java.util.HashMap;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.Typeface;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MyExpandableListAdapter extends BaseExpandableListAdapter {

  public final SparseArray<list_parent> parents;
  public LayoutInflater inflater;
  public Activity activity;
  String pp;
   
   HashMap<String, HashMap<String, Integer>> quantity_hashed= new HashMap<String, HashMap<String, Integer>>();
  public MyExpandableListAdapter(Activity act, SparseArray<list_parent> parents) {
    activity = act;
    this.parents = parents;
    inflater = act.getLayoutInflater();
    
  }

  @Override
  public Object getChild(int groupPosition, int childPosition) {
    return parents.get(groupPosition).lists.get(childPosition);
  }

  @Override
  public long getChildId(int groupPosition, int childPosition) {
    return 0;
  }

  @Override
  public View getChildView(int groupPosition, final int childPosition,
      boolean isLastChild, View convertView, ViewGroup parent) {
    Log.d("In getChildView","yeah");
	//final String children = (String) getChild(groupPosition, childPosition).;
    list_parent parent1=(list_parent)getGroup(groupPosition);
    final String par_name=parent1.string;
    pp = par_name;
    if (convertView == null) {
        convertView = inflater.inflate(R.layout.row_child, null);
    }
    TextView text = (TextView) convertView.findViewById(R.id.chtv);
    TextView text1 = (TextView) convertView.findViewById(R.id.chtv1);
    TextView text2 = (TextView) convertView.findViewById(R.id.chtv2);
    list o = (list) getChild(groupPosition, childPosition);
    if(o.name.equals("List Name"))
    {
    	text.setTypeface(null, Typeface.BOLD);
    	text1.setTypeface(null, Typeface.BOLD);
    	text2.setTypeface(null, Typeface.BOLD);
    
    }
    else
    {
    	text.setTypeface(null, Typeface.NORMAL);
    	text1.setTypeface(null, Typeface.NORMAL);
    	text2.setTypeface(null, Typeface.NORMAL);
    
    
    }
    text.setText(o.name);
    
    if(par_name.equals("AllLists"))
    	text1.setText(o.receiver);
    else
    	text1.setText("");
    text2.setText(o.destination);
    
    
    
  return convertView;
  }

  @Override
  public int getChildrenCount(int groupPosition) {
    return parents.get(groupPosition).lists.size();
  }

  @Override
  public Object getGroup(int groupPosition) {
    return parents.get(groupPosition);
  }

  @Override
  public int getGroupCount() {
    return parents.size();
  }

  @Override
  public void onGroupCollapsed(int groupPosition) {
    super.onGroupCollapsed(groupPosition);
  }

  @Override
  public void onGroupExpanded(int groupPosition) {
    super.onGroupExpanded(groupPosition);
  }

  @Override
  public long getGroupId(int groupPosition) {
    return 0;
  }

  @Override
  public View getGroupView(int groupPosition, boolean isExpanded,
      View convertView, ViewGroup parent) {
    if (convertView == null) {
      convertView = inflater.inflate(R.layout.row_parent, null);
    }
    list_parent group = (list_parent) getGroup(groupPosition);
    CheckedTextView ctv=(CheckedTextView) convertView.findViewById(R.id.textView1);
    TextView hd1 = (TextView) convertView.findViewById(R.id.hd1);
    TextView hd2 = (TextView) convertView.findViewById(R.id.hd2);
    TextView hd3 = (TextView) convertView.findViewById(R.id.hd3);
    Log.d("In getGroupView", "Group: " + group.string + " " + ctv.getText().toString());
    ctv.setText(group.string);
    ctv.setChecked(isExpanded);
   /* hd1.setText("List Name");
    if(group.string.equals("AllLists"))
    	hd2.setText("Receiver");
    hd3.setText("Store");
    */
    return convertView;
  }

  @Override
  public boolean hasStableIds() {
    return true;
  }

  @Override
  public boolean isChildSelectable(int groupPosition, int childPosition) {
    return true;
  }


} 