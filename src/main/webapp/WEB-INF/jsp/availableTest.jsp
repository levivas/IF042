<%@ page import="java.util.Date" %>
<%@ page import="java.sql.Timestamp" %>
<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script src="/resources/js/jquery.tablesorter.min.js"></script>

<div id="content">
            <table class="table table-hover" id="tableSort">
                <thead>
                    <tr>
                        <sec:authorize access="hasRole('ROLE_STUDENT')">
                        <th class="sorter-false" align="center" colspan="8">
                            <h4>Assigned tests</h4>
                        </th>
                        </sec:authorize>
                        <sec:authorize access="hasRole('ROLE_TEACHER')">
                        <th class="text-center sorter-false" colspan="7">
                            <h4>Assigned tests</h4>
                        </th>
                        <th class="sorter-false text-center">
                            <button type="button" class="btn btn-success" onclick="document.location='/availableTest/assign'"><span class="glyphicon glyphicon-plus"></span> Assign new</button>
                        </th>
                        </sec:authorize>
                    </tr>
                    <tr>
                        <th> Test </th>
                        <th> Category </th>
                        <th> Surname </th>
                        <th> Name </th>
                        <th> Midle Name </th>
                        <th> Available from </th>
                        <th> Available to </th>
                        <th align="center">Settings</th>
                    </tr>
                </thead>
                <tbody>
                <c:if test="${!empty availableTestList}">
                <c:forEach items="${availableTestList}" var="availableTest">
                    <tr>
                        <td><c:out value="${availableTest.test.name}"/></td>
                        <td><c:out value="${availableTest.test.category.title}"/></td>
                        <td><c:out value="${availableTest.user.surname}"/></td>
                        <td><c:out value="${availableTest.user.name}"/></td>
                        <td><c:out value="${availableTest.user.middleName}"/></td>
                        <td><c:out value="${availableTest.startTime}"/></td>
                        <td><c:out value="${availableTest.finishTime}"/></td>
                        <td align="center">
                            <sec:authorize access="hasRole('ROLE_TEACHER')">
                            <button type="button" class="btn btn-primary"
                                    onclick="document.location='/availableTest/edit/?availableTestId=${availableTest.availableTestId}'"><span class="glyphicon glyphicon-edit"></span>
                            </button>
                            <button type="button" class="btn btn-danger"
                                    onclick="document.location='/availableTest/delete/?availableTestId=${availableTest.availableTestId}'"><span class="glyphicon glyphicon-trash"></span>
                            </button>
                            </sec:authorize>
                            <sec:authorize access="hasRole('ROLE_STUDENT')">
                            <c:if test="${currentTime.time lt availableTest.finishTime.time and currentTime.time gt availableTest.startTime.time}">
                                <c:if test="${availableTest.sessionTest eq null}">
                                    <button type="button" class="btn btn-default"
                                            onclick="document.location='/testing/start?availableTestId=${availableTest.availableTestId}'"><span class="glyphicon glyphicon-play"></span> Start
                                    </button>
                                </c:if>

                                <c:if test="${availableTest.sessionTest.finishTime.time gt currentTime.time}">
                                    <button type="button" class="btn btn-primary"
                                            onclick="document.location='/testing/start?availableTestId=${availableTest.availableTestId}'"><span class="glyphicon glyphicon-play"></span> Continue
                                    </button>
                                </c:if>
                                <c:if test="${availableTest.sessionTest.finishTime.time lt currentTime.time}">

                                <button type="button" class="btn btn-danger"
                                            onclick="document.location='/testing/getResult/?sessionTestId=${availableTest.sessionTest.sessionTestId}'"><span class="glyphicon glyphicon-time"></span> Finish
                                    </button>
                                </c:if>
                            </c:if>
                            </sec:authorize>
                        </td>
                    </tr>
                </c:forEach>
                </c:if>
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
    $("#tableSort").tablesorter({
        sortList : [[0,0]]
    });
</script>