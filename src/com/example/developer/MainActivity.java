package com.example.developer;

import java.io.IOException;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

public class MainActivity extends Activity {

	private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
	
	private GoogleCloudMessaging gcm = null;
	
	private String SENDER_ID = "you-project-id";
	private String regid;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if( checkPlayServices(this) == true ){
			getReigstrationId(this);
		}
	}
	
	public void getReigstrationId(final Context context){
		new AsyncTask<Void, Void, Void>(){

			private String msg;
			
			@Override
			protected Void doInBackground(Void... arg0) {
				
				if (gcm == null) {
	                gcm = GoogleCloudMessaging.getInstance(context);
	            }
	            
	            try {
	            	Log.i("Sender",SENDER_ID);
	            	
					regid = gcm.register(SENDER_ID);
					
					msg = "Movil registrado, registration ID=" + regid;
					
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				
				Log.i("Developer",msg);
				Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
			}
		}.execute();
	}
	
	public static boolean checkPlayServices(Context context) {
	    int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
	    if (resultCode != ConnectionResult.SUCCESS) {
	        if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
	            GooglePlayServicesUtil.getErrorDialog(resultCode, (Activity) context,PLAY_SERVICES_RESOLUTION_REQUEST).show();
	        } else {
	            Log.i("Developer", "This device is not supported.");
	        }
	        return false;
	    }
	    return true;
	}
}
