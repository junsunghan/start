package com.mvc.serviceCenter.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mvc.board.dto.CommentDTO;
import com.mvc.board.dto.FootprintDTO;
import com.mvc.member.dto.MemberDTO;
import com.mvc.msg.dto.MsgDTO;
import com.mvc.serviceCenter.dto.ReportDTO;



public class ScDAO {

	public Connection conn = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;
	public String sql= null;
	public ScDAO() {
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle");
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void resClose() {
		try {
			if(rs!=null && !rs.isClosed()) {rs.close();}
			if(ps!=null && !ps.isClosed()) {ps.close();}
			if(conn!=null && !conn.isClosed()) {conn.close();}
			System.out.println("자원반납 완");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public int toatalCount() throws SQLException {
		String sql = "SELECT COUNT(email) FROM member";
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		int total = 0;
		if(rs.next()) {
			total = rs.getInt(1);
		}
		
		return total;
	}
	//신고 글 상세보기
	public ArrayList<FootprintDTO> contdetail() {
		sql = "SELECT * FROM footprint A INNER JOIN report1 B ON A.footprintno = B.contentno";
		ArrayList<FootprintDTO> list = null;
		FootprintDTO dto = null;
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			list = new ArrayList<FootprintDTO>();
			while(rs.next()) {
				dto = new FootprintDTO();
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	//신고 댓글 상세보기
	public ArrayList<CommentDTO> commdetail() {
		sql = "SELECT * FROM comment1 A INNER JOIN report1 B ON A.commentno = B.commentno";
		ArrayList<CommentDTO> list = null;
		CommentDTO dto = null;
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			list = new ArrayList<CommentDTO>();
			while(rs.next()) {
				dto = new CommentDTO();
				
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	//신고 메세지 상세보기
	public ArrayList<MsgDTO> messdetail() {
		sql = "SELECT * FROM message A INNER JOIN report1 B ON A.msgno = B.msgno";
		ArrayList<MsgDTO> list = null;
		MsgDTO dto = null;
		
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			list = new ArrayList<MsgDTO>();
			while(rs.next()) {
				dto = new MsgDTO();
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	//신고 글 리스트 불러오기
	public ArrayList<ReportDTO> contlist() {
		sql = "SELECT reportNo,rportText,email,reportDate,state FROM" + 
				"report1 WHERE contentno is not null;";
		ArrayList<ReportDTO> list = null;
		ReportDTO dto = null;
		
		try {
			ps=conn.prepareStatement(sql);
			rs = ps.executeQuery();
			list = new ArrayList<ReportDTO>();
			while(rs.next()) {
				dto = new ReportDTO();
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	//신고 댓글 리스트 보기
	public ArrayList<ReportDTO> commlist() {
		sql = "SELECT reportNo,rportText,email,reportDate,state FROM" + 
				"report1 WHERE commentno is not null;";
		ArrayList<ReportDTO> list = null;
		ReportDTO dto = null;
		
		try {
			ps=conn.prepareStatement(sql);
			rs = ps.executeQuery();
			list = new ArrayList<ReportDTO>();
			while(rs.next()) {
				dto = new ReportDTO();
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	//신고 메세지 리스트 보기
	public ArrayList<ReportDTO> messlist() {
		sql = "SELECT reportNo,rportText,email,reportDate,state FROM" + 
				"report1 WHERE msgno is not null;";
		ArrayList<ReportDTO> list = null;
		ReportDTO dto = null;
		
		try {
			ps=conn.prepareStatement(sql);
			rs = ps.executeQuery();
			list = new ArrayList<ReportDTO>();
			while(rs.next()) {
				dto = new ReportDTO();
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public ArrayList<MemberDTO> blacklist() {
		//sql = "SELECT email,nickname FROM member WHERE blacklist is not null";
		sql = "SELECT email,name FROM member WHERE email is not null";
		ArrayList<MemberDTO> list = null;
		MemberDTO dto = null;
		
		try {
			ps=conn.prepareStatement(sql);
			rs = ps.executeQuery();
			list = new ArrayList<MemberDTO>();
			while(rs.next()) {
				dto = new MemberDTO();
				dto.setEmail(rs.getString("email"));
				dto.setName(rs.getString("name"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}

	public MemberDTO detail(String email) {

		MemberDTO dto = null;
		
		sql = "SELECT email,nickname FROM member WHERE email = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			rs = ps.executeQuery();
			if(rs.next()) {
				dto = new MemberDTO();
				dto.setEmail(rs.getString("email"));
				dto.setNickname(rs.getString("nickname"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}

	public ArrayList<MemberDTO> memberlist() {
		//sql = "SELECT email, nickname FROM member";
		sql = "SELECT email, name FROM member";
		ArrayList<MemberDTO> list = null;
		MemberDTO dto = null;
		try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			list = new ArrayList<MemberDTO>();
			while(rs.next()) {
				dto = new MemberDTO();
				dto.setEmail(rs.getString("email"));
				//dto.setNickname(rs.getString("nickname"));
				dto.setName(rs.getString("name"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<MemberDTO> membersearch(String email) {
		sql = "SELECT email, name FROM member WHERE email=?";
		ArrayList<MemberDTO> list = null;
		MemberDTO dto = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			rs = ps.executeQuery();
			list = new ArrayList<MemberDTO>();
			if(rs.next()) {
				dto = new MemberDTO();
				dto.setEmail(rs.getString("email"));
				dto.setName(rs.getString("name"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public HashMap<String, Object> stoplist(int page) {
		//sql = "SELECT email, nickname FROM member WHERE accountBan = 1";
		int pagePerCnt = 5;
		int end = page*pagePerCnt;
		int start = (end-pagePerCnt)+1;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		//sql = "SELECT email, name FROM member";
		sql = "SELECT email, name FROM"+
		 "(SELECT ROW_NUMBER() OVER(ORDER BY email DESC) AS rnum," + 
		 "email, name FROM member) WHERE rnum BETWEEN ? AND ?";
		ArrayList<MemberDTO> list = null;
		MemberDTO dto = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, end);
			rs = ps.executeQuery();
			list = new ArrayList<MemberDTO>();
			while(rs.next()) {
				dto = new MemberDTO();
				dto.setEmail(rs.getString("email"));
				//dto.setNickname(rs.getString("nickname"));
				dto.setName(rs.getString("name"));
				list.add(dto);
			}
			int total = toatalCount(); // 총 게시글 수
			int pages = total/pagePerCnt;// 만들 수 있는 페이지 숫자
			System.out.println("총 게시글 수 : "+total+"/ 페이지 수 : "+pages);
			
			map.put("list", list);
			map.put("totalPage", pages);
			map.put("currPage", page);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
			}

	public ArrayList<MemberDTO> stopmembersearch(String email) {
		//sql = "SELECT email, name FROM member WHERE email=? AND accountBan = 1";
		sql = "SELECT email, name FROM member WHERE email=?";
		ArrayList<MemberDTO> list = null;
		MemberDTO dto = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			rs = ps.executeQuery();
			list = new ArrayList<MemberDTO>();
			if(rs.next()) {
				dto = new MemberDTO();
				dto.setEmail(rs.getString("email"));
				dto.setName(rs.getString("name"));
				list.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public MemberDTO memberdetail(String email) {
		MemberDTO dto = null;
		//sql = "SELECT email,nickname FROM member WHERE email = ?";
		sql = "SELECT * FROM member WHERE email=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			rs = ps.executeQuery();
			if(rs.next()) {
				dto = new MemberDTO();
				dto.setEmail(rs.getString("email"));
				dto.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(dto);
		return dto;
	}

	public MemberDTO stopwriteform(String email) {
		MemberDTO dto = null;
		//sql = "SELECT email,nickname FROM member WHERE email = ?";
		sql = "SELECT * FROM member WHERE email=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			rs = ps.executeQuery();
			if(rs.next()) {
				dto = new MemberDTO();
				dto.setEmail(rs.getString("email"));
				dto.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(dto);
		return dto;
	}

	public MemberDTO blackwriteform(String email) {
		MemberDTO dto = null;
		//sql = "SELECT email,nickname FROM member WHERE email = ?";
		sql = "SELECT * FROM member WHERE email=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			rs = ps.executeQuery();
			if(rs.next()) {
				dto = new MemberDTO();
				dto.setEmail(rs.getString("email"));
				dto.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println(dto);
		return dto;
	}

	public int blackregister(String email, String reason) {
		int success = 0;
		//sql = "UPDATE "
		sql = "UPDATE member SET name=? WHERE email= ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, reason);
			ps.setString(2, email);
			success = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	public int stopregister(String email, String reason) {
		int success = 0;
		//sql = "UPDATE "
		sql = "UPDATE member SET name=? WHERE email= ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, reason);
			ps.setString(2, email);
			success = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	public int stopremove(String email) {
		int success = 0;
		sql = "UPDATE member SET name=? WHERE email=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, "정지");
			ps.setString(2, email);
			success = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	public HashMap<String, Object> withdrawlist(int page) {
		System.out.println("page: "+page);
		int pagePerCnt = 5;
		int end = page*pagePerCnt;
		int start = (end-pagePerCnt)+1;
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		//sql = "SELECT email, name FROM member";
		sql = "SELECT email, name FROM"+
		 "(SELECT ROW_NUMBER() OVER(ORDER BY email DESC) AS rnum," + 
		 "email, name FROM member) WHERE rnum BETWEEN ? AND ?";
		ArrayList<MemberDTO> list = null;
		MemberDTO dto = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, end);
			rs = ps.executeQuery();
			list = new ArrayList<MemberDTO>();
			while(rs.next()) {
				dto = new MemberDTO();
				dto.setEmail(rs.getString("email"));
				//dto.setNickname(rs.getString("nickname"));
				dto.setName(rs.getString("name"));
				list.add(dto);
			}
			int total = toatalCount(); // 총 게시글 수
			int pages = total/pagePerCnt;// 만들 수 있는 페이지 숫자
			System.out.println("총 게시글 수 : "+total+"/ 페이지 수 : "+pages);
			
			map.put("list", list);
			map.put("totalPage", pages);
			map.put("currPage", page);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return map;
	}

}

