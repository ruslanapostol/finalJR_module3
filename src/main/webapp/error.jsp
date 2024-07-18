<%@ include file="header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Error</title>
</head>
<body>
<h1>An error occurred</h1>
<p>${errorMessage}</p>
<a href="${pageContext.request.contextPath}/index.jsp">Go back to the main page</a>
</body>
</html>
<%@ include file="footer.jsp" %>