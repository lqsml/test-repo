package pack_Member;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import pack_DBCP.DBConnectionMgr;

public class MemberMgr {
	
	Connection objConn = null;
	Statement objStmt = null;
	PreparedStatement objPstmt = null;
	ResultSet objRS = null;
	DBConnectionMgr objPool = null;
	
	
	// 아이디 중복 검사
	public boolean checkID(String id){
		boolean chkRes = false;	// 거짓이면 사용 가능
		
		try {
			
			objPool = DBConnectionMgr.getInstance();
			objConn = objPool.getConnection();
			
			String sql = "select count(*) from UserInfo where uId = ?";
			objPstmt = objConn.prepareStatement(sql);
			objPstmt.setString(1, id);
			objRS = objPstmt.executeQuery();
			
			if(objRS.next()) {
				int cnt = objRS.getInt(1);
				if(cnt == 1) chkRes = true;   
			} 
			
		} catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
		} finally {
			objPool.freeConnection(objConn, objPstmt, objRS);
		}
		
		return chkRes;
	}
	
}
