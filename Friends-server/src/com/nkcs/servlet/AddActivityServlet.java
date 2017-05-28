package com.nkcs.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.jspsmart.upload.SmartFile;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;
import com.nkcs.entity.Activities;
import com.nkcs.service.ActivityService;
import com.nkcs.service.impl.ActivityServiceImpl;
import com.nkcs.util.StringUtil;

public class AddActivityServlet extends HttpServlet {
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
	
		String ActivityName = null, MaxNumber = null, Deadline = null, 
				StartTime = null, EndTime = null, interest = null, 
				address1 = null, address2 = null, Introduction = null,
				address0 = null;

		System.out.println("hahahhahahahh");
		out.println("hahahhahahahh");
		
//		String ActivityName = "aa", MaxNumber = "aa", Deadline = "2012-11-11 10:10:10", 
//		StartTime = "2012-11-11 10:10:10", EndTime = "2012-11-11 10:10:10", interest = "所有", 
//		address1 = "天津市", address2 = "和平区", Introduction = "not bad";
		
	    try {

			SmartUpload smartUpload = new SmartUpload("utf-8");
			smartUpload.initialize(getServletConfig(), request, response);
			smartUpload.setAllowedFilesList("jpg,gif");
			smartUpload.upload();
			SmartFile smartFile = smartUpload.getFiles().getFile(0);  
			String serverFilePath = request.getRealPath("image/photo");
			File file = new File(serverFilePath);
			if(!file.exists()){
				file.mkdir();
			}	    
			String serverFileExt = smartFile.getFileExt();
			
			String serverFilename = StringUtil.convertFilename() + "." +serverFileExt;
			
			//步骤6---保存上传文件
			smartFile.saveAs(serverFilePath + "/" + serverFilename);
			
			ActivityName = smartUpload.getRequest().getParameter("ActivityName");
			MaxNumber = smartUpload.getRequest().getParameter("MaxNumber");
			Deadline = smartUpload.getRequest().getParameter("Deadline");
			StartTime = smartUpload.getRequest().getParameter("StartTime");
			EndTime = smartUpload.getRequest().getParameter("EndTime");
			interest = smartUpload.getRequest().getParameter("interest");
			address1 = smartUpload.getRequest().getParameter("address1");
			address2 = smartUpload.getRequest().getParameter("address2");
			Introduction = smartUpload.getRequest().getParameter("Introduction");
			address0 = smartUpload.getRequest().getParameter("address0");
			String photo = serverFilename;
			
			Activities activities = new Activities();
			activities.setAct_name(ActivityName);
			activities.setAct_type(interest);
			activities.setAct_maxNumber(Integer.parseInt(MaxNumber));
			activities.setAct_deadline(Deadline);
			activities.setAct_starttime(StartTime);
			activities.setAct_endtime(EndTime);
			activities.setAct_picture(photo);
			activities.setAct_address0(address0);
			activities.setAct_address1(address1);
			activities.setAct_address2(address2);
			activities.setAct_intro(Introduction);
			
			ActivityService activityService = new ActivityServiceImpl();
			if(activityService.addActivity(activities)){
				out.println("111");
				System.out.println("成功");
				out.flush();
			}
			
	    } catch (SmartUploadException e) {
			e.printStackTrace();
			out.println();
			System.out.println("失败");
			out.flush();
		} 
	}

}
