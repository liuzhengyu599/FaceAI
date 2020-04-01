<%--
  Created by IntelliJ IDEA.
  User: ayu
  Date: 2020/2/10
  Time: 18:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="icon" href="http://demo.sc.chinaz.com/Files/pic/iconsico/7494/g2.ico" type="image/x-icon"/>
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <title>用户信息系统</title>
    <link rel="stylesheet" type="text/css" href="https://www.layuicdn.com/layui-v2.5.4/css/layui.css"/>
    <link href="http://www.magicalcoder.com/magicaldrag/assets/drag/ui/magicalcoder/1.1.0/magicalcoder.css"
          rel="stylesheet">
    <script src="https://lib.baomitu.com/json3/3.3.3/json3.min.js"></script>
    <script src="https://www.layuicdn.com/layui-v2.5.4/layui.all.js"></script>
    <script src="https://lib.baomitu.com/echarts/4.6.0/echarts.min.js"></script>
    <script src="http://www.magicalcoder.com/magicaldrag/assets/drag/ui/magicalcoder/1.1.0/magicalcoder.js"></script>
    <!--layui的table col操作列自定义的模板 您可以根据自己的实际情况改动-->
    <script type="text/html" id="tableToolbar">
        <div class="layui-inline" lay-event="add"><i class="layui-icon layui-icon-add-1"></i></div>
        <div class="layui-inline" lay-event="update"><i class="layui-icon layui-icon-edit"></i></div>
        <div class="layui-inline" lay-event="delete"><i class="layui-icon layui-icon-delete"></i></div>
    </script>
    <script type="text/html" id="tableColToolbar">
        <a class="layui-btn layui-btn-xs" lay-event="detail">查看</a>
        <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </script>
    <script src="js/function.js"></script>
    <script src="WEB-INF/page/static/js/jquery.facedetection.min.js"></script>
</head>
<body class="layui-form" style="background-color:#eee;padding: 20px;">
<div class="layui-container">
    <div class="layui-tab">
        <ul class="layui-tab-title">
            <li class="magicalcoder-tab-title layui-this">注册</li>
            <li class="magicalcoder-tab-title">登录</li>
        </ul>

        <div class="layui-tab-content">
            <div class="layui-tab-item layui-show">
                <div class="layui-container">
                    <div class="layui-fluid">
                        <div class="layui-row">
                            <form action="user/Register" method="post" enctype="multipart/form-data">
                                <input type="text" id="takeText" hidden="hidden" name="img">
                                <div class="layui-col-xs12">
                                    <p style="height: 24px; text-indent: 0px; text-align: left; font-weight: bold;">
                                        姓名
                                    </p>
                                    <input class="layui-input" type="text" autocomplete="on" placeholder="请输入您的姓名"
                                           name="name" id="name1"/>
                                </div>
                                <div class="layui-col-xs12">
                                    <p style="height: 24px; font-weight: bold;">
                                        身份证号
                                    </p>
                                    <input class="layui-input" type="text" autocomplete="on" placeholder="请输入18位身份证号"
                                           name="idcard" id="idcard1"/>
                                    <blockquote class="layui-elem-quote">
                                        系统会根据身份证号自动计算生日
                                    </blockquote>
                                    <p style="height: 20px; font-weight: bold;">
                                        手机号码
                                    </p>
                                    <input class="layui-input" type="text" autocomplete="on" placeholder="请输入手机号"
                                           name="phoneNum"/>
                                </div>
                                <table width="100%" border="0" cellspacing="0" cellpadding="0">
                                    <tbody>
                                    <tr>
                                        <td height="101" align="left">
                                            <div id="localImag"><img id="preview"
                                                                     hidden="hidden"
                                                                     width="200px" height="200px"
                                                                     style="display: block; width: 200px; height: 200px;"
                                            >
                                            </div>
                                        </td>
                                        <td height="101" align="left">
                                            <div>
                                                <video id="video" width="200px" height="200px" autoplay="autoplay"></video>
                                            </div>
                                        </td>
                                        <td height="101" align="left">
                                            <div>
                                                <canvas id="canvas" width="200px" height="200px" hidden="hidden"></canvas>
                                            </div>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td align="left" style="padding-top:10px;">
                                            <input type="file" name="registerFile" hidden="hidden" id="registerFile01"
                                                   onchange="javascript:setImagePreview();">
                                            <button class="magicalcoder-layupload layui-btn"
                                                    style="background-color: rgb(30, 144, 255); z-index: 0;"
                                                    type="button"
                                                    onclick="registerUpload()">上传图片
                                            </button>
                                        </td>
                                        <td align="left" style="padding-top:10px;">
                                            <button class="magicalcoder-layupload layui-btn"
                                                    style="background-color: rgb(30, 144, 255); z-index: 0;"
                                                    type="button"
                                                    onclick="openRegistMedia()">开启摄像头
                                            </button>
                                        </td>
                                        <td align="left" style="padding-top:10px;">
                                            <button class="magicalcoder-layupload layui-btn"
                                                    style="background-color: rgb(30, 144, 255); z-index: 0;"
                                                    type="button"
                                                    onclick="takeRegistPhoto()">拍照
                                            </button>
                                        </td>
                                        <td align="left" style="padding-top:10px;">
                                            <button class="magicalcoder-layupload layui-btn"
                                                    style="background-color: rgb(30, 144, 255); z-index: 0;"
                                                    type="button"
                                                    onclick="closeMedia()">关闭摄像头
                                            </button>
                                        </td>
                                    </tr>
                                    </tbody>
                                </table>
                                        <div class="layui-col-xs12">
                                            <button type="submit" id="tijiao" hidden="hidden"/>
                                            <script>
                                                function registerUpload() {
                                                    document.getElementById("registerFile01").click();
                                                }
                                            </script>
                                        </div>
                                        <div align="right" style="padding-top:20px;" >
                                            <a class="layui-btn"
                                               onclick="registerCheck()">注册</a>
                                        </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
            <div class="layui-tab-item">
                <div class="layui-container">
                    <div class="layui-row">
                        <form action="user/Login" method="post" enctype="multipart/form-data">
                            <div class="layui-col-xs12">
                                <table>
                                    <tr>
                                        <td height="101" align="left">
                                            <div>
                                                <video id="loginVideo" width="200px" height="200px" autoplay="autoplay"></video>
                                            </div>
                                        </td>
                                        <td height="101" align="left">
                                            <div>
                                                <canvas id="loginCanvas" width="200px" height="200px" hidden="hidden"></canvas>
                                            </div>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                            <div class="layui-col-xs12 mc-ui-flex-end">
                                <input type="text" id="loginText" hidden="hidden" name="loginImg">
                                <input type="submit"  id="loginSubmit" hidden="hidden">
                                <a class="layui-btn" onclick="openLoginMedia()">开启人脸登录</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
