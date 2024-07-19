<%@ include file="header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.javarush.apostol.jr_module3.util.WebConstants" %>
<c:set var="pageTitle" value="Delete Game"/>
<%@ include file="header.jsp" %>
<h1>Delete Game</h1>
<form action="${pageContext.request.contextPath}${WebConstants.DELETE_GAME}" method="post">
    <button type="submit">Delete Game</button>
</form>
<%@ include file="footer.jsp" %>
