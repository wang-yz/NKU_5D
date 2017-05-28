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

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nkcs.friends.adapter.SecondInterestAdapter;
import com.nkcs.friends.adapter.SecondInterestAdapter;
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.entity.Interests;

public class SelectInterestTask extends AsyncTask<String, Void, String>{

	private String url;

	private String int_name;
	private List<String> data;
	private SecondInterestAdapter adapter;
	private ListView listView;
	private Context context;
	private String responseText;
	
	public SelectInterestTask(Context context,List<String> data,ListView listView,SecondInterestAdapter adapter){
		this.context=context;
		this.data=data;
		this.listView=listView;
		this.adapter=adapter;
		
		MyApp myApp = new MyApp();
		
		this.url = myApp.getLiuURL() + "InterestShowServlet";
	}
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		
		String int_type = arg0[0];
		

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
		Gson gson = new Gson();
		Type type = new TypeToken<List<Interests>>(){}.getType();
		List<Interests> interests = gson.fromJson(responseText, type);
		
		System.out.print(interests);

		if (interests != null) { // 兴趣列表获取成功
			for(Interests interest:interests) {
				String nameString=interest.getInt_name();
				data.add(nameString);
			}
			listView.setVisibility(View.VISIBLE);
			adapter.notifyDataSetChanged();
		} else {
			Toast.makeText(context.getApplicationContext(), "界面加载错误", Toast.LENGTH_SHORT).show();
		}
		super.onPostExecute(result);
	}
}
