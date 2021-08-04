<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style>
.controller {
	padding: 25px 0;
	margin: auto;
	width: 840px;
	text-align: center;
}

table {
	width: 840px;
	padding: 10px 0;
	border-collapse: collapse;
}

th {
	background-color: rgb(100, 100, 100);
	color: white;
}

button {
	margin: 4px 0;
	padding: 10px 0;
	width: 840px;
	background-color: rgb(255, 80, 80);
	color: white;
	border: none;
}

a {
	text-decoration: none;
	color: black;
}

a:hover {
	text-decoration-line: underline;
}
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="css/common.css" type="text/css">
</head>

<body>
	<!-- 상단 메뉴바 -->
	<c:import url="./view/topmenu.jsp" />
	<!-- 내용시작 -->
	<form action="fpupdate" method="post" enctype="multipart/form-data">

		<table>
			<tr>
				<th>발자국 번호</th>
				<td>${footprint.footPrintNO}<input type="hidden"
					name="footPrintNO" value="${footprint.footPrintNO}" />
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td><textarea name="footprintText">${footprint.footprintText}</textarea></td>
			</tr>
			<tr>
				<th>사진</th>
				<td><img src="/photo/${footprint.newFileName}" width="500px" />
					<p>
						<input type="file" name="PostPic" />
					</p></td>
			</tr>

			<tr>
				<td colspan="2">
				<input type="radio" name="ok" value="1"<c:if test ="${footprint.release eq '1'.charAt(0)}">checked</c:if> />발자국 공개 &nbsp;&nbsp;
				<input type="radio" name="ok" value="0" <c:if test ="${footprint.release eq '0'.charAt(0)}">checked</c:if> />발자국 비공개
				<br />
				<input type="button"	onclick="location.href='./'" value="발자국 리스트" />
					<button>저장</button></td>
			</tr>
		</table>
	</form>
</body>
</html>