<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 04.02.2023
  Time: 7:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Meals</title>

    <style type="text/css">
        TABLE {
            /*width: 600px;*/

            border-collapse: collapse; /* Убираем двойные линии между ячейками */
        }

        TD, TH {
            padding: 3px; /* Поля вокруг содержимого таблицы */
            border: 1px solid black; /* Параметры рамки */
        }

        TR.red {
            color: red;
        }

        TR.green {
            color: green;
        }
    </style>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<table>
    <a href="meals?add=true">Add meal</a>

    <tr>

        <th>Date</th>
        <th>ID</th>
        <th>Description</th>
        <th>Calories</th>
    </tr>

    <jsp:useBean id="mealsTos" scope="request" type="java.util.List"/>
    <c:forEach var="mealslist" items="${mealsTos}">

        <form action="/topjava/meals">

            <tr class="green">
                <c:if test="${mealslist.excess == true}">
            <tr class="red">

                </c:if>


                    <fmt:parseDate value="${ mealslist.dateTime }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                                   type="both"/>
                <td><fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }"/>
                </td>

                <td><input type="text" id="id" name="id" value=${mealslist.id} readonly size="5"><br></td>
                <td>${mealslist.description}</td>
                <td>${mealslist.calories}</td>
                <td><input type="submit" name="edit" value="edit"></td>
                <td><a href="meals?delete=true&id=${mealslist.id}">delete</a></td>
        </form>
        </tr>

    </c:forEach>
</table>


</body>
</html>
