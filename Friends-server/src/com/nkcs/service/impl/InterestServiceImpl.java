package com.nkcs.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.nkcs.dao.InterestDAO;
import com.nkcs.dao.UserDAO;
import com.nkcs.dao.impl.InterestDAOImpl;
import com.nkcs.dao.impl.UserDAOImpl;
import com.nkcs.entity.Interests;
import com.nkcs.entity.Users;
import com.nkcs.service.InterestService;

public class InterestServiceImpl implements InterestService {

	private InterestDAO interestDAO = new InterestDAOImpl();

	// 查找兴趣标签
	@Override
	public List<Interests> interestShow(String int_type) {
		List<Interests> interestList= interestDAO.findInterest(int_type); 
		return interestList;
	}

	@Override
	public int setPerInterest(int userid, ArrayList<String> listInfo) {
		System.out.println("service 层"+userid);
		int renumber = interestDAO.setPerInterest(userid, listInfo);
		
		return renumber;
	}

}
