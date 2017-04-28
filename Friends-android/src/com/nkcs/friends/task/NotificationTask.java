package com.nkcs.friends.task;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nkcs.friends.R;
import com.nkcs.friends.activity.ActivityActivity;
import com.nkcs.friends.activity.NotificationActivitiesActivity;
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.entity.Activities;
import com.nkcs.friends.fragment.FragmentManagerActivity;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;

public class NotificationTask extends AsyncTask<String, Void, String> {

	private String url;
	private Activity threadActivity;
	private NotificationManager notificationManager;
	private Notification notification;
	private String name, address, deadline, starttime, curNumber, intro, picture;
	private int id;
		
	public NotificationTask( Activity threadActivity) {
		super();
		this.threadActivity = threadActivity;
		
		MyApp myApp = new MyApp();
		this.url = myApp.getLiuURL() + "MessageNotifyServlet";
	}

	@Override
	protected String doInBackground(String... arg0) {
		
		String username = arg0[0];
		String password = arg0[1];
		
	    try {
			HttpClient client = new DefaultHttpClient();
			HttpPost request = new HttpPost(url);
			String responseText;
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", username));
			params.add(new BasicNameValuePair("password", password));
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				responseText = EntityUtils.toString(response.getEntity());
				System.out.println(responseText);
				return responseText;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		System.out.println("result =" + result);
		Gson gson = new Gson();
		Type type = new TypeToken<List<Activities>>(){}.getType();
		List<Activities> activitieslist = gson.fromJson(result, type);
		System.out.println("activitieslist =" + activitieslist);
		for(Activities activities:activitieslist){
			id = activities.getAct_id();
			name = activities.getAct_name();
			address = activities.getAct_address0() + activities.getAct_address1() + activities.getAct_address2();
			deadline = activities.getAct_deadline();
			starttime = activities.getAct_starttime();
			curNumber = activities.getAct_curNumber() + "";
			intro = activities.getAct_intro();
			picture = activities.getAct_picture();
			sendNotification(R.drawable.ic_launcher, "三人行提醒您：您有新活动要参加","三人行", "您有新活动要参加，点击查看详情", System.currentTimeMillis());
		}
		
	}
	
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
		
		Intent intent =  new Intent(threadActivity, NotificationActivitiesActivity.class);
		intent.putExtra("id", id);
		intent.putExtra("name", name);
		intent.putExtra("address", address);
		intent.putExtra("deadline", deadline);
		intent.putExtra("starttime", starttime);
		intent.putExtra("curNumber", curNumber);
		intent.putExtra("intro", intro);
		intent.putExtra("picture", picture);
		
		this.notificationManager = (NotificationManager) threadActivity.getSystemService(threadActivity.getApplicationContext().NOTIFICATION_SERVICE);	    
		PendingIntent pendingIntent = PendingIntent.getActivity(threadActivity, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
		this.notification.setLatestEventInfo(threadActivity, title, message, pendingIntent);
		this.notificationManager.notify(0, notification);	
	}
	
}
