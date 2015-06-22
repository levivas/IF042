<%--<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>--%>
<%--<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="content-type" content="text/html; charset=utf-8" />
    <title>Login iTest</title>
    <link rel="stylesheet" href="/resources/css/loginstyle.css">
</head>

<body>
<div id ="logo"><h1>iTest</h1></div>
<section class="container">
    <div class="login">
        <h1>Login on iTest</h1>
        <form class="login-form" action="j_spring_security_check" method="post" >

                <p>
                    <label for="j_username">Username</label>:
                    <input id="j_username" name="j_username" size="20" maxlength="50" type="text"/>
                </p>

                <p>
                    <label for="j_password">Password</label>:
                    <input id="j_password" name="j_password" size="20" maxlength="50" type="password"/>
                </p>

            <p class="remember_me">
                <label>
                    <input type="checkbox" name="_spring_security_remember_me" id="remember_me">
                    Remember me
                </label>
            </p>

            <p><input type="submit" value="Login"/></p>
        </form>
        <p style="color: ${color}" class="message">${message}</p>
    </div>


</section>
</body>
</html>