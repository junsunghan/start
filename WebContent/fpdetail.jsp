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
   <h2>상세보기</h2>
   
   <table>
   <tr>
      <th>발자국 번호</th>
      <td>${footprint.footPrintNO}</td>
   </tr>
   <tr>
      <th>마커 번호</th>
      <td>${footprint.markerNO}</td>
   </tr>
   <tr>
      <th>작성자</th>
      <td>${footprint.email}</td>
   </tr>
   <tr>
      <th>작성일</th>
      <td>${footprint.reg_date}</td>
   </tr>
   <tr>
      <th>발자국 내용</th>
      <td>${footprint.footprintText}</td>
   </tr>
  
   <tr>
      <th>사진</th>
      <td><img src="/photo/${footprint.newFileName}" width="500px"></td>
   </tr>
   
   <tr>
      <td colspan="2">
      <button onclick="location.href='./feedlist'">피드</button>
      <c:if test='${sessionScope.loginemail eq footprint.email}'>
      <button onclick="location.href='./fplist'">발자국</button>
      <button onclick="location.href='./fpupdateForm?footPrintNO=${footprint.footPrintNO}'">발자국 수정</button>
      <button onclick="location.href='./fpdel?footPrintNO=${footprint.footPrintNO}'">발자국 삭제</button>
</c:if>
  </td>
   </tr>
   </table>
</body>

</html>