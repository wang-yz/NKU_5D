package com.nkcs.friends.activity;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import com.nkcs.friends.R;
import com.nkcs.friends.R.id;
import com.nkcs.friends.R.layout;
import com.nkcs.friends.R.menu;
import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.task.LoginTask;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	// 获取全局类
	MyApp myapp = (MyApp) getApplication();

	private EditText edtUsername;
	private EditText edtPassword;
	private CheckBox chkRemember;

	private String url = "http://192.168.56.1:8088/Friends/UserLoginServlet";

	private String responseText;

	private String ip;
	
	private ProgressDialog loginDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		edtUsername = (EditText) findViewById(R.id.edtUsername);
		edtPassword = (EditText) findViewById(R.id.edtPassword);

		// 创建进度条对话框
		loginDialog = new ProgressDialog(this);
		loginDialog.setMessage("正在验证用户信息...");
		
		
		//获取wifi服务  
		WifiManager wifiManager = (WifiManager) getSystemService(this.WIFI_SERVICE);  
	    //判断wifi是否开启  
		if (!wifiManager.isWifiEnabled()) {  
		    wifiManager.setWifiEnabled(true);    
		}  
		WifiInfo wifiInfo = wifiManager.getConnectionInfo();       
		int ipAddress = wifiInfo.getIpAddress();   
		ip = intToIp(ipAddress);   
		System.out.println(ip);


	}
	
	
	//huo qu ip
	 private String intToIp(int i) {       
		 
		  return (i & 0xFF ) + "." +       
		  ((i >> 8 ) & 0xFF) + "." +       
		  ((i >> 16 ) & 0xFF) + "." +       
		  ( i >> 24 & 0xFF); } 
	 
	

	public void login(View v) {
		if(edtUsername.getText().toString().equals("")){
			Toast.makeText(this, "用户名不能为空", Toast.LENGTH_LONG).show();
		}
		if(edtPassword.getText().toString().equals("")){
			Toast.makeText(this, "密码不能为空", Toast.LENGTH_LONG).show();
		}
		
		loginDialog.show();

		MyApp myApp = new MyApp();

		SharedPreferences sp = getSharedPreferences("UserInfo", MODE_PRIVATE);

		// 启动登录异步线程
		LoginTask input_task = new LoginTask(LoginActivity.this, chkRemember,
				loginDialog);
		input_task.execute(edtUsername.getText().toString(), edtPassword
				.getText().toString(),ip);
	}

	public void toRegister(View v) {
		Intent intent = new Intent(this, RegisterActivity.class);
		this.startActivity(intent);
		finish();
	}

	// -----------------------------------------------------------------------------

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
