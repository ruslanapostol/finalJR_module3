package org.javarush.apostol.jr_module3.controller.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import java.io.IOException;


import static org.javarush.apostol.jr_module3.util.WebConstants.*;


@Log4j2
@WebServlet(SET_PLAYER_NAME)
public class SetPlayerNameServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String playerName = request.getParameter(PLAYER_NAME);
        request.getSession().setAttribute(PLAYER_NAME, playerName);
        response.sendRedirect(request.getContextPath() + GAME);
    }


}
