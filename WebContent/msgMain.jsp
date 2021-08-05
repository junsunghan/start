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
<body>
	<!-- 상단 메뉴바 -->
<c:import url="./view/topmenu.jsp"/>
	<!-- 내용시작 -->
	<h3>${loginemail} 님의 메세지 받은 메세지화면 입니다</h3>
		<input type="button" onclick="location.href='msgWrite.jsp'" value="메세지 쓰기" />
		<button onclick="location.href='./msgMyMsg'">내가 보낸 메세지</button>
		<button onclick="del()">메세지 삭제</button>
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
				<td colspan="5"> 받은 메세지가 없어요ㅠ </td>
			</tr>
		</c:if>
		
		<c:forEach items="${map.msgList}" var="msges">
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
				<li class="page-item"><a class="page-link" href="./msgList?page=${map.startPage-1}"
					aria-label="Previous"> <span aria-hidden="true">&laquo;</span>		
				</a></li>
				</c:if>
				<c:forEach var="i" begin="${map.startPage}" end="${map.endPage}">
				<c:if test="${i ne map.currPage}">
				<li class="page-item"><a class="page-link" href="./msgList?page=${i}">${i}</a></li>
				</c:if>
				<c:if test="${i eq map.currPage}">
				<li class="page-item active"><a class="page-link" href="./msgList?page=${i}">${i}</a></li>
				</c:if>
				</c:forEach>
				<c:if test="${map.totalPage ne map.endPage}">
				<li class="page-item"><a class="page-link" href="./msgList?page=${map.endPage+1}"
					aria-label="Next"> <span aria-hidden="true">&raquo;</span>
				</a></li>
				</c:if>
			</ul>
	</nav>
	
	<br/>
	<br/>
	
<form class="d-inline-flex justify-content-end" style="height: 25px;" action="msgSearch" method="post">
	<input class="form-control me-1" type="search" placeholder="보낸 이메일을 검색" aria-label="Search" name="searchKey" />
			<button class="btn btn-outline-secondary" type="submit">search</button>
</form>

</body>
<script>
	var msgMsg = "${msgMsg}";
	if(msgMsg != ""){
		alert(msgMsg);
	}
	
	function del(){
		var $chk =$("input[type='checkbox']:checked");
		if($chk.length>0){
			
			var chkArr = [];
			
			$chk.each(function(msgNo,msges){ // msges 의 msgNo 을 꺼낸다
				chkArr.push($(this).val());
			});
			
			$.ajax({
				type:'get',
				url:'./msgArrDel', //여기로 요청을 보내서~
				data:{'delList':chkArr},
				dataType:'JSON',
				success:function(data){ // 요청을 결과 data 를 성공적으로 받았다면~
					console.log(data);
					if(data.cnt>0){
						alert(data.cnt+'개의 메세지 삭제를 성공했습니다.');
						location.href='./msgMain';
					}else{
						alert('메세지 삭제를 실패 했습니다.');
					}
				},
				error:function(e){
					console.log(e);
				}
			});		
		}else{
			alert("삭제할 메세지를 선택해 주세요~");
		}
	}
	
</script>
</html>