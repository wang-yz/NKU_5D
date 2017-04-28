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

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

public class DeleteFriendsTask extends AsyncTask<String, Void, String> {
	
	private String url;
	private Context context;
	private String responseText;
	
	public DeleteFriendsTask(Context context) {
		super();
		this.context = context;
		
		MyApp myApp = new MyApp();
		this.url = myApp.getLiuURL() + "DeleteFriendsServlet";
	}

	@Override
	protected String doInBackground(String... arg0) {
		String nickname = arg0[0];
		String phonenumber = arg0[1];
		String userid = arg0[2];
		// 实例化HttpClient对象
		HttpClient client = new DefaultHttpClient();

		// 实例化post请求对象
		HttpPost request = new HttpPost(url);

		// 发出post请求
		try {

			// 封装请求参数
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("nickname", nickname));
			params.add(new BasicNameValuePair("phonenumber", phonenumber));
			params.add(new BasicNameValuePair("userid", userid));

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
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		if(result == null){
			Toast.makeText(context, "好友删除失败！", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(context, "好友删除成功！", Toast.LENGTH_SHORT).show();
		}

	}
}
