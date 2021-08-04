package com.mvc.member.service;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.sql.SQLException;
import java.util.HashMap;

import com.google.gson.Gson;
import com.mvc.member.dao.MemberDAO;
import com.mvc.member.dto.MemberDTO;

import javax.servlet.http.HttpServletResponse;

public class MemberService {
	
	private HttpServletRequest req = null;
	private HttpServletResponse resp = null;

	public MemberService(HttpServletRequest req, HttpServletResponse resp) {
		this.req = req;
		this.resp = resp;
	}
	public MemberDTO login(String email, String pw) {
		System.out.println(email+"/"+pw);
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = dao.login(email, pw);
		return dto;
	}
	
	public boolean join(String email, String nickname, String pw, String name, String gender, String birth,
			String phone, String style) {
		System.out.println(email+"/"+ nickname+"/"+ pw+"/"+ name+"/"+ gender+"/"+ birth+"/"+ phone+"/"+style);
		MemberDAO dao = new MemberDAO();
		int suc = dao.join(email, nickname, pw, name, gender, birth, phone, Integer.parseInt(style));
		boolean success =false;
		if(suc>0) {
			success=true;
		}
		dao.resClose();
		return success;
	}

	public String overlay(String email) {
		boolean overlay = false;
		boolean success = false;
		
		MemberDAO dao = new MemberDAO();
		try {
			overlay = dao.overlay(email);
			success = true;
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			dao.resClose();
			Gson json = new Gson();
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("success", success); //ajax에서 사용할 수 있게 객체화
			map.put("overlay", overlay); //ajax에서 사용할 수 있게 객체화
			String obj = json.toJson(map); //객체환 된 것을 String으로
			//resp.setContentType("text/html charset=UTF-8"); // 한글이 없으면 생략가능
			//resp.setHeader("Access-Control-Allow-origin", "*"); //view가 같은 서버에 있으면 생략가능
			return obj;
		}
	}
	public MemberDTO detail(String email) {
		MemberDAO dao = new MemberDAO();
		return dao.detail(email);
	}
	public boolean update(String email, String nickname, String pw, String name, String gender, String birth,
			String phone, String style) {
		MemberDAO dao = new MemberDAO();
		//System.out.println(email+ nickname+ pw+ name+ gender +birth+ phone+ style);
		return dao.update(email, nickname, pw, name, gender, birth, phone, style);
		
	}
	public MemberDTO updateForm() {
		String email = (String) req.getSession().getAttribute("loginemail");
		System.out.println("어떤 계정 수정? :"+email);
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = dao.detail(email);
		
		return dto;
	}
	
	public boolean upload(String email) {
		boolean suc = false;
		MemberDTO dto = null;
		UploadService up = new UploadService(req);
		MemberDAO dao = new MemberDAO();
		dto = up.Upload(email);
		if(dto.getNewName() != null) {
			MemberDTO photoInfo = dao.getFileName(email);
			String delFileName = null;
			if(photoInfo != null) {
				delFileName = photoInfo.getNewName();
				System.out.println("삭제파일 이름 : " +delFileName);
			}
			suc = dao.updateName(delFileName, dto);
			dao.resClose();
			//기존 파일을 지우고
			up.del(delFileName);
			System.out.println(delFileName+"파일 삭제 완료");
		}
		return suc;
	}
	/*
	 * public MemberDTO photo(String email) { boolean suc = false; MemberDTO dto =
	 * null; UploadService up = new UploadService(req); MemberDAO dao = new
	 * MemberDAO(); dto = dao.photo(email); if(dto.getNewName() != null) { MemberDTO
	 * photoInfo = dao.getFileName(email); String delFileName = null; if(photoInfo
	 * != null) { delFileName = photoInfo.getNewName(); }
	 * System.out.println("기존 파일 삭제 : "+delFileName); //새로 업로드한 파일을 photo 에 업데이트 또는
	 * insert suc = dao.updateName(delFileName, dto); //기존 파일을 지우고
	 * up.del(delFileName); } return dto; }
	 */
	public MemberDTO chk(String email, String pw) {
		MemberDAO dao = new MemberDAO();
		MemberDTO dto = null;
		System.out.println(email+"/"+pw);
		dto = dao.chk(email, pw);
		if(dto.getPw()!=null) {
			return dto;
		}else {
			return null;
		}
	}
	public boolean cancel(String email) {
		MemberDAO dao = new MemberDAO();
		return dao.cancel(email);
	}
}
