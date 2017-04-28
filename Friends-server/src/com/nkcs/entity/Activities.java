package com.nkcs.entity;

import java.io.Serializable;

import com.sun.jmx.snmp.Timestamp;

public class Activities implements Serializable {

	private int act_id;
	private int userid;
	private String act_name;
	private String act_type;
	private int act_value;
	private int act_curNumber;
	private int act_maxNumber;
	private String act_posttime;
	private String act_deadline;
	private String act_starttime;
	private String act_endtime;
	private String act_picture;
	private String act_address0;
	private String act_address1;
	private String act_address2;
	private String act_intro;
	
	public Activities() {
		super();
	}

	public Activities(int act_id, String act_name, String act_type,
			int act_value, int act_curNumber, int act_maxNumber,
			String act_posttime, String act_deadline, String act_starttime,
			String act_endtime, String act_picture, String act_address0,
			String act_address1, String act_address2, String act_intro) {
		super();
		this.act_id = act_id;
		this.act_name = act_name;
		this.act_type = act_type;
		this.act_value = act_value;
		this.act_curNumber = act_curNumber;
		this.act_maxNumber = act_maxNumber;
		this.act_posttime = act_posttime;
		this.act_deadline = act_deadline;
		this.act_starttime = act_starttime;
		this.act_endtime = act_endtime;
		this.act_picture = act_picture;
		this.act_address0 = act_address0;
		this.act_address1 = act_address1;
		this.act_address2 = act_address2;
		this.act_intro = act_intro;
	}

	public int getAct_id() {
		return act_id;
	}

	public void setAct_id(int act_id) {
		this.act_id = act_id;
	}

	public String getAct_name() {
		return act_name;
	}

	public void setAct_name(String act_name) {
		this.act_name = act_name;
	}

	public String getAct_type() {
		return act_type;
	}

	public void setAct_type(String act_type) {
		this.act_type = act_type;
	}

	public int getAct_value() {
		return act_value;
	}

	public void setAct_value(int act_value) {
		this.act_value = act_value;
	}

	public int getAct_curNumber() {
		return act_curNumber;
	}

	public void setAct_curNumber(int act_curNumber) {
		this.act_curNumber = act_curNumber;
	}

	public int getAct_maxNumber() {
		return act_maxNumber;
	}

	public void setAct_maxNumber(int act_maxNumber) {
		this.act_maxNumber = act_maxNumber;
	}

	public String getAct_posttime() {
		return act_posttime;
	}

	public void setAct_posttime(String act_posttime) {
		this.act_posttime = act_posttime;
	}

	public String getAct_deadline() {
		return act_deadline;
	}

	public void setAct_deadline(String act_deadline) {
		this.act_deadline = act_deadline;
	}

	public String getAct_starttime() {
		return act_starttime;
	}

	public void setAct_starttime(String act_starttime) {
		this.act_starttime = act_starttime;
	}

	public String getAct_endtime() {
		return act_endtime;
	}

	public void setAct_endtime(String act_endtime) {
		this.act_endtime = act_endtime;
	}

	public String getAct_picture() {
		return act_picture;
	}

	public void setAct_picture(String act_picture) {
		this.act_picture = act_picture;
	}

	public String getAct_address0() {
		return act_address0;
	}

	public void setAct_address0(String act_address0) {
		this.act_address0 = act_address0;
	}

	public String getAct_address1() {
		return act_address1;
	}

	public void setAct_address1(String act_address1) {
		this.act_address1 = act_address1;
	}

	public String getAct_address2() {
		return act_address2;
	}

	public void setAct_address2(String act_address2) {
		this.act_address2 = act_address2;
	}

	public String getAct_intro() {
		return act_intro;
	}

	public void setAct_intro(String act_intro) {
		this.act_intro = act_intro;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}
	
	
}
