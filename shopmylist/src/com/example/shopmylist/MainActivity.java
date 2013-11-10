package com.example.shopmylist;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.util.Log;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends Activity {
	SparseArray<list_parent> parents = new SparseArray<list_parent>(); 
	MyExpandableListAdapter adapter;
	ArrayList<String> my_frnd=new ArrayList<String>();
	public int flag;
	HashMap<String, ArrayList<list>> h = new HashMap<String, ArrayList<list>>();
	Activity ctx;

	public String jsonResult;
	public String jsonResult1;
	public String url = "http://10.0.2.2/angelhack/view_friends.php";
	public String url1 = "http://10.0.2.2/angelhack/view_lists.php";
	public ActionBar actionbar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		actionbar = getActionBar();
		actionbar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		//setContentView(R.layout.activity_main);
		flag=0;
		adapter = new MyExpandableListAdapter(this, parents);
		final list Pizza = new list("Pizza",new ArrayList<String>(){{add("Large");add("Medium");}},"Me","Dominos","undecided");
		final list Grocery = new list("Grocery",new ArrayList<String>(){{add("Sugar");add("Salt");}},"Me","Dominos","undecided");
		h.put("MyLists",new ArrayList<list>());
		h.put("AllLists", new ArrayList<list>());
		ctx=MainActivity.this;
		accessWebService();
		
		//abhishek
		Intent intent = new Intent(this, DownloadService.class);
		Messenger messenger = new Messenger(handler);
		intent.putExtra("MESSENGER", messenger);
		intent.setData(Uri.parse("http://developer.android.com/guide/components/services.html"));
		intent.putExtra("urlpath","http://developer.android.com/guide/components/services.html");
		startService(intent);
		//aend
		
		//accessWebService1();

		//while(flag==0);
	}

	//abhishek
	   private Handler handler = new Handler() {
			
			public void handleMessage(Message message)
			{
				
				Object path = message.obj;
				if(message.arg1 == RESULT_OK && path !=null)
				{
					//Toast.makeText(MainActivity.this, "Downloaded "+path.toString(), Toast.LENGTH_LONG).show();
				}
				else
				{
					//Toast.makeText(MainActivity.this, "Download Failed", Toast.LENGTH_LONG).show();
				}
			}
	};
	

		//aend
	public void accessWebService() {
		JsonWriteTask task = new JsonWriteTask();
		// passes values for the urls string array
		Log.d("Before","task.execute");
		//The Sequence Being The URL, The Table Number, Bill and Customer Name in String Format.
		task.execute(new String[] { url,"krans4u"});
		Log.d("After","task.execute");
	}

	public void accessWebService1() {
		JsonWriteTask1 task1 = new JsonWriteTask1();
		// passes values for the urls string array
		Log.d("Before","task.execute1");
		//The Sequence Being The URL, The Table Number, Bill and Customer Name in String Format.
		task1.execute(new String[] { url1});
		Log.d("After","task.execute1");
	}

	public void ListDrawer()
	{
		Log.d("Before","ListDrawer");
		try {
			Log.d("Before","1");
			JSONObject jsonResponse = new JSONObject(jsonResult);
			Log.d("Before","2");
			JSONArray jsonMainNode = jsonResponse.optJSONArray("friend");
			Log.d("Before","3");

			for (int i = 0; i < jsonMainNode.length(); i++) 
			{
				JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
				String friend2 = jsonChildNode.optString("friend2");
				Log.v("friend2",friend2);
				my_frnd.add(friend2);				
			}
		} 
		catch (JSONException e) {
			Toast.makeText(getApplicationContext(), "Error" + e.toString(),
					Toast.LENGTH_SHORT).show();
		}
	}

	public void ListDrawer1()
	{
		Log.d("Before","ListDrawer1");
		try {
			Log.d("Before","11");
			JSONObject jsonResponse = new JSONObject(jsonResult1);
			Log.d("Before","21");
			JSONArray jsonMainNode = jsonResponse.optJSONArray("list_details");
			Log.d("Before","31");

			for (int i = 0; i < jsonMainNode.length(); i++) 
			{
				JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
				String receiver = jsonChildNode.optString("receiver");
				String sender = jsonChildNode.optString("sender");
				String store = jsonChildNode.optString("store");
				String name = jsonChildNode.optString("name");
				list l = new list(name, new ArrayList<String>(), receiver, store, sender);
				if(receiver.equals("krans4u"))
				{
					h.get("MyLists").add(l);
					if(my_frnd.contains(sender));
					{
						//list l = new list(name, new ArrayList<String>(), receiver, store, sender);
						h.get("AllLists").add(l);
					}
				}
				else
				{
					if(my_frnd.contains(sender));
					{
						//list l = new list(name, new ArrayList<String>(), receiver, store, sender);
						h.get("AllLists").add(l);
					}
				}
			}
		} 
		catch (JSONException e) {
			Toast.makeText(getApplicationContext(), "Error" + e.toString(),
					Toast.LENGTH_SHORT).show();
		}
	}

	private class JsonWriteTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute()
		{

			//do initialization of required objects objects here                
		};


		@Override
		protected String doInBackground(String... params) {
			Log.d("Before","doInBackground");
			HttpClient httpclient = new DefaultHttpClient();
			Log.d("params",params[0]);						
			HttpPost httppost = new HttpPost(params[0]);
			try 
			{
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	            nameValuePairs.add(new BasicNameValuePair("friend1", params[1]));
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	            HttpResponse response = httpclient.execute(httppost);
	            jsonResult = inputStreamToString(response.getEntity().getContent()).toString();
				Log.d("jsonResult",jsonResult);
				/*HttpResponse response = httpclient.execute(httppost);
				jsonResult = inputStreamToString(
						response.getEntity().getContent()).toString();
				Log.d("jsonResult",jsonResult);*/
			}
			catch (ClientProtocolException e) 
			{
				Log.d("catch","1");
				e.printStackTrace();
			}
			catch (IOException e)
			{
				Log.d("catch","2");
				e.printStackTrace();
			}
			Log.d("After","doInBackground");
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.d("Before","PostExecute");
			ListDrawer();
			Log.d("after","PostExecute");
			flag=1;

			accessWebService1();
			createData();

			Tab tab2 = actionbar.newTab();
			tab2.setText("FRIENDS");
			TabListener<friendFragment> tl2 = new TabListener<friendFragment>(ctx, "FRIENDS", friendFragment.class);
			tab2.setTabListener(tl2);
			actionbar.addTab(tab2);
			Tab tab3 = actionbar.newTab();
			tab3.setText("ACCOUNT");
			TabListener<accountFragment> tl3 = new TabListener<accountFragment>(ctx, "ACCOUNT",accountFragment.class);
			tab3.setTabListener(tl3);
			actionbar.addTab(tab3);
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
				Toast.makeText(getApplicationContext(),
						"Error..." + e.toString(), Toast.LENGTH_LONG).show();
			}
			return answer;
		}
	}// end async task

	private class JsonWriteTask1 extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute()
		{

			//do initialization of required objects objects here                
		};

		@Override
		protected String doInBackground(String... params) {
			Log.d("Before","doInBackground1");
			HttpClient httpclient = new DefaultHttpClient();
			Log.d("params",params[0]);						
			HttpPost httppost = new HttpPost(params[0]);
			try 
			{
				HttpResponse response = httpclient.execute(httppost);
				jsonResult1 = inputStreamToString(
						response.getEntity().getContent()).toString();
				Log.d("jsonResult1",jsonResult1);
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
			Log.d("After","doInBackground1");
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.d("Before","PostExecute1");
			ListDrawer1();
			Log.d("after","PostExecute1");
			flag=1;
			createData();
			Tab tab1 = actionbar.newTab();
			tab1.setText("LISTS");
			TabListener<list1Fragment> tl = new TabListener<list1Fragment>(ctx, "LISTS", list1Fragment.class);
			tab1.setTabListener(tl);
			actionbar.addTab(tab1);
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
				Toast.makeText(getApplicationContext(),
						"Error..." + e.toString(), Toast.LENGTH_LONG).show();
			}
			return answer;
		}

	}// end async task

	public void createData()
	{
		int ct=0;
		for(Entry<String,ArrayList<list>> e : h.entrySet() )
		{

			list_parent parent = new list_parent(e.getKey());
			ArrayList<String> l = new ArrayList<String>();
			list a = new list("List Name", l, "Receiver", "Store", "");
			parent.lists.add(a);
			for(list s : e.getValue())
			{
				parent.lists.add(s);
				Log.d("In Create Data", "Added "+s.name+" in " + parent.string);
			}
			parents.append(ct, parent);
			ct++;
		}
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		if(item.getItemId()==R.id.action_settings)
		{
			//Start Mittal/Sagar ki Activity
			Intent intent=new Intent(MainActivity.this,MapActivity.class);
			startActivity(intent);
		}
		return super.onOptionsItemSelected(item);

	}
}
