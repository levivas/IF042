<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<link rel="stylesheet" type="text/css" href="/resources/css/styleForTestResultReview.css"/>
<div align="center">
    <form:form method="GET" action="/results/reviewAnswer" commandName="testResult">
        <table>
            <tbody>
            <tr>
                <td>
                    <h4><strong>Student:</strong><i class="colorBlue">${testResult.user.surname} ${testResult.user.name} ${testResult.user.middleName}</i><br/></h4>
                    <h4><strong>Group:</strong><i class="colorBlue">${testResult.user.group.name}</i><br/></h4>
                    <h4><strong>Subject:</strong><i class="colorBlue">${testResult.test.category.title}</i></h4>
                    <h4><strong>Subject:</strong><i class="colorBlue">${testResult.test.name}</i></h4>
                    <h4><strong> Start test:</strong><i class="colorBlue">${testResult.sessionTest.startTime}</i></h4>
                    <h4><strong>Finish time:</strong><i class="colorBlue">${testResult.sessionTest.finishTime}</i><br/></h4>

                </td>
            </tr>
            <tr>
                <td></td>
            </tr>
            <c:forEach items="${sessionQuestions}" var="sessionQuestion" varStatus="questionCounter">
                <tr>
                    <td>
                        <h4><c:out value="${questionCounter.count}."/></h4>
                    </td>
                    <td>
                        <h4><b>${sessionQuestion.question.title}</b></h4>
                    </td>
                </tr>


                <tr>
                <td> </td>
                <td>
                <c:forEach items="${sessionQuestion.sessionAnswers}" var="sessionAnswer" >

                    <c:forEach items="${sessionQuestion.question.answers}" var="answer">

                        <c:if test="${(answer.answerId eq sessionAnswer.answer.answerId)and(answer.correct)}">
                            <c:set var="c" value="class='correctAnswer'"></c:set>
                        </c:if>
                        <c:if test="${(answer.answerId eq sessionAnswer.answer.answerId)and!(answer.correct)}">
                            <c:set var="c" value="class='uncorrectAnswer'"></c:set>
                        </c:if><c:if test="${answer.correct eq 'true'}">
                            <c:set var="c1" value="<span class='glyphicon glyphicon-ok'></span>"></c:set>
                        </c:if>

                        <p ${c}>${answer.text}${c1}</p>
                        <c:set var="c" value=""></c:set>
                        <c:set var="c1" value=""></c:set>

                    </c:forEach>
                    </td>
                    </tr>
                </c:forEach>
            </c:forEach>
            <tr>
                <td>
                    <button class="btn btn-primary"  onclick="document.location='http://localhost:8080/results/'">OK</button>
                </td>
            </tr>

            </tbody>
        </table>
    </form:form>

</div>

