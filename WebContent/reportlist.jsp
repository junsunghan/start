<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<style>
table, th, td {
	border: 1px solid;
	border-collapse: collapse;
	margin: 10px;
}
</style>
</head>
<body>
	<!-- 상단 메뉴바 -->
<c:import url="./view/topmenu.jsp"/>
	<!-- 내용시작 -->
<h2>신고 리스트</h2>
		<table>
			<thead>
				<tr>
					<td><input class='search' type="text" name='email'/></td>
					<td><button class="btn">검색</button></td>
				</tr>
			</thead>
		</table>
	<form method="GET" action="memberdetail.jsp">
	<table>
		<thead>
			<tr>
				<th>이메일</th>
				<th>닉네임</th>
				<th>상세보기</th>
			</tr>
		</thead>
		<tbody></tbody>
	</table>
	</form>
</body>
<script>
	listCall();
	function listCall() {
		$.ajax({
			type : 'get',
			url : 'memberlist',
			data : {},
			dataType : 'JSON',
			success : function(data) {
				console.log(data);
				drawList(data.list);
			},
			error : function(e) {
				console.log(e);
			}
		});
	}

	//회원 리스트 출력 함수
	function drawList(list) {
		//console.log(list);
		var content = "";
		list.forEach(function(item, idx) {
			console.log(item, idx);
			content += "<tr>";
			content += "<td>" + item.email + "</td>";
			content += "<td>" + item.name + "</td>";
			//content += "<td><button onclick='location.href="+"memberdetail?email="+item.email+"'>상세보기</button></td>";
			content += "<td><a href='memberdetail?email="+item.email+"'>상세보기</a></td>";
			content += "</tr>";
		});
		$("tbody").empty();
		$("tbody").append(content);
	}

// 검색 함수

console.log($('.btn'));
 $('.btn').click(function(){
	 var param = {};
	 param.email = $("input[class='search']").val();
	 console.log(param);
	 $.ajax({
			type:'POST',
			url:'membersearch',
			data:param,
			dataType:'JSON',
			success:function(data){
				console.log(data);
					console.log(data);
					searchList(data.list);
			},
			error:function(e){
				console.log(e);
			}
		});
 });
 
 function searchList(list) {
		console.log(list);
		var content = "";
		list.forEach(function(item, idx) {
			console.log(item, idx);
			content += "<tr>";
			content += "<td>" + item.email + "</td>";
			content += "<td>" + item.name + "</td>";
			content += "<td><button onclick='location.href="+"detail?email="+item.email+"'>상세보기</button></td>";
			content += "</tr>";
		});
		$("tbody").empty();
		$("tbody").append(content);
	}
</script>
</html>