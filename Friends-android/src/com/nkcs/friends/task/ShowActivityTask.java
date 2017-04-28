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
import com.nkcs.friends.activity.ActivityActivity;
import com.nkcs.friends.adapter.ActivityAdapter;
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.entity.Activities;
import com.nkcs.friends.entity.Interests;

import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

public class ShowActivityTask extends AsyncTask<String, Void, String> {


	private String url;

	private String int_name;
	// 为方便request的发送 应将startNum, pageSize转为String型(在MainActivity已完成处理)
	private String startNum;
	private String pageSize;

	// private ActivityActivity context;
	private String responseText;
	
	private List<Activities> data;
	private List<Activities> tempActivity; // 临时容器，用于存放新的pageSize条数据

	private ListView lvwShow;
	private ActivityAdapter adapter;
	
	public ShowActivityTask(List<Activities> data, ListView lvwShow, ActivityAdapter adapter) {

		this.lvwShow = lvwShow;
		this.data = data;
		this.adapter = adapter;
		
		MyApp myApp = new MyApp();
		this.url = myApp.getLiuURL() + "ActivityShowServlet";
	}

	@Override
	protected String doInBackground(String... arg0) {
		int_name = arg0[0];
		startNum = arg0[1];
		pageSize = arg0[2];
		
		System.out.println("int_name = " + int_name);
		System.out.println("startNum = " + startNum);
		System.out.println("pageSize = " + pageSize);

		// 实例化HttpClient对象
		HttpClient client = new DefaultHttpClient();

		// 实例化post请求对象
		HttpPost request = new HttpPost(url);

		// 发出post请求
		try {

			// 封装请求参数
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("int_name", int_name));
			params.add(new BasicNameValuePair("startNum", startNum));
			params.add(new BasicNameValuePair("pageSize", pageSize));

			// 设置请求参数
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			// 发出post请求
			HttpResponse response = client.execute(request);

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
		// data.clear();
		super.onPostExecute(result);

		Gson gson = new Gson();
		Type type = new TypeToken<List<Activities>>(){}.getType();
		List<Activities> tempActivity = gson.fromJson(responseText, type);
		
		// System.out.print(activities);

		if (tempActivity != null) { // 兴趣列表获取成功

			for(Activities activity:tempActivity) {
				data.add(activity);
			}
			lvwShow.setVisibility(View.VISIBLE);
			adapter.notifyDataSetChanged();

		} else {
			// Toast.makeText(context.getApplicationContext(), "活动列表加载错误", Toast.LENGTH_SHORT).show();
			System.out.println("活动列表加载错误");
		}
	}

}
