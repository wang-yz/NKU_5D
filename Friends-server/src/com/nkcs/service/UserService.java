package com.nkcs.service;

import java.util.List;

import com.nkcs.entity.Users;

public interface UserService {

	//用户登录
	Users login(String username, String password);
	
	//用户注册
	Users register(Users user);

	void updatePhoto(String serverFilename, Users user);
	
	boolean checkUsername(String username);
	
	Users updateUser(Users users, String username, String password);
	
	boolean attendActivity(String userid,String Activityid);
	
	boolean checkUserAttend(String userid,String Activityid);
	
	boolean cancelActivity(String userid,String Activityid);
	
	List<Users> getMyFriends(String username, String password);
	
	boolean deletefriends(String nickname, String phonenumber, int userid);
	
	boolean NotNotifyUser_activity(String userid, String activityid);
	
	List<Users> selectFriends(String username, String password);
	
	boolean followFriends(String userid, String friendsid);
	
	// insert ip
	boolean insertUserIp(String username, String ip);
}
