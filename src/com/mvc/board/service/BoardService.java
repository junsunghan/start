package com.mvc.board.service;

import java.io.UnsupportedEncodingException;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.board.dao.BoardDAO;
import com.mvc.board.dto.FootprintDTO;

public class BoardService {

	HttpServletRequest req = null;
	
	
	public BoardService(HttpServletRequest req) {
		try {
			req.setCharacterEncoding("UTF-8");
			this.req = req;
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<FootprintDTO> fplist(String email) {
		
		BoardDAO dao = new BoardDAO();
		ArrayList<FootprintDTO> fplist = dao.fplist(email);
		System.out.println("fplist size : "+fplist.size());
		dao.resClose();
		return fplist;
	}
	
	
	public ArrayList<FootprintDTO> feedlist() {
		BoardDAO dao = new BoardDAO();
		ArrayList<FootprintDTO> feedlist = dao.feedlist();
		System.out.println("feedlist size : "+feedlist.size());
		dao.resClose();
		return feedlist;
	}

	//공개
	public int fpwriteOk(String email) {
		
		int pk =0;
		UploadService  upload = new UploadService(req);
		FootprintDTO dto = upload.photoUpload();//사진 업로드
		
		//글 쓰기
		BoardDAO dao = new BoardDAO();
		pk = dao.fpwriteOk(dto, email);
		System.out.println("footPrintNO : "+pk);
		dao.resClose();
		return pk;
	}

	/*
	 * //비공개 public int fpwriteNo() {
	 * 
	 * int pk =0; UploadService upload = new UploadService(req); FootprintDTO dto =
	 * upload.photoUpload();//사진 업로드
	 * 
	 * //글 쓰기 BoardDAO dao = new BoardDAO(); pk = dao.fpwriteNo(dto);
	 * System.out.println("footPrintNO : "+pk); dao.resClose(); return pk; }
	 */

	public FootprintDTO fpdetail() {
		
		FootprintDTO dto = null;
		String footPrintNO = req.getParameter("footPrintNO");
		System.out.println("footPrintNO : "+footPrintNO);
        BoardDAO dao = new BoardDAO();
		dto = dao.fpdetail(footPrintNO);
		//System.out.println("dto : "+dto);
		dao.resClose();
		return dto;
	}

	public int fpdel() {
		String footPrintNO = req.getParameter("footPrintNO");
		System.out.println("footPrintNO : "+footPrintNO);
		
		BoardDAO dao = new BoardDAO();
		//int success = dao.del(footPrintNO);
		//System.out.println("del success : "+success);
		//dao.resClose();
		
		//1. footprintNO 로 사진이 있는지 확인
		FootprintDTO dto = dao.getFileName(footPrintNO);
		System.out.println("photo dto : "+dto);
		
		//2. 글 삭제
		int success = dao.fpdel(footPrintNO);
		System.out.println("del success : "+success);
		
		//3. 글삭제 성공 && 업로드한 파일이 있다면
		if(success > 0 && dto != null) {
			System.out.println("파일 삭제 실행!!");
			UploadService upload = new UploadService(req);
			upload.fpdel(dto.getNewFileName());
		}
		dao.resClose();
		return success;
	}

	public FootprintDTO fpupdateForm() {
		String footPrintNO = req.getParameter("footPrintNO");
		System.out.println("footPrintNO : "+footPrintNO);
		BoardDAO dao = new BoardDAO();
		FootprintDTO dto = dao.fpdetail(footPrintNO);
		System.out.println("dto : "+dto);
		dao.resClose();
		return dto;
	}

	/*
	 * public int fpupdate(String footPrintNO) {
	 * 
	 * int success =0;
	 * 
	 * String footprintText = req.getParameter("footprintText");
	 * System.out.println(footprintText);
	 * 
	 * BoardDAO dao = new BoardDAO(); success =
	 * dao.fpupdate(footPrintNO,footprintText);
	 * System.out.println("fpupdate success : "+success); dao.resClose();
	 * 
	 * UploadService upload = new UploadService(req); FootprintDTO dto =
	 * upload.photoUpload();
	 * 
	 * dao.resClose(); return success; }
	 */

	public int fpupdate() {
		
		int footPrintNO = 0;
		
		UploadService upload = new UploadService(req);
		
		FootprintDTO dto = upload.photoUpload();
		System.out.println(dto.getOriFileName()+"/"+dto.getNewFileName());
		
		BoardDAO dao = new BoardDAO();
		int success = dao.fpupdate(dto);
		System.out.println("fpupdate success : "+success);
		
		  if(dto != null && success >0) { footPrintNO = dto.getFootPrintNO(); }
		 
		if(dto.getNewFileName() != null) {
			
			FootprintDTO PostPicInfo = dao.getFileName(String.valueOf(footPrintNO));
			String delFileName = null;
			if(PostPicInfo != null) {
				delFileName = PostPicInfo.getNewFileName();
			}
			System.out.println("delFileName : "+delFileName);
		dao.fpupdateFileName(delFileName, dto);
		upload.fpdel(delFileName);
		}
		dao.resClose();
		return footPrintNO;
	}

	
	/**/
public ArrayList<FootprintDTO> hashtaglist(String hashtag){
		
		BoardDAO dao = new BoardDAO();
		ArrayList<FootprintDTO> hashtaglist = dao.hashtaglist(hashtag);
		
		System.out.println("검색 결과 수 : "+hashtaglist.size());
		dao.resClose();
		return hashtaglist;
		
	}
	
}









