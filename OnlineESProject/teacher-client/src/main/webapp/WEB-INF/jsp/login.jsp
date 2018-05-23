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
</head>

<body style="background-image: url(img/back.jpg);">
<nav class="navbar navbar-inverse" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header" style="margin-left: 180px">
            <a class="navbar-brand" href="#">OnlineES在线考试平台</a>
        </div>
        <div>
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">首页</a></li>
            </ul>
        </div>
        <div>
            <ul class="nav navbar-nav" style="margin-left: 1000px">
                <li><a href="login">登录</a></li>
                <li><a href="register">注册</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="container" style="margin-top:240px; min-height:68.4%; margin-bottom: 80px;">
    <div class="row">
        <div class="col-xs-4 col-md-offset-8" style="background-color: rgba(0,0,0,0.3); border-radius: 15px;">
            <form role="form" action="action" method="post">

                <div class="form-group"
                     style="text-align:center; font-size:20px; margin-top:40px; margin-bottom:30px; color:white">
                    欢迎加入OnlineES
                </div>


                <div class="form-group" style="text-align:center; color:white">
                    <div class="form-inline">
                        账户 : <input type="text" class="form-control" name="name" style="height:27px; " value="">
                    </div>
                </div>

                <div class="form-group" style="text-align:center; color:white">
                    <div class="form-inline">
                        密码 : <input type="password" class="form-control" name="password" style="height:27px; ">
                    </div>
                </div>

                <input type="hidden" name="type" value="login"/>

                <div class="form-group" style="text-align:center; margin-top:30px; margin-bottom:40px">
                    <div class="form-inline">
                        &#12288;&#12288;&nbsp;&nbsp;&nbsp;<button type="submit" class="btn btn-info btn-sm"
                                                                  data-toggle="button"
                                                                  style="width:50%; font-size:14px">登录
                    </button>
                    </div>
                </div>

                <!--警告框，用户名或密码错误-->
                <div id="failTip" class="alert alert-warning hide" id="failTip">
                    <a class="close" data-dismiss="alert" href="#" aria-hidden="true">
                        &times;
                    </a>
                    <strong>用户名或密码错误！</strong>
                </div>


                <script language="javascript">
                    var fail = <% out.print((String)request.getAttribute("fail")); %>;
                    if (fail == "1") {
                        $('#failTip').attr("class", "alert alert-warning")
                        window.setTimeout(function () {
                            $('#failTip').attr("class", "alert alert-warning hide");
                        }, 5000);
                    }

                    $(function () {
                        $(".close").click(function () {
                            $('#failTip').attr("class", "alert alert-warning hide");
                        });
                    });
                </script>

            </form>
        </div>
    </div>
</div>

<footer>
    <div style="line-height:40px; text-align:center; text-vert:center; color:white; font-size:14px">
        你猜这是什么群 荣誉出品
    </div>
</footer>

</body>
</html>