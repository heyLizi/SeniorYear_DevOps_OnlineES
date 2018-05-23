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
        <!-- 换页函数-->
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
                url: "examList",
                data: "page=" + reqPage,
                dataType: 'html',
                success: function (data) {
                    var info = JSON.parse(data);
                    loadInfo(JSON.parse(info.data));


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
            var i;
            for (i = 0; i < info.length; i++) {

                var next = document.getElementById("exam" + (i + 1));
                next.setAttribute("class", "exam");
                var infoTag = next.getElementsByClassName("row")[0].getElementsByTagName("div");
                infoTag[1].getElementsByTagName("span")[0].innerHTML = info[i].name;
                var sTime = JSON.stringify(info[i].startTime);
                var eTime = JSON.stringify(info[i].endTime);
                var sDate = new Date();
                sDate.setTime(sTime);
                var eDate = new Date();
                eDate.setTime(eTime);
                infoTag[2].innerHTML = sDate.toLocaleString() + " ~ " + eDate.toLocaleString();
                infoTag[3].getElementsByTagName("span")[0].innerHTML = info[i].questionCount;
                infoTag[4].getElementsByTagName("span")[0].innerHTML = info[i].studentCount;
                var id = info[i].id;
                infoTag[5].innerHTML="<button class='btn btn-primary' style='margin-top: 32px' onclick='detail(" + id + ")'>查看详情</button>"
            }

            i = i + 1;

            while (i <= 5) {
                var next = document.getElementById("exam" + i);
                next.setAttribute("class", "exam hidden");
                i++;
            }
        }

        function detail(id) {
                window.location.href = "exam?eid=" + id;
        }

        window.onload = function () {

            var name ="${sessionScope.teacher.name}";
            var info = JSON.parse('${info}');
            var total = ${total};
            pages = Math.ceil(total / 5)

            <!--教师名-->
            document.getElementById("tName").innerHTML = name;

            <!--考试信息-->
            loadInfo(info);

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

<div class="container" style="margin-top: 40px; min-height: 100%;  margin-bottom: 80px;">
    <div style="font-size: 35px; color: white; margin-bottom: 20px;">
        我发布的考试
    </div>
    <div style="border-radius: 15px; border:4px solid #FFFFFF7f; padding: 16px;">
        <div class="exam hidden" id="exam1"
             style="background: white; border-radius: 10px; height: 100px; margin-bottom: 35px;">
            <div class="container">
                <div class="row" style="line-height: 100px">
                    <div class="col-md-1">
                        <span style="color: #38a1d6; font-size: 18px">&nbsp;&nbsp;&nbsp;1</span>
                    </div>
                    <div class="col-md-2">
                        <span style="color: #38a1d6; font-size: 18px">计组期中考试1</span>
                    </div>
                    <div class="col-md-4">2017-10-01 ~ 2017-10-31</div>
                    <div class="col-md-1">
                        试题数:<span style="color: #38a1d6">20</span>
                    </div>
                    <div class="col-md-2">
                        考试人数:<span style="color: #38a1d6">200</span>
                    </div>
                    <div class="col-md-2">
                        <button class="btn btn-primary" style="margin-top: 32px">查看详情</button>
                    </div>

                </div>
            </div>
        </div>
        <div class="exam hidden" id="exam2"
             style="background: white; border-radius: 10px; height: 100px; margin-bottom: 35px;">
            <div class="container">
                <div class="row" style="line-height: 100px">
                    <div class="col-md-1">
                        <span style="color: #38a1d6; font-size: 18px">&nbsp;&nbsp;&nbsp;2</span>
                    </div>
                    <div class="col-md-2">
                        <span style="color: #38a1d6; font-size: 18px">计组期中考试2</span>
                    </div>
                    <div class="col-md-4">2017-10-01 ~ 2017-10-31</div>
                    <div class="col-md-1">
                        试题数:<span style="color: #38a1d6">20</span>
                    </div>
                    <div class="col-md-2">
                        考试人数:<span style="color: #38a1d6">200</span>
                    </div>
                    <div class="col-md-2">
                        <button class="btn btn-primary" style="margin-top: 32px">查看详情</button>
                    </div>

                </div>
            </div>
        </div>
        <div class="exam hidden" id="exam3"
             style="background: white; border-radius: 10px; height: 100px; margin-bottom: 35px;">
            <div class="container">
                <div class="row" style="line-height: 100px">
                    <div class="col-md-1">
                        <span style="color: #38a1d6; font-size: 18px">&nbsp;&nbsp;&nbsp;3</span>
                    </div>
                    <div class="col-md-2">
                        <span style="color: #38a1d6; font-size: 18px">计组期中考试3</span>
                    </div>
                    <div class="col-md-4">2017-10-01 ~ 2017-10-31</div>
                    <div class="col-md-1">
                        试题数:<span style="color: #38a1d6">20</span>
                    </div>
                    <div class="col-md-2">
                        考试人数:<span style="color: #38a1d6">200</span>
                    </div>
                    <div class="col-md-2">
                        <button class="btn btn-primary" style="margin-top: 32px">查看详情</button>
                    </div>

                </div>
            </div>
        </div>
        <div class="exam hidden" id="exam4"
             style="background: white; border-radius: 10px; height: 100px; margin-bottom: 35px;">
            <div class="container">
                <div class="row" style="line-height: 100px">
                    <div class="col-md-1">
                        <span style="color: #38a1d6; font-size: 18px">&nbsp;&nbsp;&nbsp;4</span>
                    </div>
                    <div class="col-md-2">
                        <span style="color: #38a1d6; font-size: 18px">计组期中考试4</span>
                    </div>
                    <div class="col-md-4">2017-10-01 ~ 2017-10-31</div>
                    <div class="col-md-1">
                        试题数:<span style="color: #38a1d6">20</span>
                    </div>
                    <div class="col-md-2">
                        考试人数:<span style="color: #38a1d6">200</span>
                    </div>
                    <div class="col-md-2">
                        <button class="btn btn-primary" style="margin-top: 32px">查看详情</button>
                    </div>

                </div>
            </div>
        </div>
        <div class="exam hidden" id="exam5"
             style="background: white; border-radius: 10px; height: 100px; margin-bottom: 35px;">
            <div class="container">
                <div class="row" style="line-height: 100px">
                    <div class="col-md-1">
                        <span style="color: #38a1d6; font-size: 18px">&nbsp;&nbsp;&nbsp;5</span>
                    </div>
                    <div class="col-md-2">
                        <span style="color: #38a1d6; font-size: 18px">计组期中考试5</span>
                    </div>
                    <div class="col-md-4">2017-10-01 ~ 2017-10-31</div>
                    <div class="col-md-1">
                        试题数:<span style="color: #38a1d6">20</span>
                    </div>
                    <div class="col-md-2">
                        考试人数:<span style="color: #38a1d6">200</span>
                    </div>
                    <div class="col-md-2">
                        <button class="btn btn-primary" style="margin-top: 32px">查看详情</button>
                    </div>

                </div>
            </div>
        </div>
    </div>
    <br/>
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
    <div
            style="line-height: 40px; text-align: center; text-vert: center; color: white; font-size: 14px">
        你猜这是什么群 荣誉出品
    </div>
</footer>

</body>
</html>