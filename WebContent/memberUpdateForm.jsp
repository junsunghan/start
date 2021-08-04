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
  <a href="memberInfo" class="list-group-item list-group-item-action" >개인정보</a>
  <a href="#" class="list-group-item list-group-item-action">친구목록</a>
  <a href="#" class="list-group-item list-group-item-action">차단목록</a>
  <a href="#" class="list-group-item list-group-item-action">내가 쓴글</a>
  <a href="#" class="list-group-item list-group-item-action" >회원탈퇴</a>
</div>
<div class="col-6 center-block" style="margin:100 auto;">
<h2>회원 정보</h2>
	<form action="memberUpdate" method="post">
		<table class="table table-bordered text-center">
		<c:if test="${info eq null}">
			<tr><td colspan="2">요청하신 데이터가 없습니다.</td></tr>
		</c:if>
		<c:if test ="${info ne null }">
			<tr>
				<td>EMAIL</td>
				<td>${info.email }
				<input type="hidden" name="email" value="${info.email}"/></td>
			</tr>
			<tr>
				<td>NICKNAME</td>
				<td><input type="text" name="nickname" value="${info.nickname }" required/></td>
			</tr>
			<tr>
				<td>PW</td>
				<td><input type="password" name="pw" value="${info.pw }" required/></td>
			</tr>
			<tr>
				<td>NAME</td>
			<td><input type="text" name="name" value="${info.name }" required/></td>
			</tr>
			<tr>
				<td>GENDER</td>
				<td>
				<input type="radio" name="gender" value="남" <c:if test ="${info.gender eq '남'}">checked</c:if>/>남
				&nbsp;&nbsp;&nbsp;
				<input type="radio" name="gender" value="여" <c:if test ="${info.gender eq '여'}">checked</c:if>/>여
				</td>	
			</tr>
			<tr>
				<td>BIRTH</td>
				<td><input type="text" name="birth" id="birth" maxlength="8" value="${info.birth }"   required/></td>
			</tr>
			<tr>
				<td>PHONE</td>
				<td><input type="text" name="phone" maxlength="12" value="${info.phone }" required/></td>
			</tr>
			<tr>
				<td>STYLE</td>
				<td>
				<input type="radio" name="style" value="1" <c:if test ="${info.tourstyle eq '식도락'}">checked</c:if> />식도락
				&nbsp;
				<input type="radio" name="style" value="2" <c:if test ="${info.tourstyle eq '레포츠'}">checked</c:if>/>레포츠
				&nbsp;
				<br/>
				<input type="radio" name="style" value="3" <c:if test ="${info.tourstyle eq '문화'}">checked</c:if> />문화
				&nbsp;&nbsp;
				<input type="radio" name="style" value="4" <c:if test ="${info.tourstyle eq '힐링'}">checked</c:if>/>힐링
				&nbsp;
				<input type="radio" name="style" value="5" <c:if test ="${info.tourstyle eq '호캉스'}">checked</c:if>/>호캉스
				&nbsp;
				</td>
			</tr>
			</c:if>
			<tr>
				<td colspan="2">
				<button>개인정보 수정</button>
				<input type="button" onclick="location.href='memberInfo'" value="취소"/>
				</td>
			</tr>
		</table>
	</form>
	</div>
</div>
</body>
	
</html>