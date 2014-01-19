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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.Toast;

public class list1Fragment extends Fragment{
	private String jsonResult;
	public String url2="";
	//private String url2 = "http://10.0.2.2/angelhack/insert_user_list.php";
	
	public list l;
	
	public void accessWebService2(String s1,String s2,String s3,String s4,String s5) {
		url2="http://"+getString(R.string.ip)+"/angelhack/insert_user_list.php";
		
		JsonWriteTask2 task = new JsonWriteTask2();
		// passes values for the urls string array
		Log.d("Before","task.execute2");	
		task.execute(new String[] { url2,s1,s2,s3,s4,s5 });
		Log.d("After","task.execute2");
	}

	// build hash set for list view
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.list1_fragment, container, false);
		ExpandableListView listView = (ExpandableListView) v.findViewById(R.id.listView);
	    listView.setGroupIndicator(null);
	    listView.setAdapter(((MainActivity)getActivity()).adapter);
	    listView.setOnChildClickListener(new OnChildClickListener() {
			
			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				Intent in = new Intent(getActivity(),ListActivity.class);
				Bundle b = new Bundle();
				MyExpandableListAdapter ad = ((MainActivity)getActivity()).adapter;
				 l = (list) ad.getChild(groupPosition, childPosition);
				
				b.putStringArrayList("LIST", (ArrayList<String>) l.lists);
				b.putString("store", l.destination);
				b.putString("receiver", l.receiver);
				b.putString("sender", l.shopper);
				b.putString("name", l.name);
				list_parent par=(list_parent) ad.getGroup(groupPosition);
				if(par.string.equals("MyLists"))
					b.putBoolean("add", true);
				else
					b.putBoolean("add", false);
				in.putExtra("bundle", b);
				startActivityForResult(in, 1);
				return false;
			}
		});
	    Button add_new = (Button) v.findViewById(R.id.add_new_list);
	    add_new.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LayoutInflater li = LayoutInflater.from(getActivity());
				View addD = li.inflate(R.layout.add_new_dialog, null);
				AlertDialog.Builder build= new AlertDialog.Builder(getActivity());
				
				
				build.setView(addD);
				final EditText userInput = (EditText) addD.findViewById(R.id.ed1);
				final EditText userInput1 = (EditText) addD.findViewById(R.id.ed2);
				build.setTitle("ADD LIST");
				build.setView(addD);
				
				
				build.setCancelable(false)
				.setPositiveButton("OK",
				  new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog,int id) {
					// get user input and set it to result
					// edit text
					//ad.add(userInput.getText().toString());
				    	l = new list(userInput.getText().toString(), 
					    		new ArrayList<String>(), "me", 
					    		userInput1.getText().toString(), "undecided");
				    	accessWebService2("krans4u","undecided",userInput1.getText().toString(),
				    			userInput.getText().toString(),"krans4u");
				    
				    /*((MainActivity)getActivity()).parents.get(0).lists.add(l);
				    ((MainActivity)getActivity()).adapter.notifyDataSetChanged();*/
				    }
				  })
				.setNegativeButton("CANCEL",
				  new DialogInterface.OnClickListener() {
				    public void onClick(DialogInterface dialog,int id) {
					dialog.cancel();
				    }
				  });
			Log.d("debug","build ban gya");
			// create alert dialog
			AlertDialog alertDialog = build.create();
			alertDialog.show();
			
		}
	    });
		return v;
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.d("debug","back from acti");
		 if(requestCode==1)
		 {
			 Log.d("debug","back from acti1"+ data);
			 if(null!=data)
			 {
				 //Log.d("return",data.getStringExtra("BUNDLE1"));
				Bundle ex = data.getBundleExtra("BUNDLE1");
				l.lists = ex.getStringArrayList("LIST1");
				 
			 }
			}
	}
	
	private class JsonWriteTask2 extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute()
		{

			//do initialization of required objects objects here                
		};

		@Override
		protected String doInBackground(String... params) {
			Log.d("Before","doInBackground2");
			HttpClient httpclient = new DefaultHttpClient();
			Log.d("params",params[0]);						
			HttpPost httppost = new HttpPost(params[0]);
			try 
			{
				ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	            nameValuePairs.add(new BasicNameValuePair("receiver", params[1]));
	            nameValuePairs.add(new BasicNameValuePair("sender", params[2]));
	            nameValuePairs.add(new BasicNameValuePair("store", params[3]));
	            nameValuePairs.add(new BasicNameValuePair("name", params[4]));
	            nameValuePairs.add(new BasicNameValuePair("current_user", params[5]));
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	            //Final Request
				HttpResponse response = httpclient.execute(httppost);
				jsonResult = inputStreamToString(
						response.getEntity().getContent()).toString();
				Log.d("jsonResult1",jsonResult);
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
			Log.d("After","doInBackground2");
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.d("Before","PostExecute2");
			
		    ((MainActivity)getActivity()).parents.get(0).lists.add(l);
		    ((MainActivity)getActivity()).adapter.notifyDataSetChanged();
			Log.d("after","PostExecute2");
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
				Toast.makeText(getActivity(),
						"Error..." + e.toString(), Toast.LENGTH_LONG).show();
			}
			return answer;
		}

	}// end async task
		
}
