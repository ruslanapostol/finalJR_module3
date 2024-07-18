<%@ include file="header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.javarush.apostol.jr_module3.util.WebConstants" %>
<c:set var="pageTitle" value="Game List"/>
<%@ include file="header.jsp" %>
<h1>Game List</h1>
<ul>
    <li><a href="${pageContext.request.contextPath}${WebConstants.GAME}">Current Game</a></li>
    <li><a href="${pageContext.request.contextPath}${WebConstants.CREATE_GAME}">Create New Game</a></li>
    <li><a href="${pageContext.request.contextPath}${WebConstants.EDIT_GAME}">Edit Current Game</a></li>
    <li><a href="${pageContext.request.contextPath}${WebConstants.DELETE_GAME}">Delete Current Game</a></li>
</ul>
<%@ include file="footer.jsp" %>