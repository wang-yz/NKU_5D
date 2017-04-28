package com.nkcs.friends.activity;

import java.util.ArrayList;

import com.nkcs.friends.app.MyApp;
import com.nkcs.friends.task.*;

import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.R.string;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;

import com.nkcs.friends.R;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	// 组件
	private EditText edtname, edtpassword, edtphone;
	private String name, password, phone;
	// 网址
	private String url;

	private String ip;
    private static final int PROGRESS_DIALOG = 1; 
    private static final int STATE_FINISH = 1;
    private static final int STATE_ERROR = -1; 
	public MyApp myApp;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		
		// 获取组件
		edtname = (EditText) findViewById(R.id.edtname);
		edtpassword = (EditText) findViewById(R.id.edtpassword);
		edtphone = (EditText) findViewById(R.id.edtphone);
		
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

	// 点击注册按钮
	public void Register(View v) {
		// 获取数据
		name = edtname.getText().toString();
		password = edtpassword.getText().toString();
		phone = edtphone.getText().toString();
		System.out.println(name + password + phone);
		
		if(name.equals("")){
			Toast.makeText(this, "用户名不能为空", Toast.LENGTH_LONG).show();
			return ;
		}
		if(password.equals("")){
			Toast.makeText(this, "密码不能为空", Toast.LENGTH_LONG).show();
			return ;
		}
		if(phone.equals("")){
			Toast.makeText(this, "手机号不能为空", Toast.LENGTH_LONG).show();
			return ;
		}
		
		showDialog(PROGRESS_DIALOG);
		new ProgressThread(handler).start();
		
		myApp = (MyApp) getApplication();
		Intent intent = new Intent(this, SelectInterestActivity.class);
		new RegisterTask(this,myApp,intent).execute(name, password, phone,ip);
		//finish();
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		switch(id) {
		case PROGRESS_DIALOG:
			return ProgressDialog.show(this, "","Loading. Please wait...", true);
			default: return null;
			}
		} 

	private class ProgressThread extends Thread {
		private Handler handler;
		//private ArrayList<String> data;
		public ProgressThread(Handler handler) {
			this.handler = handler;
			//this.data = data;
			} 
		@Override
		public void run() {
			for (int i=0; i<8; i++) {
				//data.add("ListItem");
				//后台数据处理 
				try {
					Thread.sleep(100);
					}catch(InterruptedException e) {
						Message msg = handler.obtainMessage();
						Bundle b = new Bundle();
						b.putInt("state", STATE_ERROR);
						msg.setData(b);
						handler.sendMessage(msg);
						}
				}
			Message msg = handler.obtainMessage();
			Bundle b = new Bundle();
			b.putInt("state", STATE_FINISH);
			msg.setData(b);
			handler.sendMessage(msg);
		} 
	}
	
	private final Handler handler = new Handler(Looper.getMainLooper()) {
		public void handleMessage(Message msg) {
			// 处理Message，更新ListView
			int state = msg.getData().getInt("state");
			switch(state){
			case STATE_FINISH:
				dismissDialog(PROGRESS_DIALOG);
				//Toast.makeText(getApplicationContext(),"加载完成!",Toast.LENGTH_LONG).show();
				//adapter = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,data );
				//setListAdapter(adapter);
				break;
				case STATE_ERROR:
					dismissDialog(PROGRESS_DIALOG);
					//Toast.makeText(getApplicationContext(),"处理过程发生错误!",Toast.LENGTH_LONG).show();
					//adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, data );                                                    setListAdapter(adapter);
					break;
					default:	
			}
		}
	};
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

}
