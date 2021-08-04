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
	<form action="msgReport" method="post">
		<table>
				<tr>
					<th>신고할 메세지 번호</th>
					<td>
					${msgDetail.msgNo}
					<input type="hidden" name="msgNo" value="${msgDetail.msgNo}"/>
					</td>
				</tr>
				<tr>
					<th>신고할 이메일</th>
					<td>
					${msgDetail.sender_email}
					<input type="hidden" name="sender_email" value="${msgDetail.sender_email}"/>
					</td>
				</tr>
				<tr>
					<th>메세지 사유</th>
					<td><textarea name="reportContent"></textarea></td>
				</tr>
				<tr>
					<td colspan="2">
					<button>메세지 신고</button>
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
	
	var loginemail = "<%= session.getAttribute("loginemail") %>"
</script>
</html>