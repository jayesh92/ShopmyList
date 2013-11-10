package com.example.shopmylist;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class NotifyActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notify);
		// Show the Up button in the action bar.
		setupActionBar();
		Intent myintent=getIntent();
		String[] events=myintent.getStringArrayExtra("events");
		int pos=myintent.getIntExtra("pos", 0);
		//int pos=myintent.getIntExtra("pos", 0)
		
		Log.d("pos ","pos in notify activity "+pos);
		//Log.d("pos ","pos in notify activity "+pos);
		ArrayList< String> event=new ArrayList<String>();
		event.add("Help jayesh92");
		event.add("Help Hkrans4u");
		event.add("Get Help From Mittal");
		event.add("Get Help From Sagar");
		for(int i=4;i<pos;i++){
			Log.d("events","events are "+events[i]);		
			//event.add(events[i]);
		}
		ListView lv=(ListView)findViewById(R.id.listview1);
		lv.setAdapter(new ArrayAdapter(this,android.R.layout.simple_list_item_1,event));
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				String sel = arg0.getItemAtPosition(arg2).toString();
		//		Bundle b=new Bundle();
				Intent intent=new Intent(NotifyActivity.this,DisplayList.class);
				if(sel.startsWith("G") || sel.startsWith("a")){//means get help from friend
					Log.d("found","found a");
					intent.putExtra("flag", "2");//means that you need help of a friend
					intent.putExtra("action", "call the person");
				}
				else if(sel.startsWith("H") || sel.startsWith("h")){//means help a friend
					Log.d("found","found h");
					intent.putExtra("flag", "1");//means that you can help a friend
					intent.putExtra("action", "shoping list will be updated");
				}
				else{
					Log.d("found","found nothing");
				}
				startActivity(intent);
			}
		});
	}

	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notify, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
