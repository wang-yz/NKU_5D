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
import com.google.gson.reflect.TypeToken;
import com.nkcs.friends.activity.SelectInterestActivity;
import com.nkcs.friends.adapter.SecondInterestAdapter;
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.entity.Interests;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.renderscript.Type;
import android.support.v4.app.TaskStackBuilder;

public class SelectBackTask extends AsyncTask<String, Void, String> {

	private String url = "http://10.0.2.2:8080/Friends/";
	private Context context;
	private SecondInterestAdapter adapter;
	private List<String> data;
	private Intent intent;
	private List<String> listuploadList=new ArrayList<String>();
	private String responseText;
	public MyApp myApp;
	public SelectBackTask(Context context,SecondInterestAdapter adapter,List<String> data,Intent intent,MyApp myApp){
		this.context=context;
		this.adapter=adapter;
		this.data=data;
		this.intent=intent;
		this.myApp=myApp;
		for(int i=0;i<adapter.listboolean.size();i++){
			if(adapter.listboolean.get(i)){
				listuploadList.add(data.get(i));
			}
		}
		
		this.url = myApp.getLiuURL() + "SetPerInterestServlet";
	}
	
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		
		int userid =Integer.valueOf(arg0[0]);
		// list转为gson对象
	    Gson gson = new Gson();
	    java.lang.reflect.Type type = new TypeToken<List<String>>(){}.getType();
		String listInfo = gson.toJson(listuploadList,type);
		
		// 获取用户userid
		//MyApp myApp=(MyApp)context.getApplicationContext();
		//int userid=myApp.getUser().getUserid();
		System.out.println(userid+"----------------------------");
		// 实例化HttpClient对象
		HttpClient client = new DefaultHttpClient();

		// 实例化post请求对象
		HttpPost request = new HttpPost(url);

		// 发出post请求
		try {
			// 封装请求参数
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("userid", userid+""));
			params.add(new BasicNameValuePair("listInfo", listInfo));
			
			
			// 设置请求参数
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			// 发出post请求
			HttpResponse response = client.execute(request);
			// 接收返回数据
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				responseText = EntityUtils.toString(response.getEntity());
				System.out.print(responseText);
				return responseText;
			}

		} catch (ClientProtocolException e) {
			System.out.println("xx");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("oo");
			e.printStackTrace();
		}
		System.out.print("222222222");
		return null;
	}
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		System.out.println(responseText.charAt(2)+"");
		
		if((responseText.charAt(2)+"").equals("成")){
			System.out.println("要跳转了");
			context.startActivity(intent);
		}
		super.onPostExecute(result);
	}

}
