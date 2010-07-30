package net.thirtyloops.hitchhiking.beacon;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import java.io.IOException;
import java.lang.String;
import java.net.URI;
import java.net.URISyntaxException;
import org.json.JSONObject;
import org.json.JSONException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.entity.ByteArrayEntity;


public class BeaconService extends Service {
	private final String TAG = "BeaconService";
		
	private static URI PutUri = null;
	private static int IntervalMsec = 2000;
	private static int IntervalMeter = 10;
	
	private LocationManager lm;
	private LocationListener locationListener;
	
	//private OnClickListener mCorkyListener = new OnClickListener() {
	//    public void onClick(View v) {
	      // do something when the button is clicked
	//    }
	//};

    /** Called when the activity is first created. */
	@Override
    public void onCreate() {
    	Log.v(TAG, TAG + " started");
        super.onCreate();
    }
    
	@Override
	public void onDestroy() {
		Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show();
		Log.i(TAG, "Service stopped.");
		lm.removeUpdates(locationListener);
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		Toast.makeText(this, "My Service Started", Toast.LENGTH_LONG).show();
		Log.i(TAG, "Service started");
		
		try {
    		PutUri = new URI("http://www.mariazendre.org/hitchhiking/position/");
    	} catch (URISyntaxException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
    	
		// Obtain GPS location updates and connect it to a callback class locationListener.
        lm = (LocationManager)
        	getSystemService(Context.LOCATION_SERVICE);
        
        locationListener = new MyLocationListener();
        
        lm.requestLocationUpdates(
        		LocationManager.GPS_PROVIDER,
        		IntervalMsec,
        		IntervalMeter,
        		locationListener);
	}
	
    @Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}
    
    private class MyLocationListener implements LocationListener
    {    	
    	public void onLocationChanged(Location loc) {
    		if (loc != null) {
    			// Used for the user notifications, Toast
    			Context context = getApplicationContext();
    			int duration = Toast.LENGTH_SHORT;
				
				// Create the json string to PUT to the server
				JSONObject data = new JSONObject();
				try {
					data.put("lat", loc.getLatitude());
					data.put("lon", loc.getLongitude());
				} catch (JSONException e) {
					Toast toast = Toast.makeText(context, "JSON creation failed.", duration);
					toast.show();
				}

			    try {
			    	// Do a HTTP PUT request to post the GPS location to url
					HttpClient httpclient = new DefaultHttpClient();
					
					// Prepare a request object
				    HttpPut httpput = new HttpPut(PutUri);
				    httpput.setEntity(new ByteArrayEntity(data.toString().getBytes()));
					
				    // Execute the request
				    httpclient.execute(httpput);

			    } catch (ClientProtocolException e) {
		            // TODO Auto-generated catch block
		            e.printStackTrace();
				} catch (IOException e) {
					Toast toast = Toast.makeText(context, "Put failed", duration);
					toast.show();
				}
				Toast toast = Toast.makeText(context, data.toString(), duration);
    			toast.show();
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