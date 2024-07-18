<%@ include file="header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.javarush.apostol.jr_module3.util.WebConstants" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Star Trek Adventure Game</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #000;
            color: #fff;
        }
        h1, h2 {
            text-align: center;
        }
        .content {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
            background-color: #333;
            border-radius: 10px;
        }
        .form {
            text-align: center;
        }
        button {
            background-color: #ffcc00;
            border: none;
            padding: 10px 20px;
            font-size: 16px;
            cursor: pointer;
            border-radius: 5px;
        }
        button:hover {
            background-color: #ffaa00;
        }
    </style>
</head>
<body>
<div class="content">
    <h1>${question}</h1>
    <c:choose>
        <c:when test="${not empty options}">
            <form action="${pageContext.request.contextPath}${WebConstants.GAME}" method="post">
                <c:forEach var="option" items="${options}">
                    <input type="radio" name="answer" value="${option.key}" />${option.key}<br/>
                </c:forEach>
                <button type="submit">Ответить</button>
            </form>
        </c:when>
        <c:otherwise>
            <h2>${end}</h2>
            <form action="${pageContext.request.contextPath}${WebConstants.GAME}" method="get">
                <button type="submit">Начать заново</button>
            </form>
        </c:otherwise>
    </c:choose>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>