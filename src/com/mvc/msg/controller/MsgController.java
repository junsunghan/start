package com.mvc.msg.controller;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.msg.service.MsgService;

@WebServlet({"/msgWrite","/msgMain","/msgList","/msgDetail","/msgDel","/msgAns","/msgMyMsgDetail",
					"/msgArrDel","/msgMyMsg","/msgReport","/msgReportWrite", "/msgSearch"})

public class MsgController extends HttpServlet {

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
		MsgService service = new MsgService(req, resp);
		
		String msgMsg = "";
		
		switch (addr) {
		
		//msgMain 으로 요청으로 보내면 메세지 리스트 갱신
		case ("/msgMain"):
			System.out.println("메세지 메인 요청");
			dis = req.getRequestDispatcher("/msgList");
			dis.forward(req, resp);
			break;
			
		//메세지 보내기 기능	
		case ("/msgWrite"): 
			System.out.println("메세지 쓰기 요청");
			if(service.write() > 0) {
				//메세지 메인으로
				msgMsg = "메세지 전송 성공";
				req.setAttribute("msgMsg", msgMsg);
				dis = req.getRequestDispatcher("/msgMain");
				dis.forward(req, resp);
			} else {
				msgMsg = "경고!! 메세지를 받을사람 또는 메세지를 받는사람을 확인해주세요!!";
				req.setAttribute("msgMsg", msgMsg);
				dis = req.getRequestDispatcher("msgWrite.jsp");
				dis.forward(req, resp);
			}
			break;
		
		//나한테 온 메세지만 보기
		case ("/msgList"): //로그인한 회원이, 자기한테 온 메세지를 모아보는 기능임!
			System.out.println("메세지 리스트 요청");
			String loginemail = (String)req.getSession().getAttribute("loginemail");
			System.out.println("현재 loginemail: "+loginemail);
			req.setAttribute("map", service.msgList(loginemail));
			req.setAttribute("loginemail", loginemail);
			dis = req.getRequestDispatcher("msgMain.jsp");
			dis.forward(req, resp);
			break;	
	
		//내가보낸 메시지만 보기
		case ("/msgMyMsg"): //로그인한 회원이, 자기한테 온 메세지를 모아보는 기능임!
			System.out.println("내가 보낸 메세지 리스트 요청");
			loginemail = (String)req.getSession().getAttribute("loginemail");
			System.out.println("현재 loginemail: "+loginemail);
			req.setAttribute("map",service.msgMyMsg(loginemail));
			req.setAttribute("loginemail", loginemail);
			dis = req.getRequestDispatcher("msgMyMsg.jsp");
			dis.forward(req, resp);
			break;		
			
		//메세지 내용 상세보기	
		case ("/msgDetail"):
			System.out.println("메세지 상세보기 요청");
			req.setAttribute("msgDetail", service.msgDetail());
			dis = req.getRequestDispatcher("msgDetail.jsp");
			dis.forward(req, resp);
			break;	
			
		case ("/msgMyMsgDetail"):
			System.out.println("메세지 상세보기 요청");
			req.setAttribute("msgDetail", service.msgDetail());
			dis = req.getRequestDispatcher("msgMyMsgDetail.jsp");
			dis.forward(req, resp);
			break;	
			
		//메세지 삭제	
		case ("/msgDel"):
			System.out.println("메세지 삭제 요청");
			if(service.msgDel() > 0) {
				//메세지 리스트를 갱신하면서 메세지 메인으로
				msgMsg = "메세지 삭제 성공";
				req.setAttribute("msgMsg", msgMsg);
				dis = req.getRequestDispatcher("/msgList");
				dis.forward(req, resp);
			} else {
				msgMsg = "메세지 삭제 실패!!";
				req.setAttribute("msgMsg", msgMsg);
				dis = req.getRequestDispatcher("msgDetail.jsp");
				dis.forward(req, resp);
			}
			break;
			
		//체크박스 선택으로 여러개 지우기
		case ("/msgArrDel"):
			System.out.println("메세지 선택삭제 요청");
			String[] delList = req.getParameterValues("delList[]");
			System.out.println("삭제할 개수: "+ delList.length);
			service.msgArrDel(delList);
			break;	
			
		//메세지 답장보내기
		case ("/msgAns"):
			System.out.println("답장 보내기 요청");
			req.setAttribute("sender_email", req.getParameter("sender_email"));
			loginemail = (String)req.getSession().getAttribute("loginemail");
			req.setAttribute("loginemail", loginemail);
			dis = req.getRequestDispatcher("msgAns.jsp");
			dis.forward(req, resp);
			break;
		
		case ("/msgReportWrite"):
			System.out.println("메세지 신고폼 요청...");
			req.setAttribute("msgDetail", service.msgDetail());
			dis = req.getRequestDispatcher("msgReportWrite.jsp");
			dis.forward(req, resp);
			break;
			
		case ("/msgReport"): 
			System.out.println("메세지 신고 등록 요청");
			int success = 0;
			//신고넘버, 메세지넘버, 등록일, 신고내용, 피신고자 이메일
			success = service.msgReport();
			if(success >0) {
				System.out.println("신고 완료...");
				msgMsg = "메세지를 신고했습니다!";
			} else {
				System.out.println("신고 실패...");
				msgMsg = "메세지 신고를 실패했습니다! 재시도 해주세요!(이미 신고된 메세지)";
			}
			req.setAttribute("msgMsg",msgMsg);
			dis = req.getRequestDispatcher("/msgList");
			dis.forward(req, resp);
			break;
			
		//나한테 편지보낸사람 검색
		case ("/msgSearch"):
			System.out.println("메세지 검색 요청...");
			loginemail = (String)req.getSession().getAttribute("loginemail");
			System.out.println("로그인 이메일:" + loginemail);
			String searchKey = req.getParameter("searchKey");//검색어
			System.out.println("검색어: "+ searchKey);
			
			req.setAttribute("map", service.emailList(loginemail, searchKey));
			req.setAttribute("loginemail", loginemail);
			req.setAttribute("searchKey", searchKey);
			
			dis = req.getRequestDispatcher("msgSearch.jsp");
			dis.forward(req, resp);
			break;	
				
		}	// switch end
		
		
	}
}
