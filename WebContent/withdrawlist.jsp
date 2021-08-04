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
div.pageArea{
		margin: 10px;
	}
	
	span.page{
		padding: 2px 10px;
		margin: 5px;
		border: 1px solid gray;
	}
</style>
</head>
<body>
	<!-- 상단 메뉴바 -->
<c:import url="./view/topmenu.jsp"/>
	<!-- 내용시작 -->
<h2>으하하하 탈퇴회원목록이다~@@@@@</h2>
<p>${map.currPage}${map.totalPage}</p>
		<table>
			<thead>
				<tr>
					<td><input class='search' type="text" name='email'/></td>
					<td><button class="btn">검색</button></td>
				</tr>
			</thead>
		</table>
	<table>
		<thead>
			<tr>
				<th>이메일</th>
				<th>닉네임</th>
			</tr>
		</thead>
		<tbody></tbody>
	</table>
	<!-- 페이지를 몇부터 몇까지 보여줄건지 (이전/다음)  -->
	<div class="pageArea">
	
	</div>
</body>
<script>
	var page = 1;
	var n = 1;
	var localList;
	listCall(page);
	function listCall(page) {
		var param = {};
		param.page = page;
		$.ajax({
			type : 'get',
			url : 'withdrawlist',
			data : param,
			dataType : 'JSON',
			success : function(data) {
				console.log(data);
				drawList(data.list);
				pageList(data,n);
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
			content += "</tr>";
		});
		$("tbody").empty();
		$("tbody").append(content);
	}

	//초기 페이징 처리 함수
	function pageList(list,n){
		var content = "";
		console.log("페이징처리 함수옴")
			for(i = n; i<= list.totalPage; i++){
				content += "<span class='page'>";
				if(i != list.currPage){
					console.log("처음 localList.currPage: "+list.currPage);
					content += "<button onclick='listCall("+i+");'>"+i+"</button>";
				}else{
					content += "<b>"+i+"</b>";
				}
				content += "</span>";
				if(i%5==0){
					localList = list;
					content += "<span class='page'>";
					content += "<button onclick='nextList("+i+");'>다음</button>";
					content += "</span>";
					break;
				}
			};
			$("div").empty();
			$("div").append(content);
	}
	
	//다음 페이징 처리함수
	function nextList(a){
		var content = "";
		console.log("다음페이징처리 함수옴")
			for(i = a+1; i<= localList.totalPage; i++){
				console.log("for문 시작i: "+i);
				content += "<span class='page'>";
				if(i != localList.currPage){
					console.log("next localList.currPage: "+localList.currPage);
					content += "<button onclick='listCall("+i+");'>"+i+"</button>";
					console.log("현재페이지 i: "+i);
				}else{
					content += "<b>"+i+"</b>";
				}
				content += "</span>";
				if(i%5==0){
					content += "<span class='page'>";
					content += "<button onclick='nextList("+i+");'>다음</button>";
					content += "</span>";
					break;
				}
			};
			$("div").empty();
			$("div").append(content);
	}
	
	//이전 페이징 처리함수
	function prevList(){
		
	}
	
	
// 검색 함수 호출 함수
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
//검색 함수
 function searchList(list) {
		console.log(list);
		var content = "";
		list.forEach(function(item, idx) {
			console.log(item, idx);
			content += "<tr>";
			content += "<td>" + item.email + "</td>";
			content += "<td>" + item.name + "</td>";
			content += "</tr>";
		});
		$("tbody").empty();
		$("tbody").append(content);
		$("div").empty();
	}
</script>
</html>