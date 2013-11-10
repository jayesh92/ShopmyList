package com.example.shopmylist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Messenger;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

public class DownloadService extends IntentService implements LocationListener{

	private static final int FLAG_UPDATE_CURRENT = 134217728;
	private int result = Activity.RESULT_CANCELED;
	private int i=0;
	public List< LatLng> friend_locs = new ArrayList< LatLng>();
	public List< LatLng> shop_locs = new ArrayList< LatLng>();
	public List< LatLng> my_shop_locs = new ArrayList< LatLng>();	
	public double lat=17.4550,longi=78.4550;
	public double mylat=17.4550,mylongi=78.4550;
	
	//for location updation
	LocationManager lm;
	TextView lt, ln;
	String provider;
	Location l;
	Handler handler;
	Runnable location_status_checker;
    public int mId=1;
	public int a=1;

	
	@Override
	public void onLocationChanged(Location arg0)
	{
		l=lm.getLastKnownLocation(provider);
		double lng=l.getLongitude();
		double lat=l.getLatitude();
		Toast.makeText(this, "Found New location"+a, Toast.LENGTH_SHORT).show();
		senddata("name",lat,lng);
		a=a+1;
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
	}
	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
	}
	
//end
	

	
	public DownloadService() {
		super("DownloadService");
		// TODO Auto-generated constructor stub
	}

    void addfriend_shop_locs(){
    	double a=lat,b=longi;
    	for(int i=0;i<5;i++){
    		friend_locs.add(new LatLng(a,b)); // add points to the list for displaying the path travelled
    		if(i%2==0){a+=0.001;b+=0.001;}else{a+=0.001;b-=0.001;}
    	}
    	a=lat-0.05;b=longi-0.05;
    	for(int i=0;i<5;i++){    	
    		if(i%2==0){a+=0.001;b+=0.001;}else{a+=0.001;b-=0.001;}
    		shop_locs.add(new LatLng(a,b)); // add points to the list for displaying the path travelled
    	}
    }

    void notifyme(String[] events,int pos){
    	Log.d("pos","pos in notifyme is "+pos);
    	NotificationCompat.Builder mBuilder =
    	        new NotificationCompat.Builder(this)
    	        .setSmallIcon(R.drawable.notification_icon)
    	        .setContentTitle("My notification")
    	        .setContentText("Do something");
    	
    	NotificationCompat.InboxStyle inboxStyle =
    	        new NotificationCompat.InboxStyle();
    	
    	inboxStyle.setBigContentTitle("Event tracker details:");
    	// Moves events into the big view
    	for (int i=0; i < pos; i++) {

    	    inboxStyle.addLine(events[i]);
    	}
    	// Moves the big view style object into the notification object.
    	mBuilder.setStyle(inboxStyle);
    	// Creates an explicit intent for an Activity in your app
    	Intent resultIntent = new Intent(this, NotifyActivity.class);
    	resultIntent.putExtra("events", events);
    	resultIntent.putExtra("pos", (int)pos);
    	// The stack builder object will contain an artificial back stack for the
    	// started Activity.
    	// This ensures that navigating backward from the Activity leads out of
    	// your application to the Home screen.
    	PendingIntent resultPendingIntent=PendingIntent.getActivity(this, 0, resultIntent, FLAG_UPDATE_CURRENT);
    	mBuilder.setContentIntent(resultPendingIntent);
    	NotificationManager mNotificationManager =
    	    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    	// mId allows you to update the notification later on.
    	mNotificationManager.notify(mId, mBuilder.build());
    }
    void checkdistance(){
    	//notify me if I am near any store from which stuff has to be bought
    	//notifyme();
    	mylat=lat-0.05;
    	mylongi=longi-0.05;
    	LatLng mytemp=new LatLng(mylat,mylongi);
    	String[] events=new String[30];
    	int pos=0;
    	for(int i=0;i<shop_locs.size();i++){
    		LatLng temp=shop_locs.get(i);
    		double value=(double)(mytemp.latitude-temp.latitude)*(mytemp.latitude-temp.latitude)+(mytemp.longitude-temp.longitude)*(mytemp.longitude-temp.longitude);
    		Log.d("distance is ","distance "+value);
    		if(value<0.0002){
    			events[pos]="help your friend";
    			pos+=1;
    			//now we need to give info about helps to can make
    		}
    	}
    	//notify me if someone is near any store from which i need stuff
    	Log.d("nos are","fsz "+friend_locs.size()+" msz"+my_shop_locs.size());
    	for(int i=0;i<friend_locs.size();i++){
    		for(int j=0;j<my_shop_locs.size();j++){
    			LatLng mytemp2=friend_locs.get(i);
        		LatLng temp=my_shop_locs.get(j);
        			temp=mytemp2;
        		double value=(double)(mytemp2.latitude-temp.latitude)*(mytemp2.latitude-temp.latitude)+(mytemp2.longitude-temp.longitude)*(mytemp2.longitude-temp.longitude);
        		Log.d("distance2 is ","distance2 "+value);
        		if(value<0.0002){
        			events[pos]="a friend can help you";
        			pos+=1;
        			//now we need to give info about helps that is given to me
        		}
    		}
    	}
    	Log.d("pos is ","pos "+pos);
    	notifyme(events,pos);
    }
    public void myshoplocs(){//extract values from database of my locs and put in my_shop_locs
		my_shop_locs.add(new LatLng(17.44,78.44)); // add points to the list for displaying the path travelled    	
    }
    public void senddata(String s,double lat,double lng ){
		//Toast.makeText(this, lng+" "+lat, Toast.LENGTH_LONG).show();			
		mylat=lat;
		mylongi=lng;
    	Log.d("lat is "+lat,"lng is "+lng);
	}
	public int nonetwork=0;

	@Override
	protected void onHandleIntent(Intent intent) {
		
		Log.d("Debug","In HandleIntent");
		// TODO Auto-generated method stub
		Uri data = intent.getData();
		String uriPath = intent.getStringExtra("urlpath");
		try
		{
			//Toast.makeText(this, uriPath+data.toString()+i, Toast.LENGTH_LONG).show();			
			Log.d("hello",uriPath+data.toString()+i);
			lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			/*location_status_checker = new Runnable() {
					public void run() {
						long ctime = System.currentTimeMillis();
						handler.postDelayed(location_status_checker,1000);
					}
				};*/

			Criteria c=new Criteria();
			//criteria object will select best service based on
			//Accuracy, power consumption, response, bearing and monetary cost
			//set false to use best service otherwise it will select the default Sim network
			//and give the location based on sim network 
			//now it will first check satellite than Internet than Sim network location
			provider=lm.getBestProvider(c, false);
			Log.d("provider is",provider.toString()+nonetwork);
			//Toast.makeText(this, provider.toString()+nonetwork, Toast.LENGTH_SHORT).show();
			//now you have best provider
			//get location
			nonetwork++;

			l=lm.getLastKnownLocation(provider);
			lm.requestLocationUpdates(provider, 1000, 1,this);
			if(l!=null)
			{
				//get latitude and longitude of the location
				double lng=l.getLongitude();
				double lat=l.getLatitude();
				senddata("name",lat,lng);
				addfriend_shop_locs();
				myshoplocs();
				checkdistance();
				//display on text view
				//ln.setText(""+lng);
				//lt.setText(""+lat);
			}
			else
			{
				senddata("name",0.0,0.0);
			}	
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		 Bundle extras = intent.getExtras();
		 if(extras != null)
		 {
			 Messenger messenger = (Messenger)extras.get("MESSENGER");
			 Message message = Message.obtain();
			 message.arg1= result;
			 message.obj = "Yahoo";
			 try
			 {
				 messenger.send(message);
				 Log.d("Debug","Message Sent");	 
			 }
			 catch(android.os.RemoteException re)
			 {
				 Log.w(getClass().getName(), "Exception sending Message",re);
			 }
		 }
		 
	}
}
