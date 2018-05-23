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
        var cid = 0;
        var lid = 0;
        var selection;
        var infos;
        var qids;

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
                url: "questionList1",
                data: "&page=" + reqPage + "&lid=" + lid,
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
            var trs = document.getElementById("libInfo").getElementsByTagName("tr");

            for ( var i = 0; i < 12; i++) {
                var tds = trs[i + 1].getElementsByTagName("td");
                if(i < info.length) {
                    tds[0].innerHTML = "<input type='checkbox' onclick='_select(" + (12 * curPage + i) + ")'>";

                    if (selection[curPage * 12 + i] == 0)
                        tds[0].getElementsByTagName("input")[0].checked = false;
                    else
                        tds[0].getElementsByTagName("input")[0].checked = true;

                    tds[1].innerHTML = i  + curPage * 12 + 1;
                    tds[2].innerHTML = info[i].typeName;
                    tds[3].innerHTML = "<a href='questionDetail?qid=" + info[i].id + "'>点击查看题目详情</a>";
                    tds[4].innerHTML = info[i].optionCount;
                    tds[5].innerHTML = info[i].answerCount;
                }else {
                    for (var j = 0; j < 6; j++) {
                        tds[j].innerHTML = "-";
                    }
                }


            }
        }

        function _select(i) {
            if (selection[i] == 0) {
                selection[i] = 1;
            } else {
                selection[i] = 0;
            }
        }


        function add() {
            window.location.href = "questionAdd";
        }

        function del() {

            var qidsStr = "";
            if (selection[0] == 1) {
                qidsStr += qids[i];
                selection[0] = 0;
            }


            for (var i = 1; i < selection.length; i++) {
                if (selection[i] == 1) {
                    qidsStr += " " + qids[i];
                    selection[i] = 0;
                }
            }

            if(qidsStr == "")
                return;


            $.ajax({
                type: "get",
                url: "questionDelete",
                data: "&qids=" + qidsStr + "&cid=" + cid + "&lid=" + lid,
                dataType: 'html',
                success: function (data) {
                    var d = JSON.parse(data);
                    if(d.success) {
                        alert(d.data);
                    } else {
                        alert(d.errorMessage);
                    }
                    changePage(curPage);
                },
                complete: function (XMLHttpRequest, textStatus) {
                },
                error: function () {
                    alert("发生了未知的错误");
                }
            });

        }

        window.onload = function (){

            var name = "${sessionScope.teacher.name}";
            var lName = "${lName}";
            var cName = "${cName}"
            var total = ${total};
            infos = JSON.parse('${infos}');
            qids = "${sessionScope.lid}";

            <!-- 初始化一些全局数据 -->
            pages = Math.ceil(total / 12);
            cid = ${cid};
            lid = ${sessionScope.lid};
            selection = new Array(total);
            selection.fill(0);

            <!--教师名-->
            document.getElementById("tName").innerHTML = name;

            <!--课程名-->
            document.getElementById("cName").innerHTML = cName;

            <!--题库名-->
            document.getElementById("lName").innerHTML = lName;

            <!--题库列表-->
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
                <a href="courses">我的课程</a>
                &gt;
                <a href="libs?cid=${cid}">
                "<span id="cName"></span>
                "&nbsp;的题库
                </a>
                &gt;
                <span id="lName"></span>
            </h2>
        </div>

        <!-- 排序功能先不加了-->
        <div class="btn-group" data-toggle="buttons-checkbox">
            <button class="btn btn-primary"  id="add" onclick="add()">
                新增试题
            </button>
            <button class="btn btn-primary"  id="delete" onclick="del()">
                删除试题
            </button>
        </div>

        <table class="table table-striped" id="libInfo">
            <caption style="color: black">
            </caption>
            <tr>
                <th style='text-align: center;'></th>
                <th style='text-align: center;'>序号</th>
                <th style='text-align: center;'>题型</th>
                <th style='text-align: center;'>内容</th>
                <th style='text-align: center;'>选项个数</th>
                <th style='text-align: center;'>正确答案个数</th>
            </tr>
            <tbody>
            <tr >
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
            </tr>
            <tr >
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
            </tr>
            <tr >
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
            </tr>
            <tr >
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
            </tr>
            <tr >
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
            </tr>
            <tr >
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
            </tr>
            <tr >
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
            </tr>
            <tr >
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
            </tr>
            <tr >
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
            </tr>
            <tr >
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
            </tr>
            <tr >
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
                <td style='text-align: center; vertical-align:middle;'>-</td>
            </tr>
            <tr >
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