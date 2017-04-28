package com.nkcs.entity;

import java.io.Serializable;

public class Photowalls implements Serializable {

	private int pho_id;
	private int act_id;
	private int pho_name;
	
	public Photowalls() {
		super();
	}

	public Photowalls(int pho_id, int act_id, int pho_name) {
		super();
		this.pho_id = pho_id;
		this.act_id = act_id;
		this.pho_name = pho_name;
	}

	public int getPho_id() {
		return pho_id;
	}

	public void setPho_id(int pho_id) {
		this.pho_id = pho_id;
	}

	public int getAct_id() {
		return act_id;
	}

	public void setAct_id(int act_id) {
		this.act_id = act_id;
	}

	public int getPho_name() {
		return pho_name;
	}

	public void setPho_name(int pho_name) {
		this.pho_name = pho_name;
	}
	
	
}
