package com.nkcs.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.nkcs.dao.ActivityDAO;
import com.nkcs.dao.UserDAO;
import com.nkcs.dbutil.DBManager;
import com.nkcs.entity.Activities;
import com.nkcs.entity.Chatrecords;
import com.nkcs.entity.Interests;
import java.sql.Timestamp;
public class ActivityDAOImpl implements ActivityDAO {

	private DBManager dbManager = new DBManager();
	private UserDAO userDAO = new UserDAOImpl();
	
	@Override
	public List<Activities> findActivity(String int_name, int startNum,int pageSize) {
		String sql;
		List<Activities> activityList;
		ResultSet rst;
		if(int_name.equals("Latest")){
			sql = "select * from activities order by act_posttime desc limit ?,?";
			activityList = new ArrayList<Activities>();
			rst = dbManager.execQuery(sql, startNum, pageSize);
		} else {
			sql = "select * from activities where (act_id) in (select act_id from interests_activities where int_name = ?) order by act_posttime desc limit ?,?";
			activityList = new ArrayList<Activities>();
			rst = dbManager.execQuery(sql, int_name, startNum, pageSize);
		}
		System.out.println(sql);
		System.out.println(rst);

		try {
			while(rst.next()) {
				Activities activity = new Activities();
				activity.setAct_id(rst.getInt(1));
				activity.setUserid(rst.getInt(2));
				activity.setAct_name(rst.getString(3));
				activity.setAct_type(rst.getString(4));
				activity.setAct_value(rst.getInt(5));
				activity.setAct_curNumber(rst.getInt(6));
				activity.setAct_maxNumber(rst.getInt(7));
				activity.setAct_posttime(rst.getString(8));
				activity.setAct_deadline(rst.getString(9));
				activity.setAct_starttime(rst.getString(10));
				activity.setAct_endtime(rst.getString(11));
				activity.setAct_picture(rst.getString(12));
				activity.setAct_address0(rst.getString(13));
				activity.setAct_address1(rst.getString(14));
				activity.setAct_address2(rst.getString(15));
				activity.setAct_intro(rst.getString(16));
				activityList.add(activity);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			dbManager.closeConnection();
		}
		return activityList;
	}
	
	@Override
	public boolean addActivity(Activities activities) {
		String sql = "insert into activities values(null, ?, ?, 0, 0, ?, now(), ?, ?, ?, ?, null, ?, ?, ?)";
		return dbManager.execUpdate(sql, activities.getAct_name(), activities.getAct_type(), 
				activities.getAct_maxNumber(), activities.getAct_deadline(), 
				activities.getAct_starttime(), activities.getAct_endtime(), activities.getAct_picture(),
				activities.getAct_address1(), activities.getAct_address2(), activities.getAct_intro()) > 0;
	}

	@Override
	public List<Activities> findActivityByName(String act_name) {
		System.out.println(act_name);
		List<Activities> activityList=new ArrayList<Activities>();
		String sql="select * from activities where act_name like ? ";
		ResultSet rst=dbManager.execQuery(sql, "%"+act_name+"%");
		try {
			while(rst.next()) {
				Activities activity = new Activities();
				activity.setAct_id(rst.getInt(1));
				activity.setUserid(rst.getInt(2));
				activity.setAct_name(rst.getString(3));
				activity.setAct_type(rst.getString(4));
				activity.setAct_value(rst.getInt(5));
				activity.setAct_curNumber(rst.getInt(6));
				activity.setAct_maxNumber(rst.getInt(7));
				activity.setAct_posttime(rst.getString(8));
				activity.setAct_deadline(rst.getString(9));
				activity.setAct_starttime(rst.getString(10));
				activity.setAct_endtime(rst.getString(11));
				activity.setAct_picture(rst.getString(12));
				activity.setAct_address0(rst.getString(13));
				activity.setAct_address1(rst.getString(14));
				activity.setAct_address2(rst.getString(15));
				activity.setAct_intro(rst.getString(16));
				activityList.add(activity);
			}
		} catch (SQLException e) {
			System.err.println("while出错");
			e.printStackTrace();
		}
		return activityList;
	}

	@Override
	public List<Activities> findCalendar(int userid, String calendarModel,Date curDate) {
		String sql;
		List<Activities> activityList;
		ResultSet rst;
		activityList = new ArrayList<Activities>();
		Date usefulDate;
		
		if(curDate==null){
			usefulDate = new Date();
		}else {
			usefulDate=curDate;
		}
		
		
		//把时间转为整点时间，去掉时分秒
		//SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd 00:00:00");
		String str = sdf.format(usefulDate);
		//时间存储为字符串
		System.out.println(str);
		usefulDate=Timestamp.valueOf(str);//转换时间字符串为Timestamp
		System.out.println(Timestamp.valueOf(str));//输出结果
		
		System.out.println("dao 开始");
		if(calendarModel.equals("Attend")){
			//参加
			//System.out.println("dao if");
			sql = "select * from activities "
					+ "where (act_id) in (select act_id from users_activities where userid = ?) "
					+ "and TIMESTAMPDIFF(second,?,act_starttime) < 86400 "
					+ "and TIMESTAMPDIFF(second,?,act_starttime) >= 0 "
					+ "order by act_posttime desc";
		} else {
			//发起的
			//System.out.println("dao else");
			sql = "select * from activities "
					+ "where userid = ? "
					+ "and TIMESTAMPDIFF(second,act_starttime,?) < 86400 "
					+ "and TIMESTAMPDIFF(second,act_starttime,?) >= 0 "
					+ "order by act_posttime desc";
		}
		
		rst = dbManager.execQuery(sql, userid,usefulDate,usefulDate);
		
		System.out.println(sql);
		System.out.println(rst);

		int i=0;
		try {
			while(rst.next()) {
				i++;
				System.out.println("查到了第"+i+"个");
				Activities activity = new Activities();
				activity.setAct_id(rst.getInt(1));
				activity.setUserid(rst.getInt(2));
				activity.setAct_name(rst.getString(3));
				activity.setAct_type(rst.getString(4));
				activity.setAct_value(rst.getInt(5));
				activity.setAct_curNumber(rst.getInt(6));
				activity.setAct_maxNumber(rst.getInt(7));
				activity.setAct_posttime(rst.getString(8));
				activity.setAct_deadline(rst.getString(9));
				activity.setAct_starttime(rst.getString(10));
				activity.setAct_endtime(rst.getString(11));
				activity.setAct_picture(rst.getString(12));
				activity.setAct_address0(rst.getString(13));
				activity.setAct_address1(rst.getString(14));
				activity.setAct_address2(rst.getString(15));
				activity.setAct_intro(rst.getString(16));
				activityList.add(activity);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			dbManager.closeConnection();
		}
		return activityList;
	}

	@Override
	public List<Activities> findActivityByUserid(int userid) {
		// TODO Auto-generated method stub
		System.out.println("userid="+userid);
		List<Activities> activityList=new ArrayList<Activities>();
		String sql="select * from activities "
				+ "where act_id in "
				+ "(select distinct act_id from interests_activities where int_name in (select int_name from users_interests where users_interests.userid=?)) "
				+ "and act_id not in (select act_id from users_activities where users_activities.userid=?) and activities.userid !=?;";
		//order by (act_curNumber/act_maxNumber);  //猜你喜欢排序
		ResultSet rst=dbManager.execQuery(sql, userid,userid,userid);
		
		try {
			while(rst.next()) {
				Activities activity = new Activities();
				activity.setAct_id(rst.getInt(1));
				activity.setUserid(rst.getInt(2));
				activity.setAct_name(rst.getString(3));
				activity.setAct_type(rst.getString(4));
				activity.setAct_value(rst.getInt(5));
				activity.setAct_curNumber(rst.getInt(6));
				activity.setAct_maxNumber(rst.getInt(7));
				activity.setAct_posttime(rst.getString(8));
				activity.setAct_deadline(rst.getString(9));
				activity.setAct_starttime(rst.getString(10));
				activity.setAct_endtime(rst.getString(11));
				activity.setAct_picture(rst.getString(12));
				activity.setAct_address0(rst.getString(13));
				activity.setAct_address1(rst.getString(14));
				activity.setAct_address2(rst.getString(15));
				activity.setAct_intro(rst.getString(16));
				activityList.add(activity);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("while出错");
			e.printStackTrace();
		}
		return activityList;
	}
	//select * from activities where act_id in (select distinct act_id from interests_activities where int_name in (select int_name from users_interests where users_interests.userid=1)) and act_id not in (select act_id from users_activities where users_activities.userid=1) and activities.userid !1;";

	@Override
	public List<Activities> MessageNotify(String username, String password) {
	        //int i=1;
			System.out.println("username="+username+"   "+"password="+password);
			
			List<Activities> activityList=new ArrayList<Activities>();
			String sql="select * from activities , users , users_activities "
					+ "where users.userid = users_activities.userid and users_activities.act_id = activities.act_id "
					+ "and username = ? and password = ? and is_notify = 1 and TIMESTAMPDIFF(day,now(),act_starttime) <= 1 "
					+ "and TIMESTAMPDIFF(day,now(),act_starttime) >= 0 ";
			System.out.println("sql="+sql);
			
			ResultSet rst=dbManager.execQuery(sql, username , password);
			
			try {
					while(rst.next()) {
						Activities activity = new Activities();
						
						activity.setAct_id(rst.getInt(1));
						activity.setUserid(rst.getInt(2));
						activity.setAct_name(rst.getString(3));
						activity.setAct_type(rst.getString(4));
						activity.setAct_value(rst.getInt(5));
						activity.setAct_curNumber(rst.getInt(6));
						activity.setAct_maxNumber(rst.getInt(7));
						activity.setAct_posttime(rst.getString(8));
						activity.setAct_deadline(rst.getString(9));
						activity.setAct_starttime(rst.getString(10));
						activity.setAct_endtime(rst.getString(11));
						activity.setAct_picture(rst.getString(12));
						activity.setAct_address0(rst.getString(13));
						activity.setAct_address1(rst.getString(14));
						activity.setAct_address2(rst.getString(15));
						activity.setAct_intro(rst.getString(16));
						
						activityList.add(activity);
						
//						System.out.println("第"+i+"个activity为"+activity);
//						i++;
					}	
			} catch (SQLException e) {
				System.out.println("while出错");
				e.printStackTrace();
			}
			return activityList;
	}

	@Override
	public boolean addMessage(int act_id, int cha_userid, String cha_content) {
		String sql =  "insert into chatrecords values(null, ?, ?, now(), ?)";
		return dbManager.execUpdate(sql, act_id, cha_userid, cha_content) > 0;
	}

	@Override
	public String selectChatrecords(String act_id) {
		
		String sql = "select * from chatrecords where act_id = ?";
		ResultSet rs = dbManager.execQuery(sql, act_id);
		String chatrecords = "";
		int cha_userid = 0;
		String username = null;
		
		try {
			while(rs.next()){
				cha_userid = rs.getInt(3);
				username = userDAO.findUsers(cha_userid).getUsername();
				chatrecords += username + ":\n" + rs.getString(5) + "\n";
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return chatrecords;
	}
	
	@Override
	public List<Activities> findChatActivity(String userid) {
		int i=1;
		System.out.println("userid = " + userid);
		
		List<Activities> activityList=new ArrayList<Activities>();
		String sql="select * from users_activities , activities "
				+ "where users_activities.userid = ? and users_activities.act_id = activities.act_id  group by users_activities.act_id";
		ResultSet rst=dbManager.execQuery(sql, userid);
		
		try {
				//如果有数据，以列表返回
				while(rst.next()) {
					Activities activity = new Activities();
					
					activity.setAct_id(rst.getInt(2));
					activity.setAct_name(rst.getString(6));
					activity.setAct_curNumber(rst.getInt(9));
					activity.setAct_picture(rst.getString(15));
					activity.setAct_intro(rst.getString(19));
					
					activityList.add(activity);
					
					System.out.println("第"+i+"个activity为"+activity);
					i++;
				}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.println("while出错");
			e.printStackTrace();
		}
		return activityList;
	}
	
}
