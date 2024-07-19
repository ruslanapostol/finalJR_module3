package org.javarush.apostol.jr_module3.controller.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import java.io.IOException;
import static org.javarush.apostol.jr_module3.util.WebConstants.*;


@Log4j2
@WebServlet("/setPlayerName")
public class SetPlayerNameServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String playerName = request.getParameter("playerName");
        request.getSession().setAttribute(PLAYER_NAME, playerName);
        response.sendRedirect(request.getContextPath() + GAME_LIST);

    }
}
