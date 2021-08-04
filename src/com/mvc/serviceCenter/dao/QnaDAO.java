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

public class QnaDAO {

	public Connection conn = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;
	
	public QnaDAO() {
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

		String sql = "SELECT qnano,title,email,reg_date FROM"
				+ "(SELECT ROW_NUMBER() OVER(ORDER BY qnano DESC) AS rnum,"
				+ "qnano,title,email,reg_date FROM qna) WHERE rnum BETWEEN ? AND ?";
		
		ArrayList<QnaDTO> list = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		QnaDTO dto = null;
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
			list = new ArrayList<QnaDTO>();
			while(rs.next()) {
				dto = new QnaDTO();
				dto.setQnano(rs.getInt("qnano"));
				dto.setEmail(rs.getString("email"));
				dto.setTitle(rs.getString("title"));
				dto.setReg_date(rs.getDate("reg_date"));
			
				list.add(dto);
				
			}
			
			int total = totalCount(); // 총 게시글 수 가져옵시다
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


	public int write(String title, String email, String content) {
		String sql="INSERT INTO qna(qnano, title, email, content) "
				+"VALUES(qna_seq.NEXTVAL,?,?,?)";
		int success = 0;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, title);
			ps.setString(2, email);			
			ps.setString(3, content);
			success = ps.executeUpdate();			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		
		return success;
	}

	

	


	public ArrayList<QnaDTO> searchlist(String searchKey) {
		String sql="SELECT qnano,email,title,content,reg_date FROM qna";
		ArrayList<QnaDTO> searchlist = null;
		QnaDTO dto = null;
		
		try {
			ps= conn.prepareStatement(sql);
			ps.setString(1, "%"+searchKey+'%');
			ps.setString(2, "%"+searchKey+'%');
			rs= ps.executeQuery();
			searchlist = new ArrayList<QnaDTO>();
			while(rs.next()) {
				dto = new QnaDTO();
				dto.setQnano(rs.getInt("qnano"));
				dto.setEmail(rs.getString("email"));
				dto.setTitle(rs.getString("title"));
				dto.setContent(rs.getString("content"));
				dto.setReg_date(rs.getDate("reg_date"));
				searchlist.add(dto);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return searchlist;
	}


	
	private int totalCount() throws SQLException {
		String sql = "SELECT COUNT(qnano) FROM qna";
		ps = conn.prepareStatement(sql);
		rs = ps.executeQuery();
		int total = 0;
		if (rs.next()) {
			total = rs.getInt(1);
		}
		
		return total;
	}

	public QnaDTO detail(String qnano) {
		String sql = "SELECT * FROM qna WHERE qnano=?";
		QnaDTO dto = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, qnano);
			rs = ps.executeQuery();
			if(rs.next()) {
				dto = new QnaDTO();
				dto.setQnano(rs.getInt("qnano"));
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
	
	
	
	
}
