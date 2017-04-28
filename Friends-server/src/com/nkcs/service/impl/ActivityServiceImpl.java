package com.nkcs.service.impl;

import java.util.Date;
import java.util.List;

import com.nkcs.dao.ActivityDAO;
import com.nkcs.dao.UserDAO;
import com.nkcs.dao.impl.ActivityDAOImpl;
import com.nkcs.dao.impl.UserDAOImpl;
import com.nkcs.entity.Activities;
import com.nkcs.service.ActivityService;

public class ActivityServiceImpl implements ActivityService {
	
	private ActivityDAO activityDAO = new ActivityDAOImpl();
	private UserDAO userDAO = new UserDAOImpl();

	@Override
	public List<Activities> activityShow(String int_name, int startNum, int pageSize) {
		List<Activities> activityList= activityDAO.findActivity(int_name, startNum, pageSize); 
		return activityList;
	}

	@Override
	public boolean addActivity(Activities activities) {
		return activityDAO.addActivity(activities);
	}

	@Override
	public List<Activities> findActivityByName(String act_name) {
		return activityDAO.findActivityByName(act_name);
	}

	@Override
	public List<Activities> calendarShow(int userid, String calendarModel,Date curDate) {
		List<Activities> activityList= activityDAO.findCalendar(userid, calendarModel,curDate); 
		return activityList;
	}

	@Override
	public List<Activities> findActivityByUserid(int userid) {
		return activityDAO.findActivityByUserid(userid);
	}

	@Override
	public List<Activities> MessageNotify(String username, String password) {
		return activityDAO.MessageNotify(username, password);
	}

	@Override
	public boolean saveMessage(String username, String password, String msg,
			int activityId) {
		int userid = userDAO.findUser(username, password).getUserid();
		return activityDAO.addMessage(activityId, userid, msg);
	}

	@Override
	public String getChatRecord(String act_id) {
		return activityDAO.selectChatrecords(act_id);
	}
	
	@Override
	public List<Activities> findChatActivity(String userid) {
		return activityDAO.findChatActivity(userid);
	}


}
