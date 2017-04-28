package com.nkcs.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.nkcs.dbutil.DBManager;
import com.nkcs.entity.Users;
import com.nkcs.dao.UserDAO;

public class UserDAOImpl implements UserDAO {

	private DBManager dbManager = new DBManager();
	
	
	//查找用户
	@Override
	public Users findUser(String username, String password) {
		
		String sql = "select * from users where username = ? and password = ?";
		
		ResultSet rs = dbManager.execQuery(sql, username, password);
		
		try {
			if(rs.next()){   //找到
				
				//创建并填充实体bean
				Users user = new Users();
				
				user.setUserid(rs.getInt(1));
				user.setUsername(username);
				user.setPassword(password);
				user.setGender(rs.getString(4));
				user.setPhoto(rs.getString(5));
				user.setIntroduction(rs.getString(6));
				user.setScore(rs.getInt(7));
				user.setNickname(rs.getString(8));
				user.setAddress1(rs.getString(9));
				user.setAddress2(rs.getString(10));
				user.setPhonenumber(rs.getString(11));
				user.setIp(rs.getString(12));
				return user;
				
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			
		} finally{
			dbManager.closeConnection();
		}
		
		return null;
	}

	//更新积分
	@Override
	public boolean updateScore(Users user, int score) {
		String sql = "update users set score = score + ? where userid = ?";
		return dbManager.execUpdate(sql, score, user.getUserid()) > 0;
	}
	
	//插入用户
	@Override
	public boolean insertUser(Users user) {
		String sql = "insert into users values(null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";	
		return dbManager.execUpdate(sql, user.getUsername(), user.getPassword(), user.getGender(),
				user.getPhoto(), user.getIntroduction(), user.getScore(), user.getNickname(), 
				user.getAddress1(), user.getAddress2(), user.getPhonenumber(),user.getIp()) > 0;
	}
	
	@Override
	public boolean findUsername(String username) {
		// TODO Auto-generated method stub
		String sql = "select * from users where username = ?";
		ResultSet rs = dbManager.execQuery(sql, username);
		try {
			if(rs.next())
				return true;
			else	
				return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	//更新头像
	@Override
	public boolean updatePhoto(Users user, String serverFilename) {
		String sql = "update users set photo = ? where userid = ?";
		System.out.println(serverFilename+"        "+user.getUserid());
		return dbManager.execUpdate(sql,serverFilename,user.getUserid()) > 0;
	}
	
	@Override
	public boolean updateUsers(Users user, String username, String password) {
		// TODO Auto-generated method stub
		String sql = "update Users set password = ?, gender = ?, introduction = ?"
				+ ", nickname = ?, address1 = ?, address2 = ?, phonenumber = ? "
				+ "where username = ? and password = ?";
		
		return dbManager.execUpdate(sql, user.getPassword(), user.getGender(), user.getIntroduction(),
				user.getNickname(), user.getAddress1(), user.getAddress2(), user.getPhonenumber(), 
				username, password) > 0;
	}
	@Override
	public boolean attendActivity(String userid, String Activityid) {
		String sql = "insert into users_activities values(?,?)";	
		return dbManager.execUpdate(sql, userid,Activityid) > 0;
	}

	@Override
	public boolean updateInActivity(String Activityid) {
		String sql = "update activities set act_curNumber = act_curNumber + ? where act_id = ?";
		return dbManager.execUpdate(sql, 1, Activityid) > 0;
	}
	
	@Override
	public boolean checkUserAttend(String userid,String Activityid) {
		if(dbManager==null){System.out.println("dbManager is null!");}
		String sql = "select * from users_activities where userid = ? and act_id= ? ";
		ResultSet rs = dbManager.execQuery(sql, userid,Activityid);
		try {
			if(rs.next())
				return true;
			else	
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean cancelActivity(String userid, String Activityid) {
		String sql = "delete from users_activities where userid = ? and act_id = ?";	
		return dbManager.execUpdate(sql, userid,Activityid) > 0;
	}

	@Override
	public boolean updateDeActivity(String Activityid) {
		String sql = "update activities set act_curNumber = act_curNumber - ? where act_id = ?";
		return dbManager.execUpdate(sql, 1, Activityid) > 0;
	}

	@Override
	public List<Integer> findUserUser(int userid) {
		String sql = "select use_userid from users_users where userid = ?";
		ResultSet rs = dbManager.execQuery(sql, userid);
		List<Integer> list = new ArrayList<Integer>();
		try {
			while(rs.next()){
				list.add(rs.getInt(1));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public Users findUsers(int id) {
		String sql = "select * from users where userid = ?";
		ResultSet rs = dbManager.execQuery(sql, id);
		Users user = new Users();
		try {
			while(rs.next()){
				user.setUserid(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setGender(rs.getString(4));
				user.setPhoto(rs.getString(5));
				user.setIntroduction(rs.getString(6));
				user.setScore(rs.getInt(7));
				user.setNickname(rs.getString(8));
				user.setAddress1(rs.getString(9));
				user.setAddress2(rs.getString(10));
				user.setPhonenumber(rs.getString(11));
				user.setIp(rs.getString(12));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public boolean deleteUserUser(int userid, int use_userid) {
		String sql = "delete from users_users where userid = ? and use_userid = ?";
		return dbManager.execUpdate(sql, userid, use_userid) > 0;
	}

	@Override
	public Users findUserByPhoneNick(String nickname, String phonenumber) {
		String sql = "select * from users where nickname = ? and phonenumber = ?";
		ResultSet rs = dbManager.execQuery(sql, nickname, phonenumber);
		try {
			if(rs.next()){
				Users user = new Users();
				user.setUserid(rs.getInt(1));
				user.setUsername(rs.getString(2));
				user.setPassword(rs.getString(3));
				user.setGender(rs.getString(4));
				user.setPhoto(rs.getString(5));
				user.setIntroduction(rs.getString(6));
				user.setScore(rs.getInt(7));
				user.setNickname(rs.getString(8));
				user.setAddress1(rs.getString(9));
				user.setAddress2(rs.getString(10));
				user.setPhonenumber(rs.getString(11));
				user.setIp(rs.getString(12));
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean updateUser_activity(String userid, String activityid) {
		String sql = "update users_activities set is_notify = 0 where userid = ? and act_id = ? ";
		return dbManager.execUpdate(sql, userid, activityid) > 0;
	}

	@Override
	public Set<Integer> selectFriends(int usersid) {
		
		Set<Integer> setId = new HashSet<Integer>();
		
		String sqlAct = "select act_id from users_activities where userid = ?";
		ResultSet rsAct = dbManager.execQuery(sqlAct, usersid);

		
		String sqlId = "select userid from users_activities where act_id = ? and userid != ? and userid not in "
				+ "(select use_userid from users_users where userid = ?)";
		try {
			while(rsAct.next()){
				ResultSet rsId = dbManager.execQuery(sqlId, rsAct.getInt(1), usersid, usersid);
				while(rsId.next()){
					setId.add(rsId.getInt(1));
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return setId;
	}

	@Override
	public boolean insertUsers_users(String userid, String use_userid) {
		String sql = "insert into users_users values(?,?)";	
		return dbManager.execUpdate(sql, userid, use_userid) > 0;
	}

	//insert ip
		@Override
		public boolean insertUserIp(String username, String ip) {
			// TODO Auto-generated method stub
			
			String addip_sql = "update users set ip = ? where username = ?";
			int rows=dbManager.execUpdate(addip_sql, ip,username);
			if(rows>0){
				return true;
			}
			return false;
		}

}
