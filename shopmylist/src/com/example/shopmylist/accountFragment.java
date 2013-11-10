package com.example.shopmylist;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class accountFragment extends Fragment{

	public int flag ;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		flag=0;
		// TODO Auto-generated method stub
		final View v = inflater.inflate(R.layout.account_fragment, container, false);
		Button bt = (Button)v.findViewById(R.id.add_trans);
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				LayoutInflater li = LayoutInflater.from(getActivity());
				View addD = li.inflate(R.layout.add_trans, null);
				AlertDialog.Builder build= new AlertDialog.Builder(getActivity());

				build.setTitle("Add Transaction");
				build.setView(addD);
				build.setPositiveButton("SUBMIT",
						  new DialogInterface.OnClickListener() {
						    public void onClick(DialogInterface dialog,int id) {
							// get user input and set it to result
							// edit text
							//ad.add(userInput.getText().toString());
						    flag=1;
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
		Button bt1 = (Button)v.findViewById(R.id.view_trans);
		bt1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(flag==1)
				{
					TextView tv1= (TextView)v.findViewById(R.id.txt1);
					tv1.setText("jayesh92");
					TextView tv2= (TextView)v.findViewById(R.id.txt2);
					tv2.setText("-100");
				}
			}
		});
				
			
		
		return v;
		
	}
	
	
	
}
