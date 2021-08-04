package com.mvc.msg.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mvc.msg.dao.MsgDAO;
import com.mvc.msg.dto.MsgDTO;

public class MsgService {
	
	private HttpServletRequest req = null;
	private HttpServletResponse resp = null;

	public MsgService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public int write() {
		int success = 0;
		//받는사람, 보내는사람, 메세지내용, (메세지번호, 작성시간)
		String sender = req.getParameter("sender");
		String reciever = req.getParameter("reciever");
		String content = req.getParameter("content");
		System.out.println("보내는 사람: "+sender+" / 받는사람: "+reciever+" / content: "+content);
		
		//메세지를 받을 사람을 적지 않았거나, 본인에게 메세지를 보내는 경우, 보내는사람이 없는 경우 => 경고 띄움
		if ( reciever.equals("") || reciever.equals(sender) || sender.equals("") || content.equals("")) {
			System.out.println("경고!! 메세지를 받을사람 또는 메세지를 받는사람을 확인해주세요!!");
			success = 0;
		} else {
			MsgDAO dao = new MsgDAO();
			success = dao.write(sender, reciever, content);
			System.out.println(success+"개 의 messang 저장");
			dao.resClose();
			System.out.println("자원반납 했음!");
		}
		return success;
	}

	public HashMap<String, Object> msgList(String loginemail) {
		String page = req.getParameter("page");
		MsgDAO dao = new MsgDAO();
		if(page==null) {
			page= "1";
		}
		System.out.println("현재 page: " + page);
		
		HashMap<String, Object> map = dao.msgList(Integer.parseInt(page), loginemail);
		dao.resClose();
		System.out.println("자원반납 했음!");
		return map;
	}

	public MsgDTO msgDetail() {
		int msgNo = Integer.parseInt(req.getParameter("msgNo"));
		MsgDAO dao = new MsgDAO();
		MsgDTO msgDetail  = new MsgDTO();
		
		try {
			dao.conn.setAutoCommit(false);
			if (dao.msgOpen(msgNo) >0) {
				msgDetail = dao.msgDetail(msgNo);
			}
			System.out.println("상세보기 데이터 불러오기(msgDetail): "+msgDetail);
			
			if(msgDetail == null) {
				dao.conn.rollback();
			}else {
				dao.conn.commit();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			dao.resClose();
			System.out.println("자원반납 했음!");
		}
		return msgDetail;
	}

	public int msgDel() {
		int msgNo = Integer.parseInt(req.getParameter("msgNo"));
		int success = 0;
		MsgDAO dao = new MsgDAO();	
		success = dao.msgDel(msgNo);
		if (success > 0) {
			System.out.println("메세지 삭제 성공!");
		} else {
			System.out.println("메세지 삭제 실패!");
		}
		dao.resClose();
		System.out.println("자원반납 했음!");
		return success;
	}

	public void msgArrDel(String[] delList) throws IOException {
		
		MsgDAO dao = new MsgDAO();
		HashMap<String, Object> map = new HashMap<String, Object>(); //ajax 사용하기 위함
		
		int cnt = dao.msgArrDel(delList);
		System.out.println("메세지 삭제 성공 개수: " + cnt);
		map.put("cnt", cnt); //ajax 에 전달하기 위해서 해쉬맵에 담고
		
		dao.resClose();
		System.out.println("자원반납 했음!");
		
		resp.getWriter().println(new Gson().toJson(map)); // 해쉬맵을 ajax 가 읽을 수 있게 json 으로 변환
	}

	public HashMap<String, Object> msgMyMsg(String loginemail) {
		String page = req.getParameter("page");
		
		MsgDAO dao = new MsgDAO();
		if(page==null) {
			page= "1";
		}
		System.out.println("현재 page: " + page);
		
		HashMap<String, Object> map = dao.msgMyMsg(Integer.parseInt(page), loginemail);
		dao.resClose();
		System.out.println("자원반납 했음!");
		
		return map;
	}
	
	public int msgReport() {
		//신고넘버, 메세지넘버, 등록일, 신고내용, 피신고자 이메일
		int success = 0;
		String msgNo = req.getParameter("msgNo");
		String sender_email = req.getParameter("sender_email");
		String reportContent = req.getParameter("reportContent");
		MsgDAO dao = new MsgDAO();
		success = dao.msgReport(msgNo, sender_email, reportContent);
		dao.resClose();
		System.out.println("자원반납 했음!");
		return success;
	}

	public HashMap<String,Object> emailList(String loginemail, String searchKey) {
		String page = req.getParameter("page");
		MsgDAO dao = new MsgDAO();
		if(page==null) {
			page= "1";
		}
		System.out.println("현재 page: " + page);
		
		HashMap<String, Object> map = dao.emailList(Integer.parseInt(page), loginemail, searchKey);
		dao.resClose();
		System.out.println("자원반납 했음!");
		return map;
		
	}


}
