package com.nkcs.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nkcs.entity.Users;
import com.nkcs.service.UserService;
import com.nkcs.service.impl.UserServiceImpl;
import com.google.gson.Gson;


public class UserLoginServlet extends HttpServlet {
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
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String ip=request.getParameter("ip");
		System.out.println("  username="+username +"    pass= "+ password +"    ip="+ip);
		
		//组合业务对象
		UserService userService = new UserServiceImpl();
		Users user=new Users();
		//调用业务方法
		if(userService.insertUserIp(username, ip)){
			System.out.println("ip insert successfully");
		}
		user = userService.login(username, password);
		System.out.println(user);
	    Gson gson = new Gson();
		
		String userInfo = gson.toJson(user);
		out.println(userInfo);
		
		out.flush();
			
	}

}