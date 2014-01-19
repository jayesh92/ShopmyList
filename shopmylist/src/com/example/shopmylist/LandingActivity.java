package com.example.shopmylist;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LandingActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_landing);
		Button bt =(Button) findViewById(R.id.login);
		final EditText userName = (EditText)findViewById(R.id.user_id);
		bt.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if(userName.getText().toString().equals("krans4u"))
				{
					Intent intent = new Intent(getBaseContext(),MainActivity.class);
					startActivity(intent);
				}
				
				else
				{
					Toast.makeText(getBaseContext(), "User Doesn't Exist", Toast.LENGTH_SHORT).show();
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.landing, menu);
		return true;
	}

}