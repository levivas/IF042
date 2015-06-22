<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>


<div id="content">
    <div class="well col-md-8">
        <form:form  class="form-horizontal" method="post" action="/tests/addtest" commandName="newTest">
            <table class="table borderless col-md-12">
                <tr id="idr">
                    <td>
                        <form:label path="testId">Id</form:label>
                    </td>
                    <td>
                        <form:input class="form-control" path="testId" id="testId" type="number" />
                    </td>
                </tr>
                <tr>
                    <td>
                        <form:label path="name">Name</form:label>
                    </td>
                    <td>
                        <form:input class="form-control" path="name" id="name" />
                    </td>
                    <td class="whitebg">
                        <form:errors path="name" cssClass="alert-danger"/>
                    </td>
                </tr>
                <tr>
                    <td><label for="categoryId">Category</label></td>
                    <td>
                        <select class="form-control" name="categoryId" path="categoryId" id="categoryId">
                            <c:forEach items="${categoryList}" var="c">
                                <option value="${c.categoryId}"  <c:if test="${categId eq c.categoryId}"> selected </c:if> >${c.title}</option>
                            </c:forEach>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>
                        <form:label path="duration">Duration(minutes)</form:label>
                    </td>
                    <td>
                        <form:input class="form-control" path="duration" id="duration" type="number" />
                    </td>
                    <td class="whitebg" >
                        <form:errors path="duration" cssClass="alert-danger"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <form:label path="grade">Grade</form:label>
                    </td>
                    <td>
                        <form:input class="form-control" path="grade" id="grade" type="number" />
                    </td>
                    <td class="whitebg">
                        <form:errors path="grade" cssClass="alert-danger"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <form:label path="easyQuestionsCount">Easy questions count</form:label>
                    </td>
                    <td>
                        <form:input class="form-control" path="easyQuestionsCount" id="easyQuestionsCount" type="number" />
                    </td>
                    <td class="whitebg" >
                        <form:errors path="easyQuestionsCount" cssClass="alert-danger"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <form:label path="normalQuestionsCount">Normal questions count</form:label>
                    </td>
                    <td>
                        <form:input class="form-control" path="normalQuestionsCount" id="normalQuestionsCount" type="number" />
                    </td>
                    <td class="whitebg">
                        <form:errors path="normalQuestionsCount" cssClass="alert-danger"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <form:label path="hardQuestionsCount">Hard questions count</form:label>
                    </td>
                    <td>
                        <form:input class="form-control" path="hardQuestionsCount" id="hardQuestionsCount" type="number" />
                    </td>
                    <td class="whitebg">
                        <form:errors path="hardQuestionsCount" cssClass="alert-danger"/>
                    </td>
                </tr>
                <tr>
                    <td>
                        <form:button  class="btn btn-primary" type="submit" value="Save"><span class="glyphicon glyphicon-ok"></span> Save</form:button>
                        <button  class="btn btn-danger" type="button" onclick="form.reset()">
                            <span class="glyphicon glyphicon-ban-circle"></span> Cancel
                        </button>
                    </td>
                    <td>
                    </td>
                </tr>
            </table>
        </form:form>
    </div>
</div>

<script>
    $('document').ready(function(){
        var testId = $('#testId');
        testId.val('${test.testId}');
        if(testId.val() == '')
            testId.val('0');

        var duration = $('#duration');
        duration.val('${test.duration}');
        if(duration.val() == '')
            duration.val('0');

        var grade = $('#grade');
        grade.val('${test.grade}');
        if(grade.val() == '')
            grade.val('0');

        var easy = $('#easyQuestionsCount');
        easy.val('${test.easyQuestionsCount}');
        if(easy.val() == '')
            easy.val('0');

        var normal = $('#normalQuestionsCount');
        normal.val('${test.normalQuestionsCount}');
        if(normal.val() == '')
            normal.val('0');

        var hard = $('#hardQuestionsCount');
        hard.val('${test.hardQuestionsCount}');
        if(hard.val() == '')
            hard.val('0');

        $('#name').val('${test.name}');
        $('#idr').hide();
    });
</script>