<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<script src="/resources/js/jquery.min.js"></script>
<script src="/resources/js/ratingUser.js"></script>

<div id="content">
    <div id="choise">
        <br/>
        Group
        <select class="input-sm span3" id="group_select" name="groups">
            <option value="--">--</option>
            <c:if test="${not empty groupList}">
                <c:forEach items="${groupList}" var="g">
                    <option value="${g.groupId}">${g.name}</option>
                </c:forEach>
            </c:if>
        </select>
                Category
        <select class="input-sm span3" id="category_select" name="categories">
            <option value="--">--</option>
            <c:if test="${not empty categoryList}">
                <c:forEach items="${categoryList}" var="c">
                    <option value="${c.categoryId}">${c.title}</option>
                </c:forEach>
            </c:if>
        </select>
        <button class="btn btn-primary" onclick="loadData()"><span class="glyphicon glyphicon-refresh"></span> Show</button>
        <br/>
        <button onclick="document.location='/rating/categories'" class="btn btn-default"><span class="glyphicon glyphicon-list"></span> Categories rating</button>


    </div>
    <div id="container">
        <div id="upd">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>№ </th>
                    <th>Surname</th>
                    <th>Name</th>
                    <th>Middle name</th>
                    <th>Group</th>
                    <th>grade point average</th>
                </tr>
                </thead>
                <c:if test="${not empty resultList}">
                    <tbody>


                            <c:set var="i" value="${position}" scope="page"></c:set>
                            <c:forEach items="${resultList}" var="result">
                            <c:if test="${currentUserId eq result.user.userId}"><c:set var="c" value="class='btn-info'"></c:set></c:if>
                        <tr onclick="document.location='/userStats?userId=${result.user.userId}'">
                            <td ${c}>${i}</td>
                            <td ${c}>${result.user.surname}</td>
                            <td ${c}>${result.user.name}</td>
                            <td ${c}>${result.user.middleName}</td>
                            <td ${c}>${result.user.group.name}</td>
                            <td ${c}><fmt:formatNumber type="number" maxFractionDigits="2" value="${result.markPercents}"/></td>
                            <c:set var="c" value=""></c:set>
                            <c:set var="i" value="${i + 1}"></c:set>
                        </tr>
                    </c:forEach>
                    </tbody>
                </c:if>
            </table>
            <c:if test="${pageCount gt 1}">
                <ul class="pagination pagination-sm">
                    <ul class="pagination pagination-sm">
                        <c:forEach begin="1" end="${pageCount}" var="i" varStatus="loop">
                            <li <c:if test="${i-1 eq currentPage}"> class="active" </c:if> ><a href="#" onclick="loadData(${i-1})">${i}</a></li>
                        </c:forEach>
                    </ul>
                </ul>
            </c:if>
        </div><!--upd-->
    </div><!--end container-->

</div>



