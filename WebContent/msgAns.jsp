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
<h3> 답장 보내기 폼 </h3>
	<form action="msgWrite" method="post">
		<table>
				<tr>
					<th>보내는 사람</th>
					<td>
					${loginemail}
					<input type="hidden" name="sender" value='${loginemail}'/>
					</td>
				</tr>
				<tr>
					<th>받는 사람</th>
					<td>
					${sender_email}
					<input type="hidden" name="reciever" value='${sender_email}'/>
					</td>
				</tr>
				<tr>
					<th>메세지 내용</th>
					<td><textarea name="content"></textarea></td>
				</tr>
				<tr>
					<td colspan="2">
					<button>답장 전송하기</button>
					&nbsp;&nbsp;&nbsp;&nbsp;
					<input type="button" onclick="location.href='./msgMain'" value="메세지 메인" />
				<tr>	
		</table>
	</form>
</body>
<script>
	$('#reciever').val('${sender_email}');

	var msgMsg = "${msgMsg}";
	if(msgMsg != ""){
		alert(msgMsg);
	}
</script>
</html>