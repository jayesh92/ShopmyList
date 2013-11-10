package com.example.shopmylist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Activity {
	public int mId=1;
	public GoogleMap mMap=null;
	public double lat=17.4550,longi=78.4550;
	public Marker marker;
	public List< LatLng> points = new ArrayList< LatLng>();
	public List< LatLng> friend_locs = new ArrayList< LatLng>();
	public List< LatLng> shop_locs = new ArrayList< LatLng>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setUpMapIfNeeded();
    }
    public void updatelocs(){//it changes the location of the delivery boy and updates it on the map
    	for(int i=0;i<(int)friend_locs.size();i++){
    		marker=mMap.addMarker(new MarkerOptions()
    		.position(friend_locs.get(i))
    		.title("friend"+i));
    	}
    	for(int i=0;i<(int)shop_locs.size();i++){
    		marker=mMap.addMarker(new MarkerOptions()
    		.position(shop_locs.get(i))
    		.title("shop"+i)
    		.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
    	}
    	CameraUpdate update = CameraUpdateFactory.newLatLngZoom(shop_locs.get(0),14);
    	mMap.animateCamera(update);
    }
    

    void addfriend_shop_locs(){
    	double a=lat,b=longi;
    	for(int i=0;i<10;i++){
    		friend_locs.add(new LatLng(a,b)); // add points to the list for displaying the path travelled
    		if(i%2==0){a+=0.01;b+=0.01;}else{a+=0.01;b-=0.01;}
    	}
    	a=lat-0.05;b=longi-0.05;
    	for(int i=0;i<5;i++){    	
    		shop_locs.add(new LatLng(a,b)); // add points to the list for displaying the path travelled
    		if(i%2==0){a+=0.01;b+=0.01;}else{a+=0.01;b-=0.01;}
    	}
    }
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
                                .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
            	//for(int i=0;i<10;i++){
            		//changelocation(i);
            		//Toast.makeText(this, "sleeping"+i, Toast.LENGTH_SHORT).show();
            	//}
            	addfriend_shop_locs();
            	updatelocs();
                // The Map is verified. It is now safe to manipulate the map.
            }
        }
    }
}