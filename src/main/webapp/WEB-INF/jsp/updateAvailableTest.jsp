<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script src="../../resources/js/jquery-2.1.0.min.js"></script>
<script src="../../resources/js/jquery.datetimepicker.js"></script>
<script src="../../resources/js/jquery.sortElements.js"></script>
<link rel="stylesheet" type="text/css" href="../../resources/css/jquery.datetimepicker.css"/>
<div id="content">
    <div id="mainTable">
        <link rel="stylesheet" type="text/css" href="../../resources/css/jquery.datetimepicker.css"/>
        <div id ="errors" style="color: red"> </div>
        <br/>
        <div class="well">
            <h2>Assign test</h2>
            <form:form class="form-horizontal" method="POST" action="../../${action}" onsubmit="return validation()" commandName="availableTest">
                <table class="table table-hover">
                    <tr>
                        <td> Category </td>
                        <td>
                            <select class="form-control" id="categorySelect" name="categorySelect">
                                <option value="-1"></option>
                                <c:forEach items="${categoryList}" var="category">
                                    <c:if test="${category.categoryId==currentCategory.categoryId}">
                                         <option selected value="${category.categoryId}">${category.title} </option>
                                    </c:if>
                                    <c:if test="${category.categoryId!=currentCategory.categoryId}">
                                        <option  value="${category.categoryId}">${category.title} </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td> Test </td>
                        <td>
                            <select class="form-control" id="testSelect" name="testSelect">
                                <c:forEach items="${testList}" var="test">
                                    <c:if test="${test.testId==currentTest.testId}">
                                        <option selected value="${test.testId}">${test.name} </option>
                                    </c:if>
                                    <c:if test="${test.testId!=currentTest.testId}">
                                        <option value="${test.testId}">${test.name}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td> Group </td>
                        <td>
                            <select class="form-control" id="groupSelect"  name="groupSelect">
                                <option value="-1"></option>
                                <c:forEach items="${groupList}" var="group">
                                    <c:if test="${group.groupId==currentGroup.groupId}">
                                        <option selected value="${group.groupId}">${group.name} </option>
                                    </c:if>
                                    <c:if test="${group.groupId!=currentGroup.groupId}">
                                        <option  value="${group.groupId}">${group.name} </option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td> Student </td>
                        <td>
                            <select class="form-control" id="userSelect" name="userSelect">
                                <c:forEach items="${userList}" var="user">
                                    <c:if test="${user.userId==currentUser.userId}">
                                        <option selected value="${user.userId}">${user.surname} ${user.name} ${user.middleName}</option>
                                    </c:if>
                                    <c:if test="${user.userId!=currentUser.userId}">
                                        <option value="${user.userId}">${user.surname} ${user.name} ${user.middleName}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><form:label path="startTime">Available From</form:label></td>
                        <td><form:input class="form-control" id="startTime" path="startTime" value="${availableTest.startTime}"/></td>
                        <td><form:errors path="startTime" cssClass="alert-danger"/></td>
                    </tr>
                    <tr>
                        <td><form:label path="finishTime">Available To</form:label></td>
                        <td><form:input class="form-control" id="finishTime" path="finishTime" value="${availableTest.finishTime}"/></td>
                        <td><form:errors path="finishTime" cssClass="alert-danger"/></td>
                    </tr>
                    <tr>
                        <td>
                            <%--<input type="submit" value="Assign" />--%>
                            <button class="btn btn-success" type="submit"><span class="glyphicon glyphicon-ok"></span> Assign/Update</button>
                        </td>
                        <td>
                            <button class="btn btn-danger"  type="button" onclick="document.location='/availableTest'"><span class="glyphicon glyphicon-ban-circle"></span> Cancel</button>
                        </td>
                    </tr>
                </table>
            </form:form>
        </div>
    </div>
</div>
<script type="text/javascript">
    $(document).ready(function() {

        $('#startTime').datetimepicker({format:'Y-m-d H:00:00'});
        $('#finishTime').datetimepicker({format:'Y-m-d H:00:00'});

        $('#groupSelect').change(function(e) {
           var group = $("#groupSelect");
            var groupId=group.val();
            $.get('${pageContext.request.contextPath}/user/getUsersByGroup?id='+groupId, function(users) {
                if (groupId == -1){
                    var appendList='';
                } else {
                    var appendList='<option value="-2">Whole group</option>';
                }
                $.each(users, function(index, user) {
                    appendList+='<option value="'+user.userId+'">' + user.name + ' ' + user.middleName + ' '
                            + user.surname + '</option>'
                });
                $("#userSelect").empty();
                $('#userSelect').append(appendList);
            });
        });

        $('#categorySelect').change(function(e) {
            var category = $("#categorySelect");
            var categoryId=category.val();
            $.get('${pageContext.request.contextPath}/tests/getTestsByCategory?id='+categoryId, function(tests) {
                var appendList='';
                $.each(tests, function(index, test) {
                    appendList+='<option value="'+test.testId+'">' + test.name + '</option>'
                });
                $("#testSelect").empty();
                $('#testSelect').append(appendList);
            });
        });
    });
    function validation() {
        var category   = $('#categorySelect');
        var group      = $('#groupSelect');
        var test       = $('#testSelect');
        var user       = $('#userSelect');
        var startTime  = $('#startTime');
        var finishTime = $('#finishTime');
        var err        = $('#errors');

        var result = true;

        err.html('');

        if(category.val() == null) {
            err.append('Please choose a category</br>');
            result = false;
        }
        if(group.val() == null) {
            err.append('Please choose a group</br>');
            result = false;
        }
        if(test.val() == null) {
            err.append('Please choose a test</br>');
            result = false;
        }
        if(user.val() == null) {
            err.append('Please choose a user</br>');
            result = false;
        }
        if(startTime.val() == '') {
            err.append('Please choose start time</br>');
            result = false;
        }
        if(finishTime.val() == '') {
            err.append('Please choose finish time</br>');
            result = false;
        }

        if(new Date(startTime.val()).getTime() >= new Date(finishTime.val()).getTime()) {
            err.append('The start time is after finish time! It\'s impossible!</br>');
            result = false;
        }

        return result;
    }
</script>












