package com.nkcs.friends.activity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

import com.nkcs.friends.R;

public class NotificationActivity extends Activity {

	private NotificationManager notificationManager;
	private Notification notification;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notification);

		sendNotification(R.drawable.ic_launcher, "三人行提醒您：您有新活动要参加","三人行", "您有新活动要参加，点击查看详情", System.currentTimeMillis());
		
	}
	

	//发送通知
	@SuppressWarnings("deprecation")
	private void sendNotification(final int icon, final String tickerText, final String title,
			                      final String message, final long time){
		this.notification = new Notification();
		this.notification.icon = icon;
		this.notification.tickerText = tickerText;
		this.notification.when = time;
		this.notification.flags = Notification.FLAG_AUTO_CANCEL;
		this.notification.defaults = Notification.DEFAULT_SOUND;
		//this.notification.sound = Uri.parse("");
		this.notification.defaults |= Notification.DEFAULT_VIBRATE;
		long[] vibrate = {0, 100, 200, 300};
		notification.vibrate = vibrate;	
		
		Intent intent =  new Intent(NotificationActivity.this, NotificationActivitiesActivity.class);
		intent.putExtra("text", message);	
		
		this.notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);	    
		PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		this.notification.setLatestEventInfo(getApplicationContext(), title, message, pendingIntent);
		this.notificationManager.notify(0, notification);	
	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notification, menu);
		return true;
	}

}
