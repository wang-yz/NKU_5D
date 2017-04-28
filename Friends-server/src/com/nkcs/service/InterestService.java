package com.nkcs.service;

import java.util.ArrayList;
import java.util.List;

import com.nkcs.entity.Interests;

public interface InterestService {
	
	// 查找兴趣标签
	List<Interests> interestShow(String str);

	// 设置个人兴趣标签
	int setPerInterest(int userid,ArrayList<String> listInfo);
}
