package com.mvc.board.controller;



import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mvc.board.service.BoardService;
import com.mvc.board.service.SerachService;
import com.oreilly.servlet.MultipartRequest;




@WebServlet({"/fpsearch","/fplist","/fpwrite","/fpwriteNo","/fpdetail","/fpdel","/fpupdateForm","/fpupdate","/fpserach","/feedlist"})


public class BoardController extends HttpServlet {


	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	dual(req, resp);
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
	dual(req,resp);
		
	
	}

	private void dual(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{
	
		
		
		String uri = req.getRequestURI();
		String ctx = req.getContextPath();
		String addr = uri.substring(ctx.length());
		System.out.println("addr : "+addr);
		
		RequestDispatcher dis = null;
		BoardService service = new BoardService(req);
		String page ="";
		String msg = "";
	    SerachService services = new SerachService();
		
		switch(addr) {
		
		
		  case "/fplist": 
			  System.out.println("발자국 불러오기"); 
			  String email = (String) req.getSession().getAttribute("loginemail");
			  req.setAttribute("fplist",  service.fplist(email));
			  dis = req.getRequestDispatcher("fplist.jsp");
		  dis.forward(req, resp); 
		  break;
		  
		  case "/feedlist":
			  System.out.println("피드 불러오기");
			  req.setAttribute("feedlist", service.feedlist());
			  dis = req.getRequestDispatcher("feedlist.jsp");
			  dis.forward(req, resp);
			  break;
		 
		
		case "/fpwrite":
			System.out.println("발자국 글쓰기 요청");
			email = (String) req.getSession().getAttribute("loginemail");
		    int num = service.fpwriteOk(email);
		   page = num > 0 ? "./fpdetail?footPrintNO="+num:"feedlist.jsp";
		    resp.sendRedirect(page);
		    
		break;
		
	/*
	 * case "/fpwriteNo": System.out.println("발자국 글쓰기 요청"); num =
	 * service.fpwriteNo(); page = num > 0 ?
	 * "./fpdetail?footPrintNO="+num:"fplist.jsp"; resp.sendRedirect(page);
	 * 
	 * break;
	 */
		
		case "/fpdetail":
		System.out.println("발자국 상세보기 요청");
		
		req.setAttribute("footprint", service.fpdetail());
		dis = req.getRequestDispatcher("fpdetail.jsp");
		dis.forward(req, resp);
		break;
		
		
		case "/fpdel":
			System.out.println("삭제 요청");
			service.fpdel();
			resp.sendRedirect("fplist");
			break;
		
		case "/fpupdateForm":
		System.out.println("수정 요청");
		req.setAttribute("footprint", service.fpupdateForm());
		System.out.println("공개 여부 :"+service.fpupdateForm().getRelease());
		dis = req.getRequestDispatcher("fpupdate.jsp");
		dis.forward(req, resp);
		break;
		
		
		case "/fpupdate":
			System.out.println("수정 요청");
			int footPrintNO = service.fpupdate();
			System.out.println("footPrintNO :"+footPrintNO);
			msg ="수정 실패 ㅠㅠ";
			page ="fpupdateForm?footPrintNO="+footPrintNO;
			if(footPrintNO>0) {
				msg="수정 성공!!";
				page ="fpdetail?footPrintNO="+footPrintNO;
			}
			
			req.setAttribute("msg", msg);
			dis = req.getRequestDispatcher(page);
			dis.forward(req, resp);
			
			break;
			
		case "/fpsearch":
			System.out.println("발자국 검색 요청");		
			email = (String)req.getSession().getAttribute("loginemail");
	        String hashtag = req.getParameter("hashtag");// 옵션 키워드
	        System.out.println("해시태그 : "+hashtag);
	        req.setAttribute("hashtaglist", service.hashtaglist(hashtag));
	        dis = req.getRequestDispatcher("fpsearch.jsp");
	        dis.forward(req, resp);
	        
             
			break;
		}		
		
		
		
		
	}

	
}
