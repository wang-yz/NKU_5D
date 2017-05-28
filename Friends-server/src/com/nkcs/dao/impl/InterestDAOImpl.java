package com.nkcs.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.nkcs.dao.InterestDAO;
import com.nkcs.dbutil.DBManager;
import com.nkcs.entity.Interests;

public class InterestDAOImpl implements InterestDAO {

	private DBManager dbManager = new DBManager();
	
	@Override
	public List<Interests> findInterest(String int_type) {
		String sql;
		List<Interests> interestList;
		ResultSet rst;
		// int_type = "旅行";
		// int_type = "Food";
		if(int_type.equals("second")){
			sql = "select * from interests where int_type != 'first'";
			interestList = new ArrayList<Interests>();
			rst = dbManager.execQuery(sql);
		} else {
			sql = "select * from interests where int_type = ?";
			interestList = new ArrayList<Interests>();
			rst = dbManager.execQuery(sql, int_type);
		}
		System.out.println(sql);
		System.out.println(rst);

		try {
			while(rst.next()) {
				Interests interest = new Interests();
				interest.setInt_name(rst.getString(1));
				interest.setInt_type(rst.getString(2));
				interest.setInt_image(rst.getString(3));
				interestList.add(interest);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			dbManager.closeConnection();
		}
		return interestList;
	}

	@Override
	public int setPerInterest(int userid, ArrayList<String> listInfo) {
		String sql;
		List<Interests> interestList;
		ResultSet rst;
		System.out.println("dao 层"+userid);
		// 先删除原有的
		sql = "delete from users_interests where userid =?";
		int rowsdelete= dbManager.execUpdate(sql, userid);
		if(rowsdelete>=0){
			System.out.println("原数据添加成功");
		}
		
		// 后更新新的
		int sumrows=0; // 计算总共插入了几个
		for(int i=0;i<listInfo.size();i++){
			sql = "insert into users_interests values(?,?) ";
			int rowsinsert= dbManager.execUpdate(sql, userid,listInfo.get(i));
			if(rowsinsert==1){
				System.out.println("第"+(i+1)+"项增加成功");
				sumrows++;
			}
		}
		
		return sumrows;
	}
}
