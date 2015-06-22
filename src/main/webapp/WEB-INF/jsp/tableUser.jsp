<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="/resources/js/forCreatingUser.js"></script>
<script src="/resources/js/ValidationForUser.js"></script>

<div id="content">
        <table  class="table table-hover">
            <thead>
                <tr>
                    <th colspan="6">
                        <h4>Users list</h4>
                    </th>
                    <td class="whitebg">
                        <button type="button" class="btn btn-success"  onclick="document.location='/user/add?groupId=${groupId}'"><span class="glyphicon glyphicon-plus"> Add</span></button>
                    </td>

                </tr>
                <tr>
                    <th>Surname</th>
                    <th>Name</th>
                    <th>Middle name</th>
                    <th>Login</th>
                    <th>Group</th>
                    <th>Created</th>
                    <th>Settings</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${userList}" var="user">
                <c:if test="${((not (user.userId eq superAdminId)) and (not (user.userId eq currentUserId)))}">
                <tr>
                    <td><c:out value="${user.surname}"/></td>
                    <td><c:out value="${user.name}"/></td>
                    <td><c:out value="${user.middleName}"/></td>
                    <td><c:out value="${user.userName}"/></td>
                    <td><c:out value="${user.group.name}"/></td>
                    <td><c:out value="${user.created}"/></td>
                    <td>
                        <button type="button" class="btn btn-primary" onclick="document.location='/user/edit?userId=${user.userId}'"><span class="glyphicon glyphicon-edit"></span></button>
                        <button type="button" class="btn btn-danger" onclick="document.location='/user/delete?userId=${user.userId}&groupId=${user.group.groupId}'"><span class="glyphicon glyphicon-trash"></span></button>
                    </td>
                </tr>
                </c:if>
                </c:forEach>
            </tbody>
        </table>
           <c:if test="${pageCount gt 1}">
        <ul class="pagination pagination-sm">
            <ul class="pagination pagination-sm">
                <c:forEach begin="1" end="${pageCount}" var="i" varStatus="loop">
                    <li <c:if test="${i-1 eq currentPage}"> class="active" </c:if> ><a href="?page=${i-1}&groupId=${groupId}">${i}</a></li>
                </c:forEach>
            </ul>
        </ul>
    </c:if>
</div>
