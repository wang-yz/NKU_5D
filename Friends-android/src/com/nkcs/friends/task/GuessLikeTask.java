package com.nkcs.friends.task;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
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
import com.nkcs.friends.adapter.ActivityAdapter;
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.entity.Activities;
import com.nkcs.friends.fragment.RecommendFragment;

import android.R.integer;
import android.content.Context;
import android.os.AsyncTask;
import android.provider.ContactsContract.Contacts.Data;
import android.widget.EditText;
import android.widget.Toast;

public class GuessLikeTask extends AsyncTask<String, Void, String>{

	private String url;
	
	
	private int userid;
	private String responseText;
	private RecommendFragment context;

	public GuessLikeTask(RecommendFragment context){
		this.context=context;

		MyApp myApp = new MyApp();
		this.url = myApp.getLiuURL() + "GuessLikeServlet";
	}
	@Override
	protected String doInBackground(String... arg0) {
		// TODO Auto-generated method stub
		userid=Integer.valueOf(arg0[0]);
		
		HttpClient client=new DefaultHttpClient();
		HttpPost request=new HttpPost(url);
		
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("userid", userid+""));
		
		try {
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
		System.out.println("开始gson解码: "+responseText);
		List<Activities> activities = gson.fromJson(responseText, type);

		if (activities != null) { // 活动列表获取成功
			for(Activities activity:activities) {
				System.out.println( "活动          " + activity.getAct_intro());
				
				context.data.add(activity);
				System.out.println( "添加成功            ");
			}
			String string = gson.toJson(context.data);
			System.out.print("inside           "+string);
		} else {
			//Toast.makeText(context.getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
		}
		System.out.println("kai shi notify");
		context.adapter.notifyDataSetChanged();
		
		super.onPostExecute(result);
	}

}
