<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<style>
	table,tr,th,td{
		border: 1px solid;
		border-collapse: collapse;
		padding : 10px;
	}
</style>
</head>
<body>
	<!-- 상단 메뉴바 -->
<c:import url="./view/topmenu.jsp"/>
	<!-- 내용시작 -->
	<form action="msgWrite" method="post">
		<table>
				<tr>
					<th>보내는 사람</th>
					<td>
					${loginemail}
					<input type="hidden" name="sender" value="${loginemail}"/>
					</td>
					
				</tr>
				<tr>
					<th>받는 사람</th>
					<td><input type="text" name="reciever"/></td>
				</tr>
				<tr>
					<th>메세지 내용</th>
					<td><textarea name="content"></textarea></td>
				</tr>
				<tr>
					<td colspan="2">
					<button>메세지 전송</button>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" onclick="location.href='./msgMain'" value="메세지 메인" />
				<tr>	
		</table>
	</form>
</body>
<script>
	var msgMsg = "${msgMsg}";
	if(msgMsg != ""){
		alert(msgMsg);
	}
	
	//로그인한 아이디 가져오기!!!
	var loginemail = "<%= session.getAttribute("loginemail") %>"
</script>
</html>