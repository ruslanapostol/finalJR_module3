<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Text Adventure Game</title>
</head>
<body>
<h1>${question}</h1>
<c:choose>
    <c:when test="${not empty options}">
        <form action="game" method="post">
            <c:forEach var="option" items="${options}">
                <input type="radio" name="answer" value="${option.key}" />${option.key}<br/>
            </c:forEach>
            <button type="submit">Ответить</button>
        </form>
    </c:when>
    <c:otherwise>
        <h2>${end}</h2>
        <form action="game" method="get">
            <button type="submit">Начать заново</button>
        </form>
    </c:otherwise>
</c:choose>
</body>
</html>
