package com.mvc.msg.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.mvc.msg.dto.MsgDTO;

public class MsgDAO {

	public Connection conn = null;
	public PreparedStatement ps = null;
	public ResultSet rs = null;
	
	public MsgDAO() { // 생성자
		try {
			Context ctx = new InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/Oracle");
			conn = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//페이징처리용 토탈카운트 
	private int totalCount(String loginemail) throws SQLException {
		String sql = "SELECT COUNT(msgNo) FROM message WHERE sender_email = ? ";
		ps = conn.prepareStatement(sql);
		ps.setString(1, loginemail);
		rs = ps.executeQuery();
		int total = 0;
		if(rs.next()) {
			total = rs.getInt(1);
		}	
		return total;
	}
	
	public int write(String sender, String reciever, String content) {
		//msgOpen 은 열람 여부 인데, 처음보낼때 0이고, 읽으면 1.
		//작성일은 Default Sysdate 
		int success = 0;
		String sql = "INSERT INTO message(msgNo,sender_email,receiver_email,msgContent,msgOpen) "
							+ "VALUES(msgNo_seq.NEXTVAL,?,?,?,0)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, sender);
			ps.setString(2, reciever);
			ps.setString(3, content);
			success = ps.executeUpdate();
			if(success>0) {
				System.out.println("메시지 DB 삽입 성공! 삽입개수: "+success);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}
	
	//나한테 보낸사람 이메일, 내용, 받은날짜
	//리시버 이메일은 현재 로그인을 한 사람의 이메일 이여야 함!
	public HashMap<String, Object> msgList(int page, String loginemail) {
		String sql = "SELECT msgNo, sender_email, msgContent, msgOpen, reg_date FROM "
							+ "(SELECT ROW_NUMBER() OVER(ORDER BY msgNo DESC)AS rnum, msgNo, sender_email, msgContent, msgOpen, reg_date FROM message WHERE receiver_email=?) "
							+ "WHERE rnum BETWEEN ? AND ? ";
		
		ArrayList<MsgDTO> msgList = null;
		HashMap<String, Object> msgMap = new HashMap<String, Object>();
		MsgDTO dto = null;
		
		
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
			ps.setString(1, loginemail);
			ps.setInt(2, start);
			ps.setInt(3, end);
			rs = ps.executeQuery();
			msgList = new ArrayList<MsgDTO>();
			while (rs.next()) {
				dto = new MsgDTO();
				dto.setMsgNo(rs.getInt("msgNo"));
				dto.setSender_email(rs.getString("sender_email"));
				dto.setMsgContent(rs.getString("msgContent"));
				dto.setMsgOpen(rs.getString("msgOpen"));
				dto.setReg_date(rs.getDate("reg_date"));
				msgList.add(dto);
			}
			
			System.out.println("msgList 값이 있나욘? : " +msgList);
			
			int total = totalCount(loginemail);
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
			
			//int pages = total / pagePerCnt; // 만들 수 있는 페이지 숫자
			
			msgMap.put("msgList", msgList);
			msgMap.put("totalPage", totalPages);
			msgMap.put("currPage", page);
			msgMap.put("pageLength", pageLength);
			msgMap.put("startPage", startPage);
			msgMap.put("endPage", endPage);	
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return msgMap;
	}

	
	public MsgDTO msgDetail(int msgNo) {
		String sql ="SELECT msgNo, sender_email, receiver_email, msgContent, msgOpen, reg_date FROM message WHERE msgNo=?";
		MsgDTO dto = new MsgDTO();
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, msgNo);
			rs = ps.executeQuery();
			if(rs.next()) {
				dto.setMsgNo(rs.getInt("msgNo"));
				dto.setSender_email(rs.getString("sender_email"));
				dto.setReceiver_email(rs.getString("receiver_email"));
				dto.setMsgContent(rs.getString("msgContent"));
				dto.setMsgOpen(rs.getString("msgOpen"));
				dto.setReg_date(rs.getDate("reg_date"));
			}	
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dto;
	}

	public int msgDel(int msgNo) {
		int success = 0;
		String sql = "DELETE FROM message WHERE msgNo=?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, msgNo);
			success = ps.executeUpdate();
			if (success > 0 ) {
				System.out.println("메시지 DB 삭제 성공! 삭제개수: "+success);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	public int msgArrDel(String[] delList) {
		
		String sql = "DELETE FROM message WHERE msgNo = ?";
		int cnt = 0;
		for(String msgNo : delList) {
			try {
				ps = conn.prepareStatement(sql);
				ps.setString(1, msgNo);
				cnt += ps.executeUpdate();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return cnt;
		
	}
	/*
	public ArrayList<MsgDTO> msgMyMsg(String loginemail) {
		String sql = "SELECT msgNo, sender_email, receiver_email, msgContent, reg_date FROM message WHERE sender_email=?";
		ArrayList<MsgDTO> msgMyMsg = null;
		MsgDTO dto = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, loginemail);
			rs = ps.executeQuery();
			msgMyMsg = new ArrayList<MsgDTO>();
			while (rs.next()) {
				dto = new MsgDTO();
				dto.setMsgNo(rs.getInt("msgNo"));
				dto.setSender_email(rs.getString("sender_email"));
				dto.setReceiver_email(rs.getString("receiver_email"));
				dto.setMsgContent(rs.getString("msgContent"));
				dto.setReg_date(rs.getDate("reg_date"));
				msgMyMsg.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return msgMyMsg;
	}
	*/
	
	
	public HashMap<String, Object> msgMyMsg(int page, String loginemail) {
		String sql = "SELECT msgNo, sender_email, receiver_email, msgContent, msgOpen, reg_date FROM "
							+ "(SELECT ROW_NUMBER() OVER(ORDER BY msgNo DESC)AS rnum, msgNo, sender_email, receiver_email, msgContent, msgOpen, reg_date FROM message WHERE sender_email=?) "
							+ "WHERE rnum BETWEEN ? AND ? ";
		
		ArrayList<MsgDTO> msgList = null;
		HashMap<String, Object> msgMap = new HashMap<String, Object>();
		MsgDTO dto = null;
		
		
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
			ps.setString(1, loginemail);
			ps.setInt(2, start);
			ps.setInt(3, end);
			rs = ps.executeQuery();
			msgList = new ArrayList<MsgDTO>();
			while (rs.next()) {
				dto = new MsgDTO();
				dto.setMsgNo(rs.getInt("msgNo"));
				dto.setSender_email(rs.getString("sender_email"));
				dto.setReceiver_email(rs.getString("receiver_email"));
				dto.setMsgContent(rs.getString("msgContent"));
				dto.setMsgOpen(rs.getString("msgOpen"));
				dto.setReg_date(rs.getDate("reg_date"));
				msgList.add(dto);
			}
		
			int total = totalCount(loginemail);
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
			
			//int pages = total / pagePerCnt; // 만들 수 있는 페이지 숫자
			
			msgMap.put("msgList", msgList);
			msgMap.put("totalPage", totalPages);
			msgMap.put("currPage", page);
			msgMap.put("pageLength", pageLength);
			msgMap.put("startPage", startPage);
			msgMap.put("endPage", endPage);	
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		return msgMap;
	}
	
	/*
	public int msgReport(String msgNo, String sender_email, String reportContent) {
		int success = 0;
		String sql = "INSERT INTO report1(reportNo, msgNo, email, reportText, state) "+
							"VALUES(reportNo_seq.NEXTVAL,?,?,?,1)";
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, msgNo);
			ps.setString(2, sender_email);
			ps.setString(3, reportContent);
			success = ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}
	*/
	
	public int msgReport(String msgNo, String sender_email, String reportContent) {
		int success = 0;
		String sql1 = "SELECT reportNo FROM report1 WHERE msgNo = ? AND state = 1";
		String sql2 = "INSERT INTO report1(reportNo, msgNo, email, reportText, state) "+
							"VALUES(reportNo_seq.NEXTVAL,?,?,?,1)";
		try {
			ps = conn.prepareStatement(sql1);
			ps.setString(1, msgNo);
			rs = ps.executeQuery();
			if(!rs.next()) {
				try {
					ps = conn.prepareStatement(sql2);
					ps.setString(1, msgNo);
					ps.setString(2, sender_email);
					ps.setString(3, reportContent);
					success = ps.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e1) {
			e1.printStackTrace();
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
	}

	public int msgOpen(int msgNo) {
		int success = 0;
		String sql = "UPDATE message SET msgOpen =  1 WHERE msgNo = ?";
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, msgNo);
			success = ps.executeUpdate();
			System.out.println("조회 확인... (1=메세지 읽음)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return success;
	}

	
	//편지를 받는 사람으로 검색
	/*
	public ArrayList<MsgDTO> emailList(String searchKey, String loginemail) {
		String sql = "SELECT msgNo,sender_email,receiver_email,msgContent,reg_date,msgOpen "
							+"FROM message WHERE (receiver_email LIKE ? OR msgContent LIKE ?) AND sender_email = ?";
		ArrayList<MsgDTO> emailList = null;
		MsgDTO dto = null;
		
		try {
			ps = conn.prepareStatement(sql);
			ps.setString(1, "%"+searchKey+'%');
			ps.setString(2, "%"+searchKey+'%');
			ps.setString(3, loginemail);
			rs = ps.executeQuery();
			emailList = new ArrayList<MsgDTO>();
			while (rs.next()) {
				dto = new MsgDTO();
				dto.setMsgNo(rs.getInt("msgNo"));
				dto.setSender_email(rs.getString("sender_email"));
				dto.setReceiver_email(rs.getString("receiver_email"));
				dto.setMsgContent(rs.getString("msgContent"));
				dto.setReg_date(rs.getDate("reg_date"));
				dto.setMsgOpen(rs.getString("msgOpen"));
				emailList.add(dto);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return emailList;
	}
	*/
	
	
	//나한테 편지를 보낸사람을 검색
	public HashMap<String, Object> emailList(int page, String loginemail, String searchKey) {
		/*
		String sql =  "SELECT msgNo,sender_email,receiver_email,msgContent,reg_date,msgOpen FROM "
						+	"(SELECT ROW_NUMBER() OVER(ORDER BY msgNo DESC)AS rnum, msgNo, sender_email, receiver_email, msgContent, msgOpen, reg_date FROM message WHERE receiver_email=? AND sender_email LIKE ?) "
						+ "WHERE rnum BETWEEN ? AND ? ";
		*/
		
		String sql = "SELECT msgNo,sender_email,receiver_email,msgContent,reg_date,msgOpen FROM "
		+"(SELECT ROW_NUMBER() OVER(ORDER BY msgNo DESC)AS rnum, msgNo, sender_email, receiver_email, msgContent, msgOpen, reg_date FROM message WHERE receiver_email= ? AND sender_email LIKE ?) "
		+"WHERE rnum BETWEEN ? AND ?";
		
		ArrayList<MsgDTO> emailList = null;
		HashMap<String, Object> msgMap = new HashMap<String, Object>();
		MsgDTO dto = null;
		
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
			ps.setString(1, loginemail);
			ps.setString(2, "%"+searchKey+'%');
			ps.setInt(3, start);
			ps.setInt(4, end);
			rs = ps.executeQuery();
			emailList = new ArrayList<MsgDTO>();
			while (rs.next()) {
				dto = new MsgDTO();
				dto.setMsgNo(rs.getInt("msgNo"));
				dto.setSender_email(rs.getString("sender_email"));
				dto.setReceiver_email(rs.getString("receiver_email"));
				dto.setMsgContent(rs.getString("msgContent"));
				dto.setMsgOpen(rs.getString("msgOpen"));
				dto.setReg_date(rs.getDate("reg_date"));
				emailList.add(dto);
			}
			System.out.println("emailList 값이 있나욘?: "+ emailList);
			
			int total = totalCount(loginemail);
			// 총 게시글 수에 나올 페이지수 나눠서 짝수면 나눠주고 홀수면 +1
			int totalPages = total % pagePerCnt == 0 ? total / pagePerCnt : (total / pagePerCnt) + 1;
			if (totalPages == 0) {
				totalPages = 1;
			}
			// 끝지점을 맨 마지막 페이지로 지정
			if (endPage > totalPages) {
				endPage = totalPages;
			}
			
			System.out.println("토탈카운팅할 이메일: " + loginemail);
			System.out.println("서치키 내용: " + searchKey);
			System.out.println("총 데이터수 : " + total);
			System.out.println("토탈 페이지 : " + totalPages);
			System.out.println();
			
			//int pages = total / pagePerCnt; // 만들 수 있는 페이지 숫자
			
			msgMap.put("emailList", emailList);
			msgMap.put("totalPage", totalPages);
			msgMap.put("currPage", page);
			msgMap.put("pageLength", pageLength);
			msgMap.put("startPage", startPage);
			msgMap.put("endPage", endPage);	
		} catch (SQLException e) {
			e.printStackTrace();
		} 
		
		return msgMap;
	}
	
}
