package org.javarush.apostol.jr_module3.controller.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import java.io.IOException;
import static org.javarush.apostol.jr_module3.util.WebConstants.*;


@Log4j2
@WebServlet(DELETE_GAME)
public class DeleteGameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().removeAttribute("gameState");
        resp.sendRedirect(GAME_LIST);
    }
}
