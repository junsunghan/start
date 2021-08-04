package com.mvc.serviceCenter.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.serviceCenter.service.ScService;




@WebServlet({"/rcontload","/rcommload","/rmessload",
	"/contentload","/commentload","/messageload",
	"/blackdetail","/blackdeatilView","/blacklist","/blackwriteform","/blackregister",
	"/memberlist","/membersearch","/memberdetail",
	"/stoplist","/stopmembersearch","/stopwriteform","/stopregister","/stopremove",
	"/withdrawlist"
})
public class ScController extends HttpServlet {


	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req, resp);
	}

	private void dual(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException{
		String uri = req.getRequestURI();
	    String ctx = req.getContextPath();
	    String addr = uri.substring(ctx.length());
	    System.out.println("addr:" + addr);
	    
	    RequestDispatcher dis = null;
	    ScService service = new ScService(req,resp);

	    String page = req.getParameter("page");
		if(page == null) {
			page = "1";
		}
	    
		switch (addr) {
		//신고 관련 부분 함수 
		//신고 리스트 부르기
		case "/rcontload":
			System.out.println("신고글 리스트 부르기");
			service.contlist();
			break;
		case "/rcommload":
			System.out.println("신고댓글 리스트 부르기");
			service.commlist();
			break;
		case "/rmessload":
			System.out.println("신고메세지 리스트 부르기");
			service.messlist();
			break;
		
		//신고 원본 불러오기
		case "/contentload":
			System.out.println("신고글 불러오기");
			service.contlist();
			break;
		case "/commentload":
			System.out.println("신고댓글 불러오기");
			service.commlist();
			break;
		case "/messageload":
			System.out.println("신고메세지 불러오기");
			service.messlist();
			break;
			
		//블랙리스트 관련 함수
		//블랙리스트 불러오기
		case "/blacklist":
			System.out.println("블랙리스트 불러오기");
			service.blacklist();
			break;
		//블랙리스트 상세보기
		case "/blackdetail":
			System.out.println("블랙리스트 상세보기");
			String email = req.getParameter("email");
			req.getSession().setAttribute("email", email);
			resp.sendRedirect("detail.jsp");
			break;
		//블랙리스트 상세정보 요청
		case "/blackdeatilView":
			System.out.println("상세 정보 요청");
			service.blackdetail();
			break;
		//회원블랙리스트 폼 이동
		case "/blackwriteform":
			System.out.println("블랙리스트 이유 글쓰기 이동");
			req.setAttribute("member", service.memberdetail());
			dis = req.getRequestDispatcher("blackwriteform.jsp");
			dis.forward(req, resp);
			break;
		//회원블랙리스트 함수
		case "/blackregister":
			System.out.println("블랙리스트 등록");
			email = req.getParameter("email");
			if(service.blackregister(email)>0) {
				System.out.println("수정 성공");
				dis = req.getRequestDispatcher("memberlist.jsp");
				dis.forward(req, resp);
			};
			break;
			
		//회원리스트 출력 및 검색 함수
		//회원리스트 불러오기
		case "/memberlist":
			System.out.println("회원리스트 가져오기");
			service.memberlist();
			break;
		//회원리스트 검색
		case "/membersearch":
			System.out.println("회원검색하기");
			email = req.getParameter("email");
			service.membersearch(email);
			break;
		//회원상세정보
		case "/memberdetail":
			System.out.println("회원상세보기 가져오기");
			req.setAttribute("member", service.memberdetail());
			dis = req.getRequestDispatcher("memberdetail.jsp");
			dis.forward(req, resp);
			break;
		
		//회원정지리스트 출력 및 검색 및 정지하기 함수
		//회원정지리스트 불러오기
		case "/stoplist":
			System.out.println("회원정지리스트 가져오기");
			service.stoplist(Integer.parseInt(page));
			break;
		//정지회원 검색
		case "/stopmembersearch":
			System.out.println("정지회원 검색");
			email = req.getParameter("email");
			service.stopmembersearch(email);
		//회원정지 폼 이동
		case "/stopwriteform":
			System.out.println("정지 이유 글쓰기로 이동");
			req.setAttribute("member", service.memberdetail());
			dis = req.getRequestDispatcher("stopwriteform.jsp");
			dis.forward(req, resp);
		//회원정지 함수
		case "/stopregister":
			System.out.println("정지 등록");
			email = req.getParameter("email");
			if(service.stopregister(email)>0) {
				System.out.println("정지등록 성공");
				dis = req.getRequestDispatcher("memberlist.jsp");
				dis.forward(req, resp);
			};
			break;
		case "/stopremove":
			System.out.println("정지 해제하기");
			email = req.getParameter("email");
			if(service.stopremove(email)>0) {
				System.out.println("정지 해제 성공");
				dis = req.getRequestDispatcher("stoplist.jsp");
				dis.forward(req, resp);
			};
			
		// 회원탈퇴 관련 함수
		// 탈퇴회원 리스트 불러오기
		case "/withdrawlist":
			System.out.println("탈퇴회원 리스트 불러오기");
			service.withdrawlist(Integer.parseInt(page));
			break;
		 
		default:
			break;
		}
		
	}

}
