package com.nkcs.dao;

import java.util.List;
import java.util.Set;

import com.nkcs.entity.Users;

public interface UserDAO {

	boolean insertUserIp(String username, String ip);
	//根据用户名和密码查找用户
	Users findUser(String username, String password);
	boolean findUsername(String username);
	
	//给用户加积分
	boolean updateScore(Users user, int score);

	//插入新用户 
	boolean insertUser(Users user);
	
	boolean updatePhoto(Users user,String serverFilename);
	
	boolean updateUsers(Users user, String username, String password);
	
	boolean attendActivity(String userid, String Activityid);
	
	boolean updateInActivity(String Activityid);
	
	boolean checkUserAttend(String userid,String Activityid);
	
	boolean cancelActivity(String userid, String Activityid);
	
	boolean updateDeActivity(String Activityid);
	
	List<Integer> findUserUser(int userid);
	Users findUsers(int id);
	
	boolean deleteUserUser(int userid, int use_userid);
	Users findUserByPhoneNick(String nickname, String phonenumber);

	boolean updateUser_activity(String userid ,	String activityid);
	
	Set<Integer> selectFriends(int usersid);
	
	boolean insertUsers_users(String userid, String use_userid);
}