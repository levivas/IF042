<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<script src="/resources/js/jquery-2.1.0.min.js"></script>
<%--<script src="/resources/js/userStats.js"></script>--%>
<script src="/resources/js/highcharts/highcharts.js"></script>
<script src="/resources/js/highcharts/modules/exporting.js"></script>
<script src="/resources/js/userStats.js"></script>

<div id="content">
    <h3>${test}</h3>
    <div id="answersChartContainer"
         style="min-width: 310px; height: 350px; max-width: 800px; min-height: 300px; margin: 0 auto"></div>
    <div id="timeChartContainer"
         style="min-width: 310px; max-width: 800px; min-height: 600px; margin: 0 auto"></div>

</div>
