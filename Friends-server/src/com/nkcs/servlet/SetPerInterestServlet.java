package com.nkcs.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.If;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nkcs.service.InterestService;
import com.nkcs.service.impl.InterestServiceImpl;


public class SetPerInterestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    private InterestService interestServiceImpl=new InterestServiceImpl();


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//设置输出内容类型
		response.setContentType("text/html;charset=utf-8");	
		
		//设置字符编码---需要字符转码时加此句
		request.setCharacterEncoding("utf-8"); 
		
		//获取out输出对象---需要输出内容时加此句
		PrintWriter out = response.getWriter();	
		
		int userid = Integer.valueOf(request.getParameter("userid"));
		String listInfo =  request.getParameter("listInfo");
		Gson gson=new Gson();
		java.lang.reflect.Type type = new TypeToken<ArrayList<String>>(){}.getType();
		ArrayList<String> interests = gson.fromJson(listInfo, type);
		
		System.out.println("这是setper"+interests.size());
		System.out.println("这是setper"+userid);
		
		int sumrows=interestServiceImpl.setPerInterest(userid, interests); 
		//sumrows是共加入的行数
		//int sumrows=3;
		if(sumrows==interests.size()){
			out.println("修改成功");
		}else{
			out.println("修改失败");
		}
		out.println("修改成功");
	}

}
