<%--
  Created by IntelliJ IDEA.
  User: admin
  Date: 2019/5/7
  Time: 14:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
hello
</body>
</html>
<script>
    jump();
    function jump() {
        window.location.href="${pageContext.request.contextPath}/quartz/listJob"
    }
</script>