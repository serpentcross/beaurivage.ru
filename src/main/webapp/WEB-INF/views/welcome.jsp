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

            <div class="row" id="circle4">
                <div class="small-3 large-2 columns text-center">
                    <div class="grow pic">ARN  o.s.w.s.PageNotFound#1136 No mapping found for HTTP request with URI [/favicon.ico] in DispatcherServlet with name 'dispatcher'
                        Hibernate: select user0_.id as id1_1_, user0_.password as password2_1_, user0_.username as username3_1_ from user user0_ where user0_.username=?
                        Hibernate: select roles0_.user_id as user_id1_1_0_, roles0_.role_id as role_id2_2_0_, role1_.id as id1_0_1_, role1_.name as name2_0_1_ from user_role roles0_ inner join role role1_ on roles0_.role_id=role1_.id where roles0_.user_id=?
                    </div>
                </div>
                <div class="small-3 large-2 columns text-center">
                    <div class="grow pic"><img src="http://lorempixel.com/400/400" /></div>
                </div>
                <div class="small-3 large-2 large-offset-2 columns text-center">
                    <div class="grow pic"><img src="http://lorempixel.com/400/400" /></div>
                </div>
                <div class="small-3 large-2 columns text-center">
                    <div class="grow pic"><img src="http://lorempixel.com/400/400" /></div>
                </div>
                <div class="small-3 large-2 columns text-center">
                    <div class="grow pic">ARN  o.s.w.s.PageNotFound#1136 No mapping found for HTTP request with URI [/favicon.ico] in DispatcherServlet with name 'dispatcher'
                        Hibernate: select roles0_.user_id as user_id1_1_0_, roles0_.role_id as role_id2_2_0_, role1_.id as id1_0_1_, role1_.name as name2_0_1_ from user_role roles0_ inner join role role1_ on roles0_.role_id=role1_.id where roles0_.user_id=?
                    </div>
                </div>
                <div class="small-3 large-2 columns text-center">
                    <div class="grow pic"><img src="http://lorempixel.com/400/400" /></div>
                </div>
                <div class="small-3 large-2 columns text-center">
                    <div class="grow pic"><img src="http://lorempixel.com/400/400" /></div>
                </div>
            </div>

        </div>
        <!-- /container -->
        <script src="${contextPath}/resources/js/jquery-3.1.1.slim.min.js"></script>
        <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
        </body>
    </html>
