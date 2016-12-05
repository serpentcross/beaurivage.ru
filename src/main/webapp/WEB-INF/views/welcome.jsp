<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
    <html>
        <head>
            <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
            <meta http-equiv="X-UA-Compatible" content="IE=edge">
            <meta name="viewport" content="width=device-width, initial-scale=1">
            <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
            <meta name="description" content="">
            <meta name="author" content="">

            <title>Welcome</title>

            <link href="${contextPath}/resources/css/bootstrap.min.css" rel="stylesheet">
            <link href="${contextPath}/resources/css/common.css" rel="stylesheet">

            <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
            <!--[if lt IE 9]>
            <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
            <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
            <![endif]-->
        </head>
        <body>
        <div class="container">
            <div class="form-group">
                <label for="sel1">Select list:</label>
                <select class="form-control" id="sel1">
                    <option>Понедельник</option>
                    <option>Вторник</option>
                    <option>Среда</option>
                    <option>Четверг</option>
                    <option>Пятница</option>
                    <option>Суббота</option>
                    <option>Воскресенье</option>
                </select>
            </div>
            Try it Yourself »

            <c:if test="${pageContext.request.userPrincipal.name != null}">
                <form id="logoutForm" method="POST" action="${contextPath}/logout">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>

                <h2>Welcome ${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()">Logout</a></h2>

            </c:if>

            <c:if test="${pageContext.request.isUserInRole('ROLE_USER')}">

                User ${pageContext.request.userPrincipal.name} in ADMIN Group

            </c:if>
            <table class="table table-bordered">
                <thead>
                <tr class="bg-info">
                    <th class="tg-s6z2">Время записи</th>
                    <th class="tg-s6z2">Понедельник</th>
                    <th class="tg-s6z2">Вторник</th>
                    <th class="tg-s6z2">Среда</th>
                    <th class="tg-s6z2">Четверг</th>
                    <th class="tg-s6z2">Пятница</th>
                    <th class="tg-s6z2">Суббота</th>
                    <th class="tg-s6z2">Воскресенье</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>1</td>
                    <td>John</td>
                    <td>Carter</td>
                    <td>johncarter@mail.com</td>
                    <td>1</td>
                    <td>John</td>
                    <td>Carter</td>
                    <td>johncarter@mail.com</td>
                </tr>
                <tr>
                    <td>2</td>
                    <td>Peter</td>
                    <td>Parker</td>
                    <td>peterparker@mail.com</td>
                    <td>1</td>
                    <td>John</td>
                    <td>Carter</td>
                    <td>johncarter@mail.com</td>
                </tr>
                </tbody>
            </table>

        </div>
        <!-- /container -->
        <script src="${contextPath}/resources/js/jquery-3.1.1.slim.min.js"></script>
        <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
        </body>
    </html>
