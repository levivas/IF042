<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="../../resources/js/forCreatingUser.js"></script>

<div id="content">
    <div class="well col-md-8">
        <h2> Creating users </h2>
        <form:form class="form-horizontal" method="post" action="${action}" commandName="user">
            <table class="table borderless col-md-12">
                <tr id="idr">
                    <td><form:input path="userId" id="userId" type="number"  /> <td>
                    </td>
                </tr>
                <tr>
                    <td><form:label path="surname" >Surname</form:label></td>
                    <td><form:input class="form-control"  path="surname" /></td>
                    <td><form:errors element="div" cssClass="alert alert-danger" path="surname" /></td>
                </tr>

                <tr>
                    <td><form:label path="name">Name</form:label></td>
                    <td><form:input class="form-control"  path="name" /></td>
                    <td><form:errors element="div"  cssClass="alert alert-danger" path="name" /></td>
                </tr>

                <tr>
                    <td><form:label path="middleName">Middle name</form:label></td>
                    <td><form:input class="form-control"  path="middleName" /></td>
                    <td><form:errors element="div"  cssClass="alert alert-danger" path="middleName" /></td>
                </tr>

                <tr>
                    <td>
                        Group</td>
                    <td>
                        <select class="form-control" id="groupSelect" name="groupSelect" onchange="f()">
                            <c:forEach items="${groupList}" var="group">
                                <c:if test="${group.groupId eq currentGroup.groupId}">
                                    <option selected value="${group.groupId}">${group.name} </option>
                                </c:if>
                                <c:if test="${group.groupId != currentGroup.groupId and group.name != 'SuperAdmin'}">
                                    <option  value="${group.groupId}">${group.name} </option>
                                </c:if>
                            </c:forEach>
                        </select>
                    </td>
                </tr>

                <tr>
                    <td><form:label path="userName">Login(email)</form:label></td>
                    <td><form:input class="form-control" path="userName"  /></td>
                    <td><form:errors element="div" cssClass="alert alert-danger"  path="userName" /></td>
                </tr>


                <tr id="trTitle">
                    <td>Categories</td>
                    <td id="checkboxes">
                            <form name="cat">
                                <ul class="list-group">
                                    <c:forEach var = "category" items = "${categoryList}">
                                        <c:set var = "f" value = "false"/>
                                        <c:forEach var="c" items="${currentCategories}">
                                            <c:if test="${c.categoryId eq category.categoryId}">
                                                <c:set var="f" value="true"/>
                                            </c:if>
                                        </c:forEach>
                                        <li class="list-group-item"><input type = "checkbox" name = "categoryIds" value = "${category.categoryId}" <c:if test="${f eq 'true'}"> checked </c:if> /><c:out value = "${category.title}" /></li>
                                    </c:forEach>
                                </ul>
                            </form>
                    </td>
                </tr>
            </table>
            <button type="submit"  class="btn btn-primary">Save<span class="glyphicon glyphicon-ok"></span></button>
            <button type="button" onclick="document.location='/group'" class="btn btn-danger" >Cancel<span class="glyphicon glyphicon-ban-circle"></span></button>
            <span id="error"></span>

        </form:form>
    </div>

</div>
<script>
    $('document').ready(function(){
        if ($("#groupSelect").val() != '3') {
            $("#checkboxes").hide();
            $("#trTitle").hide();
        }});

    $('document').ready(function(){
        $('#idr').hide();

    });

</script>
