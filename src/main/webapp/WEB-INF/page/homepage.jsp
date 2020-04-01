<%@ page import="com.ayu.domain.User" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>挑选房间</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap-theme.min.css">
    <script src="${pageContext.request.contextPath}/static/js/vue.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
    <script src="${pageContext.request.contextPath}/static/js/bootstrap.min.js"></script>
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
    <link rel="stylesheet" type="text/css" href="https://www.layuicdn.com/layui-v2.5.4/css/layui.css"/>
    <link href="http://www.magicalcoder.com/magicaldrag/assets/drag/ui/magicalcoder/1.1.0/magicalcoder.css"
          rel="stylesheet">
    <script src="https://lib.baomitu.com/json3/3.3.3/json3.min.js"></script>
    <script src="https://www.layuicdn.com/layui-v2.5.4/layui.all.js"></script>
    <script src="https://lib.baomitu.com/echarts/4.6.0/echarts.min.js"></script>
    <script src="http://www.magicalcoder.com/magicaldrag/assets/drag/ui/magicalcoder/1.1.0/magicalcoder.js"></script>
</head>
<body class="layui-form" style="background-color:#eee;padding: 20px;">
<h2 class="text-primary">欢迎您，${name}</h2>
<h2 hidden="hidden" id="idcard">${idcard}</h2>
<button type="button" class=" btn btn-group btn-xs" @click="logout">登出</button>
<div id="app">
    <div class="layui-tab">
        <ul class="layui-tab-title">
            <li class="magicalcoder-tab-title layui-this">房间列表</li>
            <li class="magicalcoder-tab-title">我的房间</li>
        </ul>
        <div class="layui-tab-content">
            <div class="layui-tab-item layui-show">
                <table class="table">
                    <thead>
                    <tr>
                        <th>房间号</th>
                        <th>房间类型</th>
                        <th>价格</th>
                        <th>状态</th>
                        <th>
                            <button type="button" id="bth" class="btn-danger" @click="getRoom">刷新</button>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="(item) in room" :class="item.status?'success':'danger'">
                        <td>{{item.num}}</td>
                        <td>{{types[item.type]}}</td>
                        <td>{{item.price}}</td>
                        <td>{{item.status?"空闲":"已预订"}}</td>
                        <td>
                            <button type="button" class="btn btn-group" v-show="item.status" @click="bookRoom(item)">
                                预订房间
                            </button>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="layui-tab-item">
                <table class="table">
                    <thead>
                    <tr>
                        <th>房间号</th>
                        <th>房间类型</th>
                        <th>
                            <button type="button" id="freshMyRoom" class="btn-danger" @click="freshMyRoom">刷新</button>
                        </th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="(item) in myroom">
                        <td>{{item.num}}</td>
                        <td>{{types[item.type]}}</td>
                        <td>
                            <button type="button" class="btn btn-primary" v-show="!item.status" @click="bookRoom(item)">
                                退房
                            </button>
                        </td>
                    </tr>
                </table>
            </div>
        </div>
    </div>


</div>

<script>
    var vm = new Vue({
        el: "#app",
        data: {
            types: ["单人间", "双人间", "多人间"],
            room: [],
            myroom:[]
        },
        methods: {
            bookRoom: function (item) {
                let that = this;
                axios.post("../room/bookRoom", {
                    //直接用\${}会使精度丢失
                    idcard: document.querySelector('#idcard').innerHTML,
                    status: Number(!(item.status)),
                    num: item.num
                }).then(function (response) {
                    that.room = response.data;
                    that.freshMyRoom();
                })
            },
            logout: function () {
                window.alert("退出成功");
                window.close();
            },
            getRoom: function () {
                let that = this;
                axios.get("../room/getRoomList").then(function (response) {
                    that.room = response.data;
                })
            },
            freshMyRoom:function () {
                let that = this;
                axios.post("../room/getRoomListByIdcard",
                    {idcard:document.querySelector('#idcard').innerHTML}
                ).then(function (response) {
                    that.myroom = response.data;
                })
            }
        }
    })
</script>
</body>
</html>