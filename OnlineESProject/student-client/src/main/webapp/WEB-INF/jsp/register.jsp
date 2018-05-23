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
				<div class="form-group" style="text-align:center; font-size:20px; margin-top:40px; margin-bottom:30px; color:white">
					欢迎加入OnlineES
				</div>
				
				<form id="registerForm">
					<div class="form-group" style="text-align:center; color:white">
						<div class="form-inline">
							学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;校 : 
							<select name="school" id="sel_school" class="js-data-example-ajax form-control" style="width:200px;" onchange="showBelongingInstitutions()">
								<option value="" selected="true" disabled="true">选择学校</option>
							</select>
							<span id="schoolErrMsg"></span>		
						</div>
					</div>

					<div class="form-group" style="text-align:center; color:white">
						<div class="form-inline">
							院&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;系: 
							<select name="institution" id="sel_institution" class="js-data-example-ajax form-control" style="width:200px;" onchange="showBelongingGrades()" disabled="disabled">
								<option value="" selected="true" disabled="true">选择院系</option>
							</select>
							<span id="institutionErrMsg"></span>		
						</div>
					</div>
					
					<div class="form-group" style="text-align:center; color:white">
						<div class="form-inline">
							年&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;级 : 
							<select name="grade" id="sel_grade" class="js-data-example-ajax form-control" style="width:200px;"  onchange="showBelongingClasses()" disabled="disabled">
								<option value="" selected="true" disabled="true">选择年级</option>
							</select>
							<span id="gradeErrMsg"></span>		
						</div>
					</div>
					
					<div class="form-group" style="text-align:center; color:white">
						<div class="form-inline">
							班&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;级 : 
							<select name="class" id="sel_class" class="js-data-example-ajax form-control" style="width:200px;" disabled="disabled">
								<option value="" selected="true" disabled="true">选择班级</option>
							</select>
							<span id="classErrMsg"></span>		
						</div>
					</div>
					
					<div class="form-group" style="text-align:center; color:white">
						<div class="form-inline">
							学&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号 : <input type="text" class="form-control" name="id" id="id" style="width:200px; height:27px;">
							<span id="idErrMsg"></span>		
						</div>
					</div>

					<div class="form-group" style="text-align:center; color:white">
						<div class="form-inline">
							姓&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;名 : <input type="text" class="form-control" name="name" id="name" style="width:200px; height:27px;">
							<span id="nameErrMsg"></span>		
						</div>
					</div>
					
					<div class="form-group" style="text-align:center; color:white">
						<div class="form-inline">
							电子邮箱 : <input type="text" class="form-control" name="mailbox" id="mailbox" style="width:200px; height:27px;">
							<span id="emailErrMsg"></span>		
						</div>
					</div>
					
					<div class="form-group" style="text-align:center; color:white">
						<div class="form-inline">
							密&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;码 : <input type="password" class="form-control" name="password" id="password" style="width:200px; height:27px;">
							<span id="passwdErrMsg"></span>
						</div>
					</div>
					
					<div class="form-group" style="text-align:center; color:white">
						<div class="form-inline">
							确认密码 : <input type="password" class="form-control" id="cpassword" style="width:200px; height:27px;">
							<span id="cpasswdErrMsg"></span>
						</div>
					</div>
					
					<div class="form-group" style="text-align:center; margin-top:30px; margin-bottom:40px">
						<div class="form-inline">
							<button type="button" id="registerBtn" class="btn btn-info btn-sm" style="width:35%; font-size:14px;" onclick="verifyAndSubmit()">注册</button>
						</div>
					</div>					
				</form>
				<!-- 如果因为网络不通而无法注册，错误信息显示在这里  -->
				<div><p id="errNetMsg" class='pop'>请检查您的网络连接</p></div>
			</div>
		</div>
	</div>

</body>

<script>

	/*------------------------------------------------------------------------ 删除原有的select2部分  --------------------------------------------------*/
	/*         
	$(function () {
		var schoolData = ["北京大学", "复旦大学", "南京大学", "清华大学", "上海交通大学", "浙江大学", "中国科学技术大学", "其他"];
        var institutionData = ["化学系", "物理系", "数学系", "生命科学学院", "计算机科学系", "软件学院", "文学院", "哲学系", "其他"];
        var gradeData = ["大一", "大二", "大三", "大四", "其他"];
        var classData = ["1班", "2班", "3班", "4班", "其他"]; 
        
	    $("#sel_school").select2({
	    	data:schoolData,
	    	language: "zh-CN",
	    	allowClear:false, //不允许清空
	        escapeMarkup: function (markup) { return markup; }, // 自定义格式化防止xss注入
	        minimumInputLength: 0, //最少输入多少个字符后开始查询
	    });
	    $("#sel_school").on("select2:close",function(){  //每当下拉框关闭时就判断是否选了学校，若用“change”事件，最开始无法报错
			var data = $(this).val();  
			if(data==undefined || data=="school"){
				document.getElementById("schoolErrMsg").innerHTML="<font color='red'>请至少选择一个学校</font>";
				document.getElementById("submit").disabled = true;	
			}
			else{
				document.getElementById("schoolErrMsg").innerHTML="<font color='red'></font>";
				document.getElementById("submit").disabled &= false;
			}
		}); 
        
	    $("#sel_institution").select2({
	    	data:institutionData,
	    	language: "zh-CN",
	    	allowClear:false, //不允许清空
	        escapeMarkup: function (markup) { return markup; }, // 自定义格式化防止xss注入
	        minimumInputLength: 0, //最少输入多少个字符后开始查询
	    });
	    $("#sel_institution").on("select2:close",function(){  //每当下拉框关闭时就判断是否选了学院，若用“change”事件，最开始无法报错
			var data = $(this).val();  
			if(data==undefined || data=="institution"){
				document.getElementById("institutionErrMsg").innerHTML="<font color='red'>请至少选择一个学院</font>";
				document.getElementById("submit").disabled = true;	
			}
			else{
				document.getElementById("institutionErrMsg").innerHTML="<font color='red'></font>";
				document.getElementById("submit").disabled &= false;
			}
		}); 
	    
	    $("#sel_grade").select2({
	    	data:gradeData,
	    	language: "zh-CN",
	    	allowClear:false, //不允许清空
	        escapeMarkup: function (markup) { return markup; }, // 自定义格式化防止xss注入
	        minimumInputLength: 0, //最少输入多少个字符后开始查询
	    });
	    $("#sel_grade").on("select2:close",function(){  //每当下拉框关闭时就判断是否选了年级，若用“change”事件，最开始无法报错
			var data = $(this).val();  
			if(data==undefined || data=="grade"){
				document.getElementById("gradeErrMsg").innerHTML="<font color='red'>请至少选择一个年级</font>";
				document.getElementById("submit").disabled = true;	
			}
			else{
				document.getElementById("gradeErrMsg").innerHTML="<font color='red'></font>";
				document.getElementById("submit").disabled &= false;
			}
		}); 
	    
	    $("#sel_class").select2({
	    	data:classData,
	    	language: "zh-CN",
	    	allowClear:false, //不允许清空
	        escapeMarkup: function (markup) { return markup; }, // 自定义格式化防止xss注入
	        minimumInputLength: 0, //最少输入多少个字符后开始查询
	    });
	    $("#sel_class").on("select2:close",function(){  //每当下拉框关闭时就判断是否选了班级，若用“change”事件，最开始无法报错
			var data = $(this).val();  
			if(data==undefined || data=="class"){
				document.getElementById("classErrMsg").innerHTML="<font color='red'>请至少选择一个班级</font>";
				document.getElementById("submit").disabled = true;	
			}
			else{
				document.getElementById("classErrMsg").innerHTML="<font color='red'></font>";
				document.getElementById("submit").disabled &= false;
			}
		});  
	
	});
	*/

	/*------------------------------------------------------------------------ 换成select的代码   --------------------------------------------------*/
	
	
	$(function (){
		//弹出框的消息（提示网络异常）不显示
		$('.pop').empty();
		
		//加载学校列表
		$.ajax({
	    	url: "schools/list",
	        async: false,
	        type: "GET",
	        dataType: "json",
	        success: function (result) {
	        	var data = result.data;
	        	var str = "<option value='invalid'>选择学校</option>";
	            for (var i = 0; i < data.length; i++) {
	                str += "<option value='"+data[i].id+"'>" + data[i].name + "</option>";
	            }
	            $("#sel_school").html(str);
	        }
	    });
	});    
	
	//根据选择的学校加载对应的院系列表
	function showBelongingInstitutions(){
		var obj = document.getElementById("sel_school");
		var selectIndex = obj.selectedIndex;
		var selectSchoolId = obj.options[selectIndex].value;
		
		$.ajax({
	    	url: "institutions/list/"+selectSchoolId,
	        async: false,
	        type: "GET",
	        dataType: "json",
	        success: function (result) {
	        	var data = result.data;
	        	var str = "<option value='invalid'>选择院系</option>";
	            for (var i = 0; i < data.length; i++) {
	                str += "<option value='"+data[i].id+"'>" + data[i].name + "</option>";
	            }
	            $("#sel_institution").html(str);
	        }
	    });
		
		$("#sel_institution").prop("disabled", "");
	}
	
	//根据选择的学校、院系加载对应的年级列表
	function showBelongingGrades(){
		var obj = document.getElementById("sel_institution");
		var selectIndex = obj.selectedIndex;
		var selectInstitutionId = obj.options[selectIndex].value;
		
		$.ajax({
	    	url: "grades/list/"+selectInstitutionId,
	        async: false,
	        type: "GET",
	        dataType: "json",
	        success: function (result) {
	        	var data = result.data;
	        	var str = "<option value='invalid'>选择年级</option>";
	            for (var i = 0; i < data.length; i++) {
	                str += "<option value='"+data[i].id+"'>" + data[i].name + "</option>";
	            }
	            $("#sel_grade").html(str);
	        }
	    });
		
		$("#sel_grade").prop("disabled", "");
	}
	
	//根据选择的学校、院系、年级加载对应的班级列表
	function showBelongingClasses(){
		var obj = document.getElementById("sel_grade");
		var selectIndex = obj.selectedIndex;
		var selectGradeId = obj.options[selectIndex].value;
		
		$.ajax({
	    	url: "classes/list/"+selectGradeId,
	        async: false,
	        type: "GET",
	        dataType: "json",
	        success: function (result) {
	        	var data = result.data;
	        	var str = "<option value='invalid'>选择班级</option>";
	            for (var i = 0; i < data.length; i++) {
	                str += "<option value='"+data[i].id+"'>" + data[i].name + "</option>";
	            }
	            $("#sel_class").html(str);
	        }
	    });
		
		$("#sel_class").prop("disabled", "");
	}
	
	
	//验证学校的值是否合法
	function verifySchool(){
		var obj = document.getElementById("sel_school");
		var selectIndex = obj.selectedIndex;
		if(selectIndex == 0){
			document.getElementById("schoolErrMsg").innerHTML = "<font color='red'>请至少选择一个学校</font>";
			return false;
		}
		else{
			document.getElementById("schoolErrMsg").innerHTML = "<font color='red'></font>";
			return true;
		}
	}
	
	//验证院系的值是否合法
	function verifyInstitution(){
		var obj = document.getElementById("sel_institution");
		var selectIndex = obj.selectedIndex;
		if(selectIndex == 0){
			document.getElementById("institutionErrMsg").innerHTML = "<font color='red'>请至少选择一个院系</font>";
			return false;
		}
		else{
			document.getElementById("institutionErrMsg").innerHTML = "<font color='red'></font>";
			return true;
		}
	}
	
	//验证年级的值是否合法
	function verifyGrade(){
		var obj = document.getElementById("sel_grade");
		var selectIndex = obj.selectedIndex;
		if(selectIndex == 0){
			document.getElementById("gradeErrMsg").innerHTML = "<font color='red'>请至少选择一个年级</font>";
			return false;
		}
		else{
			document.getElementById("gradeErrMsg").innerHTML = "<font color='red'></font>";
			return true;
		}
	}
	
	//验证班级的值是否合法
	function verifyClass(){
		var obj = document.getElementById("sel_class");
		var selectIndex = obj.selectedIndex;
		if(selectIndex == 0){
			document.getElementById("classErrMsg").innerHTML = "<font color='red'>请至少选择一个班级</font>";
			return false;
		}
		else{
			document.getElementById("classErrMsg").innerHTML = "<font color='red'></font>";
			return true;
		}
	}
	
	//验证学号的值是否合法
	function verifyId(){
		var id = document.getElementById("id").value;
		if(id == ""){
			document.getElementById("idErrMsg").innerHTML = "<font color='red'>学号不能为空</font>";
			return false;
		}
		else{
			document.getElementById("idErrMsg").innerHTML = "<font color='red'></font>";
			return true;
		}
	}

	//验证姓名的值是否合法
	function verifyName(){
		var name = document.getElementById("name").value;
		if(name == ""){
			document.getElementById("nameErrMsg").innerHTML = "<font color='red'>姓名不能为空</font>";
			return false;		  
		}
		else{
			document.getElementById("nameErrMsg").innerHTML = "<font color='red'></font>";
			return true;
		}
	}
	
	//验证邮箱的值是否合法
	function verifyEmail(){
		var emailAddress = document.getElementById("mailbox").value;
		var regex =  /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		if(!regex.test(emailAddress)){
			document.getElementById("emailErrMsg").innerHTML = "<font color='red'>邮箱格式错误</font>";
			return false;		  
		}
		else{
			document.getElementById("emailErrMsg").innerHTML = "<font color='red'></font>";
			return true;
		}
	}
	
	//验证密码的值是否合法
	function verifyPasswd(){
		var passwd = document.getElementById("password").value;
		if(passwd == ""){
			document.getElementById("passwdErrMsg").innerHTML = "<font color='red'>密码不能为空</font>";
			return false;		  
		}
		else{
			document.getElementById("passwdErrMsg").innerHTML = "<font color='red'></font>";
			return true;
		}
	}
	
	//验证两次输入密码的值是否一致
	function verifyCpasswd(){
		var passwd = document.getElementById("password").value;
		var cpasswd = document.getElementById("cpassword").value;
		if(passwd != cpasswd){
			document.getElementById("cpasswdErrMsg").innerHTML = "<font color='red';>两次密码不一致</font>";
			return false;
		}
		else{
			document.getElementById("cpasswdErrMsg").innerHTML = "<font color='red';></font>";
			return true;
		}
	}
	
	function verifyAndSubmit(){
		//这里用位运算符&，而不是逻辑运算符&&，是为了保证每一个方法都能运行到，一次性显示出全部的错误信息
		if(verifySchool() & verifyInstitution() & verifyGrade() & verifyClass() & 
		   verifyId() & verifyName() & verifyEmail() & verifyPasswd() & verifyCpasswd()){
			//所有数据都通过了验证，可以提交表单了
			$.ajax({
				cache: true,
				url:"registerAction",
				type: "POST",
				data:$('#registerForm').serialize(),
				success: function(result) {
					//alert(result);
					var resultObj = eval("("+result+")");
					var isSuccess = resultObj.success;
					
					if(isSuccess == false){
						var errMsg = resultObj.errorMessage;
						if(errMsg.charAt(0) == 'E'){
							document.getElementById("emailErrMsg").innerHTML = "<font color='red'>该邮箱已经被人注册</font>";
							document.getElementById("idErrMsg").innerHTML = "<font color='red'></font>";
						}
						else if(errMsg.charAt(0) == 'N'){
							document.getElementById("emailErrMsg").innerHTML = "<font color='red'></font>";
							document.getElementById("idErrMsg").innerHTML = "<font color='red'>该学号已经被人注册</font>";
						}
						else{
							
						}
					}
					else{
						//注册成功，跳转到激活页面
						var stuData = resultObj.data;
						var stuSid = stuData.id;
						var stuMail = stuData.mail;
						
						//跳转到activate界面
						window.location.href = "activate";
					}
				},
				//这种情况应对网络中断导致的错误等
				error: function(err) {
					//alert(err);
					document.getElementById("emailErrMsg").innerHTML = "<font color='red'></font>";
					document.getElementById("idErrMsg").innerHTML = "<font color='red'></font>";
					setTimeout(function(){
						$('.pop').empty().remove();
					},2000);
				}
			});
		}
		else{
			//某些数据都没能通过验证，留在本界面
			return ;
		}
	}

</script>

</html>