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
        var curPage = 0;
        var pages = 0;

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
                url: "courseList",
                data: "&page=" + reqPage,
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
            var trs = document.getElementById("courseInfo").getElementsByTagName("tr");

            for ( var i = 0; i < 5; i++) {
                var k = i * 2;
                var tds = trs[k + 1].getElementsByTagName("td");
                if(i < info.length) {

                    tds[0].innerHTML = i + 1 + curPage * 5;
                    tds[1].innerHTML = info[i].name;
                    tds[2].innerHTML = info[i].courseTypeName;
                    tds[3].innerHTML = info[i].institutionName;
                    tds[4].innerHTML = info[i].period;
                    tds[5].innerHTML = info[i].score;
                    tds[6].innerHTML = '<button class="pull-right btn btn-primary btn-ms" style="margin-left: 20px; margin-right: 30px" onclick="viewLib('+ info[i].id +')">查看题库</button><button class="pull-right btn btn-primary btn-ms" onclick="createExam(' +  info[i].id + ')">创建考试</button>';
                } else {
                    for (var j = 0; j < 7; j++) {
                        tds[j].innerHTML = "";
                    }
                }


            }

            for (var i = 0; i < 4; i++) {
                var k = i * 2 + 1;
                var tds = trs[k + 1].getElementsByTagName("td");
                for (var j = 0; j < 7; j++) {
                    tds[j].innerHTML = "";
                }
            }
        }

        function viewLib(cid){
            window.location.href = "libs?cid=" + cid;
        }

        function createExam(cid) {
            window.location.href = "createExam?cid=" + cid;
        }

        window.onload = function () {

            var name = "${sessionScope.teacher.name}";
            var total = "${total}";
            var infos = JSON.parse('${infos}');

            <!-- 初始化一些全局数据 -->
            pages = Math.ceil(total / 5);

            <!--教师名-->
            document.getElementById("tName").innerHTML = name;

            <!--考试列表-->
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
            </h2>
        </div>

        <!-- 排序功能先不加了
        <div>
            <button class="btn btn-primary" style="margin-top: 15px;margin-left:32px" id="add">
                导出答案
            </button>
        </div>
        <div>
            <button class="btn btn-primary" style="margin-top: 15px;margin-left:32px" id="delete">
                导出答案
            </button>
        </div>
        <div>
            <button class="btn btn-primary" style="margin-top: 15px;margin-left:32px" id="rename">
                导出答案
            </button>
        </div> -->

        <table class="table table-striped" id="courseInfo">
            <caption style="color: black">
            </caption>
            <tr>
                <th style='text-align: center;'></th>
                <th style='text-align: center;'>课程名</th>
                <th style='text-align: center;'>课程类型</th>
                <th style='text-align: center;'>学院</th>
                <th style='text-align: center;'>学期</th>
                <th style='text-align: center;'>学分</th>
                <th style='text-align: center;'>操作</th>
            </tr>
            <tbody>
            <tr style="height: 100px; ">
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
            </tr>
            <tr>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
            </tr>
            <tr style="height: 100px">
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
            </tr>
            <tr>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
            </tr>
            <tr style="height: 100px">
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
            </tr>
            <tr>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
            </tr>
            <tr style="height: 100px">
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
            </tr>
            <tr>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
                <td style='text-align: center;'>-</td>
            </tr>
            <tr style="height: 100px; border-bottom: 1px solid #ddd">
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
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