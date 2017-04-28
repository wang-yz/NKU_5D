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
import com.nkcs.friends.activity.ChatRoomActivity;
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.entity.Activities;
import com.nkcs.friends.entity.Users;
import com.nkcs.friends.fragment.Chatroom_FriendFragment;
import com.nkcs.friends.fragment.Chatroom_FriendFragment.MyAdapter;

import android.R.string;
import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

public class ChatRoomTask extends AsyncTask<String, Void, String> {

	private String userid;
	private String url;
	private String responseText;
	private List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
	private MyAdapter adapter;
	private Chatroom_FriendFragment chatroom_FriendFragment;
	
	public ChatRoomTask(Chatroom_FriendFragment chatroom_FriendFragment, List<Map<String, Object>> dataList){
		super();
		this.chatroom_FriendFragment = chatroom_FriendFragment;
		this.dataList = dataList;
		
		MyApp myApp = new MyApp();
		this.url = myApp.getLiuURL() + "ChatRoomServlet";
	}
	
	@Override
	protected String doInBackground(String... arg0) {
		userid=arg0[0];
		
		HttpClient client = new DefaultHttpClient();
		HttpPost request = new HttpPost(url);
		try {
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("userid", userid));
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
		System.out.println(result);
		Gson gson = new Gson();
		Type type = new TypeToken<List<Activities>>(){}.getType();
		List<Activities> tempActivities = gson.fromJson(responseText, type);
		
		if (tempActivities != null) { // 聊天室列表获取成功

			for(Activities activity:tempActivities) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("act_id", activity.getAct_id());
				map.put("photo", activity.getAct_picture());
				map.put("chatname", activity.getAct_name());
				map.put("curnum", activity.getAct_curNumber());
				dataList.add(map);						
			}
			chatroom_FriendFragment.adapter.notifyDataSetChanged();

		} else {
			System.out.println("聊天室列表加载错误");
		}
	}
}
