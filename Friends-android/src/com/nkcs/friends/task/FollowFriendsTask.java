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

import com.nkcs.friends.app.MyApp;

import android.app.Activity;
import android.os.AsyncTask;

public class FollowFriendsTask extends AsyncTask<String, Void, String> {

	private Activity activity;
	private String url;
	private String responseText;	
	
	public FollowFriendsTask(Activity activity) {
		super();
		this.activity = activity;
		
		MyApp myApp = new MyApp();
		this.url = myApp.getLiuURL() + "FollowFriendsServlet";
	}

	@Override
	protected String doInBackground(String... arg0) {
		
		String userid = arg0[0];
		String friendsid = arg0[1];
		
		
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(url);
		
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("userid", userid));
			params.add(new BasicNameValuePair("friendsid", friendsid));
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse response = client.execute(request);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				responseText = EntityUtils.toString(response.getEntity());
				return responseText;
			}

		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		}
		
		return null;
	}

}
