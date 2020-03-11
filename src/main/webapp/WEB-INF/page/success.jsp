<%--
  Created by IntelliJ IDEA.
  User: ayu
  Date: 2020/2/10
  Time: 14:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>处理页</title>
</head>
<body>
<p align="center">
    ${msg}
</p>
<div>
    <span id="text1">5</span>秒后返回
</div>
<script>
    var i=5;
    var text1=document.getElementById("text1");
    var fun=function(){
        text1.innerHTML=i;
        i--;
        if(i==-1){
            clearInterval(time1);
            history.back();
        }
    }
    var time1=setInterval(fun,1000);
</script>

</body>
</html>
