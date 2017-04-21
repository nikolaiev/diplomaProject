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
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>


</head>
<body>
    <div align="center" class="top-buffer">
        <h6>Original file</h6>
        <br>
        <audio controls>
            <source src="/static/${sessionScope.initFileNameShort}" type="audio/wav">
            Your browser does not support the audio element.
        </audio>
        <br>

        <h6>Filtered file</h6>

        <br>
        <audio controls>
            <source src="/static/${sessionScope.filteredFileName}" type="audio/wav">
            Your browser does not support the audio element.
        </audio>

        <form class="top-buffer" action="/wav/handle" method="get">
            <button type="submit" class="btn btn-warning">Change filter params</button>
        </form>
    </div>


    <div id="chart_div_samples" class="my-diagram"></div>
    <div id="chart_div_freq" class="my-diagram"></div>

</body>
</html>

<jsp:include page="/WEB-INF/view/fragments/footer.jsp" />

<%--charts scriplets--%>
<script>
    google.charts.load('current', {packages: ['corechart', 'line']});
    google.charts.setOnLoadCallback(drawBasic);
    function drawBasic() {
        drawSamples();
        drawFrequensies();
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

    function drawFrequensies() {
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

