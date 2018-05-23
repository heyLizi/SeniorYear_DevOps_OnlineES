<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; c
    harset=utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>OnlineES</title>
    <link type="text/css" href="../../css/bootstrap.min.css" rel="stylesheet">
    <link type="text/css" href='http://fonts.googleapis.com/css?family=Open+Sans:400italic,600italic,400,600'
        rel='stylesheet'>
	<script src="../../js/jquery.min.js"></script>
</head>

<body style="background-image: url(../../img/back.jpg);">
    <nav class="navbar navbar-inverse" role="navigation">
	    <div class="container-fluid">
		    <div class="navbar-header" style="margin-left:180px">
		        <a class="navbar-brand" href="#">OnlineES在线考试平台</a>
		    </div>
		    <div>
		        <ul class="nav navbar-nav">
		            <li><a href="#">首页</a></li>
		            <li class="active"><a href="#">考试</a></li>
		        </ul>
		    </div>
	    </div>
	</nav>
  
	<div class="container" style="margin-top:180px; min-height:68.4%;">
		<div class="row">
			<div class="col-xs-8 col-md-offset-2" style="background-color: rgba(0,0,0,0.3); border-radius: 15px;">
				<br>
				<div class="form-group" style="text-align:center; color:white">
					<h4>考试信息确认 </h4>
				</div>
				<div  class="form-group" style="margin:30px auto; color:white; width:80%;">
					<p> ${stuExamInfo.getName()}, 您好</p>
					<p>您即将参加的"${examInfo.getCourseName()}"课程的考试 ${examInfo.getName()} , 考试将于 ${startTime} 开始，于  ${endTime} 结束。本次考试共有 ${examInfo.getQuestionCount()} 道试题，考试时长 ${examInfo.getPeriod()} 分钟。请您调整心态，合理分配时间，独立作答。祝您取得好成绩！</p>
					<br>
					<HR style="FILTER: alpha(opacity=100,finishopacity=0,style=3)" width="100%" color=#987cb9 SIZE=3>
					<p>如上述考试信息无误，请您输入邮箱中参加考试的密码来参加考试。</p>
					<br>
					<div class="form-inline" style="text-align:center; color:white">
						<form id="verifyForm">
							<input type="hidden" name="eid" value="${eid}">
							<input type="hidden" name="sid" value="${sid}">
							<input type="text" class="form-control" id="password" name="password" value="" style="width:200px; height:27px;">
							<button type="button" id="verifyBtn" class="btn btn-info btn-sm" data-toggle="button" style="width:13%; height:27px;" onclick="verifyPasswd()">参加考试</button>			
							<span id="passwdErrMsg"></span>
						</form>
						<form id="enterForm" action="../../enter" method="POST">
							<input type="hidden" name="eid" value="${eid}">
							<input type="hidden" name="sid" value="${sid}">
							<input type="hidden" name="sname" value="${stuExamInfo.getName()}">
							<button type="submit" id="enterBtn" class="btn btn-info btn-sm" data-toggle="button" style="display:none;"></button>			
						</form>
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

	function verifyPasswd(){
		$.ajax({
			cache: true,
			url:"../../verify",
			type: "POST",
			data:$('#verifyForm').serialize(),
			success: function(result) {
				var resultObj = eval("("+result+")");
				var isSuccess = resultObj.success;
				if(isSuccess == false){
					document.getElementById("passwdErrMsg").innerHTML = "<font color='red'>密码错误</font>";
				}
				else{
					//密码正确，进入考试
					$('#enterBtn').click();
				}
			},
			//这种情况应对网络中断导致的错误等
			error: function(err) {
				//alert(err);
				document.getElementById("passwdErrMsg").innerHTML = "<font color='red'></font>";
				setTimeout(function(){
					$('.pop').empty().remove();
				},2000);
			}
		});	
	}
	
</script>

</html>