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
 	<h3>친구등록 테스트</h3>
 	
 		<form action="./friendsList">
 			<button>내 친구 리스트 보기</button>
 		</form>
 	
 		<form action="./friendsAddOverlay">
 			<input type="text" name="friends_email" />
 			<button>친구 추가</button>
 		</form>
 		
 		<form action="./friendsDel">
 			<input type="text" name="friends_email" />
 			<button>친구 삭제</button>
 		</form>
 		
 		<table>
 			<tr>
 				<th>나</th>
 				<th>친구</th>
 				<th>차단여부</th>
 			</tr>
 			<c:if test="${friendsList eq null || friendsList eq ''}">
 				<tr><td colspan="3">친구가 없어요ㅠㅠ</td></tr>
 			</c:if>
 			<c:forEach items="${friendsList}" var="friend">
 				<tr>
 					<td>${friend.email}</td>
 					<td>${friend.friends_email}</td>
 					<td>${friend.block}</td>
 				</tr>
 			</c:forEach>
 		</table>

 		
 		<form action="./friendsBlock">
 			<input type="text" name="friends_email" />
 			<button>친구 차단</button>
 		</form>
 		
 		<form action="./friendsBlockCancle">
 			<input type="text" name="friends_email" />
 			<button>친구 차단 해제</button>
 		</form>
 		
 		
 		<form action="./friendsRecomend">
 			<button>추천 친구 보기</button>
 		</form>
 		
 		<table>
 			<tr>
 				<th>추천 친구</th>
 			</tr>
 			<c:if test="${recomendList eq null || recomendList eq ''}">
 				<tr><td>여행스타일을 등록해주세요!</td></tr>
 			</c:if>
 			<c:forEach items="${recomendList}" var="friend">
 				<tr>
 					<td>${friend.email}</td>
 				</tr>
 			</c:forEach>
 		</table>
 		
 		
</body>
<script>
var msgMsg = "${msgMsg}";
if(msgMsg != ""){
	alert(msgMsg);
}
</script>
</html>