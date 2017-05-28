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
import com.google.gson.Gson;
import com.nkcs.friends.activity.UserHomeActivity;
import com.nkcs.friends.activity.UserUpdateActivity;
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.entity.Users;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

public class UserUpdateTask extends AsyncTask<String, Void, String> {

	private String url;
	private Context context;	
	private ProgressDialog userUpdateDialog;
	private MyApp myApp;
	


	
	public UserUpdateTask(Context context, ProgressDialog registerDialog,
			MyApp myApp) {
		super();
		this.context = context;
		this.userUpdateDialog = registerDialog;
		this.myApp = myApp;
		
		this.url = myApp.getLiuURL() + "UserUpdateServlet";
	}

	@Override
	protected String doInBackground(String... arg0) {
		try {
			String responseText = null;

			HttpClient client = new DefaultHttpClient();

			HttpPost request = new HttpPost(url);

			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("nickname", arg0[0]));
			params.add(new BasicNameValuePair("password", arg0[1]));
			params.add(new BasicNameValuePair("phonenumber", arg0[2]));
			params.add(new BasicNameValuePair("introduction", arg0[3]));
			params.add(new BasicNameValuePair("gender", arg0[4]));
			params.add(new BasicNameValuePair("username", arg0[5]));
			params.add(new BasicNameValuePair("oldPassword", arg0[6]));
			params.add(new BasicNameValuePair("address0", arg0[7]));
			params.add(new BasicNameValuePair("address1", arg0[8]));
			

			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			HttpResponse response = client.execute(request);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				responseText = EntityUtils.toString(response.getEntity());
			}
			return responseText;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		userUpdateDialog.dismiss();
		Gson gson = new Gson();
		Users user = gson.fromJson(result, Users.class);
		if(user != null){
			myApp.setUser(user);
			Toast.makeText(context, "信息更新成功", Toast.LENGTH_SHORT).show();
			((Activity) context).finish();
			Intent intent = new Intent(context, UserHomeActivity.class);
			context.startActivity(intent);
			((Activity) context).finish();
		}else{	
			Toast.makeText(context, "信息更新失败", Toast.LENGTH_SHORT).show();
			((Activity) context).finish();
			Intent intent = new Intent(context, UserUpdateActivity.class);
			context.startActivity(intent);
			((Activity) context).finish();
		}
		super.onPostExecute(result);
	}

	
}
