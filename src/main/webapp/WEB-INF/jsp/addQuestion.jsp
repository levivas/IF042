<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>

<div id="content">
    <h3>Add/edit question</h3>
    <form name="QuestionForm" action="/tests/addQuestion?testId=${testId}&questionId=${question.questionId}" method="post" onsubmit="return validation()">
        <br/>
        <div id ="errors" style="color: red"> </div>
        <br/>
        <label for="questionText" >Question:</label>
        <textarea class="form-control" name="questionText" id="questionText" cols="30" rows="5">${question.title}</textarea>
        </td>
        <br/>
        <label for="rank">Level</label>
        <select class="form-control" name="rank" id="rank">
            <option value="1" <c:if test="${question.rank eq 1}"> selected </c:if> >Easy</option>
            <option value="2" <c:if test="${question.rank eq 2}"> selected </c:if> >Normal</option>
            <option value="3" <c:if test="${question.rank eq 3}"> selected </c:if> >Hard</option>
        </select>
        <label for="type">Type</label>
        <select class="form-control" name="type" id="type">
            <option value="0" <c:if test="${not (question.type eq 1)}">selected="selected" </c:if> >Single</option>
            <option value="1" <c:if test="${question.type eq 1}">selected="selected" </c:if> >Multi</option>
        </select>
        <div id="answers">
            <h4>Answers:</h4>

            <c:if test="${not empty question and not empty question.answers}">
                <c:forEach var="answer" items="${question.answers}" varStatus="i">
                    <div id="block${i.count}">
                        ${i.count}.&nbsp;
                        <input class="form-control-sm" type="${type}" name="correct" value="${i.count}" <c:if test="${answer.correct}"> checked </c:if> />
                        <br/>
                        <textarea class="form-control" name="answer${i.count}" id="answer${i.count}" cols="30" rows="2">${answer.text}</textarea>
                        <br/>
                    </div>
                </c:forEach>
            </c:if>
            <%--else--%>
            <c:if test="${empty question or empty question.answers}">
                1.&nbsp;<input class="form-control-sm" type="${type}" name="correct" value="1" checked />
                <br/>
                <textarea class="form-control" name="answer1" id="answer1" cols="30" rows="2"></textarea>
                <br/>
                2.&nbsp;<input class="form-control-sm" type="${type}" name="correct" value="2"/>
                <br/>
                <textarea class="form-control" name="answer2" id="answer2" cols="30" rows="2"></textarea>
                <br/>
            </c:if>

        </div>
        <button type="button" id="more" class="btn btn-default"><span class="glyphicon glyphicon-plus"></span></button>&nbsp;
        <button type="button" id="less" class="btn btn-default" type="button" disabled="disabled"><span class="glyphicon glyphicon-minus"></span></button>
        <br/>
        <button type="submit" class="btn btn-success"><span class="glyphicon glyphicon-ok"></span> Save</button>
    </form>
</div>

<script>
    $('document').ready(function(){
        var type = $('#type'); // TYPE select
        var s = '${question.answers.size()}';
        i = s == '' ? 3 : +s + 1;
        $('#rank').val('${question.rank}');

        //MORE BUTTON CLICK
        $('#more').click(function() {
            var div = $('#answers');
            div.append('<div id="block' + i + '">'
                     + i + '.&nbsp;<input class="form-control-sm" type="' + (type.val() == '0' ? 'radio' : 'checkbox') +'" name="correct" value="' + i + '"/>'
                     + '<br/>'
                     + '<textarea class="form-control" name="answer' + i + '" id="answer' + i + '" cols="30" rows="2"></textarea>'
                     + '<br/>'
                     + '</div>');

            $('#less').prop("disabled", false);
            ++i;
            $("html, body").animate({ scrollTop: $(document).height() }, 1000);
        });

        $('#less').click(function() {
            var block = $('#block' + (i-1));
            block.remove();
            --i;
            if(i == 3) {
                $('#less').prop("disabled", true);
            }
        });


        type.change(function(){
            $('input[name=correct]').each(function(index, value) {
                $(value).attr('type', type.val() == '0' ? 'radio' : 'checkbox');
            });
        });
    });

    function validation() {
        var result = true;
        var err = $('#errors');
        err.html('');
        if ($('#questionText').val() == "") {
            err.append("*Please enter a question*<br/>");
            result = false;
        }
        if ($('#rank').val() == "") {
            err.append("*Please enter rank*<br/>");
            result = false;
        }
        var i = 1;
        var answer = $('#answer'+i);
        while (answer.length > 0) {
            if (answer.val() == "") {
                err.append("*Please enter an answer number " + i + "*<br/>");
                result = false;
            }
            ++i;
            answer = $('#answer'+i);
        }
        return result;
    }
</script>
