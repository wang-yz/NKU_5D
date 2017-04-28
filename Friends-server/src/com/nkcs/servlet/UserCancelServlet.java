package com.nkcs.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nkcs.service.UserService;
import com.nkcs.service.impl.UserServiceImpl;

//不再提醒
public class UserCancelServlet extends HttpServlet {
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
				String userid = request.getParameter("userid");
				String activityid = request.getParameter("activityid");
				
				System.out.println("userid = " + userid );
				System.out.println("activityid = " + activityid);
				
//				String userid ="1";
//				String activityid = "1";
				
				//组合业务对象
				UserService userService = new UserServiceImpl();
				
				if(userService.NotNotifyUser_activity(userid, activityid))
					System.out.println("更新成功");
				else System.out.println("更新失败");
	}

}
