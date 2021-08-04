package com.mvc.serviceCenter.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.mvc.board.dto.CommentDTO;
import com.mvc.board.dto.FootprintDTO;
import com.mvc.member.dto.MemberDTO;
import com.mvc.msg.dto.MsgDTO;
import com.mvc.serviceCenter.dao.ScDAO;
import com.mvc.serviceCenter.dto.ReportDTO;


public class ScService {

	private HttpServletRequest req = null;
	private HttpServletResponse resp = null;

	public ScService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}

	public void contdetail() throws IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();

		ScDAO dao = new ScDAO();
		ArrayList<FootprintDTO> list = null;
		try {
			list = dao.contdetail();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.resClose();
			map.put("list", list);
		}

		resp.setContentType("text/html; charset=UTF-8");
		resp.getWriter().println(new Gson().toJson(map));
	}

	public void commdetail() throws IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();

		ScDAO dao = new ScDAO();
		ArrayList<CommentDTO> list = null;
		try {
			list = dao.commdetail();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.resClose();
			map.put("list", list);
		}

		resp.setContentType("text/html; charset=UTF-8");
		resp.getWriter().println(new Gson().toJson(map));

	}

	public void messdetail() throws IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();

		ScDAO dao = new ScDAO();
		ArrayList<MsgDTO> list = null;
		try {
			list = dao.messdetail();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			dao.resClose();
			map.put("list", list);
		}

		resp.setContentType("text/html; charset=UTF-8");
		resp.getWriter().println(new Gson().toJson(map));

	}

	public void contlist() throws IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		
		ScDAO dao = new ScDAO();
		ArrayList<ReportDTO> list = null;
		
		list = dao.contlist();
		dao.resClose();
		map.put("list", list);
		resp.setContentType("text/html; charset=UTF-8");
		resp.getWriter().println(new Gson().toJson(map));
		
		
	}

	public void commlist() throws IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		ScDAO dao = new ScDAO();
		ArrayList<ReportDTO> list = null;
		
		list = dao.commlist();
		dao.resClose();
		map.put("list", list);
		resp.setContentType("text/html; charset=UTF-8");
		resp.getWriter().println(new Gson().toJson(map));
	}

	public void messlist() throws IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		ScDAO dao = new ScDAO();
		ArrayList<ReportDTO> list = null;
		
		list = dao.messlist();
		dao.resClose();
		map.put("list", list);
		resp.setContentType("text/html; charset=UTF-8");
		resp.getWriter().println(new Gson().toJson(map));
		
	}

	public void blacklist() throws IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		ScDAO dao = new ScDAO();
		ArrayList<MemberDTO> list = null;
		
		list = dao.blacklist();
		dao.resClose();
		map.put("list", list);
		resp.setContentType("text/html; charset=UTF-8");
		resp.getWriter().println(new Gson().toJson(map));
		
	}

	public void blackdetail() throws IOException {
		String email = (String) req.getSession().getAttribute("email");
		System.out.println("상세 데이터 가져올 아이디: "+email);
		
		ScDAO dao = new ScDAO();
		MemberDTO dto = null;
		HashMap<String, Object> map = new HashMap<String, Object>();
		boolean success = false;
		try {
			dto = dao.detail(email);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			dao.resClose();
			System.out.println("가져올 이메일: "+dto.getEmail());
			map.put("info", dto);
			map.put("success", success);
		}
		if(email != null) {
			req.getSession().removeAttribute("email");
		}
		
		resp.setContentType("text/html; charset=UTF-8");
		resp.getWriter().print(new Gson().toJson(map));
		
	}

	public void memberlist() throws IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		ScDAO dao = new ScDAO();
		ArrayList<MemberDTO> list = null;
		
		list = dao.memberlist();
		dao.resClose();
		map.put("list", list);
		resp.setContentType("text/html; charset=UTF-8");
		resp.getWriter().println(new Gson().toJson(map));
		
	}
	public void membersearch(String email) throws IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		ScDAO dao = new ScDAO();
		ArrayList<MemberDTO> list = null;
		
		list = dao.membersearch(email);
		dao.resClose();
		map.put("list", list);
		resp.setContentType("text/html; charset=UTF-8");
		resp.getWriter().println(new Gson().toJson(map));
		
	}

	public void stoplist(int page) throws IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		ScDAO dao = new ScDAO();
		
		map = dao.stoplist(page);
		dao.resClose();
		resp.setContentType("text/html; charset=UTF-8");
		resp.getWriter().println(new Gson().toJson(map));
		
	}

	public void stopmembersearch(String email) throws IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		ScDAO dao = new ScDAO();
		ArrayList<MemberDTO> list = null;
		
		list = dao.stopmembersearch(email);
		dao.resClose();
		map.put("list", list);
		resp.setContentType("text/html; charset=UTF-8");
		resp.getWriter().println(new Gson().toJson(map));
		
	}

	public MemberDTO memberdetail() throws IOException {
		MemberDTO dto = null;
		ScDAO dao = new ScDAO();
		String email = req.getParameter("email");
		System.out.println("email: "+email);
		dto = dao.memberdetail(email);
		dao.resClose();
		return dto;
		
	}

	public MemberDTO stopwriteform(String email) {
		MemberDTO dto = null;
		ScDAO dao = new ScDAO();
		email = req.getParameter("email");
		System.out.println("email: "+email);
		dto = dao.stopwriteform(email);
		dao.resClose();
		return dto;
		
	}

	public MemberDTO blackwriteform(String email) {
		MemberDTO dto = null;
		ScDAO dao = new ScDAO();
		email = req.getParameter("email");
		System.out.println("email: "+email);
		dto = dao.blackwriteform(email);
		dao.resClose();
		return dto;
		
	}

	public int blackregister(String email) {
		int success = 0;
		ScDAO dao = new ScDAO();
		String reason = req.getParameter("reason");
		System.out.println("이메일: "+email+"사유: "+reason);
		success = dao.blackregister(email,reason);
		dao.resClose();
		
		return success;
	}

	public int stopregister(String email) {
		int success = 0;
		ScDAO dao = new ScDAO();
		String reason = req.getParameter("reason");
		System.out.println("이메일: "+email+"사유: "+reason);
		success = dao.stopregister(email,reason);
		dao.resClose();
		
		return success;
	}

	public int stopremove(String email) {
		int success = 0;
		ScDAO dao = new ScDAO();
		success = dao.stopremove(email);
		dao.resClose();
		
		return success;
		
	}

	public void withdrawlist(int page) throws IOException {
		HashMap<String, Object> map = new HashMap<String, Object>();
		ScDAO dao = new ScDAO();
		
		map = dao.withdrawlist(page);
		dao.resClose();
		resp.setContentType("text/html; charset=UTF-8");
		resp.getWriter().println(new Gson().toJson(map));
		System.out.println("map:" + map);
	}
		
}

