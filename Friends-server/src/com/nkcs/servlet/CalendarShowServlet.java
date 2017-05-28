package com.nkcs.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nkcs.entity.Activities;
import com.nkcs.service.ActivityService;
import com.nkcs.service.impl.ActivityServiceImpl;

public class CalendarShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 设置输出内容类型
		response.setContentType("text/html;charset=utf-8");

		// 设置字符编码---需要字符转码时加此句
		request.setCharacterEncoding("utf-8");

		// 获取out输出对象---需要输出内容时加此句
		PrintWriter out = response.getWriter();

		int userid = Integer.parseInt(request.getParameter("userid"));
		String calendarModel = request.getParameter("calendarModel");
		String currDate=request.getParameter("currDate");

		Gson gson = new Gson();
		Type type = new TypeToken<Date>(){}.getType();
		Date curDate = gson.fromJson(currDate, type);
		System.out.println("传入的数据");
		System.out.println("userid: " + userid);
		System.out.println("calendarModel: " + calendarModel);
		System.out.println("currDate="+currDate);

		ActivityService activityService = new ActivityServiceImpl();
		//List<Activities> activityList =new ArrayList<Activities>();
		List<Activities> activityList = activityService.calendarShow(userid, calendarModel,curDate);

		Gson gson1 = new Gson();
		String activityJson = gson1.toJson(activityList);

		System.out.println(activityJson);
		out.println(activityJson);
		out.flush();
		out.close();
	}

}
