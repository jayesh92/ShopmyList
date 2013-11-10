package com.example.shopmylist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ListActivity extends Activity {
	private String jsonResult;
	private String url3 = "http://10.0.2.2/angelhack/insert_user_list_items.php";
	
	public void accessWebService3(String s1,String s2,String s3,String s4) {
		JsonWriteTask3 task = new JsonWriteTask3();
		// passes values for the urls string array
		Log.d("Before","task.execute2");	
		task.execute(new String[] { url3,s1,s2,s3,s4 });
		Log.d("After","task.execute2");
	}
	
	ArrayAdapter<String> ad;
	ArrayList<String> arr;
	String name,store;
	Context ctx;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		// Show the Up button in the action bar.
		setupActionBar();
		ctx = this;
		ListView l = (ListView) findViewById(R.id.sec_list);
		Log.d("debug","Got ListView");
		Bundle b = getIntent().getBundleExtra("bundle");
		Log.d("debug","Got Bundle");
		arr = b.getStringArrayList("LIST");
		name = b.getString("name");
		store = b.getString("store");
		Log.d("debug","Got array");
		ad = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,arr);
		Log.d("debug","Created adapter");
		l.setAdapter(ad);
		Button bt = (Button) findViewById(R.id.add_bt);
		if(b.getBoolean("add"))
		{

			bt.setVisibility(View.VISIBLE);
			bt.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//build.setMessage("Name Of the Item:");
					LayoutInflater li = LayoutInflater.from(ctx);
					View addD = li.inflate(R.layout.add_dialog, null);
					AlertDialog.Builder build= new AlertDialog.Builder(ctx);


					build.setView(addD);
					final EditText userInput = (EditText) addD.findViewById(R.id.ed1);
					build.setTitle("ADD ITEM");
					build
					.setCancelable(false)
					.setPositiveButton("OK",
							new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							// get user input and set it to result
							// edit text
							//ad.add(userInput.getText().toString());
							//store,name,current_user,order
							arr.add(userInput.getText().toString());
							accessWebService3(store, name, "krans4u", userInput.getText().toString());
							
						}
					})
					.setNegativeButton("Cancel",
							new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,int id) {
							dialog.cancel();
						}
					});
					Log.d("debug","build ban gya");
					// create alert dialog
					AlertDialog alertDialog = build.create();

					// show it
					alertDialog.show();

				}
			});
		}
		else
		{
			bt.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * Set up the {@link android.app.ActionBar}.
	 */

	private void setupActionBar() {

		getActionBar().setDisplayHomeAsUpEnabled(true);

	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
		Log.d("debug", "inbackbuttpressed");
		Intent myintent = new Intent();
		Bundle b = new Bundle();
		b.putStringArrayList("LIST1", arr);
		myintent.putExtra("BUNDLE1", b);
		setResult(1,myintent);
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.list, menu);
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
	
	private class JsonWriteTask3 extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute()
		{
			//do initialization of required objects objects here                
		};

		@Override
		protected String doInBackground(String... params) {
			Log.d("Before","doInBackground3");
			HttpClient httpclient = new DefaultHttpClient();
			Log.d("params",params[0]);						
			HttpPost httppost = new HttpPost(params[0]);
			try 
			{
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	            nameValuePairs.add(new BasicNameValuePair("store", params[1]));
	            nameValuePairs.add(new BasicNameValuePair("name", params[2]));
	            nameValuePairs.add(new BasicNameValuePair("current_user", params[3]));
	            nameValuePairs.add(new BasicNameValuePair("order", params[4]));
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	            //Final Request
	            Log.d("Store",params[1]);
	            Log.d("name",params[2]);
	            Log.d("current_user",params[3]);
	            Log.d("order",params[4]);
				HttpResponse response = httpclient.execute(httppost);
				jsonResult = inputStreamToString(
						response.getEntity().getContent()).toString();
				Log.d("jsonResult3",jsonResult);
			}
			catch (ClientProtocolException e) 
			{
				Log.d("catch","11");
				e.printStackTrace();
			}
			catch (IOException e)
			{
				Log.d("catch","21");
				e.printStackTrace();
			}
			Log.d("After","doInBackground3");
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.d("Before","PostExecute3");
			Log.d("after","PostExecute3");
			
			ad.notifyDataSetChanged();
		}

		private StringBuilder inputStreamToString(InputStream is) {
			String rLine = "";
			StringBuilder answer = new StringBuilder();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));

			try {
				while ((rLine = rd.readLine()) != null) {
					answer.append(rLine);
				}
			}

			catch (IOException e) {
				// e.printStackTrace();
				Toast.makeText(getBaseContext(),
						"Error..." + e.toString(), Toast.LENGTH_LONG).show();
			}
			return answer;
		}

	}// end async task

}
