<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page language="java" contentType="text/html" pageEncoding="UTF-8"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="ru-ru" dir="ltr" class="uk-admin-background">

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>BEAU-RIVAGE</title>
        <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
        <link rel="apple-touch-icon-precomposed" href="images/apple-touch-icon.png">
        <link rel="stylesheet" href="${contextPath}/resources/css/bootstrap.min.css">
        <link rel="stylesheet" href="${contextPath}/resources/css/uikit.min.css">
        <link rel="stylesheet" href="${contextPath}/resources/css/beaurivage.css">
        <link rel="stylesheet" href="${contextPath}/resources/css/tadle.css">
        <script src="${contextPath}/resources/js/jquery-3.1.1.min.js"></script>
        <script src="${contextPath}/resources/js/uikit.js"></script>
        <script src="${contextPath}/resources/js/beaurivage.js"></script>
    </head>

    <body class="uk-font-days uk-padding-tm">

        <!-- ФИО, тел. e-mail -->
        <div class="">
            <div class="uk-container uk-container-center uk-text-center">
                <form class="uk-form" id="customerData">
                    <div class="uk-grid">
                        <div class="uk-width-medium-1-4 uk-margin-top"><input type="text" id="customerName" placeholder="Имя" class="uk-width-medium-1-1"></div>
                        <div class="uk-width-medium-1-4 uk-margin-top"><input type="text" id="customerSurname" placeholder="Фамилия" class="uk-width-medium-1-1"></div>
                        <div class="uk-width-medium-1-4 uk-margin-top"><input type="text" id="customerMiddlename" placeholder="Отчество" class="uk-width-medium-1-1"></div>
                        <div class="uk-width-medium-1-4 uk-margin-top"><input type="text" id="customerPhone" placeholder="Телефон" class="uk-width-medium-1-1"></div>
                    </div>
                </form>
            </div>
        </div>
        <!-- Конец ФИО, тел. e-mail -->

        <!-- ФИО, тел. e-mail -->
        <div class="uk-margin-top">
            <div class="uk-container uk-container-center uk-text-center">
                <form class="uk-form uk-margin uk-form-stacked" method="post">
                    <fieldset>
                        <div class="uk-grid">
                            <div class="uk-width-medium-1-3 uk-margin-top">
                                <select id="dayOfWeek" class="uk-width-medium-1-1">
                                    <option value="mon">Понедельник</option>
                                    <option value="tue">Вторник</option>
                                    <option value="wed">Среда</option>
                                    <option value="thr">Четверг</option>
                                    <option value="frd">Пятница</option>
                                    <option value="sat">Суббота</option>
                                    <option value="sun">Воскресенье</option>
                                </select>
                            </div>
                            <div class="uk-width-medium-1-3 uk-margin-top">
                                <input type="text" id="timeFr" placeholder="время начала приёма 00:00:00" class="uk-width-medium-1-1">
                            </div>
                            <div class="uk-width-medium-1-3 uk-margin-top">
                                <input type="text" id="timeTo" placeholder="время конца приёма 00:00:00" class="uk-width-medium-1-1">
                            </div>
                        </div>
                    </fieldset>
                </form>
                <div class="uk-width-medium-1-2 uk-container-center uk-margin-top">
                    <input type="button" id="createRecord" class="uk-button uk-button-primary uk-width-medium-2-3" value="cоздать запись">
                </div>
            </div>
        </div>
        <!-- Конец ФИО, тел. e-mail -->

        <!-- Таблица -->
        <div class="uk-margin-top uk-padding-tm">
            <div class="uk-container uk-container-center uk-text-center">
                <div class="uk-flex uk-fl-sise" style="height: 100%; background: #fafafa;">
                    <div class="uk-width-medium-1-7 uk-panel uk-panel-box uk-panel-box-primary">ПОН<div id="record" class="uk-panel-time">9:00-12:00</div></div>
                    <div class="uk-width-medium-1-7 uk-panel uk-panel-box uk-panel-box-primary">ВТ</div>
                    <div class="uk-width-medium-1-7 uk-panel uk-panel-box uk-panel-box-primary">СР</div>
                    <div class="uk-width-medium-1-7 uk-panel uk-panel-box uk-panel-box-primary">ЧТ</div>
                    <div class="uk-width-medium-1-7 uk-panel uk-panel-box uk-panel-box-primary">ПТН</div>
                    <div class="uk-width-medium-1-7 uk-panel uk-panel-box uk-panel-box-primary">СБ</div>
                    <div class="uk-width-medium-1-7 uk-panel uk-panel-box uk-panel-box-primary">ВС</div>
                </div>
            </div>
        </div>
        <!-- Конец Таблицы -->

        <div id="popupWindow" class="modal">
            <div class="modal-content">
                <div class="modal-header">
                    sometext
                </div>
                <div class="modal-body">
                    <p>Some text in the Modal Body</p>
                    <p>Some other text...</p>
                    <input type="button" onclick="closeModal()" value="close">
                </div>
            </div>

        </div>

        <c:if test="${pageContext.request.userPrincipal.name != null}">
            <form id="logoutForm" method="POST" action="${contextPath}/logout">
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
            </form>

            <h2>Welcome ${pageContext.request.userPrincipal.name} | <a onclick="document.forms['logoutForm'].submit()">Logout</a></h2>

        </c:if>

        <script>
            // Get the modal
            var modal = document.getElementById('popupWindow');

            // Get the button that opens the modal
            var btn = document.getElementById("record");

            // Get the <span> element that closes the modal
            var span = document.getElementsByClassName("close")[0];

            // When the user clicks the button, open the modal
            btn.onclick = function() {
                modal.style.display = "block";
            };

            // When the user clicks on <span> (x), close the modal
            function closeModal() {
                modal.style.display = "none";
            }

        </script>

    </body>
</html>