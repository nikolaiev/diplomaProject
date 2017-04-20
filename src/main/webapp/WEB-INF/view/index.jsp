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
    <link href="/css/dropzone.css" rel="stylesheet"/>
    <script rel="script" src="${pageContext.request.contextPath}/js/wavPage.js"></script>
    <script rel="script" src="${pageContext.request.contextPath}/js/dropzone.js"></script>
</head>
<body>
    Diploma project start page! UPLOAD YOUR WAV FILE!!!
    <div id="my_dropzone">
        <form action="/file-upload" class="dropzone">
            <div class="fallback">
                <input name="file" type="file" />
            </div>
        </form>
    </div>


    <form method="post" action="/api/upload" enctype="multipart/form-data">
        <input type="file" name="file_name" value="wavFile" accept="audio/wav"/>
        <br>
        <br>
        <input type="submit" value="uploadFile"/>
    </form>


</body>
</html>

<jsp:include page="/WEB-INF/view/fragments/footer.jsp" />
