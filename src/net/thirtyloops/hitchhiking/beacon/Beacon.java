package net.thirtyloops.hitchhiking.beacon;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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


public class Beacon extends Activity implements OnClickListener {
	private final String TAG = "Beacon";
	
	Button startButton, stopButton;
	
	/** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	Log.v(TAG, TAG + " started");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        startButton = (Button) findViewById(R.id.startButton);
        stopButton = (Button) findViewById(R.id.stopButton);
        
        startButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
    }
    
	@Override
	public void onClick(View src) {
		switch(src.getId()) {
		case R.id.startButton:
			Log.i(TAG, "startButton pressed");
			startService(new Intent(this, BeaconService.class));
			break;
		case R.id.stopButton:
			Log.i(TAG, "stopButton pressed");
			stopService(new Intent(this, BeaconService.class));
			break;
		}
	}
}