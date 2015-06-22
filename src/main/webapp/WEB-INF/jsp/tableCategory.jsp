<%@ page language="java" contentType="text/html; charset=utf-8"
         pageEncoding="utf-8"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>

<script src="../../resources/js/forCreatingUser.js"></script>
<script src="../../resources/js/ValidationForUser.js"></script>
<script src="/resources/js/bootstrap.file-input.js"></script>
<script src="/resources/js/jquery.tablesorter.min.js"></script>

<div class="container">
    <c:if test="${!empty categoryList}">
     <table class="table table-hover" id="tableSort">
         <thead>
            <tr>
                <th colspan="2" class="sorter-false">
                    <h4>Categories</h4>
                </th>
                <sec:authorize access="hasRole('ROLE_ADMIN')">
                    <th class="sorter-false">
                        <button type="button" class="btn btn-success" onclick="document.location='/category/add'"><span class="glyphicon glyphicon-plus"></span> Create</button>
                    </th>
                </sec:authorize>
            </tr>
            <tr>
                <th class="col-md-5">Title</th>
                <th class="col-md-4">Count</th>
                <th class="col-md-3">Settings</th>
            </tr>
         </thead>
         <tbody>
            <c:forEach items="${categoryList}" var="category">
                <tr>
                    <td onclick="document.location='/tests?categoryId=${category.categoryId}'">${category.title}</td>
                    <td onclick="document.location='/tests?categoryId=${category.categoryId}'">${fn:length(category.tests)}</td>
                     <td>
                     <sec:authorize access="hasRole('ROLE_ADMIN')">
                     <button type="button" class="btn btn-primary" onclick="document.location='/category/edit?categoryId=${category.categoryId}'"><span class="glyphicon glyphicon-edit"></span></button>
                     <button type="button" class="btn btn-danger" onclick="document.location='/category/delete/?categoryId=${category.categoryId}'"><span class="glyphicon glyphicon-trash"></span></button>
                     </sec:authorize>
                     <button type="button" class="btn btn-primary" onclick="document.location='/xml/testcat?categoryId=${category.categoryId}'"><span class="glyphicon glyphicon-export"></span> Export</button>
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
                    <li <c:if test="${i-1 eq currentPage}"> class="active" </c:if> ><a href="?page=${i-1}">${i}</a></li>
                </c:forEach>
            </ul>
        </ul>
    </c:if>
    <br/>

    <div class="alert alert-info col-md-8">
        <form method="POST" action="xml/uploadFile" enctype="multipart/form-data">
            <table class="borderless">
                <tr class="col-md-12">
                    <td class="col-md-1">
                        <button type="button" class="but btn btn-info" onclick="document.location='../xml/tests'"><span class="glyphicon glyphicon-export"></span> Export</button>
                    </td>
                    <td class="col-md-1">
                        <button type="submit" class="btn btn-info"><span class="glyphicon glyphicon-import"></span>Import</button>
                    </td>
                    <td class="col-md-8">
                        <select id="testSelect" class="form-control" name="categorySelect">
                            <c:forEach items="${categoryList}" var="category">
                                <option  value="${category.categoryId}">${category.title}</option>
                            </c:forEach>
                        </select>
                    </td>
                    <td class="col-md-2">
                        <input type="file" id="file1"  name="file1" title="Select a file to add" class="btn-primary" />
                    </td>
                </tr>
            </table>
        </form>
    </div>
</div>

<script>
    $('input[type=file]').bootstrapFileInput();
    $('.file-inputs').bootstrapFileInput();

    $("#tableSort").tablesorter();
</script>
