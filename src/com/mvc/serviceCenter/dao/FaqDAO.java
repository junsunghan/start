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


import com.mvc.serviceCenter.dto.FaqDTO;
import com.mvc.serviceCenter.dto.QnaDTO;

public class FaqDAO {
	public Connection conn = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;
	
	public FaqDAO() {
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle");
			conn = ds.getConnection();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void resClose() {
		try {
			if(rs != null && !rs.isClosed()) {rs.close();}
			if(ps != null && !ps.isClosed()) {ps.close();}
			if(conn != null && !conn.isClosed()) {conn.close();}
			System.out.println("자원반납");
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}


	public HashMap<String, Object> list(int page) {
		
		String sql = "SELECT idx,title,email,reg_date,categoryno FROM"
				+ "(SELECT ROW_NUMBER() OVER(ORDER BY idx DESC) AS rnum,"
				+ "idx,title,email,reg_date,categoryno FROM noticefaq WHERE categoryno=89) WHERE rnum BETWEEN ? AND ?";
		
		ArrayList<FaqDTO> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		FaqDTO dto = null;
		// 한블럭당 페이지 갯수
		int pageLength = 5;
		// 블럭 인덱스
		int currentBlock = page % pageLength == 0 ? page / pageLength : (page / pageLength) + 1;
		// 시작페이지
		int startPage = (currentBlock - 1) * pageLength + 1;
		// 끝페이지
		int endPage = startPage + pageLength - 1;
		System.out.println("시작 페이지 : " + startPage + " / 끝 페이지 : " + endPage);
		// 노출할 데이터 갯수
		int pagePerCnt = 10;
		int end = page * pagePerCnt;
		int start = (end - pagePerCnt) + 1;
		
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, end);
		
			rs = ps.executeQuery();
			list = new ArrayList<FaqDTO>();
			while(rs.next()) {
				dto = new FaqDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setEmail(rs.getString("email"));
				dto.setTitle(rs.getString("title"));
				dto.setReg_date(rs.getDate("reg_date"));
			
				list.add(dto);
				
			}
			
			int total = totalCount(sql); // 총 게시글 수 가져옵시다
			System.out.println("total:" + total);
			// 총 게시글 수에 나올 페이지수 나눠서 짝수면 나눠주고 홀수면 +1
			int totalPages = total % pagePerCnt == 0 ? total / pagePerCnt : (total / pagePerCnt) + 1;
			if (totalPages == 0) {
				totalPages = 1;
			}
			// 끝지점을 맨 마지막 페이지로 지정
			if (endPage > totalPages) {
				endPage = totalPages;
			}
			System.out.println("총 데이터수 : " + total);
			System.out.println("토탈 페이지 : " + totalPages);
			System.out.println();
			int pages = total / pagePerCnt; // 만들 수 있는 페이지 숫자
			System.out.println("만들 수 있는 페이지 수 : " + pages);
			map.put("list", list);
			map.put("totalPage", totalPages);
			map.put("currPage", page);
			map.put("pageLength", pageLength);
			map.put("startPage", startPage);
			map.put("endPage", endPage);
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return map;
	}


	private int totalCount(String searchKey) throws SQLException {
		String sql = "SELECT COUNT(idx) FROM noticefaq WHERE categoryno=89";
		ps = conn.prepareStatement(sql);	
		rs = ps.executeQuery();
		int total = 0;
		if (rs.next()) {
			total = rs.getInt(1);
		}

		return total;
	}


	public int write(String title, String email, String content, String categoryNo) {
		String sql="INSERT INTO noticefaq(idx,categoryNo, title, email, content) "
				+"VALUES(noticefaq_seq.NEXTVAL,?,?,?,?)";
		int success = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, Integer.parseInt(categoryNo));
			ps.setString(2, title);
			ps.setString(3, email);			
			ps.setString(4, content);
			success = ps.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return success;
	}


	public FaqDTO detail(String idx) {
		String sql = "SELECT*FROM noticefaq WHERE idx=?";
		FaqDTO dto = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, idx);
			rs = ps.executeQuery();
			if(rs.next()) {
				dto = new FaqDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setEmail(rs.getString("email"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setReg_date(rs.getDate("reg_date"));
			}
			
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
		
		
		return dto;
	}


	public int del(String idx) {
		String sql="DELETE FROM noticefaq WHERE idx=?";
		int success =0;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, idx);
			success = ps.executeUpdate();
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		
		return success;
	}


	public FaqDTO faqdetail(String idx) {
		FaqDTO dto = null;
		String sql = "SELECT*FROM noticefaq WHERE idx =?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, idx);
			rs = ps.executeQuery();
			if(rs.next()) {
				dto = new FaqDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setEmail(rs.getString("email"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setReg_date(rs.getDate("reg_date"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return dto;
	}


	public int faqupdate(String idx, String title, String content, String email) {
		int success = 0;
		String sql = "UPDATE noticefaq SET title=?, content=?, email=? WHERE idx=?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, title);
			ps.setString(2, content);
			ps.setString(3, email);
			ps.setString(4, idx);
			success = ps.executeUpdate();
		}catch (SQLException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	
		
		
		return success;
	}


	public HashMap<String, Object> searchlist(int page, String searchKey) {
		String sql="SELECT idx,email,categoryno,title,content,reg_date FROM (SELECT ROW_NUMBER() OVER(ORDER BY idx DESC) AS rnum,\r\n" + 
				"idx,email,categoryno,title,content,reg_date FROM noticefaq WHERE (title LIKE ? OR email LIKE ?) AND categoryno=90) WHERE rnum BETWEEN ? AND ?";
		ArrayList<FaqDTO> searchlist = null;
		HashMap<String, Object> srmap = new HashMap<String, Object>();
		FaqDTO dto = null;
		


		// 한블럭당 페이지 갯수
				int pageLength = 5;
				// 블럭 인덱스
				int currentBlock = page % pageLength == 0 ? page / pageLength : (page / pageLength) + 1;
				// 시작페이지
				int startPage = (currentBlock - 1) * pageLength + 1;
				// 끝페이지
				int endPage = startPage + pageLength - 1;
				System.out.println("시작 페이지 : " + startPage + " / 끝 페이지 : " + endPage);
				// 노출할 데이터 갯수
				int pagePerCnt = 10;
				int end = page * pagePerCnt;
				int start = (end - pagePerCnt) + 1;		
		
		
		try {
			ps= conn.prepareStatement(sql);
			ps.setString(1, "%"+searchKey+'%');
			ps.setString(2, "%"+searchKey+'%');
			ps.setInt(3, start);
			ps.setInt(4, end);
			rs= ps.executeQuery();
			searchlist = new ArrayList<FaqDTO>();
			while(rs.next()) {
				dto = new FaqDTO();
				dto.setIdx(rs.getInt("idx"));
				dto.setEmail(rs.getString("email"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setReg_date(rs.getDate("reg_date"));
				searchlist.add(dto);
			}
			
			int total = totalCount(searchKey);
			// 총 게시글 수에 나올 페이지수 나눠서 짝수면 나눠주고 홀수면 +1
			int totalPages = total % pagePerCnt == 0 ? total / pagePerCnt : (total / pagePerCnt) + 1;
			if (totalPages == 0) {
				totalPages = 1;
			}
			// 끝지점을 맨 마지막 페이지로 지정
			if (endPage > totalPages) {
				endPage = totalPages;
			}
			System.out.println("총 데이터수 : " + total);
			System.out.println("토탈 페이지 : " + totalPages);
			System.out.println();
			System.out.println("검색어 남아 있니? : "+searchKey);
			//int pages = total / pagePerCnt; // 만들 수 있는 페이지 숫자
			
			srmap.put("searchlist", searchlist);
			srmap.put("totalPage", totalPages);
			srmap.put("currPage", page);
			srmap.put("pageLength", pageLength);
			srmap.put("startPage", startPage);
			srmap.put("endPage", endPage);	
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return srmap;
	}





	
	
	
	
}
