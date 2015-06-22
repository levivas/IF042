<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="content">
    <h2>Creating group</h2>

    <form:form method="post" action="${action}" commandName="group">
        <table>
            <tr>
                <td><form:label path="name">Name</form:label></td>
                <td><form:input class="form-control" path="name" /></td>
                <td class="whitebg"><form:errors path="name" cssClass="alert-danger"/></td>

            </tr>
            <tr>
                <td>
                    Role</td>
                <td>

                    <select class="form-control" name="roleSelect">
                        <c:forEach items="${roleList}" var="role">
                            <c:if test="${role.roleId==currentRole.roleId}">
                                <option selected value="${role.roleId}">${role.roleName}</option>
                            </c:if>
                            <c:if test="${role.roleId!=currentRole.roleId and role.roleName != 'SuperAdmin'}">
                                <option  value="${role.roleId}">${role.roleName} </option>
                            </c:if>
                        </c:forEach>
                    </select>
                </td>
            </tr>

            <tr>
                <td colspan="2">
                    <button type="submit" class="btn btn-success" ><span class="glyphicon glyphicon-ok"></span></button>
                    <button type="button" class="btn btn-danger" onclick="document.location='/group'"><span class="glyphicon glyphicon-ban-circle"></span></button></td>
            </tr>
        </table>
    </form:form>

    <c:if test="${!empty groupList}">
        <h2>List groups</h2>
        <table class="table table-hover">
            <thead>
                <tr>
                    <td>Name</td>
                    <td>Role</td>
                    <td>Setting</td>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${groupList}" var="group">
                <c:if test="${ group.role.roleName != 'SuperAdmin'}">
                    <tr>
                        <td class="center"><a href="/user?groupId=${group.groupId}"><c:out value="${group.name}"/></a></td>
                        <td class="center"><c:out value="${group.role.roleName}"/></td>
                        <td class="center"><button class="btn btn-primary" onclick="document.location='/group/editGroup?groupId=${group.groupId}'"><span class="glyphicon glyphicon-edit"></span></button>
                        <button class="btn btn-danger" onclick="document.location='/group/deleteGroup?groupId=${group.groupId}'"><span class="glyphicon glyphicon-trash"></span></button>
                    </tr>
                </c:if>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
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