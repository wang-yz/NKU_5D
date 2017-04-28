package com.nkcs.dao;

import java.util.ArrayList;
import java.util.List;

import com.nkcs.entity.Interests;

public interface InterestDAO {
	
	// 查找兴趣标签
	List<Interests> findInterest(String str);
	
	// 设置个人兴趣标签
	int setPerInterest(int userid,ArrayList<String> listInfo);

}
