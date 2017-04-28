package com.nkcs.entity;

import java.io.Serializable;

public class Interests_activities implements Serializable {

	private String int_name;
	private int act_id;
	
	public Interests_activities() {
		super();
	}

	public Interests_activities(String int_name, int act_id) {
		super();
		this.int_name = int_name;
		this.act_id = act_id;
	}

	public String getInt_name() {
		return int_name;
	}

	public void setInt_name(String int_name) {
		this.int_name = int_name;
	}

	public int getAct_id() {
		return act_id;
	}

	public void setAct_id(int act_id) {
		this.act_id = act_id;
	}
	
	
}
