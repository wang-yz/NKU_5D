package com.nkcs.friends.task;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.entity.Users;
import com.nkcs.friends.fragment.List_FriendFragment;
import com.nkcs.friends.fragment.List_FriendFragment.MyAdapter;

import android.os.AsyncTask;

public class MyFriendsTask extends AsyncTask<String, Void, String> {

	private String name;
	private String password;
	private String url;
	private String responseText;
	private List<Map<String, Object>> dataList;
	public MyAdapter adapter;
	public List_FriendFragment list_FriendFragment;
	
	public MyFriendsTask(List_FriendFragment list_FriendFragment, List<Map<String, Object>> dataList) {
		super();
		this.list_FriendFragment = list_FriendFragment;
		this.dataList = dataList;
		
		MyApp myApp = new MyApp();
		this.url = myApp.getLiuURL() + "MyFriendsServlet";
	}

	@Override
	protected String doInBackground(String... arg0) {
		
		name = arg0[0];
		password = arg0[1];
		
		// 实例化HttpClient对象
		HttpClient client = new DefaultHttpClient();

		// 实例化post请求对象
		HttpPost request = new HttpPost(url);

		// 发出post请求
		try {

			// 封装请求参数
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", name));
			params.add(new BasicNameValuePair("password", password));

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

		dataList.clear();
		
		Gson gson = new Gson();
		Type type = new TypeToken<List<Users>>(){}.getType();
		List<Users> tempUsers = gson.fromJson(responseText, type);

		if (tempUsers != null) { // 兴趣列表获取成功

			for(Users user:tempUsers) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("photo", user.getPhoto());
				map.put("nickname", user.getNickname());
				map.put("phonenumber", user.getPhonenumber());
				dataList.add(map);
				
				list_FriendFragment.adapter.notifyDataSetChanged();
			}

		} else {
			System.out.println("好友列表加载错误");
		}
	}

}
