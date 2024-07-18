<%@ include file="header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.javarush.apostol.jr_module3.util.WebConstants" %>
<c:set var="pageTitle" value="Edit Game"/>
<%@ include file="header.jsp" %>
<h1>Edit Game</h1>
<form action="${pageContext.request.contextPath}${WebConstants.EDIT_GAME}" method="post">
    <!-- Add form fields to edit the game state -->
    <input type="text" name="currentStep" placeholder="Current Step" />
    <button type="submit">Save Changes</button>
</form>
<%@ include file="footer.jsp" %>