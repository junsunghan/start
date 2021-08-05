<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<style type="text/css">
body, head {
	top: 0;
	bottom: 0;
	margin: 0;
	padding: 0;
}
</style>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
</head>
<body>
	<!-- 상단 메뉴바 -->
<c:import url="./view/topmenu.jsp"/>
	<!-- 내용시작 -->
	<div class="row m-2">
	<div class="list-group col-md-2 m-2">
  <a href="memberInfo" class="list-group-item list-group-item-action list-group-item-light" >개인정보</a>
  <a href="#" class="list-group-item list-group-item-action list-group-item-light">친구목록</a>
  <a href="#" class="list-group-item list-group-item-action list-group-item-light">차단목록</a>
  <a href="#" class="list-group-item list-group-item-action list-group-item-light">내가 쓴글</a>
  <a href="./cancel.jsp" class="list-group-item list-group-item-action list-group-item-light" >회원탈퇴</a>
</div>
<div class="col-6 center-block" style="margin:100 auto;">
<h2>회원 탈퇴</h2>
	<form action="chk" method="post">
	<h3>비밀번호 확인</h3>
		<table class="table table-bordered text-center">
			<tr>
				<td>비밀번호</td>
				<td><input type="password" name="pw" required/></td>
			</tr>
			<tr>
				<td colspan="2">
				<button class="confirm">회원 탈퇴</button>
		<!-- 		<input type="button" onclick="location.href='memberInfo'" value="취소"/> -->
				</td>
			</tr>
		</table>
	</form>
	</div>
</div>
</body>
	<script>
	
	var msg = "${msg}";
	var msgc = "${msgc}";
	if(msg!=""){
	 var rs	= confirm(msg);
		if(rs){
			location.href="cancel";
		}else{
			location.href="cancel.jsp";
		}
	}
		if(msgc!=""){
			alert(msgc);
			location.href="cancel.jsp";
		}

</script>
</html>