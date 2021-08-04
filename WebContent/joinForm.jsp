<%@ page language="java" contentType="text/html; charset=UTF-8"   pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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
</head>
<body>
<!-- 상단 메뉴바 -->
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
  <div class="container-fluid">
    <a class="navbar-brand" href="./index.jsp">발자국</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNavDropdown" aria-controls="navbarNavDropdown" aria-expanded="false" aria-label="Toggle navigation">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse nav justify-content-end bg-light" id="navbarNavDropdown">
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link active" aria-current="page" href="./index.jsp">홈</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">지도</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">피드</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">발자국</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="#">메시지</a>
        </li>
        <li class="nav-item dropdown">
          <a class="nav-link" href="./login.jsp" >
            프로필
          </a>
        </li>
      </ul>
    </div>
  </div>
</nav>
<!-- 내용 시작 -->
<div class="col-4" style="margin:100 auto;">
<h2>회원가입</h2>
	<form action="join" method="post">
		<table class="table table-bordered text-center">
			<tr>
				<td>EMAIL</td>
				<td><input type="text" name="email"  minlength="1" required/><input type="button"  name="overlay" id ="overlay" value="중복"/></td>
				
			</tr>
			<tr>
				<td>NICKNAME</td>
				<td><input type="text" name="nickname" minlength="1" required/></td>
			</tr>
			<tr>
				<td>PW</td>
				<td><input type="password" minlength="6" name="pw" id="pw1" required/>
					<br/>
					<input type="password" minlength="6" id="pw2" placeholder="pw를 재입력해주세요" required/><br/> <input type="button" onclick="test()" value="확인">
				</td>
			</tr>
			<tr>
				<td>NAME</td>
				<td><input type="text" name="name" minlength="1" required/></td>
			</tr>
			<tr>
				<td>GENDER</td>
				<td>
				<input type="radio" name="gender" value="남" checked/>남
				&nbsp;&nbsp;&nbsp;
				<input type="radio" name="gender" value="여"/>여
				</td>
			</tr>
			<tr>
				<td>BIRTH</td>
				<td><input type="text" name="birth"  minlength="8" maxlength="8" placeholder="생년월일 8자로 입력해주세요. 19990101" required/>  ex)19990101</td>
			</tr>
			<tr>
				<td>PHONE</td>
				<td><input type="text" name="phone" minlength="11" maxlength="12" placeholder="-제외 입력해주세요" required/>  ex)01000000000</td>
			</tr>
			<tr>
				<td>STYLE</td>
				<td>
				<input type="radio" name="style" value="1"  checked/>식도락
				&nbsp;
				<input type="radio" name="style" value="2" />레포츠
				&nbsp;
				<br/>
				<input type="radio" name="style" value="3" />문화
				&nbsp;&nbsp;
				<input type="radio" name="style" value="4" />힐링
				&nbsp;
				<input type="radio" name="style" value="5" />호캉스
				&nbsp;
				</td>
			</tr>
			<tr>
				<td colspan="2">
				<button>회원가입</button>
				</td>
			</tr>
		</table>
	</form>
	</div>
</body>
<script>
var success = "${success}";
if(success == "true") {
	alert("회원 가입에 성공 했습니다.");
	location.href="index.jsp";
}
if(success == "false") {
	alert("회원 가입에 실패 했습니다.");
}
	
	
	$("#overlay").click(function(){
		console.log("클릭!")
		var email = ($("input[name='email']").val());
		console.log(email)
		$.ajax({
			type:'get',
			url:'overlay',
			data:{'email':email}, //$("input[name='id']").val()이 value
			dataType: 'JSON',
			success:function(data){
				console.log(data);			
				if(!data.success){
					alert("처리중 문제가 발생 했습니다. 다시 시도해주세요.");
				}else{
					if(email==""){
						alert("email을 입력해주세요.")
						$("input[name='email']").val("");
					}else if(data.overlay){
						alert("이미 사용 중 입니다.");
						 $("input[name='email']").val("");//입력한 아이디를 공백으로 만듬
					
				}else{
						alert("사용 가능합니다.")
					}
				}
			},
			error:function(e){
				console.log(e);
			}
		});
	});
	
	function test() {
	      var p1 = document.getElementById('pw1').value;
	      var p2 = document.getElementById('pw2').value;
	      
	      if(p1.length < 6) {
	              alert('입력한 글자가 6글자 이상이어야 합니다.');
	              return false;
	          }
	          
	          if( p1 != p2 ) {
	            alert("비밀번호불일치");
	            return false;
	          } else{
	            alert("비밀번호가 일치합니다");
	            return true;
	          }
	    }
</script>
</html>