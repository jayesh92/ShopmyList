package com.example.shopmylist;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
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

public class friendFragment extends Fragment{
	ArrayAdapter<String> ad;
	ArrayList<String> fr_req ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View v = inflater.inflate(R.layout.friend_fragment, container, false);
/*		fr_req = new ArrayList<String>();
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
		
*/		//ArrayList<String> my_frnds = new ArrayList<String>();
		ArrayList<String> my_frnds = ((MainActivity)getActivity()).my_frnd;
		/*my_frnds.add("Lahori");
		my_frnds.add("Ballu");
		my_frnds.add("Mittal");*/
		ListView my_frnds1 = (ListView) v.findViewById(R.id.my_frnds);
		my_frnds1.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, my_frnds));
		Button addfrnd = (Button) v.findViewById(R.id.addfrnd);
		addfrnd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				LayoutInflater li = LayoutInflater.from(getActivity());
				View addD = li.inflate(R.layout.addfriend, null);
				EditText userInput = (EditText) addD.findViewById(R.id.aed1);
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
						    dialog.cancel();
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
