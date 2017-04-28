package com.nkcs.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nkcs.entity.Users;
import com.nkcs.util.StringUtil;
import com.nkcs.service.UserService;
import com.nkcs.service.impl.UserServiceImpl;
import com.google.gson.Gson;
import com.jspsmart.upload.SmartFile;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

public class FileUploadServlet extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//设置输出内容类型
		response.setContentType("text/html;charset=utf-8");	

		//获取out输出对象---需要输出内容时加此句
		PrintWriter out = response.getWriter();	   	

	    try {
			//步骤1---创建上传组件---实例化SmartUpload对象
			SmartUpload smartUpload = new SmartUpload("utf-8");

			//步骤2---初始化上传组件---调用initialize()方法
			smartUpload.initialize(getServletConfig(), request, response);
				
			//限制上传的文件类型
			smartUpload.setAllowedFilesList("jpg,gif");

			//限制上传的文件大小
			smartUpload.setMaxFileSize(200*1024);
			
			//步骤3---上传文件到服务器的临时内存中---调用upload()方法
			smartUpload.upload();

			//步骤4---提取上传文件
			SmartFile smartFile = smartUpload.getFiles().getFile(0);  
			
			//步骤5---准备上传文件的存储路径和文件名---保证文件名唯一
			String serverFilePath = request.getRealPath("image/photo");
			//String serverFilePath = "e:/upload";
			File file = new File(serverFilePath);
			if(!file.exists()){
				file.mkdir();
			}	    
			
			//System.out.println("real path=" + serverFilePath);
			
			//生成唯一的文件名  yyyyMMddHHmmssXXX 000-999  
			String serverFileExt = smartFile.getFileExt();
			
			String serverFilename = StringUtil.convertFilename() + "." +serverFileExt;
			
			//步骤6---保存上传文件
			smartFile.saveAs(serverFilePath + "/" + serverFilename);
			
			//接收数据
			String username=smartUpload.getRequest().getParameter("username");
			String password=smartUpload.getRequest().getParameter("password");

			System.out.println(username+"   "+password);

			UserService userService = new UserServiceImpl();
			
			Users user = userService.login(username, password);
			
			System.out.println(user.getUsername()+user.getPhoto());
			userService.updatePhoto(serverFilename,user);
			
			Gson gson = new Gson();
			String userJson = gson.toJson(user);
			
			out.println(userJson);

	    } catch (SmartUploadException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}

}

