package net.thirtyloops.hitchhiking.beacon;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

public class Beacon extends Activity {
	private final String TAG = "Beacon";
	
	private LocationManager lm;
	private LocationListener locationListener;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.v(TAG, TAG + " started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        //---- Obtain GPS location
        lm = (LocationManager)
        	getSystemService(Context.LOCATION_SERVICE);
        
        locationListener = new MyLocationListener();
        
        lm.requestLocationUpdates(
        		LocationManager.GPS_PROVIDER,
        		100,
        		10,
        		locationListener);
    }
    
    private class MyLocationListener implements LocationListener
    {
    	private HttpURLConnection httpCon = null;
    	private URL url = null;
    	public void onLocationChanged(Location loc) {
    		if (loc != null) {
    			// Generate URL instance
				try {
					url = new URL("http://www.mariazendre.org/hitchhiking/position/");
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				// Do a HTTP PUT request to post the GPS location to url
				try {
					httpCon = (HttpURLConnection) url.openConnection();
	    			httpCon.setDoOutput(true);
	    			httpCon.setRequestMethod("PUT");
	    			OutputStreamWriter out = new OutputStreamWriter(httpCon.getOutputStream());
	    			out.write("Lat=" + loc.getLatitude() + ";Long=" + loc.getLongitude());
	    			out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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