package com.nkcs.dao;

import java.util.Date;
import java.util.List;

import com.nkcs.entity.Activities;
import com.nkcs.entity.Chatrecords;

public interface ActivityDAO {
	// 根据二级兴趣标签查找+发布时间倒序
	List<Activities> findActivity(String int_name, int startNum, int pageSize);
	
	// 根据时间倒叙排列
	// List<Activities> findActivityTime();
	
	// 根据用户信息推荐
	// List<Activities> findActivityGuess(...);
	
	boolean addActivity(Activities activities);
	
	//活动名搜索活动
	List<Activities> findActivityByName(String act_name);

	List<Activities> findCalendar(int userid, String calendarModel,Date curDate);
	
	//根据userid猜你喜欢哪些活动
	List<Activities> findActivityByUserid(int userid);
	
	//查询判断返回活动列表
	List<Activities> MessageNotify(String username,String password);
	
	boolean addMessage(int act_id, int cha_userid, String cha_content);
	
	String selectChatrecords(String act_id);
	
	List<Activities> findChatActivity(String userid);
}
