package com.nkcs.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nkcs.service.ActivityService;
import com.nkcs.service.impl.ActivityServiceImpl;

public class GetChatRecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//设置输出内容类型
		response.setContentType("text/html;charset=utf-8");	

		//获取out输出对象---需要输出内容时加此句
		PrintWriter out = response.getWriter();	   	

		//获取session对象---需要session对象时加此句
	    HttpSession session = request.getSession();	

		//设置字符编码---需要字符转码时加此句
		request.setCharacterEncoding("utf-8"); 
		
		//接收数据
//		String act_id = request.getParameter("act_id");
		String act_id = "1";
		
		ActivityService activityService = new ActivityServiceImpl();
		String str = activityService.getChatRecord(act_id);
		System.out.println(str);
		out.println(str);
		out.flush();
	}

}
