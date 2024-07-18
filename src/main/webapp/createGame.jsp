<%@ include file="header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.javarush.apostol.jr_module3.util.WebConstants" %>
<c:set var="pageTitle" value="Create New Game"/>
<%@ include file="header.jsp" %>
<h1>Create New Game</h1>
<form action="${pageContext.request.contextPath}${WebConstants.CREATE_GAME}" method="post">
    <button type="submit">Create Game</button>
</form>
<%@ include file="footer.jsp" %>