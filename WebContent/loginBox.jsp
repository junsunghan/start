<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<div id="login"></div>
<script>
	var loginE = "${sessionScope.loginemail}";
	var nick =  "${sessionScope.nickname}";
	
	if(loginE ==""){
		alert("로그인이 필요한 서비스 입니다.");
		location.href="login.jsp";
	} else{
		var content = nick+"님";
		document.getElementById("login").innerHTML = content;
	}
//	var suc = "${sessionScope.suc}";
	/* if(!suc){
		alert("email 및 password를 확인해주세요!");
		location.href="login.jsp";
	} */
	
</script>