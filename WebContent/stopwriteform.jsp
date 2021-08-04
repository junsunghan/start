<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
	<!-- 상단 메뉴바 -->
<c:import url="./view/topmenu.jsp"/>
	<!-- 내용시작 -->
	<h2>정지FORM</h2>
	<form method="POST" action="stopregister">
	<table>
		<tr>
			<th>이메일</th>
			<td><input name="email" type="text" value=${member.email}></td>
		</tr>
		<tr>
			<th>이름</th>
			<td>${member.name}</td>
		</tr>
		<tr>
			<th>사유</th>
			<td><input type="text" name="reason"/></td>
		</tr>
		<tr>
			<td colspan="2">
			<button>수정</button>
			<button onclick="location.href='memberlist.jsp'">뒤로가기</button>
			</td>
		</tr>
	</table>
	</form>
</body>
<script>

</script>
</html>