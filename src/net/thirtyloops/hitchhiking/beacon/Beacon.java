package net.thirtyloops.hitchhiking.beacon;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
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


public class Beacon extends Activity {
	private final String TAG = "Beacon";
	
	private LocationManager lm;
	private LocationListener locationListener;
	
	//private OnClickListener mCorkyListener = new OnClickListener() {
	//    public void onClick(View v) {
	      // do something when the button is clicked
	//    }
	//};

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.v(TAG, TAG + " started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        // Obtain GPS location updates and connect it to a callback class locationListener.
        lm = (LocationManager)
        	getSystemService(Context.LOCATION_SERVICE);
        
        locationListener = new MyLocationListener();
        
        lm.requestLocationUpdates(
        		LocationManager.GPS_PROVIDER,
        		2000,
        		10,
        		locationListener);
    }
    
    private class MyLocationListener implements LocationListener
    {
    	
    	private URI uri = null;	// URI to make the HTTP PUT request to.
    	
    	public void onLocationChanged(Location loc) {
    		if (loc != null) {
    			// Used for the user notifications, Toast
    			Context context = getApplicationContext();
    			int duration = Toast.LENGTH_SHORT;

    			// Generate URL instance
				try {
					uri = new URI("http://www.mariazendre.org/hitchhiking/position/");
				} catch (URISyntaxException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
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
				    HttpPut httpput = new HttpPut(uri);
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