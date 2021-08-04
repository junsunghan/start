package com.mvc.serviceCenter.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.serviceCenter.service.NoticeService;

@WebServlet({ "/noticelist", "/noticewrite", "/noticedetail", "/noticedel", "/noticeupdateForm", "/noticeupdate" })
public class NoticeController extends HttpServlet {

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
		String page = "";
		String msg = "";
		NoticeService service = new NoticeService(req);

		switch (addr) {
		case "/noticelist":
			System.out.println("공지 리스트 요청");
			req.setAttribute("map", service.list());
			dis = req.getRequestDispatcher("noticelist.jsp");
			dis.forward(req, resp);
			break;

		case "/noticewrite":
			System.out.println("공지 글쓰기 요청");
			msg = "글 작성 실패";
			page = "noticewriteForm.jsp";

			if (service.write() == 1) {
				msg = "글 작성 성공";
				page = "noticelist";
				System.out.println("msg :" + msg);
			}
			req.setAttribute("msg", msg);

			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp); 
			
			break;

		case "/noticedetail":
			System.out.println("공지 상세보기");
			req.setAttribute("noticefaq", service.detail());
			dis = req.getRequestDispatcher("noticedetail.jsp");
			dis.forward(req, resp);

			break;

		case "/noticedel":
			System.out.println("공지 지우기 요청");
			service.del();
			page = "noticelist";
			resp.sendRedirect(page);
			break;

		case "/noticeupdateForm":
			System.out.println("공지 수정 요청");
			req.setAttribute("noticefaq", service.noticeupdateForm());
			dis = req.getRequestDispatcher("noticeupdateForm.jsp");
			dis.forward(req, resp);
			break;

		case "/noticeupdate":
			// 여기도 주소가 남아잇네
			System.out.println("공지 업데이트 완료");
			String idx = req.getParameter("idx");
			System.out.println("idx : " + idx);
			msg = "수정에 실패 했다";
			page = "noticeupdateForm?idx=" + idx;
			if (service.noticeupdate(idx) > 0) {
				msg = "수정에 성공";
				page = "noticedetail?idx=" + idx;
			}
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
			break;

		}

	}

}
