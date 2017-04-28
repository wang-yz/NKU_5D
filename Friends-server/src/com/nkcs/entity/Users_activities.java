package com.nkcs.entity;

import java.io.Serializable;

public class Users_activities implements Serializable {

	private int userid;
	private int act_id;
	private String is_notify;
	
	public Users_activities() {
		super();
	}

	public Users_activities(int userid, int act_id) {
		super();
		this.userid = userid;
		this.act_id = act_id;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public int getAct_id() {
		return act_id;
	}

	public void setAct_id(int act_id) {
		this.act_id = act_id;
	}

	public String getIs_notify() {
		return is_notify;
	}

	public void setIs_notify(String is_notify) {
		this.is_notify = is_notify;
	}
	
	
	
}
