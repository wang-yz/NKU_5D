package com.nkcs.service;

import java.util.Date;
import java.util.List;

import com.nkcs.entity.Activities;

public interface ActivityService {
	// 根据二级兴趣标签查找+发布时间倒序
	List<Activities> activityShow(String int_name, int startNum, int pageSize);
	
	boolean addActivity(Activities activities);
	
	//根据活动名字符串搜索活动
	public List<Activities> findActivityByName(String act_name);
	
	List<Activities> calendarShow(int userid, String calendarModel, Date curDate);
	
	//根据userid查找可能喜欢的活动
	public List<Activities> findActivityByUserid(int userid);
	
	//查询判断返回活动列表
	List<Activities> MessageNotify(String username,String password);
	
	boolean saveMessage(String username, String password, String msg, int activityId);
	
	String getChatRecord(String act_id);
	
	List<Activities> findChatActivity(String userid);
	
}
