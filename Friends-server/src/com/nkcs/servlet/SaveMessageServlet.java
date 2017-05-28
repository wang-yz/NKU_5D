package com.nkcs.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nkcs.service.ActivityService;
import com.nkcs.service.impl.ActivityServiceImpl;

public class SaveMessageServlet extends HttpServlet {
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
		
		//接收数据
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String msg = request.getParameter("msg");
		int activityId = Integer.parseInt(request.getParameter("activityId").toString());
		
//		String username = "user1";
//		String password = "1";
//		String msg = "年后";
//		int activityId = 1;
		
		ActivityService activityService = new ActivityServiceImpl();
		if(activityService.saveMessage(username, password, msg, activityId)){
			System.out.println("true");
			out.println("true");
			out.flush();
		}else{
			System.out.println("false");
			out.println();
			out.flush();
		}
		
		
	}

}
