package com.nkcs.friends.task;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
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

import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;

public class ShowCalendarTask extends AsyncTask<String, Void, String> {

	private String url;

	private String userid;
	private String calendarModel;
	private String responseText;
	private List<Activities> data;
	private ListView lvwCalendar;
	private ActivityAdapter adapter;
	private String currDate;
	private Date downDate;
	public ShowCalendarTask(List<Activities> data, ListView lvwCalendar, ActivityAdapter adapter,Date downDate) {
		
		this.lvwCalendar = lvwCalendar;
		this.data = data;
		this.adapter = adapter;
		this.downDate=downDate;
		
		MyApp myApp = new MyApp();
		this.url = myApp.getLiuURL() + "CalendarShowServlet";
	}

	
	@Override
	protected String doInBackground(String... arg0) {
		userid = arg0[0];
		calendarModel = arg0[1];
		currDate=arg0[2];
		
		Gson gson=new Gson();
		
		String downtime = gson.toJson(downDate);
		
		//System.out.println(currDate);
		System.out.println(downtime);
		System.out.println("userid = " + userid);
		System.out.println("calendarModel = " + calendarModel);

		// 实例化HttpClient对象
		HttpClient client = new DefaultHttpClient();

		// 实例化post请求对象
		HttpPost request = new HttpPost(url);

		// 发出post请求
		try {

			// 封装请求参数
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("userid", userid));
			params.add(new BasicNameValuePair("calendarModel", calendarModel));
			params.add(new BasicNameValuePair("currDate", downtime));

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
		
		data.clear();
		
		Gson gson = new Gson();
		Type type = new TypeToken<List<Activities>>(){}.getType();
		List<Activities> tempActivity = gson.fromJson(responseText, type);
		
		// System.out.print(activities);

		if (tempActivity != null) { // 兴趣列表获取成功

			for(Activities activity:tempActivity) {
				data.add(activity);
			}
			lvwCalendar.setVisibility(View.VISIBLE);
			adapter.notifyDataSetChanged();

		} else {
			// Toast.makeText(context.getApplicationContext(), "活动列表加载错误", Toast.LENGTH_SHORT).show();
			System.out.println("日程列表加载错误");
		}
		
	}

}
