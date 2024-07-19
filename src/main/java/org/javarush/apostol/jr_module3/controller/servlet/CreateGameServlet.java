package org.javarush.apostol.jr_module3.controller.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.javarush.apostol.jr_module3.controller.service.GameService;
import org.javarush.apostol.jr_module3.model.GameState;

import java.io.IOException;

import static org.javarush.apostol.jr_module3.util.WebConstants.*;

@Log4j2
@WebServlet(CREATE_GAME)
public class CreateGameServlet extends HttpServlet {
    private GameService gameService;

    @Override
    public void init() {
        gameService = GameService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(CREATE_GAME).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        GameState gameState = gameService.createGameState();
        req.getSession().setAttribute(GAME_STATE, gameState);
        resp.sendRedirect(GAME);
    }
}
