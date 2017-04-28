package com.nkcs.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.nkcs.entity.Activities;
import com.nkcs.service.ActivityService;
import com.nkcs.service.impl.ActivityServiceImpl;


public class SelectActivityServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		//设置输出内容类型
		response.setContentType("text/html;charset=utf-8");	
		
		//设置字符编码---需要字符转码时加此句
		request.setCharacterEncoding("utf-8"); 
		
		//获取out输出对象---需要输出内容时加此句
		PrintWriter out = response.getWriter();	
		
		String act_name=request.getParameter("act_name");
		//System.out.println(act_name+"在外部");
		ActivityService activityServiceImpl=new ActivityServiceImpl();
		
		List<Activities> list=new ArrayList<Activities>();
		list=activityServiceImpl.findActivityByName(act_name);
		Gson gson=new Gson();
		String act_toString=gson.toJson(list);
		//System.out.println(act_toString);
		//System.out.println("fdgdfg"+act_toString);
		out.println(act_toString);
		out.flush();
		out.close();
	}

}
