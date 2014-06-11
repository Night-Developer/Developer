package com.example.developer;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

public class GCMService extends IntentService{

    private NotificationManager mNotificationManager;

    private int NOTIFICATION_ID = 1234;
	
	public GCMService() {
		super("Listener-Service");
	}

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();

        sendNotification("Received: " + extras.toString());
        
        GCMBroadCastReceiver.completeWakefulIntent(intent);
    }

    private void sendNotification(String msg) {
        mNotificationManager = (NotificationManager)this.getSystemService(Context.NOTIFICATION_SERVICE);

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,new Intent(this, MainActivity.class), 0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
        											.setSmallIcon(R.drawable.ic_launcher)
											        .setContentTitle("Developer")
											        .setStyle(new NotificationCompat.BigTextStyle()
											        .bigText(msg))
											        .setContentText(msg);

        mBuilder.setContentIntent(contentIntent);
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }
}