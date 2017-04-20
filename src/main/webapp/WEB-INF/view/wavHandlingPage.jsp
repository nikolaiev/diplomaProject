<%--
  Created by IntelliJ IDEA.
  User: vlad
  Date: 20.04.17
  Time: 21:37
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>

    <jsp:include page="/WEB-INF/view/fragments/header.jsp" />
</head>
<body>

    <form action="/api/filter" method="post">
        <input type="number" min="1" name="begin_freq">
        <br>
        <input type="number" max="9600" name="end_freq">
        <br>
        <input type="submit" value="filter file">
    </form>
    <%--TODO insert frequency diagram--%>

</body>
</html>


<jsp:include page="/WEB-INF/view/fragments/footer.jsp" />
