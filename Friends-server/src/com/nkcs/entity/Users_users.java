package com.nkcs.entity;

import java.io.Serializable;

public class Users_users implements Serializable {

	private int userid;
	private int use_userid;
	
	public Users_users() {
		super();
	}
	
	public Users_users(int userid, int use_userid) {
		super();
		this.userid = userid;
		this.use_userid = use_userid;
	}
	
	public int getUserid() {
		return userid;
	}
	public void setUserid(int userid) {
		this.userid = userid;
	}
	public int getUse_userid() {
		return use_userid;
	}
	public void setUse_userid(int use_userid) {
		this.use_userid = use_userid;
	}
	
	
}
