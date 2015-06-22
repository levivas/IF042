<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="content">
    <div class="well">
        <h2>Creating group</h2>
        <form:form class="form-horizontal" method="post" action="${action}" commandName="group">
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
                        <button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-ok"></span></button>
                        <button type="button" class="btn btn-danger" onclick="document.location='/group'"><span class="glyphicon glyphicon-ban-circle"></span></button></td>
                </tr>
            </table>
        </form:form>
    </div>
</div>
