<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>OnlineES</title>
    <script src="js/jquery.min.js"></script>
    <link type="text/css" rel="stylesheet" href="css/main.css">
    <link type="text/css" href="css/bootstrap.min.css" rel="stylesheet">
    <link type="text/css" href="css/footer.css" rel="stylesheet">

    <!-- time picker-->
    <script src="https://cdn.bootcss.com/moment.js/2.18.1/moment-with-locales.min.js"></script>
    <link href="https://cdn.bootcss.com/bootstrap-datetimepicker/4.17.47/css/bootstrap-datetimepicker.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/bootstrap-datetimepicker/4.17.47/js/bootstrap-datetimepicker.min.js"></script>

    <!--java-->
    <script type="text/javascript">

        var info;
        var cid = 0;
        var selection;


        function reloadScore() {

            var singleNumV = document.getElementById("singleNum").value;
            var singleScoreV = document.getElementById("singleScore").value;
            var multiNumV = document.getElementById("multiNum").value;
            var multiScoreV = document.getElementById("multiScore").value;
            var singleTotal = parseInt(document.getElementById("singleTotal").value);
            var multiTotal = parseInt(document.getElementById("multiTotal").value);


            if(singleNumV == "" || singleNumV == null)
                singleNumV = 0;
            if(singleNumV < 0) {
                alert("题目数量不得小于零!")
                singleNumV = 0;
                document.getElementById("singleNum").value = 0;
            }

            if(singleScoreV == "" || singleScoreV == null)
                singleScoreV = 0;
            if(singleScoreV < 0) {
                singleScoreV = 0;
                alert("分值不得小于零!")
                document.getElementById(" singleScore").value = 0;
            }

            if(multiNumV == "" || multiNumV == null)
                multiNumV = 0;
            if(multiNumV < 0) {
                multiNumV = 0;
                alert("题目数量不得小于零!")
                document.getElementById("multiNum").value = 0;
            }

            if(multiScoreV == "" || multiScoreV == null)
                multiScoreV = 0;
            if(multiScoreV < 0) {
                multiScoreV = 0;
                alert("分值不得小于零!");
                document.getElementById(" multiScore").value = 0;
            }

            if(singleNumV > singleTotal) {
                alert("单选题数量超过题库限制，请添加试题来源的题库");
                document.getElementById("singleNum").value = singleTotal;
            }

            if(multiNumV > multiTotal) {
                alert("多选题数量超过题库限制，请添加试题来源的题库");
                document.getElementById("multiNum").value = multiTotal;
            }

            document.getElementById("totalScore").value = singleNumV * singleScoreV + multiNumV * multiScoreV;

        }

        function reloadTotal(i) {
            var singleTotal = parseInt(document.getElementById("singleTotal").value);
            var multiTotal = parseInt(document.getElementById("multiTotal").value);
            if(selection[i] == 0) {
                selection[i] = 1;
                singleTotal += info[i].single;
                multiTotal += info[i].multi;
            } else {
                selection[i] = 0;
                singleTotal -= info[i].single;
                multiTotal -= info[i].multi;
            }
            document.getElementById("singleTotal").value = singleTotal;
            document.getElementById("multiTotal").value = multiTotal;
        }

        window.onload = function () {

            var tName = "${sessionScope.teacher.name}";
            var cName = "${cName}";
            info = JSON.parse('${infos}');
            cid = ${cid};
            selection = new Array(info.length);
            selection.fill(0);


            <!--教师名-->
            document.getElementById("tName").innerHTML = tName;

            <!--课程名-->
            document.getElementById("cName").innerHTML = cName;

            <!--题库-->
            var innerText = "";
            for(var i = 0; i < info.length; i++) {
                innerText += "<input type='checkbox' onclick='reloadTotal(" + i + ")'>" + info[i].name + "<span style='margin-left: 10px'></span>";
            }
            var libs = document.getElementById("libs").innerHTML = innerText;
        }

        function upload() {
            var form = new FormData(document.getElementById("fileForm"));
            $.ajax({
                type: "post",
                url: "createUpload",
                data: form,
                processData:false,
                contentType:false,
                success: function (data) {

                    var d = JSON.parse(data);
                    if(d.success) {
                        document.getElementById("cUpload").setAttribute("value", "已上传");
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

        function create() {
            var eName = document.getElementById("eName").value;
            var sTime = document.getElementById("time1").value;
            var eTime = document.getElementById("time2").value;

            var singleNumV = document.getElementById("singleNum").value;
            var singleScoreV = document.getElementById("singleScore").value;
            var multiNumV = document.getElementById("multiNum").value;
            var multiScoreV = document.getElementById("multiScore").value;

            if(eName == null || eName == "") {
                alert("请输入考试名称");
                return;
            }
            if(sTime == null || sTime == "") {
                alert("请输入考试开始时间");
                return;
            }
            if(eTime == null || eTime == "") {
                alert("请输入考试结束时间");
                return;
            }
            if(sTime >= eTime) {
                alert("开始时间必须小于结束时间");
                return;
            }
            if(singleNumV == 0 || singleScoreV == 0|| multiNumV ==0 || multiScoreV == 0 || singleNumV == null || singleNumV == "" || singleScoreV == null || singleScoreV == "" || multiNumV == null || multiNumV == "" || multiScoreV == null || multiScoreV == "") {
                alert("请输入有效的题目数和分数");
                return;
            }



            var i = 0;
            for(var j = 0; j < selection.length; j++) {
                if(selection[j] == 1) {
                    i++;
                }
            }

            var k = 0;
            var ids = "";
            for(var j = 0; j < selection.length; j++) {
                if(selection[j] == 1) {
                    ids += info[j].id;
                    if(k != i - 1) {
                        ids += ",";
                    }
                    k++;
                }
            }


            $.ajax({
                type: "post",
                url: "createAction",
                data: {"cid": cid, "eName": eName, "sTime": sTime, "eTime": eTime, "singleNum": singleNumV, "singleScore": singleScoreV, "multiNum": multiNumV, "multiScore": multiScoreV, "ids": ids},
                dataType: 'html',
                success: function (data) {
                    var d = JSON.parse(data);
                    if(d.success) {
                        alert("创建成功!");
                        clean();
                        window.location.href = "courses";
                    } else {
                        alert(d.errorMessage);
                    }
                },
                complete: function (XMLHttpRequest, textStatus) {
                },
                error: function () {
                    alert("创建失败，请检查学生列表的格式");
                }
            });
        }

        function clean() {
            document.getElementById("eName").value = "";
            document.getElementById("time1").value = "";
            document.getElementById("time2").value = "";

            document.getElementById("singleNum").value = 0;
            document.getElementById("singleScore").value = 0;
            document.getElementById("multiNum").value = 0;
            document.getElementById("multiScore").value = 0;

            document.getElementById("totalScore").value = 0;
            document.getElementById("singleTotal").value = 0;
            document.getElementById("multiTotal").value = 0;

            document.getElementById("cUpload").value = "确认上传";

            var libs = document.getElementById("libs").getElementsByTagName("input");
            for(var i = 0 ; i < libs.length; i++) {
                libs[i].checked = false;
            }

            document.getElementById("fileForm").innerHTML = '<input style="margin-left: 68px" type="file" id="inputFile" name="file">';
        }

        $(function () {
            $('#date1').datetimepicker({
                format: 'YYYY-MM-DD HH:mm:ss',
                locale: moment.locale('zh-cn')
            });

            $('#date2').datetimepicker({
                format: 'YYYY-MM-DD HH:mm:ss',
                locale: moment.locale('zh-cn')
            });
        });

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
                        "&nbsp;创建考试
                    </h2>
                </div>
            </div>

            <HR style="FILTER: alpha(opacity=100,finishopacity=0,style=2)" width="80%" color=#987cb9 SIZE=10>

            <div class="row" style="line-height:50px">
                <div class="col-md-4">
                </div>
                <div class="col-md-4">
                    <div style="text-align: center; font-size: 20px; margin-bottom: 10px">基础设置</div>
                    <div class="form-group" style="text-align:left;">
                        <div class="form-inline" style="font-size: 15px">
                            考试名称&nbsp;&nbsp;<input type="text" class="form-control" style="width: 218px" id="eName">
                        </div>
                    </div>
                    <div class="form-group" style="text-align:left;">
                        <div class="form-inline" style="font-size: 15px">
                            开始时间&nbsp;
                            <div class='input-group date' id='date1'>
                                <input type='text' class="form-control" id="time1"/>
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group" style="text-align:left;">
                        <div class="form-inline" style="font-size: 15px">
                            结束时间&nbsp;
                            <div class='input-group date' id='date2'>
                                <input type='text' class="form-control" id="time2"/>
                                <span class="input-group-addon">
                                    <span class="glyphicon glyphicon-calendar"></span>
                                </span>
                            </div>
                        </div>
                    </div>
                    <div class="form-group"  style="text-align:left;">
                        <div class="form-inline" style="font-size: 15px">
                            <div>学生名单&nbsp;&nbsp;<img style="margin-left: 44px" src="img/download.jpg"/><a href="createTemplate">学生名单模板下载</a></div>
                        </div>
                    </div>
                    <div class="form-group"  style="text-align:left;">
                        <div class="form-inline" style="font-size: 15px">
                            <form id="fileForm">
                                <input style="margin-left: 68px" type="file" id="inputFile" name="file">
                            </form>
                            <input id="cUpload" class="btn btn-default" value="确认上传" style="margin-left: 120px; margin-top: 20px; width:90px" onclick="upload()">
                        </div>
                    </div>

                </div>
                <div class="col-md-4">
                </div>
            </div>
            <HR style="FILTER: alpha(opacity=100,finishopacity=0,style=2)" width="80%" color=#987cb9 SIZE=10>

            <div class="row" style="line-height:50px; margin-top: 10px">
                <div class="col-md-3">
                </div>
                <div class="col-md-6">
                    <div style="text-align: center; font-size: 20px;  margin-bottom: 10px">试题设置</div>
                    <div class="form-group" style="text-align:left;">
                        <div class="form-inline" style="font-size: 15px">
                            单选题个数&nbsp;&nbsp;<input type="number" class="form-control" onchange="reloadScore()" id="singleNum">
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            每题分值&nbsp;&nbsp;<input type="number" class="form-control" onchange="reloadScore()" id="singleScore">
                        </div>
                    </div>
                    <div class="form-group" style="text-align:left;">
                        <div class="form-inline" style="font-size: 15px">
                            多选题个数&nbsp;&nbsp;<input type="number" class="form-control" onchange="reloadScore()" id="multiNum">
                            &nbsp;&nbsp;&nbsp;&nbsp;
                            每题分值&nbsp;&nbsp;<input type="number" class="form-control" onchange="reloadScore()" id="multiScore">
                        </div>
                    </div>
                    <div class="form-group" style="text-align:left;">
                        <div class="form-inline" style="font-size: 15px">
                            总&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分&nbsp;&nbsp;<input type="text" class="form-control" readonly value=0 id="totalScore">
                        </div>
                    </div>
                    <div class="form-group" style="text-align:left;">
                        <div class="form-inline row" style="font-size: 15px" >
                            <div class="col-md-2">试&nbsp;题&nbsp;来&nbsp;源</div>
                            <div class="col-md-10" id="libs" style="text-align: left"></div>
                        </div>
                    </div>
                    <div class="form-group" style="text-align:left;">
                        <div class="form-inline" style="font-size: 15px">
                            单选题总数&nbsp;&nbsp;<input type="number" class="form-control" readonly value=0 id="singleTotal">
                        </div>
                    </div>
                    <div class="form-group" style="text-align:left;">
                        <div class="form-inline" style="font-size: 15px">
                            多选题总数&nbsp;&nbsp;<input type="number" class="form-control" readonly value=0 id="multiTotal">
                        </div>
                    </div>

                    <div class="form-group" style="text-align:center; margin-top: 40px; margin-bottom: 40px;">
                        <div class="form-inline" style="font-size: 15px">
                            <button class="btn btn-default btn-lg" onclick="create()">创建考试</button>
                        </div>
                    </div>

                </div>
                <div class="col-md-3">
                </div>
            </div>
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