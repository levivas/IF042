<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="content">
    <div class="well">
    <h2> Creating category </h2>
    <form:form class="form-horizontal" method="post" action="${action}" commandName="category">
        <table>
            <tr>
                <td><form:label path="title">Name of Category</form:label></td>
                <td><form:input class="form-control" path="title" /></td>
                <td class="whitebg"><form:errors path="title" cssClass="alert-danger"/></td>
            </tr>

            <tr><td>Teacher</td>
                <td><select class="form-control" name="teacherSelectIds" multiple>

                   <c:forEach items="${userList}" var="user">
                    <c:set var="a" value="0"/>
                       <c:forEach items="${currentTeacherList}" var = "currentUser">
                           <c:if test="${user.userId eq currentUser.userId}">
                               <c:set var="a" value="1"/>
                           </c:if>
                       </c:forEach>
                       <option value="${user.userId}" <c:if test="${a eq '1'}">selected</c:if> >${user.name}</option>

                   </c:forEach>

                </select>
                </td>
            </tr>

            <tr>
                <td colspan="2">
                    <button class="btn btn-success" type="submit" ><span class="glyphicon glyphicon-ok"></span></button>
                    <button class="btn btn-danger" onclick="document.location='/category'"><span class="glyphicon glyphicon-ban-circle"></span></button>
                </td>
            </tr>
        </table>
    </form:form>
    </div>
   
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