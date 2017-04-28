package com.nkcs.entity;

import java.io.Serializable;

public class Users_interests implements Serializable {

	private int userid;
	private String int_name;
	
	public Users_interests() {
		super();
	}

	public Users_interests(int userid, String int_name) {
		super();
		this.userid = userid;
		this.int_name = int_name;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getInt_name() {
		return int_name;
	}

	public void setInt_name(String int_name) {
		this.int_name = int_name;
	}
	
	
}
