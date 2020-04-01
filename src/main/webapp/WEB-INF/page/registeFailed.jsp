<%--
  User: ayu
  Date: 2020/3/10
  Time: 11:40
  注册失败，已经存在用户的错误页面
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册失败</title>
</head>
<body>
<script>
    let name='${name}';
    alert("你已经注册了"+name);
    history.go(-1);
</script>


</body>
</html>
