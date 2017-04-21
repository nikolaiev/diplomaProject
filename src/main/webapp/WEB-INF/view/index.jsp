<%--
  Created by IntelliJ IDEA.
  User: vlad
  Date: 20.04.17
  Time: 5:00
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <jsp:include page="/WEB-INF/view/fragments/header.jsp" />
    <link href="${pageContext.request.contextPath}/css/dropzone.css" rel="stylesheet"/>
    <script rel="script" src="${pageContext.request.contextPath}/js/dropzone.js"></script>
    <script rel="script" src="${pageContext.request.contextPath}/js/wavPage.js"></script>
</head>
<body>

    <div class="alert alert-danger" id="max-files-reached" hidden>
        <strong>Only one file allowed!</strong> Go to Handle menu.
    </div>

    <div class="alert alert-success" id="success-upload" hidden>
        <strong>File uploaded!</strong> Go to Handle menu.
    </div>


    <div>
        <%--param name is file_name--%>
        <form class="dropzone" drop-zone="" id="file-dropzone"></form>
    </div>





</body>
</html>

<jsp:include page="/WEB-INF/view/fragments/footer.jsp" />
