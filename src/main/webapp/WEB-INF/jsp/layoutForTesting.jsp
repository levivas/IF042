<%@ page import="org.springframework.security.core.context.SecurityContextHolder" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${first eq true}">
    <html>
    <head>
        <title></title>
        <link rel="stylesheet" href="/resources/css/testing.css"/>
        <script src="/resources/js/jquery-2.1.0.min.js"></script>

        <script src="/resources/js/jquery.countdown.min.js"></script>
    </head>
    <body style="margin-bottom: 90px;">

    <div id="content" style="min-height: 600px;">
    <div id="middle">
    <div id="page-title-hr"></div>
    <div>
        <h1 id="page-title">${sessionQuestion.sessionTest.test.name}</h1>
    </div>
    <div style="margin-top:0px;">
    <div id="testrunner">
    <div class="testrunner">
    <div class="sidebar" id="test-sidebar">
        <div id="clock" class="clock"></div>
        <ul class="items">

            <c:forEach items="${sessionQuestionIds}" var="sessionQuestionId" varStatus="questionCounter">
                <li>
                    <button id="b${sessionQuestionId}"
                            class="btn btn-default btn-xs <c:if test="${sessionQuestionId eq sessionQuestion.sessionQuestionId}"> active</c:if> ">${questionCounter.count}</button>
                </li>
            </c:forEach>

        </ul>
        <div class="buttons"><input data-toggle="modal" data-target="#myModal" id="qweewq" name="finish" type="button"
                                    value="Finish"
                                    class="btn btn-danger"></div>
    </div>
</c:if>
<div id="upd">
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                    <h4 class="modal-title" id="myModalLabel">Finish test</h4>
                </div>
                <div class="modal-body">
                    <h4>Do you want to finish test</h4>
                </div>
                <div class="modal-footer">
                    <button type="button"
                            class="btn btn-default"
                            data-dismiss="modal">No</button>

                    <button id="finishTest"
                            type="button" method="POST"
                            onclick="document.location='/testing/getResult?sessionTestId=${sessionQuestion.sessionTest.sessionTestId}'"
                            class="btn btn-primary">Yes</button>
                </div>
            </div>
        </div>
    </div>

    <div class="question" id="questionPanel">
        <pre class="chili">${sessionQuestion.question.title}</pre>
        <ul class="answers">

            <c:forEach items="${sessionQuestion.question.answers}" var="answer" varStatus="answerCounter">
                    <li>
                        <input type="${type}" name="answerIds" value="${answer.answerId}"
                            <c:forEach items="${sessionQuestion.sessionAnswers}" var="sessionAnswer">
                               <c:if test="${sessionAnswer.answer eq answer}">checked</c:if>
                            </c:forEach>
                                >
                        <label>${answer.text}</label>
                    </li>
            </c:forEach>
        </ul>
        <div class="buttons">
            <input id="save" name="answer" type="button" value="Save" class="btn btn-primary">
        </div>
    </div>
</div>
<c:if test="${first eq true}">
    </div>
    </div>
    </div>
    </div>
    </div>
    </body>
    </html>

    <script>
        $(document).ready(function () {
            Date.prototype.format = function (format) //author: meizz
            {
                var o = {
                    "M+": this.getMonth() + 1, //month
                    "d+": this.getDate(),    //day
                    "h+": this.getHours(),   //hour
                    "m+": this.getMinutes(), //minute
                    "s+": this.getSeconds(), //second
                    "q+": Math.floor((this.getMonth() + 3) / 3),  //quarter
                    "S": this.getMilliseconds() //millisecond
                }

                if (/(y+)/.test(format)) format = format.replace(RegExp.$1,
                        (this.getFullYear() + "").substr(4 - RegExp.$1.length));
                for (var k in o)if (new RegExp("(" + k + ")").test(format))
                    format = format.replace(RegExp.$1,
                                    RegExp.$1.length == 1 ? o[k] :
                                    ("00" + o[k]).substr(("" + o[k]).length));
                return format;
            }


            function timer() {
                var time = new Date().getTime();
                time += ${duration} * 60 * 1000;
                $('#clock').countdown(new Date(time).format('yyyy/MM/dd h:mm:ss'))
                        .on('update.countdown', function (event) {
                            var $this = $(this).html(event.strftime(''
                                    + '<span class="time">%H:%M:%S</span>'));
                        })
                        .on('finish.countdown', function (event) {
                            $('#finishTest').trigger('click');
//                        $(this).parent()
//                            .addClass('disabled')
//                            .html('<strong>This offer has expired!</strong>');
                        });
            }

            function setSaveClick() {
                var id = $('.active').attr('id').substring(1);
                var url = '/testing/save?sessionQuestionId=' + id + '&';
                var ids = [];
                $('input[name=answerIds]').each(function (index, value) {
                    if (value.checked) {
                        ids.push('answerIds=' + $(value).val());
                    }
                });
                $.ajax({
                    url: url + ids.join('&'),
                    type: 'POST',
                    success: function () {
                        $('#b' + id).parent().addClass('answered');
                    }
                });
            }

            timer();

            $('#save').click(setSaveClick);

            $.each($(':button'), function (index, value) {
                if ($(value).attr('id')[0] == 'b') {
                    var id = $(value).attr('id').substring(1);

                    $(value).click(function () {

                        var currBtn = this;
                        $(':button').each(function (index, value) {
                            if ($(value).hasClass('active'))
                                $(value).removeClass('active');
                        });

                        $.ajax({
                            url: '/testing/getQuestion?SessionQuestionId=' + id,
                            success: function (data) {
                                $('#upd').html(data);
                                $(currBtn).addClass('active');
                                $('#save').click(setSaveClick);
                            }
                        });

                    });
                }
            });

        });
    </script>

</c:if>
