package com.nkcs.friends.entity;

import java.io.Serializable;

public class Interests implements Serializable {

	private String int_name;
	private String int_type;
	private String int_image;
	
	public Interests() {
		super();
	}

	public Interests(String int_name, String int_type) {
		super();
		this.int_name = int_name;
		this.int_type = int_type;
	}

	public String getInt_name() {
		return int_name;
	}

	public void setInt_name(String int_name) {
		this.int_name = int_name;
	}

	public String getInt_type() {
		return int_type;
	}

	public void setInt_type(String int_type) {
		this.int_type = int_type;
	}

	public String getInt_image() {
		return int_image;
	}

	public void setInt_image(String int_image) {
		this.int_image = int_image;
	}
	
	
}
