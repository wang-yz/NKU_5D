package com.nkcs.friends.task;

import java.io.IOException;
import java.lang.reflect.Type;
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
import com.nkcs.friends.activity.SecondInterestActivity;
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.entity.Interests;
import com.nkcs.friends.fragment.InterestFragment;

import android.os.AsyncTask;
import android.util.Log;

public class ShowSecondInterestTask extends AsyncTask<String, Void, String> {

	private String url;

	private String int_type;
	private String responseText;
	
	private SecondInterestActivity secondInterestActivity;

	public ShowSecondInterestTask(SecondInterestActivity secondInterestActivity) {
		super();
		this.secondInterestActivity = secondInterestActivity;
		
		MyApp myApp = new MyApp();
		this.url = myApp.getLiuURL() + "InterestShowServlet";
		
		System.out.println("跳转到了ShowSecondInterestTask");
	}
	
	
	@Override
	protected String doInBackground(String... arg0) {
		int_type = arg0[0];
		
		// 实例化HttpClient对象
		HttpClient client = new DefaultHttpClient();

		// 实例化post请求对象
		HttpPost request = new HttpPost(url);

		// 发出post请求
		try {

			// 封装请求参数
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("int_type", int_type));

			// 设置请求参数
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			// 发出post请求
			HttpResponse response = client.execute(request);

			// 接收返回数据
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				responseText = EntityUtils.toString(response.getEntity());

				System.out.println("responseText = " + responseText);
				
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
		super.onPostExecute(result);

		Gson gson = new Gson();
		Type type = new TypeToken<List<Interests>>(){}.getType();
		List<Interests> interests = gson.fromJson(responseText, type);
		
		Log.d("INFO1",interests.get(0).getInt_name().toString());
		if (interests != null) { // 兴趣列表获取成功

			System.out.println("兴趣列表获取成功");
			
			for(Interests interest:interests) {
				secondInterestActivity.dataInterest.add(interest);
			}
			secondInterestActivity.adapterInterest.notifyDataSetChanged();
		} else {
			System.out.println("兴趣标签加载错误");
		}
		Log.d("INFO2", secondInterestActivity.dataInterest.get(0).getInt_name());
	}

}
