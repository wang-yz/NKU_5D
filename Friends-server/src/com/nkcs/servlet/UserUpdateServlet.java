package com.nkcs.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.nkcs.entity.Users;
import com.nkcs.service.UserService;
import com.nkcs.service.impl.UserServiceImpl;

public class UserUpdateServlet extends HttpServlet {
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
		String introduction = request.getParameter("introduction");
		String gender = request.getParameter("gender");
		String nickname = request.getParameter("nickname");
	    String oldPassword = request.getParameter("oldPassword");
		String address1 = request.getParameter("address0");
		String address2 = request.getParameter("address1");

		//创建并填充实体bean
		Users user = new Users();
		
		user.setUsername(username);
		user.setPassword(password);
		user.setPhonenumber(phonenumber);
		user.setIntroduction(introduction);
		user.setGender(gender);
		user.setNickname(nickname);
		user.setAddress1(address1);
		user.setAddress2(address2);
		
		System.out.println(username);
		System.out.println(password);
		System.out.println(oldPassword);
		System.out.println(address1);
		System.out.println(address2);
		
		UserService userService = new UserServiceImpl();
		user = userService.updateUser(user, username, oldPassword);
		
	    Gson gson = new Gson();			
		String userInfo = gson.toJson(user);
		out.println(userInfo);
		System.out.println(userInfo);
		out.flush();

	}

}
