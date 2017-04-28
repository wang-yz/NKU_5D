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
import com.google.gson.reflect.TypeToken;
import com.nkcs.entity.Users;
import com.nkcs.service.UserService;
import com.nkcs.service.impl.UserServiceImpl;
import com.sun.org.apache.bcel.internal.generic.Type;

public class SelectFriendsServlet extends HttpServlet {
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
		
//		String username = "0301";
//		String password = "111";
		System.out.println(username);
		System.out.println(password);
		UserService userService = new UserServiceImpl();
		List<Users> friendsList = userService.selectFriends(username, password);
		Gson gson = new Gson();
		String friendsJson = gson.toJson(friendsList);
		
		System.out.println("friendsJson" + friendsJson);

		out.println(friendsJson);
		out.flush();
		out.close();		
		
	}

}
