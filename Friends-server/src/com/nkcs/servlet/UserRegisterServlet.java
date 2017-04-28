package com.nkcs.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.nkcs.dbutil.DBManager;
import com.nkcs.entity.Users;
import com.nkcs.service.UserService;
import com.nkcs.service.impl.UserServiceImpl;

public class UserRegisterServlet extends HttpServlet {
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
		String phonenumber = request.getParameter("phonenumber");
		String ip = request.getParameter("ip");
	
		System.out.println("username="+username);
		System.out.println("password="+password);
		System.out.println("phonenumber="+phonenumber);
		//创建并填充实体bean
		Users user = new Users();
		
		user.setUsername(username);
		user.setPassword(password);
		user.setPhonenumber(phonenumber);
		
		user.setGender("男");
		user.setPhoto("1.gif");
		user.setIntroduction("");
		user.setScore(50);
		user.setNickname("");
		user.setAddress1("");
		user.setAddress2("");
		user.setIp(ip);
		
		System.out.println("user="+user);
		//组合业务对象
		UserService userService = new UserServiceImpl();
		
		if(!userService.checkUsername(username)){

			user = userService.register(user);
		    Gson gson = new Gson();			
			String userInfo = gson.toJson(user);
			out.println(userInfo);			
			out.flush();
		}
		else{
			
			out.println();
			out.flush();
		}
	
	}

}
