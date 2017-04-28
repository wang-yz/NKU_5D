package com.nkcs.entity;

import java.io.Serializable;

public class Chatrecords implements Serializable {

	private int cha_id;
	private int act_id;
	private int cha_userid;
	private String cha_time;
	private String cha_content;
	
	public Chatrecords() {
		super();
	}

	public Chatrecords(int cha_id, int act_id, int cha_userid, String cha_time,
			String cha_content) {
		super();
		this.cha_id = cha_id;
		this.act_id = act_id;
		this.cha_userid = cha_userid;
		this.cha_time = cha_time;
		this.cha_content = cha_content;
	}

	public int getCha_id() {
		return cha_id;
	}

	public void setCha_id(int cha_id) {
		this.cha_id = cha_id;
	}

	public int getAct_id() {
		return act_id;
	}

	public void setAct_id(int act_id) {
		this.act_id = act_id;
	}

	public int getCha_userid() {
		return cha_userid;
	}

	public void setCha_userid(int cha_userid) {
		this.cha_userid = cha_userid;
	}

	public String getCha_time() {
		return cha_time;
	}

	public void setCha_time(String cha_time) {
		this.cha_time = cha_time;
	}

	public String getCha_content() {
		return cha_content;
	}

	public void setCha_content(String cha_content) {
		this.cha_content = cha_content;
	}

	
	
}
