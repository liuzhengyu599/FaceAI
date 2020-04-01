<%--
  Created by IntelliJ IDEA.
  User: ayu
  Date: 2020/2/11
  Time: 11:07
  登录失败时跳转的页面
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"  isELIgnored="false" %>
<html>
<head>
    <title>登陆失败</title>
</head>
<body>
<script>
    alert("没有此用户！");
    history.go(-1);
</script>

</body>
</html>
