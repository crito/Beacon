package net.thirtyloops.hitchhiking.beacon;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class Beacon extends Activity {
	private LocationManager lm;
	private LocationListener locationListener;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        
        //---- Obtain GPS location
        lm = (LocationManager)
        	getSystemService(Context.LOCATION_SERVICE);
        
        locationListener = new MyLocationListener();
        
        lm.requestLocationUpdates(
        		LocationManager.GPS_PROVIDER,
        		0,
        		0,
        		locationListener);
    }
    
    private class MyLocationListener implements LocationListener
    {
    	public void onLocationChanged(Location loc) {
    		if (loc != null) {
    			Toast.makeText(getBaseContext(), 
    					"Location changed: Lat: " + loc.getLatitude() + 
    					" Lng: " + loc.getLongitude(), 
    					Toast.LENGTH_SHORT).show();
    		}
    	}
    	public void onProviderDisabled(String provider) {
    		//TODO
    	}
    	public void onProviderEnabled(String provider) {
    		//TODO
    	}
    	public void onStatusChanged(String provider, int status, Bundle extras) {
    		//TODO
    	}
    }
}