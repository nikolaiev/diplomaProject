<%--
  Created by IntelliJ IDEA.
  User: vlad
  Date: 21.04.17
  Time: 0:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <%--CSS--%>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/alertify.min.css"/>

    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap-theme.min.css"/>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/myCss.css"/>
    <%--JS--%>
    <script src="${pageContext.request.contextPath}/js/jquery-3.2.1.min.js" ></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js" ></script>
    <script src="${pageContext.request.contextPath}/js/alertify.min.js" ></script>
</head>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">Diploma project</a>
        </div>
        <ul class="nav navbar-nav">
            <li><a href="/wav">Home</a></li>
            <li><a href="/wav/handle">Handle</a></li>
            <li><a href="/wav/download">Download</a></li>
            <li><a href="/wav/about">About</a></li>
        </ul>
    </div>
</nav>

</html>
