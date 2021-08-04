package com.mvc.serviceCenter.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.serviceCenter.service.FaqService;

@WebServlet({ "/faqlist", "/faqwrite", "/faqdetail", "/faqdel", "/faqupdateForm", "/faqupdate" , "/faqsearch"})
public class FaqContorller extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req, resp);
	}

	private void dual(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String uri = req.getRequestURI();
		String ctx = req.getContextPath();
		String addr = uri.substring(ctx.length());
		System.out.println("addr : " + addr);

		RequestDispatcher dis = null;
		req.setCharacterEncoding("UTF-8");
		String page = "";
		String msg = "";
		FaqService service = new FaqService(req);

		switch (addr) {

		case "/faqlist":
			System.out.println("faq 리스트 요청");
			req.setAttribute("map", service.list());
			dis = req.getRequestDispatcher("faqlist.jsp");
			dis.forward(req, resp);

			break;

		case "/faqwrite":
			System.out.println("공지 글쓰기 요청");
			msg = "글 작성 실패";
			page = "faqwriteForm.jsp";
			if (service.write() == 1) {
				msg = "글 작성 성공";
				page = "faqlist";
			}
			
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);

			break;

		case "/faqdetail":
			System.out.println("faq 상세보기");
			req.setAttribute("noticefaq", service.detail());
			dis = req.getRequestDispatcher("faqdetail.jsp");
			dis.forward(req, resp);
			break;

		case "/faqdel":
			System.out.println("faq 삭제 요청");

			msg = "삭제 성공";
			page = "faqlist";
			if(service.del()>0) {
				msg="글 작성 성공";
				page="faqlist";
			}
			req.setAttribute("msg", msg);
			resp.sendRedirect(page);

			break;

		case "/faqupdateForm":
			System.out.println("faq 수정 요청");
			req.setAttribute("noticefaq", service.faqupdateForm());
			dis = req.getRequestDispatcher("faqupdateForm.jsp");
			dis.forward(req, resp);
			break;

		case "/faqupdate":
			System.out.println("공지 업데이트 완료");
			String idx = req.getParameter("idx");
			System.out.println("idx : " + idx);
			msg = "수정에 실패 했다";
			page = "faqupdateForm?idx=" + idx;
			if (service.faqupdate(idx) > 0) {
				msg = "수정에 성공";
				page = "faqdetail?idx=" + idx;
			}
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
			break;
			
		case "/faqsearch" :
			System.out.println("faq 검색 요청");
			String searchKey = req.getParameter("searchKey");//검색어
			System.out.println("검색어 : " + searchKey);
			req.setAttribute("map", service.searchlist(searchKey));
			req.setAttribute("searchlist", searchKey);
			dis= req.getRequestDispatcher("faqsearch.jsp");
			dis.forward(req, resp);
			
			break;
			
			
		}

	}

}
