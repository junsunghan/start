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
	<div class="list-group col-md-2 m-1 text-center">
		<form action="uploadphoto" method="post" enctype="multipart/form-data" style="width:242px; border: 1px solid gray; text-align :center;">
			<img src="/photo/${info.newName}" width="230px" height="230px" class="mt-1"/><br/>
			<label for="photo">사진 선택</label>
			 <input type="file" id="photo" name="photo" style="display:none;"/>
			 <button class="mt-1 mb-1" id="up">업로드</button>
		</form>
  <a href="memberInfo" class="list-group-item list-group-item-action list-group-item-light" >개인정보</a>
  <a href="#" class="list-group-item list-group-item-action list-group-item-light">친구목록</a>
  <a href="#" class="list-group-item list-group-item-action list-group-item-light">차단목록</a>
  <a href="#" class="list-group-item list-group-item-action list-group-item-light">내가 쓴글</a>
  <a href="cancel.jsp" class="list-group-item list-group-item-action list-group-item-light" >회원탈퇴</a>
</div>
<div class="col-6 center-block" style="margin:100 auto;">
<h2>회원 정보</h2>
	<form action="memberUpdateForm" method="post">
		<table class="table table-bordered text-center">
		<c:if test="${info eq null}">
			<tr><td colspan="2">요청하신 데이터가 없습니다.</td></tr>
		</c:if>
		<c:if test ="${info ne null }">
			<tr>
				<td>EMAIL</td>
				<td>${info.email }</td>
			</tr>
			<tr>
				<td>NICKNAME</td>
				<td>${info.nickname }</td>
			</tr>
			<tr>
				<td>PW</td>
				<td>보안상 비공개</td>
			</tr>
			<tr>
				<td>NAME</td>
				<td>${info.name }</td>
			</tr>
			<tr>
				<td>GENDER</td>
				<td>${info.gender }		
				</td>
			</tr>
			<tr>
				<td>BIRTH</td>
				<td>${info.birth }</td>
			</tr>
			<tr>
				<td>PHONE</td>
				<td>${info.phone }</td>
			</tr>
			<tr>
				<td>STYLE</td>
				<td>${info.tourstyle }
				</td>
			</tr>
			</c:if>
			<tr>
				<td colspan="2">
				<button>개인정보 수정</button>
				</td>
			</tr>
		</table>
	</form>
	</div>
</div>
</body>
<script>
var msg = "${msg}";
if(msg!=""){
	alert(msg);
}
$("#up").hide();
$('#photo').change(function(){
	console.log("실행")
    $('#up').show();
})


</script>
</html>