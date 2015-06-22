<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script src="/resources/js/test_handlers.js"></script>
<script src="/resources/js/jquery.tablesorter.min.js"></script>

<div id="content">
            <table class="table table-hover" id="tableSort">
                <thead>
                    <tr class="sorter-false">
                        <th colspan="8" class="sorter-false"><h3>Tests</h3></th>
                        <th class="sorter-false">
                            <button type="button" class="btn btn-success"
                                    onclick="document.location='/tests/add?categId=${categId}'"><span class="glyphicon glyphicon-plus"></span> Add</button>
                        </th>
                    </tr>
                    <tr>
                        <th>Name</td>
                        <th>Duration(minutes)</th>
                        <th>Category</th>
                        <th>Grade</th>
                        <th>Created</th>
                        <th>CreatedBy</th>
                        <th>Edited</th>
                        <th>EditedBy</th>
                        <th colspan="2">Settings</td>
                    </tr>
                </thead>
        <c:if test="${not empty testList}">
                <tbody>
                <c:forEach items="${testList}" var="test">
                    <tr id="t${test.testId}">
                        <td onclick="document.location='/tests/questions?testId=${test.testId}'">${test.name}</td>
                        <td onclick="document.location='/tests/questions?testId=${test.testId}'">${test.duration}</td>
                        <td onclick="document.location='/tests/questions?testId=${test.testId}'">${test.category.title}</td>
                        <td onclick="document.location='/tests/questions?testId=${test.testId}'">${test.grade}</td>
                        <td onclick="document.location='/tests/questions?testId=${test.testId}'">${test.created}</td>
                        <td onclick="document.location='/tests/questions?testId=${test.testId}'">${test.createdBy}</td>
                        <td onclick="document.location='/tests/questions?testId=${test.testId}'">${test.edited}</td>
                        <td onclick="document.location='/tests/questions?testId=${test.testId}'">${test.editedBy}</td>
                        <td colspan="2">
                            <button onclick="document.location='/testStats?testId=${test.testId}&test=${test.name}'" title="Statistics"
                                    type="button" class="btn btn-default"><span class="glyphicon glyphicon-stats"></span></button>

                            <button type="button" class="btn btn-primary" title="Edit"
                                    onclick="document.location='/tests/edit?id=${test.testId}&categId=${categId}'"><span class="glyphicon glyphicon-edit"></span></button>

                            <button type="button" class="btn btn-danger" title="Delete"
                                    onclick="deleteTest(${test.testId})"><span class="glyphicon glyphicon-trash"></span></button>

                            <button type="button" class="btn btn-default" title="Try"
                                    onclick="document.location='/testing/start?testId=${test.testId}'"><span class="glyphicon glyphicon-eye-open"></span></button>

                            <button type="button" class="btn btn-default" title="Export"
                                    onclick="document.location='/xml/test?testId=${test.testId}'"><span class="glyphicon glyphicon-export"></span></button>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:if>
        <c:if test="${pageCount gt 1}">
            <ul class="pagination pagination-sm">
                <ul class="pagination pagination-sm">
                    <c:forEach begin="1" end="${pageCount}" var="i" varStatus="loop">
                        <li <c:if test="${i-1 eq currentPage}"> class="active" </c:if> ><a href="/tests?categoryId=${categId}&page=${i-1}">${i}</a></li>
                    </c:forEach>
                </ul>
            </ul>
        </c:if>
    </table>
</div>

<script>
    $("#tableSort").tablesorter({
        sortList : [[0,0]]
    });
</script>