<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="content">
    <p class="meta"><small><a href="#"></a></small></p>
    <h3>Your profile</h3><br>
    <%--Get and showing information about user--%>
    <div class="entry">
        <h4> Surname: ${user.surname}</h4>
        <h4> Name: ${user.name}</h4>
        <h4> Middle name: ${user.middleName}</h4>
        <h4> Created: ${user.created}</h4>
        <h4> Group: ${user.group.name}</h4>
        <h4> Role: ${user.group.role.roleName}</h4>
        <c:if test="${not empty user.categories}">
            <h4> Categories: </h4>
                <c:forEach items="${user.categories}" var="category">
                    <h5>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;${category.title}</h5>
                </c:forEach>
        </c:if>
    </div>
    <!-- Button trigger modal -->
    <button class="btn btn-primary" data-toggle="modal" data-target="#myModal">
        <span class="glyphicon glyphicon-edit"></span> Edit profile
    </button>

    <form:form method="post" action="profile" commandName="user" onsubmit="return validation()">
    <!-- Modal -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Edit profile</h4>
                </div>
                <div class="modal-body">
                    <table>
                        <tr>
                            <td>
                                <form:label path="surname">Surname</form:label>
                            </td>
                            <td>
                                <form:input class="form-control" path="surname" id="surname" />
                                <div id ="error1" style="color: red"> </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <form:label path="name">Name</form:label>
                            </td>
                            <td>
                                <form:input class="form-control" path="name" id="name" />
                                <div id ="error2" style="color: red"> </div>
                            </td>
                        </tr>
                        <tr>
                            <td>
                                <form:label path="middleName">Middle name</form:label>
                            </td>
                            <td>
                                <form:input class="form-control" path="middleName" id="middleName"/>
                                <div id ="error3" style="color: red"> </div>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="modal-footer">
                    <button type="submit" class="btn btn-primary" ><span class="glyphicon glyphicon-ok"></span> Save</button>
                    <button type="button" class="btn btn-default" data-dismiss="modal"><span class="glyphicon glyphicon-ban-circle"></span> Close</button>
                </div>
            </div>
        </div>
    </div>
    </form:form>



</div>
<script>
    function validation() {
        var surname    = $('#surname');
        var name       = $('#name');
        var middleName = $('#middleName');
        var err1       = $('#error1');
        var err2       = $('#error2');
        var err3       = $('#error3');

        var result = true;

        err1.html('');
        err2.html('');
        err3.html('');

        var regexp = /^[A-Za-z]{2,30}$/

        if(surname.val().match(regexp) == null) {
            err1.append(' wrong surname</br>');
            result = false;
        }
        if(name.val().match(regexp) == null) {
            err2.append('wrong name</br>');
            result = false;
        }
        if(middleName.val().match(regexp) == null) {
            err3.append('wrong middleName</br>');
            result = false;
        }
        return result;
    }
    </script>