<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<script src="../../resources/js/jquery.min.js"></script>
<script src="../../resources/js/ratingUser.js"></script>
<script src="../../resources/js/highcharts/highcharts.js"></script>
<script src="../../resources/js/highcharts/modules/data.js"></script>
<script src="../../resources/js/highcharts/modules/exporting.js"></script>


<div id="content">
    <div id="choise">
        <br/>
        Group
        <select class="input-sm" id="group_select" name="groups">
            <option value="--">--</option>
            <c:if test="${not empty groupList}">
                <c:forEach items="${groupList}" var="g">
                    <option value="${g.groupId}">${g.name}</option>
                </c:forEach>
            </c:if>
        </select>
        <button class="btn btn-primary" onclick="loadDataForCategoryRating()"><span class="glyphicon glyphicon-refresh"></span> Show</button>
        <br/>
</div>
    <div id="container">
        <div id="upd">
            <table id="table" class="table table-hover">
                <thead>
                <tr>
                    <th>№ </th>
                    <th>Category</th>
                    <th>grade point average</th>
                </tr>
                </thead>
                <c:if test="${not empty resultList}">
                    <tbody>
                    <c:forEach items="${resultList}" var="result"  varStatus="i">
                        <tr>
                            <td>${i.count}</td>
                            <td>${result.caption}</td>
                            <td><fmt:formatNumber type="number" maxFractionDigits="2" value="${result.val}"/></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </c:if>
            </table>
            <c:if test="${pageCount gt 1}">
                <ul class="pagination pagination-sm">
                    <ul class="pagination pagination-sm">
                        <c:forEach begin="1" end="${pageCount}" var="i" varStatus="loop">
                            <li <c:if test="${i-1 eq currentPage}"> class="active" </c:if> ><a href="?page=${i-1}">${i}</a></li>
                        </c:forEach>
                    </ul>
                </ul>
            </c:if>
        </div><!--upd-->
    </div><!--end container-->

    <div id="chartContainer" style="min-width: 310px; min-height: 400px; margin: 0 auto"></div>
</div>



