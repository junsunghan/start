<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
#main {
   float :left;
   width:950px;
   height:600px;
   text-align:center;
   vertical-align:middle;
   overflow:auto;
   
}
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<link rel="stylesheet" href="css/common.css" type="text/css">
</head>
<body>
 	<!-- 상단 메뉴바 -->
<c:import url="./view/topmenu.jsp"/>
	<!-- 내용시작 -->


<table>
      <tr>
       <th>발자국 번호</th>
      <th>발자국 내용</th>
      <th>해시태그</th>
      </tr>
      <c:if test="${hashtaglist eq null || hashtaglist eq ''}">
       <tr><td colspan="5">해당 데이터가 존재하지 않습니다.</td></tr>
    </c:if>
   
   <c:forEach items="${hashtaglist}" var="hashtag" >
   <tr>
      <%-- <td>${footprint.boardNO}</td> --%>
      <td>${hashtag.footPrintNO}</td>
      <td><a href="fpdetail?footPrintNO=${hashtag.footPrintNO}">${hashtag.footprintText}</a></td>
      <td>${hashtag.hashTag}</td>
   </tr>
   </c:forEach>
   </table>

  <form action="fpsearch" method="post"  enctype="multipart/form-data">  
     
  

  
   
  </form>
</body>


</html>