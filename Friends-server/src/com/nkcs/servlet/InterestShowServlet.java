package com.nkcs.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.nkcs.entity.Interests;
import com.nkcs.service.InterestService;
import com.nkcs.service.impl.InterestServiceImpl;


public class InterestShowServlet extends HttpServlet {
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
		
		String int_type =  request.getParameter("int_type");
		
		InterestService interestService = new InterestServiceImpl();
		List<Interests> interestList = interestService.interestShow(int_type);
		
		System.out.println(int_type);
		System.out.println(interestList);
		
		Gson gson = new Gson();
		String interestJson = gson.toJson(interestList);
		
		out.println(interestJson);
		out.flush();
		out.close();
	}

}
