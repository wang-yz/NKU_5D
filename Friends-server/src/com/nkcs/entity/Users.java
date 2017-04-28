package com.nkcs.entity;

import java.io.Serializable;

public class Users implements Serializable {

	private int userid;
	private String username;
	private String password;
	private String gender;
	private String photo;
	private String introduction;
	private int score;
	private String nickname;
	private String address1;
	private String address2;
	private String phonenumber;
	private String ip;
	
	public Users() {
		super();
	}

	public Users(int userid, String username, String password, String gender,
			String photo, String introduction, int score, String nickname,
			String address1, String address2, String phonenumber) {
		super();
		this.userid = userid;
		this.username = username;
		this.password = password;
		this.gender = gender;
		this.photo = photo;
		this.introduction = introduction;
		this.score = score;
		this.nickname = nickname;
		this.address1 = address1;
		this.address2 = address2;
		this.phonenumber = phonenumber;
	}

	public int getUserid() {
		return userid;
	}

	public void setUserid(int userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(String phonenumber) {
		this.phonenumber = phonenumber;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}
	
}
