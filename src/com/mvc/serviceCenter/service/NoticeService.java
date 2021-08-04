package com.mvc.serviceCenter.service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import com.mvc.serviceCenter.dao.NotcieDAO;
import com.mvc.serviceCenter.dto.NoticeDTO;

public class NoticeService {
	
	private HttpServletRequest req = null;
	

	public NoticeService(HttpServletRequest req) {
		try {
			req.setCharacterEncoding("UTF-8");
			this.req = req;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public HashMap<String, Object> list() {
		
		String page = req.getParameter("page");
		System.out.println("현재 page : " + page);
		NotcieDAO dao = new NotcieDAO();
		if(page == null) {
			page= "1";
		}
		HashMap<String, Object> map = dao.list(Integer.parseInt(page));
		dao.resClose();// 자원 반납
		return map;
	}


	public int write() {

		int success = 0;
		
		String title = req.getParameter("title");
		String email = req.getParameter("email");
		String content = req.getParameter("content");
		String categoryno = req.getParameter("categoryNo");
		System.out.println(title+"/"+email+"/"+content+"/"+categoryno);
		NotcieDAO dao = new NotcieDAO();
		
		if(title.equals("")||email.equals("")||content.equals("")) {
			System.out.println("경고 빈 칸이 있습니다.");
			success = 0;
			
		}else {
			
			success = dao.write(title,email,content,categoryno);
			System.out.println("insert success : " +success);
			
		}
			dao.resClose();
			return success;		
		
	}


	public NoticeDTO detail() {
		
		NoticeDTO dto = null;
		String idx = req.getParameter("idx");
		System.out.println("idx : " + idx);
		
		NotcieDAO dao = new NotcieDAO();
		
		try {
			dao.conn.setAutoCommit(false);
			dto = dao.detail(idx);
			System.out.println("detail DTO : "+dto);
			
			if(dto == null) {//commit || rollback
				dao.conn.rollback();
			}else {
				dao.conn.commit();
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			dao.resClose();//자원반납			
		}
		
		
		
		return dto;
	}


	public NoticeDTO noticeupdateForm() {
		String idx = req.getParameter("idx");
		System.out.println("idx : " +idx);
		
		NotcieDAO dao = new NotcieDAO();
		NoticeDTO dto = dao.noticedetail(idx);
		System.out.println("dto : " +dto);
		
		dao.resClose();
		return dto;
	}


	public int noticeupdate(String idx) {
		/*
		int success = 0;
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String email = req.getParameter("email");
		System.out.println(title+"/"+content+"/"+email);
		
		ScDAO dao = new ScDAO();
		success = dao.noticeupdate(idx,title,content,email);
		System.out.println("update success : " + success);
		dao.resClose();
		
		
		
		return success;
		*/
		int success = 0;
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String email = req.getParameter("email");
		System.out.println(title+"/"+content+"/"+email);
		
		if(title.equals("")||email.equals("")||content.equals("")) {
			System.out.println("경고 빈 칸이 있습니다.");
			success = 0;
			
		}else {
			NotcieDAO dao = new NotcieDAO();
			success = dao.noticeupdate(idx,title,content,email);
			System.out.println("update success : " + success);
			dao.resClose();
			System.out.println("완료");
		}
			return success;		
		
	}


	public int del() {
		String idx = req.getParameter("idx");
		System.out.println("idx : " + idx);
		
		NotcieDAO dao = new NotcieDAO();
		int success = dao.del(idx);
		System.out.println("del success : " +success);
		dao.resClose();
		
		return success;
		
	}
	
	
}

