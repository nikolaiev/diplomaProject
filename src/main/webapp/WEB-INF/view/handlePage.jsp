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

    <script type="text/javascript" src="${pageContext.request.contextPath}/js/loader.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/handlingPage.js"></script>

</head>
<body>

    <div id="chart_div_samples" class="my-diagram"></div>
    <div id="chart_div_freq" class="my-diagram"></div>

    <form action="/api/filter" method="post" id="filter-form">

        <%--TODO replace with proper type--%>

        <input type="hidden" class="form-control"  name="service_type" value="FILTER">

        <div class="form-group">
            <label >Lower frequency:</label>
            <input type="number" class="form-control" min="1" name="begin_freq">
        </div>

        <div class="form-group">
            <label >Top frequency:</label>
            <input type="number" class="form-control" max="${sessionScope.max_freq/2}" name="end_freq">
        </div>

        <div align="center">
            <button type="submit" class="btn btn-info">Filter</button>
        </div>
    </form>


</body>
</html>


<jsp:include page="/WEB-INF/view/fragments/footer.jsp" />

<%--charts scriplets--%>
<script>
    google.charts.load('current', {packages: ['corechart', 'line']});
    google.charts.setOnLoadCallback(drawBasic);

    function drawBasic() {
        drawSamples();
        drawFrequencies();
    }
    
    function drawSamples() {

        var data = new google.visualization.DataTable();
        data.addColumn('number', 'X');
        data.addColumn('number', 'Amplitude');

        var freqData=[];
        <%
         final int MAX_SAMPLES_COUNT=50000;
         int [] samplesArray=(int[]) request.getSession().getAttribute("samples");
         if(samplesArray==null)
             return;
         int arrayLength=samplesArray.length;
         double normilizeCoeff=arrayLength>MAX_SAMPLES_COUNT?arrayLength/10000:1;

         for (int i=0,j=0; i<samplesArray.length; i+=normilizeCoeff,j++) { %>
        freqData[<%= j %>] = [<%= j %>,<%= samplesArray[i] %>];
        <% } %>

        data.addRows(freqData);

        var options = {
            hAxis: {
                title: 'Samples'
            },
            vAxis: {
                title: 'Value'
            }
        };

        var chart = new google.visualization.LineChart(document.getElementById('chart_div_samples'));

        chart.draw(data, options);
    }
    
    function drawFrequencies() {
        var data = new google.visualization.DataTable();
        data.addColumn('number', 'X');
        data.addColumn('number', 'Amplitude');

        var freqData=[];
        <%
        //TODO normilize array

         int [] freqArray=(int[]) request.getSession().getAttribute("freq");

       for (int i=0; i<freqArray.length; i++) { %>
            freqData[<%= i %>] = [<%= i %>,<%= freqArray[i] %>];
        <% } %>

        data.addRows(freqData);

        var options = {
            hAxis: {
                title: 'Frequency'
            },
            vAxis: {
                title: 'Db'
            }
        };

        var chart = new google.visualization.LineChart(document.getElementById('chart_div_freq'));

        chart.draw(data, options);
    }
</script>