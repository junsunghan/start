package com.mvc.board.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mvc.board.dto.FootprintDTO;
import com.sun.org.apache.xml.internal.serializer.utils.StringToIntTable;

public class BoardDAO {

	public Connection conn = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;
	
	
	public BoardDAO() {
		Context ctx;
		try {
			ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/Oracle");
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void resClose() {
		try {
			if(rs != null && !rs.isClosed()) {rs.close();}
			if(ps != null && !ps.isClosed()) {ps.close();}
			if(conn != null && !conn.isClosed()) {conn.close();}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	public ArrayList<FootprintDTO> fplist(String email) {
		String sql="SELECT fnum, footPrintNO, markerNO, email, reg_date, footprintText FROM (SELECT ROW_NUMBER() OVER (ORDER BY footprintNO DESC) AS fnum, footPrintNO, markerNO, email, reg_date, footprintText,postblind FROM footprint WHERE email=? AND postblind IS NULL OR postblind=0) ";
	    ArrayList<FootprintDTO>fplist = null;
	    FootprintDTO dto = null;
	    
	    try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			rs = ps.executeQuery();
			fplist = new ArrayList<FootprintDTO>();
			while(rs.next()) {
				dto = new FootprintDTO();
				dto.setBoardNO(rs.getInt("fnum"));
				dto.setFootPrintNO(rs.getInt("footPrintNO"));
				dto.setMarkerNO(rs.getInt("markerNO"));
				dto.setEmail(rs.getString("email"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setFootprintText(rs.getString("footprintText"));
				fplist.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return fplist;
	}
	//피드 리스트
	public ArrayList<FootprintDTO> feedlist() {
		String sql="SELECT fnum, footPrintNO, markerNO, email, reg_date, footprintText FROM (SELECT ROW_NUMBER() OVER (ORDER BY footprintNO DESC) AS fnum, footPrintNO, markerNO, email, reg_date, footprintText,postblind FROM footprint WHERE release = 1 AND postblind IS NULL OR postblind=0) ";
	    ArrayList<FootprintDTO> feedlist = null;
	    FootprintDTO dto = null;
	    try {
			ps = conn.prepareStatement(sql);
			rs = ps.executeQuery();
			feedlist = new ArrayList<FootprintDTO>();
			while(rs.next()) {
				dto = new FootprintDTO();
				dto.setBoardNO(rs.getInt("fnum"));
				dto.setFootPrintNO(rs.getInt("footPrintNO"));
				dto.setMarkerNO(rs.getInt("markerNO"));
				dto.setEmail(rs.getString("email"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setFootprintText(rs.getString("footprintText"));
				feedlist.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return feedlist;
	}

	//공개 글쓰기
	public int fpwriteOk(FootprintDTO dto, String email) {
		String sql ="INSERT INTO footprint(footPrintNO, footprintText,release, email) "
				         +"VALUES(footprint_seq.NEXTVAL,?,?,?)";
		int pk =0;
		try {
			ps = conn.prepareStatement(sql, new String[] {"footPrintNO"});
			ps.setString(1, dto.getFootprintText());
			ps.setString(2, String.valueOf(dto.getRelease()));// 공개가 1!!!
			ps.setString(3, email);
			ps.executeUpdate();
			rs = ps.getGeneratedKeys();
			if(rs.next()) {
				//pk = Integer.parseInt(rs.getString("footPrintNO"));
				sql ="INSERT INTO PostPic(footPrintNO, oriFileName, newFileName)"
						 +" VALUES(?,?,?)";
				ps = conn.prepareStatement(sql);
				pk = rs.getInt(1);
				ps.setInt(1, pk);
				ps.setString(2, dto.getOriFileName());
				ps.setString(3, dto.getNewFileName());
				System.out.println(dto.getOriFileName()+"/"+dto.getNewFileName());
				
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return pk;
	}

	/*
	 * //비공개 글쓰기 public int fpwriteNo(FootprintDTO dto) { String sql
	 * ="INSERT INTO footprint(footPrintNO, footprintText,release) "
	 * +"VALUES(footprint_seq.NEXTVAL,?,0)";
	 * 
	 * int pk =0;
	 * 
	 * 
	 * try { ps = conn.prepareStatement(sql, new String[] {"footPrintNO"});
	 * ps.setString(1, dto.getFootprintText());
	 * 
	 * ps.executeUpdate(); rs = ps.getGeneratedKeys(); if(rs.next()) { sql
	 * ="INSERT INTO PostPic(footPrintNO, oriFileName, newFileName)"
	 * +"VALUES(PostPic_seq.NEXTVAL,?,?)"; ps = conn.prepareStatement(sql); pk =
	 * rs.getInt(1); ps.setInt(1, pk); ps.setString(1, dto.getOriFileName());
	 * ps.setString(2, dto.getNewFileName()); ps.executeUpdate(); } } catch
	 * (SQLException e) { e.printStackTrace(); } return pk; }
	 */


	public FootprintDTO fpdetail(String footPrintNO) {
		FootprintDTO dto = null;
		
		/*
		 * String sql =
		 * " SELECT f.footPrintNO ,f.markerNO, f.email, f.footprintText, f.reg_date, P.oriFileName, P.newFileName "
		 * +
		 * "FROM footprint f LEFT OUTER JOIN PostPic P ON f.footPrintNO = P.footPrintNO WHERE f.footPrintNO = ?"
		 * ;
		 */
		String sql = "SELECT f.footPrintNO ,f.markerNO, f.email, f.footprintText, f.reg_date, P.oriFileName, P.newFileName, f.release FROM footprint f LEFT OUTER JOIN PostPic P ON f.footPrintNO = P.footPrintNO WHERE f.footPrintNO = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, footPrintNO);
			rs = ps.executeQuery();
			if(rs.next()) {
				dto = new FootprintDTO();
				dto.setFootPrintNO(rs.getInt("footPrintNO"));
				dto.setMarkerNO(rs.getInt("markerNO"));
				dto.setEmail(rs.getString("email"));
				dto.setFootprintText(rs.getString("footprintText"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setOriFileName(rs.getString("oriFileName"));
				dto.setNewFileName(rs.getString("newFileName"));
				dto.setRelease(rs.getString("release").charAt(0));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}

	public int fpdel(String footPrintNO) {
		int success = 0;
		String sql ="UPDATE footprint SET postblind = 1 WHERE footPrintNO = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, footPrintNO);
			success = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	/*
	 * public int fpupdate(String footPrintNO, String footprintText) {
	 * 
	 * int success =0; String sql
	 * ="UPDATE footprint SET footprintText =? WHERE footPrintNO=?";
	 * 
	 * try { ps = conn.prepareStatement(sql); ps.setString(1, footprintText);
	 * ps.setString(2, footPrintNO); success = ps.executeUpdate(); } catch
	 * (SQLException e) { e.printStackTrace(); } return success; }
	 */

	public int fpupdate(FootprintDTO dto) {
		int success = 0;
		String sql = "UPDATE footprint SET footprintText =?, release = ? WHERE footPrintNO=?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, dto.getFootprintText());
			ps.setString(2, toString().valueOf(dto.getRelease()));
			ps.setInt(3, dto.getFootPrintNO());
			success = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	public FootprintDTO getFileName(String footPrintNO) {
		
		FootprintDTO dto = null;
		String sql ="SELECT oriFileName, newFileName FROM PostPic WHERE footPrintNO=?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, footPrintNO);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				dto = new FootprintDTO();
				dto.setOriFileName(rs.getString("oriFileName"));
				dto.setNewFileName(rs.getString("newFileName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return dto;
	}



	public FootprintDTO getFileName1(String footPrintNO) {
		FootprintDTO dto = null;
		String sql ="SELECT oriFileName, newFileName  FROM Postpic WHERE footPrintNO=?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, footPrintNO);
			rs=ps.executeQuery();
			
			if(rs.next()) {
				dto = new FootprintDTO();
				dto.setOriFileName(rs.getString("oriFileName"));
				dto.setNewFileName(rs.getString("newFileName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		return dto;
	}

	public void fpupdateFileName(String delFileName, FootprintDTO dto) {
		
		String sql ="";
		
		try {
			if(delFileName != null) {
			sql ="UPDATE PostPic SET newFileName =?, oriFileName =? WHERE footPrintNO=?";	
			ps = conn.prepareStatement(sql);
		    ps.setString(1, dto.getNewFileName());
			ps.setString(2, dto.getNewFileName());
			ps.setInt(3, dto.getFootPrintNO());
			ps.executeQuery();
			}else {
				sql="INSERT INTO PostPic(footPrintNO, oriFileName, newFileName)"
						 +"VALUES(PostPic_seq.NEXTVAL,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setString(1, dto.getNewFileName());
				ps.setString(2, dto.getOriFileName());
				ps.executeQuery();
			}
			
			
			
			} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public ArrayList<FootprintDTO> hashtaglist(String hashtag) {
		  String sql = "SELECT f.footPrintNO, f.footprintText, p.hashtag"
				  +" FROM footprint f LEFT OUTER JOIN post_tag p ON f.footprintno = p.footprintno"
				  +" WHERE f.footprintno IN (select footprintno from post_tag WHERE hashtag LIKE ?)";
				  
				  //"SELECT f.footPrintNO, f.footprintText, p.hashtag "
		  		//+ "FROM footprint f LEFT OUTER JOIN post_tag p ON f.footprintno = p.footprintno "
		  		//+ "WHERE f.footprintno IN (select footprintno from post_tag)";
		  
		  
		  ArrayList<FootprintDTO> hashtaglist = null;
		  FootprintDTO dto = null;
		  
		  try {
			ps = conn.prepareStatement(sql);
			
		    ps.setString(1, hashtag+'%');
			rs = ps.executeQuery();
			hashtaglist = new ArrayList<FootprintDTO>();
			while(rs.next()){
				dto = new FootprintDTO();
				dto.setFootPrintNO(rs.getInt("footPrintNO"));
				dto.setFootprintText(rs.getString("footprintText"));
				dto.setHashTag(rs.getString("hashtag"));
				hashtaglist.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
			return hashtaglist;
		}


	
	
	
}













