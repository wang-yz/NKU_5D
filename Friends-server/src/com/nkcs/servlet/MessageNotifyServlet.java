package com.nkcs.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.nkcs.entity.Activities;
import com.nkcs.service.ActivityService;
import com.nkcs.service.UserService;
import com.nkcs.service.impl.ActivityServiceImpl;
import com.nkcs.service.impl.UserServiceImpl;

public class MessageNotifyServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
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
				String username = request.getParameter("username");
				String password = request.getParameter("password");
				
//				String username = "0301";
//				String password = "111";
				
				//组合业务对象
				ActivityService activityService=new ActivityServiceImpl();
				
				List<Activities> list = activityService.MessageNotify(username, password);
				
				Gson gson=new Gson();
				String act_toString=gson.toJson(list);
//				System.out.println("act_toString="+act_toString);
				out.println(act_toString);
				out.flush();
				out.close();
	}

}
