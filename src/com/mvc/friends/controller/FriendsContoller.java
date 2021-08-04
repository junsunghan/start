package com.mvc.friends.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.friends.dto.TourStyleDTO;
import com.mvc.friends.service.FriendsService;

@WebServlet({"/friendsAddOverlay", "/friendsAdd", "/friendsDel", "/friendsList", "/friendsBlock", "/friendsBlockCancle", "/friendsRecomend"})
public class FriendsContoller extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		dual(req, resp);
	}

	private void dual(HttpServletRequest req, HttpServletResponse resp)  throws ServletException, IOException {

		String uri = req.getRequestURI();
		String ctx = req.getContextPath();
		String addr = uri.substring(ctx.length());
		System.out.println("addr: "+addr);
		
		RequestDispatcher dis = null;
		req.setCharacterEncoding("UTF-8");
		FriendsService service = new FriendsService(req, resp);
	
		String msgMsg = "";	
		
		//String loginemail = (String) req.getSession().getAttribute("loginemail");
		
		switch (addr) {
		
		case ("/friendsAddOverlay"):
			System.out.println("친구 중복 검사 요청...");
			String friends_email = req.getParameter("friends_email");
			//로그인이메일 테스트용
			String loginemail = "test@test";
			//overlay=false; => 중복된 이메일 없음! overlay=true; => 중복된 이메일 있음 
			boolean overlay = service.friendsAddOverlay(loginemail, friends_email);
			if(overlay) {
				msgMsg = "이미 친구등록된 이메일 입니다!";
				System.out.println("이미 친구등록된 이메일!");
				req.setAttribute("msgMsg", msgMsg);
				dis = req.getRequestDispatcher("/friendsList");
				dis.forward(req, resp);
			} else {
				System.out.println("친구등록 진행...");
				req.setAttribute("friends_email", friends_email);
				dis = req.getRequestDispatcher("/friendsAdd");
				dis.forward(req, resp);
			}
			break;
			

		case ("/friendsAdd"):
			System.out.println("친구 추가 요청...");
			friends_email = req.getParameter("friends_email");
			//로그인이메일 테스트용
			loginemail = "test@test";
			int success = service.friendsAdd(loginemail, friends_email);
			if (success>0) {
				msgMsg = "친구등록을 했습니다!";
				System.out.println("친구등록 성공!");
			} else {
				msgMsg = "친구등록을 실패했습니다!";
				System.out.println("친구등록 실패!");
			}
			req.setAttribute("msgMsg", msgMsg);
			dis = req.getRequestDispatcher("/friendsList");
			dis.forward(req, resp);
			break;

		case ("/friendsDel") :
			System.out.println("친구 삭제 요청...");
			friends_email = req.getParameter("friends_email");
			//로그인이메일 테스트용
			loginemail = "test@test";
			success = service.friendsDel(loginemail, friends_email);
			if (success>0) {
				msgMsg = "친구를 삭제 했습니다!";
				System.out.println("친구를 삭제 했습니다!");
			} else {
				msgMsg = "친구 삭제를 실패했습니다!";
				System.out.println("친구 삭제를 실패했습니다!");
			}
			req.setAttribute("msgMsg", msgMsg);
			dis = req.getRequestDispatcher("/friendsList");
			dis.forward(req, resp);
			break;
		
		case ("/friendsList"):
			System.out.println("친구 리스트 요청...");
			//임시로 로그인 아이디 지정함, 테스트용
			loginemail = "test@test";
			req.setAttribute("friendsList",service.friendsList(loginemail));
			dis = req.getRequestDispatcher("friendsTest.jsp");
			dis.forward(req, resp);
			break;

		case ("/friendsBlock"):
			System.out.println("친구 차단 요청...");
			friends_email = req.getParameter("friends_email");
			//로그인이메일 테스트용
			loginemail = "test@test";
			success = service.friendsBlock(loginemail, friends_email);
			if (success>0) {
				msgMsg = "친구를 차단했습니다!";
				System.out.println("친구 차단 성공!");
			} else {
				msgMsg = "친구 차단을 실패했습니다!";
				System.out.println("친구 차단 실패!");
			}
			req.setAttribute("msgMsg", msgMsg);
			dis = req.getRequestDispatcher("/friendsList");
			dis.forward(req, resp);
			break;
			
			
		case ("/friendsBlockCancle"):
			System.out.println("친구 차단 해제 요청...");
			friends_email = req.getParameter("friends_email");
			//로그인이메일 테스트용
			loginemail = "test@test";
			success = service.friendsBlockCancle(loginemail, friends_email);
			if (success>0) {
				msgMsg = "친구 차단을 해제했습니다!";
				System.out.println("친구 차단 해제 성공!");
			} else {
				msgMsg = "친구 차단 해제를 실패했습니다!";
				System.out.println("친구 차단 해제 실패!");
			}
			req.setAttribute("msgMsg", msgMsg);
			dis = req.getRequestDispatcher("/friendsList");
			dis.forward(req, resp);
			break;
			
		case ("/friendsRecomend"):
			System.out.println("친구 추천 리스트 요청...");
			loginemail = "test@test";
			req.setAttribute("recomendList", service.friendsRecomend(loginemail));
			req.setAttribute("loginemail", loginemail);
			dis = req.getRequestDispatcher("friendsTest.jsp");
			dis.forward(req, resp);
			break;
			
		} //case end
		
		
	} //dual end
	

} //class end


