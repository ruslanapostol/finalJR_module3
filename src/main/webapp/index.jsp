<%@ include file="header.jsp" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="org.javarush.apostol.jr_module3.util.WebConstants" %>
<%@ page language="java" %>
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
        .intro {
            margin-bottom: 30px;
        }
        .form, .start-game {
            text-align: center;
            margin-bottom: 20px;
        }
        button {
            background-color: #ffcc00;
            border: none;
            padding: 10px 20px;
            font-size: 16px;
            cursor: pointer;
            border-radius: 5px;
            margin: 5px;
        }
        button:hover {
            background-color: #ffaa00;
        }
        input[type="text"] {
            padding: 10px;
            border-radius: 5px;
            border: 1px solid #ccc;
            margin-right: 10px;
        }
    </style>
</head>
<body>
<div class="content">
    <h1>Приветствие</h1>
    <div class="intro">
        <h2>Пролог</h2>
        <p>Звездная дата 2265.9. Вы находитесь в звездном порту Земли и готовитесь к поднятию на борт звездолета USS Enterprise. Впереди вас ждут неисследованные миры, новые цивилизации и приключения, о которых можно только мечтать.</p>
        <p>Ваша миссия — исследовать дальние уголки галактики, защищать Федерацию и открыть новые горизонты.</p>
    </div>
    <div class="intro">
        <h2>Знакомство с экипажем</h2>
        <p>Поднимаясь на борт USS Enterprise, вас встречает первая офицер Спок, держащий планшет со списком задач:</p>
        <p>— Добро пожаловать на борт, капитан! Я — Спок, ваш первый офицер. Позвольте представить вам остальных членов экипажа.</p>
        <p>— В инженерном отсеке работает командир Монтгомери Скотт, наш главный инженер. За тактическую сторону отвечает лейтенант Хикару Сулу, а наш медицинский офицер — доктор Леонард Маккой.</p>
        <p>Как мы можем обращаться к вам, капитан?</p>
    </div>
    <div class="start-game">
        <form action="${pageContext.request.contextPath}${WebConstants.GAME}" method="get">
            <input type="text" name="playerName" placeholder="Введите ваше имя" required />
            <button type="submit">Начать игру</button>
        </form>
    </div>
</div>
<%@ include file="footer.jsp" %>
</body>
</html>