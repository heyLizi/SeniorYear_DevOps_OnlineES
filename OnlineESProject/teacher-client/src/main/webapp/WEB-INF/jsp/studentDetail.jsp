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
        <!-- 换页函数-->
        var curPage = 0;
        var pages = 0;
        var eid = 0;
        var sid = 0;
        var epid = 0;
        var state = 0;

        function changePage(page) {
            <!--通过ajax获取数据，然后调用loadInfo载入，最后改变换页条状态-->
            <!-- 0:第一页 -1:上一页 -2:下一页 -3:最后一页-->
            var reqPage;
            switch (page) {
                case 0:
                    curPage = 0;
                    break;
                case -1:
                    if (curPage > 0) {
                        curPage--;
                    }
                    break;
                case -2:
                    if (curPage < pages - 1) {
                        curPage++;
                    }
                    break;
                case -3:
                    curPage = pages - 1;
                    break;
                default:
                    if (page > 0) {
                        curPage = page;
                    }
                    break;
            }
            reqPage = curPage;

            $.ajax({
                type: "get",
                url: "questionList",
                data: "eid=" + eid + "&sid=" + sid + "&page=" + reqPage,
                dataType: 'html',
                success: function (data) {
                    var info = JSON.parse(data).data;
                    infos = info;
                    loadInfo(infos);


                    if (reqPage - 2 >= 0) {
                        var cur = document.getElementById("page1");
                        cur.parentNode.setAttribute("style", "");
                        cur.parentNode.setAttribute("class", "btn btn-default")
                        document.getElementById("option1").onclick = function () {
                            changePage(reqPage - 2)
                        };
                        cur.innerHTML = reqPage - 1;
                    } else {
                        var cur = document.getElementById("page1");
                        cur.parentNode.setAttribute("style", "display: none")
                    }

                    if (reqPage - 1 >= 0) {
                        var cur = document.getElementById("page2");
                        cur.parentNode.setAttribute("style", "");
                        cur.parentNode.setAttribute("class", "btn btn-default");
                        document.getElementById("option2").onclick = function () {
                            changePage(reqPage - 1)
                        };
                        cur.innerHTML = reqPage;
                    } else {
                        var cur = document.getElementById("page2");
                        cur.parentNode.setAttribute("style", "display: none")
                    }

                    {
                        var cur = document.getElementById("page3");
                        cur.parentNode.setAttribute("style", "");
                        cur.parentNode.setAttribute("class", "btn btn-default active")
                        document.getElementById("option3").onclick = function () {
                            changePage(reqPage)
                        };
                        cur.innerHTML = reqPage + 1;
                    }

                    if (reqPage + 1 <= pages - 1) {
                        var cur = document.getElementById("page4");
                        cur.parentNode.setAttribute("style", "");
                        cur.parentNode.setAttribute("class", "btn btn-default");
                        document.getElementById("option4").onclick = function () {
                            changePage(reqPage + 1)
                        };
                        cur.innerHTML = reqPage + 2;
                    } else {
                        var cur = document.getElementById("page4");
                        cur.parentNode.setAttribute("style", "display: none")
                    }

                    if (reqPage + 2 <= pages - 1) {
                        var cur = document.getElementById("page5");
                        cur.parentNode.setAttribute("style", "");
                        cur.parentNode.setAttribute("class", "btn btn-default");
                        document.getElementById("option5").onclick = function () {
                            changePage(reqPage + 2)
                        };
                        cur.innerHTML = reqPage + 3;
                    } else {
                        var cur = document.getElementById("page5");
                        cur.parentNode.setAttribute("style", "display: none")
                    }
                },
                complete: function (XMLHttpRequest, textStatus) {
                },
                error: function () {
                }
            });

        }

        <!--替换页面中的考试信息-->
        function loadInfo(info) {
            var trs = document.getElementById("questionInfo").getElementsByTagName("tr");
            var i;
            for (i = 0; i < info.length; i++) {
                var num = curPage * 12 + i + 1;
                var typeName = info[i].typeName;
                var question = info[i].question;

                if(question.length > 30) {
                    question = question.substring(0, 27) + "...";
                }

                var options = "";
                for (var j = 0; j < info[i].options.length; j++) {
                    options += String.fromCharCode(65 + j) + "." + info[i].options[j].content + " ";
                }

                if(options.length > 30) {
                    options = options.substring(0, 27) + "...";
                }


                var trueAnswer = info[i].tAnswer;
                var studentAnswer = info[i].sAnswer;
                var sScore = info[i].sScore;


                var tds = trs[i + 1].getElementsByTagName("td");
                tds[0].innerHTML = num;
                tds[1].innerHTML = typeName;
                tds[2].innerHTML = question;
                tds[3].innerHTML = options;
                tds[4].innerHTML = trueAnswer;
                tds[5].innerHTML = studentAnswer;
                tds[6].innerHTML = sScore;
                tds[7].innerHTML = '<button class="pull-right btn btn-primary btn-xs" style="margin-right: 5px">查看详情</button>';

            }

            for (; i < 12; i++) {
                var tds = trs[i + 1].getElementsByTagName("td");
                for (var j = 0; j < 8; j++) {
                    tds[j].innerHTML = "-";
                }
            }
        }

        function loadExam(info) {
            document.getElementById("eName").innerHTML = info.eName;
            document.getElementById("cName").innerHTML = info.courseName;
            document.getElementById("grade").innerHTML = info.gradeName;
            if (state == 1) {
                document.getElementById("state").innerHTML = "考试中";
                document.getElementById("state").setAttribute("style", "background-color: #28D7AF; color: white");
            } else if (state == 2) {
                document.getElementById("state").innerHTML = "已评分";
                document.getElementById("state").setAttribute("style", "background-color: #33ccff; color: white");
            } else {
                document.getElementById("state").innerHTML = "缺/弃考";
                document.getElementById("state").setAttribute("style", "background-color: #c30; color: white");
            }

            document.getElementById("sName").innerHTML = info.sName;
            if (state == 2)
                document.getElementById("score").innerHTML = info.score;
            else
                document.getElementById("score").innerHTML = "-";
            document.getElementById("totalScore").innerHTML = info.totalScore;
            document.getElementById("number").innerHTML = info.number;
            document.getElementById("class").innerHTML = info.className;

            if (state == 2) {
                var startTime = JSON.stringify(info.trueStartTime);
                var endTime = JSON.stringify(info.trueEndTime);
                var startDate = new Date();
                startDate.setTime(startTime);
                var endDate = new Date();
                endDate.setTime(endTime);

                document.getElementById("trueStartTime").innerHTML = startDate.toLocaleString();
                document.getElementById("trueEndTime").innerHTML = endDate.toLocaleString();
            } else {
                document.getElementById("trueStartTime").innerHTML = "-";
                document.getElementById("trueEndTime").innerHTML = "-";
            }
        }

        function generate(type) {
            window.location.href = "generateExam?" + "type=" + type + "&epid=" + epid;
        }

        window.onload = function () {

            var name = "${sessionScope.teacher.name}";
            var info = JSON.parse('${info}');
            var total = ${num};
            infos = JSON.parse('${infos}');

            <!-- 初始化一些全局数据 -->
            pages = Math.ceil(total / 12);
            eid = info.eid;
            sid = info.sid;
            epid = info.id;
            state = info.state;


            <!--教师名-->
            document.getElementById("tName").innerHTML = name;

            <!--考试信息-->
            loadExam(info);

            <!--学生列表-->
            loadInfo(infos);

            <!--翻页条-->

            document.getElementById("optionF").onclick = function () {
                changePage(0);
            };
            document.getElementById("optionP").onclick = function () {
                changePage(-1);
            };
            document.getElementById("optionN").onclick = function () {
                changePage(-2);
            };
            document.getElementById("optionL").onclick = function () {
                changePage(-3);
            };
            document.getElementById("option1").onclick = function () {
                changePage(0);
            };

            if (pages > 1) {
                var cur = document.getElementById("option2");
                cur.onclick = function () {
                    changePage(1);
                };
                cur.parentNode.setAttribute("style", "");
            }

            if (pages > 2) {
                var cur = document.getElementById("option3");
                cur.onclick = function () {
                    changePage(2);
                };
                cur.parentNode.setAttribute("style", "");
            }
        }

        <!-- 几个按钮的点击事件 -->


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
                <li class="active"><a href="exams">我的考试</a></li>
                <li><a href="courses">我的课程</a></li>
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

<div class="container" style="margin-top: 40px; min-height: 100%;">
    <div id="exam_info" style="background: white; border-radius: 10px;padding-top:5px; padding-bottom:5px">
        <div class="container" style="">
            <div class="row">
                <div class="col-md-10">
                    <h3 style="margin-left:35px;margin-right: 10px;"><span id="eName">计组期中考试</span>
                        <small>&nbsp;科目:<span style="color: #38a1d6" id="cName">计组</span></small>
                    </h3>
                </div>
                <div class="col-md-2" style="margin-top:30px">
                    <span id="state"></span>
                </div>
            </div>
        </div>
        <br/>
        <div class="container" style="margin-bottom:20px">
            <div class="row">
                <div class="col-md-3">
                    <ul>
                        <li>姓名 :&nbsp;<span style="color: #38a1d6" id="sName">王二狗</span></li>
                        <li>学号 :&nbsp;<span style="color: #38a1d6" id="number">141250182</span></li>
                    </ul>
                </div>


                <div class="col-md-3">
                    <ul>
                        <li>年级 :&nbsp;<span style="color: #38a1d6" id="grade">大一</span></li>
                        <li>班级 :&nbsp;<span style="color: #38a1d6" id="class">15</span></li>
                    </ul>
                </div>

                <div class="col-md-2">
                    <ul>
                        <li>得分 :&nbsp;<span style="color: #38a1d6" id="score">89.5</span></li>
                        <li>总分 :&nbsp;<span style="color: #38a1d6" id="totalScore">100</span></li>

                    </ul>
                </div>

                <div class="col-md-4">
                    <ul>
                        <li>开始考试时间 :&nbsp;<span style="color: #38a1d6" id="trueStartTime">2017-10-10 20:31:01</span>
                        </li>
                        <li>提交考试时间 :&nbsp;<span style="color: #38a1d6" id="trueEndTime">2017-10-10 20:35:01</span></li>
                    </ul>
                </div>


            </div>
        </div>
    </div>

    <div id="students"
         style="background: white; border-radius: 10px;padding-top:5px; padding-left:15px; padding-right: 15px; padding-bottom: 5px; margin-top: 30px; margin-bottom: 30px"
         ;>
        <!-- 排序功能先不加了 -->
        <div class="container">
            <div class="row">
                <div class="col-md-2">
                    <h3>试题列表</h3>
                </div>
                <div class="col-md-6">
                </div>
                <div class="col-md-4" style="padding-left: 80px;">
                    <button class="btn btn-primary" style="margin-top: 15px;margin-left:32px" id="exportPaper"
                            onclick="generate(0)">导出试卷
                    </button>
                    <button class="btn btn-primary" style="margin-top: 15px;margin-left:32px" id="exportAnswer"
                            onclick="generate(1)">导出答案
                    </button>
                </div>
            </div>
        </div>
        <table class="table table-striped table-bordered" id="questionInfo" >
            <caption style="color: black">
            </caption>
            <thead>
            <tr>
                <th style='text-align: center;'>序号</th>
                <th style='text-align: center;'>题型</th>
                <th style='text-align: center;'>考题</th>
                <th style='text-align: center;'>备选答案</th>
                <th style='text-align: center;'>正确答案</th>
                <th style='text-align: center;'>考生答案</th>
                <th style='text-align: center;'>获得分数</th>
                <th style='text-align: center;'>操作</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
            </tr>
            <tr>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
            </tr>
            <tr>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
            </tr>
            <tr>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
            </tr>
            <tr>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
            </tr>
            <tr>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
            </tr>
            <tr>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
            </tr>
            <tr>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
            </tr>
            <tr>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
            </tr>
            <tr>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
            </tr>
            <tr>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
            </tr>
            <tr>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
            </tr>


            </tbody>
        </table>
    </div>


    <div style="text-align: center; margin-top: 50px">
        <div class="btn-group" data-toggle="buttons" id="pageBtns">
            <label class="btn btn-default"> <input type="radio"
                                                   name="options" id="optionF"> 首页
            </label>
            <label class="btn btn-default"> <input type="radio"
                                                   name="options" id="optionP"> 上一页
            </label>
            <label class="btn btn-default active"> <input type="radio"
                                                          name="options" id="option1"> <span id="page1">1</span>
            </label>
            <label class="btn btn-default" style="display: none"> <input
                    type="radio" name="options" id="option2"> <span id="page2">2</span>
            </label>
            <label class="btn btn-default" style="display: none"> <input
                    type="radio" name="options" id="option3"> <span id="page3">3</span>
            </label>
            <label class="btn btn-default" style="display: none"> <input
                    type="radio" name="options" id="option4"> <span id="page4">4</span>
            </label>
            <label class="btn btn-default" style="display: none"> <input
                    type="radio" name="options" id="option5"> <span id="page5">5</span>
            </label>
            <label class="btn btn-default"> <input type="radio" name="options" id="optionN"> 下一页
            </label>
            <label class="btn btn-default"> <input type="radio"
                                                   name="options" id="optionL"> 尾页
            </label>
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