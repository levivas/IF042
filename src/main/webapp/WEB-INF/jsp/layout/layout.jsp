<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="Cache-Control" content="no-store" />
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title><tiles:insertAttribute name="title" ignore="true" /></title>
    <link rel="stylesheet" type="text/css" href="/resources/css/style.css"/>
    <link rel="stylesheet" type="text/css" href="/resources/css/bootstrap.min.css"/>

    <script src="/resources/js/jquery-2.1.0.min.js"></script>
    <script src="/resources/js/bootstrap.min.js"></script>
</head>
<body>
    <tiles:insertAttribute name="header" />
    <div id="page">
	    <tiles:insertAttribute name="body" />
	    <%--<tiles:insertAttribute name="sidebar" />--%>
    </div>
    <tiles:insertAttribute name="footer" />
</body>
</html>