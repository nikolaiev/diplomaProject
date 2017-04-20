<%--
  Created by IntelliJ IDEA.
  User: vlad
  Date: 20.04.17
  Time: 23:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Download file</title>
    <jsp:include page="/WEB-INF/view/fragments/header.jsp" />

</head>
<body>

<audio controls>
    <source src="horse.ogg" type="audio/ogg">
    <source src="horse.mp3" type="audio/mpeg">
    <source src="/static/${sessionScope.filteredFileName}" type="audio/mpeg">
    Your browser does not support the audio element.
</audio>
<br>
<br>
<a href="/wav/handle">Change filter params</a>

</body>
</html>

<jsp:include page="/WEB-INF/view/fragments/footer.jsp" />

