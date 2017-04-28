package com.nkcs.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.nkcs.entity.Activities;
import com.nkcs.service.ActivityService;
import com.nkcs.service.impl.ActivityServiceImpl;

public class ActivityShowServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//设置输出内容类型
		response.setContentType("text/html;charset=utf-8");	
	
		//设置字符编码---需要字符转码时加此句
		request.setCharacterEncoding("utf-8"); 
		
		//获取out输出对象---需要输出内容时加此句
		PrintWriter out = response.getWriter();	
		
		String int_name =  request.getParameter("int_name");
		int startNum =  Integer.parseInt(request.getParameter("startNum"));
		int pageSize =  Integer.parseInt(request.getParameter("pageSize"));

		System.out.println("int_name: " + int_name);
		System.out.println("startNum: " + startNum);
		System.out.println("pageSize: " + pageSize);
		
		ActivityService activityService = new ActivityServiceImpl();
		List<Activities> activityList = activityService.activityShow(int_name, startNum, pageSize);
		
		 System.out.println(activityList.get(0).getAct_intro().toString());
		
		Gson gson = new Gson();
		String activityJson = gson.toJson(activityList);
		
		System.out.println("activityJson =" + activityJson);

		out.println(activityJson);
		out.flush();
		out.close();
	}

}
