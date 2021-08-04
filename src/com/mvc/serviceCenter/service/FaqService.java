package com.mvc.serviceCenter.service;

import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;


import com.mvc.serviceCenter.dao.FaqDAO;
import com.mvc.serviceCenter.dto.FaqDTO;

public class FaqService {

	private HttpServletRequest req = null;
	
	public FaqService(HttpServletRequest req) {
		try {
			req.setCharacterEncoding("UTF-8");
			this.req = req;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	/*
	 * public Object list() {
	 * 
	 * FaqDAO dao = new FaqDAO(); ArrayList<FaqDTO> list = dao.list();
	 * System.out.println("list size : " + list.size()); dao.resClose();
	 * 
	 * 
	 * return list; }
	 */
	public HashMap<String, Object> list() {
		String page = req.getParameter("page");
		System.out.println("현재 page : " + page);
		FaqDAO dao = new FaqDAO();
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
		FaqDAO dao = new FaqDAO();
		
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

	public FaqDTO detail() {
		
		FaqDTO dto = null;
		String idx = req.getParameter("idx");
		System.out.println("idx : " + idx);
		
		FaqDAO dao = new FaqDAO();
		
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

	public int del() {
		int success = 0;
		String idx = req.getParameter("idx");
		System.out.println("idx : " + idx);
		
		FaqDAO dao = new FaqDAO();
		success = dao.del(idx);
		System.out.println("del success : " +success);
		
		dao.resClose();		
		return success;
		
	}

	public FaqDTO faqupdateForm() {
		String idx = req.getParameter("idx");
		System.out.println("idx : " +idx);
		FaqDAO dao = new FaqDAO();
		FaqDTO dto = dao.faqdetail(idx);
		System.out.println("dto : " +dto);
		dao.resClose();
		return dto;
	}

	public int faqupdate(String idx) {
		int success = 0;
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String email = req.getParameter("email");
		System.out.println(title+"/"+content+"/"+email);
		
		if(title.equals("")||email.equals("")||content.equals("")) {
			System.out.println("경고 빈 칸이 있습니다.");
			success = 0;
			
		}else {
			FaqDAO dao = new FaqDAO();
			success = dao.faqupdate(idx,title,content,email);
			System.out.println("update success : " + success);
			dao.resClose();
			System.out.println("완료");
		}
			return success;		
	}

	public HashMap<String, Object> searchlist(String searchKey) {
String page = req.getParameter("page");
		
		FaqDAO dao = new FaqDAO();
		if(page==null) {
			page= "1";
		}
		System.out.println("현재 page: " + page);
		
		HashMap<String, Object> map = dao.searchlist(Integer.parseInt(page), searchKey);
		dao.resClose();
		System.out.println("자원반납 했음!");
		return map;
	}



	

}
