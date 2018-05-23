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
        var cid = 0;
        var lid = 0;

        window.onload = function () {
            var name = "${sessionScope.teacher.name}";
            var cName = "${cName}"
            var lName = "${lName}";

            cid = ${cid};
            lid = ${lid};

            <!--教师名-->
            document.getElementById("tName").innerHTML = name;

            <!--课程名-->
            document.getElementById("cName").innerHTML = cName;

            <!--题库名-->
            document.getElementById("lName").innerHTML = lName;
        }

        function upload() {
            var form = new FormData(document.getElementById("fileForm"));
            $.ajax({
                type: "post",
                url: "questionUpload",
                data: form,
                processData:false,
                contentType:false,
                success: function (data) {
                    var d = JSON.parse(data);
                    if(d.success) {
                        document.getElementById("cUpload").setAttribute("value", "已上传");
                        alert("上传成功！点击确定返回题目列表");
                        window.location.href="questions?lid=" + lid;
                    } else {
                        alert(d.errorMessage);
                    }
                },
                complete: function (XMLHttpRequest, textStatus) {
                },
                error: function () {
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
                        "<span id="cName"></span>
                        "&nbsp;的题库
                        &gt;
                        <span id="lName"></span>
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
                        <label style="margin-left: 20px">1.下载试题模板文件</label>
                        <div class="form-group"  style="text-align:left;">
                            <div class="form-inline" style="font-size: 15px">
                                <div><img style="margin-left: 110px" src="img/download.jpg"/><a  href="questionTemplate">试题模板下载</a></div>
                            </div>
                        </div>
                        <label style="margin-left: 20px">2.按照试题模板文件的格式来编辑您的试题</label>
                        <br/>
                        <label style="margin-left: 20px">3.上传您的试题文件</label>
                        <div class="form-group"  style="text-align:left;">
                            <div class="form-inline" style="font-size: 15px">
                                <form id="fileForm">
                                    <input style="margin-left: 95px" type="file" id="inputFile" name="file">
                                    <input type="hidden" name="lid" value="${lid}">
                                </form>

                                <input id="cUpload" class="btn btn-default" value="确认上传" style="margin-left: 140px; margin-top: 20px; width:90px" onclick="upload()">
                            </div>
                        </div>
                    </div>
                    <div>
                        <h3>温馨提示</h3>
                        <label style="margin-left: 20px">1. 请严格按照模板的格式编辑试题，否则会出现上传失败或选项错乱的情况</label>
                        <br/>
                        <label style="margin-left: 20px">2. 一次最多上传50道试题，多出的试题请分多次上传</label>
                        <br/>
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