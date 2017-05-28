package com.nkcs.friends.task;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
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
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.entity.Activities;
import com.nkcs.friends.entity.Users;

import android.R.string;
import android.content.Context;
import android.os.AsyncTask;

public class UserAttendTask extends AsyncTask<String, Void, String> {

	private Context context;
	public MyApp myApp;
	
	public UserAttendTask(Context context, MyApp myApp){
		this.context = context;
		this.myApp=myApp;
	}
	
	@Override
	protected String doInBackground(String... arg0) {
		//初始
		String url=arg0[0];
		Users user=myApp.getUser();
		Activities activity=myApp.getActivity();
		
		try {
			HttpClient client = new DefaultHttpClient();
			
			HttpPost request = new HttpPost(url);
			
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("userid", user.getUserid()+""));
			System.out.println("userid="+user.getUserid());
			params.add(new BasicNameValuePair("activityid", activity.getAct_id()+""));
			System.out.println("activityid="+activity.getAct_id());
						
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			
			HttpResponse response = client.execute(request);
						
			if(response.getStatusLine().getStatusCode()==HttpStatus.SC_OK){
				
				String responseText = EntityUtils.toString(response.getEntity());

				return responseText;
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		
		Activities activity=myApp.getActivity();
		activity.setAct_curNumber(activity.getAct_curNumber()+1);
		myApp.setActivity(activity);
		
		System.out.println("activity.getAct_curNumber()="+activity.getAct_curNumber());
		super.onPostExecute(result);
	}
}
