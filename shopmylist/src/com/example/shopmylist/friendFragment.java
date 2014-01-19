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

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class friendFragment extends Fragment{
	ArrayAdapter<String> ad;
	ArrayAdapter<String> ad1;
	ArrayList<String> fr_req ;
	ArrayList<String> my_frnds ;
	String frn_name;
	public String url6="";
	//private String url6 = "http://10.0.2.2/angelhack/find_friend.php";
	    
	public  String jsonResult6;
	public void accessWebService6(String s1) {
		url6="http://"+getString(R.string.ip)+"/angelhack/find_friend.php";
		
		JsonWriteTask6 task = new JsonWriteTask6();
		// passes values for the urls string array
		Log.d("Before","task.execute2");	
		task.execute(new String[] { url6,s1 });
		Log.d("After","task.execute2");
	}
	
	private class JsonWriteTask6 extends AsyncTask<String, Void, String> {
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
				nameValuePairs.add(new BasicNameValuePair("friend1", "krans4u"));
	            nameValuePairs.add(new BasicNameValuePair("friend2", params[1]));
	            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	            //Final Request
	            Log.d("Store",params[1]);
	            
				HttpResponse response = httpclient.execute(httppost);
				jsonResult6 = inputStreamToString(
						response.getEntity().getContent()).toString();
				Log.d("jsonResult6",jsonResult6);
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
			
			ad1.notifyDataSetChanged();
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

	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.friend_fragment, container, false);
		/*fr_req = new ArrayList<String>();
		fr_req.add("Seeta");
		fr_req.add("Geeta");
		ListView fr_reqq = (ListView) v.findViewById(R.id.fr_req);
		ad = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,fr_req);
		fr_reqq.setAdapter(ad);
		fr_reqq.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				LayoutInflater li = LayoutInflater.from(getActivity());
				View addD = li.inflate(R.layout.add_dialog, null);
				final AlertDialog.Builder build= new AlertDialog.Builder(getActivity());
				final String req  = ((TextView) arg1).getText().toString();
				build.setTitle("Add "+ req);
				build.setMessage(req + " sent a friend request to you");
				build.setPositiveButton("ACCEPT",
						  new DialogInterface.OnClickListener() {
						    public void onClick(DialogInterface dialog,int id) {
							// get user input and set it to result
							// edit text
							//ad.add(userInput.getText().toString());
						    fr_req.remove(req);
						    ad.notifyDataSetChanged();
						    }
						  });
				build.setNegativeButton("IGNORE",
						  new DialogInterface.OnClickListener() {
						    public void onClick(DialogInterface dialog,int id) {
							dialog.cancel();
						    }
						    
				});
				AlertDialog d = build.create();
				d.show();
			}
		});
		*/
		//ArrayList<String> my_frnds = new ArrayList<String>();
		 my_frnds = ((MainActivity)getActivity()).my_frnd;
		/*my_frnds.add("Lahori");
		my_frnds.add("Ballu");
		my_frnds.add("Mittal");*/
		ListView my_frnds1 = (ListView) v.findViewById(R.id.my_frnds);
		ad1 = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, my_frnds);
		my_frnds1.setAdapter(ad1);
		Button addfrnd = (Button) v.findViewById(R.id.addfrnd);
		addfrnd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LayoutInflater li = LayoutInflater.from(getActivity());
				View addD = li.inflate(R.layout.addfriend, null);
				final EditText userInput = (EditText) addD.findViewById(R.id.aed1);
				
				final AlertDialog.Builder build= new AlertDialog.Builder(getActivity());
				
				build.setTitle("ADD A NEW FRIEND");
				build.setView(addD);
				build.setPositiveButton("ADD",
						  new DialogInterface.OnClickListener() {
						    public void onClick(DialogInterface dialog,int id) {
							// get user input and set it to result
							// edit text
							//ad.add(userInput.getText().toString());
						    //Check if userInput.getText().toString() is in users database
						    my_frnds.add(userInput.getText().toString());
						    accessWebService6(userInput.getText().toString());
						 //   dialog.cancel();
						    }
						  });
				build.setNegativeButton("CANCEL",
						  new DialogInterface.OnClickListener() {
						    public void onClick(DialogInterface dialog,int id) {
							dialog.cancel();
						    }
						    
				});
				AlertDialog d = build.create();
				d.show();
	
			
			}
		});
		return v;
		
	}
	
	
	
}