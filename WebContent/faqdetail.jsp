<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="css/common.css" type="text/css">
</head>
<body>
	<!-- 상단 메뉴바 -->
<c:import url="./view/topmenu.jsp"/>
	<!-- 내용시작 -->
	<h2>faq 상세보기</h2>	
	<table>
		<tr>
			<th>글번호</th>
			<td>${noticefaq.idx}</td>
		</tr>
		<tr>
			<th>제목</th>
			<td>${noticefaq.title}</td>
		</tr>
		<tr>
			<th>작성자</th>
			<td>${noticefaq.email}</td>
		</tr>
		<tr>
			<th>작성일</th>
			<td>${noticefaq.reg_date}</td>
		</tr>
		<tr>
			<th>내용</th>
			<td>${noticefaq.content}</td>
		</tr>
		<tr>
			<td colspan="2">
				<button onclick="location.href='./faqlist'">리스트</button>
				<button onclick="location.href='./faqupdateForm?idx=${noticefaq.idx}'">수정</button>
				<button onclick="location.href='./faqdel?idx=${noticefaq.idx}'">삭제</button>			
			</td>
		</tr>
	</table>
</body>
<script>
	var msg = "${msg}";
	if(msg != ""){
		alert(msg);
	}
</script>
</html>