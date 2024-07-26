<%@ include file="header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Star Trek Adventure Game</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/styles/main.css">
    <script src="https://cdn.jsdelivr.net/npm/fireworks-js@latest/dist/fireworks.min.js"></script>
</head>
<body>
<div class="content">
    <h1>${question}</h1>
    <c:choose>
        <c:when test="${not empty options}">
            <form action="${pageContext.request.contextPath}/game" method="post">
                <c:forEach var="option" items="${options}">
                    <label>
                        <input type="radio" name="answer" value="${option.key}" />
                            ${option.key}
                    </label><br/>
                </c:forEach>
                <button type="submit">Ответить</button>
            </form>
        </c:when>
        <c:otherwise>
            <div id="fireworks-container"></div>
            <h2>${end}</h2>
            <form action="${pageContext.request.contextPath}/game" method="post">
                <input type="hidden" name="reset" value="true" />
                <button type="submit">Начать заново</button>
            </form>
            <script>
                // Initialize fireworks if the game has ended with a win
                if ("${end}" === "Ты начал новую жизнь, свободную от прошлого. Победа.") {
                    const container = document.getElementById('fireworks-container');
                    const fireworks = new Fireworks(container, {
                        maxRockets: 3, // max # of rockets to spawn
                        rocketSpawnInterval: 150, // milliseconds to check if new rockets should spawn
                        numParticles: 100, // number of particles to spawn when rocket explodes
                        explosionMinHeight: 0.2, // percentage. min height at which rockets can explode
                        explosionMaxHeight: 0.9, // percentage. max height before a particle is exploded
                        explosionChance: 0.08 // chance in each tick the rocket will explode
                    });
                    fireworks.start();
                }
            </script>
        </c:otherwise>
    </c:choose>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>