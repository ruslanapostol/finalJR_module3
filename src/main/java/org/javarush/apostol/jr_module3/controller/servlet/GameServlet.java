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
            HttpSession session = request.getSession();
        try {

            if (checkIfPlayerNameIsSet(request, response, session)) return;

            GameState gameState = gameService.getOrCreateGameState(session);
            GameStep currentStep = gameLogicService.getStep(gameState.getCurrentStep());
            setGameStepAttributes(request, currentStep);

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
            HttpSession session = request.getSession();

            if (checkIfPlayerNameIsSet(request, response, session)) return;

            if (resetCheck(request, response, session)) return;

            String answer = request.getParameter("answer");
            GameState gameState = gameService.getOrCreateGameState(session);
            gameService.navigateGame(gameState, answer);
            GameStep currentStep = gameLogicService.getStep(gameState.getCurrentStep());
            setGameStepAttributes(request, currentStep);

            request.getRequestDispatcher(GAME_JSP).forward(request, response);
        } catch (Exception e) {
            log.error("Error in GameServlet doPost", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while" +
                    " processing the game.");
        }
    }

    private boolean resetCheck(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        String reset = request.getParameter("reset");
        if ("true".equals(reset)) {
            GameState gameState = gameService.getOrCreateGameState(session);
            gameState.reset();
            response.sendRedirect(request.getContextPath() + GAME);
            return true;
        }
        return false;
    }

    private static boolean checkIfPlayerNameIsSet(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws IOException {
        return !PlayerUtils.ensurePlayerName(request, response, session);
    }

    private static void setGameStepAttributes(HttpServletRequest request, GameStep currentStep) {
        request.setAttribute("question", currentStep.getQuestion());
        request.setAttribute("options", currentStep.getOptions());
        if (currentStep.getEnd() != null) request.setAttribute("end", currentStep.getEnd());
    }
}