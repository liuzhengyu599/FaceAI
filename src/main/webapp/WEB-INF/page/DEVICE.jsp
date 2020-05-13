<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<link href="https://cdn.bootcdn.net/ajax/libs/twitter-bootstrap/4.4.1/css/bootstrap.min.css" rel="stylesheet">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <style>
        #app {
            text-align: center;
        }
    </style>
</head>

<body background="${pageContext.request.contextPath}/static/img/2.jpg">
<div id="app">
    <input type="button" id="Init" @click="Init" value="初始化连接" class="btn-primary"><br>
    <input type="button" id="status" @click="isConnected" value="获取状态" class="btn-primary"><br>
    <input type="button" id="temp" @click="getTemp" value="获取温湿度" class="btn-primary"><br>
    <button id="door" @click="getDoor" class="btn-primary">获取房门状态</button>
    <br>
    <button id="light" @click="getLight" class="btn-primary">获取光线强度</button>
    <br>
    <button id="rain" @click="getRain" class="btn-primary">获取雨水强度</button>
    <br>
    <button id="doorClose" @click="closeDoor" class="btn-primary">关门</button>
    <br>
    <button id="doorOpen" @click="openDoor" class="btn-primary">开门</button>
    <br>
    <button id="lightClose" @click="closeLight" class="btn-primary">关灯</button>
    <br>
    <button id="lightOpen" @click="openLight" class="btn-primary">开灯</button>
    <br>
    <button id="heaterClose" @click="closeHeater" class="btn-primary">关闭热水器</button>
    <br>
    <button id="heaterOpen" @click="openHeater" class="btn-primary">开启热水器</button>
    <br>
    <textarea name="" :value="text" cols="50" rows="20" style="resize: none;" readonly
              style="word-wrap : break-word;"></textarea>
</div>
<script>
    var vm = new Vue({
        el: '#app',
        data: {
            text: ""
        },
        methods: {
            getTemp: function () {
                var that = this;
                axios.get("../Socket/getTemp")
                    .then(function (response) {
                        console.log(response);
                        that.text = that.text + new Date().toLocaleTimeString() + "\n" + response.data + "\n";
                    })
            },
            isConnected: function () {
                var that = this;
                axios.get("../Socket/isConnected")
                    .then(function (response) {
                        console.log(response);
                        that.text = that.text + new Date().toLocaleTimeString() + "\n" + response.data + "\n";
                    })
            },
            getDoor: function () {
                var that = this;
                axios.get("../Socket/getDoorStatus")
                    .then(function (response) {
                        console.log(response);
                        that.text = that.text + new Date().toLocaleTimeString() + "\n" + response.data + "\n";
                    })
            },
            getLight: function () {
                var that = this;
                axios.get("../Socket/getLightStatus")
                    .then(function (response) {
                        console.log(response);
                        that.text = that.text + new Date().toLocaleTimeString() + "\n" + response.data + "\n";
                    })
            },
            getRain: function () {
                var that = this;
                axios.get("../Socket/getRainStatus")
                    .then(function (response) {
                        console.log(response);
                        that.text = that.text + new Date().toLocaleTimeString() + "\n" + response.data + "\n";
                    })
            },
            closeDoor: function () {
                var that = this;
                axios.get("../Socket/closeDoor")
                    .then(function (response) {
                        console.log(response);
                        that.text = that.text + new Date().toLocaleTimeString() + "\n" + response.data + "\n";
                    })
            },
            openDoor: function () {
                var that = this;
                axios.get("../Socket/openDoor")
                    .then(function (response) {
                        console.log(response);
                        that.text = that.text + new Date().toLocaleTimeString() + "\n" + response.data + "\n";
                    })
            },
            closeLight: function () {
                var that = this;
                axios.get("../Socket/closeLight")
                    .then(function (response) {
                        console.log(response);
                        that.text = that.text + new Date().toLocaleTimeString() + "\n" + response.data + "\n";
                    })
            },
            openLight: function () {
                var that = this;
                axios.get("../openLight")
                    .then(function (response) {
                        console.log(response);
                        that.text = that.text + new Date().toLocaleTimeString() + "\n" + response.data + "\n";
                    })
            },
            closeHeater: function () {
                var that = this;
                axios.get("../Socket/closeHeater")
                    .then(function (response) {
                        console.log(response);
                        that.text = that.text + new Date().toLocaleTimeString() + "\n" + response.data + "\n";
                    })
            },
            openHeater: function () {
                var that = this;
                axios.get("../Socket/openHeater")
                    .then(function (response) {
                        console.log(response);
                        that.text = that.text + new Date().toLocaleTimeString() + "\n" + response.data + "\n";
                    })
            },
            Init: function () {
                var that = this;
                axios.get("../Socket/Init")
                    .then(function (response) {
                        console.log(response);
                    })
            },
        }
    })
</script>
</body>

</html>