<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<script src="/resources/js/jquery.datetimepicker.js"></script>
<link rel="stylesheet" type="text/css" href="/resources/css/jquery.datetimepicker.css"/>

<script src="/resources/js/results_handlers.js"></script>
<script src="/resources/js/jquery.tablesorter.min.js"></script>

<div id="content">
    <div id="choise">
        <table class="table borderless">
            <thead>
                <tr><th colspan="6">Show results...</th></tr>
                <tr>
                    <th>From</th>
                    <th>To</th>
                    <th>Category</th>
                    <th>Test</th>
                    <sec:authorize access="hasRole('ROLE_TEACHER')">
                    <th>Group</th>
                    </sec:authorize>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>
                        <input class="input-sm form-control" type="text" name="dateFrom" id="dateFrom"/>
                    </td>
                    <td>
                        <input class="input-sm form-control" type="text" name="dateTo" id="dateTo"/>
                    </td>
                    <td>
                        <select class="input-sm form-control" id="category_select" name="categories">
                            <option value="--">--</option>
                            <c:if test="${not empty categoryList}">
                                <c:forEach items="${categoryList}" var="c">
                                    <option value="${c.categoryId}">${c.title}</option>
                                </c:forEach>
                            </c:if>
                        </select>
                    </td>
                    <td>
                        <select class="input-sm form-control" id="test_select" name="tests">
                            <option value="--">--</option>
                            <c:if test="${not empty testList}">
                                <c:forEach items="${testList}" var="t">
                                    <option value="${t.testId}">${t.name}</option>
                                </c:forEach>
                            </c:if>
                        </select>
                    </td>
                    <td>
                        <sec:authorize access="hasRole('ROLE_TEACHER')">
                        <select class="input-sm form-control" id="group_select" name="groups">
                            <option value="--">--</option>
                            <c:if test="${not empty groupList}">
                                <c:forEach items="${groupList}" var="g">
                                    <c:if test="${g.groupId gt 3}">
                                        <option value="${g.groupId}">${g.name}</option>
                                    </c:if>
                                </c:forEach>
                            </c:if>
                        </select>
                        </sec:authorize>
                    </td>
                </tr>
                <tr>
                    <td>
                        <button class="btn btn-info" onclick="loadData()"><span class="glyphicon glyphicon-refresh"></span> Show</button>
                    </td>
                </tr>
            </tbody>
        </table>

        <script>$('#dateFrom').datetimepicker({format:'Y-m-d H:00:00'});</script>
        <script>$('#dateTo').datetimepicker({format:'Y-m-d H:00:00'});</script>

    </div>
    <div id="container">
        <div id="upd">
            <table class="table table-hover" id="table"><!--TABLE-->
                    <thead>
                        <tr>
                            <th id="test_header">Test</th>
                            <sec:authorize access="hasRole('ROLE_TEACHER')">
                                <th>Name</th>
                                <th>Middle name</th>
                                <th>Surname</th>
                                <th>Group</th>
                            </sec:authorize>
                            <th>Category</th>
                            <th>Result from 100%</th>
                            <th>Max grage for test</th>
                            <th>Mark</th>
                            <th>Date</th>
                            <sec:authorize access="hasRole('ROLE_TEACHER')">
                                <th colspan="2">Action</th>
                            </sec:authorize>
                        </tr>
                    </thead>
                <c:if test="${not empty resultList}">
                    <tbody>
                        <c:forEach items="${resultList}" var="result">
                            <tr id="row${result.testResultId}">
                                <td>${result.test.name}</td>
                                 <sec:authorize access="hasRole('ROLE_TEACHER')">
                                    <td>${result.user.name}</td>
                                    <td>${result.user.middleName}</td>
                                    <td>${result.user.surname}</td>
                                    <td>${result.user.group.name}</td>
                                </sec:authorize>
                                <td>${result.test.category.title}</td>
                                <td>${result.markPercents}</td>
                                <td>${result.test.grade}</td>
                                <td>${result.test.grade*result.markPercents/100}</td>
                                <td>${result.passDate}</td>
                                <sec:authorize access="hasRole('ROLE_TEACHER')">
                                    <td>
                                        <button type="button" class="btn btn-primary" title="Statistics"
                                                onclick="document.location = '/userStats?resultId=${result.testResultId}'"><span class="glyphicon glyphicon-stats"></span></button>
                                    </td>
                                    <td>
                                        <button type="button" class="btn btn-danger"
                                                onclick="removeResult(${result.testResultId})"><span class="glyphicon glyphicon-trash"></span></button>
                                    </td>
                                </sec:authorize>
                            </tr>
                        </c:forEach>
                    </tbody>
                </c:if>
            </table>
            <sec:authorize access="hasRole('ROLE_TEACHER')">
            <button class="btn btn-info" id="generateBtn" onclick="spanMessage()"><span class="glyphicon glyphicon-list-alt"></span> Generate PDF report</button>
            <span id="messageForButton">select all fields</span>
            </sec:authorize>

            <c:if test="${pageCount gt 1}">
                <ul class="pagination pagination-sm">
                    <ul class="pagination pagination-sm">
                        <c:forEach begin="1" end="${pageCount}" var="i" varStatus="loop">
                            <li <c:if test="${i-1 eq currentPage}"> class="active" </c:if> ><a href="#" onclick="loadData(${i-1})">${i}</a></li>
                        </c:forEach>
                    </ul>
                </ul>
            </c:if>
        </div><!--upd-->
    </div><!--end container-->
</div>

<script src="/resources/js/jquery.tablesorter.min.js"></script>

<script>
    $(document).ready(function(){
//       $('#dateFrom').datetimepicker({format:'Y-m-d H:00:00'});
//       $('#dateTo').datetimepicker({format:'Y-m-d H:00:00'});
        $(function(){
            $("#table").tablesorter();
        });
    });
</script>



