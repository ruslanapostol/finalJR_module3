<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 20px;
            background-color: #fff;
            color: #000;
        }
        .content {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
            background-color: #f2dede;
            border-radius: 10px;
            color: #a94442;
        }
        a {
            color: #337ab7;
        }
    </style>
</head>
<body>
<div class="content">
    <h1>An error occurred</h1>
    <p>Status Code: ${statusCode}</p>
    <p>Servlet Name: ${servletName}</p>
    <p>Request URI: ${requestUri}</p>
    <a href="${pageContext.request.contextPath}/index.jsp">Go back to the main page</a>
</div>
</body>
</html>