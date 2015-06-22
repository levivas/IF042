<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script src="/resources/js/test_handlers.js"></script>

<div id="content">
    <h1>${test.name}</h1>
    <h2>All questions</h2>
    <button type="button" class="btn btn-success"
            onclick="document.location='/tests/questions/add?testId=${test.testId}'"><span class="glyphicon glyphicon-plus"></span> Add new</button>
        <table class="table table-hover">
            <thead>
                <tr>
                    <th>Title</th>
                    <th>Rank</th>
                    <th>Action</th>
                </tr>
            </thead>
    <c:if test="${!empty questionList}">
            <tbody>
            <c:forEach items="${questionList}" var="question">
                <tr id="q${question.questionId}">
                    <td>${question.title}</td>
                    <td>${question.rank}</td>
                    <td>
                        <button type="button" class="btn btn-primary"
                                onclick="document.location='/tests/answers?questionId=${question.questionId}'"><span class="glyphicon glyphicon-list"></span> Answers</button>

                        <button type="button" class="btn btn-primary"
                                onclick="document.location='/tests/questions/edit?id=${question.questionId}'"><span class="glyphicon glyphicon-edit"></span></button>

                        <button type="button" class="btn btn-danger"
                                onclick="deleteQuestion(${question.questionId})"><span class="glyphicon glyphicon-trash"></span></button>
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
                    <li <c:if test="${i-1 eq currentPage}"> class="active" </c:if> ><a href="/tests/questions?testId=${test.testId}&page=${i-1}">${i}</a></li>
                </c:forEach>
            </ul>
        </ul>
    </c:if>
</div>
