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
        var selection;
        var selectA = false;
        var infos;
        var ids;

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
                url: "studentList",
                data: "eid=" + eid + "&page=" + reqPage,
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
            var trs = document.getElementById("studentInfo").getElementsByTagName("tr");
            var i;
            for (i = 0; i < info.length; i++) {
                var num = curPage * 12 + i + 1;
                var name = info[i].name;
                var number = info[i].number;
                var attend;
                if (info[i].status)
                    attend = "是";
                else
                    attend = "否";

                var numOfTrueAnswer = info[i].correctCount;
                var score = info[i].score;
                var rank = info[i].rank;


                var tds = trs[i + 1].getElementsByTagName("td");
                tds[0].innerHTML = "<input type='checkbox' onclick='_select(" + (12 * curPage + i) + ")'>";
                if (selection[curPage * 12 + i] == 0 || selection[curPage * 12 + i] == undefined)
                    tds[0].getElementsByTagName("input")[0].checked = false;
                else
                    tds[0].getElementsByTagName("input")[0].checked = true;

                tds[1].innerHTML = num;
                tds[2].innerHTML = name;
                tds[3].innerHTML = number;
                tds[4].innerHTML = attend;
                if (info[i].status == 2) {
                    tds[5].innerHTML = numOfTrueAnswer;
                    tds[6].innerHTML = score;
                    tds[7].innerHTML = rank;
                } else {
                    tds[5].innerHTML = "-";
                    tds[6].innerHTML = "-";
                    tds[7].innerHTML = "-";
                }
                tds[8].innerHTML = '<button class="pull-right btn btn-primary btn-xs" style="margin-right: 5px" id="detail' + i + '" onclick="detail(' + i + ')"' + '>查看详情</button>';
            }

            for (; i < 12; i++) {
                var tds = trs[i + 1].getElementsByTagName("td");
                for (var j = 0; j < 9; j++) {
                    tds[j].innerHTML = "-";
                }
            }
        }

        function loadExam(info) {
            document.getElementById("name").innerHTML = info.name;
            document.getElementById("cName").innerHTML = info.courseName;
            document.getElementById("numOfQuestion").innerHTML = info.questionCount;
            document.getElementById("singleChoice").innerHTML = info.singleNum;
            document.getElementById("multiChoice").innerHTML = info.multiNum;
            document.getElementById("totalScore").innerHTML = info.totalScore;
            document.getElementById("singleScore").innerHTML = info.singleScore;
            document.getElementById("multiScore").innerHTML = info.multiScore;
            document.getElementById("total").innerHTML = info.totalNum;
            document.getElementById("attend").innerHTML = info.attendNum;
            document.getElementById("passed").innerHTML = info.passedNum;
            document.getElementById("average").innerHTML = info.averageScore;
            document.getElementById("highest").innerHTML = info.highestScore;
            document.getElementById("lowest").innerHTML = info.lowestScore;

            var sTime = JSON.stringify(info.startTime);
            var eTime = JSON.stringify(info.endTime);
            var sDate = new Date();
            sDate.setTime(sTime);
            var eDate = new Date();
            eDate.setTime(eTime);
            document.getElementById("time").innerHTML = sDate.toLocaleString() + " ~ " + eDate.toLocaleString();
        }

        function _select(i) {
            if (selection[i] == 0) {
                selection[i] = 1;
            } else {
                selection[i] = 0;
                document.getElementById("selectAll").checked = false;
                selectA = false;
            }
        }

        function selectAll() {
            if (selectA) {
                selection.fill(0);
                selectA = false;
                loadInfo(infos);
            } else {
                selection.fill(1);
                selectA = true;
                loadInfo(infos);
            }
        }

        function detail(i) {
            window.location.href = "paperDetail?epid=" + infos[i].id;
        }

        function generate(type) {
            var sids = "";
            var data;
            if (selection[0] == 1) {
                sids += ids[0];
            }
            for (var i = 1; i < selection.length; i++) {
                if (selection[i] == 1) {
                    sids += " " + ids[i];
                }
            }
            if(sids == "" && type != 0)
                return;

            if(type == 0) {
                sids = "0";
            }

            window.location.href = "generateReport?" + "type=" + type + "&eid=" + eid + "&sidStr=" + sids;
        }

        window.onload = function () {

            var name = "${sessionScope.teacher.name}";
            var info = JSON.parse('${info}');
            infos = JSON.parse('${infos}');

            <!-- 初始化一些全局数据 -->
            pages = Math.ceil(total / 12);
            eid = info.id;
            selection = new Array(info.totalNum);
            selection.fill(0);
            ids = JSON.parse('${ids}');


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

<div class="container" style="margin-top: 40px; min-height: 100%; margin-bottom: 80px;">
    <div id="exam_info" style="background: white; border-radius: 10px;padding-top:5px; padding-bottom:5px">
        <div class="container" style="">
            <div class="row">
                <div class="col-md-6">
                    <h3 style="margin-left:35px"><span id="name">计组期中考试</span>
                        <small>&nbsp;科目:<span style="color: #38a1d6" id="cName">计组</span></small>
                    </h3>
                </div>
                <div class="col-md-4" style="margin-top:30px">
                    <span id="time">2017-10-01 ~ 2017-10-31</span>
                </div>
                <div class="col-md-2">
                    <button class="btn btn-primary" style="margin-top: 15px;margin-left:32px" id="detail"
                            onclick="generate(0)">生成成绩单
                    </button>
                </div>
            </div>
        </div>
        <br/>
        <div class="container" style="margin-bottom:20px">
            <div class="row">
                <div class="col-md-3">
                    <ul>
                        <li>题目数 :&nbsp;<span style="color: #38a1d6" id="numOfQuestion">20</span></li>
                        <li></li>
                        <li>单选题 :&nbsp;<span style="color: #38a1d6" id="singleChoice">10</span></li>
                        <li>多选题 :&nbsp;<span style="color: #38a1d6" id="multiChoice">10</span></li>
                    </ul>
                </div>
                <div class="col-md-3">
                    <ul>
                        <li>总&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;分 :&nbsp;<span style="color: #38a1d6" id="totalScore">100</span>
                        </li>
                        <li>单选分值 :&nbsp;<span style="color: #38a1d6" id="singleScore">4</span></li>
                        <li>多选分值 :&nbsp;<span style="color: #38a1d6" id="multiScore">6</span></li>
                    </ul>
                </div>
                <div class="col-md-3">
                    <ul>
                        <li>考试人数 :&nbsp;<span style="color: #38a1d6" id="total">200</span></li>
                        <li>实到人数 :&nbsp;<span style="color: #38a1d6" id="attend">190</span></li>
                        <li>及格人数 :&nbsp;<span style="color: #38a1d6" id="passed">168</span></li>
                    </ul>
                </div>
                <div class="col-md-3">
                    <ul>
                        <li>平均分 :&nbsp;<span style="color: #38a1d6" id="average">89.5</span></li>
                        <li>最高分 :&nbsp;<span style="color: #38a1d6" id="highest">99</span></li>
                        <li>最低分 :&nbsp;<span style="color: #38a1d6" id="lowest">46</span></li>
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
                    <h3>考生列表</h3>
                </div>
                <div class="col-md-6">
                </div>
                <div class="col-md-4" style="padding-left: 80px;">
                    <button class="btn btn-primary" style="margin-top: 15px;margin-left:32px" id="exportPaper"
                            onclick="generate(1)">导出试卷
                    </button>
                    <button class="btn btn-primary" style="margin-top: 15px;margin-left:32px" id="exportAnswer"
                            onclick="generate(2)">导出答案
                    </button>
                </div>
            </div>
        </div>
        <table class="table table-striped table-bordered" id="studentInfo">
            <caption style="color: black">
            </caption>
            <thead>
            <tr>
                <th style='text-align: center;'><label><input type="checkbox" id="selectAll"
                                                              onclick="selectAll()"></label></th>
                <th style='text-align: center;'>序号</th>
                <th style='text-align: center;'>姓名</th>
                <th style='text-align: center;'>学号</th>
                <th style='text-align: center;'>是否参加考试</th>
                <th style='text-align: center;'>答对题数</th>
                <th style='text-align: center;'>成绩</th>
                <th style='text-align: center;'>排名</th>
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
                <td style='text-align: center;'>-</td>
            </tr>


            </tbody>
        </table>
    </div>


    <div style="text-align: center; margin-bottom: 80px">
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