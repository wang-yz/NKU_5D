package com.nkcs.dbutil;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBManager {

	private Connection conn;
	private PreparedStatement pstmt;
	
	//加载驱动类
	public DBManager(){
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	//打开数据库连接
	private void openConnection(){
		
		try {
			this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/friends", "root", "123456"); // 3306是MySQL
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	//数据更新  
	public int execUpdate(String sql, Object... params){
		
		this.openConnection();
		
		try {
			//创建预处理语句对象
			this.pstmt = this.conn.prepareStatement(sql);
			
			//参数赋值
			for(int i=0; i< params.length; i++){
				this.pstmt.setObject(i+1, params[i]);
			}
			
			//更新数据
			return this.pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			return -1;
		} finally{
			this.closeConnection();
		}
		
	}
	
	//数据查询
	public ResultSet execQuery(String sql, Object... params){
		
		this.openConnection();
		
		try {
			//创建预处理语句对象
			this.pstmt = this.conn.prepareStatement(sql);
			
			//参数赋值
			for(int i=0; i< params.length; i++){
				this.pstmt.setObject(i+1, params[i]);
			}
			
			//查询数据
			return this.pstmt.executeQuery();
			
		} catch (SQLException e) {
			e.printStackTrace();
			
			return null;
		}
		
	}
	
	//关闭数据库连接
	public void closeConnection(){
		
		try {
			if(this.pstmt!=null){
				this.pstmt.close(); 
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try {
			if(this.conn!=null){
				this.conn.close(); 
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		
		DBManager dbManager = new DBManager();
		
		String sql = "select * from user";
		
		ResultSet rs = dbManager.execQuery(sql);
		
		try {
			while(rs.next()){
				System.out.println(rs.getInt(1) + "-" + rs.getString(2) + "-" + rs.getString(3) + "-" + rs.getInt(4) );
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			dbManager.closeConnection();
		}
	}

}
