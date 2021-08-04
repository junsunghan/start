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
	
		li{
   	   	list-style:none;
   		float: left;
   		margin-left: 10px;
   }
</style>
</head>


<c:set var="searchKey" value="${searchKey}" />

<c:choose>

<c:when test="${searchKey eq ''}">

<h3>검색할 이메일을 입력해주세요!</h3>
<br/>
<input type="button" onclick="location.href='./msgMain'" value="메세지 메인" />
</c:when>


<c:when test="${searchkey ne ''}">
<body>

	<h3>로그인한 회원: ${loginemail}</h3>
	<h3>"${searchKey}" 로 검색한 결과...</h3>
		
<table>
		<tr>
			<th></th>
			<th>보낸 사람</th>
			<th>메세지 내용</th>
			<th>받은날짜</th>
			<th>읽음 상태</th>
		</tr>
		
			<c:if test="${map eq null || map eq ''}">
			<tr>
				<td colspan="5"> 검색 결과가 없어요ㅠ </td>
			</tr>
		</c:if>
		
		<c:forEach items="${map.emailList}" var="msges">
			<tr>
				<td><input type="checkbox" value='${msges.msgNo}'/></td>
				<td>${msges.sender_email}</td>
				<td><a href="msgDetail?msgNo=${msges.msgNo}">${msges.msgContent}</a></td>
				<td>${msges.reg_date}</td>
				
				<c:if test="${msges.msgOpen eq  '1'}">
				<td>읽음</td>	
				</c:if>
				<c:if test="${msges.msgOpen eq  '0'}">
				<td>읽지 않음</td>
				</c:if>
				
			</tr>
		</c:forEach>
</table>
	
	<br/>
	
	<nav>
			<ul class="pagination">
				<c:if test="${map.startPage ne 1}">
				<li class="page-item"><a class="page-link" href="./msgSearch?page=${map.startPage-1}&searchKey=${searchKey}"
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>		
				</a></li>
				</c:if>
				<c:forEach var="i" begin="${map.startPage}" end="${map.endPage}">
				<c:if test="${i ne map.currPage}">
				<li class="page-item"><a class="page-link" href="./msgSearch?page=${i}&searchKey=${searchKey}">${i}</a></li>
				</c:if>
				<c:if test="${i eq map.currPage}">
				<li class="page-item active"><a class="page-link" href="./msgSearch?page=${i}&searchKey=${searchKey}">${i}</a></li>
				</c:if>
				</c:forEach>
				<c:if test="${map.totalPage ne map.endPage}">
				<li class="page-item"><a class="page-link" href="./msgSearch?page=${map.endPage+1}&searchKey=${searchKey}"
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
				</c:if>
			</ul>
	</nav>
	
	<br/>
	<br/>
	
	<input type="button" onclick="location.href='./msgMain'" value="메세지 메인" />
</body>

</c:when>
</c:choose>
</html>