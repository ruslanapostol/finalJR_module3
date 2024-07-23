package org.javarush.apostol.jr_module3.controller.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.log4j.Log4j2;
import org.javarush.apostol.jr_module3.controller.service.GameLogicService;
import org.javarush.apostol.jr_module3.controller.service.GameService;
import org.javarush.apostol.jr_module3.model.GameState;
import org.javarush.apostol.jr_module3.model.GameStep;
import org.javarush.apostol.jr_module3.util.PlayerUtils;

import java.io.IOException;
import static org.javarush.apostol.jr_module3.util.WebConstants.*;


@Log4j2
@WebServlet(GAME)
public class GameServlet extends HttpServlet {
    private GameService gameService;
    private GameLogicService gameLogicService;

    @Override
    public void init() {
        gameService = GameService.getInstance();
        gameLogicService = GameLogicService.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws
            IOException {
        try {
            HttpSession session = request.getSession();

            if (!PlayerUtils.ensurePlayerName(request, response, session)) {
                return;
            }

            GameState gameState = gameService.getOrCreateGameState(session);
            GameStep stepData = gameLogicService.getStep(gameState.getCurrentStep());
            setGameStepAttributes(request, stepData);

            request.getRequestDispatcher(GAME_JSP).forward(request, response);
        } catch (Exception e) {
            log.error("Error in GameServlet doGet", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while" +
                    " processing the game.");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            String answer = request.getParameter("answer");
            HttpSession session = request.getSession();
            GameState gameState = gameService.getOrCreateGameState(session);

            gameService.navigateGame(gameState, answer);

            response.sendRedirect(GAME);
        } catch (Exception e) {
            log.error("Error in GameServlet doPost", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while" +
                    " processing the game.");
        }
    }

    private static void setGameStepAttributes(HttpServletRequest request, GameStep stepData) {
        request.setAttribute("question", stepData.getQuestion());
        request.setAttribute("options", stepData.getOptions());
        request.setAttribute("end", stepData.getEnd());
    }
}