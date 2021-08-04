package com.mvc.member.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mvc.member.dto.MemberDTO;

public class MemberDAO {

	public Connection conn = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;
	String sql ="";

	public MemberDAO() {
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource)ctx.lookup("java:comp/env/jdbc/Oracle");
			conn = ds.getConnection();
			System.out.println(conn);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public MemberDTO login(String email, String pw) {
		String sql = "SELECT email, nickname, cancelmember, adminstate FROM member WHERE email=? AND password=?";
		MemberDTO dto = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, pw);
			rs = ps.executeQuery();
			if(rs.next()) {
				System.out.println(rs.getString("email")+"/"+rs.getString("nickname"));
				System.out.println(rs.getString("adminstate")+"관리자 여부 입력");
				dto = new MemberDTO();
				dto.setEmail(rs.getString("email"));
				dto.setNickname(rs.getString("nickname"));
				if(rs.getString("cancelmember")!=null) {
					dto.setCancelMember(rs.getString("cancelmember").charAt(0));
				}
				if(rs.getString("adminstate")!=null) {
					dto.setAdminState(rs.getString("adminstate").charAt(0));
				}
			}
			//System.out.println("db 실행");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return dto;
	}

	public void resClose() {
		try {
			if(rs != null &&!rs.isClosed()) {rs.close();}
			if(ps != null &&!ps.isClosed()) {ps.close();}
			if(conn != null && !conn.isClosed()) {conn.close();}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public int join(String email, String nickname, String pw, String name, String gender, String birth, String phone, int style) {
		sql = "INSERT INTO member(email, nickname, password, name, gender, birth, phone) VALUES(?,?,?,?,?,?,?)";
		int suc = 0;
		try {
			SimpleDateFormat fm = new SimpleDateFormat("yyyyMMdd");
			//Date date = (Date) fm.parse(birth);
			 java.util.Date utilDate = new java.util.Date();
			 java.sql.Date date = new java.sql.Date(fm.parse(birth).getTime());
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, nickname);
			ps.setString(3, pw);
			ps.setString(4, name);
			ps.setString(5, gender);
			ps.setDate(6, date);
			ps.setString(7, phone);
			ps.executeUpdate();
			
			sql ="INSERT INTO tourstyle VALUES(?,?)";
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ps.setInt(2, style);
			suc =ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			resClose();
		} return suc;
	}

	public boolean overlay(String email) throws SQLException {
		String sql = "SELECT email FROM member WHERE email = ?";
		ps = conn.prepareStatement(sql);
		ps.setString(1, email);
		rs = ps.executeQuery();
		return rs.next();
		//service에서 자원 반납
	}

	/*
	 * public MemberDTO detail(String email) { MemberDTO dto = null; String sql =
	 * "SELECT m.email, m.nickname,m.password,m.name,m.gender,m.birth, m.phone, t.categoryno "
	 * +
	 * " FROM member m LEFT OUTER JOIN tourstyle t ON m.email = t.email WHERE t.email = ?"
	 * ; try { ps = conn.prepareStatement(sql); ps.setString(1, email); rs=
	 * ps.executeQuery(); if(rs.next()) { dto = new MemberDTO();
	 * dto.setEmail(rs.getString("email"));
	 * dto.setNickname(rs.getString("nickname"));
	 * dto.setPw(rs.getString("password")); dto.setName(rs.getString("name"));
	 * dto.setGender(rs.getString("gender")); dto.setBirth(rs.getDate("birth"));
	 * dto.setPhone(rs.getString("phone"));
	 * //System.out.println(rs.getInt("categoryno")+"번 "); String style = ""; switch
	 * (rs.getInt("categoryno")) { case 1: style = "식도락"; break; case 2: style =
	 * "레포츠"; break; case 3: style = "문화"; break; case 4: style = "힐링"; break; case
	 * 5: style = "호캉스"; break; } dto.setTourstyle(style); } } catch (Exception e) {
	 * e.printStackTrace(); }finally { resClose(); } return dto; }
	 */
	public MemberDTO detail(String email) {
		MemberDTO dto = null;
		String sql = "SELECT m.email, m.nickname, m.password,m.name,m.gender,m.birth, m.phone, t.categoryno, p.oriname, p.newname FROM member m LEFT OUTER JOIN tourstyle t ON m.email = t.email LEFT OUTER JOIN profilephoto p ON m.email = p.email WHERE m.email = ?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			rs= ps.executeQuery();
			System.out.println("sql 실행");
			if(rs.next()) {
				dto = new MemberDTO();
				dto.setEmail(rs.getString("email"));
				dto.setNickname(rs.getString("nickname"));
				dto.setPw(rs.getString("password"));
				dto.setName(rs.getString("name"));
				dto.setGender(rs.getString("gender"));
				dto.setBirth(rs.getDate("birth"));
				dto.setPhone(rs.getString("phone"));
				//System.out.println(rs.getInt("categoryno")+"번 ");
				String style = "";
				switch (rs.getInt("categoryno")) {
				case 1:
					style = "식도락";
					break;
				case 2:
					style = "레포츠";
					break;
				case 3:
					style = "문화";
					break;
				case 4:
					style = "힐링";
					break;
				case 5:
					style = "호캉스";
					break;
				}
				dto.setTourstyle(style);
				dto.setOriName(rs.getString("oriname"));
				dto.setNewName(rs.getString("newname"));
				System.out.println(dto.getEmail()+" :이계정 가져오기");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return dto;
	}

	public boolean update(String email, String nickname, String pw, String name, String gender, String birth,
			String phone, String style) {
		boolean suc = false;
			String sql = "UPDATE member SET nickname = ? ,password = ? ,name = ? ,gender = ? ,birth = ? , phone = ?  WHERE email  = ?";
			try {
				ps = conn.prepareStatement(sql);
				SimpleDateFormat fm = new SimpleDateFormat("yyyyMMdd");
				//Date date = (Date) fm.parse(birth);
				 java.util.Date utilDate = new java.util.Date();
				 java.sql.Date date = new java.sql.Date(fm.parse(birth).getTime());
				ps.setString(1, nickname);
				ps.setString(2, pw);
				ps.setString(3, name);
				ps.setString(4, gender);
				ps.setDate(5, date);
				ps.setString(6, phone);
				ps.setString(7, email);
				ps.executeUpdate();
				
				sql = "UPDATE tourstyle SET categoryno = ? WHERE email = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, style);
				ps.setString(2, email);
				if(ps.executeUpdate()>0)
				{ suc = true;
				System.out.println(" DB수정 완료?"+suc );}
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				resClose();
			} return suc;
	}

	public boolean upload(MemberDTO dto) {
		boolean suc = false;
		String sql = "INSERT INTO profilephoto(email,oriname,newname) VALUES (?,?,?)";
		
		try {
			ps= conn.prepareStatement(sql);
			ps.setString(1, dto.getEmail());
			ps.setString(2, dto.getOriName());
			ps.setString(3, dto.getNewName());
			if(ps.executeUpdate()>0) {
				suc=true;
			};
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			resClose();
		} return suc;
		
		
	}

	public MemberDTO photo(String email) {
		MemberDTO dto = null;
		String sql = "SELECT  email, oriname, newname FROM profilephoto WHERE email=?";
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			rs = ps.executeQuery();
			if(rs.next()) {
				dto = new MemberDTO();
				dto.setEmail(rs.getString("email"));
				dto.setOriName(rs.getString("oriname"));
				dto.setNewName(rs.getString("newname"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return dto;
	}

	public MemberDTO getFileName(String email) {
		MemberDTO dto = null;
		String sql= "SELECT email, oriname, newname FROM profilephoto WHERE email=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			rs = ps.executeQuery();
			if(rs.next()) {
				dto = new MemberDTO();
				dto.setEmail(rs.getString("email"));
				dto.setOriName(rs.getString("oriname"));
				dto.setNewName(rs.getString("newname"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}

	public boolean updateName(String delFileName, MemberDTO dto) {
		boolean success=false;
		int suc = 0;
		String sql = "";		
		try {
			if(delFileName != null) {
				sql="UPDATE profilephoto SET newname=?, oriname=? WHERE email=?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, dto.getNewName());
				ps.setString(2, dto.getOriName());
				ps.setString(3, dto.getEmail());
				suc = ps.executeUpdate();
				System.out.println("update 완료"+ dto.getEmail());
			}else {//insert(기존 파일이 없을 경우)
				sql="INSERT INTO profilephoto(email, oriname, newname)"+
							" VALUES( ?,?,?)";
				ps = conn.prepareStatement(sql);
				ps.setString(3, dto.getNewName());
				ps.setString(2, dto.getOriName());
				ps.setString(1, dto.getEmail());
				suc = ps.executeUpdate();
				System.out.println("insert 완료"+ dto.getEmail());
			}
			if(suc>0) {
				success=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}return success;
	}

	public MemberDTO chk(String email, String pw) {
		sql = "SELECT password FROM member WHERE email =? AND password = ?";
		MemberDTO dto = new MemberDTO();
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			ps.setString(2, pw);
			rs = ps.executeQuery();
			if(rs.next()) {
			dto.setPw(rs.getString("password"));
			}
			/*if(rs.next()) {
				dto.setEmail(rs.getString("email"));
				dto.setPw(rs.getString("pw"));
				System.out.println("비밀번호 일치");
				sql = "UPDATE member SET cancelmember = 1 WHERE email = ?";
				ps = conn.prepareStatement(sql);
				ps.setString(1, dto.getEmail());
				ps.executeQuery();
			}*/
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			resClose();
		}
		return dto;
	}

	public boolean cancel(String email) {
		sql = "UPDATE member SET cancelmember = 1 WHERE email = ?"; //1은 회원 탈퇴로 하자
		boolean suc = false;	
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, email);
			if(ps.executeUpdate()>0) {
				suc = true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			resClose();
		}
		return suc;
	}
}
