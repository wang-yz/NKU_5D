package com.nkcs.friends.app;

import com.nkcs.friends.entity.Activities;
import com.nkcs.friends.entity.Users;

import android.app.Application;

public class MyApp extends Application {
	
	private Users user;
	
	private Activities activity;
	
	private String LiuURL;
	
	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}
	
	public Activities getActivity(){
		return activity;
	}
	
	public void setActivity(Activities activity){
		this.activity = activity;
	}
	
	// 协同开发需要 定义全局URL
	public String getLiuURL() {
		this.LiuURL = "http://123.206.20.37:8080/Friends/";
		return LiuURL;
	}
}
