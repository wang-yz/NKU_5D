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
import com.nkcs.friends.activity.LoginActivity;
import com.nkcs.friends.activity.MainActivity;
import com.nkcs.friends.activity.TabHostActivity;
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.entity.Users;
import com.nkcs.friends.fragment.FragmentManagerActivity;

import android.R.string;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.widget.CheckBox;
import android.widget.Toast;

public class LoginTask extends AsyncTask<String, Void, String> {

	private String url;

	private String responseText;
	private String username;
	private String password;

	private String ip;
	
	
	private LoginActivity login;
	private ProgressDialog loginDialog;
	private CheckBox chkAutoLogin;

	public LoginTask(LoginActivity login, CheckBox chkAutoLogin,
			ProgressDialog loginDialog) {
		this.login = login;
		this.chkAutoLogin = chkAutoLogin;
		this.loginDialog = loginDialog;
		
		MyApp myApp = new MyApp();
		this.url = myApp.getLiuURL() + "UserLoginServlet";
	}

	@Override
	protected String doInBackground(String... arg0) {

		username = arg0[0];
		password = arg0[1];

		ip=arg0[2];
		System.out.println("客户端" + username + password);

		// 实例化HttpClient对象
		HttpClient client = new DefaultHttpClient();

		// 实例化post请求对象
		HttpPost request = new HttpPost(url);

		// 发出post请求
		try {

			// 封装请求参数
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("username", username));
			params.add(new BasicNameValuePair("password", password));
			params.add(new BasicNameValuePair("ip", ip));

			// 设置请求参数
			request.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

			// 发出post请求
			HttpResponse response = client.execute(request);

			// 接收返回数据
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

				responseText = EntityUtils.toString(response.getEntity());
				System.out.println(responseText);
				return responseText;

			}

		} catch (ClientProtocolException e) {

			System.out.println("1");

			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("2");
			e.printStackTrace();
		}
		return null;
	}

	@Override
	protected void onPostExecute(String result) {

		super.onPostExecute(result);

		// 销毁进度条对话框
		loginDialog.dismiss();

		Gson gson = new Gson();
		Users user = gson.fromJson(responseText, Users.class);

		if (user != null) { // 登录成功

			System.out.println(user.getUsername());

			// 判断下次是否自动登录
//			if (chkAutoLogin.isChecked()) {
//
//				// 保存密码
//				SharedPreferences sp = login
//						.getSharedPreferences("UserInfo", 0);
//
//				Editor editor = sp.edit();
//
//				editor.putString("username", username);
//				editor.putString("password", password);
//
//				editor.commit();
//
//			}

			// 将user对象保存到全局变量中
			MyApp myApp = (MyApp) login.getApplication();
			myApp.setUser(user);

			// 跳转到主界面
			// Intent intent = new Intent(login, MainActivity.class);
			Intent intent = new Intent(login, FragmentManagerActivity.class);
			login.startActivity(intent);

			// 关闭当前页面
			login.finish();

		} else {
			Toast.makeText(login.getApplicationContext(), "用户名或密码错误，请重新输入",
					Toast.LENGTH_SHORT).show();
		}
	}

}
