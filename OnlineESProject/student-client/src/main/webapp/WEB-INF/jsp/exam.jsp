<!-- 注释

有任何问题来问我

从头到尾的每一个组件：
考生名 : <span id="sName">狗剩</span>  id=sName

进度条 : id="stateBar" 使用方法 ：设置它的style属性， style="width: 90%; background-color: #28d7af" 其中那个width就是进度，按百分比设置

当前题/题目总数 : <span id="cState" style="color: white;"> 15/20</span>  id=cState

剩余时间 :  <span id="rTime" style="color: white;">23:30</span>  id=rTime

题目类型 :  <span id="questionType">单选题</span>  id=questionType

题目内容 :  <div id="question">  内容xxx  </div> id=question，我一般就用innerHTML=xxx来设置了

题目选项 :  <div id="options">  id=options

                这下面这段是一个选项的html代码，因为可能选项数不确定，所以这一段可能要整个加到options的里面，用js应该好加，记住
                <div style="border: 1px solid rgba(0,0,0,0.27); margin-top: 15px; margin-bottom: 15px">
                    <div class="row">
                        <div class="col-sm-1" style="margin-top: 10px; margin-bottom: 10px;">
                            <input type="radio" style="margin-left: 20px;">&nbsp;A.
                        </div>
                        <div class="col-sm-10" style="margin-top: 10px; margin-bottom: 10px; text-align: left">
                            是的风格和阿
                        </div>
                    </div>
                </div>

答题情况  :  <div class="row" style="line-height: 27px" id="examState">  id=examState
             这下面4段是4种情况，也是html代码直接加到examState下，用js加

                1.已答题
                <div class="col-sm-1" style="text-align: center; padding: 5px;">
                    <div style="background-color:#28d7af; color: white; border-radius: 5px">
                        <div>2</div>
                        <div>B</div>
                    </div>
                </div>

                2.已标记的题
                <div class="col-sm-1" style="text-align: center; padding: 5px;">
                    <div style="background-color:#ea782c; color: white; border-radius: 5px">
                        <div>3</div>
                        <div>&nbsp;</div>
                    </div>
                </div>

                3.当前题
                 <div class="col-sm-1" style="text-align: center; padding: 5px;">
                    <div style="background-color: white; color: rgba(0,0,0,0.37); border-radius: 5px; border: 1px dashed #28d7af">
                        <div>9</div>
                        <div>&nbsp;</div>
                    </div>
                </div>

                 4.未答题
                <div class="col-sm-1" style="text-align: center; padding: 5px;">
                    <div style="background-color: white; color: rgba(0,0,0,0.37); border-radius: 5px; border: 1px solid rgba(0,0,0,0.27)">
                        <div>10</div>
                        <div>&nbsp;</div>
                    </div>
                </div>
-->

<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>OnlineES</title>
    <script src="js/jquery.min.js"></script>
    <link type="text/css" rel="stylesheet" href="css/main.css">
    <link type="text/css" href="css/bootstrap.min.css" rel="stylesheet">
    <link type="text/css" href="css/footer.css" rel="stylesheet">
</head>

<body style="background-image: url(img/back.jpg);">
	<nav class="navbar navbar-inverse" role="navigation">
	    <div class="container-fluid">
	        <div class="navbar-header" style="margin-left: 190px">
	            <a class="navbar-brand" href="#">OnlineES在线考试平台</a>
	        </div>
	        <div>
		        <ul class="nav navbar-nav">
		            <li><a href="#">首页</a></li>
		            <li class="active"><a href="#">考试</a></li>
		        </ul>
		    </div>
		    <div>
		        <ul class="nav navbar-nav"  style="margin-left:460px">
		            <li>
		            	<label style="margin-top: 15px; color: white">&nbsp;&nbsp;<span id="sName">狗剩</span>&nbsp;&nbsp;</label>
	                    <span style="color: #9d9d9d">|&nbsp;考试中</span>
		            <li>
		        </ul>
		    </div>
	    </div>
	</nav>
	
	<div class="container" style="width:70%; background: white; margin-top: 40px; margin-bottom: 80px;">
	    <div class="row" style="background-color: rgba(0,0,0,0.65)">
	        <div class="col-sm-7 col-sm-offset-1" style="margin-top: 20px;">
	            <!--进度条和时间-->
	            <div class="form-inline">
	                <div class="progress">
	                    <div id="stateBar" class="progress-bar progress-bar-success" role="progressbar"
	                         aria-valuenow="60" aria-valuemin="0" aria-valuemax="100"
	                         style="width: 50%; background-color: #28d7af">
	                    </div>
	                </div>
	            </div>
	        </div>
	        <div class="col-sm-1"  style="margin-top: 20px;">
	        	<span id="cState-cur" style="color: white;">15</span><span style="color: white;"> / </span><span id="cState-total" style="color: white;">20</span>
	        </div>
	        <div class="col-sm-2" style="margin-top: 20px; margin-left:15px;">
	            <span style="margin-left:10px; color: white;">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="img/time.png"/></span>
	            <span style="margin-left:10px;color: white;"><span id='hour'>01</span>:<span id='minute'>59</span>:<span id='second'>59</span></span>
	        </div>
	    </div>
	
	 	<!--题目类型和标记按钮-->
	    <div class="row">
	        <div class="col-sm-8 col-sm-offset-1" style="vertical-align: middle; margin-top: 15px">
	            <div style="font-size: 20px" id="type" >
	            <img src="img/que.png"/>&nbsp;[<span id="questionType">单选题</span>&nbsp;]
	            </div>
	        </div>
	        <div class="col-sm-1 col-sm-offset-1" style="margin-top: 15px">
	            <input type="button" id="markBtn" class="btn btn-primary btn-ms" style="margin-left:-20px; background-color: #28d7af; font-color: white; width: 80px; border: none" value="标记此题" onclick="markQues()">
	        </div>
	    </div>
	
	    <hr style="color: black; margin-top: 10px" width="100%">
	
		<!-- 题目信息 -->
	    <div class="row" style="line-height:30px">
	        <div class="col-sm-10 col-sm-offset-1">
	            <!--题目内容和选项-->
	            <div id="question">
	                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean euismod bibendum laoreet. Proin
	                gravida dolor sit amet lacus accumsan et viverra justo commodo. Proin sodales pulvinar tempor. Cum
	            </div>
	            <div id="options">
	            	<!-- 选项情况，通过循环来生成每个选项的div -->
	           </div>
	        </div>
	    </div>
	
	    <div class="row" style="line-height:50px;">
	        <div class="col-sm-10 col-sm-offset-1">
	
	            <div class="row">
	                <div class="container">
	                    <div class="col-sm-2">
	                    	<input type="button" class="btn btn-primary btn-ms" id="lastQuesBtn"
	                               style="background-color: #28d7af; font-color: white; margin-left:-15px; width: 100px; border: none"
	                               value="上一题" onclick="turnToLastQuestion()">
	                    </div>
	                    <div class="col-sm-2 col-sm-offset-5">
	                        <input type="button" class="btn btn-primary btn-ms" id="nextQuesBtn"
	                               style="background-color: #28d7af; font-color: white; width: 100px; border: none"
	                               value="下一题" onclick="turnToNextQuestion()">
	                    </div>
	                </div>
	            </div>
	
	        </div>
	    </div>
	
	    <hr style="color: black; margin-top: 10px; margin-bottom: 10px" width="100%">
	
	    <div class="row" style="margin-bottom: 20px">
	        <!--题目类型和标记按钮-->
	        <div class="col-sm-8 col-sm-offset-1" style="vertical-align: middle; margin-top: 10px">
	            <div style="font-size: 20px" id="type" }><img src="img/book.png"/>&nbsp;[答题情况]</div>
	        </div>
	    </div>
	    <div class="row" style="margin-bottom: 10px">
	        <div class="col-sm-10 col-sm-offset-1">
	            <div class="row" style="line-height: 27px" id="examState">
	            	<!-- 答题情况，通过循环来生成每道题的情况div -->
	            </div>
	        </div>
	    </div>
	    
	    <div class="row" style="margin-bottom: 20px">
	        <!--题目类型和标记按钮-->
	        <div class="col-sm-2 col-sm-offset-2 " style="vertical-align: middle; margin-top: 10px">
	            <div style="margin-left: 40px; background-color:#28d7af; color: white; border-radius: 5px; width: 50px; height: 50px">
	            </div>
	            <div style="margin-left: 40px;">[已答题]</div>
	        </div>
	
	        <div class="col-sm-2" style="vertical-align: middle; margin-top: 10px">
	            <div style="margin-left: 40px; background-color:#ea782c; color: white; border-radius: 5px; width: 50px; height: 50px">
	            </div>
	            <div style="margin-left: 40px;">[已标记]</div>
	        </div>
	
	        <div class="col-sm-2" style="vertical-align: middle; margin-top: 10px">
	            <div style="margin-left: 40px; background-color: white; color: rgba(0,0,0,0.37); border-radius: 5px; border: 1px dashed #28d7af; width: 50px; height: 50px">
	            </div>
	            <div style="margin-left: 40px;">[当前题]</div>
	        </div>
	
	        <div class="col-sm-2" style="vertical-align: middle; margin-top: 10px">
	            <div style="margin-left: 40px; background-color: white; color: rgba(0,0,0,0.37); border-radius: 5px; border: 1px solid rgba(0,0,0,0.27); width: 50px; height: 50px">
	            </div>
	            <div style="margin-left: 40px;">[未答题]</div>
	        </div>
	
	    </div>
	
		<div class="col-sm-2 col-sm-offset-5">
	        <input type="button" class="btn btn-primary btn-ms"
	               style="margin-top: 20px; margin-bottom: 40px; margin-left:15px; background-color: #ea782c; font-color: white; width: 100px; border: none"
	               value="提交试卷" onclick="submitPaper()">
	         <span id="submitErrMsg"></span>
	    </div>
	    <form id="finishForm" action="exam/finish" method="POST">
			<input type="hidden" name="eid" value="${eid}">
			<input type="hidden" name="sid" value="${sid}">
			<button type="submit" id="finishBtn" class="btn btn-info btn-sm" data-toggle="button" style="display:none;"></button>
		</form>
		
	    <div><p id="errNetMsg" class='pop'>请检查您的网络连接</p></div>
	</div>

</body>

<script>

	//全局变量
	var currentQues = 0;	//当前试题数
	var totalQues = 0;		//试题总数
	var data;				//所有试题信息
	
	var currentData;		//当前试题的详细信息
	var isAnswered;			//当前试题是否被标记
	var stuAnswer;			//当前试题的作答详细信息(如果有，对应的是数据库中的选项ID的列表)，为提交数据使用
	var isMarked;			//当前试题是否被标记
	
	var stateAreaNormalColor = "background-color: white; color: rgba(0,0,0,0.37);";		//答题区域正常背景色的属性值
	var stateAreaAnsweredColor = "background-color:#28d7af; color: white;";				//答题区域已答题背景色的属性值
	var stateAreaMarkedColor = "background-color:#ea782c; color: white;";				//答题区域正常背景色的属性值
	var stateAreaColorPattern = stateAreaNormalColor;									//答题区域的现有颜色属性，当去掉边框属性值时能够不影响内部颜色的属性
	var stateAreaNormalBorder = "border-radius: 5px; border: 1px solid rgba(0,0,0,0.27);";//答题区域正常边界的属性值
	var stateAreaCurrentBorder = "border-radius: 5px; border: 1px dashed #28d7af;";		//答题区域当前边界的属性值
	
	//初始化数据和界面基本元素
	$(function (){
		
		//弹出框的消息（提示网络异常）不显示
		$('.pop').empty();
		
		//当前试题数(第1题对应CurInfo的下标0，同样的，也对应currentQues的0)，这里要先保存这个值，每次跳到下一题要记得更新这个值
		currentQues = ${curInfo.getCur()};
		
		//获取学生某场考试的所有试题信息
		data = ${paperInfo};
		
		//获取试题总数
		totalQues = 0;
		for(var eachVar in data){
			totalQues += 1;
		}
		
		//更新学生id信息
		document.getElementById("sName").innerHTML = "${sname}";
		
		//更新当前的进度信息:当前题数/总题数
		var curPercentage = parseInt((currentQues+1)*100 / totalQues);
		$("#stateBar").attr("style","width:"+curPercentage+"%; background-color: #28d7af");
		document.getElementById("cState-cur").innerHTML = currentQues+1;
		document.getElementById("cState-total").innerHTML = totalQues;
		
		//更新剩余时间的信息
		var remainTime = ${remainSec};
		var intervalTimerID = 0;
		//指定1秒刷新一次，intervalTimerID是返回的ID值，需要用它来关闭这个定时器
		intervalTimerID = window.setInterval(function(){timeRefresh()}, 1000);
		
		function timeRefresh(){
			if(remainTime > 0){
				remainTime -= 1;
				
				var remainHour = parseInt(remainTime / 3600);
				var remainMinute = parseInt((remainTime % 3600) / 60);
				var remainSecond = parseInt(remainTime % 60);
				
				if(remainHour < 10) {remainHour = '0'+remainHour};
				if(remainMinute < 10) {remainMinute = '0'+remainMinute};
				if(remainSecond < 10) {remainSecond = '0'+remainSecond};
				
				document.getElementById("hour").innerHTML = remainHour;
				document.getElementById("minute").innerHTML = remainMinute;
				document.getElementById("second").innerHTML = remainSecond;
			}
			else{
				window.clearInterval(intervalTimerID);
				//提交试卷
				$("#finishBtn").click();
			}
		}
	                
		//初始化“答题情况”区域，需要按照学生的答题情况回显“答题情况”区域（需要最开始的时候就进行回显操作）
		var stateContentStr = '';
		for(var i=0; i<totalQues; i++){
			var styleStr = stateAreaNormalColor+stateAreaNormalBorder; //“答题情况”的正常属性的值
			var answerStr = "&nbsp;"; //“答题情况”的学生回答选项的值
			
    		stuAnswer = data[i].studentAnswers;
    		var stuAnswerNum = 0; //学生选了多少个选项
    		var stuAnswerArr = new Array();
    		if(stuAnswer != undefined && stuAnswer != null && stuAnswer.replace(/(^s*)|(s*$)/g, "").length != 0){ //如果没有被回答的话，stuAnswer的值是 null或者是空白字符串
    			//如果被回答，获取选择了多少个选项，哪些选项
    			stuAnswerArr = stuAnswer.split(",");
    			stuAnswerNum = stuAnswerArr.length; 
    		}
    		
    		var opt = data[i].options;
    		var optNum = 0; //选项个数
    		var optArr = new Array();
    		if(opt != null && opt.replace(/(^s*)|(s*$)/g, "").length != 0){
    			//如果选项不为空，获取有多少个选项，哪些选项
				optArr = opt.split(",");
				optNum = optArr.length;
    		}
    		
    		var intToCharArr = new Array("A","B","C","D","E","F","G","H"); //用于做int-char转换的数组，选项的下标从0开始，对应显示的字母从A开始
    		
    		if(stuAnswerNum > 0){//该题被回答
    			//更改属性为“已答题”
    			styleStr = stateAreaAnsweredColor+stateAreaNormalBorder;
	    		//获取表示已选选项的字符串
	    		for(var j=0; j<stuAnswerNum; j++){
	    			for(var k=0; k<optNum; k++){//找到该答案对应选项的下标
	    				if(stuAnswerArr[j] == optArr[k]){
	    					answerStr += intToCharArr[k]+",";
	    				}
	    			}
	    		}
	    		//去掉字符串结尾的","
				answerStr = answerStr.slice(0, -1);
    		}
    		
    		//试题是否被标记（这个要放在“是否被回答”之后，因为“已标记”拥有最高的权限）
    		isMarked = data[i].markStatus;
			if(isMarked == 1){//该题被标记
    			//更改属性为“已标记”
    			styleStr = stateAreaMarkedColor+stateAreaNormalBorder;
			}
			
			stateContentStr += '<div class="col-sm-1" style="text-align: center; padding: 5px;"  onclick="turnToSelectedQuestion('+i+')">'+
							   '<div id="refQues'+i+'" style="'+styleStr+'">'+
							   '<div>'+(i+1)+'</div><div id="refQuesAns'+i+'">'+answerStr+'</div></div></div>';
		}
		document.getElementById("examState").innerHTML = stateContentStr;
	
		//刷新当前试题的信息
		getAndRefreshCurrentQuestionContent();//再一次刷新界面内容
	});
	
	
	//获取并刷新每道题的信息
	function getAndRefreshCurrentQuestionContent(){
		
		var curUnqId = data[currentQues].id; //得到currentQues对应的其唯一的ID
		
		//更新当前的进度信息:当前题数/总题数
		var curPercentage = parseInt((currentQues+1)*100 / totalQues);
		$("#stateBar").attr("style","width:"+curPercentage+"%; background-color: #28d7af");
		document.getElementById("cState-cur").innerHTML = currentQues+1;
		document.getElementById("cState-total").innerHTML = totalQues;
		
		if(currentQues == 0){ //如果是第一题，那么“上一题”按钮不可点击
			$("#lastQuesBtn").attr("disabled",true);
		}
		else{//如果不是第一题，那么“上一题”按钮可点击
			$("#lastQuesBtn").attr("disabled",false);
		}
		
		if(currentQues == totalQues-1){//如果是最后一题，那么“下一题”按钮不可点击（注意current从0开始）
			$("#nextQuesBtn").attr("disabled",true);
		}
		else{//如果不是最后一题，那么“下一题”按钮可点击
			$("#nextQuesBtn").attr("disabled",false);
		}
		
		$.ajax({
			cache: true,
			url:"exam/exercise/"+curUnqId,
			type: "GET",
			dataType: "json",
			success: function(result) {
				
				var isSuccess = result.success;
				if(isSuccess == false){
					//没有获取到题目的详细数据，暂时不知道干什么
				}
				else{//获取到了题目的详细数据，更新界面
					currentData = result.data;
		        	
					//试题类型
					var type = currentData.exerciseType;
		        	var typeStr = (type == 1)? "单选题":"多选题"
		        	document.getElementById("questionType").innerHTML = typeStr;
		        	
					//该题是否被回答，这里因为后期需要在选项列表中循环找到该题的回答，因此要在循环之前取到学生回答的值
					//考虑到多选题，学生可能回答了一个以上的答案，转为数组形式
		    		stuAnswer = currentData.studentAnswers;
		    		var stuAnswerNum = 0; //学生选了多少个选项
		    		var stuAnswerArr = new Array();
		    		if(stuAnswer != undefined && stuAnswer != null && stuAnswer.replace(/(^s*)|(s*$)/g, "").length != 0){
		    			//如果被回答，获取选择了多少个选项，哪些选项
		    			stuAnswerArr = stuAnswer.split(",")
		    			stuAnswerNum = stuAnswerArr.length; 
		    		}
		    		//p.s.如果没有被回答的话，stuAnswer的值是 null或者是空白字符串
		        	
		        	//题目内容
		        	var quesContent = currentData.content;
		        	document.getElementById("question").innerHTML = quesContent;
		        	
		        	//题目选项
		        	var quesOption = currentData.optionDtos;
		        	//获取选项个数
		    		var optNum = 0;
		    		for(var eachVar in quesOption){
		    			optNum += 1;
		    		}
		    		
		    		//初始化题目选项区域
		    		var optionContentStr = '';
		    		var intToCharArr = new Array("A","B","C","D","E","F","G","H"); //用于做int-char转换的数组，选项的下标从0开始，对应显示的字母从A开始
		    		var selectedOptIndex = new Array(); //在做循环的时候找到学生回答的那些选项，回显到“答题情况”区域，回显到选择框区域
		    		if(stuAnswerNum != 0){//如果学生回答了这道题
		    			for(var i=0; i<optNum; i++){
		    				if(type == 1){//如果是单选，则input的type="radio"，且对应同一个name
			    				for(var j=0; j<stuAnswerNum; j++){//找出学生回答的选项对应的下标及选项内容，便于回显
			    					if(quesOption[i].id == stuAnswerArr[j]){//如果学生选择了该选项，radioBtn被标记为“checked”，该选项被记录以回显到“答题情况”区域
						    			optionContentStr += '<div style="border: 1px solid rgba(0,0,0,0.27); margin-top: 15px; margin-bottom: 15px">'+
						    							'<div class="row">'+'<div class="col-sm-1" style="margin-top: 10px; margin-bottom: 10px; margin-left:10px;">'+
						    							'<input type="radio" id="radioBtn'+i+'" name="singleSelect" value="'+quesOption[i].id+'" checked="checked">&nbsp;'+intToCharArr[i]+'.</div>'+
						    	                        '<div class="col-sm-10" id="optContent'+(i+1)+'" style="margin-top: 10px; margin-bottom: 10px; text-align: left">'+
						    	                        quesOption[i].content+'</div></div></div>';
						    	        selectedOptIndex[j] = intToCharArr[i];
			    					}
			    					else{//如果学生没有选择该选项，radioBtn非checked
			    						optionContentStr += '<div style="border: 1px solid rgba(0,0,0,0.27); margin-top: 15px; margin-bottom: 15px">'+
		    							'<div class="row">'+'<div class="col-sm-1" style="margin-top: 10px; margin-bottom: 10px; margin-left:10px;">'+
		    							'<input type="radio" id="radioBtn'+i+'" name="singleSelect" value="'+quesOption[i].id+'">&nbsp;'+intToCharArr[i]+'.</div>'+
		    	                        '<div class="col-sm-10" id="optContent'+(i+1)+'" style="margin-top: 10px; margin-bottom: 10px; text-align: left">'+
		    	                        quesOption[i].content+'</div></div></div>';
			    					}
			    				}
		    				}
		    				else{//如果是多选，则input的type="checkbox"，不需要对应同一个name
			    				var hasFound = false;
			    				var foundIndex = -1;
			    				for(var j=0; j<stuAnswerNum; j++){//找出学生回答的选项对应的下标及选项内容，便于回显
			    					if(quesOption[i].id == stuAnswerArr[j]){
			    						hasFound = true;
			    						foundIndex = j;
			    						break;
			    					}
			    				}
			    				if(hasFound){
					    			optionContentStr += '<div style="border: 1px solid rgba(0,0,0,0.27); margin-top: 15px; margin-bottom: 15px">'+
					    							'<div class="row">'+'<div class="col-sm-1" style="margin-top: 10px; margin-bottom: 10px; margin-left:10px;">'+
					    							'<input type="checkbox" id="checkbox'+i+'" name="multiSelect" value="'+quesOption[i].id+'" checked="checked">&nbsp;'+intToCharArr[i]+'.</div>'+
					    	                        '<div class="col-sm-10" id="optContent'+(i+1)+'" style="margin-top: 10px; margin-bottom: 10px; text-align: left">'+
					    	                        quesOption[i].content+'</div></div></div>';
					    	        selectedOptIndex[foundIndex] = intToCharArr[i];
			    				}
		    					else{//如果学生没有选择该选项，checkbox非checked
		    						optionContentStr += '<div style="border: 1px solid rgba(0,0,0,0.27); margin-top: 15px; margin-bottom: 15px">'+
	    							'<div class="row">'+'<div class="col-sm-1" style="margin-top: 10px; margin-bottom: 10px; margin-left:10px;">'+
	    							'<input type="checkbox" id="checkbox'+i+'" name="multiSelect" value="'+quesOption[i].id+'">&nbsp;'+intToCharArr[i]+'.</div>'+
	    	                        '<div class="col-sm-10" id="optContent'+(i+1)+'" style="margin-top: 10px; margin-bottom: 10px; text-align: left">'+
	    	                        quesOption[i].content+'</div></div></div>';
		    					}
		    				}
		    			}
		    		}
		    		
		    		else{//如果学生没有回答这道题
		    			for(var i=0; i<optNum; i++){
		    				if(type == 1){//如果是单选，则input的type="radio"，且对应同一个name
		    					//每一个选项都没有被选择，radioBtn非checked
	    						optionContentStr += '<div style="border: 1px solid rgba(0,0,0,0.27); margin-top: 15px; margin-bottom: 15px">'+
    							'<div class="row">'+'<div class="col-sm-1" style="margin-top: 10px; margin-bottom: 10px; margin-left:10px;">'+
    							'<input type="radio" id="radioBtn'+i+'" name="singleSelect" value="'+quesOption[i].id+'" >&nbsp;'+intToCharArr[i]+'.</div>'+
    	                        '<div class="col-sm-10" id="optContent'+(i+1)+'" style="margin-top: 10px; margin-bottom: 10px; text-align: left">'+
    	                        quesOption[i].content+'</div></div></div>';
		    				}
		    				else{
		    					//每一个选项都没有被选择，checkbox非checked
		    					optionContentStr += '<div style="border: 1px solid rgba(0,0,0,0.27); margin-top: 15px; margin-bottom: 15px">'+
    							'<div class="row">'+'<div class="col-sm-1" style="margin-top: 10px; margin-bottom: 10px; margin-left:10px;">'+
    							'<input type="checkbox" id="checkbox'+i+'" name="multiSelect" value="'+quesOption[i].id+'">&nbsp;'+intToCharArr[i]+'.</div>'+
    	                        '<div class="col-sm-10" id="optContent'+(i+1)+'" style="margin-top: 10px; margin-bottom: 10px; text-align: left">'+
    	                        quesOption[i].content+'</div></div></div>';
		    				}
		    			}
		    		}
		    		document.getElementById("options").innerHTML = optionContentStr;
		    		//选项初始化完毕
		    		
		    		//接下来回显“答题情况”区域
		    		stateAreaColorPattern = stateAreaNormalColor; //将“答题情况”区域的默认颜色置为正常色
		    		var selectedStr = "&nbsp;";
		    		if(stuAnswerNum > 0){//该题被回答
		    			//更改属性为“已答题”+“当前题”（注意:“已答题”属性对颜色进行处理，“当前题”属性对边框进行处理）
		    			stateAreaColorPattern = stateAreaAnsweredColor;
			    		//获取表示已选选项的字符串
			    		for(var k=0; k<stuAnswerNum-1; k++){
			    			selectedStr += selectedOptIndex[k]+",";
			    		}
			    		selectedStr += selectedOptIndex[stuAnswerNum-1];
			    	}
		    		else{//该题未被回答
		    			//更改属性为当前题“未答题”+“当前题”！（注意:“已答题”属性对颜色进行处理，“当前题”属性对边框进行处理）
		    			stateAreaColorPattern = stateAreaNormalColor;
		    		}
		    		
		    		//试题是否被标记（这个要放在“是否被回答”之后，因为“已标记”拥有最高的权限）
		    		isMarked = currentData.markStatus;
					if(isMarked == 1){//该题被标记
		    			//更改属性为“已标记”+“当前题”（注意:“已标记”属性对颜色进行处理，“当前题”属性对边框进行处理）
			    		stateAreaColorPattern = stateAreaMarkedColor;
			    		//标记试题按钮的提示语变成“取消标记”，样式变化为橘色背景
						$("#markBtn").attr("value", "取消标记");
						$("#markBtn").attr("style", "margin-left:-20px; background-color: #ea782c; font-color: white; width: 80px; border: none");
					}
					else{
						//标记试题按钮的提示语变成“标记此题”，样式变化为橘色背景
						$("#markBtn").attr("value", "标记此题");
						$("#markBtn").attr("style", "margin-left:-20px; background-color: #28d7af; font-color: white; width: 80px; border: none");
					}
					
					//alert(stateAreaColorPattern);
					//颜色、边界属性回显到“答题情况”区域
					$("#refQues"+currentQues).attr("style",stateAreaColorPattern+stateAreaCurrentBorder);
					//“已选选项”回显到“答题情况”区域
		    		$("refQuesAns"+currentQues).innerHTML = selectedStr;
				}
			},
			//这种情况应对网络中断导致的错误等
			error: function(err) {
				//alert(err);
				setTimeout(function(){
					$('.pop').empty().remove();
				},2000);
			}
		});	
	}
	

	//转到上一道题
	function turnToLastQuestion(){
		removeCurrentBorderAttribute();//移除当前题目的“当前试题”的边界属性
		getAndShowSelectedAnswer(); //获取并显示学生选择的答案
		selectQues(); //提交试题
		
		currentQues--; //更改全局变量的值，变为其上一题
		getAndRefreshCurrentQuestionContent(); //获取并刷新每道题的信息
	}
	
	//转到下一道题
	function turnToNextQuestion(){
		removeCurrentBorderAttribute();//移除当前题目的“当前试题”的边界属性
		getAndShowSelectedAnswer(); //获取并显示学生选择的答案
		selectQues(); //提交试题
		
		currentQues++; //更改全局变量的值，变为其下一题
		getAndRefreshCurrentQuestionContent(); //获取并刷新每道题的信息
	}
	
	
	//转到指定的试题
	function turnToSelectedQuestion(quesId){//参数quesId同currentQues一样，下标从0开始
		removeCurrentBorderAttribute();//移除当前题目的“当前试题”的边界属性
		getAndShowSelectedAnswer(); //获取并显示学生选择的答案
		selectQues(); //提交试题
		
		currentQues = quesId; //更改全局变量的值，变为其指定的题号
		getAndRefreshCurrentQuestionContent(); //获取并刷新每道题的信息
	}
	
	
	//移除“当前试题”的属性
	function removeCurrentBorderAttribute(){
		$("#refQues"+currentQues).attr("style", stateAreaColorPattern+stateAreaNormalBorder);
	}
	
	//获取并显示学生选择的答案
	function getAndShowSelectedAnswer(){
		
		var intToCharArr = new Array("A","B","C","D","E","F","G","H"); //用于做int-char转换的数组，选项的下标从0开始，对应显示的字母从A开始
		
		if(currentData.exerciseType == 1){//当前试题为单选题
			var selectedValue = $("input[name='singleSelect']:checked").val();
			if (selectedValue == null){//什么也没有选中
				isAnswered = false;
				//回显到“答题情况”区域的“已选选项”中(“已选选项”为空白，否则会出现大小失调的问题)
	    		document.getElementById("refQuesAns"+currentQues).innerHTML = "&nbsp;";
					
				stateAreaColorPattern = stateAreaNormalColor;
			}	
			else{//选中了某个选项
				isAnswered = true;
				stuAnswer = selectedValue;
				var selectedRadioId =  $("input[name='singleSelect']:checked").attr('id'); //获取ID
				var selectedRadioIndex = selectedRadioId.substr(-1); //获取最后一位，即是下标值
				var selectedRadioStr = intToCharArr[parseInt(selectedRadioIndex)];
				//回显到“答题情况”区域的“已选选项”中
	    		document.getElementById("refQuesAns"+currentQues).innerHTML = selectedRadioStr;
				
				stateAreaColorPattern = stateAreaAnsweredColor;
			}

			if(isMarked){//该试题被标记
				stateAreaColorPattern = stateAreaMarkedColor;
			}
			
			//改变“答题情况”区域的颜色属性
			$("#refQues"+currentQues).attr("style",stateAreaColorPattern+stateAreaNormalBorder);
		}
		
		else{//当前试题为多选题
			var selectedValue = "";
			isAnswered = false;
			var selectedCheckboxStr = "";
			
			var checkboxes = document.getElementsByName('multiSelect'); //选择所有name="multiSelect"的对象，返回数组 
			//循环检测每一个是否被选中
			for(var i=0; i<checkboxes.length; i++){ 
				if(checkboxes[i].checked){
					isAnswered = true;
					selectedValue += checkboxes[i].value+",";
					var selectedCheckboxId = checkboxes[i].id;//获取ID
					var selectedCheckboxIndex = selectedCheckboxId.substr(-1); //获取最后一位，即是下标值
					selectedCheckboxStr += intToCharArr[selectedCheckboxIndex]+",";
				}
			}
			
			if(!isAnswered){//什么也没有选中
				//回显到“答题情况”区域的“已选选项”中(“已选选项”为空白，否则会出现大小失调的问题)
	    		document.getElementById("refQuesAns"+currentQues).innerHTML = "&nbsp;";
				
				stateAreaColorPattern = stateAreaNormalColor;				
			}
			else{//选中了某些选项，去掉字符串结尾的","
				selectedValue = selectedValue.slice(0, -1);
				selectedCheckboxStr = selectedCheckboxStr.slice(0,-1);

				stuAnswer = selectedValue;
				//回显到“答题情况”区域的“已选选项”中
	    		document.getElementById("refQuesAns"+currentQues).innerHTML = selectedCheckboxStr;

				stateAreaColorPattern = stateAreaAnsweredColor;
			}
			
			if(isMarked){//该试题被标记
				stateAreaColorPattern = stateAreaMarkedColor;
			}
			
    		//改变“答题情况”区域的颜色属性
			$("#refQues"+currentQues).attr("style",stateAreaColorPattern+stateAreaNormalBorder);
		}
	}
	
	//标记试题
	function markQues(){		
		
		var sid = ${sid};		//学生ID
		var eid = ${eid};		//考试ID
		var paperId = data[currentQues].examPaperId;	//试卷ID
		var exerciseId = data[currentQues].exerciseId;	//试题ID
		var targetState = 1 - isMarked;					//目标标记状态 = 1-当前标记状态
		
		$.ajax({
			url:"exam/mark",
			type: "POST",
            headers : {
                'Content-Type' : 'application/json;charset=utf-8'
            },
            data: JSON.stringify({"studentId":sid,"examId":eid,"paperId":paperId,"exerciseId":exerciseId,"state":targetState}),
			success: function(result) {
				var isSuccess = result.success;
				if(isSuccess == false){
					//没成功，暂时不知道干什么
				}
				else{
					//成功，更新界面元素
					if(targetState == 0){
						//取消了原有的标记，改变isMarked的值
						isMarked = false;
						//标记试题按钮的提示语变成“标记试题”，样式变化为蓝色背景
						$("#markBtn").attr("value", "标记试题");
						$("#markBtn").attr("style", "margin-left:-20px; background-color: #28d7af; font-color: white; width: 80px; border: none");
						//“答题情况”区域属性变化
						//需要根据isAnswered来判断该题目是否已经被作答，否则取消标记后不知道“答题情况”区域显示“未答题”还是“已答题”
						if(isAnswered == true){//已经回答过该题目
			    			$("#refQues"+currentQues).attr("style","background-color: #28d7af; color: white; border-radius: 5px; border: 1px dashed #28d7af;");
						}
						else{//还未回答过该题目
							$("#refQues"+currentQues).attr("style","background-color: white; color: rgba(0,0,0,0.37); border-radius: 5px; border: 1px dashed #28d7af;");
						}
					}
					else{
						//标记了试题，改变isMarked的值
						isMarked = true;
						//标记试题按钮的提示语变成“取消标记”，样式变化为橘色背景
						$("#markBtn").attr("value", "取消标记");
						$("#markBtn").attr("style", "margin-left:-20px; background-color: #ea782c; font-color: white; width: 80px; border: none");
						//“答题情况”区域属性变化，显示为“已标记”+“当前题”！！！！（注意这里有一个“当前题”属性，对边框进行处理） （“已标记”拥有最高权限，覆盖其他3种答题情况的内部显示属性）
			    		$("#refQues"+currentQues).attr("style","background-color:#ea782c; color: white; border-radius: 5px; border: 1px dashed #28d7af;");
					}
				}		
			},
			//这种情况应对网络中断导致的错误等
			error: function(err) {
				//alert(err);
				setTimeout(function(){
					$('.pop').empty().remove();
				},2000);
			}
		});	
	}

	
	//提交试题
	function selectQues(){		
		
		var sid = ${sid};		//学生ID
		var eid = ${eid};		//考试ID
		var paperId = data[currentQues].examPaperId;	//试卷ID
		var exerciseId = data[currentQues].exerciseId;	//试题ID
		
		$.ajax({
			url:"exam/select",
			type: "POST",
            headers : {
                'Content-Type' : 'application/json;charset=utf-8'
            },
            data: JSON.stringify({"studentId":sid,"examId":eid,"paperId":paperId,"exerciseId":exerciseId,"studentAnswers":stuAnswer}),
			success: function(result) {
				var isSuccess = result.success;
				if(isSuccess == false){
					//没提交成功，暂时不知道干什么
				}
				else{
					//提交成功，更新界面元素
					//“答题情况”区域属性变化
					if(isMarked == false){//如果没有被标记过，那么可以改变属性
						if(isAnswered == true){//已经回答过该题目
			    			$("#refQues"+currentQues).attr("style","background-color: #28d7af; color: white; border-radius: 5px; border: 1px dashed #28d7af;");
						}
						else{//还未回答过该题目
							$("#refQues"+currentQues).attr("style","background-color: white; color: rgba(0,0,0,0.37); border-radius: 5px; border: 1px dashed #28d7af;");
						}
					}
				}		
			},
			//这种情况应对网络中断导致的错误等
			error: function(err) {
				//alert(err);
				setTimeout(function(){
					$('.pop').empty().remove();
				},2000);
			}
		});	
	}
	
	//提交整张试卷
	function submitPaper(){
		if(window.confirm("考试还未结束，您确定要提交试卷吗？")){
			selectQues();//只需要把当前题目提交即可（也有可能当前题目已经被提交，为了保险起见再提交一次）
			$("#finishBtn").click();
		}
	}
	
</script>

</html>
