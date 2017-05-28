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


public class GuessLikeServlet extends HttpServlet {

	private int userid;
	private List<Activities> data;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		response.setContentType("text/html;charset=utf-8");
		request.setCharacterEncoding("UTF-8");
		PrintWriter out =response.getWriter();
		
		
		userid=Integer.valueOf(request.getParameter("userid"));
		//System.out.println(act_name+"在外部");
		ActivityService activityServiceImpl=new ActivityServiceImpl();
		
		List<Activities> list=new ArrayList<Activities>();
		list=activityServiceImpl.findActivityByUserid(userid);
		Gson gson=new Gson();
		String act_toString=gson.toJson(list);
		System.out.println(act_toString);
		out.println(act_toString);
		out.flush();
		out.close();
	}

}
