<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>OnlineES</title>
    <script src="js/jquery.min.js"></script>
    <link type="text/css" rel="stylesheet" href="css/main.css">
    <link type="text/css" href="css/bootstrap.min.css" rel="stylesheet">
    <link type="text/css" href="css/footer.css" rel="stylesheet">

    <style type="text/css">
        ul li {
            list-style: none;
        }
    </style>
    <script type="text/javascript">
        <!--换页函数-->
        var cid = 0;
        var lid = 0;
        var info;


        window.onload = function () {

            var name = "${sessionScope.teacher.name}";
            var lName = "${lName}";
            var cName = "${cName}"

            <!-- 初始化一些全局数据 -->
            cid = ${cid};
            lid = ${sessionScope.lid};
            info = JSON.parse('${info}');


            <!--教师名-->
            document.getElementById("tName").innerHTML = name;

            <!--课程名-->
            document.getElementById("cName").innerHTML = cName;

            <!--题库名-->
            document.getElementById("lName").innerHTML = lName;

            <!--题目内容-->
            document.getElementById("content").innerHTML = info.question;
            var optionsList = info.options;
            var con = "";
            for (var i = 0; i < optionsList.length; i++) {
                if (optionsList[i].validity == 1) {
                    con += "<div><input type='checkbox' style='margin-left: 30px; margin-right: 10px' readonly checked></label>" + optionsList[i].content + "</div><br/>";
                } else {
                    con += "<div><input type='checkbox' style='margin-left: 30px; margin-right: 10px' readonly></label>" + optionsList[i].content + "</div><br/>";
                }
            }
            document.getElementById("options").innerHTML = con;

        }
    </script>

</head>

<body style="background-image: url(img/back.jpg);">
<nav class="navbar navbar-inverse" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header" style="margin-left: 180px">
            <a class="navbar-brand" href="#">OnlineES在线考试平台</a>
        </div>
        <div>
            <ul class="nav navbar-nav">
                <li><a href="exams">首页</a></li>
                <li><a href="exams">我的考试</a></li>
                <li class="active"><a href="courses">我的课程</a></li>
            </ul>
        </div>
        <div>
            <ul class="nav navbar-nav" style="margin-left: 800px">
                <li><img src="img/doge.jpg" class="img-circle"
                         style="height: 40px; width: 40px; margin-top: 5px;  margin-right: 6px"></li>
                <li><label id="teacherName"
                           style="margin-top: 15px; color: white">&nbsp;&nbsp;<span id="tName"></span>&nbsp;&nbsp;&nbsp;</label><span
                        style="color: #9d9d9d">|</span>
                </li>
                <li><a href="quit">注销</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container" style="margin-top: 20px; min-height: 100%; margin-bottom: 80px;">
    <div id="students"
         style="background: white; border-radius: 10px;padding-top:5px; padding-left:15px; padding-right: 15px; padding-bottom: 5px; margin-top: 30px; margin-bottom: 30px"
         ;>
        <div>
            <h2>
                我的课程
                &gt;
                "<span id="cName"></span>
                "&nbsp;的题库
                &gt;
                <span id="lName"></span>
                &gt;试题详情
            </h2>
        </div>
        <div class="container">
            <div class="row">
                <div class="col-md-3"></div>
                <div class="col-md-6" style="margin-top: 40px; margin-bottom: 40px">
                    <h4>内容&nbsp;:&nbsp;<span id="content"></span></h4>
                    <br/>
                    <div id="options"></div>
                </div>
                <div class="col-md-3"></div>
            </div>
        </div>
    </div>
</div>
<footer>
    <div style="line-height: 40px; text-align: center; text-vert: center; color: white; font-size: 14px">
        你猜这是什么群 荣誉出品
    </div>
</footer>

</body>
</html>