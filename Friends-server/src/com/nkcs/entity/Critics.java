package com.nkcs.entity;

import java.io.Serializable;

public class Critics implements Serializable {

	private int cri_id;
	private int act_id;
	private String cri_content;
	private int cri_fromid;
	private int cri_toid;
	private String cri_time;
	
	public Critics() {
		super();
	}

	public Critics(int cri_id, int act_id, String cri_content, int cri_fromid,
			int cri_toid, String cri_time) {
		super();
		this.cri_id = cri_id;
		this.act_id = act_id;
		this.cri_content = cri_content;
		this.cri_fromid = cri_fromid;
		this.cri_toid = cri_toid;
		this.cri_time = cri_time;
	}

	public int getCri_id() {
		return cri_id;
	}

	public void setCri_id(int cri_id) {
		this.cri_id = cri_id;
	}

	public int getAct_id() {
		return act_id;
	}

	public void setAct_id(int act_id) {
		this.act_id = act_id;
	}

	public String getCri_content() {
		return cri_content;
	}

	public void setCri_content(String cri_content) {
		this.cri_content = cri_content;
	}

	public int getCri_fromid() {
		return cri_fromid;
	}

	public void setCri_fromid(int cri_fromid) {
		this.cri_fromid = cri_fromid;
	}

	public int getCri_toid() {
		return cri_toid;
	}

	public void setCri_toid(int cri_toid) {
		this.cri_toid = cri_toid;
	}

	public String getCri_time() {
		return cri_time;
	}

	public void setCri_time(String cri_time) {
		this.cri_time = cri_time;
	}
	
	
	
}
