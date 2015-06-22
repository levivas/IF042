<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<c:url value="../../../../" var="homeUrl"/>
<c:url value="../../../../user" var="userUrl"/>
<c:url value="../../../../tests" var="testUrl"/>
<c:url value="../../../../group" var="groupUrl"/>
<c:url value="../../../../category" var="categoryUrl"/>
<c:url value="../../../../results" var="resultsUrl"/>
<c:url value="../../../../profile" var="profileUrl"/>
<c:url value="../../../../logout" var="logoutUrl"/>
<c:url value="../../../../rating" var="rating"/>

<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
    <div class="container">
        <div class="navbar-header">
            <button id="header button" type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">iTester</a>
        </div>
        <div class="collapse navbar-collapse">
            <ul class="nav navbar-nav">
                <li><a href="${homeUrl}">Home</a></li>
                <sec:authorize access="hasRole('ROLE_TEACHER')">
                    <li><a href="${groupUrl}">Groups</a></li>
                </sec:authorize>
                <sec:authorize access="hasRole('ROLE_ADMIN') or hasRole('ROLE_TEACHER')">
                    <li><a href="${categoryUrl}">Categories</a></li>
                </sec:authorize>
                <li><a href="${resultsUrl}">Results</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="${rating}">Rating</a>
                </li>
                <li><a href="${profileUrl}"><span class="glyphicon glyphicon-user"></span> Profile</a></li>
                <li><a href="${logoutUrl}"><span class="glyphicon glyphicon-log-out"></span>  Logout</a></li>
            </ul>
        </div><!--/.nav-collapse -->
    </div>
</div>

