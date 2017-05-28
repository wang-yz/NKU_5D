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
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.entity.Users;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

public class RegisterTask extends AsyncTask<String, Void, String> {

	private String responseText;
	private String name,password,phone;
	public Intent intent;
	private String url;
	private String ip;
	
	private Context context;
	public MyApp myApp;
	public RegisterTask(Context context,MyApp myApp,Intent intent){
		this.context=context;
		this.myApp=myApp;
		this.intent=intent;
		
		this.url = myApp.getLiuURL() + "UserRegisterServlet";
	}
	@Override
	protected String doInBackground(String... arg0) {
		
		name = arg0[0];
		password = arg0[1];
		phone=arg0[2];
		ip=arg0[3];
		
		HttpClient client = new DefaultHttpClient();

		HttpPost request = new HttpPost(url);
		
		try {

			System.out.println("接收完成");
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", name));
			params.add(new BasicNameValuePair("password", password));
			params.add(new BasicNameValuePair("phonenumber", phone));
			params.add(new BasicNameValuePair("ip", ip));
			// System.out.println(name+password+phone+"3333333");
			
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			HttpResponse response = client.execute(request);

			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				responseText = EntityUtils.toString(response.getEntity());
				System.out.println("responseText="+responseText+"跳转完成");	
				return responseText;
			}

		} catch (ClientProtocolException e) {
			
			e.printStackTrace();
			
		} catch (IOException e) {
			
			e.printStackTrace();
			
		}
		return null;
	}

	
	@Override
	protected void onPostExecute(String result) {

		System.out.println("在 onpostexecute       "+name+password+phone);	
		Gson gson=new Gson();
		Users user=gson.fromJson(responseText,Users.class);

		if(user==null){
			Toast.makeText(context, "用户名已存在", Toast.LENGTH_LONG).show();
		}else{
			// 将user对象保存到全局变量中
			//MyApp myApp = (MyApp) context.getApplicationContext();
			myApp.setUser(user);
			context.startActivity(intent);
		}
		
		super.onPostExecute(result);
	}
}
