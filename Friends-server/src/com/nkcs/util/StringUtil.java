﻿package com.nkcs.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class StringUtil {

	//转换日期时间的格式   format  Data-->String    parse  String --> Date
	public static String convertDatetime(String source, String pattern){
		
		SimpleDateFormat sdfSource = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Date date = null;
		
		try {
			 date = sdfSource.parse(source);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		SimpleDateFormat sdfDest = new SimpleDateFormat(pattern);
		
		return sdfDest.format(date);
		
	}
	
	//转换关键字加红
	public static String convertKeyword(String source, String keyword){
		return source.replaceAll(keyword, "<font color=red><b>" + keyword + "</b></font>");
		
	}
	
	//转换文件名格式   yyyyMMddHHmmssXXX 100-999  
	public static String convertFilename(){
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		
		Date date = new Date();
	
		String now = sdf.format(date);
		
		Random rd = new Random();
		
		int random = rd.nextInt(900) + 100;
		
		return now + random;
		
	}
}
