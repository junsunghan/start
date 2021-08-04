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
<form action="noticeupdate" method="POST">
	<table>
		<tr>
			<th>글번호</th>
			<td>
				${noticefaq.idx}
				<input type="hidden" name="idx" value="${noticefaq.idx}"/>
			</td>
		</tr>
		<tr>
			<th>제목</th>
			<td><input type="text" name="title" value="${noticefaq.title}"/></td>
		</tr>
		<tr>
			<th>작성자</th>
			<td><input type="text" name="email" value="${noticefaq.email}"/></td>
		</tr>
		<tr>
			<th>내용</th>
			<td><textarea name="content">${noticefaq.content}</textarea></td>
		</tr>
		<tr>
			<td colspan="2">
				<input type="button" onclick="location.href='./noticelist'" value="리스트"/>
				<button>저장</button>
			</td>
		</tr>
	</table>
</form>
</body>
<script>
	var msg = "${msg}";
	if(msg != ""){
		alert(msg);
	}
</script>
</html>