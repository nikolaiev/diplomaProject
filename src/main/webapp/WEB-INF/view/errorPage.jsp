<%--
  Created by IntelliJ IDEA.
  User: vlad
  Date: 20.04.17
  Time: 5:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error page</title>
    <jsp:include page="/WEB-INF/view/fragments/header.jsp"/>
</head>
<body>
    <div class="alert alert-danger">
        <strong>Error!</strong> ${error}
    </div>
</body>
</html>

<jsp:include page="/WEB-INF/view/fragments/footer.jsp" />