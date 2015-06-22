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

<div id="header">
    <div id="logo">
        <h1><a href="#">iTest</a></h1>
</div>
    <div id="menu">
        <ul>
                 <li><a href="${homeUrl}">Home</a></li>
            <sec:authorize access="hasRole('ROLE_TEACHER')">
                <li><a href="${groupUrl}">Groups</a></li>
            </sec:authorize>
            <sec:authorize access="hasRole('ROLE_ADMIN')">
                <li><a href="${categoryUrl}">Categories</a></li>
            </sec:authorize>
                <li><a href="${resultsUrl}">Results</a></li>
        </ul>
    </div>
    <div id="menu-right">
        <ul>
            <li>
                <a href="${rating}">Rating</a>
            </li>
            <li><a href="${profileUrl}">Profile</a></li>
            <li><a href="${logoutUrl}">Logout</a></li>
        </ul>
    </div>
    <div id ="header-right">
    </div>
</div>

