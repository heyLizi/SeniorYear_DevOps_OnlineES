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
					<h4>提示信息 </h4>
				</div>
				<div class="form-group" style="text-align:center; color:white">
					<br>
					<p id="msgPrompt"></p>
					<p id=waitTime></p>
					<br>
				</div>
				<div class="form-group" style="text-align:center; color:white">
					<form id="dataForm" action="enter" method="POST">
						<input type="hidden" name="eid" value="${eid}">
						<input type="hidden" name="sid" value="${sid}">
						<input type="hidden" name="sname" value="${sname}">
						<button type="submit" id="enterBtn" class="btn btn-info btn-sm" data-toggle="button" style="display:none;">进入考试</button>			
					</form>
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
		
		//获取传来的信息
		var examState = ${state};
		var waitTime = ${waitSec};
		
		var intervalTimerID = 0;
		
		if(examState == 0){
			//考试尚未开始
			document.getElementById("msgPrompt").innerHTML = "本场考试尚未开始";
			document.getElementById("waitTime").innerHTML = "<br>距离考试开始还有 <span id='hour'></span> 小时 <span id='minute'></span> 分钟 <span id='second'></span>秒"
			
			//指定1秒刷新一次，intervalTimerID是返回的ID值，需要用它来关闭这个定时器
			intervalTimerID = window.setInterval(function(){timeRefresh()}, 1000);
		}
		else if(examState == 2){
			//考试已经结束
			document.getElementById("msgPrompt").innerHTML = "本场考试已结束";
		}

		function timeRefresh(){
			if(waitTime > 0){
				waitTime -= 1;
				
				var waitHour = parseInt(waitTime / 3600);
				var waitMinute = parseInt((waitTime % 3600) / 60);
				var waitSecond = parseInt(waitTime % 60);
				
				if(waitHour < 10) {waitHour = '0'+waitHour};
				if(waitMinute < 10) {waitMinute = '0'+waitMinute};
				if(waitSecond < 10) {waitSecond = '0'+waitSecond};
				
				document.getElementById("hour").innerHTML = waitHour;
				document.getElementById("minute").innerHTML = waitMinute;
				document.getElementById("second").innerHTML = waitSecond;
			}
			else{
				window.clearInterval(intervalTimerID);
				document.getElementById("msgPrompt").innerHTML = "本场考试已开始";
				$("#enterBtn").attr("style","width:13%; height:27px; display:inline;");
			}
		}
	});

	
</script>

</html>