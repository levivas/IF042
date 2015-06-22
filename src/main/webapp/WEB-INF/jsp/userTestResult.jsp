<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div>
    <div>
        <table class="table table-hover">
            <thead>
                <tr>
                    <th colspan="2">
                        <h4>Your test result</h4>
                    </th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>Your name</td>
                    <td>${result.user.surname}&nbsp;${result.user.middleName}&nbsp;${result.user.name}</td>
                </tr>
                <tr>
                    <td>Test</td>
                    <td>${result.test.name}</td>
                </tr>
                <tr>
                    <td>Category</td>
                    <td>${result.test.category.title}</td>
                </tr>
                <tr>
                    <td>Max grade for test</td>
                    <td>${result.test.grade}</td>
                </tr>
                <tr>
                    <td>Result from 100%</td>
                    <td>${result.markPercents}</td>
                </tr>
                <tr>
                    <td>Result grade</td>
                    <td>${result.test.grade*result.markPercents/100}</td>
                </tr>
            </tbody>
        </table>
        <div align="center">
            <button class="btn btn-primary"  onclick="document.location='/availableTest'">OK</button>
        </div>
    </div><!--end container-->
</div>