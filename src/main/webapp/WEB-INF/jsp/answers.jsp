<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<script src="/resources/js/test_handlers.js"></script>

<div id="content">
        <table class="table table-hover">
            <thead>
                <tr>
                    <th colspan="2">
                        <h3>${questionTitle}</h3>
                    </th>
                </tr>
                <tr>
                    <th>Text</th>
                    <th>Is correct</th>
                </tr>
            </thead>
    <c:if test="${!empty answerList}">
            <tbody>
                <c:forEach items="${answerList}" var="answer">
                    <tr id="a${answer.answerId}">
                        <td>${answer.text}</td>
                        <td>${answer.correct}</td>
                   </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</div>