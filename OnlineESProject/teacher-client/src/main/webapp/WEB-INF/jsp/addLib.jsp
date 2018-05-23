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

    <!--java-->
    <script type="text/javascript">

        var sid;
        var eid;


        window.onload = function () {

            var tName = "${sessionScope.teacher.name}";
            var info = JSON.parse('${course}');
            cid = info.id;


            <!--教师名-->
            document.getElementById("tName").innerHTML = tName;

            <!--课程名-->
            document.getElementById("cName").innerHTML = info.name;
        }

        function create() {
            var name = document.getElementById("lName").value;
            if(name == "") {
                alert("请输入题库名称!");
                return;
            }
            $.ajax({
                type: "post",
                url: "libAdd",
                data: "cid=" + cid + "&name=" + name,
                dataType: 'html',
                success: function (data) {
                    var d = JSON.parse(data);
                    if(d.success) {
                        alert("创建成功!点击确定返回题库列表");
                        window.location.href = "libs?cid=" + cid;
                    } else {
                        alert(d.errorMessage);
                    }
                },
                complete: function (XMLHttpRequest, textStatus) {
                },
                error: function () {
                    alert("发生了未知的错误");
                }
            });
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
                         style="height: 40px; width: 40px; margin-top: 5px; margin-right: 6px"></li>
                <li><label id="teacherName"
                           style="margin-top: 15px; color: white">&nbsp;&nbsp;<span id="tName"></span>&nbsp;&nbsp;&nbsp;</label>
                    <span style="color: #9d9d9d">|</span>
                </li>
                <li><a href="quit">注销</a></li>
            </ul>
        </div>
    </div>
</nav>
<div class="container" style="margin-top: 40px; margin-bottom: 80px; min-height: 100%;">
    <div id="exam_info" style="background: white; border-radius: 10px;padding-top:5px; padding-bottom:5px">
        <div class="container">
            <div class="row">
                <div class="col-md-12">
                    <h2>
                        <a href="courses">我的课程</a>
                        &gt;
                        <a href="libs?cid=${cid}">
                        "<span id="cName"></span>
                        "&nbsp;的题库
                        </a>
                        &gt;新增题库
                    </h2>
                </div>
            </div>

            <HR style="FILTER: alpha(opacity=100,finishopacity=0,style=2)" width="80%" color=#987cb9 SIZE=10>

            <div class="row" style="line-height:50px">
                <div class="col-md-3">
                </div>
                <div class="col-md-6">
                    <div>
                        <h3>步骤说明</h3>
                        <label>1. 输入新题库名称，点击“确认”按钮创建新题库</label>
                        <br/>
                        <label>2. 题库创建成功后，请在题库列表中找到对应的新题库，点击“查看详情”按钮进入详情页面</label>
                        <br/>
                        <label> 3. 点击“新增试题”按钮来导入试题</label>
                    </div>
                    <div>
                        <div class="form-group">
                            <div class="form-inline" style="font-size: 15px">
                                <h3>输入题库名称&nbsp;&nbsp;<input type="text" class="form-control" style="width: 218px" id="lName"></h3>
                            </div>
                            <div class="form-inline" style="font-size: 15px; margin-top: 30px">
                                <input class="btn btn-primary btn-ms" style="width: 100px; margin-left: 220px" value="确定" onclick="create()">
                            </div>
                        </div>

                    </div>
                </div>
                <div class="col-md-3">
                </div>
            </div>
            <HR style="FILTER: alpha(opacity=100,finishopacity=0,style=2)" width="80%" color=#987cb9 SIZE=10>


        </div>
    </div>
</div>

<footer>
    <div
            style="line-height: 40px; text-align: center; text-vert: center; color: white; font-size: 14px">
        你猜这是什么群 荣誉出品
    </div>
</footer>

</body>
</html>