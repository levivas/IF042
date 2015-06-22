<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<script src="/resources/js/jquery.tablesorter.min.js"></script>
<div id="content">
        <table  class="table table-hover" id="tableSort">
            <thead>
    <table  class="table table-hover">
        <thead>
        <tr>
            <th colspan="2" class="col-md-10">
                <h4>List groups</h4>
            </th>
            <th class="col-md-2">
                <button type="button" class="btn btn-success"  onclick="document.location='/group/add'"><span class="glyphicon glyphicon-plus"></span> Add</button>
            </th>
        </tr>
        <tr>
            <th class="col-md-5">Name</th>
            <th class="col-md-5">Role</th>
            <th class="col-md-2">Setting</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${groupList}" var="group">
            <c:if test="${ group.role.roleName != 'SuperAdmin'}">
                <tr>
                    <td onclick="document.location='/user?groupId=${group.groupId}'">${group.name}</td>
                    <td onclick="document.location='/user?groupId=${group.groupId}'">${group.role.roleName}</td>
                    <td class="center">
                        <button type="button" class="btn btn-primary" onclick="document.location='/group/editGroup?groupId=${group.groupId}'"><span class="glyphicon glyphicon-edit"></span></button>
                        <button type="button" class="btn btn-danger" onclick="document.location='/group/deleteGroup?groupId=${group.groupId}'"><span class="glyphicon glyphicon-trash"></span></button>
                </tr>
            </c:if>
        </c:forEach>
        </tbody>
    </table>

    <c:if test="${pageCount gt 1}">
        <ul class="pagination pagination-sm">
            <ul class="pagination pagination-sm">
                <c:forEach begin="1" end="${pageCount}" var="i" varStatus="loop">
                    <li <c:if test="${i-1 eq currentPage}"> class="active" </c:if> ><a href="?page=${i-1}">${i}</a></li>
                </c:forEach>
            </ul>
        </ul>
    </c:if>
</div>

<script>
    $("#tableSort").tablesorter();
</script>