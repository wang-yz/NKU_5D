package com.nkcs.friends.task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.nkcs.friends.activity.NotificationActivitiesActivity;
import com.nkcs.friends.app.MyApp;

import android.os.AsyncTask;


public class NotmoreTask extends AsyncTask<String, Void, String> {

	private String url;
	private NotificationActivitiesActivity notificationActivitiesActivity;
	private String responseText;
	
	public NotmoreTask(
			NotificationActivitiesActivity notificationActivitiesActivity) {
		super();
		this.notificationActivitiesActivity = notificationActivitiesActivity;
		
		MyApp myApp = new MyApp();
		this.url = myApp.getLiuURL() + "UserCancelServlet";
	}

	@Override
	protected String doInBackground(String... arg0) {
		
		String userid = arg0[0];
		String activityid = arg0[1];
		
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(url);
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("userid", userid));
			params.add(new BasicNameValuePair("activityid", activityid));
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				responseText = EntityUtils.toString(response.getEntity());
				System.out.println(responseText);
				return responseText;
			}
		} catch (ClientProtocolException e) {
			System.out.println("1");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("2");
			e.printStackTrace();
		}		
		return null;
	}

}
