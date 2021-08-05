<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<style type="text/css">
body, head {
	top: 0;
	bottom: 0;
	margin: 0;
	padding: 0;
}
</style>
</head>
<body>
	<!-- 상단 메뉴바 -->
<c:import url="./view/topmenu.jsp"/>
	<!-- 내용시작 -->
	<div class="container px-4 my-4">
		<div class="row">
			<div class="col-6">
				<h2>지도</h2>
				<hr />
				내용 들어감
			</div>
			<div class="col-6">
				<h2><a href="fplist">플래너시작</a></h2>
				<hr />
				내용 들어감
			</div>
		</div>
		<div class="row row-cols-1 row-cols-md-4 g-4 mt-4">
		 <c:if test="${feedlist eq null || feedlist eq ''}">
     <h1>해당 데이터가 존재하지 않습니다.</h1>
   	 </c:if>	
   	 
   	 <c:forEach items="${feedlist}" var="footprint" varStatus = "no">
   <%-- <tr>
      <td>${footprint.boardNO}</td>
      <td>${fn:length(feedlist) - no.index}</td>
      <td>${footprint.markerNO}</td>
      <td>${footprint.email}</td>
      <td>${footprint.reg_date}</td>
      <td><a href="fpdetail?footPrintNO=${footprint.footPrintNO}">${footprint.footprintText}</a></td>
   </tr> --%>
			<div class="col">
				<div class="card">
					<h4>${footprint.boardNO}</h4>
					<div class="card-body">
						<h5 class="card-title">작성자 : ${footprint.email} </h5>
						<p class="card-text">내용이 보고싶나?</p>
					</div>
					<div class="card-footer text-center">
						<button class="btn btn-primary" onclick="location.href='fpdetail?footPrintNO=${footprint.footPrintNO}'">자세히 보기</button>
					</div>
				</div>
			</div>
   </c:forEach>
		</div>
	</div>
<!-- 하단단 메뉴바 -->
<c:import url="./view/bottom.jsp"/>
</body>
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script>
var success = "${success}";
if(success == "true") {
	alert("로그인 성공");
	location.href="main.jsp";
}
if(success == "false") {
	alert("이메일 및 비밀번호를 확인해주세요");
	location.href="login.jsp";
}
</script>

</html>