<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div align="center">
<form:form method="POST" action="../../../testing/getResult?testId=${testId}&userId=${userId}" commandName="test">
    <table width="95%">
        <tbody>
        <tr>
            <td class="whitebg"></td>
        </tr>
        <tr>
            <td class="whitebg" align="left" colspan="2">
                <h4>
                    You start test: ${testName}. Time left: <span id="countdown"></span>
                </h4>
            </td>
        </tr>
        <tr>
            <td class="whitebg"></td>
        </tr>
        <c:forEach items="${questions}" var="question" varStatus="questionCounter">
            <tr>
                <td class="whitebg" rowspan="5" valign="top" width="2%" align="right">
                    <b><c:out value="${questionCounter.count}."/></b>
                </td>
                <td class="whitebg" width="95%" valign="top" align="left">
                    &nbsp;
                    <b>
                        ${question.title}
                    </b>
                </td>
            </tr>
            <c:forEach items="${question.answers}" var="answer" varStatus="answerCounter">
                <tr>
                    <td class="whitebg">
                        <input type="radio" name="answerForQuestion${question.questionId}" value="${answer.answerId}">
                        <b><c:out value="${answerCounter.count})"/></b>
                        &nbsp;  ${answer.text}
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td class="whitebg" colspan="2">
                    <hr size="1">
                </td>
            </tr>
            <tr>
                <td class="whitebg" colspan="2">
                    &nbsp;
                </td>
            </tr>
        </c:forEach>
        <tr>
            <td class="whitebg" align="middle" colspan="2">
                <button class="btn btn-primary" id="submitButton" type="submit">Finish test</button>
            </td>
        </tr>
        <tr>
            <td class="whitebg" colspan="2" align="middle">
                <br/>
            </td>
        </tr>
        <tr>
            <td class="whitebg" colspan="2" align="middle">
                <br/>
            </td>
        </tr>
        </tbody>
    </table>
</form:form>
</div>
<script>
    /// variables for time units
    var  hours, minutes, seconds;
    var duration = ${duration} * 1000 * 60;
    // get tag element
    var countdown = document.getElementById("countdown");

    // update the tag with id "countdown" every 1 second
    setInterval(function () {
        // find the amount of "seconds" between now and target
        duration -= 1000;
        var seconds_left = duration / 1000;
        // do some time calculations
        hours = parseInt(seconds_left / 3600);
        seconds_left = seconds_left % 3600;
        minutes =parseInt(seconds_left / 60);
        seconds = parseInt(seconds_left % 60);
        // format countdown string + set tag value
        countdown.innerHTML = hours + "h, "
                + minutes + "m, " + seconds + "s";
        if(duration<1){
            $("#submitButton").trigger("click");
        }
    }, 1000);
</script>
