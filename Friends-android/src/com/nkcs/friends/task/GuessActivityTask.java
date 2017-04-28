package com.nkcs.friends.task;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.security.GeneralSecurityException;
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
import com.google.gson.reflect.TypeToken;
import com.nkcs.friends.adapter.ActivityAdapter;
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.entity.Activities;
import com.nkcs.friends.entity.Interests;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Adapter;
import android.widget.EditText;
import android.widget.Toast;

public class GuessActivityTask extends AsyncTask<String, Void, String>{

	private String url;
	
	private String responseText;
	private Context context;
	private EditText editText;
	private ActivityAdapter adapter;
	private List<Activities> data;
	
	public GuessActivityTask(Context context,List<Activities> data,EditText editText,ActivityAdapter adapter) {
		this.context=context;
		this.data=data;
		this.editText=editText;
		this.adapter=adapter;
		
		MyApp myApp = new MyApp();
		this.url = myApp.getLiuURL() + "SelectActivityServlet";
	}
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		String name=editText.getText().toString();
		System.out.println("name="+name);
		// 实例化HttpClient对象
		HttpClient client = new DefaultHttpClient();

		// 实例化post请求对象
		HttpPost request = new HttpPost(url);
		// 封装请求参数
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("act_name", name));


		// 设置请求参数
		try {
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// 发出post请求
		HttpResponse response;
		try {
			response = client.execute(request);
			
			// 接收返回数据
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
	
	@Override
	protected void onPostExecute(String result) {
		Gson gson = new Gson();
		Type type = new TypeToken<List<Activities>>(){}.getType();
		System.out.println("开始gson解码 222     "+responseText);
		List<Activities> activities = gson.fromJson(responseText, type);

		if (activities != null) { // 活动列表获取成功
			for(Activities activity:activities) {
				data.add(activity);
			}
			//System.out.print("活动列表获取成功");
		} else {
			Toast.makeText(context.getApplicationContext(), "未找到相关活动", Toast.LENGTH_SHORT).show();
		}
		
		adapter.notifyDataSetChanged();
		
		super.onPostExecute(result);
	}

}
