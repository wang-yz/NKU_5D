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

import com.nkcs.friends.activity.ChatActivity;
import com.nkcs.friends.app.MyApp;

import android.os.AsyncTask;

public class SaveMessageTask extends AsyncTask<String, Void, String> {

	private String url;
	private String responseText;
	private ChatActivity chatActivity;
	
	public SaveMessageTask(ChatActivity chatActivity) {
		super();
		this.chatActivity = chatActivity;
		
		MyApp myApp = new MyApp();
		this.url = myApp.getLiuURL() + "SaveMessageServlet";
	}

	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		
		String username = arg0[0];
		String password = arg0[1];
		String msg = arg0[2];
		String activityId = arg0[3];
		
		// 实例化HttpClient对象
		HttpClient client = new DefaultHttpClient();

		// 实例化post请求对象
		HttpPost request = new HttpPost(url);

		// 发出post请求
		try {

			// 封装请求参数
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", username));
			params.add(new BasicNameValuePair("password", password));
			params.add(new BasicNameValuePair("msg", msg));
			params.add(new BasicNameValuePair("activityId", activityId));

			// 设置请求参数
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			// 发出post请求
			HttpResponse response = client.execute(request);

			// 接收返回数据
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
