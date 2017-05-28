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
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.entity.Users;
import com.nkcs.friends.fragment.Recommend_FriendFragment;
import com.nkcs.friends.fragment.Recommend_FriendFragment.MyAdapter;

import android.os.AsyncTask;

public class SelectFriendsTask extends AsyncTask<String, Void, String> {

	private String username;
	private String password;
	private String url;
	private String responseText;
	public List<Users> dataList = new ArrayList<Users>();
	private Recommend_FriendFragment recommend_FriendFragment;
	
	public SelectFriendsTask(Recommend_FriendFragment recommend_FriendFragment ,List<Users> dataList) {
		super();
		this.recommend_FriendFragment = recommend_FriendFragment;
		this.dataList = dataList;
		
		MyApp myApp = new MyApp();
		
		this.url = myApp.getLiuURL() + "SelectFriendsServlet";
	}


	@Override
	protected String doInBackground(String... arg0) {
		
		username = arg0[0];
		password = arg0[1];
		
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(url);
		
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", username));
			params.add(new BasicNameValuePair("password", password));
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
			HttpResponse response = client.execute(request);
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
		super.onPostExecute(result);
		dataList.clear();
		try {
			Gson gson = new Gson();
			Type type = new TypeToken<List<Users>>(){}.getType();
			List<Users> tempUsers = gson.fromJson(responseText, type);
			
			if (tempUsers != null) { // 兴趣列表获取成功

				for(Users user:tempUsers) {
					dataList.add(user);		
				}
				recommend_FriendFragment.adapter.notifyDataSetChanged();
			} else {
				System.out.println("加载错误，请检查网络");
			}
		} catch (JsonSyntaxException e) {
			// TODO Auto-generated catch block
			System.out.println("jiazai");
			e.printStackTrace();
		}
		
	}
	
}
