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
		text-align: center;
	}
</style>
</head>
<body>
	<!-- 상단 메뉴바 -->
<c:import url="./view/topmenu.jsp"/>
	<!-- 내용시작 -->
<table>
		<tr>
			<th></th>
			<th>받은 사람</th>
			<th>메세지 내용</th>
			<th>받은날짜</th>
			<th>읽음 상태</th>
		</tr>
		
		<c:if test="${searchlist eq null || searchlist eq ''}">
			<tr>
				<td colspan="5"> 검색 결과가 없어요ㅠ </td>
			</tr>
		</c:if>
		
		<c:forEach items="${searchlist}" var="qnas">
			<tr>
				<td><input type="checkbox" value='${qnas.qnano}'/></td>
				
				<td>${qnas.email}</td>
				<td><a href="qnadetail?qnano=${qnas.qnano}">${qnas.content}</a></td>
				<td>${qnas.reg_date}</td>
				
								
			</tr>
		</c:forEach>
	</table>
	
	<br/>

</body>
</html>