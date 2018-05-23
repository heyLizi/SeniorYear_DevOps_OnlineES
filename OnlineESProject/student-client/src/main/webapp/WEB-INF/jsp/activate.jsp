<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; c
    harset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>OnlineES</title>
    <link type="text/css" href="css/bootstrap.min.css" rel="stylesheet">
    <link type="text/css" href='http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,400,600'
        rel='stylesheet'>
	<script src="js/jquery.min.js"></script>
    <style>  
	#resendBtn {  
	    color: #337ab7;      
	    background-color: transparent;  
	    border: 0px none;  //去边框  
	    font-family: "宋体";  
	    font-size: 15px;  
	}  
	#resendBtn:hover{  
	    color: grey;  
	    border: none;  
	    cursor: hand;  
	    cursor: pointer;  
	}  
	#resendBtn:focus {   
	    outline: none;    //去边框  
	}   
	</style>
</head>

<body style="background-image: url(img/back.jpg);">
    <nav class="navbar navbar-inverse" role="navigation">
	    <div class="container-fluid">
		    <div class="navbar-header" style="margin-left:180px">
		        <a class="navbar-brand" href="#">OnlineES在线考试平台</a>
		    </div>
		    <div>
		        <ul class="nav navbar-nav">
		            <li><a href="#">首页</a></li>
		            <li><a href="#">考试</a></li>
		        </ul>
		    </div>
		    <div>
		        <ul class="nav navbar-nav"  style="margin-left:500px">
		            <li class="active"><a href="#">注册</a></li>
		        </ul>
		    </div>
	    </div>
	</nav>
  
	<div class="container" style="margin-top:180px; min-height:68.4%;">
		<div class="row">
			<div class="col-xs-8 col-md-offset-2" style="background-color: rgba(0,0,0,0.3); border-radius: 15px;">
				<div class="form-group" style="text-align:center; color:white">
					<%
						String sid = (String)request.getAttribute("sid");
						if(sid == null) {
						    sid = "error";
						}
						String email = (String)request.getAttribute("email");
						if(email == null) {
						    email = "error";
						}
					%>
					<h5>注册成功！ </h5>
					<p>我们已经往您的注册邮箱  <a href="http://www.${email}"> ${email} </a> 发送了验证码，请在下方输入框输入验证码来激活账户</p>
					<br>
					<div class="form-inline">
						<form id="activateForm">
							<input type="text" class="form-control" id="activateCode" name="activateCode" value="" style="width:200px; height:27px; ">
							<button type="button" id="activateBtn" class="btn btn-info btn-sm" data-toggle="button" style="width:10%; height:27px" onclick="activateAccount()">验证</button>						
						</form>
						<p id="codeErrMsg"></p>
					</div>
					<br>
					<br>
					<div class="form-inline">
						<form id="resendForm">
							<button type="button" id="resendBtn" class="btn btn-info btn-sm" data-toggle="button" onclick="resendEmail()"> 没有收到邮件？？ </button> 
						</form>
						<p id="resendMsg"></p>					
					</div>
				</div>
				<!-- 如果因为网络不通而无法注册，错误信息显示在这里  -->
				<div><p id="errNetMsg" class='pop'>请检查您的网络连接</p></div>
			</div>
		</div>
	</div>
</body>

<script>

	$(function (){
		//弹出框的消息（提示网络异常）不显示
		$('.pop').empty();
		
	});
	
	function activateAccount(){
		$.ajax({
			cache: true,
			url:"activateAction",
			type: "POST",
			async: false, 
			data:$('#activateForm').serialize(),
			success: function(result) {
				var resultObj = eval("("+result+")");
				var isSuccess = resultObj.success;
				if(isSuccess == false){
					document.getElementById("codeErrMsg").innerHTML = "<font color='red'>验证码错误</font>";
				}
				else{
					//跳转到欢迎页面
					window.location.href = "welcome";
				}
			},
			//这种情况应对网络中断导致的错误等
			error: function(err) {
				//alert(err);
				document.getElementById("codeErrMsg").innerHTML = "<font color='red'></font>";
				setTimeout(function(){
					$('.pop').empty().remove();
				},2000);
			}
		});
	}
	
	function resendEmail(){
		$.ajax({
			url:"resendAction",
			type: "POST",
			async: false, 
			success: function(result) {
				var resultObj = eval("("+result+")");
				var isSuccess = resultObj.success;
				
				if(isSuccess == false){
					document.getElementById("resendMsg").innerHTML = "<font color='white'>重新发送失败</font>";
				}
				else{
					document.getElementById("resendMsg").innerHTML = "<font color='white'>已重新发送验证码</font>";
				}
			},
			//这种情况应对网络中断导致的错误等
			error: function(err) {
				//alert(err);
				document.getElementById("resendMsg").innerHTML = "<font color='red'></font>";
				setTimeout(function(){
					$('.pop').empty().remove();
				},2000);
			}
		});
	}
</script>

</html>