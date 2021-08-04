<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="css/common.css" type="text/css">
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

#main {
   float :left;
   width:950px;
   height:600px;
   text-align:center;
   vertical-align:middle;
   overflow:auto;
   
}
</style>
</head>
<body>
	<!-- 상단 메뉴바 -->
<c:import url="./view/topmenu.jsp"/>
	<!-- 내용시작 -->
  <table>
      <tr>
       <th>피드 번호</th>
      <th>마커 번호</th>
       <th>작성자</th>
      <th>작성일</th>
      <th>발자국 내용</th>
      </tr>
      <c:if test="${feedlist eq null || feedlist eq ''}">
       <tr><td colspan="5">해당 데이터가 존재하지 않습니다.</td></tr>
    </c:if>
   
   <c:forEach items="${feedlist}" var="footprint" varStatus = "no">
   <tr>
      <%-- <td>${footprint.boardNO}</td> --%>
      <td>${fn:length(feedlist) - no.index}</td>
      <td>${footprint.markerNO}</td>
      <td>${footprint.email}</td>
      <td>${footprint.reg_date}</td>
      <td><a href="fpdetail?footPrintNO=${footprint.footPrintNO}">${footprint.footprintText}</a></td>
   </tr>
   </c:forEach>
   </table>
   
   <button onclick="location.href='fpwrite.jsp'">발자국 글 쓰기</button>
   
   
</body>
</html>