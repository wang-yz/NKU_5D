package com.nkcs.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.nkcs.dao.UserDAO;
import com.nkcs.dao.impl.UserDAOImpl;
import com.nkcs.entity.Users;
import com.nkcs.service.UserService;

public class UserServiceImpl implements UserService {

private UserDAO userDAO = new UserDAOImpl();
	
	//用户登录
	@Override
	public Users login(String username, String password){
		
		Users user = userDAO.findUser(username, password);				
		if(user!=null){
			if(userDAO.updateScore(user, 1)){ 
				user.setScore(user.getScore() + 1);	
				return user;
			}
		}
		
		return null;
	}

	//用户注册
	@Override
	public Users register(Users user) {
		if(userDAO.insertUser(user)){
			user = userDAO.findUser(user.getUsername(), user.getPassword());
			return user;
		}
		return null;
	}

	//更新用户头像
	@Override
	public void updatePhoto(String serverFilename, Users user) {
		if(userDAO.updatePhoto(user, serverFilename)){
			System.out.println("成功！");
		}
	}

	//检查用户名是否存在
	public boolean checkUsername(String username) {
		if(userDAO.findUsername(username))
			return true;
		else 
			return false;
	}

	@Override
	public Users updateUser(Users users, String username, String password) {
		// TODO Auto-generated method stub
		if(userDAO.updateUsers(users, username, password))
			return userDAO.findUser(users.getUsername(), users.getPassword());
		else 
			return null;
	}
	
	@Override
	public boolean attendActivity(String userid, String Activityid) {
		if(userDAO.attendActivity(userid, Activityid)&&userDAO.updateInActivity(Activityid))
			return true;
		else 
			return false;
	}
	
	@Override
	public boolean checkUserAttend(String userid, String Activityid) {
		if(userDAO.checkUserAttend(userid, Activityid))
			return true;
		else return false;
	}

	@Override
	public boolean cancelActivity(String userid, String Activityid) {
		if(userDAO.cancelActivity(userid, Activityid)&&userDAO.updateDeActivity(Activityid))
			return true;
		else 
			return false;
	}

	@Override
	public List<Users> getMyFriends(String username, String password) {
		List<Users> userlist = new ArrayList<Users>();
		
		Users user = userDAO.findUser(username, password);
		
		List<Integer> idlist = new ArrayList<Integer>();
		
		idlist = userDAO.findUserUser(user.getUserid());
		
		for(int id:idlist){
			Users users = new Users();
			users = userDAO.findUsers(id);
			userlist.add(users);
		}
		System.out.println(userlist);
		return userlist;
	}

	@Override
	public boolean deletefriends(String nickname, String phonenumber, int userid) {
		Users users = userDAO.findUserByPhoneNick(nickname, phonenumber);
		return userDAO.deleteUserUser(userid, users.getUserid());
	}

	@Override
	public boolean NotNotifyUser_activity(String userid, String activityid) {
		return userDAO.updateUser_activity(userid, activityid);
	}

	@Override
	public List<Users> selectFriends(String username, String password) {
		
		Set<Integer> set = userDAO.selectFriends(userDAO.findUser(username, password).getUserid());
		
		List<Users> userslist = new ArrayList<Users>();
		
		Iterator<Integer> iterator = set.iterator();
		
		while (iterator.hasNext()) {			
			Users users = userDAO.findUsers(iterator.next());
			userslist.add(users);			
		}		
		return userslist;
	}

	@Override
	public boolean followFriends(String userid, String friendsid) {
		return userDAO.insertUsers_users(userid, friendsid);
	}

	//insert ip
	@Override
	public boolean insertUserIp(String username, String ip) {
		// TODO Auto-generated method stub
		
		return userDAO.insertUserIp(username, ip);
	}
		
}
