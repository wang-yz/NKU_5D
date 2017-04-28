package com.nkcs.entity;

import java.io.Serializable;

public class Address implements Serializable {

	private String add_name;
	private String add_type;
	
	public Address() {
		super();
	}

	public Address(String add_name, String add_type) {
		super();
		this.add_name = add_name;
		this.add_type = add_type;
	}

	public String getAdd_name() {
		return add_name;
	}

	public void setAdd_name(String add_name) {
		this.add_name = add_name;
	}

	public String getAdd_type() {
		return add_type;
	}

	public void setAdd_type(String add_type) {
		this.add_type = add_type;
	}
	
	
	
}
