package com.mvc.serviceCenter.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.serviceCenter.service.QnaService;

@WebServlet({ "/qnalist", "/qnawrite", "/qnadetail", "/qnadel", "/qnaupdateForm", "/qnaupdate", "/qnaSearch" })
public class QnaController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		daul(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		daul(req, resp);
	}

	private void daul(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String uri = req.getRequestURI();
		String ctx = req.getContextPath();
		String addr = uri.substring(ctx.length());
		System.out.println("addr : " + addr);

		RequestDispatcher dis = null;
		req.setCharacterEncoding("UTF-8");
		String page = "";
		String msg = "";
		QnaService service = new QnaService(req);

		switch (addr) {
		case "/qnalist":
			System.out.println("QNA 리스트 요청");
			req.setAttribute("map", service.list());
			dis = req.getRequestDispatcher("qnalist.jsp");
			dis.forward(req, resp);
			break;
			
		case "/qnawrite":
			System.out.println("Q&A 글쓰기 요청");
			msg = "글 작성 실패";
			page = "qnawriteForm.jsp";
			if (service.write() == 1) {
				msg = "글 작성 성공";
				page = "qnalist";
			}
			req.setAttribute("msg", msg);

			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);

			break;
			
		case "/qnadetail":
			System.out.println("Q&A 상세보기");
			req.setAttribute("qna", service.detail());
			dis = req.getRequestDispatcher("qnadetail.jsp");
			dis.forward(req, resp);
			
			break;
	
			
		case "/qnadel":
			System.out.println("Q&A 삭제 요청");

			

			break;
			
		case "/qnaupdateForm":
			System.out.println("Q&A 수정 요청");
		
			break;

			
		case "/qnaupdate":
			System.out.println("Q&A 업데이트 완료");
			
			break;

		
		case "/qnaSearch" :
			System.out.println("qna 검색 요청");
			String searchKey = req.getParameter("searchKey");//검색어
			System.out.println("검색어 : " + searchKey);
			req.setAttribute("searchlist", service.searchlist(searchKey));
			dis= req.getRequestDispatcher("qnasearch.jsp");
			dis.forward(req, resp);
			
			break;
				
		}
	}

}
