<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f2f2f2;
            color: #333;
            text-align: center;
            padding: 50px;
        }
        .error-container {
            background-color: #fff;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
            padding: 30px;
            max-width: 600px;
            margin: auto;
        }
        h1 {
            color: #e74c3c;
        }
        p {
            margin: 10px 0;
        }
        a {
            color: #3498db;
            text-decoration: none;
        }
        a:hover {
            text-decoration: underline;
        }
    </style>
</head>
<body>
<div class="error-container">
    <h1>An error occurred</h1>
    <p>Status Code: <c:out value="${pageContext.request.getAttribute('javax.servlet.error.status_code')}" /></p>
    <p>Servlet Name: <c:out value="${pageContext.request.getAttribute('javax.servlet.error.servlet_name')}" /></p>
    <p>Request URI: <c:out value="${pageContext.request.getAttribute('javax.servlet.error.request_uri')}" /></p>
    <c:if test="${pageContext.request.getAttribute('javax.servlet.error.exception') != null}">
        <p>Exception: <c:out value="${pageContext.request.getAttribute('javax.servlet.error.exception')}" /></p>
    </c:if>
    <a href="${pageContext.request.contextPath}/index.jsp">Go back to the main page</a>
</div>
</body>
</html>