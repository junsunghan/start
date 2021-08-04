package com.mvc.friends.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mvc.friends.dto.FriendsDTO;
import com.mvc.friends.dto.TourStyleDTO;

public class FriendsDAO {

	public Connection conn = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;
	
	public FriendsDAO() {
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle");
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	} //생성자 end

	
	public boolean friendsAddOverlay(String loginemail, String friends_email) {
		boolean overlay = false;
		String sql = "SELECT email,friends_email,block FROM friends "+
							"WHERE email = ? AND friends_email = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginemail);
			ps.setString(2, friends_email);
			rs = ps.executeQuery();
			if(rs.next()) {
				System.out.println("이미 친구등록이 된 이메일!");
				overlay = true;
			} else {
				System.out.println("친구등록 가능한 이메일!");
				overlay = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return overlay;
	}
	
	public int friendsAdd(String loginemail, String friends_email) {
		int success = 0;
		String sql = "INSERT INTO friends(email,friends_email,block) "
							+ "VALUES(?,?,0)";
		//block 컬럼에 0 넣는건, 차단하지 않았다는 뜻임! 1이 차단! 생성할땐 무조건 안차단!
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginemail);
			ps.setString(2, friends_email);
			success = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return success;
	}

	
	public int friendsDel(String loginemail, String friends_email) {
		int success = 0;
		String sql = "DELETE FROM friends WHERE email = ? AND friends_email = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginemail);
			ps.setString(2, friends_email);
			success = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}
	
	public ArrayList<FriendsDTO> friendsList(String loginemail) {
		String sql ="SELECT email, friends_email, block FROM friends WHERE email = ?";
		ArrayList<FriendsDTO> friendsList = null;
		FriendsDTO dto = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginemail);
			rs = ps.executeQuery();
			friendsList = new ArrayList<FriendsDTO>();
			while (rs.next()) {
				dto = new FriendsDTO();
				dto.setEmail(rs.getString("email"));
				dto.setFriends_email(rs.getString("friends_email"));
				dto.setBlock(rs.getInt("block"));
				friendsList.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return friendsList;
	}
	
	public int friendsBlock(String loginemail, String friends_email) {
		int success = 0;
		String sql = "UPDATE friends SET block = 1 "+
							"WHERE email = ? AND friends_email = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginemail);
			ps.setString(2, friends_email);
			success = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}
	
	
	public int friendsBlockCancle(String loginemail, String friends_email) {
		int success = 0;
		String sql = "UPDATE friends SET block = 0 "+
							"WHERE email = ? AND friends_email = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginemail);
			ps.setString(2, friends_email);
			success = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}
		
	
	
	
	
	public void resClose() { //자원반납
		try {
			if (rs !=null && !rs.isClosed()) {
				rs.close();
			}
			if (ps !=null && !ps.isClosed()) {
				ps.close();
			}
			if (conn !=null && !conn.isClosed()) {
				conn.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		} 
	} //resClose end


	public ArrayList<TourStyleDTO> friendsRecomend(String loginemail) {
		/*
		String sql = "SELECT email FROM "
				+ "(SELECT ROW_NUMBER() OVER(ORDER BY email DESC) AS rnum, email, categoryNo FROM TourStyle WHERE categoryNo = (SELECT categoryNo FROM TourStyle WHERE email = ?)) "
				+ " WHERE rnum BETWEEN 1 AND 5";
		String sql = "SELECT email FROM "
				+"(SELECT ROW_NUMBER() OVER(ORDER BY email DESC) AS rnum, categoryNo, email FROM TourStyle WHERE email NOT IN (SELECT friends_email FROM friends WHERE email = 'test@test') AND categoryNo = (SELECT categoryNo FROM TourStyle WHERE email = 'test@test')) "
				+"WHERE rnum BETWEEN 1 AND 5";
		*/
		
		String sql = "SELECT email FROM "
				+"(SELECT ROW_NUMBER() OVER(ORDER BY DBMS_RANDOM.RANDOM()) AS rnum, categoryNo, email FROM TourStyle WHERE email NOT IN (SELECT friends_email FROM friends WHERE email = ?) AND categoryNo = (SELECT categoryNo FROM TourStyle WHERE email = ?) AND email != ?) "
				+"WHERE rnum BETWEEN 1 AND 5";
		//ORDER BY DBMS_RANDOM.RANDOM() => 랜덤으로 정렬!
		
		TourStyleDTO dto = null;
		ArrayList<TourStyleDTO> recomendList = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginemail);
			ps.setString(2, loginemail);
			ps.setString(3, loginemail);
			rs = ps.executeQuery();
			recomendList = new ArrayList<TourStyleDTO>();
			while(rs.next()){
				dto = new TourStyleDTO();
				dto.setEmail(rs.getString("email"));
				recomendList.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return recomendList;
	}





	


	






	
} // class end
